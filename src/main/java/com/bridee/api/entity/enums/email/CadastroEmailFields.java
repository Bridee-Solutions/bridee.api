package com.bridee.api.entity.enums.email;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum CadastroEmailFields implements EmailFields{

    COUPLE_NAME("coupleName"), REGISTER_URL("registerUrl"),
    ASSESSOR_NAME("assessorName"), IS_ASSESSOR("isAssessor"),
    VERIFICATION_TOKEN("verificationToken");

    private String value;

    @Override
    public String getValue() {
        return value;
    }
}
