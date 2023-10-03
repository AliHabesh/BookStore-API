package com.example.bookstore.uid;

import java.util.UUID;

public class UIDGenerator {
    public static String generateUID(){
        return UUID.randomUUID().toString();
    }
}
