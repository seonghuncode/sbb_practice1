package com.mysite.sbb_practice1.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired //의존성 주입!!!
    private QuestionService questionService;


    @RequestMapping("/list")
    public String showList(Model model){
        List<Question> questions = questionService.getList();
        model.addAttribute("questions", questions);
        return "question_list";
        //question/list url에 대해 매핑하는 컨트롤러이다.
    }


    @RequestMapping(value = "/detail/{id}")
    //변하는 값을 얻기 위해서는 pqthvariable을 사용해 주어야 한다. 이때 pathvariable의 매개변수의 이름을 동일하게 만들어 주어야 한다
    //question_list에서 제목을 눌렀을때 detail로 상세보기로 보내기 때문에 해당 URL에 대한 매핑을 만들어 주는 작업을 해주어야 한다.
    public String showDetail(Model model, @PathVariable("id") Integer id){
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }






}
