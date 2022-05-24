package com.mysite.sbb_practice1.answer;

import com.mysite.sbb_practice1.DataNotFoundException;
import com.mysite.sbb_practice1.question.Question;
import com.mysite.sbb_practice1.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser author){
        Answer answer = new Answer();

        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);  //SiteUser객체를 추가로 전달 받아 답변 저장시 author을 추가로 세팅

        answerRepository.save(answer);

        return answer; //controller에서 답변이 등록된 위치로 이동하기 위해서는  답변 객체가 반드시 필요하기 때문에 리턴을 해주어야 한다

    }

    public Answer getAnswer(Integer id){
        Optional<Answer> answer = answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }
        else{
            throw new DataNotFoundException("answer not found/답변을 찾을 수 없습니다 확인해 주세요");
        }
    }


    public void modify(Integer id , String content){
        Answer answer = answerRepository.findById(id).get();
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void  delete(Answer answer){
        answerRepository.delete(answer);
    }


    public void vote(Answer answer, SiteUser siteUser){
        answer.getVoter().add(siteUser);
        answerRepository.save(answer);
    }





}
