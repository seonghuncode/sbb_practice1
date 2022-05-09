package com.mysite.sbb_practice1.question;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //아래 final을 선언하여 사용하기 위해서 사용하는 어노테이션 이다.
@Service
public class QuestionService {
    //QuestionService를 만드는 이유는 컨트롤러에서 여러개의 리포지터리를 사용하여 데이터를 조회하여 가공하여 리턴 하는 것 보다. 이런 기능들을 서비스로 만들어 두면 컨트롤러 에서
    //해당 서비스를 호풀하여 사용하면 되기 때문이다.
    //만약 서비스를 만들지 않고 컨트롤러에서 구현하려고 한다면 해당 기능을 필요호 하는 모든 컨트롤러에 동일한 기능을 중복으로 구현해야 하는 단점이 있기 때문이다.
    // ++ 컨트롤러가 리포지터리 없이 서비스를 통해서만 데이터베이스에 접근하도록 구현하는 것은 보안상 안정하기 때문이다.
    //(해커가 컨트롤러에 접근 하더라도 리포지터리에는 접근 할 수 없기 때문이다.)

    private final QuestionRepository questionRepository;


    public List<Question> getList(){
        return questionRepository.findAll();
    }


}
