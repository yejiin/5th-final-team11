package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoveAlcoholResponse {

    private boolean love;
    private int loveTotalCnt;
}
