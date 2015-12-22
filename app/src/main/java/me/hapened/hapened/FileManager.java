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
    private List<Integer> filenames = null;
    private List<String> titles = new ArrayList<>();
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
        boolean empty = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(mainFile));
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
        try {
            if (empty == true) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(mainFile));
                writer.write(numEntries);
                filenames = new ArrayList<>(numEntries);
                filenames.add(0);

            } else {
                BufferedReader br = new BufferedReader(new FileReader(mainFile));
                numEntries = Integer.parseInt(br.readLine());
                //filenames = new ArrayList<>(numEntries);
                for (int i = 0; i < numEntries; i++) {
                    filenames.set(i, Integer.parseInt(br.readLine()));
                    BufferedReader individualBr = new BufferedReader(new FileReader((new File(con.getFilesDir(), filenames.get(i).toString()))));
                    titles.add(br.readLine());
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
        System.out.println(index);
        boolean empty = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(con.getFilesDir(), filenames.get(index).toString())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = null;
        String title = null;
        BufferedReader br = null;
        try {
            System.out.println("gti"+index);
            System.out.println("gtfn"+filenames.get(index).toString());
            br = new BufferedReader(new FileReader(new File(con.getFilesDir(), filenames.get(index).toString())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            title = br.readLine();
            System.out.println("title"+title);
            String line = null;
            text = "";
            while ((line = br.readLine()) != null) {
                text+="\n"+ line;
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(title);
        Entry targetEntry = new Entry(title, text);
        return targetEntry;

    }

    public void addItem(Context con, int index) {

        int num = filenames.get(0) + 1;
        File newFile = new File(con.getFilesDir(), Integer.toString(num));
        filenames.add(0, num);
        titles.add("");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(mainFile));
            writer.write(numEntries + 1);
            for (int i = 0; i < filenames.size(); i++) {
                writer.write(filenames.get(i));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public void deleteItem(Context con, int index) {
        con.deleteFile(Integer.toString(filenames.get(index)));
        filenames.remove(index);
        titles.remove(index);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(mainFile));
            writer.write(numEntries - 1);
            for (int i = 0; i < filenames.size(); i++) {
                writer.write(filenames.get(i));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    public void setItem(Context con, int index, Entry newEntry) {
        titles.set(index, newEntry.getTitle());
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(con.getFilesDir(), filenames.get(index).toString())));
            writer.write(newEntry.getTitle()+"\n");
            writer.write(newEntry.getText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTitles(Context con) {
        return titles;
    }

    public void save() {

    }
}
