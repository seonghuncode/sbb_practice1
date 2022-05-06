package com.mysite.sbb_practice1.answer;

import com.mysite.sbb_practice1.question.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "text")
    private String content;

    private LocalDateTime createDate;

    //어노테이션을 달면서 -> Answer앤티티의 questuon속성과 Question앤태태가 서로 연결이 되게 된다.
    @ManyToOne //답변은 여러개 이고 질문은 하나이기 때문에 시용(부모, 자식 관계에서 사용한다.)
    private Question question;
    //답변 앤티티 에서 질문 앤티티를 참조하기 위해 추가한 코드(answer.geTQuestion().geTSubject()를 할 수 있게 해준다.)

}
