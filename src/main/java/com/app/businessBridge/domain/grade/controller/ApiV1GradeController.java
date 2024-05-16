package com.app.businessBridge.domain.grade.controller;

import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.request.GradeRequest;
import com.app.businessBridge.domain.grade.response.GradeResponse;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grades")
public class ApiV1GradeController {
    private final GradeService gradeService;

    // 직급 등록
    @PostMapping("")
    public RsData post(@Valid @RequestBody GradeRequest.CreateRequest createRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.gradeService.create(createRequest.getCode(),
                createRequest.getName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }


    // 직급 다건 조회
    @GetMapping("")
    public RsData<GradeResponse.GetGrades> getAll() {
        RsData<List<Grade>> rsData = this.gradeService.findAll();

        return RsData.of(rsData.getRsCode(), rsData.getMsg(),
                new GradeResponse.GetGrades(rsData.getData()));
    }

    // 직급 수정
    @PatchMapping("")
    public RsData<GradeResponse.PatchedGrade> patch(@Valid @RequestBody GradeRequest.UpdateRequest updateRequest,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData<Grade> rsData = this.gradeService.update(updateRequest.getId(), updateRequest.getCode(),
                updateRequest.getName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new GradeResponse.PatchedGrade(rsData.getData()));
    }

    // 직급 삭제
    @DeleteMapping("")
    public RsData delete(@Valid @RequestBody GradeRequest.DeleteRequest deleteRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.gradeService.delete(deleteRequest.getId());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }
}
