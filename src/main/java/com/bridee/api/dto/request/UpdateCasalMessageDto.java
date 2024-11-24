package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCasalMessageDto {

    @NotBlank
    private String message;

}
