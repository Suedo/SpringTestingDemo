package com.example.testingdemo;

import com.example.testingdemo.config.MyBasicVaultConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultTransitContext;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableConfigurationProperties(MyBasicVaultConfig.class)
@Slf4j
public class TestingdemoApplication implements CommandLineRunner {

    @Autowired
    VaultTemplate vaultTemplate;

    private final MyBasicVaultConfig secretsFromVault;

    public TestingdemoApplication(MyBasicVaultConfig secretsFromVault) {this.secretsFromVault = secretsFromVault;}

    public static void main(String[] args) {
        SpringApplication.run(TestingdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        byte[] vaultContext = "myRandomVaultContext".getBytes(StandardCharsets.UTF_8);
        VaultTransitContext vaultTransitContext = VaultTransitContext.fromContext(vaultContext);
        VaultTransitOperations vaultTransitOperations = vaultTemplate.opsForTransit();
        String plainText = "somjit";

        log.info("--------- NORMAL ENCRYPT DECRYPT TEST ------------");

        // normal transit key: orders
        String normalKey = "orders";
        String cipherText = vaultTransitOperations.encrypt(normalKey, plainText);
        log.info("first encrypt: ciphertext: {}", cipherText);
        log.info("second encrypt: ciphertext: {}", vaultTransitOperations.encrypt(normalKey, plainText));
        log.info("first encrypt: ciphertext: {}", vaultTransitOperations.encrypt(normalKey, plainText));
        // ^^ idea is that all of these ciphertexts will be different

        String decryptedText = vaultTransitOperations.decrypt(normalKey, cipherText);
        log.info("plaintext: {}, decrypted: {} << these should be same", plainText, decryptedText);


        log.info("--------- CONVERGENT ENCRYPT DECRYPT TEST ------------");

        // convergent transit key
        String convergentKey = "orders-convergent";
        cipherText = vaultTransitOperations.encrypt(convergentKey,
                                                    plainText.getBytes(StandardCharsets.UTF_8),
                                                    vaultTransitContext);
        log.info("first encrypt: ciphertext: {}", cipherText);
        log.info("second encrypt: ciphertext: {}",
                 vaultTransitOperations.encrypt(convergentKey,
                                                plainText.getBytes(StandardCharsets.UTF_8),
                                                vaultTransitContext));
        log.info("first encrypt: ciphertext: {}",
                 vaultTransitOperations.encrypt(convergentKey,
                                                plainText.getBytes(StandardCharsets.UTF_8),
                                                vaultTransitContext));
        // ^^ idea is that all of these ciphertexts will be SAME

        // THIS is NOT working !! i dunno why
        // the ciphertext when decrypted from vaultUI gives proper value
        decryptedText = vaultTransitOperations.decrypt(convergentKey, cipherText, vaultTransitContext).toString();
        log.info("plaintext: {}, decrypted: {} << these should be same", plainText, decryptedText);

        log.info("Command line runner starting ...");
        String username = secretsFromVault.getUsername();
        String password = secretsFromVault.getPassword();
        log.info("secrets obtained: username: {} , password: {}", username, password);
    }
}
