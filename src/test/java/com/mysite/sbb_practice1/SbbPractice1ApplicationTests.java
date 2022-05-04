package com.mysite.sbb_practice1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest // =>스프링 부트 test 클래스를 의미한다
class SbbPractice1ApplicationTests {

	@Autowired //questionRepository객체를 스프링이 자동으로 생성해주는 역할(객체 주입을 위해 사용하는 어노테이션)
	private QuestionRepository questionRepository;


	@Test
	void testSaveQuestion() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());

		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());

		questionRepository.save(q2);
	}

}
