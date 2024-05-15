package com.app.businessBridge.domain.division.DTO;

import com.app.businessBridge.domain.division.entity.Division;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class DivisionDTO {
    private Long id;
    private Integer code;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @JsonIgnore
    private List<DivisionDTO> divisionDTOList;

    public DivisionDTO(Division division){
        this.id = division.getId();
        this.code = division.getCode();
        this.name = division.getName();
        this.createdDate = division.getCreatedDate();
        this.modifiedDate = division.getModifiedDate();
    }
}
