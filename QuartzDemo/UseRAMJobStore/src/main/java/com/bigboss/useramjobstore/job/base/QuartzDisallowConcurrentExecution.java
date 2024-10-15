package com.bigboss.useramjobstore.job.base;

import com.bigboss.useramjobstore.util.JobInvokeUtil;
import lombok.Setter;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: maifuwa
 * @date: 2024/10/14 10:36
 * @description: 不会并发执行的定时任务
 */
@Setter
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends QuartzJobBean {

    private String invokeTarget;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobInvokeUtil.invokeMethod(invokeTarget);
        } catch (NoSuchBeanDefinitionException | NoSuchMethodException | IllegalAccessException e) {
            throw new JobExecutionException("invokeTarget 配置错误", e); // should be caught by ScheduleServiceImpl, done yet
        } catch (InvocationTargetException e) {
            throw new JobExecutionException("业务执行失败", e); // should be caught by aop, done yet
        }
    }
}
