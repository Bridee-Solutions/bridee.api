package com.bridee.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    private String to;
    private String subject;
    private String message;
    private boolean isHTML;

}
