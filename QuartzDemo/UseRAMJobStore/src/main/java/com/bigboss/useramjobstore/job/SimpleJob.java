package com.bigboss.useramjobstore.job;

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
    }

    public void execute(String params) {
        log.info("SimpleJob execute params:{}", params);
    }
}
