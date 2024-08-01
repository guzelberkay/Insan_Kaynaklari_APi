package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.entity.CompanyManager;
import org.takim2.insan_kaynaklari_api.repository.CompanyManagerRepository;

@Service
@RequiredArgsConstructor
public class CompanyManagerService {
    private final CompanyManagerRepository companyManagerRepository;




}
