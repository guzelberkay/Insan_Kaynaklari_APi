package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.dto.request.RegisterRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.RegisterResponseDto;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.UserServiceException;
import org.takim2.insan_kaynaklari_api.mapper.UserMapper;
import org.takim2.insan_kaynaklari_api.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public RegisterResponseDto register(RegisterRequestDto dto) {
        // Password ve repassword eşitliği kontrol edilir:
        if (!dto.getPassword().equals(dto.getRePassword())) {
            throw new UserServiceException(ErrorType.PASSWORDS_NOT_MATCHED);
        }

        // E-mail daha önce alınmış mı kontrol edilir:
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserServiceException(ErrorType.EMAIL_ALREADY_TAKEN);
        }
        // Kullanıcıyı kaydet
        User user = UserMapper.INSTANCE.registerRequestDtoToUser(dto);
        userRepository.save(user);
        RegisterResponseDto response = new RegisterResponseDto();
        response.setMessage("Kayıt Başarılı");
        return response;
    }
}
