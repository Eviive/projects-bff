package dev.albertv.projects.bff.service;

import com.c4_soft.springaddons.security.oidc.starter.properties.SpringAddonsOidcProperties;
import dev.albertv.projects.bff.dto.LoginOptionDTO;
import lombok.Getter;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoginOptionsService {

    @Getter
    private final List<LoginOptionDTO> loginOptions = new ArrayList<>();

    public LoginOptionsService(
        final OAuth2ClientProperties oAuth2ClientProperties,
        final SpringAddonsOidcProperties springAddonsOidcProperties
    ) {
        final URI clientUri = springAddonsOidcProperties
            .getClient()
            .getClientUri()
            .orElse(null);

        if (clientUri == null) {
            return;
        }

        final Map<String, OAuth2ClientProperties.Registration> registrations = oAuth2ClientProperties.getRegistration();

        for (final Map.Entry<String, OAuth2ClientProperties.Registration> registrationEntry : registrations.entrySet()) {
            final OAuth2ClientProperties.Registration registration = registrationEntry.getValue();

            if (!"authorization_code".equals(registration.getAuthorizationGrantType())) {
                continue;
            }

            final String registrationId = registrationEntry.getKey();
            final String providerId = registration.getProvider();

            final String loginUri = "%s/oauth2/authorization/%s"
                .formatted(clientUri, registrationId);

            loginOptions.add(
                new LoginOptionDTO(
                    providerId,
                    loginUri
                )
            );
        }
    }

}
