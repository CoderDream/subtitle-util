package com.coderdream.subtitleutil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author CoderDream
 */
//@SpringBootApplication
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SubtitleUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubtitleUtilApplication.class, args);
    }

}
