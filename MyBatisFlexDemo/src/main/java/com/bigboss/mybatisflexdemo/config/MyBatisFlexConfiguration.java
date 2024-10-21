package com.bigboss.mybatisflexdemo.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author: maifuwa
 * @date: 2024/10/21 10:24
 * @description:
 */

@Configuration
public class MyBatisFlexConfiguration implements ConfigurationCustomizer {

    private static final Logger sqlLogger = LoggerFactory.getLogger("mybatis-flex-sql");

    @Override
    public void customize(FlexConfiguration configuration) {

        // 配置 sql 日志打印
        AuditManager.setAuditEnable(true);
        AuditManager.setMessageCollector(auditMessage ->
                sqlLogger.info("Flex exec sql took {} ms >> {}", auditMessage.getElapsedTime(), auditMessage.getFullSql())
        );

        // 配置逻辑删除
        FlexGlobalConfig globalConfig = FlexGlobalConfig.getDefaultConfig();
        globalConfig.setNormalValueOfLogicDelete(false);
        globalConfig.setDeletedValueOfLogicDelete(true);
    }

}
