# Quartz 与 SpringBoot 的最佳实践

我会使用`Mysql`来存储`Quartz`的任务信息, 两种实现方法：

1. 由`Quartz`的`JDBCJobStore`来调度任务，持久化任务信息
2. 由`Quartz`的`RAMJobStore`来调度任务，手动`orm`持久化任务信息

## 1. JDBCJobStore

既然是`JDBCJobStore`那肯定需要先需要配置数据源

两种方法:

1. 由`Spring`维护数据源，使用`Quartz`的`JobStoreCMT`来管理调度任务(`SpringBoot`提供了继承`JobStoreCMT`的
   `LocalDataSourceJobStore`)
2. 由`Quartz`自己维护数据源，使用`Quartz`的`JobStoreTX`来管理调度任务

这里我选择第一种方法.在`application.yml`中配置好数据源后，我们再来配置`spring.quartz`
> 如果需要自定义初始化`schema`，可以在`application.yml`中配置`spring.quartz.jdbc.schema`属性

```yml
spring:
  quartz:
    job-store-type: jdbc
    jdbc:
      initialize-schema: always
      schema: classpath:schema.sql
    properties:
      org:
        quartz:
          jobStore:
            tablePrefix: QUARTZ_
          threadPool:
            threadCount: 5
```

代码环节直接注入`Spring`维护的`Scheduler`，增删改查任务信息即可。如果需要为任务添加异常处理可实现`JobListener`接口并添加到
`Scheduler`中。

具体实现在本项目中有例子(项目结构在调整中，因为看到了一篇项目规范的文章，于是在本项目中实践)

> 话是这么说,但是使用起来非常别扭，而且当`Job`被`Spring AOP`代理`Quartz`会找不到`Job`实例
>
> 不能使用`AOP`就有点麻烦，例如：没有声明式事务、异常捕获、声明式缓存、声明式异步执行等等
>
> 所以更加推荐第二种方法

## 2. RAMJobStore

既然是手动维护任务信息，那第一件事肯定也是配置数据源(笑，这随便抓一个人都会就不多说了)

直接来到任务持久化吧！我定义了一个`JobDetails`，存储任务的基本信息和执行需要调用的方法和参数，并使用`jobName`和`jobGroup`
做为任务的唯一标识

`执行需要调用的方法和参数` 小伙伴看到这个有些疑问，不是直接实现`Job`来写定时任务吗？怎么还存储调用的方法和参数？

这就是与前一种方法的不同之处，我们使用反射来执行定时任务中任务逻辑，这样就保留了`Spring`的`AOP`功能

具体查看[JobInvokeUtil.java](UseRAMJobStore%2Fsrc%2Fmain%2Fjava%2Fcom%2Fbigboss%2Fuseramjobstore%2Futil%2FJobInvokeUtil.java)

