package ru.icbcom.aistdapsdkjava.impl.datastore.auth.tokens;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Builder
@Getter
public class DefaultTokens implements Tokens {

    private final String accessToken;
    private final int expiresIn;
    private final String refreshToken;
    private final Instant obtainedAt;

    @Override
    public long getSecondsToExpiration() {
        Duration duration = Duration.between(Instant.now(), obtainedAt.plusSeconds(expiresIn));
        return duration.getSeconds();
    }

}
