package com.mysite.sbb_practice1.answer;

import com.mysite.sbb_practice1.question.Question;
import com.mysite.sbb_practice1.question.QuestionService;
import com.mysite.sbb_practice1.user.SiteUser;
import com.mysite.sbb_practice1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;


    @PreAuthorize("isAuthenticated()") // ==>로그인이 필요한 기능을 실행하면 로그인 페이지로 이동한다.
    @PostMapping("/create/{id}") //question_detail의 form태그에서 answer/create/id값으로 날려 주기 때문에 이에맞는 url매핑을 해준다.
    public String showCreate(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
                             BindingResult bindingResult, Principal principal){  //textarea의 name속성명이 content이며 이를 받기 위해 사용하였다 --> RequestParm
        //현재 로그인한 사용자의 정보를 얻기 위해서는 pricipal을 사용한다
        Question question = questionService.getQuestion(id); //해당 질문에 대한 정보를 가지고 온다
        SiteUser user = userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question); //리턴될때 정보가 필요하기 때문에 보내준다.
            return "question_detail";
        }

        answerService.create(question, answerForm.getContent(), user); // question_detail에서 질문 등록을 누르면 최종적으로 답변의 저장이 된다

        return String.format("redirect:/question/detail/%s", id);
    }


    @PreAuthorize("isAuthenticated")
    @GetMapping("/modify/{id}")
    public String showModify(Principal principal, @PathVariable("id")  Integer id, AnswerForm answerForm){
        Answer a = answerService.getAnswer(id);
        if(!a.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다!!");
        }
        answerForm.setContent(answerForm.getContent());
        return "answer_form";
    }




}
