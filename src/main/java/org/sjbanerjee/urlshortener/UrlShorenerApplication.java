package org.sjbanerjee.urlshortener;

import org.sjbanerjee.urlshortener.service.UrlShortener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class UrlShorenerApplication {

    private final static Logger logger = LoggerFactory.getLogger(UrlShorenerApplication.class);
    public static ApplicationContext ctx;

    public static void main(String[] args) {

        if(args.length < 1){
            return;
        }
        
        ctx = SpringApplication.run(UrlShorenerApplication.class, args);
        logger.info("*************Started***************");
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        //Get the Authentication Header
        UrlShortener shortener = (UrlShortener) ctx.getBean("urlShortener");
        shortener.shorten(args[0]);
    }

}
