package com.bridee.api.entity.enums.email.fields;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum CadastroEmailFields implements EmailFields{

    COUPLE_NAME("coupleName"), REGISTER_URL("registerUrl"),
    ASSESSOR_NAME("assessorName"), IS_ASSESSOR("isAssessor"),
    VERIFICATION_TOKEN("verificationToken"), BODY_IMAGE("bodyImage"),
    LOGO("logoImage");

    private String value;

    @Override
    public String getValue() {
        return value;
    }
}
