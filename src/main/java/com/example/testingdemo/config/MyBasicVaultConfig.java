package com.example.testingdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This is to obtain kv secret pairs from vault
 * the class is called *Config because kv pairs in vault are essentially configs
 * that are too sensitive to be kept in plaintext/version control
 */
@ConfigurationProperties("basic")
@Getter @Setter
public class MyBasicVaultConfig {

    private String username;
    private String password;

}
