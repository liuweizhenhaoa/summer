Redis优化—redis的过期策略和内存淘汰策略
 最近在做redis内存优化的项目，在项目中用到了redis的过期策略和内存淘汰策略。发现这两个策略比较容易混淆，做下笔记方便以后查询使用。
过期策略
我们都知道，Redis是key-value数据库，我们可以设置Redis中缓存的key的过期时间。Redis的过期策略就是指当Redis中缓存的key过期了，Redis如何处理。
过期策略通常有以下三种：
l 定时过期：每个设置过期时间的key都需要创建一个定时器，到过期时间就会立即清除。该策略可以立即清除过期的数据，对内存很友好；但是会占用大量的CPU资源去处理过期的数据，从而影响缓存的响应时间和吞吐量。
l 惰性过期：只有当访问一个key时，才会判断该key是否已过期，过期则清除。该策略可以最大化地节省CPU资源，却对内存非常不友好。极端情况可能出现大量的过期key没有再次被访问，从而不会被清除，占用大量内存。
l 定期过期：每隔一定的时间，会扫描一定数量的数据库的expires字典中一定数量的key，并清除其中已过期的key。该策略是前两者的一个折中方案。通过调整定时扫描的时间间隔和每次扫描的限定耗时，可以在不同情况下使得CPU和内存资源达到最优的平衡效果。(expires字典会保存所有设置了过期时间的key的过期时间数据，其中，key是指向键空间中的某个键的指针，value是该键的毫秒精度的UNIX时间戳表示的过期时间。键空间是指该Redis集群中保存的所有键。)
Redis中同时使用了惰性过期和定期过期两种过期策略。
内存淘汰策略
Redis的内存淘汰策略是指在Redis的用于缓存的内存不足时，怎么处理需要新写入且需要申请额外空间的数据。
Redis的内存淘汰策略的选取并不会影响过期的key的处理。内存淘汰策略用于处理内存不足时的需要申请额外空间的数据；过期策略用于
处理过期的缓存数据
 
Redis关于缓存雪崩 缓存穿透 热点key 及解决办法
 redis关于缓存雪崩 缓存穿透 热点key
穿透
穿透：频繁查询一个不存在的数据，由于缓存不命中，每次都要查询持久层。从而失去缓存的意义。
解决办法： 持久层查询不到就缓存空结果，查询时先判断缓存中是否exists(key) ,如果有直接返回空，没有则查询后返回，
注意insert时需清除查询的key，否则即便DB中有值也查询不到(当然也可以设置空缓存的过期时间）
雪崩
雪崩：缓存大量失效的时候，引发大量查询数据库。
解决办法：
1用锁/分布式锁或者队列串行访问
2缓存失效时间均匀分布
热点key
热点key:某个key访问非常频繁，当key失效的时候有大量线程来构建缓存，导致负载增加，系统崩溃。
解决办法：
1使用锁，单机用synchronized,lock等，分布式用分布式锁。
2缓存过期时间不设置，而是设置在key对应的value里。如果检测到存的时间超过过期时间则异步更新缓存。
3在value设置一个比过期时间t0小的过期时间值t1，当t1过期的时候，延长t1并做更新缓存操作。
4设置标签缓存，标签缓存设置过期时间，标签缓存过期后，需异步地更新实际缓存 具体参照userServiceImpl4的处理方式
总结
一、查询redis缓存时，一般查询如果以非id方式查询，建议先由条件查询到id,再由id查询pojo
二、异步kafka在消费端接受信息后，该怎么识别处理那张表，调用哪个方法，此问题暂时还没解决
三、比较简单的redis缓存，推荐使用canal
 
 
 Redis系列之Redis发布订阅
消息发布者：TestPublish 这个类向频道redisChatTest发布消息，订阅了该频道会收到该频道的消息。
package com.ldzhao.pubsub;

import com.ldzhao.Redis;
import com.ldzhao.RedisFactory;
import org.junit.Test;
import redis.clients.jedis.Jedis;
/*
 * 
 */
public class TestPublish {
	@Test
	public void testPublish() throws Exception {
        Redis redis = RedisFactory.get();
        redis.publish("redisChatTest", "我是天才");
        Thread.sleep(5000);
        redis.publish("redisChatTest", "我牛逼");
        Thread.sleep(5000);
        redis.publish("redisChatTest", "哈哈");
	}
}

消息订阅（订阅者+监听器）：
订阅者：TestSubscribe
该类实现对频道redisChatTest的订阅监听，频道的订阅，取消订阅，收到消息都会调用listener对象的对应方法。注意：subscribe是一个阻塞的方法，在取消订阅该频道前，会一直阻塞在这，只有当取消了订阅才会执行下面的other code，所以，我在onMessage里面收到消息后，调用了this.unsubscribe(); 来取消订阅，这样才会执行后面的other code
package com.ldzhao.pubsub;

import com.ldzhao.Redis;
import com.ldzhao.RedisFactory;
import org.junit.Test;


public class TestSubscribe {

    @Test
    public void testSubscribe() throws Exception {
        Redis redis = RedisFactory.get();
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
        redis.subscribe(listener, "redisChatTest");
        // other code
    }
}
监听器：RedisMsgPubSubListener 消息监听器类，继承自JedisPubSub，并实现其抽象方法
package com.ldzhao.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

public class RedisMsgPubSubListener extends JedisPubSub{

	private static Logger logger = LoggerFactory.getLogger(RedisMsgPubSubListener.class);
	@Override
	public void onMessage(String channel, String message) {
		logger.info("Message received. Channel: {}, Msg: {}", channel, message);
		System.out.println("channel:" + channel + "receives message :" + message);
		// 可以根据设置何时取消订阅
        // this.unsubscribe(); // 如果注释掉则会收到三条信息，间隔是5秒
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {		
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println("channel:" + channel + "is been subscribed:" + subscribedChannels);  
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println("channel:" + channel + "is been unsubscribed:" + subscribedChannels);  
	}
}

  


Redis有哪些数据结构？
字符串String、字典Hash、列表List、集合Set、有序集合SortedSet。
如果你是Redis中高级用户，还需要加上下面几种数据结构HyperLogLog、Geo、Pub/Sub。
如果你说还玩过Redis Module，像BloomFilter，RedisSearch，Redis-ML，面试官得眼睛就开始发亮了。
###使用过Redis分布式锁么，它是什么回事？
先拿setnx来争抢锁，抢到之后，再用expire给锁加一个过期时间防止锁忘记了释放。
这时候对方会告诉你说你回答得不错，然后接着问如果在setnx之后执行expire之前进程意外crash或者要重启维护了，那会怎么样？
这时候你要给予惊讶的反馈：唉，是喔，这个锁就永远得不到释放了。紧接着你需要抓一抓自己得脑袋，故作思考片刻，好像接下来的结果是
你主动思考出来的，然后回答：我记得set指令有非常复杂的参数，这个应该是可以同时把setnx和expire合成一条指令来用的！对方这时
会显露笑容，心里开始默念：摁，这小子还不错。
###假如Redis里面有1亿个key，其中有10w个key是以某个固定的已知的前缀开头的，如果将它们全部找出来？
使用keys指令可以扫出指定模式的key列表。
对方接着追问：如果这个redis正在给线上的业务提供服务，那使用keys指令会有什么问题？
这个时候你要回答redis关键的一个特性：redis的单线程的。keys指令会导致线程阻塞一段时间，线上服务会停顿，直到指令执行完毕，
服务才能恢复。这个时候可以使用scan指令，scan指令可以无阻塞的提取出指定模式的key列表，但是会有一定的重复概率，在客户端
做一次去重就可以了，但是整体所花费的时间会比直接用keys指令长。
###使用过Redis做异步队列么，你是怎么用的？
一般使用list结构作为队列，rpush生产消息，lpop消费消息。当lpop没有消息的时候，要适当sleep一会再重试。
如果对方追问可不可以不用sleep呢？list还有个指令叫blpop，在没有消息的时候，它会阻塞住直到消息到来。
如果对方追问能不能生产一次消费多次呢？使用pub/sub主题订阅者模式，可以实现1:N的消息队列。
如果对方追问pub/sub有什么缺点？在消费者下线的情况下，生产的消息会丢失，得使用专业的消息队列如rabbitmq等。
如果对方追问redis如何实现延时队列？我估计现在你很想把面试官一棒打死如果你手上有一根棒球棍的话，怎么问的这么详细。但是你很克制，然后神态自若的回答道：使用sortedset，拿时间戳作为score，消息内容作为key调用zadd来生产消息，消费者用zrangebyscore指令获取N秒之前的数据轮询进行处理。
到这里，面试官暗地里已经对你竖起了大拇指。但是他不知道的是此刻你却竖起了中指，在椅子背后。
如果有大量的key需要设置同一时间过期，一般需要注意什么？
###如果大量的key过期时间设置的过于集中，到过期的那个时间点，redis可能会出现短暂的卡顿现象。一般需要在时间上加一个随机值，使得过期时间分散一些。
###Redis如何做持久化的？
bgsave做镜像全量持久化，aof做增量持久化。因为bgsave会耗费较长时间，不够实时，在停机的时候会导致大量丢失数据，所以需要aof来配合使用。在redis实例重启时，会使用bgsave持久化文件重新构建内存，再使用aof重放近期的操作指令来实现完整恢复重启之前的状态。
对方追问那如果突然机器掉电会怎样？取决于aof日志sync属性的配置，如果不要求性能，在每条写指令时都sync一下磁盘，就不会丢失数据。但是在高性能的要求下每次都sync是不现实的，一般都使用定时sync，比如1s1次，这个时候最多就会丢失1s的数据。
对方追问bgsave的原理是什么？你给出两个词汇就可以了，fork和cow。fork是指redis通过创建子进程来进行bgsave操作，cow指的是copy on write，子进程创建后，父子进程共享数据段，父进程继续提供读写服务，写脏的页面数据会逐渐和子进程分离开来。
###Pipeline有什么好处，为什么要用pipeline？
可以将多次IO往返的时间缩减为一次，前提是pipeline执行的指令之间没有因果相关性。使用redis-benchmark进行压测的时候可以发现影响redis的QPS峰值的一个重要因素是pipeline批次指令的数目。
###Redis的同步机制了解么？
Redis可以使用主从同步，从从同步。第一次同步时，主节点做一次bgsave，并同时将后续修改操作记录到内存buffer，待完成后将rdb文件全量同步到复制节点，复制节点接受完成后将rdb镜像加载到内存。加载完成后，再通知主节点将期间修改的操作记录同步到复制节点进行重放就完成了同步过程。
###是否使用过Redis集群，集群的原理是什么？
Redis Sentinal着眼于高可用，在master宕机时会自动将slave提升为master，继续提供服务。
Redis Cluster着眼于扩展性，在单个redis内存不足时，使用Cluster进行分片存储。


 
 
redis中事物
考虑redis的时候，有没有考虑容量？大概数据量会有多少？
Redis集群宕机，数据迁移问题	
Nosql、redis等的熟悉、了解
http://www.yiibai.com/redis/redis_quick_guide.html
redis和memcached什么区别?为什么单线程的redis比多线程的memcached效率要高?
redis 支持复杂的数据结构
redis 相比 memcached 来说，拥有更多的数据结构，能支持更丰富的数据操作。如果需要缓存能够支持更复杂的结构和操作， redis 会是不错的选择。
redis 原生支持集群模式
在 redis3.x 版本中，便能支持 cluster 模式，而 memcached 没有原生的集群模式，需要依靠客户端来实现往集群中分片写入数据。
性能对比
由于 redis 只使用单核，而 memcached 可以使用多核，所以平均每一个核上 redis 在存储小数据时比 memcached 性能更高。而在 100k 以上的数据中，memcached 性能要高于 redis，虽然 redis 最近也在存储大数据的性能上进行优化，但是比起 memcached，还是稍有逊色。
为啥 redis 单线程模型也能效率这么高？
纯内存操作
核心是基于非阻塞的 IO 多路复用机制
单线程反而避免了多线程的频繁上下文切换问题
redis有什么数据类型?都在哪些场景下使用?
（1）string
这是最基本的类型了，没啥可说的，就是普通的set和get，做简单的kv缓存
（2）hash
这个是类似map的一种结构，这个一般就是可以将结构化的数据，比如一个对象（前提是这个对象没嵌套其他的对象）给缓存在redis里，然后每次读写缓存的时候，可以就操作hash里的某个字段。
value={
  “id”: 150,
  “name”: “zhangsan”,
  “age”: 20
}
hash类的数据结构，主要是用来存放一些对象，把一些简单的对象给缓存起来，后续操作的时候，你可以直接仅仅修改这个对象中的某个字段的值
value={
  “id”: 150,
  “name”: “zhangsan”,
  “age”: 21
}
（3）list
有序列表，这个是可以玩儿出很多花样的
微博，某个大v的粉丝，就可以以list的格式放在redis里去缓存
key=某大v
value=[zhangsan, lisi, wangwu]
比如可以通过list存储一些列表型的数据结构，类似粉丝列表了、文章的评论列表了之类的东西
比如可以通过lrange命令，就是从某个元素开始读取多少个元素，可以基于list实现分页查询，这个很棒的一个功能，基于redis实现简单的高性能分页，可以做类似微博那种下拉不断分页的东西，性能高，就一页一页走
比如可以搞个简单的消息队列，从list头怼进去，从list尾巴那里弄出来
（4）set
无序集合，自动去重
直接基于set将系统里需要去重的数据扔进去，自动就给去重了，如果你需要对一些数据进行快速的全局去重，你当然也可以基于jvm内存里的HashSet进行去重，但是如果你的某个系统部署在多台机器上呢？
得基于redis进行全局的set去重
可以基于set玩儿交集、并集、差集的操作，比如交集吧，可以把两个人的粉丝列表整一个交集，看看俩人的共同好友是谁？对吧
把两个大v的粉丝都放在两个set中，对两个set做交集
（5）sorted set
排序的set，去重但是可以排序，写进去的时候给一个分数，自动根据分数排序，这个可以玩儿很多的花样，最大的特点是有个分数可以自定义排序规则
比如说你要是想根据时间对数据排序，那么可以写入进去的时候用某个时间作为分数，人家自动给你按照时间排序了
排行榜：将每个用户以及其对应的什么分数写入进去，zadd board score username，接着zrevrange board 0 99，就可以获取排名前100的用户；zrank board username，可以看到用户在排行榜里的排名
zadd board 85 zhangsan
zadd board 72 wangwu
zadd board 96 lisi
zadd board 62 zhaoliu
96 lisi
85 zhangsan
72 wangwu
62 zhaoliu
zrevrange board 0 3
获取排名前3的用户
96 lisi
85 zhangsan
72 wangwu
zrank board zhaoliu
4
 


redis主从复制如何实现的?redis的集群模式如何实现?redis的key是如何寻址的?分布式寻址都有哪些算法？了解一致性 hash 算法吗？
在前几年，redis 如果要搞几个节点，每个节点存储一部分的数据，得借助一些中间件来实现，比如说有 codis，或者 twemproxy，都有。有一些 redis 中间件，你读写 redis 中间件，redis 
中间件负责将你的数据分布式存储在多台机器上的 redis 实例中。
这两年，redis 不断在发展，redis 也不断的有新的版本，现在的 redis 集群模式，可以做到在多台机器上，部署多个 redis 实例，每个实例存储一部分的数据，同时每个 redis 实例可以挂 
redis 从实例，自动确保说，如果 redis 主实例挂了，会自动切换到 redis 从实例顶上来。

现在 redis 的新版本，大家都是用 redis cluster 的，也就是 redis 原生支持的 redis 集群模式，那么面试官肯定会就 redis cluster 对你来个几连炮。要是你没用过 redis cluster，
正常，以前很多人用 codis 之类的客户端来支持集群，但是起码你得研究一下 redis cluster 吧。
如果你的数据量很少，主要是承载高并发高性能的场景，比如你的缓存一般就几个 G，单机就足够了，可以使用 replication，一个 master 多个 slaves，要几个 slave 跟你要求的读吞吐量有关
，然后自己搭建一个 sentinel 集群去保证 redis 主从架构的高可用性。

redis cluster，主要是针对海量数据+高并发+高可用的场景。redis cluster 支撑 N 个 redis master node，每个 master node 都可以挂载多个 slave node。这样整个 
redis 就可以横向扩容了。如果你要支撑更大数据量的缓存，那就横向扩容更多的 master 节点，每个 master 节点就能存放更多的数据了。
面试题剖析
redis cluster 介绍
    自动将数据进行分片，每个 master 上放一部分数据
    提供内置的高可用支持，部分 master 不可用时，还是可以继续工作的
在 redis cluster 架构下，每个 redis 要放开两个端口号，比如一个是 6379，另外一个就是 加1w 的端口号，比如 16379。

16379 端口号是用来进行节点间通信的，也就是 cluster bus 的东西，cluster bus 的通信，用来进行故障检测、配置更新、故障转移授权。cluster bus 用了另外一种二进制的协议，
gossip 协议，用于节点间进行高效的数据交换，占用更少的网络带宽和处理时间。
节点间的内部通信机制
基本通信原理

    redis cluster 节点间采用 gossip 协议进行通信 集中式是将集群元数据（节点信息、故障等等）几种存储在某个节点上。集中式元数据集中存储的一个典型代表
    ，就是大数据领域的 storm。它是分布式的大数据实时计算引擎，是集中式的元数据存储的结构，底层基于 zookeeper（分布式协调的中间件）对所有元数据进行存储维护。

zookeeper-centralized-storage

redis 维护集群元数据采用另一个方式， gossip 协议，所有节点都持有一份元数据，不同的节点如果出现了元数据的变更，就不断将元数据发送给其它的节点，让其它节点也进行元数据的变更。

###redis-gossip
集中式的好处在于，元数据的读取和更新，时效性非常好，一旦元数据出现了变更，就立即更新到集中式的存储中，其它节点读取的时候就可以感知到；不好在于，所有的元数据的更新压力全部集中在
一个地方，可能会导致元数据的存储有压力。

gossip 好处在于，元数据的更新比较分散，不是集中在一个地方，更新请求会陆陆续续，打到所有节点上去更新，降低了压力；不好在于，元数据的更新有延时，可能导致集群中的一些操作会有一些滞后。

    10000 端口 每个节点都有一个专门用于节点间通信的端口，就是自己提供服务的端口号+10000，比如 7001，那么用于节点间通信的就是 17001 端口。每个节点每隔一段时间都会往另外几个节点发送 ping 消息，同时其它几个节点接收到 ping 之后返回 pong。

    交换的信息 信息包括故障信息，节点的增加和删除，hash slot 信息 等等。

gossip 协议

gossip 协议包含多种消息，包含 ping,pong,meet,fail 等等。

    meet：某个节点发送 meet 给新加入的节点，让新节点加入集群中，然后新节点就会开始与其它节点进行通信。

redis-trib.rb add-node

其实内部就是发送了一个 gossip meet 消息给新加入的节点，通知那个节点去加入我们的集群。

    ping：每个节点都会频繁给其它节点发送 ping，其中包含自己的状态还有自己维护的集群元数据，互相通过 ping 交换元数据。
    pong：返回 ping 和 meeet，包含自己的状态和其它信息，也用于信息广播和更新。
    fail：某个节点判断另一个节点 fail 之后，就发送 fail 给其它节点，通知其它节点说，某个节点宕机啦。

ping 消息深入

ping 时要携带一些元数据，如果很频繁，可能会加重网络负担。
每个节点每秒会执行 10 次 ping，每次会选择 5 个最久没有通信的其它节点。当然如果发现某个节点通信延时达到了 cluster_node_timeout / 2，那么立即发送 ping，避免数据交换延时过长，落后的时间太长了。比如说，两个节点之间都 10 分钟没有交换数据了，那么整个集群处于严重的元数据不一致的情况，就会有问题。所以 cluster_node_timeout 可以调节，如果调得比较大，那么会降低 ping 的频率。
每次 ping，会带上自己节点的信息，还有就是带上 1/10 其它节点的信息，发送出去，进行交换。至少包含 3 个其它节点的信息，最多包含总结点-2 个其它节点的信息。
分布式寻址算法

    hash 算法（大量缓存重建）
    一致性 hash 算法（自动缓存迁移）+ 虚拟节点（自动负载均衡）
    redis cluster 的 hash slot 算法

hash 算法
来了一个 key，首先计算 hash 值，然后对节点数取模。然后打在不同的 master 节点上。一旦某一个 master 节点宕机，所有请求过来，都会基于最新的剩余 master 节点数去取模，尝试去取数据。这会导致大部分的请求过来，全部无法拿到有效的缓存，导致大量的流量涌入数据库。
hash
一致性 hash 算法
一致性 hash 算法将整个 hash 值空间组织成一个虚拟的圆环，整个空间按顺时针方向组织，下一步将各个 master 节点（使用服务器的 ip 或主机名）进行 hash。这样就能确定每个节点在其哈希环上的位置。
来了一个 key，首先计算 hash 值，并确定此数据在环上的位置，从此位置沿环顺时针“行走”，遇到的第一个 master 节点就是 key 所在位置。
在一致性哈希算法中，如果一个节点挂了，受影响的数据仅仅是此节点到环空间前一个节点（沿着逆时针方向行走遇到的第一个节点）之间的数据，其它不受影响。增加一个节点也同理。
燃鹅，一致性哈希算法在节点太少时，容易因为节点分布不均匀而造成缓存热点的问题。为了解决这种热点问题，一致性 hash 算法引入了虚拟节点机制，即对每一个节点计算多个 hash，每个计算结果位置都放置一个虚拟节点。这样就实现了数据的均匀分布，负载均衡。

consistent-hashing-algorithm
redis cluster 的 hash slot 算法
redis cluster 有固定的 16384 个 hash slot，对每个 key 计算 CRC16 值，然后对 16384 取模，可以获取 key 对应的 hash slot。

redis cluster 中每个 master 都会持有部分 slot，比如有 3 个 master，那么可能每个 master 持有 5000 多个 hash slot。hash slot 让 node 的增加和移除很简单，增加一个 master，就将其他 master 的 hash slot 移动部分过去，减少一个 master，就将它的 hash slot 移动到其他 master 上去。移动 hash slot 的成本是非常低的。客户端的 api，可以对指定的数据，让他们走同一个 hash slot，通过 hash tag 来实现。

任何一台机器宕机，另外两个节点，不影响的。因为 key 找的是 hash slot，不是机器。

hash-slot
redis cluster 的高可用与主备切换原理

redis cluster 的高可用的原理，几乎跟哨兵是类似的

判断节点宕机
如果一个节点认为另外一个节点宕机，那么就是 pfail，主观宕机。如果多个节点都认为另外一个节点宕机了，那么就是 fail，客观宕机，跟哨兵的原理几乎一样，sdown，odown。
在 cluster-node-timeout 内，某个节点一直没有返回 pong，那么就被认为 pfail。
如果一个节点认为某个节点 pfail 了，那么会在 gossip ping 消息中，ping 给其他节点，如果超过半数的节点都认为 pfail 了，那么就会变成 fail。
从节点过滤
对宕机的 master node，从其所有的 slave node 中，选择一个切换成 master node。
检查每个 slave node 与 master node 断开连接的时间，如果超过了 cluster-node-timeout * cluster-slave-validity-factor，那么就没有资格切换成 master。
从节点选举
每个从节点，都根据自己对 master 复制数据的 offset，来设置一个选举时间，offset 越大（复制数据越多）的从节点，选举时间越靠前，优先进行选举。
所有的 master node 开始 slave 选举投票，给要进行选举的 slave 进行投票，如果大部分 master node（N/2 + 1）都投票给了某个从节点，那么选举通过，那个从节点可以切换成 master。
从节点执行主备切换，从节点切换为主节点。
与哨兵比较
整个流程跟哨兵相比，非常类似，所以说，redis cluster 功能强大，直接集成了 replication 和 sentinel 的功能。

使用redis如何设计分布式锁?说一下实现思路?使用zk可以吗?如何实现?这两种有什么区别?
知道redis 的持久化吗?底层如何实现的?有什么优点缺点?
由于Redis的数据都存放在内存中，如果没有配置持久化，redis重启后数据就全丢失了，于是需要开启redis的持久化功能，将数据保存到磁盘上，当redis重启后，可以从磁盘中恢复数据。redis提供两种方式进行持久化，一种是RDB持久化（原理是将Reids在内存中的数据库记录定时dump到磁盘上的RDB持久化），另外一种是AOF（append only file）持久化（原理是将Reids的操作日志以追加的方式写入文件）。
2、二者的区别
RDB持久化是指在指定的时间间隔内将内存中的数据集快照写入磁盘，实际操作过程是fork一个子进程，先将数据集写入临时文件，写入成功后，再替换之前的文件，用二进制压缩存储。

 
AOF持久化以日志的形式记录服务器所处理的每一个写、删除操作，查询操作不会记录，以文本的方式记录，可以打开文件看到详细的操作记录。

 
3、二者优缺点
RDB存在哪些优势呢？
1). 一旦采用该方式，那么你的整个Redis数据库将只包含一个文件，这对于文件备份而言是非常完美的。比如，你可能打算每个小时归档一次最近24小时的数据，同时还要每天归档一次最近30天的数据。通过这样的备份策略，一旦系统出现灾难性故障，我们可以非常容易的进行恢复。
2). 对于灾难恢复而言，RDB是非常不错的选择。因为我们可以非常轻松的将一个单独的文件压缩后再转移到其它存储介质上。
3). 性能最大化。对于Redis的服务进程而言，在开始持久化时，它唯一需要做的只是fork出子进程，之后再由子进程完成这些持久化的工作，这样就可以极大的避免服务进程执行IO操作了。
4). 相比于AOF机制，如果数据集很大，RDB的启动效率会更高。
RDB又存在哪些劣势呢？
1). 如果你想保证数据的高可用性，即最大限度的避免数据丢失，那么RDB将不是一个很好的选择。因为系统一旦在定时持久化之前出现宕机现象，此前没有来得及写入磁盘的数据都将丢失。
2). 由于RDB是通过fork子进程来协助完成数据持久化工作的，因此，如果当数据集较大时，可能会导致整个服务器停止服务几百毫秒，甚至是1秒钟。
AOF的优势有哪些呢？
1). 该机制可以带来更高的数据安全性，即数据持久性。Redis中提供了3中同步策略，即每秒同步、每修改同步和不同步。事实上，每秒同步也是异步完成的，其效率也是非常高的，所差的是一旦系统出现宕机现象，那么这一秒钟之内修改的数据将会丢失。而每修改同步，我们可以将其视为同步持久化，即每次发生的数据变化都会被立即记录到磁盘中。可以预见，这种方式在效率上是最低的。至于无同步，无需多言，我想大家都能正确的理解它。
2). 由于该机制对日志文件的写入操作采用的是append模式，因此在写入过程中即使出现宕机现象，也不会破坏日志文件中已经存在的内容。然而如果我们本次操作只是写入了一半数据就出现了系统崩溃问题，不用担心，在Redis下一次启动之前，我们可以通过redis-check-aof工具来帮助我们解决数据一致性的问题。
3). 如果日志过大，Redis可以自动启用rewrite机制。即Redis以append模式不断的将修改数据写入到老的磁盘文件中，同时Redis还会创建一个新的文件用于记录此期间有哪些修改命令被执行。因此在进行rewrite切换时可以更好的保证数据安全性。
4). AOF包含一个格式清晰、易于理解的日志文件用于记录所有的修改操作。事实上，我们也可以通过该文件完成数据的重建。
AOF的劣势有哪些呢？
1). 对于相同数量的数据集而言，AOF文件通常要大于RDB文件。RDB 在恢复大数据集时的速度比 AOF 的恢复速度要快。
2). 根据同步策略的不同，AOF在运行效率上往往会慢于RDB。总之，每秒同步策略的效率是比较高的，同步禁用策略的效率和RDB一样高效。
二者选择的标准，就是看系统是愿意牺牲一些性能，换取更高的缓存一致性（aof），还是愿意写操作频繁的时候，不启用备份来换取更高的性能，待手动运行save的时候，再做备份（rdb）。rdb这个就更有些 eventually consistent的意思了。
4、常用配置
RDB持久化配置
Redis会将数据集的快照dump到dump.rdb文件中。此外，我们也可以通过配置文件来修改Redis服务器dump快照的频率，在打开6379.conf文件之后，我们搜索save，可以看到下面的配置信息：
save 900 1              #在900秒(15分钟)之后，如果至少有1个key发生变化，则dump内存快照。
save 300 10            #在300秒(5分钟)之后，如果至少有10个key发生变化，则dump内存快照。
save 60 10000        #在60秒(1分钟)之后，如果至少有10000个key发生变化，则dump内存快照。
AOF持久化配置
在Redis的配置文件中存在三种同步方式，它们分别是：
appendfsync always     #每次有数据修改发生时都会写入AOF文件。
appendfsync everysec  #每秒钟同步一次，该策略为AOF的缺省策略。
appendfsync no          #从不同步。高效但是数据不会被持久化。
redis过期策略都有哪些?LRU算法知道吗?写一下java代码实现?
redis 过期策略是：定期删除+惰性删除。
所谓定期删除，指的是 redis 默认是每隔 100ms 就随机抽取一些设置了过期时间的 key，检查其是否过期，如果过期就删除。
假设 redis 里放了 10w 个 key，都设置了过期时间，你每隔几百毫秒，就检查 10w 个 key，那 redis 基本上就死了，cpu 负载会很高的，消耗在你的检查过期 key 上了。注意，这里可不是每隔 100ms 就遍历所有的设置过期时间的 key，那样就是一场性能上的灾难。实际上 redis 是每隔 100ms 随机抽取一些 key 来检查和删除的。
但是问题是，定期删除可能会导致很多过期 key 到了时间并没有被删除掉，那咋整呢？所以就是惰性删除了。这就是说，在你获取某个 key 的时候，redis 会检查一下 ，这个 key 如果设置了过期时间那么是否过期了？如果过期了此时就会删除，不会给你返回任何东西。
    获取 key 的时候，如果此时 key 已经过期，就删除，不会返回任何东西。
但是实际上这还是有问题的，如果定期删除漏掉了很多过期 key，然后你也没及时去查，也就没走惰性删除，此时会怎么样？如果大量过期 key 堆积在内存里，导致 redis 内存块耗尽了，咋整？
答案是：走内存淘汰机制。
内存淘汰机制
redis 内存淘汰机制有以下几个：
    noeviction: 当内存不足以容纳新写入数据时，新写入操作会报错，这个一般没人用吧，实在是太恶心了。
    allkeys-lru：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的 key（这个是最常用的）。
    allkeys-random：当内存不足以容纳新写入数据时，在键空间中，随机移除某个 key，这个一般没人用吧，为啥要随机，肯定是把最近最少使用的 key 给干掉啊。
    volatile-lru：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，移除最近最少使用的 key（这个一般不太合适）。
    volatile-random：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，随机移除某个 key。
    volatile-ttl：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，有更早过期时间的 key 优先移除
1、redis和memcheched 什么区别为什么单线程的redis比多线程的memched效率要高啊?
相同点：都是使用的多路io复用的方式，减少了阻塞，充分的利用cpu和内存性能。
不同点：redis单进程单线程，Memcache 多进程单线程，redis自己写了一套epoll的实现，而Memcache 使用的是Libevent，这个组件本身比较大，有很多无用代码，对Memcache性能有影响，还有就是Memcache采用的CAS这种方式也会对性能造成影响。
2、redis有什么数据类型都在哪些场景下使用啊?
3、reids的主从复制是怎么实现的redis的集群模式是如何实现的呢redis的key是如何寻址的啊?
1.全量同步
Redis全量复制一般发生在Slave初始化阶段，这时Slave需要将Master上的所有数据都复制一份。具体步骤如下：
　　1）从服务器连接主服务器，发送SYNC命令；
　　2）主服务器接收到SYNC命名后，开始执行BGSAVE命令生成RDB文件并使用缓冲区记录此后执行的所有写命令；
　　3）主服务器BGSAVE执行完后，向所有从服务器发送快照文件，并在发送期间继续记录被执行的写命令；
　　4）从服务器收到快照文件后丢弃所有旧数据，载入收到的快照；
　　5）主服务器快照发送完毕后开始向从服务器发送缓冲区中的写命令；
　　6）从服务器完成对快照的载入，开始接收命令请求，并执行来自主服务器缓冲区的写命令；
2.增量同步
RedisSlave初始化后开始正常工作时主服务器发生的写操作同步到从服务器的过程。
增量复制的过程主要是主服务器每执行一个写命令就会向从服务器发送相同的写命令，从服务器接收并执行收到的写命令。
3.同步策略
主从刚刚连接的时候，进行全量同步；全同步结束后，进行增量同步。当然，如果有需要，slave 在任何时候都可以发起全is 策略是，无论如何，首先会尝试进行增量同步，如不成功，要求从机进行全量同步。
4.注意点
如果多个Slave断线了，需要重启的时候，因为只要Slave启动，就会发送sync请求和主机全量同步，当多个同时出现的时候，可能会导致Master IO剧增宕机。
1.3 分布式锁
（1）使用redis如何设计分布式锁？使用zk来设计分布式锁可以吗？这两种分布式锁的实现方式哪种效率比较高？

（1）redis分布式锁
官方叫做RedLock算法，是redis官方支持的分布式锁算法。

这个分布式锁有3个重要的考量点，互斥（只能有一个客户端获取锁），不能死锁，容错（大部分redis节点或者这个锁就可以加可以释放）。

第一个最普通的实现方式，如果就是在redis里创建一个key算加锁。

SET my:lock 随机值 NX PX 30000，这个命令就ok，这个的NX的意思就是只有key不存在的时候才会设置成功，PX 30000的意思是30秒后锁自动释放。别人创建的时候如果发现已经有了就不能加锁了。

释放锁就是删除key，但是一般可以用lua脚本删除，判断value一样才删除：

关于redis如何执行lua脚本，自行百度
  if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
    else
        return 0
    end


为啥要用随机值呢？因为如果某个客户端获取到了锁，但是阻塞了很长时间才执行完，此时可能已经自动释放锁了，此时可能别的客户端已经获取到了这个锁，要是你这个时候直接删除key的话会有问题，
所以得用随机值加上面的lua脚本来释放锁。

但是这样是肯定不行的。因为如果是普通的redis单实例，那就是单点故障。或者是redis普通主从，那redis主从异步复制，如果主节点挂了，key还没同步到从节点，此时从节点切换为主节点，
别人就会拿到锁。

redis最普通的分布式锁的实现原理.png

第二个问题，RedLock算法

这个场景是假设有一个redis cluster，有5个redis master实例。然后执行如下步骤获取一把锁：

1）获取当前时间戳，单位是毫秒；
2）跟上面类似，轮流尝试在每个master节点上创建锁，过期时间较短，一般就几十毫秒；
3）尝试在大多数节点上建立一个锁，比如5个节点就要求是3个节点（n / 2 +1）；
4）客户端计算建立好锁的时间，如果建立锁的时间小于超时时间，就算建立成功了；
5）要是锁建立失败了，那么就依次删除这个锁；
6）只要别人建立了一把分布式锁，你就得不断轮询去尝试获取锁。


（2）zk分布式锁

zk分布式锁，其实可以做的比较简单，就是某个节点尝试创建临时znode，此时创建成功了就获取了这个锁；这个时候别的客户端来创建锁会失败，只能注册个监听器监听这个锁。释放锁就是删除这个znode，一旦释放掉就会通知客户端，然后有一个等待着的客户端就可以再次重新枷锁。

    /**
     * ZooKeeperSession
     * @author Administrator
     *
     */
    public class ZooKeeperSession {
        
        private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
        
        private ZooKeeper zookeeper;
    private CountDownLatch latch;
     
        public ZooKeeperSession() {
            try {
                this.zookeeper = new ZooKeeper(
                        "192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181", 
                        50000, 
                        new ZooKeeperWatcher());            
                try {
                    connectedSemaphore.await();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
     
                System.out.println("ZooKeeper session established......");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取分布式锁
         * @param productId
         */
        public Boolean acquireDistributedLock(Long productId) {
            String path = "/product-lock-" + productId;
        
            try {
                zookeeper.create(path, "".getBytes(), 
                        Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    return true;
            } catch (Exception e) {
    while(true) {
                    try {
    Stat stat = zk.exists(path, true); // 相当于是给node注册一个监听器，去看看这个监听器是否存在
    if(stat != null) {
    this.latch = new CountDownLatch(1);
    this.latch.await(waitTime, TimeUnit.MILLISECONDS);
    this.latch = null;
    }
    zookeeper.create(path, "".getBytes(), 
                            Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    return true;
    } catch(Exception e) {
    continue;
    }
    }

    // 很不优雅，我呢就是给大家来演示这么一个思路
    // 比较通用的，我们公司里我们自己封装的基于zookeeper的分布式锁，我们基于zookeeper的临时顺序节点去实现的，比较优雅的
            }
    return true;
        }

        /**
         * 释放掉一个分布式锁
         * @param productId
         */
        public void releaseDistributedLock(Long productId) {
            String path = "/product-lock-" + productId;
            try {
                zookeeper.delete(path, -1); 
                System.out.println("release the lock for product[id=" + productId + "]......");  
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 建立zk session的watcher
         * @author Administrator
         *
         */
        private class ZooKeeperWatcher implements Watcher {
     
            public void process(WatchedEvent event) {
                System.out.println("Receive watched event: " + event.getState());
     
                if(KeeperState.SyncConnected == event.getState()) {
                    connectedSemaphore.countDown();
                } 
     
    if(this.latch != null) {  
    this.latch.countDown();  
    }
            }
            
        }

        /**
         * 封装单例的静态内部类
         * @author Administrator
         *
         */
        private static class Singleton {
            
            private static ZooKeeperSession instance;
            
            static {
                instance = new ZooKeeperSession();
            }
            
            public static ZooKeeperSession getInstance() {
                return instance;
            }
            
        }

        /**
         * 获取单例
         * @return
         */
        public static ZooKeeperSession getInstance() {
            return Singleton.getInstance();
        }

        /**
         * 初始化单例的便捷方法
         */
        public static void init() {
            getInstance();
        }
        
    }
     



（3）redis分布式锁和zk分布式锁的对比

redis分布式锁，其实需要自己不断去尝试获取锁，比较消耗性能；

zk分布式锁，获取不到锁，注册个监听器即可，不需要不断主动尝试获取锁，性能开销较小。

另外一点就是，如果是redis获取锁的那个客户端bug了或者挂了，那么只能等待超时时间之后才能释放锁；而zk的话，因为创建的是临时znode，只要客户端挂了，znode就没了，此时就自动释放锁。

redis分布式锁大家每发现好麻烦吗？遍历上锁，计算时间等等。zk的分布式锁语义清晰实现简单。

所以先不分析太多的东西，就说这两点，我个人实践认为zk的分布式锁比redis的分布式锁牢靠、而且模型简单易用。

    public class ZooKeeperDistributedLock implements Watcher{
        
        private ZooKeeper zk;
        private String locksRoot= "/locks";
        private String productId;
        private String waitNode;
        private String lockNode;
        private CountDownLatch latch;
        private CountDownLatch connectedLatch = new CountDownLatch(1);
    private int sessionTimeout = 30000; 
     
        public ZooKeeperDistributedLock(String productId){
            this.productId = productId;
             try {
           String address = "192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181";
                zk = new ZooKeeper(address, sessionTimeout, this);
                connectedLatch.await();
            } catch (IOException e) {
                throw new LockException(e);
            } catch (KeeperException e) {
                throw new LockException(e);
            } catch (InterruptedException e) {
                throw new LockException(e);
            }
        }
     
        public void process(WatchedEvent event) {
            if(event.getState()==KeeperState.SyncConnected){
                connectedLatch.countDown();
                return;
            }
     
            if(this.latch != null) {  
                this.latch.countDown(); 
            }
        }
     
        public void acquireDistributedLock() {   
            try {
                if(this.tryLock()){
                    return;
                }
                else{
                    waitForLock(waitNode, sessionTimeout);
                }
            } catch (KeeperException e) {
                throw new LockException(e);
            } catch (InterruptedException e) {
                throw new LockException(e);
            } 
    }
     
        public boolean tryLock() {
            try {
            // 传入进去的locksRoot + “/” + productId
            // 假设productId代表了一个商品id，比如说1
            // locksRoot = locks
            // /locks/10000000000，/locks/10000000001，/locks/10000000002
                lockNode = zk.create(locksRoot + "/" + productId, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
       
                // 看看刚创建的节点是不是最小的节点
            // locks：10000000000，10000000001，10000000002
                List<String> locks = zk.getChildren(locksRoot, false);
                Collections.sort(locks);
        
                if(lockNode.equals(locksRoot+"/"+ locks.get(0))){
                    //如果是最小的节点,则表示取得锁
                    return true;
                }
        
                //如果不是最小的节点，找到比自己小1的节点
          int previousLockIndex = -1;
                for(int i = 0; i < locks.size(); i++) {
            if(lockNode.equals(locksRoot + “/” + locks.get(i))) {
                        previousLockIndex = i - 1;
                break;
            }
           }
           
           this.waitNode = locks.get(previousLockIndex);
            } catch (KeeperException e) {
                throw new LockException(e);
            } catch (InterruptedException e) {
                throw new LockException(e);
            }
            return false;
        }
         
        private boolean waitForLock(String waitNode, long waitTime) throws InterruptedException, KeeperException {
            Stat stat = zk.exists(locksRoot + "/" + waitNode, true);
            if(stat != null){
                this.latch = new CountDownLatch(1);
                this.latch.await(waitTime, TimeUnit.MILLISECONDS);                 this.latch = null;
            }
            return true;
    }
     
        public void unlock() {
            try {
            // 删除/locks/10000000000节点
            // 删除/locks/10000000001节点
                System.out.println("unlock " + lockNode);
                zk.delete(lockNode,-1);
                lockNode = null;
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
    }
     
        public class LockException extends RuntimeException {
            private static final long serialVersionUID = 1L;
            public LockException(String e){
                super(e);
            }
            public LockException(Exception e){
                super(e);
            }
    }

     
    // 如果有一把锁，被多个人给竞争，此时多个人会排队，第一个拿到锁的人会执行，然后释放锁，后面的每个人都会去监听排在自己前面的那个人创建的node上，一旦某个人释放了锁，排在自己后面的人就会被zookeeper给通知，一旦被通知了之后，就ok了，自己就获取到了锁，就可以执行代码了
     
    }  
Redis详解（四）------ redis的底层数据结构
目录
1、演示数据类型的实现
2、简单动态字符串
3、链表
4、字典
5、跳跃表
6、整数集合
7、压缩列表
8、总结
 
--------------------------------------------------------------------------------
　　上一篇博客我们介绍了 redis的五大数据类型详细用法，但是在 Redis 中，这几种数据类型底层是由什么数据结构构造的呢？本篇博客我们就来详细介绍Redis中五大数据类型的底层实现。
回到顶部
1、演示数据类型的实现
　　上篇博客我们在介绍 key 相关命令的时候，介绍了如下命令：
OBJECT ENCODING    key 
　　该命令是用来显示那五大数据类型的底层数据结构。
　　比如对于 string 数据类型：
　　

　　我们可以看到实现string数据类型的数据结构有 embstr 以及 int。
　　再比如 list 数据类型：
　　

　　这里我们就不做过多的演示了，那么上次出现的 embstr 以及 int 还有 quicklist 是什么数据结构呢？下面我们就来介绍Redis中几种主要的数据结构。
回到顶部
2、简单动态字符串
　　第一篇文章我们就说过 Redis 是用 C 语言写的，但是对于Redis的字符串，却不是 C 语言中的字符串（即以空字符’\0’结尾的字符数组），它是自己构建了一种名为 简单动态字符串（simple dynamic string,SDS）的抽象类型，并将 SDS 作为 Redis的默认字符串表示。
　　SDS 定义：
123456789 struct sdshdr{     //记录buf数组中已使用字节的数量     //等于 SDS 保存字符串的长度     int len;     //记录 buf 数组中未使用字节的数量     int free;     //字节数组，用于保存字符串     char buf[];}
　　用SDS保存字符串 “Redis”具体图示如下：
　　 

　　　　　　　　　图片来源：《Redis设计与实现》
 　　我们看上面对于 SDS 数据类型的定义：
　　1、len 保存了SDS保存字符串的长度
　　2、buf[] 数组用来保存字符串的每个元素
　　3、free j记录了 buf 数组中未使用的字节数量
　　上面的定义相对于 C 语言对于字符串的定义，多出了 len 属性以及 free 属性。为什么不使用C语言字符串实现，而是使用 SDS呢？这样实现有什么好处？
　　①、常数复杂度获取字符串长度
　　由于 len 属性的存在，我们获取 SDS 字符串的长度只需要读取 len 属性，时间复杂度为 O(1)。而对于 C 语言，获取字符串的长度通常是经过遍历计数来实现的，时间复杂度为 O(n)。通过 strlen key 命令可以获取 key 的字符串长度。
　　②、杜绝缓冲区溢出
　　我们知道在 C 语言中使用 strcat  函数来进行两个字符串的拼接，一旦没有分配足够长度的内存空间，就会造成缓冲区溢出。而对于 SDS 数据类型，在进行字符修改的时候，会首先根据记录的 len 属性检查内存空间是否满足需求，如果不满足，会进行相应的空间扩展，然后在进行修改操作，所以不会出现缓冲区溢出。
　　③、减少修改字符串的内存重新分配次数
　　C语言由于不记录字符串的长度，所以如果要修改字符串，必须要重新分配内存（先释放再申请），因为如果没有重新分配，字符串长度增大时会造成内存缓冲区溢出，字符串长度减小时会造成内存泄露。
　　而对于SDS，由于len属性和free属性的存在，对于修改字符串SDS实现了空间预分配和惰性空间释放两种策略：
　　1、空间预分配：对字符串进行空间扩展的时候，扩展的内存比实际需要的多，这样可以减少连续执行字符串增长操作所需的内存重分配次数。
　　2、惰性空间释放：对字符串进行缩短操作时，程序不立即使用内存重新分配来回收缩短后多余的字节，而是使用 free 属性将这些字节的数量记录下来，等待后续使用。（当然SDS也提供了相应的API，当我们有需要时，也可以手动释放这些未使用的空间。）
　　④、二进制安全
　　因为C字符串以空字符作为字符串结束的标识，而对于一些二进制文件（如图片等），内容可能包括空字符串，因此C字符串无法正确存取；而所有 SDS 的API 都是以处理二进制的方式来处理 buf 里面的元素，并且 SDS 不是以空字符串来判断是否结束，而是以 len 属性表示的长度来判断字符串是否结束。
　　⑤、兼容部分 C 字符串函数
　　虽然 SDS 是二进制安全的，但是一样遵从每个字符串都是以空字符串结尾的惯例，这样可以重用 C 语言库<string.h> 中的一部分函数。
　　⑥、总结
　　

　　一般来说，SDS 除了保存数据库中的字符串值以外，SDS 还可以作为缓冲区（buffer）：包括 AOF 模块中的AOF缓冲区以及客户端状态中的输入缓冲区。后面在介绍Redis的持久化时会进行介绍。
回到顶部
3、链表
　　链表是一种常用的数据结构，C 语言内部是没有内置这种数据结构的实现，所以Redis自己构建了链表的实现。关于链表的详细介绍可以参考我的这篇博客。
　　链表定义：
12345678 typedef  struct listNode{       //前置节点       struct listNode *prev;       //后置节点       struct listNode *next;       //节点的值       void *value;  }listNode
　　通过多个 listNode 结构就可以组成链表，这是一个双端链表，Redis还提供了操作链表的数据结构：
1234567891011121314 typedef struct list{     //表头节点     listNode *head;     //表尾节点     listNode *tail;     //链表所包含的节点数量     unsigned long len;     //节点值复制函数     void (*free) (void *ptr);     //节点值释放函数     void (*free) (void *ptr);     //节点值对比函数     int (*match) (void *ptr,void *key);}list;
　　

　　Redis链表特性：
　　①、双端：链表具有前置节点和后置节点的引用，获取这两个节点时间复杂度都为O(1)。
　　②、无环：表头节点的 prev 指针和表尾节点的 next 指针都指向 NULL,对链表的访问都是以 NULL 结束。　　
　　③、带链表长度计数器：通过 len 属性获取链表长度的时间复杂度为 O(1)。
　　④、多态：链表节点使用 void* 指针来保存节点值，可以保存各种不同类型的值。
回到顶部
4、字典
　　字典又称为符号表或者关联数组、或映射（map），是一种用于保存键值对的抽象数据结构。字典中的每一个键 key 都是唯一的，通过 key 可以对值来进行查找或修改。C 语言中没有内置这种数据结构的实现，所以字典依然是 Redis自己构建的。
　　Redis 的字典使用哈希表作为底层实现，关于哈希表的详细讲解可以参考我这篇博客。
　　哈希表结构定义：
 typedef struct dictht{     //哈希表数组     dictEntry **table;     //哈希表大小     unsigned long size;     //哈希表大小掩码，用于计算索引值     //总是等于 size-1     unsigned long sizemask;     //该哈希表已有节点的数量     unsigned long used; }dictht
　　哈希表是由数组 table 组成，table 中每个元素都是指向 dict.h/dictEntry 结构，dictEntry 结构定义如下：
 typedef struct dictEntry{     //键     void *key;     //值     union{          void *val;          uint64_tu64;          int64_ts64;     }v;      //指向下一个哈希表节点，形成链表     struct dictEntry *next;}dictEntry
　　key 用来保存键，val 属性用来保存值，值可以是一个指针，也可以是uint64_t整数，也可以是int64_t整数。
　　注意这里还有一个指向下一个哈希表节点的指针，我们知道哈希表最大的问题是存在哈希冲突，如何解决哈希冲突，有开放地址法和链地址法。这里采用的便是链地址法，通过next这个指针可以将多个哈希值相同的键值对连接在一起，用来解决哈希冲突。
　　

　　①、哈希算法：Redis计算哈希值和索引值方法如下：
1234 #1、使用字典设置的哈希函数，计算键 key 的哈希值hash = dict->type->hashFunction(key);#2、使用哈希表的sizemask属性和第一步得到的哈希值，计算索引值index = hash & dict->ht[x].sizemask;
　　②、解决哈希冲突：这个问题上面我们介绍了，方法是链地址法。通过字典里面的 *next 指针指向下一个具有相同索引值的哈希表节点。
　　③、扩容和收缩：当哈希表保存的键值对太多或者太少时，就要通过 rerehash(重新散列）来对哈希表进行相应的扩展或者收缩。具体步骤：
　　　　　　1、如果执行扩展操作，会基于原哈希表创建一个大小等于 ht[0].used*2n 的哈希表（也就是每次扩展都是根据原哈希表已使用的空间扩大一倍创建另一个哈希表）。相反如果执行的是收缩操作，每次收缩是根据已使用空间缩小一倍创建一个新的哈希表。
　　　　　　2、重新利用上面的哈希算法，计算索引值，然后将键值对放到新的哈希表位置上。
　　　　　　3、所有键值对都迁徙完毕后，释放原哈希表的内存空间。
　　④、触发扩容的条件：
　　　　　　1、服务器目前没有执行 BGSAVE 命令或者 BGREWRITEAOF 命令，并且负载因子大于等于1。
　　　　　　2、服务器目前正在执行 BGSAVE 命令或者 BGREWRITEAOF 命令，并且负载因子大于等于5。
　　　　ps：负载因子 = 哈希表已保存节点数量 / 哈希表大小。
　　⑤、渐近式 rehash
　　　　什么叫渐进式 rehash？也就是说扩容和收缩操作不是一次性、集中式完成的，而是分多次、渐进式完成的。如果保存在Redis中的键值对只有几个几十个，那么 rehash 操作可以瞬间完成，但是如果键值对有几百万，几千万甚至几亿，那么要一次性的进行 rehash，势必会造成Redis一段时间内不能进行别的操作。所以Redis采用渐进式 rehash,这样在进行渐进式rehash期间，字典的删除查找更新等操作可能会在两个哈希表上进行，第一个哈希表没有找到，就会去第二个哈希表上进行查找。但是进行 增加操作，一定是在新的哈希表上进行的。
回到顶部
5、跳跃表
　　关于跳跃表的趣味介绍：http://blog.jobbole.com/111731/
　　跳跃表（skiplist）是一种有序数据结构，它通过在每个节点中维持多个指向其它节点的指针，从而达到快速访问节点的目的。具有如下性质：
　　1、由很多层结构组成；
　　2、每一层都是一个有序的链表，排列顺序为由高层到底层，都至少包含两个链表节点，分别是前面的head节点和后面的nil节点；
　　3、最底层的链表包含了所有的元素；
　　4、如果一个元素出现在某一层的链表中，那么在该层之下的链表也全都会出现（上一层的元素是当前层的元素的子集）；
　　5、链表中的每个节点都包含两个指针，一个指向同一层的下一个链表节点，另一个指向下一层的同一个链表节点；
　　

　　Redis中跳跃表节点定义如下：
1234567891011121314151617 typedef struct zskiplistNode {     //层     struct zskiplistLevel{           //前进指针           struct zskiplistNode *forward;           //跨度           unsigned int span;     }level[];      //后退指针     struct zskiplistNode *backward;     //分值     double score;     //成员对象     robj *obj; } zskiplistNode
　　多个跳跃表节点构成一个跳跃表：
123456789 typedef struct zskiplist{     //表头节点和表尾节点     structz skiplistNode *header, *tail;     //表中节点的数量     unsigned long length;     //表中层数最大的节点的层数     int level; }zskiplist;
　　

　　①、搜索：从最高层的链表节点开始，如果比当前节点要大和比当前层的下一个节点要小，那么则往下找，也就是和当前层的下一层的节点的下一个节点进行比较，以此类推，一直找到最底层的最后一个节点，如果找到则返回，反之则返回空。
　　②、插入：首先确定插入的层数，有一种方法是假设抛一枚硬币，如果是正面就累加，直到遇见反面为止，最后记录正面的次数作为插入的层数。当确定插入的层数k后，则需要将新元素插入到从底层到k层。
　　③、删除：在各个层中找到包含指定值的节点，然后将节点从链表中删除即可，如果删除以后只剩下头尾两个节点，则删除这一层。
回到顶部
6、整数集合
　　整数集合（intset）是Redis用于保存整数值的集合抽象数据类型，它可以保存类型为int16_t、int32_t 或者int64_t 的整数值，并且保证集合中不会出现重复元素。
　　定义如下：
123456789 typedef struct intset{     //编码方式     uint32_t encoding;     //集合包含的元素数量     uint32_t length;     //保存元素的数组     int8_t contents[]; }intset;
　　整数集合的每个元素都是 contents 数组的一个数据项，它们按照从小到大的顺序排列，并且不包含任何重复项。
　　length 属性记录了 contents 数组的大小。
　　需要注意的是虽然 contents 数组声明为 int8_t 类型，但是实际上contents 数组并不保存任何 int8_t 类型的值，其真正类型有 encoding 来决定。
　　①、升级
　　当我们新增的元素类型比原集合元素类型的长度要大时，需要对整数集合进行升级，才能将新元素放入整数集合中。具体步骤：
　　1、根据新元素类型，扩展整数集合底层数组的大小，并为新元素分配空间。
　　2、将底层数组现有的所有元素都转成与新元素相同类型的元素，并将转换后的元素放到正确的位置，放置过程中，维持整个元素顺序都是有序的。
　　3、将新元素添加到整数集合中（保证有序）。
　　升级能极大地节省内存。
　　②、降级
　　整数集合不支持降级操作，一旦对数组进行了升级，编码就会一直保持升级后的状态。
回到顶部
7、压缩列表
　　压缩列表（ziplist）是Redis为了节省内存而开发的，是由一系列特殊编码的连续内存块组成的顺序型数据结构，一个压缩列表可以包含任意多个节点（entry），每个节点可以保存一个字节数组或者一个整数值。
　　压缩列表的原理：压缩列表并不是对数据利用某种算法进行压缩，而是将数据按照一定规则编码在一块连续的内存区域，目的是节省内存。
　　

　　压缩列表的每个节点构成如下：
　　

　　①、previous_entry_ength：记录压缩列表前一个字节的长度。previous_entry_ength的长度可能是1个字节或者是5个字节，如果上一个节点的长度小于254，则该节点只需要一个字节就可以表示前一个节点的长度了，如果前一个节点的长度大于等于254，则previous length的第一个字节为254，后面用四个字节表示当前节点前一个节点的长度。利用此原理即当前节点位置减去上一个节点的长度即得到上一个节点的起始位置，压缩列表可以从尾部向头部遍历。这么做很有效地减少了内存的浪费。
　　②、encoding：节点的encoding保存的是节点的content的内容类型以及长度，encoding类型一共有两种，一种字节数组一种是整数，encoding区域长度为1字节、2字节或者5字节长。
　　③、content：content区域用于保存节点的内容，节点内容类型和长度由encoding决定。
回到顶部
8、总结
　　大多数情况下，Redis使用简单字符串SDS作为字符串的表示，相对于C语言字符串，SDS具有常数复杂度获取字符串长度，杜绝了缓存区的溢出，减少了修改字符串长度时所需的内存重分配次数，以及二
进制安全能存储各种类型的文件，并且还兼容部分C函数。
　　通过为链表设置不同类型的特定函数，Redis链表可以保存各种不同类型的值，除了用作列表键，还在发布与订阅、慢查询、监视器等方面发挥作用（后面会介绍）。
　　Redis的字典底层使用哈希表实现，每个字典通常有两个哈希表，一个平时使用，另一个用于rehash时使用，使用链地址法解决哈希冲突。
　　跳跃表通常是有序集合的底层实现之一，表中的节点按照分值大小进行排序。
　　整数集合是集合键的底层实现之一，底层由数组构成，升级特性能尽可能的节省内存。
　　压缩列表是Redis为节省内存而开发的顺序型数据结构，通常作为列表键和哈希键的底层实现之一。
　　以上介绍的简单字符串、链表、字典、跳跃表、整数集合、压缩列表等数据结构就是Redis底层的一些数据结构，用来实现上一篇博客介绍的Redis五大数据类型，那么每种数据类型是由哪些数据结构实现的呢？下一篇博客进行介绍。