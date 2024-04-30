package com.app.businessBridge.domain.grade.controller;

import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.request.GradeRequest;
import com.app.businessBridge.domain.grade.response.GradeResponse;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grades")
public class ApiV1GradeController {
    private final GradeService gradeService;

    // 직급 등록
    @PostMapping("")
    public RsData post(@Valid @RequestBody GradeRequest.CreateRequest createRequest) {
        RsData rsData = this.gradeService.create(createRequest.getGradeCode(),
                createRequest.getGradeName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }


    // 직급 다건 조회
    @GetMapping("")
    public RsData<GradeResponse.GetGrades> getAll() {
        RsData<List<Grade>> rsData = this.gradeService.readAll();

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new GradeResponse.GetGrades(rsData.getData()));
    }

    // 직급 수정
    @PatchMapping("/{id}")
    public RsData<GradeResponse.PatchedGrade> patch(@Valid @RequestBody GradeRequest.UpdateRequest updateRequest) {
        RsData<Grade> rsData = this.gradeService.update(updateRequest.getId(), updateRequest.getGradeCode(),
                updateRequest.getGradeName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new GradeResponse.PatchedGrade(rsData.getData()));
    }

    // 직급 삭제
    @DeleteMapping("/{id}")
    public RsData delete(@Valid @RequestBody GradeRequest.DeleteRequest deleteRequest) {
        RsData rsData = this.gradeService.delete(deleteRequest.getId());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }
}
