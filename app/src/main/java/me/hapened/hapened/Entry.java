package me.hapened.hapened;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
    private String title;
    private String text,date,loc;
    private int image=0;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    Entry(String title,String text){
        this(title,text,(new SimpleDateFormat("EEE, MMM d, yyyy")).format(new Date()));
    }

    public Entry(String title, String text, String date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    Entry(){
        this("","");
    }

    public boolean isEmpty(){
        return (title==null||title.length()==0);//&&(text==null||text.length()==0);
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return title+"\n"+image+"\n"+loc+"\n"+date+"\n"+text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
