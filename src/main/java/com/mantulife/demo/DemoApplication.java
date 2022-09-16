package com.mantulife.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 程序启动类
 *
 * @author W_wang
 */
//开启缓存
@EnableCaching
@Slf4j
@SpringBootApplication
@EnableFeignClients  // 启用Feign功能
@EnableDiscoveryClient // 开启nacos服务发现
//设置包扫描规则（可以扫描以com.mantulife 开头的包）
@ComponentScan(basePackages = {"com.mantulife.common", "com.mantulife.demo"})
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(DemoApplication.class, args);
        showVisitInfo(application);
    }

    /**
     * 打印应用访问信息
     *
     * @param application
     */
    private static void showVisitInfo(ConfigurableApplicationContext application) {
        Environment env = application.getEnvironment();
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("========>启动时显示访问路径错误:{}", e.toString());
        }
        String context_path = env.getProperty("server.servlet.context-path");
        String server_port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! " +
                        "Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}{}\n\t" +
                        "External: \thttp://{}:{}{}\n\t" +
                        "Doc: \thttp://{}:{}{}/doc.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                server_port,
                context_path,
                host,
                server_port,
                context_path,
                host,
                server_port,
                context_path);
    }

}
