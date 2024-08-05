package org.takim2.insan_kaynaklari_api.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;
import org.takim2.insan_kaynaklari_api.entity.enums.UserStatus;
import org.takim2.insan_kaynaklari_api.service.UserService;

@Component
@RequiredArgsConstructor
public class SaveAdmin {
    private final UserService userService;

    @PostConstruct
    public void saveAdmin(){
        userService.saveAdmin(User.builder().email("admin@example.com").password("123456789").userRole(UserRole.ADMIN).userStatus(UserStatus.ACTIVE).build());
    }
}
