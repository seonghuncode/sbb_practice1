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

    public void create(Question question, String content, SiteUser author){
        Answer answer = new Answer();

        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);  //SiteUser객체를 추가로 전달 받아 답변 저장시 author을 추가로 세팅

        answerRepository.save(answer);

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
