package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.Stu;

public interface IStuService {

    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);

    void saveParent();

    void saveChildren();
}
