package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Wrapper클래스는 단일 List데이터 전송시에만 사용합니다.
@Getter
@Setter
@AllArgsConstructor
public class WrapperDTO {

	private Object response;
	
}
