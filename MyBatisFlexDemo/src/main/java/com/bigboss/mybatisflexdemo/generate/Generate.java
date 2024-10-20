package com.bigboss.mybatisflexdemo.generate;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/16 11:14
 * @description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Generate {

    private final HikariDataSource dataSource;

    @Value("${generate.ddl:false}")
    private Boolean generate;

    @PostConstruct
    public void generate() {
        if (!generate) return;

        log.info("Start generate code from database");
        GlobalConfig globalConfig = createGlobalConfig();
        Generator generator = new Generator(dataSource, globalConfig);
        generator.generate();
    }


    private static GlobalConfig createGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();

        String packageName = Generate.class.getPackageName();

        globalConfig.getPackageConfig()
                .setSourceDir("MyBatisFlexDemo/src/main/java")
                .setBasePackage(packageName.substring(0, packageName.lastIndexOf(".")));

        globalConfig.enableEntity()
                .setWithLombok(true)
                .setJdkVersion(17)
                .setOverwriteEnable(true);

        globalConfig.enableMapper()
                .setOverwriteEnable(true);

        return globalConfig;
    }
}
