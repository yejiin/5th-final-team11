package com.doubleslash.fifth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoveResponse {

    private boolean love;
    private int loveTotalCnt;
}
