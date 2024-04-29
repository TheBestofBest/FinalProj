package com.app.businessBridge.domain.department.DTO;

import com.app.businessBridge.domain.department.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DepartmentDTO {

    private Integer departmentCode;
    private String departmentName;

    @JsonIgnore
    private List<DepartmentDTO> departmentDTOList;

    public DepartmentDTO(Department department){
        this.departmentCode = department.getDepartmentCode();
        this.departmentName = department.getDepartmentName();
    }
}
