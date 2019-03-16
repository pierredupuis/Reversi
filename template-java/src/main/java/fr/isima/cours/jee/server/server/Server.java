
package fr.isima.cours.jee.server.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication

@EnableAutoConfiguration
@ComponentScan
@ServletComponentScan("fr.isima")
public class Server {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Server.class)
                .run(args);
    }
}