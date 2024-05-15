package com.app.businessBridge.domain.department.response;

import com.app.businessBridge.domain.department.DTO.DepartmentDTO;
import com.app.businessBridge.domain.department.entity.Department;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartmentResponse {

    @Getter
    public static class GetDepartment {
        private DepartmentDTO departmentDTO;

        public GetDepartment(Department department) {
            this.departmentDTO = new DepartmentDTO(department);
        }
    }

    @Getter
    public static class GetDepartments {
        private List<DepartmentDTO> dtoList;

        public GetDepartments(List<Department> departmentList) {
            this.dtoList = departmentList.stream().map(DepartmentDTO::new).toList();
        }
    }

    @Getter
    public static class PatchedDepartment {
        private DepartmentDTO departmentDTO;

        public PatchedDepartment(Department department) {
            this.departmentDTO = new DepartmentDTO(department);
        }
    }

}
