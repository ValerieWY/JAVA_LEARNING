package com.itheima;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {
    @Test
    // 循环10000次，获取10000个UUID
    public void test1() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(UUID.randomUUID().toString());
            // 3ac9136e-22c0-4333-a874-408fb19bd17f  32字符+4-
        }
    }
}

