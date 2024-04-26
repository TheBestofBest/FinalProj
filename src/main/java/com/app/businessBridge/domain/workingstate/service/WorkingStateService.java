package com.app.businessBridge.domain.workingstate.service;


import com.app.businessBridge.domain.workingstate.repository.WorkingStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkingStateService {

    private final WorkingStateRepository workingStateRepository;

}
