package com.mysite.sbb_practice1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);

        //비밀번호의 경우 법적으로 암호화를 의무적으로 해주어 만들어야 한다.
        //대표적으로 사용하는 방법이니 숙지 하기
//      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); ==> @Bean으로 만들었기 때문에 이렇게 사용이 가능해졌다.
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return user;
    }

}
