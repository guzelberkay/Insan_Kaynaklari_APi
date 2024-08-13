package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.takim2.insan_kaynaklari_api.Vw.UserView;
import org.takim2.insan_kaynaklari_api.dto.request.ChangePasswordDTO;
import org.takim2.insan_kaynaklari_api.dto.request.UserLoginRequestDto;
import org.takim2.insan_kaynaklari_api.dto.request.UserUpdateRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.entity.CompanyManager;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.entity.enums.UserStatus;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.CompanyMapper;
import org.takim2.insan_kaynaklari_api.repository.CompanyManagerRepository;
import org.takim2.insan_kaynaklari_api.repository.CompanyRepository;
import org.takim2.insan_kaynaklari_api.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.takim2.insan_kaynaklari_api.dto.request.RegisterRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.RegisterResponseDto;
import org.takim2.insan_kaynaklari_api.mapper.UserMapper;
import org.takim2.insan_kaynaklari_api.util.CodeGenerator;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenManager jwtTokenManager;
    private final CodeGenerator codeGenerator;
    private final CompanyManagerRepository companyManagerRepository;
    private final JavaMailSender mailSender;

    public void saveAdmin(User user) {
        userRepository.save(user);
    }

    public Map<Long,UserView> getUsersByIds(List<Long> companyManagersUserIds) {
        List<UserView> userViewList = userRepository.findAllByIds(companyManagersUserIds);
        Map<Long,UserView> result = userViewList.stream().collect(Collectors.toMap(UserView::getId, userView -> userView));
        return result;
    }
    public void updateUserStatus(Long id, UserStatus userStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        user.setUserStatus(userStatus);
        userRepository.save(user);
    }





    public RegisterResponseDto register(RegisterRequestDto dto) {

        //TODO düzenlenecek

        // Password ve rePassword eşitliği kontrol edilir:
        if (!dto.getPassword().equals(dto.getRePassword())) {
            throw new HumanResourcesAppException(ErrorType.PASSWORD_MISMATCH);
        }

        // E-mail daha önce alınmış mı kontrol edilir:
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new HumanResourcesAppException(ErrorType.EMAIL_EXIST);
        }

        //Kontrollerden başarılı bir şekilde geçildiyse dto'dan gelen bilgilerle Company nesnesi oluşturulur.

        Company company = CompanyMapper.INSTANCE.toCompany(dto);
        company.setCompanyName(dto.getCompanyName());
        company.setEmployeeLimitLevel(dto.getEmployeeLimitLevel());
        company.setEmployeeNumberLimit(dto.getEmployeeLimitLevel().getEmployeeLimit());
        company.setSubscriptionPlan(dto.getSubscriptionPlan());

        // RegisterRequestDto'dan User nesnesi oluştur
        User user = UserMapper.INSTANCE.toUser(dto);

        user = userRepository.save(user);
        CompanyManager companyManager = new CompanyManager();
        companyManager.setUser(user);
        companyManager = companyManagerRepository.save(companyManager);
        company.setCompanyManager(companyManager);

        company = companyRepository.save(company);

        RegisterResponseDto response = new RegisterResponseDto();
        response.setMessage("Kayıt Başarılı");
        return response;
    }

    public String Login(UserLoginRequestDto dto){
        Optional<User> optionalByUser = userRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if(optionalByUser.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.PASSWORD_MISMATCH);
        }

        if(!optionalByUser.get().getUserStatus().equals(UserStatus.ACTIVE)){
            throw new HumanResourcesAppException(ErrorType.USER_STATUS_NOT_ACTIVE);
        }

        return jwtTokenManager.createToken( optionalByUser.get().getId(), optionalByUser.get().getUserRole() );
    }

    //Şifre Yenileme mail gönderme eklenince düzenlenicek
    public ResponseDTO<Boolean> forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }
        userOptional.get().setRePasswordCode(codeGenerator.codeGenerator());

        userRepository.save(userOptional.get());

        // Şifre yenileme maili gönderilecek
        String link = "http://localhost:3000/changeMyPassword";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setBcc("ertugrulsaliher@gmail.com");
        mailMessage.setSubject("Forgot Password Reset Link");
        mailMessage.setText("Doğrulama Kodunuz :"+ userOptional.get().getRePasswordCode() +" ,şifre yenilemek için linke tıklayın  " +link);
        mailMessage.setTo(email);
        mailSender.send(mailMessage);


        return ResponseDTO.<Boolean>builder()
                .code(200)
                .message("emaile şifre gönderildi. Mailinizi kontrol ediniz.")
                .data(true)
                .build();
    }

    public ResponseDTO<Boolean> resetPasswordCodeControl(ChangePasswordDTO changePasswordDTO){
        Optional<User> userOptional = userRepository.findByRePasswordCode(changePasswordDTO.getCode());
        if (userOptional.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }

        userOptional.get().setRePasswordCode(null);
        userOptional.get().setPassword(changePasswordDTO.getPassword());
        userRepository.save(userOptional.get());

        return ResponseDTO.<Boolean>builder()
                .code(200)
                .message("Şifre yenileme işlemi başarılı.")
                .data(true)
                .build();
    }
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Boolean userUpdate(UserUpdateRequestDto dto){
        Optional<Long> userId = jwtTokenManager.getUserIdFromToken(dto.getToken());
        //token kontrolü
        if(userId.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> userOptional = userRepository.findById(userId.get());
        //user var mı kontrolü
        if (userOptional.isPresent()){

            User user = userOptional.get();
            // girilen veriler boş mu kontrolü
            if (!dto.getAvatar().isEmpty()){
                user.setAvatar(dto.getAvatar());
            }
            if(!dto.getFirstName().isEmpty()){
                user.setFirstName(dto.getFirstName());
            }
            if(!dto.getLastName().isEmpty()){
                user.setLastName(dto.getLastName());
            }
            userRepository.save(user);
            return true;
        }else{
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }
    }
}
