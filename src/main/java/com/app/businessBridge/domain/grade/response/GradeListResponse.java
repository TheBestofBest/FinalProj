package com.app.businessBridge.domain.grade.response;

import com.app.businessBridge.domain.grade.DTO.GradeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GradeListResponse {
    List<GradeDTO> gradeDTOList;
}
