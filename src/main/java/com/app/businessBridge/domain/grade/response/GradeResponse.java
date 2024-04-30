package com.app.businessBridge.domain.grade.response;

import com.app.businessBridge.domain.grade.DTO.GradeDTO;
import com.app.businessBridge.domain.grade.entity.Grade;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GradeResponse {

    @Getter
    public static class GetGrade {
        private GradeDTO gradeDTO;

        public GetGrade(Grade grade) {
            this.gradeDTO = new GradeDTO(grade);
        }
    }

    @Getter
    public static class GetGrades {
        private List<GradeDTO> gradeDTOList;

        public GetGrades(List<Grade> gradeList) {
            this.gradeDTOList = gradeList.stream().map(GradeDTO::new).toList();
        }
    }

    @Getter
    public static class PatchedGrade {
        private GradeDTO gradeDTO;

        public PatchedGrade(Grade grade) {
            this.gradeDTO = new GradeDTO(grade);
        }
    }
}
