package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ConnectsHolder {

    private ArrayList<String> uris = new ArrayList<>();

    public void setUris(ArrayList<String> uris) {
        this.uris = uris;
    }

    public ArrayList<String> getUris() {
        return uris;
    }
}
