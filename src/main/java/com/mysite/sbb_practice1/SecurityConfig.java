package com.mysite.sbb_practice1;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //환경설정 파일임을 의미하는 애너테이션이다. 여기서는 스프링 시큐리티의 설정을 위해 사용되었다.
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //이 클래스를 만든이유는 스프링 시큐리티를 사용하기 위해서 의존성 주입을 했는데 주입 후 부터 모든 페이지 이동을 위해서는
    //로그인 페이지가 나와 사용을 할 수 없으므로 일단 모든 접근을 허용해주기 위해서 만들었다
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll();
        //모든 인증되지 않은 요청을 허락한다는 의미이다. 따라서 로그인을 하지 않더라도 모든 페이지에 접근할 수 있다.
    }
}
