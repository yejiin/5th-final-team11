package com.doubleslash.fifth.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CabinetResponse<T> {

	private List<T> AlcoholList;
	private Long totalCnt;
}
