package me.hapened.hapened;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static synchronized FileManager getInstance() {
        return instance;
    }

    private FileManager(Context con) {
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
        //System.out.println("fminit");
        instance = new FileManager(c);
    }

    // get the ith entry
    public Entry getItem(Context con, int index) {
        String text = "", title = "", date = "", image = "",loc;
        System.out.println(index);
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(con.getFilesDir(), filenames.get(index).toString())));
            title = br.readLine();
            image = br.readLine();
            loc=br.readLine();//future features
            System.out.println("image" + image);
            date = br.readLine();
            //String line;
            //text = "";
            StringBuilder sb = new StringBuilder();
            int line = br.read();
            while (line != -1) {
                sb.append((char) (line));
                line = br.read();
            }
            if (title == null) {
                title = "";
            }
            text = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(title);
        Entry targetEntry = new Entry(title, text, date);
        try {
            targetEntry.setImage(Integer.parseInt(image));
        }catch (Exception e){

        }
        return targetEntry;
    }

    public void addItem(Context con) {
        int num = filenames.get(0) + 1;
        File newFile = new File(con.getFilesDir(), Integer.toString(num));
        try {
            newFile.createNewFile();
            filenames.add(0, num);
            titles.add(0, "");
            numEntries += 1;
            writeFile(con, 0, new Entry());
            writeMainFile();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public void deleteItem(Context con, int index) {
        Entry e=getItem(con,index);
        int n=e.getImage();
        String filepref = filenames.get(index) + "_", filepostf = ".png";
        for (int i = 0; i < n; i++) {
            String name = filepref + i+filepostf;
            con.deleteFile(name);
        }
        con.deleteFile(Integer.toString(filenames.get(index)));
        filenames.remove(index);
        titles.remove(index);
        numEntries -= 1;
        writeMainFile();
    }


    public void setItem(Context con, int index, Entry newEntry) {
        titles.set(index, newEntry.getTitle());
        writeFile(con, index, newEntry);
        writeMainFile();
    }

    public List<String> getTitles() {
        return titles;
    }

    public void save() {

    }

    public ArrayList<Bitmap> loadImages(Context con, Entry newEntry, int index) {
        String filepref = filenames.get(index) + "_", filepostf = ".png";
        ArrayList<Bitmap> l = new ArrayList<>();
        int len = newEntry.getImage();
        System.out.println(len);
        for (int i = 0; i < len; i++) {
            String name = filepref + i+filepostf;
            FileInputStream fis;
            Bitmap b = null;
            try {
                fis = con.openFileInput(name);
                b = BitmapFactory.decodeStream(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (b != null) {
                l.add(b);
            }
        }
        newEntry.setImage(l.size());
        return l;
    }

    public void saveImages(Context con, Entry newEntry, int index, ArrayList<Bitmap> l) {
        String filepref = filenames.get(index) + "_", filepostf = ".png";
        for (int i = 0; i < l.size(); i++) {
            String name = filepref + i+filepostf;
            System.out.println("imagename "+name);
            FileOutputStream fos;
            try {
                fos = con.openFileOutput(name, Context.MODE_PRIVATE);
                l.get(i).compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkEmpty(Context con, int index) {
        if (getItem(con, index).isEmpty()) {
            deleteItem(con, index);
        }
    }

    private void writeMainFile() {
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

    private void writeFile(Context con, int index, Entry ent) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(con.getFilesDir(), filenames.get(index).toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print(ent.toString());
        writer.close();
    }
}
