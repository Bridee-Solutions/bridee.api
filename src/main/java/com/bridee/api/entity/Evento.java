package com.bridee.api.entity;

import java.time.LocalDate;

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
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn
    private Integer tipoEvento;
    
    private LocalDate dataInicio;
    
    private LocalDate dataFim;
    
    @ManyToOne
    @JoinColumn
    private Integer casalId;
    
    @ManyToOne
    @JoinColumn
    private Integer assessorId;
    
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    
    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
    
    @CreatedDate
    @Column(updatable = false)
    private String createdAt;
    
    @LastModifiedDate
    @Column(insertable = false)
    private String updatedAt;
}
