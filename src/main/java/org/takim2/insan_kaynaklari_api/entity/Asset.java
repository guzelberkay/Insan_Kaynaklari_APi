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
@Table(name = "tbl_assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String serialNumber;
    private String assetName;
    private Long assignedDate;
    private boolean isReturned;

    private String verificationStatus; // New field to track verification status ("pending", "verified", "rejected")

    @Column(length = 1000)
    private String note; // New field to allow personnel to add a note if the asset doesn't match

    @Builder.Default
    private Long createAt = System.currentTimeMillis();
    private Long updateAt;
}
