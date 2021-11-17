package com.nft;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class MybatisPlusCodeGenerator {
    /**
     * 所有实体的基类(全类名)
     */
    private static final String DEFAULT_CONFIG_PATH = "/generator.properties";
    private static final String TEMPLATE_FOLDER = "template/";
    private final static String FILE_NAME_MODEL = "entity.java.vm";
    private final static String FILE_NAME_MAPPER = "mapper.java.vm";
    private final static String FILE_NAME_MAPPERXML = "mapper.xml.vm";
    private final static String FILE_NAME_SERVICE = "service.java.vm";
    private final static String FILE_NAME_SERVICEIMPL = "serviceImpl.java.vm";
    private final static String FILE_NAME_CONTROLLER = "controller.java.vm";
    private String projectRootDirectory;
    private String author;
    private String driver;
    private String jdbcUrl;
    private String user;
    private String password;
    private String basePackage;
    private String modulePackage;
    private String tableName;
    private String tablePrefix;
    private String superEntity = "BaseDO";

    public MybatisPlusCodeGenerator(String propertyFilePath) {
        loadProperty(propertyFilePath);
    }

    public static void main(String[] args) {
        generate(null);
    }

    /**
     * 生成代码
     *
     * @param resourceFilePath 资源文件路径，默认为/generator.properties
     */
    public static void generate(String resourceFilePath) {
        String propertyFilePath = !StringUtils.isEmpty(resourceFilePath) ? resourceFilePath : DEFAULT_CONFIG_PATH;
        String path = MybatisPlusCodeGenerator.class.getResource(propertyFilePath).getPath();
        System.out.println(path);
        MybatisPlusCodeGenerator myMapperCodeGenerator = new MybatisPlusCodeGenerator(path);
        myMapperCodeGenerator.doGenerate();
    }

    private void loadProperty(String propertyFilePath) {
        Properties properties = new Properties();
        File file = new File(propertyFilePath);

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            properties.load(in);
        } catch (IOException var31) {
            var31.printStackTrace();
            System.out.printf("加载配置文件错误");
        }
        String projectPath = System.getProperty("user.dir");
        projectRootDirectory = projectPath + properties.getProperty("projectRootDirectory");
        author = properties.getProperty("author");
        driver = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        basePackage = properties.getProperty("basePackage");
        modulePackage = properties.getProperty("modulePackage");
        tableName = properties.getProperty("tableName");
        tablePrefix = properties.getProperty("tablePrefix");
        if (!StringUtils.isEmpty(properties.getProperty("superEntity"))) {
            superEntity = properties.getProperty("superEntity");
        }
    }

    private void doGenerate() {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(getGlobalConfig());
        mpg.setDataSource(getDataSourceConfig());
        mpg.setStrategy(getStrategyConfig());
        mpg.setPackageInfo(getPackageConfig());
        mpg.setCfg(getInjectionConfig());
        mpg.setTemplate(getTemplateConfig());
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        // 执行生成
        mpg.execute();
    }

    private GlobalConfig getGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //输出文件路径

        gc.setOutputDir(projectRootDirectory + "/src/main/java");
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor(author);
        gc.setSwagger2(true);
        gc.setOpen(false);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");

        return gc;
    }

    private DataSourceConfig getDataSourceConfig() {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(driver);
        dsc.setUsername(user);
        dsc.setPassword(password);
        dsc.setUrl(jdbcUrl);
        return dsc;
    }

    private StrategyConfig getStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(superEntity);
        strategy.setSuperEntityColumns("id", "gmt_create", "gmt_update", "deleted");

        // 需要生成的表
        strategy.setInclude(tableName.split(","));
        strategy.setTablePrefix(tablePrefix);
        //生成@RestController控制器
        strategy.setRestControllerStyle(true);
        //实体是否为lombok模型
        strategy.setEntityLombokModel(true);
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        return strategy;
    }

    private PackageConfig getPackageConfig() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        String subDir = "";
        if (!StringUtils.isEmpty(modulePackage)) {
            subDir = "." + modulePackage;
        }
        pc.setParent(basePackage);
        pc.setController("controller" + subDir);
        pc.setService("service" + subDir);
        pc.setServiceImpl("service" + subDir + ".impl");
        pc.setMapper("data.mapper" + subDir);
        pc.setEntity("data.entity" + subDir);
        pc.setXml("xml");
        return pc;
    }

    private InjectionConfig getInjectionConfig() {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(getTemplateFilePath(FILE_NAME_MAPPERXML)) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectRootDirectory + "/src/main/resources/mapper/" + modulePackage
                        + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        focList.add(new FileOutConfig(getTemplateFilePath(FILE_NAME_MODEL)) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectRootDirectory + StringUtils.replace("/src/main/java/" + basePackage + "/data/entity/" + modulePackage, ".", "/")
                        + "/" + tableInfo.getEntityName() + "DO.java";
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }

    private TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板，entity和xml另外在InjectionConfig中配置
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(null);
//        templateConfig.setEntity(getTemplateFilePathExcludeSuffix(FILE_NAME_MODEL));
        templateConfig.setService(getTemplateFilePathExcludeSuffix(FILE_NAME_SERVICE));
        templateConfig.setController(getTemplateFilePathExcludeSuffix(FILE_NAME_CONTROLLER));
        templateConfig.setServiceImpl(getTemplateFilePathExcludeSuffix(FILE_NAME_SERVICEIMPL));
        templateConfig.setMapper(getTemplateFilePathExcludeSuffix(FILE_NAME_MAPPER));
        templateConfig.setXml(null);
        return templateConfig;
    }

    private String getTemplateFilePathExcludeSuffix(String vmFileName) {
        return TEMPLATE_FOLDER + vmFileName.substring(0, vmFileName.length() - 3);
    }

    private String getTemplateFilePath(String vmFileName) {
        return TEMPLATE_FOLDER + vmFileName;
    }

}
