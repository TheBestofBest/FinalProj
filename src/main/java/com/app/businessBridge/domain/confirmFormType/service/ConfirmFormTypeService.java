package com.app.businessBridge.domain.confirmFormType.service;

import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.repository.ConfirmFormTypeRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmFormTypeService {
    private final ConfirmFormTypeRepository confirmFormTypeRepository;

    public List<ConfirmFormType> getAll() {
        return this.confirmFormTypeRepository.findAll();
    }

    @Transactional
    public RsData<ConfirmFormType> create(String formName, String formDescription) {
        ConfirmFormType confirmFormType = ConfirmFormType.builder()
                .formName(formName)
                .formDescription(formDescription)
                .build();
        this.confirmFormTypeRepository.save(confirmFormType);

        return RsData.of(
                RsCode.S_02,
                "결재 양식 등록이 성공했습니다.",
                confirmFormType
        );
    }
}
