package it.laziocrea.jemoloapp.service.util;

import org.apache.commons.lang3.RandomStringUtils;

import com.mifmif.common.regex.Generex;

import ch.qos.logback.classic.Logger;
import it.laziocrea.jemoloapp.config.Constants;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;
    private static final int DEF_SHORTCOUNT = 8;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password.
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_SHORTCOUNT);
    	//Generex generex = new Generex(Constants.PASSWORD_REGEX);
    	//return generex.random();
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key.
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key.
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a unique series to validate a persistent token, used in the
     * authentication remember-me mechanism.
     *
     * @return the generated series data.
     */
    public static String generateSeriesData() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate a persistent token, used in the authentication remember-me mechanism.
     *
     * @return the generated token data.
     */
    public static String generateTokenData() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }
}
