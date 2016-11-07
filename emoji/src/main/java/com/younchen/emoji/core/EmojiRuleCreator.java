package com.younchen.emoji.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by mryou on 2016/7/14.
 */

public class EmojiRuleCreator {

    public static HashMap<String, String> createRuleMap(String configureFileName) {
        HashMap<String, String> ruleMap = new HashMap<>();
        try{
            File file = new File(configureFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String content;
            while ((content = reader.readLine()) != null) {
                String[] str = content.split(",");
                String fileName = str[0];
                String value = str[1];
                ruleMap.put(fileName, value);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ruleMap;
    }


}
