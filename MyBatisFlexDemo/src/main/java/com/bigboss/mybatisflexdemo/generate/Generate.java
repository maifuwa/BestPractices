package com.bigboss.mybatisflexdemo.generate;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.datasource.FlexDataSource;
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


    @Value("${mybatis-flex.generate.ddl:false}")
    private Boolean generate;

    /**
     * 数据库逆向生成代码(适配多数据源)
     */
    @PostConstruct
    public void generate() {
        if (!generate) return;

        log.info("Start generate code from database");

        FlexDataSource flexDataSource = FlexGlobalConfig.getDefaultConfig()
                .getDataSource();

        flexDataSource.getDataSourceMap().forEach((key, value) -> {
            GlobalConfig globalConfig = createGlobalConfig(key);
            Generator generator = new Generator(value, globalConfig);
            generator.generate();
        });

    }


    private static GlobalConfig createGlobalConfig(String dataSourceName) {
        GlobalConfig globalConfig = new GlobalConfig();

        String packageName = Generate.class.getPackageName();

        globalConfig.getPackageConfig()
                .setSourceDir("MyBatisFlexDemo/src/main/java")
                .setBasePackage(packageName.substring(0, packageName.lastIndexOf(".")));

        globalConfig.getStrategyConfig()
                .setLogicDeleteColumn("is_delete");

        globalConfig.enableEntity()
                .setWithLombok(true)
                .setJdkVersion(17)
                .setDataSource(dataSourceName)
                .setOverwriteEnable(true);

        globalConfig.enableMapper()
                .setOverwriteEnable(true);

        return globalConfig;
    }
}
