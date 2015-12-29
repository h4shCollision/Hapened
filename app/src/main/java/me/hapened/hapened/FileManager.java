package me.hapened.hapened;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellens on 10/12/15.
 */
public class FileManager {
    private static FileManager instance;
    private List<Integer> filenames = null;
    private List<String> titles = new ArrayList<>();
    private static String fileName = "filename.txt";//list of all files
    private int numEntries = 0;
    private File mainFile;
    private Context con;

    public static synchronized FileManager getInstance() {
        return instance;
    }

    private FileManager(Context con) {
        this.con =con;
        mainFile = new File(con.getFilesDir(), fileName);
        try {
            if (!mainFile.exists()) {
                mainFile.createNewFile();
                PrintWriter writer = new PrintWriter(new FileWriter(mainFile));
                writer.println(0);
                filenames = new ArrayList<>();
                filenames.add(0);
                writer.close();
                System.out.println("DNE");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(mainFile));
                numEntries = Integer.parseInt(br.readLine());
                filenames = new ArrayList<>(numEntries);
                System.out.println("nume" + numEntries);
                for (int i = 0; i < numEntries; i++) {
                    filenames.add(Integer.parseInt(br.readLine()));
                    BufferedReader individualBr = new BufferedReader(new FileReader((new File(con.getFilesDir(), filenames.get(i).toString()))));
                    titles.add(individualBr.readLine());
                }
                filenames.add(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setInstance(Context c) {
        System.out.println("fminit");
        instance=new FileManager(c);
    }

    // get the ith entry
    public Entry getItem(int index) {
        String text = "", title = "";
        System.out.println(index);
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(con.getFilesDir(), filenames.get(index).toString())));
            title = br.readLine();
            System.out.println("title" + title);
            String line = null;
            text = "";
            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }
            if (text.length() >= 1) {
                text = text.substring(0, text.length() - 1);
            }
            if (title == null) {
                title = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(title);
        Entry targetEntry = new Entry(title, text);
        return targetEntry;
    }

    public void addItem() {

        int num = filenames.get(0) + 1;
        File newFile = new File(con.getFilesDir(), Integer.toString(num));
        try {
            newFile.createNewFile();
            filenames.add(0, num);
            titles.add(0, "");
            numEntries += 1;
            writeMainFile();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public void deleteItem(int index) {
        con.deleteFile(Integer.toString(filenames.get(index)));
        filenames.remove(index);
        titles.remove(index);
        numEntries -= 1;
        writeMainFile();
    }


    public void setItem(int index, Entry newEntry) {
        /*if(newEntry==null||newEntry.isEmpty()){
            deleteItem(index);
            return;
        }*/
        titles.set(index, newEntry.getTitle());
        try {
            PrintWriter writer = new PrintWriter(new File(con.getFilesDir(), filenames.get(index).toString()));
            writer.println(newEntry.getTitle());
            writer.println(newEntry.getText());
            writer.close();
            writeMainFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTitles() {
        return titles;
    }

    public void save() {

    }

    public void checkEmpty(int index) {
        if (getItem(index).isEmpty()) {
            deleteItem(index);
        }
    }

    private void writeMainFile(){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(mainFile);
            writer.println(numEntries);
            for (int i = 0; i < filenames.size() - 1; i++) {
                writer.println(filenames.get(i));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
