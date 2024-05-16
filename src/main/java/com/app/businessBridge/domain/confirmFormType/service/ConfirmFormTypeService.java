package com.app.businessBridge.domain.confirmFormType.service;

import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.repository.ConfirmFormTypeRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmFormTypeService {
    private final ConfirmFormTypeRepository confirmFormTypeRepository;

    public List<ConfirmFormType> getAll() {
        return this.confirmFormTypeRepository.findAll();
    }

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

    public Optional<ConfirmFormType> getConfirmFormType(Long confirmFormTypeId) {
        return this.confirmFormTypeRepository.findById(confirmFormTypeId);
    }

    public RsData<ConfirmFormType> updateConfirmFormType(ConfirmFormType confirmFormType, String formName, String formDescription) {
        ConfirmFormType updatedConfirmFormType = confirmFormType.toBuilder()
                .formName(formName)
                .formDescription(formDescription)
                .build();
        this.confirmFormTypeRepository.save(updatedConfirmFormType);

        return RsData.of(
                RsCode.S_03,
                "%d번 결재 양식이 수정되었습니다.".formatted(confirmFormType.getId()),
                updatedConfirmFormType
                );
    }

    public void deleteConfirmFormType(ConfirmFormType confirmFormType) {
        this.confirmFormTypeRepository.delete(confirmFormType);
    }

    public RsData<ConfirmFormType> getConfirmFormTypeByFormName(String formName) {
        Optional<ConfirmFormType> optionalConfirmFormType = this.confirmFormTypeRepository.findByFormName(formName);
        if(optionalConfirmFormType.isEmpty()){
            return RsData.of(RsCode.F_04,"해당 이름의 결재 양식은 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "%s 결재양식을 찾았습니다.".formatted(formName), optionalConfirmFormType.get());
    }
}
