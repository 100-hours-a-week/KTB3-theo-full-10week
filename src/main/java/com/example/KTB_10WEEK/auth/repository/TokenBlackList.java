package com.example.KTB_10WEEK.auth.repository;


import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlackList {
    private Set<String> blackList = Collections.synchronizedSet(new HashSet<>());

    public TokenBlackList() {
    }

    public Set<String> getTable() {
        return blackList;
    }
}
