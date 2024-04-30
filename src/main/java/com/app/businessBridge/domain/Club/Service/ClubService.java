package com.app.businessBridge.domain.Club.Service;

import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.domain.Club.Entity.Club;
import com.app.businessBridge.domain.Club.Repository.ClubRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    public List<Club> getList() {
        return this.clubRepository.findAll();
    }
    public Optional<Club> findById(Long id) {
        return this.clubRepository.findById(id);
    }
    @Transactional
    public RsData<Club> create(String name) {
        Club club = Club.builder()
                .name(name)
                .build();

        this.clubRepository.save(club);

        return RsData.of(RsCode.S_02,
                "동아리가 생성 되었습니다.",
                club
        );
    }
    public Optional<Club> getclub(Long id) {
        return this.clubRepository.findById(id);
    }
    public RsData<Club> modify(Club club, String name) {
        club.setName(name);
        clubRepository.save(club);

        return RsData.of(RsCode.S_03,
                "%d번 동아리가 수정 되었습니다.".formatted(club.getId()),
                club
        );
    }

    public RsData<Club> deleteById(Long id) {
        clubRepository.deleteById(id);

        return RsData.of(RsCode.S_04,
                "%d번 동아리가 삭제 되었습니다.".formatted(id),
                null
        );
    }


}
