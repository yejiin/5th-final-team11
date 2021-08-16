package com.doubleslash.fifth.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.doubleslash.fifth.dto.CustomTokenDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord.CreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	// idToken 추출
	public String getBearerToken(HttpServletRequest request) {
		String bearerToken = null;
		String authorization = request.getHeader("Authorization");
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
			bearerToken = authorization.substring(7);
		}

		return bearerToken;
	}

	// idToken 검증
	public String verifyToken(HttpServletRequest request) throws Exception {
		String idToken = getBearerToken(request);
		FirebaseToken decodedToken = null;
		String uid = "";

		// 토큰이 존재하지 않음
		if (idToken == null) {
			return null;
		}
		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		} catch (FirebaseAuthException e) {
			// 토큰이 유효하지 않음
			return null;
		}

		uid = decodedToken.getUid();
		userService.insertUser(uid);

		return uid;
	}

	// Kakao Access Token 검증
	public Map<String, String> verifyKakaoAccessToken(String accessToken) {

		Map<String, String> res = new HashMap<>();

		String requestUrl = "https://kapi.kakao.com/v2/user/me";
		URL url;

		try {
			url = new URL(requestUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
			JSONParser parser = new JSONParser();
			JSONObject mainObj = (JSONObject) parser.parse(result);

			JSONObject subObj = (JSONObject) mainObj.get("kakao_account");
			boolean isNotEmailAgreed = (boolean) subObj.get("email_needs_agreement");
			System.out.println(mainObj.get("id"));
			if (!isNotEmailAgreed) {
				res.put("email", subObj.get("email").toString());
			} else {
				res.put("uid", mainObj.get("id").toString());
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return res;

	}

	// Firebase Custom Token 발급
	public CustomTokenDTO getFirebaseCustomToken(String accessToken) throws FirebaseAuthException {
		Map<String, String> val = verifyKakaoAccessToken(accessToken);

		boolean isEmail = val.containsKey("email") ? true : false;

		CreateRequest request = new CreateRequest();

		String email = null;
		String kakaoUid = null;
		String uId = null;

		if (isEmail) {
			email = val.get("email");
			request.setEmail(email);

		} else {
			kakaoUid = val.get("uid");
			request.setUid(kakaoUid);

		}

		try {
			uId = FirebaseAuth.getInstance().createUser(request).getUid();
		} catch (FirebaseAuthException e) {
			try {
				if (isEmail) {
					uId = FirebaseAuth.getInstance().getUserByEmail(email).getUid();
				} else {
					uId = FirebaseAuth.getInstance().getUser(kakaoUid).getUid();
				}
			} catch (FirebaseAuthException e1) {
				System.out.println(e.getMessage());
			}
		}
		
		// 커스텀 토큰 생성
		String customToken = FirebaseAuth.getInstance().createCustomToken(uId);
		return new CustomTokenDTO(customToken);

	}
}
