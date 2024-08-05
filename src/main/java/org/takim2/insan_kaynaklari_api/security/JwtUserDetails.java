package org.takim2.insan_kaynaklari_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.service.UserService;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadByTokenId(Long userId,String role){

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        org.takim2.insan_kaynaklari_api.entity.User user = userService.getUserById(userId).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        return User.builder().username(user.getEmail()).password("").authorities(grantedAuthority).build(); // email c no fark etmez
    }

}
