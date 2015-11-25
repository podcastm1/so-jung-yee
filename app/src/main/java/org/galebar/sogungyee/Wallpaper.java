package org.galebar.sogungyee;

import android.graphics.Bitmap;

/**
 * Created by Code on 2015-07-23.
 */
public class Wallpaper {
    private String title;
    private String author;
    private Bitmap bitmap;

    public Wallpaper(String title, String author, Bitmap img) {
        this.title = title;
        this.author = author;
        bitmap = img;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getTitle() {
        return title;
    }
}
