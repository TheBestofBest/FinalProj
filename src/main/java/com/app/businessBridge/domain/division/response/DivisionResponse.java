package com.app.businessBridge.domain.division.response;

import com.app.businessBridge.domain.division.DTO.DivisionDTO;
import com.app.businessBridge.domain.division.entity.Division;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DivisionResponse {
    @Getter
    public static class GetDivision{
        private DivisionDTO divisionDTO;

        public GetDivision(Division division){this.divisionDTO = new DivisionDTO(division);}
    }

    @Getter
    public static class GetDivisions{
        private List<DivisionDTO> divisionDTOList;

        public GetDivisions(List<Division> divisionList){
            this.divisionDTOList = divisionList.stream().map(DivisionDTO::new).toList();
        }
    }

    @Getter
    public static class PatchedDivision{
        private DivisionDTO divisionDTO;

        public PatchedDivision(Division division){this.divisionDTO = new DivisionDTO(division);}
    }
}
