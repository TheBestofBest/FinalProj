package com.app.businessBridge.domain.department.response;

import com.app.businessBridge.domain.department.DTO.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DepartmentListResponse {
    private List<DepartmentDTO> departmentDTOList;
}
