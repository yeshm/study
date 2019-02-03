package study.yeshm.sample

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@CompileStatic
@SpringBootApplication
class DemoApp {

    static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args)
    }

}
