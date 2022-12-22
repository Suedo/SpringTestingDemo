package com.example.testingdemo;

import com.example.testingdemo.config.MyBasicVaultConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyBasicVaultConfig.class)
@Slf4j
public class TestingdemoApplication implements CommandLineRunner {

    private final MyBasicVaultConfig secretsFromVault;

    public TestingdemoApplication(MyBasicVaultConfig secretsFromVault) {this.secretsFromVault = secretsFromVault;}

    public static void main(String[] args) {
        SpringApplication.run(TestingdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Command line runner starting ...");
        String username = secretsFromVault.getUsername();
        String password = secretsFromVault.getPassword();
        log.info("secrets obtained: username: {} , password: {}", username, password);
    }
}
