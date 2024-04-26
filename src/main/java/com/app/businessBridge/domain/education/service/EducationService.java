package com.app.businessBridge.domain.education.service;


import com.app.businessBridge.domain.education.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
}
