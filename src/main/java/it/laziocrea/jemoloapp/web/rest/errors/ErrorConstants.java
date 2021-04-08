package it.laziocrea.jemoloapp.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://roster.regione.lazio.it/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI CODICE_FISCALE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/codice-fiscale-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");
	public static final URI PEC_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/pec-already-used");
	public static final URI PIVA_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/piva-already-used");
	public static final URI CF_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/codice-fiscale-not-found");

    private ErrorConstants() {
    }
}
