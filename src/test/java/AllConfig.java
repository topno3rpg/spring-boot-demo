import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.extend.ExtendPropertiesFactoryBean;
import com.taobao.diamond.manager.DiamondManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * Created by Admin on 2016/12/5.
 */
public class AllConfig {

    static Logger logger = LoggerFactory.getLogger(AllConfig.class);

    static String[/*groupId*/][/*dataId*/] aicai_groupid_dataid = new String[][]{
            {"aicai", "filter"},
            {"aicai", "dubbo"},
            {"aicai", "dubboconfig"},
            {"aicai-publish-dubbo", "baseconfig"},
            {"aicai-publish-dubbo", "pingan"},
            {"aicai-publish-dubbo", "logconfig"},
            {"aicai-publish-dubbo", "egbank"},
            {"aicai-publish-dubbo", "dubboconfig"},
            {"aicai-publish-dubbo", "systemconfig"},
            {"aicai-web-backend", "baseconfig"},
            {"aicai-web-backend", "logconfig"},
            {"aicai-web-cash-gateway", "baseconfig"},
            {"aicai-web-cash-gateway", "logconfig"},
            {"aicai-web-cash-gateway", "freemarker"},
            {"aicai-web-cashier-desk", "baseconfig"},
            {"aicai-web-cashier-desk", "logconfig"},
            {"aicai-web-cashier-desk", "freemarker"},
            {"aicai-web-collect", "baseconfig"},
            {"aicai-web-collect", "logconfig"},
            {"aicai-web-collect", "freemarker"},
            {"aicai-web-external", "baseconfig"},
            {"aicai-web-external", "logconfig"},
            {"aicai-web-external", "freemarker"},
            {"aicai-web-nc", "baseconfig"},
            {"aicai-web-nc", "freemarker"},
            {"aicai-web-nc", "logconfig"},
            {"aicai-web-openapi", "baseconfig"},
            {"aicai-web-openapi", "logconfig"},
            {"aicai-web-openapi", "freemarker"},
            {"aicai-web-picture", "baseconfig"},
            {"aicai-web-picture", "logconfig"},
            {"aicai-web-picture", "freemarker"}
    };


    static String[/*groupId*/][/*dataId*/] cps_groupid_dataid = new String[][]{
            {"cps", "filter"},
            {"cps", "dubbo"},
            {"cps", "dubboconfig"},
            {"cps-publish-dubbo", "baseconfig"},
            {"cps-publish-dubbo", "logconfig"},
            {"cps-task-consumer", "baseconfig"},
            {"cps-task-consumer", "logconfig"},
            {"cps-task-consumer", "push"},
            {"cps-task-external", "baseconfig"},
            {"cps-task-external", "logconfig"},
            {"cps-task-scheduler", "baseconfig"},
            {"cps-task-scheduler", "logconfig"},
            {"cps-task-scheduler", "pass_user"},
            {"cps-task-scheduler", "rig_user"},
            {"cps-task-solr", "baseconfig"},
            {"cps-task-solr", "logconfig"},
            {"cps-web-app", "baseconfig"},
            {"cps-web-app", "freemarker"},
            {"cps-web-app", "logconfig"},
            {"cps-web-app", "alipay"},
            {"cps-web-backend", "baseconfig"},
            {"cps-web-backend", "freemarker"},
            {"cps-web-backend", "logconfig"},
            {"cps-web-external", "baseconfig"},
            {"cps-web-external", "freemarker"},
            {"cps-web-external", "logconfig"},
            {"cps-web-h5", "baseconfig"},
            {"cps-web-h5", "freemarker"},
            {"cps-web-h5", "logconfig"},
            {"cps-web-merchant", "baseconfig"},
            {"cps-web-merchant", "freemarker"},
            {"cps-web-merchant", "logconfig"},
            {"cps-web-openapi", "baseconfig"},
            {"cps-web-openapi", "freemarker"},
            {"cps-web-openapi", "logconfig"},
            {"cps-web-site", "baseconfig"},
            {"cps-web-site", "freemarker"},
            {"cps-web-site", "logconfig"},
            {"cps-web-site", "alipay"},

    };


    static String domian_name = "aixuedai.com";

    static final String config_charset = "UTF-8";

    static final String new_line = "\r\n";

    static String file_path = "D:\\爱学贷项目\\aiyoumi\\%s_%s";

    static String domain_up_file = "D:\\爱学贷项目\\aiyoumi\\domain_up.txt";
    static String old_domain_dir = "D:\\爱学贷项目\\aiyoumi\\test";
    static String new_file_path = "D:\\爱学贷项目\\aiyoumi\\%s";

    static void checkConfig(String[/*groupId*/][/*dataId*/] configs) {
        logger.info("开始加载配置中心");
        for (String[] config : configs) {
            String groupId = config[0];
            final String dataId = config[1];
            StringBuffer sb = null;
            try {
                DiamondManager diamondManager = DiamondClients.createSafeDiamondManager(groupId, dataId);
                String allConfig = diamondManager.getAvailableConfigureInfomation();
                Properties properties = new Properties();
                properties.load(new ByteArrayInputStream(allConfig.getBytes()));
                logger.info("开始检查 [" + groupId + "] , [" + dataId + "]");
                Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Object, Object> entry = it.next();
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    if (value == null || value.trim().length() == 0) {
                        continue;
                    }
                    if (value.indexOf(domian_name) != -1) {
                        if (sb == null) {
                            sb = new StringBuffer();
                        }
                        sb.append(key + "=" + value + new_line);
                        System.out.println(key + "=" + value);
                    }
                }
                if (sb != null && sb.length() > 0) {
                    writeFile(String.format(file_path, groupId, dataId), sb.toString());
                }
                logger.info("结束检查 [" + groupId + "] , [" + dataId + "]");
            } catch (IOException e) {
                throw new RuntimeException("加载配置信息IO异常！groupId=" + groupId + ", dataId=" + dataId, e);
            } catch (Exception e) {
                throw new RuntimeException("加载配置信息异常！groupId=" + groupId + ", dataId=" + dataId, e);
            }
        }
        logger.info("成功加载配置中心");
    }

    static void writeFile(String fileName, String config) throws IOException {
        FileOutputStream out = null;
        PrintWriter writer = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            BufferedOutputStream stream = new BufferedOutputStream(out);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream, config_charset)));
            writer.append(config);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } finally {
            if (writer != null)
                writer.close();
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getFileContent(String path) throws IOException {
        File tFile = new File(path);
        if (!tFile.isFile()) {
            throw new RuntimeException("不是文件");
        }
        RandomAccessFile file = new RandomAccessFile(tFile, "r");
        long fileSize = file.length();
        byte[] bytes = new byte[(int) fileSize];
        long readLength = 0L;
        while (readLength < fileSize) {
            int onceLength = file.read(bytes, (int) readLength, (int) (fileSize - readLength));
            if (onceLength > 0) {
                readLength += onceLength;
            } else {
                break;
            }
        }
        try {
            file.close();
        } catch (Exception e) {

        }
        return new String(bytes, config_charset);
    }

    static void domainUp() throws IOException {
//        String fileStr = getFileContent(domain_up_file);
        Properties properties = new Properties();
        properties.load(new FileInputStream(domain_up_file));
        Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String key = entry.getKey().toString();
            if (key.indexOf("_") == -1) {
                System.out.println(key);
                list.add(key);
            }
        }

        File file = new File(old_domain_dir);
        File[] files = file.listFiles();
        for (File eachOne : files) {
            String fileName = eachOne.getName();
            logger.info("load file fileName={}", fileName);
            StringBuffer sb = new StringBuffer();
            Properties old_prop = new Properties();
            old_prop.load(new FileInputStream(eachOne));
            Iterator<Map.Entry<Object, Object>> old_it = old_prop.entrySet().iterator();
            while (old_it.hasNext()) {
                Map.Entry<Object, Object> entry = old_it.next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                if (list.contains(key)) {
                    if (properties.getProperty(key).equalsIgnoreCase(value)) {
                        sb.append("old");
                        sb.append(new_line);
                        sb.append(key + "=" + value);
                        sb.append(new_line);

                        Iterator<Map.Entry<Object, Object>> new_it = properties.entrySet().iterator();
                        int i = 0;
                        while (new_it.hasNext()) {
                            Map.Entry<Object, Object> new_entry = new_it.next();
                            String new_key = new_entry.getKey().toString();
                            System.out.println("new_key=" + new_key);
                            if (new_key.indexOf("_") == -1) {
                                continue;
                            } else {
                                String[] strArr = new_key.split("_");
                                if (strArr[0].equalsIgnoreCase(key)) {
                                    if (i == 0) {
                                        sb.append("new");
                                    }
                                    sb.append(new_line);
                                    sb.append(new_key + "=" + new_entry.getValue().toString());
                                    i++;
                                } else {
                                    if (key.indexOf("h5") != -1 && "h5".equalsIgnoreCase(strArr[1])) {
                                        if (i == 0) {
                                            sb.append("new");
                                        }
                                        sb.append(new_line);
                                        sb.append(new_key + "=" + new_entry.getValue().toString());
                                        i++;
                                    }
                                }
                            }
                        }
                        sb.append(new_line);
                        sb.append(new_line);
                    } else {
                        System.out.println("wocao : " + key + "=" + value);
                    }
                }
            }
            if (sb != null && sb.length() > 0) {
                writeFile(String.format(new_file_path, fileName), sb.toString());
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        checkConfig(aicai_groupid_dataid);
//
        domainUp();
        System.exit(1);
    }
}
