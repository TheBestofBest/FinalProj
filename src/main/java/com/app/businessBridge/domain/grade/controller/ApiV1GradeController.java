package com.app.businessBridge.domain.grade.controller;

import com.app.businessBridge.domain.grade.DTO.GradeDTO;
import com.app.businessBridge.domain.grade.request.PatchGradeRequest;
import com.app.businessBridge.domain.grade.request.PostGradeRequest;
import com.app.businessBridge.domain.grade.response.GradeListResponse;
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

    // 직급 다건 조회
    @GetMapping("")
    public RsData<GradeListResponse> GetAll() {
        RsData<List<GradeDTO>> rsData = this.gradeService.readAll();

        // result code 문제 해결 필요
        return RsData.of(null, rsData.getMsg(), new GradeListResponse(rsData.getData()));
    }

    // 직급 등록
    @PostMapping("")
    public RsData<GradeResponse> post(@Valid @RequestBody PostGradeRequest postGradeRequest) {
        RsData<GradeDTO> rsData = this.gradeService.create(postGradeRequest.getGradeCode(),
                postGradeRequest.getGradeName());

        // result code 문제 해결 필요
        return RsData.of(null, rsData.getMsg(), new GradeResponse(rsData.getData()));
    }

    // 직급 수정
    @PatchMapping("/{id}")
    public RsData<GradeResponse> patch(@PathVariable(value = "id") Long id,
                                       @Valid @RequestBody PatchGradeRequest patchGradeRequest) {
        RsData<GradeDTO> rsData = this.gradeService.update(id, patchGradeRequest.getGradeCode(),
                patchGradeRequest.getGradeName());

        // result code 문제 해결 필요
        return RsData.of(null, rsData.getMsg(), new GradeResponse(rsData.getData()));
    }

    // 직급 삭제
    @DeleteMapping("/{id}")
    public RsData<GradeResponse> delete(@PathVariable(value = "id") Long id) {
        RsData<GradeDTO> rsData = this.gradeService.delete(id);

        // result code 문제 해결 필요
        return RsData.of(null, rsData.getMsg(), new GradeResponse(rsData.getData()));
    }
}
