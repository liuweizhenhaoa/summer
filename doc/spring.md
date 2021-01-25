请简述Spring架构中IOC(控制反转)的实现原理?
.简单说就是将原先需要new出来的对象,我先把它实例化,同时把它放到一个容器里,这样后面需要这个对象的时候,直接通过注入的方式拿到,为什么说是注入,其实应该说是从容器里面拿,只不过在spring里,用的更多的是注入, spring中使用ioc的模式,首先,在spring容器中有两个map集合,一个是用来存放bean的配置信息,另一个是用来存放bean的实例
理解spring么，它的AOP实现是基于什么原理，bean的初始化过程是那些（涉及具体的源代码）,在bean factory初始化前 ，运行中，初始化后想做些事情。该怎么做？
然后问了我springmvc和mybatis的工作原理，有没有看过底层源码？
什么是IOC和DI?DI是如何实现的
你是如何理解“切面关注”这个概念的
Spring框架中Bean的生存周期
1.Spring IoC容器根据Bean的定义实例化该Bean；
2.Spring IoC容器对Bean的依赖进行注入；
3.如果Bean实现了BeanNameAware接口，则将该Bean的id传给setBeanName方法；
4.如果Bean实现了BeanFactoryAware接口，则将BeanFactory对象传给setBeanFactory方法；
5.如果Bean实现了BeanPostProcessor接口，则调用其postProcessBeforeInitialization方法；
6.如果Bean实现了InitializingBean接口，则调用其afterPropertySet方法；
7.如果有和Bean关联的BeanPostProcessors对象，则这些对象的postProcessAfterInitialization方法被调用；
8.当销毁Bean实例时，如果Bean实现了DisposableBean接口，则调用其destroy方法。

Spring中的自动装配有哪些限制？
如果使用了构造器注入或者setter注入，那么将覆盖自动装配的依赖关系。
基本数据类型的值、字符串字面量、类字面量无法使用自动装配来注入。
优先考虑使用显式的装配来进行更精确的依赖注入而不是使用自动装配

服务器性能优化的8种常用方法
1.使用内存数据库
       内存数据库，其实就是将数据放在内存中直接操作的数据库。相对于磁盘，内存的数据读写速度要高出几个数量级，将数据保存在内存中相比从磁盘上访问能够极大地提高应用的性能。内存数据库抛弃了磁盘数据管理的传统方式，基于全部数据都在内存中重新设计了体系结构，并且在数据缓存、快速算法、并行操作方面也进行了相应的改进，所以数据处理速度比传统数据库的数据处理速度要快很多。
      但是安全性的问题可以说是内存数据库最大的硬伤。因为内存本身有掉电丢失的天然缺陷，因此我们在使用内存数据库的时候，通常需要，提前对内存上的数据采取一些保护机制，比如备份，记录日志，热备或集群，与磁盘数据库同步等方式。对于一些重要性不高但是又想要快速响应用户请求的部分数据可以考虑内存数据库来存储，同时可以定期把数据固化到磁盘。
2.使用RDD
     在大数据云计算相关领域的一些应用中，Spark可以用来加快数据处理速度。Spark的核心是RDD，RDD最早来源与Berkeley实验室的一篇论文《Resilient Distributed Datasets: A Fault-Tolerant Abstraction for In-Memory Cluster Computing》。现有的数据流系统对两种应用的处理并不高效：一是迭代式算法，这在图应用和机器学习领域很常见；二是交互式数据挖掘工具。这两种情况下，将数据保存在内存中能够极大地提高性能。
3.增加缓存
      很多web应用是有大量的静态内容，这些静态内容主要都是一些小文件，并且会被频繁的读，采用Apache以及nginx作为web服务器。在web访问量不大的时候，这两个http服务器可以说是非常的迅速和高效，如果负载量很大的时候，我们可以采用在前端搭建cache服务器，将服务器中的静态资源文件缓存到操作系统内存中直接进行读操作，因为直接从内存读取数据的速度要远大于从硬盘读取。这个其实也是增加内存的成本来降低访问磁盘带来的时间消耗。
4.使用SSD
       除了对内存方面的优化，还可以对磁盘这边进行优化。跟传统机械硬盘相比，固态硬盘具有快速读写、质量轻、能耗低以及体积小等特点。但是ssd的价格相比传统机械硬盘要贵，有条件的可以使用ssd来代替机械硬盘。
5.优化数据库
      大部分的服务器请求最终都是要落到数据库中，随着数据量的增加，数据库的访问速度也会越来越慢。想要提升请求处理速度，必须要对原来的单表进行动刀了。目前主流的Linux服务器使用的数据库要属mysql了，如果我们使用mysql存储的数据单个表的记录达到千万级别的话，查询速度会很慢的。根据业务上合适的规则对数据库进行分区分表，可以有效提高数据库的访问速度，提升服务器的整体性能。另外对于业务上查询请求，在建表的时候可以根据相关需求设置索引等，以提高查询速度。
6.选择合适的IO模型
IO模型又分为：
       (1).阻塞I/O模型：数据没到达之前，I/O一直阻塞，如果数据到达，则会返回。典型的是recvfrom，一般的默认都是阻塞的。
       (2).非阻塞的I/O模型：和阻塞相反，只要不能得到结果的时候，I/O立刻返回。不会阻塞当前线程。
       IO复用模型：也就是自己要学习的部分。多路复用的意思是，将多路信号合并到一路上进行处理，类似多个管道汇集到一个管道，与之相反的是多路分解。
       IO复用模型主要是select，poll，epoll；对一个IO端口，两次调用，两次返回，比阻塞IO并没有什么优越性；关键是能实现同时对多个IO端口进行监听；函数也会使进程阻塞，但是和阻塞I/O所不同的的，这两个函数可以同时阻塞多个I/O操作。而且可以同时对多个读操作，多个写操作的I/O函数进行检测，直到有数据可读或可写时，才真正调用I/O操作函数。
      信号驱动：首先开启套接口信号驱动I/O功能,并通过系统调用sigaction安装一个信号处理函数。当数据报准备好被读时，就为该进程生成一个SIGIO信号。随即可以在信号处理程序中调用recvfrom来读数据报，井通知主循环数据已准备好被处理中。也可以通知主循环，让它来读数据报。
       异步的IO模型：告知内核启动某个操作，并让内核在整个操作完成后(包括将数据从内核拷贝到用户自己的缓冲区)通知我们。这里并不是说一定要用某个模型，epoll也并不是在所有情况下都比select性能要好的，在选择的时候还是要结合业务需求来。
7.使用多核处理策略
      现在运行服务器的主流机器配置都是多核CPU的，我们在设计服务器的时候可以利用多核心的特点，采用多进程或者多线程的框架。关于选择多线程还是多进程可以根据实际的需求，结合各自的优缺点进行选择。对于多线程的使用，特别是使用线程池的时候可以通过测试不同线程池服务器的性能来设置合适的线程池。
8.分布式部署程序
     当单机服务器已经找不到合适的优化点时，我们可以通过分布式部署来提高服务器的响应能力。优秀的服务器开发都会为自己的服务器的扩容，容灾提出一些解决方案。个人觉得服务器设计的时候简单点比较好，这样后期扩容的时候会很方便。
请详细描述springmvc处理请求全流程？
spring 一个bean装配的过程？

说一下Spring的IOC和AOP，底层什么原理
IOC控制反转：类在使用某个别的属性的时候都使用当前使用者自己去创建，IOC

动态代理有几种，Jdk与Cglib区别
一、原理区别：
java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP 
2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP 
3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换
如何强制使用CGLIB实现AOP？
 （1）添加CGLIB库，SPRING_HOME/cglib/*.jar
 （2）在spring配置文件中加入<aop:aspectj-autoproxy proxy-target-class="true"/>

JDK动态代理和CGLIB字节码生成的区别？
 （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
   因为是继承，所以该类或方法最好不要声明成final 
谈spring的过滤 filter 
过滤器和拦截器主要区别如下：
1.二者适用范围不同。Filter是Servlet规范规定的，只能用于Web程序中，而拦截器既可以用于Web程序，也可以用于Application、Swing程序中。
2.规范不同。Filter是在Servlet规范定义的，是Servlet容器支持的，而拦截器是在Spring容器内的，是Spring框架支持的。
3.使用的资源不同。同其他代码块一样，拦截器也是一个Spring的组件，归Spring管理，配置在Spring文件中，因此能使用Spring里的任何资源、对象(各种bean)，而Filter不行。
4.深度不同。Filter只在Servlet前后起作用，而拦截器能够深入到方法前后、异常跑出前后等，拦截器的使用有更大的弹性。
大致讲了下登录过滤的实现。。。
Spring的启动、加载过程？
Spring中什么是Lazy-Load？
Spring中Bean有哪些Scope？
Spring如何解决循环依赖？
Spring中如果一个注入一个对象，但是这个对象有多个实例，怎么处理？
SpringMVC常用的注解，RequestBody，ResponseBody ，RequestParam，PathVariables，RestController，ControllerAdvice， WebInitParam，InitBinder
SpringMVC如何做类型转换？
SpringMVC如何做参数校验？
Spring Boot常用的注解
说说Tomcat的各种配置，如何配置docBase
Tomcat中，什么是Host、Engine、Server、Service、Realm、Connector、Context、Wrapper、Valve？
Tomcat中，context.xml什么作用？如何配置？
Tomcat中，jsp是如何热加载的？类是否可以热加载？
Tomcat中，什么是Catalina_BASE？如何设置？
**servlet的forward、redirect 有什么区别？**
`为实现程序的模块化，就需要保证在不同的Servlet之间可以相互跳转，而Servlet中主要有两种实现跳转的方式：FORWARD方式与redirect方式。
 forward是服务器内部的重定向，服务器直接访问目标地址的URL，把那个URL的响应内容读取出来，而客户端并不知道，因此在客户端浏览器的地址栏里不会
 显示跳转后的地址，还是原来的地址。由于在整个定向的过程中用的是同一个Request，因此FORWARD会将Request的信息带到被定向的ＪＳＰ或Servlet中使用。
 redirect则是客户端的重定向，是完全的跳转，即客户端浏览器会获取跳转后的地址，然后重新发送请求，因此浏览器中会显示跳转后的地址。同时，由于这种方
 式比FORWARD方式多了一次网络请求，因此其效率低于FORWARD方式，需要注意到的是，客户端的重定向可以通过设置特定的HTTP 头或写JavaScript脚本
 来实现。
 鉴于以上的区别，一般当FORWARD方式可以满足需求时，尽可能的使用FORWARD方式。但在有些情况下，例如，需要跳转到一个其他服务器上的资源时，则必须使用redirect 方式。`
Web容器中过滤器、拦截器、监听器什么区别？
Get、Post请求什么区别？
什么是ajax？如何实现？
什么是JSP，如何工作的？
如何配置数据库连接池？
什么是Locale、ResourceBoundle
Spring Boot 中application.yml与bootstrap.yml的区别
https://blog.csdn.net/jeikerxiao/article/details/78914132
Spring如何解决循环依赖？
Spring中如何让A和B两个bean按顺序加载？
1、spring都有哪些机制啊AOP底层如何实现的啊IOC呢?
1.IOC、AOP
2.IOC的实现方式就是就是反射和CGLIB。
2、cgLib知道吗?他和jdk动态代理什么区别?手写一个jdk动态代理呗?
cglib 代理的是普通的类，而jdk代理的是接口。jdk 只能代理接口，而cglib只能代理普通类，可以在spring配置中强制使用cglib代理。
<aop:aspectj-autoproxy proxy-target-class="true"/>）。
1、理解spring么，它的AOP实现是基于什么原理，bean的初始化过程是那些（涉及具体的源代码）,在bean factory初始化前 ，运行中，初始化后想做些事情。该怎么做？
bean的创建
IOC就是一个bean的集合，bean的创建也由他负责,那么什么时候创建，怎么创建bean？
需要考虑bean的scope，一种singleton,一种prototype;还有是延迟加载属性
singleton就是只创建一次，会放到一个map中，以便下次使用；prototype就是每次都创建一个新的实例
bean的属性注入
也一样，什么时候注入，怎么注入
在创建bean之后，先找到需要注入的属性，也就是@Autowired注解的方法，或者属性
方法就需要调用，属性就需要修改值

