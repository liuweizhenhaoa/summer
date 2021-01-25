
1. junit用法，before,beforeClass,after, afterClass的执行顺序

一个测试类单元测试的执行顺序为：

@BeforeClass –> @Before –> @Test –> @After –> @AfterClass

每一个测试方法的调用顺序为：

@Before –> @Test –> @After

参考： http://blog.csdn.net/wangpeng047/article/details/9631193


2. 分布式锁

一、zookeeper 瞬时有序节点：每个客户端对某个功能加锁时，在zookeeper上的与该功能对应的指定节点的目录下，生成一个唯一的瞬时有序节点

二、memcached add函数：add会添加第一个到达的值，并返回true，后续的添加则都会返回false。（无法持久化）

三、redis分布式锁

redis分布式锁即可以结合zk分布式锁锁高度安全和memcached并发场景下效率很好的优点，可以利用jedis客户端实现。

参考http://blog.csdn.net/java2000_wl/article/details/8740911

http://surlymo.iteye.com/blog/2082684


3. nginx的请求转发算法，如何配置根据权重转发

当在一台主机上部署了多个不同的web服务器，并且需要能在80端口同时访问这些web服务器时，可以使用 nginx 的反向代理功能: 用 nginx 在80端口监听所有请求，并依据转发规则(比较常见的是以 URI 来转发)转发到对应的web服务器上。

例如有 webmail , webcom 以及 webdefault 三个服务器分别运行在 portmail , portcom , portdefault 端口，要实现从80端口同时访问这三个web服务器，则可以在80端口运行 nginx， 然后将 /mail 下的请求转发到 webmail 服务器， 将 /com下的请求转发到 webcom 服务器， 将其他所有请求转发到 webdefault 服务器。

http://blog.csdn.net/tobacco5648/article/details/51099426


4. 用hashmap实现redis有什么问题

（死锁，死循环，可用ConcurrentHashmap）


5. 线程的状态

五个状态之一：新建状态、就绪状态、运行状态、阻塞状态及死亡状态。


5. 线程的阻塞的方式

sleep() wait() join()


6. sleep和wait的区别

对于sleep()方法，是属于Thread类中的。而wait()方法，则是属于Object类中的。

sleep()方法导致了程序暂停执行指定的时间，让出cpu该其他线程，线程不会释放对象锁。

而当调用wait()方法的时候，线程会放弃对象锁，对象调用notify()方法后本线程才获取对象锁进入运行状态。


7. hashmap的底层实现

HashMap是基于哈希表的Map接口的非同步实现（Hashtable跟HashMap很像，唯一的区别是Hashtalbe中的方法是线程安全的，也就是同步的）。

HashMap底层就是一个数组，数组中的每一项又是一个链表。当程序试图将一个key-value对放入HashMap中时，程序首先根据该 key 的 hashCode() 返回值决定该 Entry 的存储位置：如果两个 Entry 的 key 的 hashCode() 返回值相同，那它们的存储位置相同。如果这两个 Entry 的key 通过 equals 比较返回 true，新添加 Entry 的 value 将覆盖集合中原有 Entry 的 value，但key不会覆盖。如果这两个 Entry 的 key 通过 equals 比较返回 false，新添加的 Entry 将与集合中原有 Entry 形成 Entry 链

http://www.cnblogs.com/ITtangtang/p/3948406.html


8. 一万个人抢100个红包，如何实现（不用队列），如何保证2个人不能抢到同一个红包？

可用jedisLock—redis分布式锁实现：基本原理：用一个状态值表示锁，对锁的占用和释放通过状态值来标识。

SETNX key value/expire KEY seconds/del KEY

http://blog.csdn.net/u010359884/article/details/50310387

http://www.cnblogs.com/0201zcr/p/5942748.html


9. java内存模型，

垃圾回收机制，不可达算法


10. 两个Integer的引用对象传给一个swap方法在方法内部交换引用，返回后，两个引用的值是否会发现变化

不会！Java里方法的参数传递方式只有一种：值传递。

Integer a =1;Integer b=2; swap(Integer a1,Integer b1){c=b1;b1=a1;a1=c}



11. aop的底层实现，动态代理是如何动态，假如有100个对象，如何动态的为这100个对象代理

AOP的核心机制通过动态代理来实现(jdk动态代理和cglib动态代理)


12. 是否用过maven install。 maven test。Git（make install是安装本地jar包）

maven install 生成jar包

maven test 运行 src/test/java下的测试用例

mvn install -Dmaven.test.skip=true跳过测试

http://www.cnblogs.com/phoebus0501/archive/2011/05/10/2042511.html


13. tomcat的各种配置，如何配置docBase

appBase这个目录下面的子目录将自动被部署为应用

docBase只是指向了你某个应用的目录

http://blog.csdn.net/liuxuejin/article/details/9104055


14. spring的bean配置的几种方式

基于XML的配置、基于注解的配置和基于Java类的配置。

http://www.cnblogs.com/zhangwenjing/p/3546006.html


15. web.xml的配置

最终加载顺序：ServletContext -> listener -> filter -> servlet

http://www.cnblogs.com/xxoome/p/5954633.html


16. spring的监听器。

http://blog.csdn.net/xrt95050/article/details/6132179


17. zookeeper的实现机制，有缓存，如何存储注册服务的

ZooKeeper是Hadoop Ecosystem中非常重要的组件，它的主要功能是为分布式系统提供一致性协调(Coordination)服务

http://blog.csdn.net/xinguan1267/article/details/38422149


18. IO会阻塞吗？readLine是不是阻塞的

readLine()是一个阻塞函数，当没有数据读取时，就一直会阻塞在那，而不是返回null

readLine()只有在数据流发生异常或者另一端被close()掉时，才会返回null值。

http://blog.csdn.net/swingline/article/details/5357581


19. 用过spring的线程池还是java的线程池？

SpringFrameWork 的 ThreadPoolTaskExecutor 是辅助 JDK 的 ThreadPoolExecutor 的工具类，它将属性通过 JavaBeans 的命名规则提供出来，方便进行配置。

http://www.cnblogs.com/chkk/p/5386356.html


20. 字符串的格式化方法 （20，21这两个问题问的太低级了）

String类的format()方法

21. 时间的格式化方法

SimpleDateFormat的format()方法
22. 定时器用什么做的

http://lengchaotian.iteye.com/blog/1887439


23. 线程如何退出结束

1. 使用退出标志，使线程正常退出，也就是当run方法完成后线程终止。 while (!exit);
2. 使用stop方法强行终止线程。
3. 使用interrupt方法中断线程。


24. java有哪些锁？

乐观锁 悲观锁 synchronized 可重入锁 读写锁,

用过reentrantlock吗？reentrantlock与synmchronized的区别

1. 等待可中断 tryLock(long timeout, TimeUnit unit)。

2.公平锁与非公平锁(synchronized的是非公平锁）

3.绑定多个Condition

http://www.cnblogs.com/fanguangdexiaoyuer/p/5313653.html


25. ThreadLocal的使用场景

ThreadLocal就是用于线程间的数据隔离的。最适合的是按线程多实例（每个线程对应一个实例）的对象的访问


26. java的内存模型，垃圾回收机制


27. 为什么线程执行要调用start而不是直接run

（直接run，跟普通方法没什么区别，先调start，run才会作为一个线程方法运行）


28. qmq消息的实现机制(qmq是去哪儿网自己封装的消息队列)
29. 遍历hashmap的三种方式

方式1：通过遍历keySet()遍历HashMap的value
用时:61
方式2：通过遍历values()遍历HashMap的value
用时:7
方式3：通过entrySet().iterator()遍历HashMap的key和映射的value
用时:12

http://blog.csdn.net/fly_zxy/article/details/43015193

 

30. jvm的一些命令

jps jstat jmap jhat jstack jinfo

 

31. memcache和redis的区别

1  Redis不仅仅支持简单的k/v类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
2  Redis支持数据的备份，即master-slave模式的数据备份。
3  Redis支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用

http://blog.csdn.net/tonysz126/article/details/8280696/

32. MySQL的行级锁加在哪个位置

表级，直接锁定整张表，在你锁定期间，其它进程无法对该表进行写操作。如果你是写锁，则其它进程则读也不允许
行级,，仅对指定的记录进行加锁，这样其它进程还是可以对同一个表中的其它记录进行操作。
页级，表级锁速度快，但冲突多，行级冲突少，但速度慢。所以取了折衷的页级，一次锁定相邻的一组记录。

http://www.jb51.net/article/50047.htm


33. ConcurrentHashmap的锁是如何加的？是不是分段越多越好

http://www.cnblogs.com/my376908915/p/6759667.html


34. myisam和innodb的区别

（innodb是行级锁，myisam是表级锁）


35. mysql其他的性能优化方式

http://www.cnblogs.com/eric-gao/articles/5549801.html

 

36. linux系统日志在哪里看

/var/log/*     ，用Ls / cat查看

http://mushme.iteye.com/blog/1001478

 

37. 如何查看网络进程

38. 统计一个整数的二进制表示中bit为1的个数

while (n >0)
    {
        if((n &1) ==1) // 当前位是1
            ++c ; // 计数器加1
        n >>=1 ; // 移位
    }
http://www.cnblogs.com/graphics/archive/2010/06/21/1752421.html

 

39. jvm内存模型，java内存模型

http://www.cnblogs.com/my376908915/p/6753498.html

 

40. 如何把java内存的数据全部dump出来
jmap来获取内存镜像；MAT/ visualvm来进行内存镜像分析
http://f.dataguru.cn/thread-714170-1-1.html

41. 如何手动触发全量回收垃圾，如何立即触发垃圾回收

手动调用gc函数

 

42. hashmap如果只有一个写其他全读会出什么问题

如果value为空(表示这个key还没有插入)，那么很可能同时几个线程get的value都是null

 

43. git rebase git merge 区别

在当前的分支下rebase一下master分支，这样我这个分支的几个commits相对于master还是处于最顶端的，也就是说rebase主要用来
跟上游同步，同时把自己的修改顶到最上面。

用rebase有时候会需要多次fix冲突；用merge确实只需要解决一遍冲突，比较简单粗暴。

 

44. mongodb和hbase的区别

Redis定位在"快"，HBase定位于"大",mongodb定位在"灵活"。

mongodb可以当作简单场景下的但是性能高数倍的MySQL,定位是取代关系型数据库，想当一个主流数据库。因为他有非结构化、方便扩充字段、写性能优于mysql。万事万物有利有弊，mongodb的内存型
缓存内容，让其速度飞快，带来内存率多，掉电数据问题等

Redis基本只会用来做缓存，是一个小而美的数据库，主要用在key-value 的内存缓存，读写性能极佳，list，set，hash等几种简单结构使得使用也很简单。缓存与简单是其定位，分布式redis架构的
出现，让redis更加广泛的使用，稳坐缓存第一把交椅。

HBase用来做离线计算，定位非结构化大数据，可伸缩性好，并不是完全高可用，底层依靠hadoop提供的HDFS，使用时有一整套zookeeper，pig，hive的生态系统。

 

45. 如何解决并发问题

代码中的处理就是线程池，多线程，生产者消费者的应用了。

一、 web外网加速相关技术
1. 镜像站点:譬如一个美国网站的中国镜像可以使来自中国的用户直接从这个中国的镜像访问，从而加快了速度。这可以看作是一种全球范围的缓存。
2. DNS负载均衡:在DNS服务器中为同一个主机名配置多个IP地址，DNS服务器对每个查询将以DNS文件中主机记录的IP地址按顺序返回不同的解析结果，将客户端的访问引导到不同的机器上去，从而达到负载均衡的目的
3. CDN内容分发:尽可能避开互联网上有可能影响数据传输速度和稳定性的瓶颈和环节
二、 内网加速技术

0. HTML静态化 ：HTTP请求---Web服务器---Servlet--HTML--响应请求
1． 负载均衡（软件负载均衡：LVS、Nginx，第七层（应用层）的应用。硬件负载均衡：F5，第四层(传输层)上的应用）
2． Web缓存服务器（数据库缓存：memcached/redis来做缓存集群）
3． Web/应用服务器分布式文件系统（图片服务器分离 )
4． 分布式数据库。数据库主从分布： M-M-Slaves方式，2个主Mysql（写），多个Slaves（读-负载均衡，结合LVS）。数据库分割：从业务层面上进行分区，比如id，切分到不同的数据库集群去。）

http://www.ablanxue.com/prone_1020_1.html


46. volatile的用途

一个最常见的volatile的应用场景是boolean的共享状态标志位，或者单例模式的双重检查锁

http://www.cnblogs.com/my376908915/p/6757533.html


47. java线程池

http://www.cnblogs.com/my376908915/p/6761364.html


48. mysql的binlog

binlog日志用于记录所有更新了数据或者已经潜在更新了数据（例如，没有匹配任何行的一个DELETE）的所有语句。语句以“事件”的形式保存，它描述数据更改。

作用:因为有了数据更新的binlog，所以可以用于实时备份，与master/slave复制。

http://blog.csdn.net/wyzxg/article/details/7412777


49. 代理模式
50. mysql是如何实现事务的
MySQL的事务支持不是绑定在MySQL服务器本身，而是与存储引擎相关

1.MyISAM：不支持事务，用于只读程序提高性能

2.InnoDB：支持ACID事务、行级锁、并发

3.Berkeley DB：支持事务

在MySQL中，事务开始使用COMMIT或ROLLBACK语句开始工作和结束。

http://www.cnblogs.com/ymy124/p/3718439.html

 

51. 读写分离何时强制要读主库，读哪个从库是通过什么方式决定的，从库的同步mysql用的什么方式

开启了读写分离，在写数据的时候写入了主库，写完都需要刷新redis缓存，强制要读主库。在写操作的同步延迟窗口之内读，则读取主库，其他情况下读从库。

读写一致：http://www.it610.com/article/4872058.htm

 

主从架构本来就是一种高可用性解决方案，主从架构下的强一致性（银行业）：只需要在主机写入时，确认更新已经同步到备机之后，再返回写操作成功即可。主流数据库均支持这种完全的同步模式。（MySQL的Semi-sync功能）

目前互联网企业对于“高并发的写操作”问题比较典型的解决方案是分表分库+写缓存，增加针对写操作的缓存层，把写操作放到队列里，排队到数据库结点上异步执行。（在数据库层之上架构一个redis这样的分布式缓存）

 


52. mysql的存储引擎

MySQL5.5以后默认使用InnoDB存储引擎，其中InnoDB和BDB提供事务安全表，其它存储引擎都是非事务安全表。若要修改默认引擎，可以修改配置文件中的default-storage-engine。

http://www.cnblogs.com/gbyukg/archive/2011/11/09/2242271.html


53. mysql的默认隔离级别，其他隔离级别

Read Uncommitted（读取未提交内容）,脏读

Read Committed（读取提交内容）：大多数数据库系统的默认隔离级别，不可重复读

Repeatable Read（可重读）：MySQL的默认事务隔离级别，幻读

Serializable（可串行化）：最高的隔离级别

http://www.jb51.net/article/96179.htm


54. 将一个链表反转（用三个指针，但是每次只发转一个）

思路：从原链表的头部一个一个取节点并插入到新链表的头部

思路：每次都将原第一个结点之后的那个结点放在新的表头后面。

http://blog.csdn.net/hyqsong/article/details/49429859


55. spring Aop的实现原理，具体说说

IOC（反转控制）：对成员变量的赋值的控制权从代码中反转到配置文件中。
AOP：Aspect（切面） Oriented（面向） Programming（编程），面向切面编程。

动态代理和发射技术，已经基本实现了AOP的功能: http://www.jb51.net/article/81788.htm


56. 何时会内存泄漏，内存泄漏会抛哪些异常

首先，这些对象是可达的，即在有向图中，存在通路可以与其相连；
其次，这些对象是无用的，即不被程序使用，然而它却占用内存。

一个生存周期远大于另一个生存周期，而且生存周期大的对象有指向生存周期小的对象的引用，

而且生存周期小的对象不再有指向其他对象的引用，那好，既然大的有指向小的引用，那垃圾回收器对小的也无可奈何。

memory leak：最终会导致out of memory！


57. 是否用过Autowire注解

Spring 2.5 引入了 @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 通过 @Autowired的使用来消除 set ，get方法。

Spring 通过一个 BeanPostProcessor 对 @Autowired 进行解析，所以要让 @Autowired 起作用必须事先在 Spring 容器中声明 AutowiredAnnotationBeanPostProcessor Bean。

Spring 将直接采用 Java 反射机制对 Boss 中的 car 和 office 这两个私有成员变量进行自动注入。


58. spring的注入bean的方式

Spring中依赖注入有三种注入方式：

一、构造器注入；

二、设值注入（setter方式注入）；

三、Feild方式注入（注解方式注入）。

http://glzaction.iteye.com/blog/1299441


59. sql语句各种条件的执行顺序，如select， where， order by， group by

from--where--group by--having--select--order by---limit

http://www.cnblogs.com/huminxxl/p/3149097.html


60. select  xx from xx where xx and xx order by xx limit xx； 如何优化这个（看explain）
加limit，和不加走的索引不一样。 select  xx from （select  xx from xx where xx and xx order by xx ） yy limit xx；
在order by  limit 一起时 执行顺序不是按照：where ------order by ------ limit

而是：order by ----- limit -------where的顺序去执行，这样就会有一个问题，按照我们管用的思路，上面的查询肯定是会丢失数据的。

http://blog.csdn.net/wulantian/article/details/42679167

 

MySQL 对于千万级的大表要怎么优化？

第一优化你的sql和索引；MySQL性能优化的最佳20+条经验：http://coolshell.cn/articles/1846.html

第二加缓存，memcached,redis；

第三以上都做了后，还是慢，就做主从复制或主主复制，读写分离

第四如果以上都做了还是慢，不要想着去做切分，mysql自带分区表：http://www.cnblogs.com/zemliu/archive/2013/07/21/3203511.html

第五如果以上都做了，那就先做垂直拆分，其实就是根据你模块的耦合度，将一个大的系统分为多个小的系统，也就是分布式系统；列与列之间关联性不大的时候，垂直切分。

第六才是水平切分，针对数据量大的表，行很多的时候水平切分表，表名取模：http://www.cnblogs.com/sns007/p/5790838.html

 

61. 四则运算写代码

http://www.jb51.net/article/71487.htm

 

62. 统计100G的ip文件中出现ip次数最多的100个ip

1，分割IP：读原始文件，去掉IP中的点转化为一个long型变量，取模为0,1,2...99的 IP都分到一个（写）文件了。（内存不够，分而治之http://blog.csdn.net/yan5105105/article/details/50783262）

2，哈希表map<(ip, count>，将每个IP作为关键字映射为出现次数，这个哈希表建好之后也得先写入硬盘

3，建小顶堆，每次有数据输入的时候可以先与根节点比较。若小于或等于根节点，则舍弃；否则用新数值替换根节点数值。并进行最小堆的调整。http://blog.csdn.net/ephuizi/article/details/11790957

基于堆实现的优先级队列：PriorityQueue 解决 Top K 问题：https://my.oschina.net/leejun2005/blog/135085


63. zookeeper的事务，结点，服务提供方挂了如何告知消费方
64. 5台服务器如何选出leader

分布式-选举算法（bully算法）：

当任何一个进程发现协调者不响应请求时，他发起一次选举，选举过程如下：

a， P进程向所有编号比他大的进程发送一个election消息；

b， 如果无人响应，则P获胜，成为协调者

c，如果编号比他大的进程响应，则由响应者接管选举工作，P的工作完成。

http://blog.csdn.net/huangwei19892008/article/details/9004970

 

65. 适配器和代理模式的区别

代理模式（Proxy）：为其他对象提供一种代理以控制对这个对象的访问。用同一接口的子类的方法去 实现接口的方法

适配器模式（Adapter）：将一个类的接口转换成客户希望的另外一个接口，使得原本接口不兼容而不能一起工作的那些类可以一起工作。用不同接口的子类的方法去 实现接口的方法


66. 读写锁

对于读多写少的场景，一个读操作无须阻塞其它读操作，只需要保证读和写  或者 写与写  不同时发生即可。

读写锁的锁定规则如下：
获得读锁后，其它线程可获得读锁而不能获取写锁
获得写锁后，其它线程既不能获得读锁也不能获得写锁

http://www.cnblogs.com/my376908915/p/6758681.html


67. static加锁

synchronized是对类的当前实例进行加锁，防止其他线程同时访问该类的该实例的所有synchronized块，注意这里是“ 类的当前实例 ”，类的两个不同实例就没有这种约束了。

那么static synchronized恰好就是要控制类的所有实例的访问了，static synchronized是限制线程同时访问jvm中该类的所有实例同时访问对应的代码块。

http://blog.csdn.net/zbuger/article/details/50827762


68. 事务隔离级别

MySQL数据库为我们提供的四种隔离级别：

　　① Serializable (串行化)：可避免脏读、不可重复读、幻读的发生。

　　② Repeatable read (可重复读)：可避免脏读、不可重复读的发生。

　　③ Read committed (读已提交)：可避免脏读的发生。

　　④ Read uncommitted (读未提交)：最低级别，任何情况都无法保证。

http://www.cnblogs.com/fjdingsd/p/5273008.html


69. 门面模式，类图(外观模式)

为子系统中的一组接口提供一个一致的界面，此模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。

http://www.cnblogs.com/wangjq/archive/2012/07/10/2583672.html


70. mybatis如何映射表结构
在SqlMapConfig.xml中 
<typeAliases>
<typeAlias alias="game" type="实体类路径"/>
</typeAliases>
这就是把你的实体类写了个别名

最后：在写查询添加的时候：
<select id="gameDao" resultType="game">返回实体类对象
select * from tb_game
</select>
这样查出的结果就对应上数据了。

71. 二叉树遍历

前序、中序以及后序三种遍历方法。

递归实现：

void preOrder1(BinTree *root)     //递归前序遍历，按照“根结点-左孩子-右孩子”的顺序进行访问。
{
    if(root!=NULL)
    {
        cout<<root->data<<" ";
        preOrder1(root->lchild);
        preOrder1(root->rchild);
    }
}

非递归实现：
1)访问结点P，并将结点P入栈;
2)判断结点P的左孩子是否为空，若为空，则取栈顶结点并进行出栈操作，并将栈顶结点的右孩子置为当前的结点P，循环至1);若不为空，则将P的左孩子置为当前的结点P;
3)直到P为NULL并且栈为空，则遍历结束。
void preOrder2(BinTree *root)     //非递归前序遍历 
{
    stack<BinTree*> s;
    BinTree *p=root;
    while(p!=NULL||!s.empty())
    {
        while(p!=NULL)
        {
            cout<<p->data<<" ";
            s.push(p);
            p=p->lchild;
        }
        if(!s.empty())
        {
            p=s.top();
            s.pop();
            p=p->rchild;
        }
    }
}
72. 主从复制

整体上来说，复制有3个步骤：   

       (1)    master将改变记录到二进制日志(binary log)中（这些记录叫做二进制日志事件，binary log events）；
       (2)    slave将master的binary log events拷贝到它的中继日志(relay log)；

       (3)    slave重做中继日志中的事件，将改变反映它自己的数据。

在每个事务更新数据完成之前，master在二日志记录这些改变。MySQL将事务串行的写入二进制日志，即使事务中的语句都是交叉执行的。在事件写入二进制日志完成后，master通知存储引擎提交事务。
下一步就是slave将master的binary log拷贝到它自己的中继日志。首先，slave开始一个工作线程——I/O线程。I/O线程在master上打开一个普通的连接，然后开始binlog dump process。Binlog dump process从master的二进制日志中读取事件，如果已经跟上master，它会睡眠并等待master产生新的事件。I/O线程将这些事件写入中继日志。
SQL slave thread（SQL从线程）处理该过程的最后一步。SQL线程从中继日志读取事件，并重放其中的事件而更新slave的数据，使其与master中的数据一致。只要该线程与I/O线程保持一致，中继日志通常会位于OS的缓存中，所以中继日志的开销很小。

http://blog.csdn.net/hguisu/article/details/7325124


73. mysql引擎区别

MyISAM类型不支持事务处理等高级处理，而InnoDB类型支持。

MyISAM类型的表强调的是性能，其执行数度比InnoDB类型更快，但是不提供事务支持，而InnoDB提供事务支持已经外部键等高级数据库功能。

一般来说，MyISAM适合：
(1)做很多count 的计算；
(2)插入不频繁，查询非常频繁；
(3)没有事务。

InnoDB适合：
(1)可靠性要求比较高，或者要求事务；
(2)表更新和查询都相当的频繁，并且表锁定的机会比较大的情况指定数据引擎的创建

http://www.jb51.net/article/38004.htm


74. 静态内部类加载到了哪个区？

方法区

静态内部类，又叫类级内部类。

延迟加载单例模式：类装载的时候不去初始化对象，延迟初始化到getInstance方法时初始化。

http://www.javaweb1024.com/java/Javajichu/2015/03/25/454.html

 

75. class文件编译后加载到了哪

方法区

java编译期会加载.class文件：加载的类是你需要编译类所依赖的类，如你使用了System这个类，因为在jdk里的lib已经存在了，所以你不用显示的如加载，已经在classpath下面了。

如果你自己写的一个Class1，把它编译后，再写了个Class2。再编译Class2的时候就需要把Class1的编译文件加载到classpath中。

 

76. web的http请求如果整体响应时间变长导致处理的请求数变少，该如何处理？

瓶颈在哪里？

用队列，当处理不了那么多http请求时将请求放到队列中慢慢处理

 

77. 线程安全的单例模式

静态内部类实现单例模式：

public class MySingleton {  
    //内部类  
    private static class MySingletonHandler{  
        private static MySingleton instance = new MySingleton();  //静态内部类在类加载是被实例化
    }   
      
    private MySingleton(){}  
    public static MySingleton getInstance() {   
        return MySingletonHandler.instance;  
    }  
} 
枚举数据类型实现单例模式：EnumFactory.singletonFactory.getInstance()

public enum EnumFactory{    
    singletonFactory;  
    private MySingleton instance;  
    private EnumFactory(){//枚举类的构造方法在类加载是被实例化  （在使用枚举时，构造方法会被自动调用）
        instance = new MySingleton();  
    }  
       
    public MySingleton getInstance(){  
        return instance;  
    }  
} 
http://blog.csdn.net/cselmu9/article/details/51366946

78. 快速排序性能考虑

该方法的基本思想（分治法）是：

1．先从数列中取出一个数作为基准数。

2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。

3．再对左右区间重复第二步，直到各区间只有一个数。

http://blog.csdn.net/morewindows/article/details/6684558

http://www.cnblogs.com/luchen927/archive/2012/02/29/2368070.html

 

79. volatile关键字用法

volatile的应用场景是boolean的共享状态标志位，或者单例模式的双重检查锁

http://www.cnblogs.com/my376908915/p/6757533.html

 

80. 求表的size，或做数据统计可用什么存储引擎

MyISAM

一般来说，MyISAM适合：
(1)做很多count 的计算；
(2)插入不频繁，查询非常频繁；
(3)没有事务。

InnoDB适合：
(1)可靠性要求比较高，或者要求事务；
(2)表更新和查询都相当的频繁，并且表锁定的机会比较大的情况指定数据引擎的创建

http://www.jb51.net/article/38004.htm

 

81. 读多写少可用什么引擎
MyISAM

82. 假如要统计多个表应该用什么引擎
MyISAM

83. concurrenhashmap求size是如何加锁的，如果刚求完一段后这段发生了变化该如何处理

Put等操作都是在单个Segment中进行的，但是ConcurrentHashMap有一些操作是在多个Segment中进行，比如size操作，ConcurrentHashMap的size操作也采用了一种比较巧的方式，来尽量避免
对所有的Segment都加锁。 　　
前面我们提到了一个Segment中的有一个modCount变量，代表的是对Segment中元素的数量造成影响的操作的次数，这个值只增不减，
size操作就是遍历了两次Segment，每次记录Segment的modCount值，然后将两次的modCount进行比较，如果相同，则表示期间没有发生过写入操作，就将原先遍历的结果返回，
如果不相同，则把这个过程再重复做一次，
如果再不相同，则就需要将所有的Segment都锁住，然后一个一个遍历了。

 

ConcurrentHashMap加锁的时候根据散列值锁住了散列值锁对应的那段，因此提高了并发性能。

ConcurrentHashMap也增加了对常用复合操作的支持，比如"若没有则添加"：putIfAbsent()，替换：replace()。这2个操作都是原子操作。

http://www.cnblogs.com/my376908915/p/6759667.html

 

84. 1000个苹果，请你将它放进10个箱子，如何放，使得顾客不管要多少个苹果，你总可以从10箱子里拿出若干个箱子，其苹果之和就是顾客要的苹果数？

箱子中依次放1，2，4，8，16，32，64，128，256，489个苹果
算法：其实就是数的二进制表示而已，你将所有的数转换成2进制后，就会发现，所有的数都是由不同位上的1组成
二进制表：1，10，100，1000，10000，100000。。。。。。。

 

85. 可重入的读写锁，可重入是如何实现的？

可重入锁又叫做递归锁。
reentrant 锁意味着什么呢？简单来说，它有一个与锁相关的获取计数器，如果拥有锁的某个线程再次得到锁，那么获取计数器就加1，然后锁需要被释放两次才能获得真正释放。
这相当于是模仿了synchronized中又可以嵌套一个synchronized这样的场景
http://blog.csdn.net/johnking123/article/details/50043961

 

86. 是否用过NIO
Buffer和Channel是标准NIO中的核心对象（网络NIO中还有个Selector核心对象），几乎每一个IO操作中都会用到它们。
http://www.cnblogs.com/my376908915/p/6767922.html

 

87. java的concurrent包用过没

java.util.concurrent包，

AtomicI原子化，基于原子操作的循环CAS算法。

Collections容器，ConcurrentLinkedQueue（非阻塞队列---基于原子引用的循环CAS），ConcurrentHashMap

Locks锁，基于非阻塞队列的循环CAS + JNI的unsafe.park(false, 0L)阻塞线程

Executor线程池。

http://www.cnblogs.com/my376908915/p/6758278.html

 

88. sting s=new string("abc")分别在堆栈上新建了哪些对象

栈：sting s
堆：new string("abc")
字符串池（方法区）："abc"

JVM中存在着一个字符串池，使用引号 创建文本的方式的String对象都会放入字符串池。可以提高效率。
String a="abc"; String b="abc";//这两句在字符串池 只创建一个实例对象。 
String a="ab"+"cd";//这一句在字符串池 创建三个实例对象。
new方式新建String对象则不会放入字符串池，放入堆。
参考： http://blog.csdn.net/lubiaopan/article/details/4776000/
89. java虚拟机的区域分配，各区分别存什么

http://www.cnblogs.com/my376908915/p/6753498.html


90. 分布式事务（JTA）
普通的jdbc事务只能针对单个connection，要实现多个数据库事务的操作，jta可以满足要求。

jta应用程序要调用 javax.transaction.UserTransaction 接口中的方法。

“用 JTA 界定事务，那么就需要有一个实现 javax.sql.XADataSource 、 javax.sql.XAConnection 和 javax.sql.XAResource 接口的 JDBC 驱动程序。
一个实现了这些接口的驱动程序将可以参与 JTA 事务。一个 XADataSource 对象就是一个 XAConnection 对象的工厂。 XAConnection s 是参与 JTA 事务的 JDBC 连接。” 
要使用JTA事务，必须使用XADataSource来产生数据库连接，产生的连接为一个XA连接。 
XA连接（javax.sql.XAConnection）和非XA（java.sql.Connection）连接的区别在于：XA可以参与JTA的事务，而且不支持自动提交。
Innodb存储引擎支持XA事务，通过XA事务可以支持分布式事务的实现。
http://blog.csdn.net/mchdba/article/details/13076803
 

91. threadlocal使用时注意的问题

（ThreadLocal和Synchonized都用于解决多线程并发访问。但是ThreadLocal与synchronized有本质的区别。

synchronized是利用锁的机制，使变量或代码块在某一时该只能被一个线程访问。

而ThreadLocal为每一个线程都提供了变量的副本，使得每个线程在某一时间访问到的并不是同一个对象，这样就隔离了多个线程对数据的数据共享。

而Synchronized却正好相反，它用于在多个线程间通信时能够获得数据共享）

http://www.cnblogs.com/my376908915/p/6763210.html

 

92. java有哪些容器

(各种集合，tomcat也是一种容器)

 

93. 二分查找算法

用二分查找在已排序的数组中查看该数组是否含有一个特定的值。速度是非常快速的。

迭代方式：

public int BinarySearchIteration(int[] array, int key)  
{  
    int begin = 0;  
    int end = array.Length - 1;  
    while (begin <= end)  
    {  
        int mid = begin + (end - begin) / 2;  
        if (array[mid] > key)  
        {  
            end = mid - 1;  
        }  
        else if (array[mid] < key)  
        {  
            begin = mid + 1;  
        }  
        else  
        {  
            return mid;  
        }  
    }  
    return -1;  
} 
http://blog.csdn.net/beiyeqingteng/article/details/5736004


94. myisam的优点，和innodb的区别

MyISAM

一般来说，MyISAM适合：
(1)做很多count 的计算；
(2)插入不频繁，查询非常频繁；
(3)没有事务。

InnoDB适合：
(1)可靠性要求比较高，或者要求事务；
(2)表更新和查询都相当的频繁，并且表锁定的机会比较大的情况指定数据引擎的创建

http://www.jb51.net/article/38004.htm


95. redis能存哪些类型

redis常用五种数据类型:string,hash,list,set,zset(sorted set).

http://blog.csdn.net/qq_19943157/article/details/50495925


96. http协议格式，get和post的区别

http协议格式: http://www.cnblogs.com/li0803/archive/2008/11/03/1324746.html

get和post的区别:http://www.cnblogs.com/hyddd/archive/2009/03/31/1426026.html


97. 可重入锁中对应的wait和notify

条件锁，http://www.cnblogs.com/my376908915/p/6758681.html

Conditon中的await()对应Object的wait()；
Condition中的signal()对应Object的notify()；
Condition中的signalAll()对应Object的notifyAll()。


98. redis能把内存空间交换进磁盘中吗

Redis利用swap文件将数据从内存转移到磁盘。

http://blog.csdn.net/nvnh7553/article/details/50107971

如果你打开虚拟内存功能，当内存用尽时, Redis就会把那些不经常使用的数据存储到磁盘。
如果Redis里的虚拟内存被禁了，他就会用上操作系统的虚拟内存(交换内存)，同时性能急剧下降。
你可以配置maxmemory参数，来避免Redis默认再分配更多的内存。

http://www.dewen.net.cn/q/242


99. java线程池中基于缓存和基于定长的两种线程池，当请求太多时分别是如何处理的？

Java自带的几种线程池：

1、newCachedThreadPool 创建一个可缓存的线程池。

这种类型的线程池特点是：

a).工作线程的创建数量几乎没有限制(其实也有限制的,数目为Interger. MAX_VALUE), 这样可灵活的往线程池中添加线程。

b).如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。终止后，如果你又提交了新的任务，则线程池重新创建一个工作线程。

2、newFixedThreadPool 创建一个指定工作线程数量的线程池。

每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。

3、newSingleThreadExecutor 创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，如果这个线程异常结束，会有另一个取代它，保证顺序执行(我觉得这点是它的特色)。

单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的 。

4、newScheduleThreadPool 创建一个定长的线程池，而且支持定时的以及周期性的任务执行，类似于Timer。


100. synchronized加在方法上用的什么锁
方法对应的实例对象。

 

101. 可重入锁中的lock和trylock的区别

ReentrantLock获取锁定与三种方式：
    a)  lock(), 如果获取了锁立即返回，如果别的线程持有锁，当前线程则一直处于休眠状态，直到获取锁

    b) tryLock(), 如果获取了锁立即返回true，如果别的线程正持有锁，立即返回false；

    c) tryLock(long timeout,TimeUnit unit)，   如果获取了锁定立即返回true，如果别的线程正持有锁，会等待参数给定的时间，在等待的过程中，如果获取了锁定，就返回true，如果等待超时，返回false；

    d) lockInterruptibly:如果获取了锁定立即返回，如果没有获取锁定，当前线程处于休眠状态，直到获得锁定，或者当前线程被别的线程中断

http://www.cnblogs.com/my376908915/p/6758681.html


102. innodb对一行数据的读会加锁吗？

对于insert、update、delete，InnoDB会自动给涉及的数据加排他锁（X）；

对于一般的Select语句，InnoDB不会加任何锁，事务可以通过以下语句给显示加共享锁或排他锁。

共享锁：select * from table_name where .....lock in share mode

排他锁：select * from table_name where .....for update

http://www.2cto.com/database/201508/429967.html


103. redis做缓存是分布式存的？不同的服务器上存的数据是否重复？guava cache呢？是否重复？不同的机器存的数据不同

由于redis是单点，项目中需要使用，必须自己实现分布式。

分布式实现：通过key做一致性哈希，实现key对应redis结点的分布。

http://www.open-open.com/lib/view/open1384603154712.html

Mysql是适合海量数据存储的，然后通过Memcached将一些常用的数据进行缓存，加快访问速度。

当数据量不断的增大的时候，进行切表，拆表的，Memcached也需要不断的跟着扩容，Memcached和Mysql的数据一致性的问题，Memcached数据命中率低或者Down机，大量的访问就会穿透到数据库，这时候Mysql可能会无法支撑。

          Redis使用最佳方式是全部数据in-memory。

          Redis更多场景是作为Memcached的替代者来使用。

         当需要除key/value之外的更多数据类型支持时，使用Redis更合适。

         当存储的数据不能被剔除时，使用Redis更合适。

http://blog.csdn.net/bemavery/article/details/47061663


104. 用awk统计一个ip文件中top10

cat File.log | awk -F ',' '{print $8}' | sort | uniq -c | sort -k1nr | head -10

 

Linux awk是一个强大的文本分析工具，相对于grep的查找，sed的编辑，awk在其对数据分析并生成报告时，显得尤为强大。http://www.cnblogs.com/ggjucheng/archive/2013/01/13/2858470.html

简单来说awk就是把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分再进行各种分析处理。http://man.linuxde.net/awk

https://yq.aliyun.com/ziliao/73055


105. 对表做统计时可直接看schema info信息，即查看表的系统信息

MySQL中有一个名为 information_schema 的数据库，在该库中有一个 TABLES 表，这个表主要字段分别是：

TABLE_SCHEMA : 数据库名

TABLE_NAME：表名

ENGINE：所使用的存储引擎

TABLES_ROWS：记录数

DATA_LENGTH：数据大小

INDEX_LENGTH：索引大小

 

use information_schema;
select table_name,table_rows from tables
where TABLE_SCHEMA = '数据库名'
order by table_rows desc;
查询出来的是每张表的行数

http://help.wopus.org/mysql-manage/607.html


106. mysql目前用的版本

据说5.0.x的版本比较稳定，兼容比较好，我现在安装的5.1.63

Mysql查看版本号的五种方式介绍：http://www.jb51.net/article/36370.htm


107. 公司经验丰富的人给了什么帮助？(一般boss面会问这些)

具体技术的点拨，思考问题的方式，解决问题的方式。


108. 自己相对于一样的应届生有什么优势

经验丰富


109. 自己的好的总结习惯，给自己今后的工作带了什么帮助，举例为证

技术总结分享，文档归档。

 

110. 原子类，线程安全的对象，异常的处理方式
JUC出现之后，这些原子操作 基于JNI提供了新的实现，

比如AtomicInteger,AtomicLong,AtomicBoolean,AtomicReference,AtomicIntegerArray/AtomicLongArray/AtomicReferenceArray；

这些操作中提供一些原子化操作，比如incrementAndGet（相当于i++），compareAndSet（安全赋值）等，直接读源代码也很容易懂。

www.cnblogs.com/my376908915/p/6758415.html

 

111. 4亿个int数，如何找出重复的数

大数据处理算法一：Bitmap算法

核心思想即通过将一个数作为下标（index）来索引一个bit表示一个数是否存在，排序时的时间复杂度为O(N)，需要的额外空间的复杂度O(N/8)，支持整个int范围（正负数都支持）的算法

http://www.open-open.com/lib/view/open1430902831226.html


112. 4亿个url，找出其中重复的（考虑内存不够，通过hash算法，将url分配到1000个文件中，不同的文件间肯定就不会重复了，再分别找出重复的）

hash算法：   H（url）出一个整数后，取1000的余数，分割到1000个文件中，余数即为文件名。

读入每个小文件，进内存HashMap（url，Counts）。

遍历EntrySets，即Counts 〉1的url

hash算法原理详解 ：http://blog.csdn.net/tanggao1314/article/details/51457585


有1万个数组，每个数组有1000个整数，每个数组都是降序的，从中找出最大的10个数。

每个数组取出前10个，堆排序，或优先队列。

 

113. LinkedHashmap的底层实现

LinkedHashMap实现与HashMap的不同之处在于，LinkedHashMap维护着一个运行于所有条目的双向链接列表。此链接列表定义了迭代顺序，该迭代顺序可以是插入顺序或者是访问顺序。

对于LinkedHashMap而言，它继承与HashMap、底层使用哈希表与双向链表来保存所有元素。其基本操作与父类HashMap相似，它通过重写父类相关的方法，来实现自己的链接列表特性。

Entry除了保存当前对象的引用外，还保存了其上一个元素before和下一个元素after的引用，从而在哈希表的基础上又构成了双向链接列表。

http://zhangshixi.iteye.com/blog/673789


114. 类序列化时类的版本号的用途，如果没有指定一个版本号，系统是怎么处理的？如果加了字段会怎么样？

如果没有显式声明序列号，那么在程序编译时会自己生成这个版本序列号。

如果加了字段，更改了实体类的时候又会重新生成一个序列号。

运行程序，就会报错：InvalidClassException

http://blog.sina.com.cn/s/blog_7f73e06d0100u52c.html


115. Override和Overload的区别，分别用在什么场景

方法重载（overload），参数不同。

方法覆盖（override），方法内容不同。用在子类

http://blog.csdn.net/zhouhong1026/article/details/8232350


116. java的反射是如何实现的

反射机制其实就是指程序在运行的时候能够获取自身的信息。

如果知道一个类的名称/或者它的一个实例对象， 就能把这个类的所有方法和变量的信息(方法名，变量名，方法，修饰符，类型，方法参数等等所有信息)找出来。

如果明确知道这个类里的某个方法名+参数个数 类型，还能通过传递参数来运行那个类里的那个方法，这就是反射。

尽管Java不是一种动态语言，但它却有一个非常突出的动态机制：Reflection。它使我们可以于运行时加载、探知、使用编译期间完全未知的 classes。

换句话说，Java程序可以加载一个运行时才得知名称的class，获悉其完整构造（但不包括methods定义），并生成其对象实体、 或对其fields设值、或唤起其methods。既一种“看透class”的能力。

http://www.tuicool.com/articles/zuIN7r

http://www.cnblogs.com/my376908915/p/6752707.html

http://blog.csdn.net/liujiahan629629/article/details/18013523

 

117.HashMap  HashTable的区别

http://www.importnew.com/7010.html

118.Map集合的四种遍历方式

http://www.cnblogs.com/blest-future/p/4628871.html

119.HashMap如何实现

Hash算法-〉数组，

+ 处理冲突-〉链表法

120.多线程循环删除List数组容器里面元素，会ConcurrentModificationException

把List数组转换成Iterator进行迭代删除，一点问题都没有：listA.iterator().remove(); Iterator进行循环操作，然后删除，是很安全的。

121.线程间共享数据的方式

http://www.cnblogs.com/my376908915/p/6756895.html

122.Spring MVC页面渲染的几种方式

http://blog.csdn.net/suifeng3051/article/details/51648360

123.Ioc和AOP的理解和源码

AOP基于什么设计模式实现的？具体说下cglib代理和jdk代理的区别，他们是怎么实现动态代理的，核心类和核心方法是什么

http://www.cnblogs.com/my376908915/p/6782604.html

124.spring bean的几种状态

用的最多的还是singleton单态，prototype原型多态。

125.spring的缓存优化是怎么做的？如何清缓存，缓存哪里用到了，用他做什么？

http://blog.csdn.net/a494303877/article/details/53780597

http://blog.csdn.net/dlf123321/article/details/51382666

126.使用spring初始化需要加载的东西，

bean.dispatcherServlet,加载Html,spring的配置文件

127.如果redis缓存宕掉了怎么办

https://baijiahao.baidu.com/po/feed/share?wfr=spider&for=pc&context=%7B%22sourceFrom%22%3A%22bjh%22%2C%22nid%22%3A%22news_3562062748706618586%22%7D

128.java中异常机制


Throwable是Error和Exception的父类，

Error一般是指JVM抛出的错误，不需要捕获，Exception是程序错误，需要捕获处理；
129.有10亿条文本，找出前一万条重复率高的

先Hash算法分割到1000个文件中去；HashMap（文本，counts）；堆排序。

BitMap算法：使用hash计算并存储次数，然后遍历一次找出top10；







130.对一千万条数据排序，你认为最好的方式是什么

分块查找，堆排序。

BitMap算法。是否有重复。申请长度为一千万位的位向量bit[10000000]，所有位设置为0，顺序读取待排序文件，每读入一个数i，便将bit[i]置为1。当所有数据读入完成，便对bit做从头到尾的遍
历，如果bit[i]=1，则输出i到文件，当遍历完成，重复的数据被输出。

131.10w行数据，每行一个单词，统计出现次数出现最多的前100个。

（1）可以使用小根堆；

（2）在linux中实现：cat words.txt | sort | uniq -c | sort -k1,1nr | head -10

uniq -c: 
 显示唯一的行，并在每行 行首 加上本行在文件中出现的次数

sort -k1,1nr:  按照第一个字段，数值排序，且为逆序

132.一个文本文件，给你一个单词，判断单词是否出现。

grep -wq "fail" 123.txt && echo "no"||echo "yes"

-w 精确匹配
