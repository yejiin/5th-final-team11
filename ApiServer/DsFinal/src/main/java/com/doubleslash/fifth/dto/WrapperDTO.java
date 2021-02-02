package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Wrapper클래스는 단일 List데이터 전송시에만 사용합니다.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperDTO {

	private Object response;
	
}
