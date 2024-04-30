package com.app.businessBridge.domain.Club.Controller;

import com.app.businessBridge.domain.Article.Controller.ApiV1ArticleController;
import com.app.businessBridge.domain.Article.DTO.ArticleDto;
import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.domain.Club.DTO.ClubDto;
import com.app.businessBridge.domain.Club.Entity.Club;
import com.app.businessBridge.domain.Club.Service.ClubService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs")
public class ApiV1ClubController {

    private final ClubService clubService;

    @GetMapping("")
    public RsData<ClubsResponse> getClubs() {
        List<ClubDto> clubDtoList = this.clubService
                .getList()
                .stream()
                .map(club -> new ClubDto(club))
                .toList();

        return RsData.of(RsCode.S_01, "성공", new ApiV1ClubController.ClubsResponse(clubDtoList));
    }

    @GetMapping("/{id}")
    public RsData<ClubResponse> getClub(@PathVariable("id") Long id) {
        return clubService.getclub(id).map(club -> RsData.of(RsCode.S_01,
                "성공",
                new ApiV1ClubController.ClubResponse(new ClubDto(club))
        )).orElseGet(() -> RsData.of(RsCode.F_01,
                "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }
    @PostMapping("")
    public RsData<WriteResponse> write(@Valid @RequestBody WriteRequest writeRequest) {

        RsData<Club> writeRs = this.clubService.create(writeRequest.getName());

//        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getRsCode(),
                writeRs.getMsg(),
                new ApiV1ClubController.WriteResponse(new ClubDto(writeRs.getData()))
        );
    }
    @PatchMapping("/{id}")
    public RsData<ModifyResponse> modify(@Valid @RequestBody ModifyRequest modifyRequest, @PathVariable("id") Long id) {
        Optional<Club> optionalClub = this.clubService.findById(id);

        if (optionalClub.isEmpty()) return RsData.of(RsCode.F_01,
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );

        RsData<Club> modifyRs = this.clubService.modify(optionalClub.get(),modifyRequest.getName());

        return RsData.of(
                modifyRs.getRsCode(),
                modifyRs.getMsg(),
                new ApiV1ClubController.ModifyResponse(modifyRs.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<RemoveResponse> remove(@PathVariable("id") Long id) {
        Optional<Club> optionalClub = this.clubService.findById(id);

        if (optionalClub.isEmpty()) return RsData.of(RsCode.F_01,
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );

        RsData<Club> deleteRs = clubService.deleteById(id);

        return RsData.of(
                deleteRs.getRsCode(),
                deleteRs.getMsg(),
                new ApiV1ClubController.RemoveResponse(optionalClub.get())
        );
    }
    @AllArgsConstructor
    @Getter
    public static class ClubsResponse {
        private final List<ClubDto> club;
    }

    @AllArgsConstructor
    @Getter
    public static class ClubResponse {
        private final ClubDto clubDto;
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public static class WriteResponse {
        private final ClubDto clubDto;
    }
    @Data
    public static class ModifyRequest {
        @NotBlank
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final Club club;
    }

    @AllArgsConstructor
    @Getter
    public static class RemoveResponse {
        private final Club club;
    }
}
