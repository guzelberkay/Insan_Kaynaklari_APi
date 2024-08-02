package org.takim2.insan_kaynaklari_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.SubscriptionPlan;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterRequestDto {
    @NotBlank(message = "username boş geçilemez.")
    String firstName;
    @NotBlank(message = "username boş geçilemez.")
    String lastName;
    @Size(min = 10, max = 10, message = "telefon numarası 10 haneli olmak zorundadır.")
    String phone;
    @Email(message = "Geçerli bir eposta adresi giriniz.")
    String email;
    String companyName;

    SubscriptionPlan subscriptionPlan;
    @NotBlank(message = "password boş geçilemez.")
    @Size(min = 8, max = 20, message = "password min 8 - max 20 karakter olabilir.")
    String password;
    String rePassword;
}
