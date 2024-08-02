package org.takim2.insan_kaynaklari_api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.RegisterRequestDto;
import org.takim2.insan_kaynaklari_api.dto.request.UserLoginRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.RegisterResponseDto;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(userService.register(dto)  );
    }
    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<String>> login(@RequestBody UserLoginRequestDto dto){

        return ResponseEntity.ok(userService.Login(dto));
    }

    @PostMapping("/forgotpassword") //Sonra
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> forgotPassword(@RequestParam String email){

        return ResponseEntity.ok(userService.forgotPassword(email));
    }

    @PostMapping("/resetcodecontrol") //Sonra
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> resetPasswordCodeControl(@RequestParam String code){

        return ResponseEntity.ok(userService.resetPasswordCodeControl(code));
    }

}
