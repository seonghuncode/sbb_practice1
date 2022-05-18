package com.mysite.sbb_practice1.question;


import com.mysite.sbb_practice1.DataNotFoundException;
import com.mysite.sbb_practice1.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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


    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); //조회할 페이지의 번호, 한 페이지 보여줄 게시물 갯수
        return questionRepository.findAll(pageable);
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











}
