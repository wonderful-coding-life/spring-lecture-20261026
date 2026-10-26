package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LombokApplication implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product appleWatch = new Product("Apple Watch", "Greatest smart watch", 800000);
        Product galaxyWatch = new Product();
        galaxyWatch.setName("Apple Watch");
        galaxyWatch.setDescription("Greatest smart watch");
        galaxyWatch.setPrice(800000);
        log.info("애플 = {}, 삼성 = {}", appleWatch, galaxyWatch);

        if (appleWatch.equals(galaxyWatch)) {
            log.info("same");
        } else {
            log.info("different");
        }

        Product xiaomiBand = Product.builder().name("샤이미밴드").price(77000).build();
        log.info("밴드 = " + xiaomiBand);

        log.trace("TRACE level log....");
        log.debug("DEBUG level log....");
        log.info("INFO level log....");
        log.warn("WARN level log....");
        log.error("ERROR level log....");
    }
}
