package com.younchen.emoji.utils;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by younchen on 2016/6/7.
 */
public class FileUtil {

    private static final String EMOJI_PATH_FILE_NAME = "emoji";

    public static File getEmojiRootPath(Context context) {
        return new File(context.getFilesDir() + File.separator + EMOJI_PATH_FILE_NAME);
    }

    public static void removeDir(File dir) {
        if (dir.exists()) {
            String[] files = dir.list();
            for (String fileName : files) {
                String path  =dir.getAbsolutePath() + File.separator + fileName;
                File file = new File(path);
                if(file.isDirectory()){
                    removeDir(file);
                }
                file.delete();
            }
            dir.delete();
        }
    }

//    public static String getDefaultEmojiPath(Context context){
//        return context.getFilesDir() + File.separator + DEFAULT_FILE_NAME;
//    }
//
//    public static boolean makeDefaultPath(Context context){
//        File file = new File(context.getFilesDir() + File.separator + DEFAULT_FILE_NAME);
//       return file.mkdir();
//    }

//    public static boolean isDefultEmojiExsit(Context context){
//        return new File(getDefaultEmojiPath(context)).exists();
//    }
//
//    public static boolean isDefultEmojiEmpty(Context context){
//        String path = getDefaultEmojiPath(context);
//        File file = new File(path);
//        return file.list().length == 0;
//    }


    //unzip(new File("/sdcard/pictures.zip"), new File("/sdcard"));
    public static void unzip(File zipFile, File targetDirectory) throws IOException {

        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }

    public static void unzip(InputStream in, File targetDirectory) throws IOException {

        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(in));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }

    public static boolean isEmptyDir(File emojiRootDir) {
        return emojiRootDir.list().length == 0;
    }
}
