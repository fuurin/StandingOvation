package com.example.komatsu.standingovation;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by komatsu on 2016/11/20.
 */

public class CSVWriter {

    private static final String EXTENSION = ".csv";

    public static boolean write(Context context, String filename, java.util.List<List<String>> data) {
        if(filename == "" || data.isEmpty()) return false;
        boolean result = true;

        String path = Environment.getExternalStorageDirectory().getPath();
        path += "/" + filename + EXTENSION;

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bfw = null;

        try {
            fos = new FileOutputStream(path);
            osw = new OutputStreamWriter(fos);
            bfw = new BufferedWriter(osw);
            String csv = makeCSVFromData(data);
            bfw.write(csv);
        } catch (IOException e) {
            Log.d("CSVWriter", e.toString());
            result = false;
        } finally {
            if(bfw != null) {
                try {
                    bfw.flush();
                    bfw.close();
                    MediaScannerConnection.scanFile(context, new String[] {path}, null, null);
                } catch (IOException e) {
                    Log.d("CSVWriter", e.toString());
                }
            }

            if(osw != null) {
                try {
                    osw.close();
                }catch (IOException e){
                    Log.d("CSVWriter", e.toString());
                }
            }

            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.d("CSVWriter", e.toString());
                }
            }
        }

        return result;
    }

    private static String makeCSVFromData(List<List<String>> data) {
        StringBuilder csv = new StringBuilder();
        Iterator<List<String>> it = data.iterator();
        while(it.hasNext()) csv.append(makeCSVRow(it.next()));
        return new String(csv);
    }

    private static String makeCSVRow(List<String> data) {
        StringBuilder row = new StringBuilder();
        Iterator<String> it = data.iterator();
        while (it.hasNext()) {
            row.append(it.next());
            if(it.hasNext())row.append(",");
        }
        row.append(System.getProperty("line.separator"));
        return new String(row);
    }

    public boolean sameNameFileExists() {
        return false;
    }
}

