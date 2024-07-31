package org.takim2.insan_kaynaklari_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Employee> employee;
    private Long startDate;
    private Long endDate;
    private Long breakTimeStart;
    private Long breakTimeEnd;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
