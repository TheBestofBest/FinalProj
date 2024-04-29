package com.app.businessBridge.domain.grade.DTO;

import com.app.businessBridge.domain.grade.entity.Grade;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GradeDTO {

    private Integer gradeCode;
    private String gradeName;

    private List<GradeDTO> gradeDTOList;

    public GradeDTO(Grade grade) {
        this.gradeCode = grade.getGradeCode();
        this.gradeName = grade.getGradeName();
    }

}