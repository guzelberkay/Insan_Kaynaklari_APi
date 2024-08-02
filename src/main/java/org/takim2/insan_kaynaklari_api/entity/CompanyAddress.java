package org.takim2.insan_kaynaklari_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_company_address")
public class CompanyAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Company company;
    private String address;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
