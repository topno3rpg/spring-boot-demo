package dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by Admin on 2016/9/7.
 */
@Service(version = "1.0", retries = 0)
public class DubboProviderImpl implements DubboProviderInterface {

    public void testProvider() {
        System.out.println("i am providering......");
    }

}
