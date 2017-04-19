import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.extend.DynamicProperties;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 2016/10/13.
 */
public class CheckDataUtil {

    //配置中心配置
    public static Properties remote_config = new Properties();
    //项目PROPERTIES配置
    public static Properties project_properties = new Properties();
    //filter PROPERTIES配置
    public static Properties filter_properties = new Properties();
    //filter dubbo PROPERTIES配置
    public static Properties dubbo_properties = new Properties();
    //编译前XML占位符
    public static List<String> before_compile_xml = new ArrayList<String>();
    //编译后XML占位符
    public static List<String> after_compile_xml = new ArrayList<String>();

    private String dubbo_project_path_before_compile = "G:/axd-project/cps-project/cps-publish-project/" +
            "cps-publish-dubbo/src/main/resources/%s";
    private String dubbo_project_path_after_compile = "G:/axd-project/cps-project/" +
            "cps-publish-project/cps-publish-dubbo/target/classes/%s";

    private String web_project_path_before_compile = "G:/axd-project/cps-project/cps-web-project/%s/src/main/resources";
    private String web_project_path_after_compile = "G:/axd-project/cps-project/cps-web-project/%s/target/classes";

    private String task_project_path_before_compile = "G:/axd-project/cps-project/cps-task-project/%s/src/main/resources";
    private String task_project_path_after_compile = "G:/axd-project/cps-project/cps-task-project/%s/target/classes";

    private String filter_file = "G:/axd-project/cps-project/filter/filter-%s.properties";
    private String dubbo_file = "G:/axd-project/cps-project/filter/dubbo.properties";

    private String dubbo_project_name = "cps-publish-dubbo";


    Logger logger = LoggerFactory.getLogger(CheckDataUtil.class);

    void loadConfig(String[/*groupId*/][/*dataId*/] configs) {
        logger.info("开始加载配置中心");
        for (String[] config : configs) {
            String groupId = config[0];
            final String dataId = config[1];
            try {
                DiamondManager diamondManager = DiamondClients.createSafeDiamondManager(groupId, dataId);
                String allConfig = diamondManager.getAvailableConfigureInfomation();
                Properties properties = new Properties();
                properties.load(new ByteArrayInputStream(allConfig.getBytes()));
                CollectionUtils.mergePropertiesIntoMap(properties, remote_config);
            } catch (IOException e) {
                throw new RuntimeException("加载配置信息IO异常！groupId=" + groupId + ", dataId = " + dataId, e);
            } catch (Exception e) {
                throw new RuntimeException("加载配置信息异常！groupId=" + groupId + ", dataId = " + dataId, e);
            }
        }
        logger.info("成功加载配置中心");
    }

    void loadFilterProperties(String env) throws Exception {
        String fileName = String.format(filter_file, env);
        File file = new File(fileName);
        logger.info("load file fileName={}", fileName);
        filter_properties.load(new FileInputStream(file));
        File dubboFile = new File(dubbo_file);
        logger.info("load file fileName={}", dubbo_file);
        dubbo_properties.load(new FileInputStream(dubboFile));
    }

    void loadProperties(String projectName) throws Exception {
        String path = null;

        if (projectName.indexOf("services") != -1) {
            path = String.format(dubbo_project_path_after_compile, "");
        } else if (projectName.indexOf("web") != -1) {
            path = String.format(web_project_path_after_compile, projectName);
        } else if (projectName.indexOf("task") != -1) {
            path = String.format(task_project_path_after_compile, projectName);
        } else {
            throw new Exception("不支持的项目名称：projectName=" + projectName);
        }

        File file = new File(path);
        File[] files = file.listFiles();
        for (File eachOne : files) {
            String fileName = eachOne.getName();
            if (fileName.indexOf(".properties") == -1) {
                continue;
            }
            logger.info("load file fileName={}", fileName);
            Properties properties = new Properties();
            properties.load(new FileInputStream(eachOne));
            CollectionUtils.mergePropertiesIntoMap(properties, project_properties);
        }
    }

    void loadXml(String projectName, String type) throws Exception {
        String path = null;
        String path_private = null;
        if (projectName.indexOf("services") != -1) {
            if ("before".equalsIgnoreCase(type)) {
                path = String.format(dubbo_project_path_before_compile, "");
            } else if ("after".equalsIgnoreCase(type)) {
                path = String.format(dubbo_project_path_after_compile, "");
            } else {
                throw new Exception("不支持的类型：type=" + type);
            }
        } else if (projectName.indexOf("web") != -1) {
            if ("before".equalsIgnoreCase(type)) {
                path = String.format(web_project_path_before_compile, projectName);
            } else if ("after".equalsIgnoreCase(type)) {
                path = String.format(web_project_path_after_compile, projectName);
            } else {
                throw new Exception("不支持的类型：type=" + type);
            }
        } else if (projectName.indexOf("task") != -1) {
            if ("before".equalsIgnoreCase(type)) {
                path = String.format(task_project_path_before_compile, projectName);
            } else if ("after".equalsIgnoreCase(type)) {
                path = String.format(task_project_path_after_compile, projectName);
            } else {
                throw new Exception("不支持的类型：type=" + type);
            }
        } else {
            throw new Exception("不支持的项目名称：projectName=" + projectName);
        }

        File file = new File(path);
        File[] files = file.listFiles();
        for (File eachOne : files) {
            String fileName = eachOne.getName();
            boolean a = fileName.indexOf(".xml") == -1;
            boolean b = projectName.equalsIgnoreCase(fileName);
            if (a && !b) {
                continue;
            }
            logger.info("load file fileName={}", fileName);
            if (!a) {
                InputStream in = new FileInputStream(eachOne);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                String regEx = "\\$\\{(.*?)}";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(buffer.toString());
                while (matcher.find()) {
                    if ("before".equalsIgnoreCase(type)) {
                        before_compile_xml.add(matcher.group(1));
                    } else {
                        after_compile_xml.add(matcher.group(1));
                    }
                }
            }
            if (b) {
                File privateFile = new File(path);
                File[] privateFiles = privateFile.listFiles();
                for (File privateOne : privateFiles) {
                    String privateFileName = privateOne.getName();
                    if (privateFileName.indexOf(".xml") == -1) {
                        continue;
                    }
                    InputStream in = new FileInputStream(privateOne);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }

                    String regEx = "\\$\\{(.*?)}";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(buffer.toString());
                    while (matcher.find()) {
                        if ("before".equalsIgnoreCase(type)) {
                            before_compile_xml.add(matcher.group(1));
                        } else {
                            after_compile_xml.add(matcher.group(1));
                        }
                    }
                }
            }

        }
    }

    void checkProperties() {
        logger.info("开始核对PROPERTIES配置");
        Iterator<Map.Entry<Object, Object>> it = project_properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (remote_config.containsKey(key)) {
                String remoteValue = remote_config.getProperty(key);
                if (remoteValue == null) {
                    if (value != null) {
                        System.out.println("key=" + key + ",远程与本地value不同");
                        System.out.println("local " + key + "=" + value);
                        System.out.println("remote is null");
                    }
                } else {
                    if (!remoteValue.equalsIgnoreCase(value)) {
                        System.out.println("key=" + key + ",远程与本地value不同");
                        System.out.println("local " + key + "=" + value);
                        System.out.println("remote " + key + "=" + remoteValue);
                    }
                }
            } else {
                System.out.println("key=" + key + ",远程配置中心不存在");
                System.out.println("local " + key + "=" + value);
            }
        }
        logger.info("结束核对PROPERTIES配置");
    }

    void checkXml() {
        logger.info("开始核对XML配置");
        for (String key : before_compile_xml) {
            if (after_compile_xml.contains(key)) {
                continue;
            }
            if (dubbo_properties.containsKey(key)) {
                continue;
            }
            System.out.println("key= " + key + "   由filter直接注入");
            System.out.println("filter " + key + "=" + filter_properties.getProperty(key));
            System.out.println("remote " + key + "=" + remote_config.getProperty(key));
        }
        logger.info("结束核对XML配置");
    }

    void execute(String projectName, String env) throws Exception {
        //加载配置中心配置
        if (projectName.indexOf("services") > -1) {
            loadConfig(ProjectConfig.config_map.get(dubbo_project_name));
        } else {
            loadConfig(ProjectConfig.config_map.get(projectName));
        }

        //加载编译后的PROPERTIES配置
        loadProperties(projectName);
        //加载编译前 的XML占位符 ${}
        loadXml(projectName, "before");
        //加载编译后 的XML占位符 ${}
        loadXml(projectName, "after");
        //加载filter配置文件
        loadFilterProperties(env);
        //核对PROPERTIES配置
        checkProperties();
        //核对XML配置
        checkXml();
    }

    public static void main(String[] args) throws Exception {
        CheckDataUtil obj = new CheckDataUtil();
//        obj.execute("aicai-web-backend", "product");
//        obj.execute("cps-services-active", "product");
//        obj.execute("cps-web-app", "product");
//        obj.execute("cps-web-backend", "product");
//        obj.execute("cps-web-external", "product");
//        obj.execute("cps-web-h5", "product");
//        obj.execute("cps-web-merchant", "product");
//        obj.execute("cps-web-openapi", "product");
//        obj.execute("cps-web-site", "product");
        obj.execute("cps-task-consumer", "product");
//        obj.execute("cps-task-external", "product");
//        obj.execute("cps-task-scheduler", "product");
//        obj.execute("cps-task-solr", "product");
    }

}
