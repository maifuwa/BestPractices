package com.bigboss.useramjobstore.job;

import com.bigboss.useramjobstore.common.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/11 14:38
 * @description:
 */
@Slf4j
@Component
public class SimpleJob {

    public void execute() {
        log.info("SimpleJob execute");
        throw new InternalException("测试任务执行异常捕获");
    }

    public void execute(String params) {
        log.info("SimpleJob execute params:{}", params);
    }
}
