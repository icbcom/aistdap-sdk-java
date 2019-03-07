package ru.icbcom.aistdapsdkjava.impl.datastore.auth;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Builder
@Getter
public class Tokens {
    private final String accessToken;
    private final int expiresIn;
    private final String refreshToken;
    private final Instant obtainedAt;

    public long secondsToExpiration() {
        Duration duration = Duration.between(Instant.now(), obtainedAt.plusSeconds(expiresIn));
        return duration.getSeconds();
    }
}
