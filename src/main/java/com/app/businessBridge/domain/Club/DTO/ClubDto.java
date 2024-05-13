package com.app.businessBridge.domain.Club.DTO;

import com.app.businessBridge.domain.Club.Entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubDto {
    private Long id;
    private String clubName;

    public  ClubDto (Club club){
        this.id= club.getId();
        this.clubName= club.getClubName();
    }
}
