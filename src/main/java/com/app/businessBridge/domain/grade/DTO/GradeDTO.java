package com.app.businessBridge.domain.grade.DTO;

import com.app.businessBridge.domain.grade.entity.Grade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GradeDTO {

    private Long id;
    private Integer gradeCode;
    private String gradeName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @JsonIgnore
    private List<GradeDTO> gradeDTOList;

    public GradeDTO(Grade grade) {
        this.id = grade.getId();
        this.gradeCode = grade.getGradeCode();
        this.gradeName = grade.getGradeName();
        this.createdDate = grade.getCreatedDate();
        this.modifiedDate = grade.getModifiedDate();
    }

}
