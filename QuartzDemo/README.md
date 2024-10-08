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
代码环节直接注入`Spring`维护的`Scheduler`，增删改查任务信息即可