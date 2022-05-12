package com.mysite.sbb_practice1;

import com.mysite.sbb_practice1.answer.Answer;
import com.mysite.sbb_practice1.answer.AnswerRepository;
import com.mysite.sbb_practice1.question.Question;
import com.mysite.sbb_practice1.question.QuestionRepository;
import com.mysite.sbb_practice1.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//테스트 클래스를 사용하는 이유는 프로그램을 개발할때 코드를 하나 작성하고 오류 확인하는 작업을 계속 반복하지 않기 위해 테스트
//클래스로 한번 만들어 놓으면 어디에서 오류가 났는지 한번이 알 수 있게 해주는 역할을 한다.

@SpringBootTest // =>스프링 부트 test 클래스를 의미한다
class SbbPractice1ApplicationTests {

	@Autowired //questionRepository객체를 스프링이 자동으로 생성해주는 역할(객체 주입을 위해 사용하는 어노테이션)
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository; // answerRepository를 사용하기 위해 객체 주입을 해준다.

	@Autowired
	private QuestionService questionService;


	@Test
	void testMakeDate(){
		Question question = new Question();
		for(int i = 1; i <= 300; i++){
			String subject = "테스트 데이터 입니다. [%03d]".formatted(i);
			String content = "테스트 데이터 내용 입니다.";

			questionService.Create(subject, content);
		}
	}


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


	@Test
	void testFindAll(){
		List<Question> allQuestion = questionRepository.findAll();
		assertEquals(2, allQuestion.size());

		Question question = allQuestion.get(0);
		assertEquals("sbb가 무엇인가요?", question.getSubject());
		//assertEquals("기대값", "실제값") 불일치 하면 실행이 실패로 끝나게 된다.
	}

	@Test
	void testFindById(){
		Optional<Question> question = questionRepository.findById(1);
		if(question.isPresent()){ //입력한 아이디 값이 존재한다면
			Question q = question.get(); //q에 값을 넣고 실제 DB에 저장 되어 있는 값을 비교한다
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}


	@Test
	void testFindBySubject(){
		//QuestionRepository에 findBySubject를 찾아올 수 있도록 만들어 주고 -> subject를 통해 해당 정보를 찾는다
		Question findSubject = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, findSubject.getId()); //그 정보의 아이디 값으로 두개의 원하던 정보가 원한는 값이 나오는지 체크
	}


	@Test
	void testFindSubjectAndContent(){
		Question fd = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, fd.getId());
	}

	@Test
	void testFindLike(){
		List<Question> fdLike = questionRepository.findBySubjectLike("sbb%");
		Question q = fdLike.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}


	@Test
	void testModify(){
//		Question q = questionRepository.findById(1).get();
//		q.setSubject("수정된 제목");
//		q.setCreateDate(LocalDateTime.now());
//		questionRepository.save(q);
// or

		Optional<Question> q = questionRepository.findById(1);
		assertTrue(q.isPresent()); //데이터의 값이 참인지 확인
		Question modify = q.get(); //수정할 객체를 만들어 입력한 id값에 해당하는 정보를 넣어준다
		modify.setSubject("수정된 제목"); //수정
		questionRepository.save(modify); //저장
	}


	@Test
	void testDelete(){
		assertEquals(2, questionRepository.count()); //전체 데이터의 갯수 비교
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?"); //제목으로 삭제할 데이터를 찾는다
		assertNotNull(q); //데이터가 존재하는지 확인

		questionRepository.delete(q); //존재한다면 삭제
		assertEquals(1, questionRepository.count()); //삭제후 게시글이 하나 줄었는지 확인

		//삭제후 계속 테스트를 해야 하므로 원래 되로 데이터를 만들어 준다
		Question remake = new Question();
		remake.setSubject("sbb가 무엇인가요?");
		remake.setContent("sbb에 대해서 알고 싶습니다.");
		remake.setCreateDate(LocalDateTime.now());

		questionRepository.save(remake);
	}



	@Test
	void testMakeAnswer(){
		//setQuestion을 해주기 위해서는 답변을 달기 위한 질문을 먼저 마들어 주어야 한다.
		Optional<Question> question = questionRepository.findById(2);
		assertTrue(question.isPresent());
		Question q = question.get(); //optional로 가지고 온후 Question객체를 만들어 데이터를 넣어준다.

		Answer a1 = new Answer();
		a1.setContent("네 자동으로 생성 됩니다.");
		a1.setCreateDate(LocalDateTime.now());
		a1.setQuestion(q);

		answerRepository.save(a1);
	}


	@Test
	void testFindtAnswer(){
		Optional<Answer> a = answerRepository.findById(1);
		assertTrue(a.isPresent());
		Answer answer = a.get();
		assertEquals(2, answer.getQuestion().getId());
	}


	@Transactional
	@Test
	void testFindAnswerFromQuestion(){
		//질문에서 답변을 조회해 보기 위해서
		Optional<Question> question = questionRepository.findById(2); //원하는 질문의 데이터를 찾는다
		assertTrue(question.isPresent()); //존재 여부 확인
		Question q = question.get(); //존재한다면 optional로 찾아오 데이터를 새 객체에 넣어준다

		//<테스트 코드에서만 생기는 오류>
		//q.getAnswerList()의 답변 리스트는 객체를 조회할때 가지고 오는 것이 아닌 아래에서 차례가 되었을때 가지고 오는디
		//이때 위에서 세션이 한번 종료 되고 다시 세션이 연결되어 가지고 오기 때문에 데이터를 가지고 올 수 없어 오류가 나기 때문에
		//@Transactional이라는 어노테이션을 사용하여 메소드가 끝날때 까지 세연이 유지 되도록 해준다
		List<Answer> answerList = q.getAnswerList(); //answer를 List형삭으로 받는 객체에 Question객체에서 있는 answerList를 넣어준다
		assertEquals(1, answerList.size()); //답변이 하나인지 체크
		assertEquals("네 자동으로 생성 됩니다.", answerList.get(0).getContent()); //가지고온 데이터가 의도했던 답변이 맞는지 확인

	}







}
