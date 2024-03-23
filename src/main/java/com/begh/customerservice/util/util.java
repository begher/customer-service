package com.begh.customerservice.util;

import java.util.UUID;

public class util {
    public String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
