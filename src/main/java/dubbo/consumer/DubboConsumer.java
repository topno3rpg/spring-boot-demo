package dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import dubbo.provider.DubboProviderInterface;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 2016/9/7.
 */
@Component
public class DubboConsumer {

    @Reference(version = "1.0", check = false)
    DubboProviderInterface dubboProviderInterface;

    public void testConsumer() {
        System.out.println("i am consumering...");
        dubboProviderInterface.testProvider();
    }

}
