package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.User;

//JpaRepository 인터페이스에 선언되어 있지 않은 사용자 메소드를 선언
//JAP메소드 규칙을 이용하여 SQL문 작성

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUid(String uid);
	
	public User findByNickname(String nickname);
	
}
