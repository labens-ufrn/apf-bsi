package br.ufrn.dct.apf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("br.ufrn.dct.apf")
public class APFApplication {

    public static void main(String[] args) {
        SpringApplication.run(APFApplication.class, args);
    }
}
