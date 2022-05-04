package com.mysite.sbb_practice1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    //리포지 터리란> --> 앤티티만으로 데이터베이스에 데이터를 저장하고 조회할 수 없다
    //==> 데이터를 처리하기 위해서는 실제 데이터베이스외 연동하는 Jpa 리포지터리가 필요하다
    //역할 ==> 앤티티에 의해 생성된 데이터베이스 테이블에 접근 하기 위해 사용하는  findALl, save등을 사용하기 위해서는 인터페이스가 필요하다
    //==>데이터 처리를 위해 어떤 값을 넣고 조회할지에 대해 처리하는 것이 리포지터리의 역할이다.
    // (앤티티에서 만들어진 데이터를 사용하기 위해 만들어 준다고 생각)
}
