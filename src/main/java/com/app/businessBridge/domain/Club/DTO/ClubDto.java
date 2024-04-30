package com.app.businessBridge.domain.Club.DTO;

import com.app.businessBridge.domain.Club.Entity.Club;
import lombok.Getter;

@Getter
public class ClubDto {
    private Long id;
    private String name;

    public  ClubDto (Club club){
        this.id= club.getId();
        this.name= club.getName();
    }
}
