package me.hapened.hapened;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellens on 10/12/15.
 */
public class FileManager {
    private static FileManager instance;
    private List<String> titles=new ArrayList();

    public static synchronized FileManager getInstance(){
        if (instance==null){
            instance=new FileManager();
        }
        return instance;
    }

    private FileManager(){
        //get list
        
    }

    public void getItem(int index){

    }

    public void addItem(int index,String s){

    }

    public void setItem(int index,String s){

    }

    public void save(){

    }

}
