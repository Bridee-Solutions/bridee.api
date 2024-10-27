package com.bridee.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.FetchType;
import lombok.Builder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Casamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    
    private LocalDate dataInicio;
    
    private LocalDate dataFim;

    private Integer totalConvidados;
    
    @ManyToOne
    @JoinColumn
    private Casal casal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Assessor assessor;
    
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    
    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
