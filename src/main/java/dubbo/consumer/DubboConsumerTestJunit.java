package dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import dubbo.provider.DubboProviderInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Admin on 2016/9/7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dubbo-root.xml", "classpath:dubbo-consumer.xml"})
public class DubboConsumerTestJunit {

    @Reference(version = "1.0")
    DubboProviderInterface dubboProviderInterface;

    @Test
    public void testConsumer() {
        dubboProviderInterface.testProvider();
        System.out.println("i am providering...");
    }

}
