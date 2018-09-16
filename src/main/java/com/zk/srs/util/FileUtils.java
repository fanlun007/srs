package com.zk.srs.util;

import java.io.*;

public class FileUtils {

    public static String readFile(String filePath) {
        File file = new File(filePath);
        FileReader fileReader = null;
        StringBuffer stringBuffer = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String tempstr = null;
            while ((tempstr = br.readLine()) != null) {
                stringBuffer.append(tempstr);
            }
            br.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static void writeFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            FileWriter fw = null;
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(JsonFormatUtils.formatJson(content));
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
