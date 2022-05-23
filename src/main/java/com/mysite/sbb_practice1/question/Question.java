package com.mysite.sbb_practice1.question;


import com.mysite.sbb_practice1.answer.Answer;
import com.mysite.sbb_practice1.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Question {

    //앤티티란 데이터베이스 컬럼과 맵핑 되는 것이다.

    @Id //고유의 번호로 각 데이터의 구분이 가능 하다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id값에 auto increment기능을 주기 위한 코드
    private Integer id;

    @Column(length = 200) //컬럼 속성 글자 길이 수를 제한 한다
    private String subject; //제목

    @Column(columnDefinition = "text") //컬럼 속성을 정의 할때 사용(글자 수를 제한 할 수 없을 경우 사용한다.)
    private String content; //내용용

    private LocalDateTime createDate; //직성 일시

    //mappedBy ==> 참조 앤티티의 속성명을 뜻 한다 == Answer앤티티에서 Question앤티티를 참조한 속성명을 적어주어야 한다
    //cascade부분은 질문 하나에 여러개의 답변이 달릴 수 있는데 이때 질문이 삭제되면 답변도 모두 삭제 되게 해주는 코드
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) //질문 하나에 여러개의 답이 달리는 관계 일때 사용
    private List<Answer> answerList; //질뭉에 대한 답변은 여러개 이므로 List로 만들어 받아 주어야 한다.
    //question.getAnswerList() --> 질문 객체에서 답변 객체를 호출 하고 싶을 경우 이런 사용이 가능해 지게 되었다.

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany //하나의 질문에 여러사람 추천 가능, 한 사람이 여러개의 질문에 추천할 수 있다.
    Set<SiteUser> voter; //Set의 경우 중복을 허용하지 않는다(추천인은 중복X)

}
