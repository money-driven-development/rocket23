package com.initcloud.rocket23.example.service.Impl;

import com.initcloud.rocket23.example.service.ExampleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExampleServiceImpl implements ExampleService {
    @Override
    public Map<String, Object> getFirstData() {

        Map<String, Object> firstData = new HashMap<>();

        firstData.put("label1","check1");
        firstData.put("label2","check2");
        firstData.put("label3","check3");

        return firstData;
    }
}
