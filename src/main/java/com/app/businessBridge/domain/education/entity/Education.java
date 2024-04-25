package com.app.businessBridge.domain.education.entity;


import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Education extends BaseEntity {


//    private Member author;

    private String category;

    private String title;

    private String content;

    private MultipartFile videoFile;

//    private MettingRoom mettingRoom;

}
