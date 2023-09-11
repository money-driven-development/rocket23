package com.initcloud.rocket23.checklist.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CursorResultDto {
    private List<HistoryDto> values = new ArrayList<>();
    private Boolean hasNext;

    public CursorResultDto(List<HistoryDto> values, Boolean hasNext) {
        this.values = values;
        this.hasNext = hasNext;
    }
}
