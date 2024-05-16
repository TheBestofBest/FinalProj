package com.app.businessBridge.domain.rebate.response;


import com.app.businessBridge.domain.rebate.dto.RebatesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RebatesResponse {

    private final List<RebatesDto> rebates;

    private final Long totalSum;
}
