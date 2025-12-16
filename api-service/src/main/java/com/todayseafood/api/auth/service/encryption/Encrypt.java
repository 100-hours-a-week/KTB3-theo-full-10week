package com.todayseafood.api.auth.service.encryption;

public interface Encrypt {

    String getAlgorithm();

    byte[] encrypt(String target, String secretKey);
}
