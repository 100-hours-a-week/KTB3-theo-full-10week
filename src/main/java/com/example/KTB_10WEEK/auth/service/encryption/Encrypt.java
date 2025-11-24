package com.example.KTB_10WEEK.auth.service.encryption;

public interface Encrypt {

    String getAlgorithm();

    byte[] encrypt(String target, String secretKey);
}
