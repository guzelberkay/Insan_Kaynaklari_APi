package org.takim2.insan_kaynaklari_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;



    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
