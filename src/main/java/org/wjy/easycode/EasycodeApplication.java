package org.wjy.easycode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class EasycodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasycodeApplication.class, args);
    }

    // 若有多个CommandLineRunner实现接口，会单线程串行执行，而不是多线程并行。
    // 不同于InitializingBean接口的afterproperties方法
    @Component
    public class CmdRun1 implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            Thread.currentThread().wait(5 * 1000);
            System.out.println("CmdRun1");
        }
    }

    @Component
    public class CmdRun2 implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            Thread.currentThread().wait(5 * 1000);
            System.out.println("CmdRun2");
        }
    }

}
