package com.zk.srs;

import com.alibaba.fastjson.JSON;
import com.zk.srs.bean.Score;
import com.zk.srs.bean.Student;
import com.zk.srs.util.FileUtils;
import com.zk.srs.util.JsonFormatUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonSerialize {

    @Test
    public void test() {
        String s = String.valueOf(new String[]{"a", "b"}[0]);
        System.out.println(s);
    }

    @Test
    public void testStudent() throws IOException {
        Student student1 = new Student(1, "fanlun", "竹九", "12345");
        Student student2 = new Student(2, "yyy", "竹八", "789876");

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        for (int i = 3; i < 7; i++) {
            studentList.add(new Student(i, "g" + i, ""+i, "1234"));
        }
        studentList.add(new Student(99, "guang", "竹九", "22233444"));
        String stuJsonArr = JSON.toJSONString(studentList);
        System.out.println(JsonFormatUtils.formatJson(stuJsonArr));

        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);
        File file = new File(rootPath + "\\src\\main\\resources\\static\\json\\students.json");

        if (file.exists()) {
//            file.delete();
//            file.createNewFile();
        } else {
            file.createNewFile();
        }

        FileWriter fw;
        try {
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(JsonFormatUtils.formatJson(stuJsonArr));
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testScore() {
        Score score1 = new Score(1, "语文", 100);
        Score score2 = new Score(2, "数学", 222);

        List<Score> scoreList = new ArrayList<>();
        scoreList.add(score1);
        scoreList.add(score2);
        String scoreJson = JSON.toJSONString(scoreList);
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\json\\scores.json";
        FileUtils.writeFile(filePath, JsonFormatUtils.formatJson(scoreJson));
    }

    @Test
    public void parseJson2Score() {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\json\\scores.json";
        String scoreJson = FileUtils.readFile(filePath);
        List<Score> scoreList = JSON.parseArray(scoreJson, Score.class);
        System.out.println(scoreList);
    }

    @Test
    public void parseJson2Entity() {
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\json\\students.json");
        System.out.println(file.getAbsolutePath());

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String tempstr = null;
            while ((tempstr = br.readLine()) != null) {
                stringBuffer.append(tempstr);
            }
            br.close();
            fileReader.close();

            List<Student> studentList = JSON.parseArray(stringBuffer.toString(), Student.class);
            System.out.println(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
