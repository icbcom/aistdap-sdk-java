package ru.icbcom.aistdapsdkjava.impl.datastore.auth.tokens;

public interface Tokens {
    long getSecondsToExpiration();

    String getAccessToken();

    int getExpiresIn();

    String getRefreshToken();

    java.time.Instant getObtainedAt();
}
