package com.qiyei.foodie.api.controller;

import com.qiyei.foodie.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

//@Controller
@ApiIgnore
@RestController
public class StuFooController {

    @Autowired
    private IStuService mStuService;

    @GetMapping("/getStu")
    public Object getStu(int id) {
        return mStuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu() {
        mStuService.saveStu();
        return "OK";
    }

    @PostMapping("/updateStu")
    public Object updateStu(int id) {
        mStuService.updateStu(id);
        return "OK";
    }

    @PostMapping("/deleteStu")
    public Object deleteStu(int id) {
        mStuService.deleteStu(id);
        return "OK";
    }

}
