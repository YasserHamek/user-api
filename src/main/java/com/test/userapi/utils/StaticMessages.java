package com.test.userapi.utils;

public class StaticMessages {

    public static final String INVALID_EMAIL = "Invalid email, please provide a valid one.";
    public static final String ONLY_ONE_ACCOUNT_PER_EMAIL_IS_ALLOWED = "Only one account per email is allowed.";
    public static final String ONLY_ADULT_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT = "Only adult are allowed to create an account.";
    public static final String ONLY_FRENCH_RESIDENTS_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT = "Only French residents are allowed to create an account.";
    public static final String INVALID_FILTER = "Invalid filter, please provide at least an email or a name.";
    public static final String BIRTHDAY_MAY_NOT_BE_NULL = "Birthday may not be null.";
    public static final String COUNTRY_MAY_NOT_BE_NULL_OR_EMPTY = "Country may not be null or empty.";
    public static final String EMAIL_MAY_NOT_BE_NULL_OR_EMPTY = "Email may not be null or empty.";
    public static final String MAY_NOT_BE_NULL_OR_EMPTY = " may not be null or empty.";

    private StaticMessages() {
        throw new IllegalStateException("Utility class");
    }
}
