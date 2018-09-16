package com.zk.srs.bean;

import lombok.Data;

@Data
public class Student {
    private int sno;
    private  String sn;
    private String dormNum;
    private String phone;

    public Student() {
    }

    public Student(int sno, String sn, String dormNum, String phone) {
        this.sno = sno;
        this.sn = sn;
        this.dormNum = dormNum;
        this.phone = phone;
    }
}
