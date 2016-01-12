package me.hapened.hapened;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ellens on 12/21/15.
 */
public class Entry {
    private String title;
    private String text,date;
    private boolean image=false;

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
        System.out.println("date"+date);
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
        return title+"\n"+image+"\n"+date+"\n"+text;
    }
}
