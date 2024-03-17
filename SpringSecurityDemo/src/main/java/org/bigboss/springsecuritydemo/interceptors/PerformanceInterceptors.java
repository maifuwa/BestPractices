package org.bigboss.springsecuritydemo.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:46
 * @description: 性能拦截器
 */
@Slf4j
public class PerformanceInterceptors implements HandlerInterceptor {

    private static final ThreadLocal<StopWatch> stopWatch = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch sw = new StopWatch();
        stopWatch.set(sw);
        sw.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        stopWatch.get().stop();
        stopWatch.get().start();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch sw = stopWatch.get();
        sw.stop();
        String method = handler.getClass().getSimpleName();
        if (handler instanceof HandlerMethod handlerMethod) {
            String beanType = handlerMethod.getBeanType().getName();
            String methodName = handlerMethod.getMethod().getName();
            method = beanType + "." + methodName;
        }
        log.info("请求路径:{}, 方法：{}，响应状态：{}, 异常：{}, 总耗时：{}ms, 处理耗时：{}ms, 最后任务耗时：{}ms",
                request.getRequestURI(), method, response.getStatus(), ex == null ? "无" : ex.getClass().getSimpleName(),
                sw.getTotalTimeMillis(), sw.getTotalTimeMillis() - sw.getLastTaskTimeMillis(),
                sw.getLastTaskTimeMillis());
        stopWatch.remove();
    }
}
