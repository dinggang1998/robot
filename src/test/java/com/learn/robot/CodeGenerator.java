package com.learn.robot;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    private static String username="stevending";
    private static String password="slyWOAINI1314";
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://rm-bp1n4l8b76qf5z4e2zo.mysql.rds.aliyuncs.com:3306/stevending?serverTimezone=UTC&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";


    @Test
    public void codeGenerator() {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setActiveRecord(true);//支持AR模式
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);//文件覆盖
        gc.setIdType(IdType.NONE);//主键自增
        gc.setServiceName("%sService");//设置接口名称是否有I
        gc.setAuthor("Steven Ding");
        gc.setBaseResultMap(true);//xml映射
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);//sql片段
        gc.setDateType(DateType.ONLY_DATE);
        gc.setEnableCache(true);
        mpg.setGlobalConfig(gc);

        // 2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(driver);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 3.配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);

        // 4.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true);//全局大小写命名
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);//使用lombok
        strategyConfig.setTablePrefix("");
        strategyConfig.setInclude("SSSS_USER");
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategyConfig);

        // 5.包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.learn.robot");
        pc.setMapper("dao");
        pc.setEntity("model.test");
        pc.setXml("mapper");

        mpg.setPackageInfo(pc);

        // 6.自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        //如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 7.执行
        mpg.execute();
    }

}
