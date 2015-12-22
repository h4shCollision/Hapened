package me.hapened.hapened;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellens on 10/12/15.
 */
public class FileManager {
    private static FileManager instance;
    private List<Integer> filenames;
    private static String fileName = "filename.txt";//list of all files
    private int numEntries = 0;
    private File mainFile;

    public static synchronized FileManager getInstance(Context c) {
        if (instance == null) {
            instance = new FileManager(c);
        }
        return instance;
    }

    private FileManager(Context con) {
        mainFile = new File(con.getFilesDir(), fileName);
        try {
            if (mainFile.createNewFile()) {
                //thing to do the first time you access the file
            } else {
                BufferedReader br = new BufferedReader(new FileReader(mainFile));
                numEntries = Integer.parseInt(br.readLine());
                filenames = new ArrayList<>(numEntries);
                for (int i = 0; i < numEntries; i++) {
                    filenames.set(i, Integer.parseInt(br.readLine()));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new File(con.getFilesDir(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // get the ith entry
    public Entry getItem(Context con, int index) {
        boolean empty = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(con.getFilesDir(), filenames.get(index).toString())));
            if (br.readLine() == null) {
                empty = true;
            } else {
                empty = false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (empty == true) {
            return null;
        } else {
            String text = null;
            String title = null;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File(con.getFilesDir(), filenames.get(index).toString())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                title = br.readLine();
                String line = null;
                text = null;
                while ((line = br.readLine()) != null) {
                    text = text + line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Entry targetEntry = new Entry(title, text);
            return targetEntry;
        }
    }

    public void addItem(Context con, int index, Entry newEntry) {
        if (!(newEntry.equals(null))) {
            int num = filenames.get(0) + 1;
            File newFile = new File(con.getFilesDir(), Integer.toString(num));
            filenames.add(0, num);
            try {
                BufferedWriter writer =new BufferedWriter(new FileWriter(mainFile));
                writer.write(numEntries+1);
                for (int i = 0; i < filenames.size(); i++) {
                    writer.write(filenames.get(i));
                }
                }catch(IOException x){
                    System.err.format("IOException: %s%n", x);
                }
            }
        }

    public void deleteItem(Context con, int index) {
       con.deleteFile(Integer.toString(filenames.get(index)));
        filenames.remove(index);
        try {
                BufferedWriter writer =new BufferedWriter(new FileWriter(mainFile));
                writer.write(numEntries-1);
                for (int i = 0; i < filenames.size(); i++) {
                    writer.write(filenames.get(i));
                }
            }catch(IOException x){
                System.err.format("IOException: %s%n", x);
            }
        }


    public void setItem(Context con, int index, Entry newEntry) {
        try {
            BufferedWriter writer =new BufferedWriter(new FileWriter(new File(con.getFilesDir(), filenames.get(index).toString())));
            writer.write(newEntry.getTitle());
            writer.write(newEntry.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {

    }
}
