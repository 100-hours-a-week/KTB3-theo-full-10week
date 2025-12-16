package com.todayseafood.api.auth.service.encryption;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class HmacSHA256 implements Encrypt {
    private String algorithm = "HmacSHA256";
    private Charset encoding = StandardCharsets.UTF_8;

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public byte[] encrypt(String target, String secretKey) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secretKey.getBytes(encoding), algorithm));
            return mac.doFinal(target.getBytes(encoding));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
