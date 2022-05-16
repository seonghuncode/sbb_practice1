package com.mysite.sbb_practice1.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByusername(String username);
    //앞으로 작성할 UserSecurityService는 사용자를 조회하는 기능이 필요하므로 다음처럼 findByUsername 메서드를 User 리포지터리에 추가하자.
}
