package com.younchen.chat.model;

import android.view.View;

import com.younchen.chat.App;
import com.younchen.utils.FileUtil;

import junit.framework.Assert;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by pc on 2016/2/14.
 */
public class TestFileModel {

    private final String root = App.getIns().getFilesDir() + "/temp";
    private File dir;


    public void onCreate() {
        String fileCodes[] = {"wc83726172.bin", "wc28376723.bin", "wc11122234.bin", "wc77732881.bin", "wc99203030.bin", "wc93827261.bin", "wc12233333.bin", "wc44556664.bin", "wc421244487.bin", "wc00938377.bin"};

        dir = new File(root);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            File temp = new File(root, fileCodes[i]);
//            if (i % 2 == 0) {
//                temp = new File(root, "myfiles" + String.valueOf(System.currentTimeMillis()));
//            } else {
//                temp = new File(root, "heheda");
//            }
            createFile(temp);
        }
    }

    public File[] getFiles() {
        File file = new File(root);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.startsWith("wc") && filename.endsWith(".bin");
            }
        });

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                return lhs.lastModified() > rhs.lastModified() ? 1 : -1;
            }
        });
        return files;
    }

    ;


    public void onDestory() {
        FileUtil.deleteFileTree(dir);
    }


    private void createFile(File file) {
        try {
            if (dir.exists()) {
                file.createNewFile();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
