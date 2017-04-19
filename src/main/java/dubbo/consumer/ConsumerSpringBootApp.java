package dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Admin on 2016/8/12.
 */
@ComponentScan
@EnableAutoConfiguration
@ImportResource({"classpath:dubbo-root.xml", "classpath:dubbo-consumer.xml"})
public class ConsumerSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerSpringBootApp.class, args);
    }

}
