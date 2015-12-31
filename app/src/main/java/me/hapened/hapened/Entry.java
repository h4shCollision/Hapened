package me.hapened.hapened;

/**
 * Created by ellens on 12/21/15.
 */
public class Entry {
    private String title;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    Entry(String title,String text){
        this.title=title;
        this.text=text;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    Entry(){
        title="";
        text="";
    }

    public boolean isEmpty(){
        return (title==null||title.length()==0);//&&(text==null||text.length()==0);
    }

}
