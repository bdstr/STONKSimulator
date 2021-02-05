package net.kloczkowski.STONKSimulator.API.security.util;

public class SecurityConstants {
    public static final String SECRET = "STONKS_SECRET_KEY";
    public static final long EXPIRATION_TIME = 3600_000; // 60 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
