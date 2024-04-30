package com.app.businessBridge.domain.department.controller;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.request.DepartmentRequest;
import com.app.businessBridge.domain.department.response.DepartmentResponse;
import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class ApiV1DepartmentController {
    private final DepartmentService departmentService;

    // 부서 등록
    @PostMapping("")
    public RsData post(@Valid @RequestBody DepartmentRequest.CreateRequest createRequest) {
        RsData rsData = this.departmentService.create(createRequest.getDepartmentCode(),
                createRequest.getDepartmentName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

    // 부서 다건 조회
    @GetMapping("")
    public RsData<DepartmentResponse.GetDepartments> getAll() {
        RsData<List<Department>> rsData = this.departmentService.readAll();

        return RsData.of(rsData.getRsCode(), rsData.getMsg(),
                new DepartmentResponse.GetDepartments(rsData.getData()));
    }

    // 부서 수정
    @PatchMapping("")
    public RsData<DepartmentResponse.PatchedDepartment> patch(@Valid @RequestBody DepartmentRequest.UpdateRequest updateRequest) {
        RsData<Department> rsData = this.departmentService.update(updateRequest.getId(), updateRequest.getDepartmentCode(),
                updateRequest.getDepartmentName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DepartmentResponse.PatchedDepartment(rsData.getData()));
    }

    // 부서 삭제
    @DeleteMapping("")
    public RsData delete(@Valid @RequestBody DepartmentRequest.DeleteRequest deleteRequest) {
        RsData rsData = this.departmentService.delete(deleteRequest.getId());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

}
