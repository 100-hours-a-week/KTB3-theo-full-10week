package com.example.KTB_10WEEK.auth.service.encoder;

import java.util.Map;

public interface Encoder {

    public String encodeJson(Map<String, Object> toByte);

    public String encodeToString(byte[] target);
}
