package com.example.demo;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RoutesController {

    @Resource
    ConnectsHolder connectsHolder;

    @PostMapping("/uris")
    public Object updateUris(@RequestBody ArrayList<String> uris) {
        connectsHolder.setUris(uris);

        return "ok";
    }
}
