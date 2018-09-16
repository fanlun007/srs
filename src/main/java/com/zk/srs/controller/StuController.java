package com.zk.srs.controller;

import com.alibaba.fastjson.JSON;
import com.zk.srs.bean.Storage;
import com.zk.srs.bean.Student;
import com.zk.srs.util.FileUtils;
import com.zk.srs.util.JsonFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class StuController {

    //获取全部列表
    @GetMapping("/stus")
    public String getAllStudent(Model model){
        String stu_jsons = FileUtils.readFile(Storage.STU_PATH);
        List<Student> studentList = JSON.parseArray(stu_jsons, Student.class);
        model.addAttribute("stu_list", studentList);
        return "list";
    }

    //转发到新增页面
    @GetMapping("/stu")
    public String addStudent(){
        return "edit";
    }

    //新增学生
    @PostMapping("/stu")
    @ResponseBody
    public Map addStu(Student newStu){
        System.out.println(newStu);
        Map<String, Object> resp = new HashMap<>();

        String stu_jsons = FileUtils.readFile(Storage.STU_PATH);
        List<Student> studentList = JSON.parseArray(stu_jsons, Student.class);
        for (Student stu : studentList){
            if (stu.getSno() == newStu.getSno()) {
                resp.put("code", 400);
                return resp;
            }
        }
        studentList.add(newStu);
        String jsonAdd = JSON.toJSONString(studentList);
        FileUtils.writeFile(Storage.STU_PATH, jsonAdd);
        resp.put("code", 200);
        return resp;
    }

    //删除学生
    @DeleteMapping("/stu/{sno}")
    public String deleteStu(@PathVariable("sno") Integer sno){
        String stu_jsons = FileUtils.readFile(Storage.STU_PATH);
        List<Student> studentList = JSON.parseArray(stu_jsons, Student.class);
        for (int i = 0; i<studentList.size(); i++){
            if (studentList.get(i).getSno() == sno) {
                studentList.remove(i);
            }
        }
        String deleteJson = JSON.toJSONString(studentList);
        FileUtils.writeFile(Storage.STU_PATH, deleteJson);
        return "redirect:/stus";
    }

    @GetMapping("/stu/{sno}")
    public String edit(@PathVariable("sno") Integer sno, Model model){
        String stu_jsons = FileUtils.readFile(Storage.STU_PATH);
        List<Student> studentList = JSON.parseArray(stu_jsons, Student.class);
        Student edit_stu = null;
        for (Student stu : studentList){
            if (stu.getSno() == sno) {
                edit_stu = stu;
            }
        }
        model.addAttribute("editStu", edit_stu);
        return "edit";
    }

    @PutMapping("/stu")
    public String modifyStu(Student student){
        String stu_jsons = FileUtils.readFile(Storage.STU_PATH);
        List<Student> studentList = JSON.parseArray(stu_jsons, Student.class);

        for (int i = 0; i< studentList.size(); i++){
            if (studentList.get(i).getSno() == student.getSno()) {
                studentList.remove(i);
                studentList.add(i, student);
            }
        }
        String jsonUpdate = JSON.toJSONString(studentList);
        FileUtils.writeFile(Storage.STU_PATH, JsonFormatUtils.formatJson(jsonUpdate));

        return "redirect:/stus";
    }
}
