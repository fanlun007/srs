package com.zk.srs.bean;

import lombok.Data;

@Data
public class Score {
    private int sno;
    private String cn;
    private int grade;

    public Score() {
    }

    public Score(int sno, String cn, int grade) {
        this.sno = sno;
        this.cn = cn;
        this.grade = grade;
    }
}
