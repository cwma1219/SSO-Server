package org.example.cas;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class CasApplication {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password","gj392p4fmejsirhg32d67");
        SpringApplication.run(CasApplication.class, args);
        // mvn package -DskipTests 若不跳過則需打開DB連線進行測試 測試完後才會打包
    }

}
