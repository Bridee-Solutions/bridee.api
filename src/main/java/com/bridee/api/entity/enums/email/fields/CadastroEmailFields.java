package com.bridee.api.entity.enums.email.fields;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CadastroEmailFields implements EmailFields{

    COUPLE_NAME("coupleName"), REGISTER_URL("registerUrl"),
    ASSESSOR_NAME("assessorName"), IS_ASSESSOR("isAssessor"),
    VERIFICATION_TOKEN("verificationToken"), BODY_IMAGE("bodyImage"),
    LOGO("logoImage");

    private final String value;

    @Override
    public String getValue() {
        return value;
    }
}
