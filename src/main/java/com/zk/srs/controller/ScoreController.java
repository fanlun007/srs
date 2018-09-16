package com.zk.srs.controller;

import com.alibaba.fastjson.JSON;
import com.zk.srs.bean.Score;
import com.zk.srs.bean.Storage;
import com.zk.srs.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ScoreController {

    @GetMapping("/scoreSelf/{sno}")
    public String toscoreListPage(@PathVariable("sno") Integer sno, Model score_list){
        String scos_json = FileUtils.readFile(Storage.SCORE_PATH);
        List<Score> allScore = JSON.parseArray(scos_json, Score.class);

        List<Score> scoWithSno = new ArrayList<>();
        for (Score score : allScore){
            if (score.getSno() == sno) {
                scoWithSno.add(score);
            }
        }
        score_list.addAttribute("score_list", scoWithSno);
        return "scoreList";
    }

    @GetMapping("/editScore")
    public void editScore(HttpServletRequest request){
        String sno = request.getParameter("sno");
        String cn = request.getParameter("cn");

    }

}
