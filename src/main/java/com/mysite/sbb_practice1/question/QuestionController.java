package com.mysite.sbb_practice1.question;

import com.mysite.sbb_practice1.answer.AnswerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired //의존성 주입!!!
    private QuestionService questionService;


    @RequestMapping("/list")
    public String showList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging); // 기존 url + ?page=2 ==> 2번 페이지가 나온다
        return "question_list";
        //question/list url에 대해 매핑하는 컨트롤러이다.

    }


    @RequestMapping(value = "/detail/{id}")
    //변하는 값을 얻기 위해서는 pqthvariable을 사용해 주어야 한다. 이때 pathvariable의 매개변수의 이름을 동일하게 만들어 주어야 한다
    //question_list에서 제목을 눌렀을때 detail로 상세보기로 보내기 때문에 해당 URL에 대한 매핑을 만들어 주는 작업을 해주어야 한다.
    public String showDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }


    @GetMapping("/create")
    public String showCreate(QuestionForm questionForm){

        return "question_form";
    }

    @PostMapping("/create")
    public String  showCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
    //vaild어노테이션을 사용해야 QuestionForm에서 설정한 설정 검증을 할 수 있다.
        //BindingResult의 경우 Vaild에 의해 이루어진 검증 절차를 의미하는 것이다.
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        questionService.Create(questionForm.getSubject(), questionForm.getContent());

        return "redirect:/question/list";
    }





}
