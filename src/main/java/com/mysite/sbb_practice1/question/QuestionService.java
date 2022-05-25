package com.mysite.sbb_practice1.question;


import com.mysite.sbb_practice1.DataNotFoundException;
import com.mysite.sbb_practice1.answer.Answer;
import com.mysite.sbb_practice1.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor //아래 final을 선언하여 사용하기 위해서 사용하는 어노테이션 이다.
@Service
public class QuestionService {
    //QuestionService를 만드는 이유는 컨트롤러에서 여러개의 리포지터리를 사용하여 데이터를 조회하여 가공하여 리턴 하는 것 보다. 이런 기능들을 서비스로 만들어 두면 컨트롤러 에서
    //해당 서비스를 호풀하여 사용하면 되기 때문이다.
    //만약 서비스를 만들지 않고 컨트롤러에서 구현하려고 한다면 해당 기능을 필요호 하는 모든 컨트롤러에 동일한 기능을 중복으로 구현해야 하는 단점이 있기 때문이다.
    // ++ 컨트롤러가 리포지터리 없이 서비스를 통해서만 데이터베이스에 접근하도록 구현하는 것은 보안상 안정하기 때문이다.
    //(해커가 컨트롤러에 접근 하더라도 리포지터리에는 접근 할 수 없기 때문이다.)

    private final QuestionRepository questionRepository;


    public Page<Question> getList(int page, String keyword){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); //조회할 페이지의 번호, 한 페이지 보여줄 게시물 갯수
        Specification<Question> spec = search(keyword); //search를 keyword에 값을 넣어 결과값을 받아 spec에 넣어 리턴해 준다.
        return questionRepository.findAll(spec, pageable);
    }



    public Question getQuestion(Integer id){
        Optional<Question> question = questionRepository.findById(id);
        //Optional객체는 값이 있을 수도 없을 수도 있기 때문에 존재하는지 여부를 판단해 주어야 한다
        if(question.isPresent()){
            return question.get();
        }
        else{
            throw new DataNotFoundException("question not found");
        }
    }


    public void Create(String subject, String content, SiteUser user){
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(user);

        questionRepository.save(question);

    }


    public void modify(Question question, String subject, String content){

        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question){
        questionRepository.delete(question);

    }


    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);  //question에서 voter을 가지고 와서 투표한 siteUser을 더해준다
        questionRepository.save(question); //투표자가 더해지면 변경 내용을 저장햊해 준다.
    }


    private Specification<Question> search(String keyword){
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); //중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + keyword + "%"), // 제목
                        cb.like(q.get("content"), "%" + keyword + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + keyword + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + keyword + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + keyword + "%"));   // 답변 작성자
            }
        };

//        q - Root, 즉 기준을 의미하는 Question 엔티티의 객체 (질문 제목과 내용을 검색하기 위해 필요)
//
//        u1 - Question 엔티티와 SiteUser 엔티티를 아우터 조인(JoinType.LEFT)하여 만든 SiteUser
//        엔티티의 객체. Question 엔티티와 SiteUser 엔티티는 author 속성으로 연결되어 있기 때문에 q.join("author")와 같이 조인해야 한다.
//        (질문 작성자를 검색하기 위해 필요)
//
//        a - Question 엔티티와 Answer 엔티티를 아우터 조인하여 만든 Answer 엔티티의 객체
//        Question 엔티티와 Answer 엔티티는 answerList 속성으로 연결되어 있기 때문에 q.join("answerList")와 같이 조인해야 한다.
//        (답변 내용을 검색하기 위해 필요)
//
//        u2 - 바로 위에서 작성한 a 객체와 다시 한번 SiteUser 엔티티와 아우터 조인하여 만든 SiteUser 엔티티의 객체
//        (답변 작성자를 검색하기 위해서 필요)
//        그리고 검색어(kw)가 포함되어 있는지를 like로 검색하기 위해 제목, 내용, 질문 작성자, 답변 내용, 답변 작성자 각각에 cb.like를
//        사용하고 최종적으로 cb.or로 OR 검색되게 하였다. 위에서 예시로 든 쿼리와 비교해 보면 코드가 어떻게 구성되었는지 쉽게 이해될 것이다.
//


    }










}
