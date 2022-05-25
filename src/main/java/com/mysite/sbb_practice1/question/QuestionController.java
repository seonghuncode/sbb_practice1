package com.mysite.sbb_practice1.question;

import com.mysite.sbb_practice1.answer.AnswerForm;
import com.mysite.sbb_practice1.user.SiteUser;
import com.mysite.sbb_practice1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;


@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired //의존성 주입!!!
    private QuestionService questionService;
    @Autowired
    private UserService userService;


    @RequestMapping("/list")
    public String showList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "kw", defaultValue = "") String kw){ //디폴트값은 빈칸으로 만들어 준다
        page--;
        Page<Question> paging = questionService.getList(page, kw);
        model.addAttribute("paging", paging); // 기존 url + ?page=2 ==> 2번 페이지가 나온다
        model.addAttribute("kw", kw); //화면에 검색한 검색어를 유지하기 위해 작성
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


    @PreAuthorize("isAuthenticated()") //로그인이 필요한 메서드를 의미하는 어노테이션
    @GetMapping("/create")
    public String showCreate(QuestionForm questionForm){

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String  showCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
    //vaild어노테이션을 사용해야 QuestionForm에서 설정한 설정 검증을 할 수 있다.
        //BindingResult의 경우 Vaild에 의해 이루어진 검증 절차를 의미하는 것이다.

        SiteUser user = userService.getUser(principal.getName()); //사용자 이름을 받아 넘겨 준다
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        questionService.Create(questionForm.getSubject(), questionForm.getContent(), user);

        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
            Question question =  questionService.getQuestion(id);
            if(!question.getAuthor().getUsername().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
            }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getSubject());
        return "question_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String  showModify(@Valid QuestionForm questionForm,BindingResult bindingResult, Principal principal,
                              @PathVariable("id") Integer id ){
        Question question = questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question q = questionService.getQuestion(id);
        if(!q.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionService.modify(q, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/%s".formatted(q.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String showdelete(Principal principal, @PathVariable("id") Integer id){
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String showVote(Principal principal, @PathVariable("id") Integer id){
        Question question = questionService.getQuestion(id); //현재 클릭한 질문에 대한 id값을 url에서 가지고 와서 question정보를 가지고 온다
        SiteUser siteUser = userService.getUser(principal.getName()); //유저에 대한 정보를 가지고 온다.
        questionService.vote(question, siteUser); //questionService에 만들어준 메서드에 대해 매개변수를 넘겨 준다.
        return "redirect:/question/detail/%d".formatted(id);
    }










}
