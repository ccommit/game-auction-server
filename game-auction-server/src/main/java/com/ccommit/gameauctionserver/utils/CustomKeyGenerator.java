package com.ccommit.gameauctionserver.utils;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.lang.reflect.Method;

public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params)
    {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(method.getName());
        keyBuilder.append(SimpleKeyGenerator.generateKey(params));
        return keyBuilder.toString();
    }
}
