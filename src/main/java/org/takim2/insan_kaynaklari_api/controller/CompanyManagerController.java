package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.takim2.insan_kaynaklari_api.service.CompanyManagerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-manager")
public class CompanyManagerController {
    private final CompanyManagerService companyManagerService;

}
