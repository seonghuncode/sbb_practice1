package com.mysite.sbb_practice1;


import com.mysite.sbb_practice1.user.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize 어노테이션을 사용하기 위해서는 필요하다.
@RequiredArgsConstructor
@Configuration //환경설정 파일임을 의미하는 애너테이션이다. 여기서는 스프링 시큐리티의 설정을 위해 사용되었다.
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //이 클래스를 만든이유는 스프링 시큐리티를 사용하기 위해서 의존성 주입을 했는데 주입 후 부터 모든 페이지 이동을 위해서는
    //로그인 페이지가 나와 사용을 할 수 없으므로 일단 모든 접근을 허용해주기 위해서 만들었다


    private  final UserSecurityService userSecurityService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
        //모든 인증되지 않은 요청을 허락한다는 의미이다. 따라서 로그인을 하지 않더라도 모든 페이지에 접근할 수 있다.

                .and()
                .formLogin()
                .loginPage("/user/login")  //로그인 페이지의 url
                .defaultSuccessUrl("/") //성공시 이동하는 default url

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);




    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        //비밀번호를 입력할때 마다 BCryptPasswordEncoder()객체를 생성해서 입력을 받아야 하기 때문에 번거럼움을 해소하고자 bean으로 만들었다
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }
}
