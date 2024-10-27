package com.bridee.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServico {
    
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;

      private String nome;
      private Boolean active;
      private String imagemUrl;
  
}
