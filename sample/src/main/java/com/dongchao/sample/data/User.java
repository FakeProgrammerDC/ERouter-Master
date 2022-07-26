package com.dongchao.sample.data;

import java.io.Serializable;


public class User implements Serializable {
    public String name;

    public User(String name) {
        this.name = name;
    }
}