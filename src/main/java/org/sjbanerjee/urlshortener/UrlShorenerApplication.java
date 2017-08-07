package org.sjbanerjee.urlshortener;

import org.sjbanerjee.urlshortener.service.UrlShortener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

import javax.persistence.PersistenceContext;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.sjbanerjee.urlshortener")
@ComponentScan(basePackages = "org.sjbanerjee.urlshortener")
@EnableCaching
public class UrlShorenerApplication {

    private final static Logger logger = LoggerFactory.getLogger(UrlShorenerApplication.class);
    public static ApplicationContext ctx;

    public static void main(String[] args) {

        ctx = SpringApplication.run(UrlShorenerApplication.class, args);
        logger.info("*************Started***************");
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
