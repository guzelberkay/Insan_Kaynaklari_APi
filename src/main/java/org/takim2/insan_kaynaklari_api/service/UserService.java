package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.UserView;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.enums.UserStatus;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


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





}
