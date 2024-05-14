package com.app.businessBridge.domain.education.entity;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private Member author;

    private String title;

    private String content;

    private Long hit;

    private String filePath;

    private String thumbnailPath;

    private double videoLength;

//    private Long mettingRoomId;

}
