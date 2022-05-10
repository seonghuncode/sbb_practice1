package com.mysite.sbb_practice1.question;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
//제목과 내용을 입력 할때 questuon_form에서 빈 제목괴 내용을 등록하는 것을 막기위해 만들었다.

@Setter
@Getter
public class QuestionForm {

    //null 또는 빈공간을 허용하지 않는다는 의미 만약 빈공간이라면 message가 출력 된다.
    @NotEmpty(message = "제목은 필수 입력 사항 입니다")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수 입력 사항 입니다.")
    private String content;


}
