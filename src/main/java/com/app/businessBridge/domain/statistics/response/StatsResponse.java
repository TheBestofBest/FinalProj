package com.app.businessBridge.domain.statistics.response;

import com.app.businessBridge.domain.statistics.dto.StatsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StatsResponse {
    private final List<StatsDto> statsDtos;
}
