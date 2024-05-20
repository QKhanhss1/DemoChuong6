package com.example.demochuong6;

import java.io.Serializable;

public class Sound implements Serializable {
    private String name;
    private int resoucre;

    public Sound(String name, int resoucre) {
        this.name = name;
        this.resoucre = resoucre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResoucre() {
        return resoucre;
    }

    public void setResoucre(int resoucre) {
        this.resoucre = resoucre;
    }
}
