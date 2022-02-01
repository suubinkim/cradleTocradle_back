package site.cradle.cradle_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CradleTocradleBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CradleTocradleBackApplication.class, args);
    }

}
