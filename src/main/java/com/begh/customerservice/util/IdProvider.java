package com.begh.customerservice.util;

import java.util.UUID;
public class IdProvider {
    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
