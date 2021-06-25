package com.doubleslash.fifth.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.repository.RecommendRepository;
import com.doubleslash.fifth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RecommendRepository recommendRepository;

	// User 중복 체크 & User 추가
	public void insertUser(String uid){
		try {
			User user = new User(uid);
			userRepository.save(user);
		}catch(Exception e) {
			return;
		}
	}
	
	// 닉네임 중복 확인
	public User nicknameCheck(String nickname) throws IOException {
		User user = userRepository.findByNickname(nickname);
		return user;
	}
	
	// user 등록 
    @Transactional
	public void registerUser(int id, String nickname, Float drink, int hangover) {
		User user = userRepository.findById(id).get();
		user.setNickname(nickname);
		user.setDrink(drink);
		user.setHangover(hangover);
    }
	
	public int getId(String uid) {
		return userRepository.findByUid(uid).getId();
	}

	public boolean isSignUpCheck(String uid) {
		if(userRepository.findByUid(uid).getNickname() == null) return false;
		return true;
	}
	
	public boolean isRecommendCheck(int id) {
		if(!recommendRepository.findById(id).isPresent()) return false;
		return true;
	}
	
}