package com.mysite.sbb_practice1.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired //의존성 주입!!!
    private QuestionRepository questionRepository;


    @RequestMapping("/list")
    public String showList(Model model){
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "question_list";
        //question/list url에 대해 매핑하는 컨트롤러이다.
    }

}
