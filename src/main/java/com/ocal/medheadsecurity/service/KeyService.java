package com.ocal.medheadsecurity.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

@Service
public class KeyService {

    @Value("${key.location}")
    private Resource resource;

    public SecretKey readSecretKeyFromFile() {
        SecretKey key = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
            key = new SecretKeySpec(keyBytes, "HmacSHA512");
        } catch (IOException e) {
            System.out.println("Key file couldn't be found ! ");
        }
        return key;
    }
}
