package com.bigboss.useramjobstore.job.base;

import com.bigboss.useramjobstore.util.JobInvokeUtil;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: maifuwa
 * @date: 2024/10/14 10:34
 * @description:
 */
@Setter
public class QuartzJobExecution extends QuartzJobBean {

    private String invokeTarget;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobInvokeUtil.invokeMethod(invokeTarget);
        } catch (NoSuchBeanDefinitionException | NoSuchMethodException | IllegalAccessException e) {
            throw new JobExecutionException("invokeTarget 配置错误", e);
        } catch (InvocationTargetException e) {
            // aop will handle this exception
        }
    }
}
