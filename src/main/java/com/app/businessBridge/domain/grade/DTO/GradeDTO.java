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
    private Integer code;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @JsonIgnore
    private List<GradeDTO> gradeDTOList;

    public GradeDTO(Grade grade) {
        this.id = grade.getId();
        this.code = grade.getCode();
        this.name = grade.getName();
        this.createdDate = grade.getCreatedDate();
        this.modifiedDate = grade.getModifiedDate();
    }

}
