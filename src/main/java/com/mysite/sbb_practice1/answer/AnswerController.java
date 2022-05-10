package com.mysite.sbb_practice1.answer;

import com.mysite.sbb_practice1.question.Question;
import com.mysite.sbb_practice1.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @PostMapping("/create/{id}") //question_detail의 form태그에서 answer/create/id값으로 날려 주기 때문에 이에맞는 url매핑을 해준다.
    public String showCreate(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult){  //textarea의 name속성명이 content이며 이를 받기 위해 사용하였다 --> RequestParm

        Question question = questionService.getQuestion(id); //해당 질문에 대한 정보를 가지고 온다

        if(bindingResult.hasErrors()){
            model.addAttribute("question", question); //리턴될때 정보가 필요하기 때문에 보내준다.
            return "question_detail";
        }

        answerService.create(question, answerForm.getContent()); // question_detail에서 질문 등록을 누르면 최종적으로 답변의 저장이 된다

        return String.format("redirect:/question/detail/%s", id);
    }





}
