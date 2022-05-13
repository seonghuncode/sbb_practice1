package com.mysite.sbb_practice1.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {
//이 클래스의 역할은 입력을 받을때 조건을 지정하는 폼을 만들어 주는 작업이다.
    //회원가입 폼에서 들어오는 정보를 원하는 방식으로 조건을 설정해 두기 위한 방법이다.

    @Size(min=3, max = 15)
    @NotEmpty(message = "사용자 이름은 필수 입력 사항입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수 입력 사항 입니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 사항 입니다.")
    private String password1;

    @NotEmpty(message = "입력한 비빌번호 일치 여부를 위해 필수 입력 사항 입니다.")
    private String password2;
}
