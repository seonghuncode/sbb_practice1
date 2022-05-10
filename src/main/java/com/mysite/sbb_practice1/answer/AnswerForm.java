package com.mysite.sbb_practice1.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AnswerForm {

    @NotEmpty(message = "답변변은 필 입력 사항 입니다.")
    private String content;
}
