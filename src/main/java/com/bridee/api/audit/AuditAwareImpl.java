package com.bridee.api.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String currentAuditor = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(currentAuditor);
    }

}
