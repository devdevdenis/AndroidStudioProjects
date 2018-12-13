package com.example.denis.varyag;

import com.dbflow5.annotation.Column;
import com.dbflow5.annotation.PrimaryKey;
import com.dbflow5.annotation.Table;
import com.dbflow5.annotation.Unique;

@Table(database = AppDatabase.class)
public class EventModel {

    @PrimaryKey(autoincrement = true)
    long id; // package-private recommended, not required

    @Column
    private String title; // private with getters and setters

    @Column
    private String img;

    @Column
    private String define;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public String getDefine() {
        return define;
    }
    public void setDefine(String define) {
        this.define = define;
    }
}
