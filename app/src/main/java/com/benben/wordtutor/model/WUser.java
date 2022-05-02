package com.benben.wordtutor.model;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

public class WUser extends BmobUser implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
