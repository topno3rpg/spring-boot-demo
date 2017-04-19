import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2016/10/13.
 */
public class ProjectConfig {

    static String[/*groupId*/][/*dataId*/] aicai_dubbo_pubish_config = new String[][]{
            {"aicai", "dubbo"},
            {"aicai", "filter"},
            {"aicai-publish-dubbo", "baseconfig"},
            {"aicai-publish-dubbo", "pingan"},
            {"aicai-publish-dubbo", "logconfig"},
            {"aicai-publish-dubbo", "dubboconfig"},
            {"aicai-publish-dubbo", "egbank"},
            {"aicai-publish-dubbo", "systemconfig"}
    };

    static String[/*groupId*/][/*dataId*/] aicai_web_backend_config = new String[][]{
            {"aicai", "dubbo"},
            {"aicai", "filter"},
            {"aicai-web-backend", "baseconfig"},
            {"aicai-web-backend", "logconfig"}
    };

    static String[/*groupId*/][/*dataId*/] aicai_web_picture_config = new String[][]{
            {"aicai", "dubbo"},
            {"aicai", "filter"},
            {"aicai-web-picture", "baseconfig"},
            {"aicai-web-picture", "logconfig"},
            {"aicai-web-picture", "freemarker"}
    };

    static String[/*groupId*/][/*dataId*/] cps_dubbo_pubish_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-publish-dubbo", "baseconfig"},
            {"cps-publish-dubbo", "logconfig"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_app_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-app", "baseconfig"},
            {"cps-web-app", "logconfig"},
            {"cps-web-app", "freemarker"},
            {"cps-web-app", "alipay"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_backend_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-backend", "baseconfig"},
            {"cps-web-backend", "logconfig"},
            {"cps-web-backend", "freemarker"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_external_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-external", "baseconfig"},
            {"cps-web-external", "logconfig"},
            {"cps-web-external", "freemarker"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_h5_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-h5", "baseconfig"},
            {"cps-web-h5", "logconfig"},
            {"cps-web-h5", "freemarker"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_merchant_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-merchant", "baseconfig"},
            {"cps-web-merchant", "logconfig"},
            {"cps-web-merchant", "freemarker"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_openapi_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-openapi", "baseconfig"},
            {"cps-web-openapi", "logconfig"},
            {"cps-web-openapi", "freemarker"}
    };

    static String[/*groupId*/][/*dataId*/] cps_web_site_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-web-site", "baseconfig"},
            {"cps-web-site", "logconfig"},
            {"cps-web-site", "freemarker"},
            {"cps-web-site", "alipay"}
    };

    static String[/*groupId*/][/*dataId*/] cps_task_consumer_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-task-consumer", "baseconfig"},
            {"cps-task-consumer", "logconfig"},
            {"cps-task-consumer", "push"}
    };

    static String[/*groupId*/][/*dataId*/] cps_task_external_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps-task-external", "baseconfig"},
            {"cps-task-external", "logconfig"}
    };

    static String[/*groupId*/][/*dataId*/] cps_task_scheduler_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-task-scheduler", "baseconfig"},
            {"cps-task-scheduler", "logconfig"},
            {"cps-task-scheduler", "pass_user"},
            {"cps-task-scheduler", "rig_user"}
    };

    static String[/*groupId*/][/*dataId*/] cps_task_solr_config = new String[][]{
            {"cps", "dubbo"},
            {"cps", "filter"},
            {"cps", "dubboconfig"},
            {"cps-task-solr", "baseconfig"},
            {"cps-task-solr", "logconfig"}
    };

    static Map<String, String[][]> config_map = new HashMap<String, String[][]>();

    static {
        config_map.put("aicai-publish-dubbo", aicai_dubbo_pubish_config);
        config_map.put("aicai-web-backend", aicai_web_backend_config);
        config_map.put("aicai-web-picture", aicai_web_picture_config);

        config_map.put("cps-publish-dubbo", cps_dubbo_pubish_config);
        config_map.put("cps-web-app", cps_web_app_config);
        config_map.put("cps-web-backend", cps_web_backend_config);
        config_map.put("cps-web-external", cps_web_external_config);
        config_map.put("cps-web-h5", cps_web_h5_config);
        config_map.put("cps-web-merchant", cps_web_merchant_config);
        config_map.put("cps-web-openapi", cps_web_openapi_config);
        config_map.put("cps-web-site", cps_web_site_config);
        config_map.put("cps-task-consumer", cps_task_consumer_config);
        config_map.put("cps-task-external", cps_task_external_config);
        config_map.put("cps-task-scheduler", cps_task_scheduler_config);
        config_map.put("cps-task-solr", cps_task_solr_config);

    }

}
