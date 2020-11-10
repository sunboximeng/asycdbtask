package com.huawei.mutidatasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("nr_user_manager")
public class TestUser {
    private int id;
    private String colA;
    private double colC;

    public TestUser(int id, String colA, double colC) {
        this.id = id;
        this.colA = colA;
        this.colC = colC;
    }
}
