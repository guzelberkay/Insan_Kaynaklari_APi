package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.PassiveCompaniesView;
import org.takim2.insan_kaynaklari_api.Vw.UserView;
import org.takim2.insan_kaynaklari_api.dto.request.SendActivationRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.PendingCompaniesResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.enums.UserStatus;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.util.CodeGenerator;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final CompanyService companyService;
    private final JavaMailSender mailSender;
    private final CodeGenerator codeGenerator;
    private final JwtTokenManager jwtTokenManager;



    public List<PendingCompaniesResponseDTO> getPendingUsers(String token) {
        //TODO token kontrolü
        //TODO user'ın Role Kontrolü

        List<PassiveCompaniesView> pendingCompanies = companyService.getPendingCompanies(false); //Pasif şirket listesi
        List<Long> companyManagersUserIds = pendingCompanies.stream().map(PassiveCompaniesView::getCompanyManagersUserId).distinct().collect(Collectors.toList()); //pasif şirketlerin manager'larının user id'leri,
        Map<Long, UserView> usersByIds = userService.getUsersByIds(companyManagersUserIds); //pasif şirketlerin manager'larınınn user view'ları

        List<PendingCompaniesResponseDTO> pendingCompaniesResponseDTOList = new ArrayList<>();

        pendingCompanies.forEach(passiveCompaniesView -> {

            pendingCompaniesResponseDTOList.add(PendingCompaniesResponseDTO.builder()
                            .userId(passiveCompaniesView.getCompanyManagersUserId())
                            .firstName(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getFirstName())
                            .lastName(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getLastName())
                            .email(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getEmail())
                            .userStatus(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getUserStatus())
                            .userRole(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getUserRole())
                            .companyId(passiveCompaniesView.getId())
                            .companyName(passiveCompaniesView.getCompanyName())
                            .numberOfEmployees(passiveCompaniesView.getEmployeeNumberLimit())
                            .subscriptionPlan(passiveCompaniesView.getSubscriptionPlan())

                    .build());
        });

        return pendingCompaniesResponseDTOList;
    }


    public void sendActivationMail(SendActivationRequestDTO sendActivationRequestDTO) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            String Token = jwtTokenManager.createToken(sendActivationRequestDTO.getUserId(), sendActivationRequestDTO.getEmail(), sendActivationRequestDTO.getCompanyId()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.TOKEN_CREATION_FAILED));
            String activationLink = "https://localhost:9090/admin/activation?token=" + Token;

            mailMessage.setTo(sendActivationRequestDTO.getEmail());
            mailMessage.setSubject("Activation Mail");
            mailMessage.setText("Your Activation Link : " + activationLink);
            mailMessage.setBcc("ertugrulsaiher@gmail.com");
            mailSender.send(mailMessage);

    }

    public void activateAccount(String token) {
        Long userId = jwtTokenManager.getUserIdFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN));
        String email = jwtTokenManager.getEmailFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN)); //Gerekli mi?
        Long companyId = jwtTokenManager.getCompanyIdFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN));



        userService.updateUserStatus(userId, UserStatus.ACTIVE);
        companyService.updateCompanyStatus(companyId,true);



    }
}
