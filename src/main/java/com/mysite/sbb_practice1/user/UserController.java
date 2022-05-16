package com.mysite.sbb_practice1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup") // get으로 요청되면 회원가입을 위한 템플릿을 렌더링 하게 된다
    public String showSignUp(UserCreateForm userCreateForm) { //매개변수로 UserCreateForm을 넣어주는 이유는 정보를 받을때 빈칸으로 받지 않기 우함
        return "signup_form";
    }

    @PostMapping("/signup") // ==> post로 요청이 오면 회원가입을 진행하는 작업이 이루어 진다.
    public String showSignUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        //@Vaild를 사용해야 UserCreateForm에서 설정한 조건을 확인할 수 있다, BindingResult는 Vaild에 의해 이루어진 검증 절차를 의미 한다.

        if (bindingResult.hasErrors()) { //만약 들어온 정보가 설정한 조건에 부합하지 않는다면 로그인 폼 화면을 유지한다.
            return "signup_form";
        }

        //입력받은 비밀번호 두개를 비교 하고 맞지 않다면 오류 메세지를 띄우는 역할을 해준다.
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            //bindingResult.rejectValue(필드명, 오류코드, 에러메시지)
            bindingResult.rejectValue("password2", "passwordInCorrect", "두 비밀번호가 일치 하지 않습니다. 다시 입력해주세요!!");
            return "signup_form";
        }
        //같은 아이디로 로그인하게 되면 오류가 발생하기 때문에 try, catch구문으로 잡아주어야 한다.
        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            //사용자ID 또는 이메일 주소가 동일할 경우에는 DataIntegrityViolationException이 발생
//          bindingResult.reject(오류코드, 오류메시지)
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    //login.form.html의 파일을 렌더링하는 역할, 스프링 시큐리티가 실제로 로그인 하는 페이지는를 해주기 때문에 @PostMApping운 필요 없다.
    public String showLogin(){

        return "login_form";
    }





}
