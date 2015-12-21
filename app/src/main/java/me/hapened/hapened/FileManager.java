package me.hapened.hapened;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellens on 10/12/15.
 */
public class FileManager {
    private static FileManager instance;
    private List<String> titles=new ArrayList();
    private static String fileName="filename.txt";//list of all files

    public static synchronized FileManager getInstance(Context c){
        if (instance==null){
            instance=new FileManager(c);
        }
        return instance;
    }

    private FileManager(Context con){
        FileInputStream fis;
        //get list
        try{
            fis=con.openFileInput(fileName);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            new File(con.getFilesDir(),fileName);
        }

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
