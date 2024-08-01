package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.PassiveCompaniesView;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.repository.CompanyRepository;

import java.util.List;

import static org.takim2.insan_kaynaklari_api.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<PassiveCompaniesView> getPendingCompanies(boolean isActive) {
        return companyRepository.findByIsActive(isActive);
    }

    public void updateCompanyStatus(Long companyId, boolean isActive) {
        Company company = companyRepository.findById(companyId).orElseThrow(()-> new HumanResourcesAppException(COMPANY_NOT_FOUND));
        company.setActive(isActive);
        companyRepository.save(company);
    }
}

