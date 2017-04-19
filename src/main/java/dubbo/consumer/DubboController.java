package dubbo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DubboController {

    @Autowired
    DubboConsumer dubboConsumer;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        dubboConsumer.testConsumer();
        return "Hello World!";
    }
}
