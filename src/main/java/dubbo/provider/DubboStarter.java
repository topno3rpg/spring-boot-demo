package dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Admin on 2016/9/7.
 */
public class DubboStarter {


    public static void main(String[] args) throws IOException, InterruptedException {
        String path = DubboStarter.class.getResource("/").getPath();
        System.out.println(path);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"file:" + path + "dubbo-root.xml", "file:" + path + "dubbo-provider.xml"});
        context.start();

        DubboProviderInterface dubboProviderInterface = (DubboProviderInterface) context.getBean(DubboProviderInterface.class);
        dubboProviderInterface.testProvider();
        while (true) {
            Thread.sleep(1000000);
        }

//        System.in.read(); // 按任意键退出
    }

}
