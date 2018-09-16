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
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ScoreController {

    @GetMapping("/scoreSelf/{sno}")
    public String toscoreListPage(@PathVariable("sno") Integer sno, Model score_list) {
        String scos_json = FileUtils.readFile(Storage.SCORE_PATH);
        List<Score> allScore = JSON.parseArray(scos_json, Score.class);

        List<Score> scoWithSno = new ArrayList<>();
        for (Score score : allScore) {
            if (score.getSno() == sno) {
                scoWithSno.add(score);
            }
        }
        if (scoWithSno.size() != 0) {
            score_list.addAttribute("score_list", scoWithSno);
        }else{
            score_list.addAttribute("score_list", null);
        }
        return "scoreList";
    }

    @GetMapping("/editScore")
    public String editScore(HttpServletRequest request, Model model) {
        String sno = request.getParameter("sno");
        String cn = request.getParameter("cn");
        String scos_json = FileUtils.readFile(Storage.SCORE_PATH);
        List<Score> allScore = JSON.parseArray(scos_json, Score.class);
        Score edit_score = null;
        for (Score score : allScore) {
            if (score.getSno() == Integer.parseInt(sno) && score.getCn().equals(cn)) {
                edit_score = score;
            }
        }
        model.addAttribute("editScore", edit_score);
        return "editScorePage";
    }

    @PutMapping("/score")
    public String modifyScore(Score mdfScore){
        String scos_json = FileUtils.readFile(Storage.SCORE_PATH);
        List<Score> allScore = JSON.parseArray(scos_json, Score.class);

        for (int i = 0; i< allScore.size(); i++){
            if (allScore.get(i).getSno() == mdfScore.getSno() && allScore.get(i).getCn().equals(mdfScore.getCn())) {
                allScore.remove(i);
                allScore.add(i, mdfScore);
            }
        }
        String jsonUpdate = JSON.toJSONString(allScore);
        FileUtils.writeFile(Storage.SCORE_PATH, jsonUpdate);
        return "redirect:/scoreSelf/" + mdfScore.getSno();
    }

    @GetMapping("/deleteScore")
    public String deleteScore(HttpServletRequest request){
        String sno = request.getParameter("sno");
        String cn = request.getParameter("cn");
        String scos_json = FileUtils.readFile(Storage.SCORE_PATH);
        List<Score> allScore = JSON.parseArray(scos_json, Score.class);

        for (int i = 0; i<allScore.size();i++){
            if (allScore.get(i).getSno() == Integer.parseInt(sno) && allScore.get(i).getCn().equals(cn)) {
                allScore.remove(i);
            }
        }
        String deleteJson = JSON.toJSONString(allScore);
        FileUtils.writeFile(Storage.SCORE_PATH, deleteJson);
        return "redirect:/scoreSelf/" + sno;
    }
}
