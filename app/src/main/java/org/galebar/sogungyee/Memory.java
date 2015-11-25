package org.galebar.sogungyee;

/**
 * Created by CodeRi on 2015. 7. 25..
 */
public class Memory {
    String title;
    String when;
    String where;
    String what;
    String why;
    String who;
    String how;
    String imgURL;

    public Memory() {}

    public Memory(String title, String imgURL) {
        this.title = title;
        this.imgURL = imgURL;
    }

    public Memory(String title, String when, String where, String what, String why, String who, String how, String imgURL) {
        this.title = title;
        this.when = when;
        this.where = where;
        this.what = what;
        this.why = why;
        this.who = who;
        this.how = how;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public String getWhen() {
        return when;
    }

    public String getWhere() {
        return where;
    }

    public String getWhat() {
        return what;
    }

    public String getWhy() {
        return why;
    }

    public String getWho() {
        return who;
    }

    public String getHow() {
        return how;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
