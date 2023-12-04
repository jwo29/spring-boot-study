package org.zerock.ex4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 엔터티 리스너를 사용하기 위해 선언한다.
public class Ex4Application {

    public static void main(String[] args) {
        SpringApplication.run(Ex4Application.class, args);
    }

}
