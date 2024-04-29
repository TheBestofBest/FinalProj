package com.app.businessBridge.domain.department.DTO;

import com.app.businessBridge.domain.department.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class DepartmentDTO {

    private Long id;
    private Integer departmentCode;
    private String departmentName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @JsonIgnore
    private List<DepartmentDTO> departmentDTOList;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.departmentCode = department.getDepartmentCode();
        this.departmentName = department.getDepartmentName();
        this.createdDate = department.getCreatedDate();
        this.modifiedDate = department.getModifiedDate();
    }
}
