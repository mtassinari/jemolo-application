package it.laziocrea.jemoloapp.config;

/**
 * Application constants.
 */
public final class Constants {
	
	/* Regex for acceptable CF 
	^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$
	tra le parentesi tonde vi è l'identificativo del comune di nascita (codice catastale). 
	Le lettere elencate (abcd ecc.) indicano il mese di nascita mentre l'ultima lettera è quella di controllo.*/
	public static final String CODICE_FISCALE_REGEX = "^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$";
	// Regex for acceptable eMail ^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$
	public static final String EMAIL_REGEX = "^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$";
    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    // Regex for acceptable password ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$
    // public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "it";
    public static final String ANONYMOUS_USER = "anonymoususer";

    private Constants() {
    }
}
