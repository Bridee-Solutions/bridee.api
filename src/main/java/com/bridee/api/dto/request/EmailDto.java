package com.bridee.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    @NotBlank
    @Email
    private String to;
    @NotBlank
    @Email
    private String subject;
    @NotBlank
    private String message;
    private boolean isHTML;

}
