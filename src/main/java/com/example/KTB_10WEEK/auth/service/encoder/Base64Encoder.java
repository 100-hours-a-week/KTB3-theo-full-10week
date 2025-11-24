package com.example.KTB_10WEEK.auth.service.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class Base64Encoder implements Encoder {
    private final Base64.Encoder encoder =  Base64.getUrlEncoder().withoutPadding();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encodeJson(Map<String, Object> toByte) {
        try {
            byte[] json = objectMapper.writeValueAsBytes(toByte);
            return encoder.encodeToString(json);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String encodeToString(byte[] target) {
        return encoder.encodeToString(target);
    }
}
