package com.example.avinash.jsonobjectdemo.api;

/**
 * Created on 30-01-2017.
 */

public class Url {

    /**
     * App environment enum.
     * (can be more like: UAT/STAGING/DEBUG...)
     */
    enum Environment {
        DEVELOPMENT, TESTING,
        PRODUCTION
    }

    /**
     * This defines the environment or the server to which the app is pointing to.
     */
    private static Environment environment = Environment.DEVELOPMENT;

    /**
     * Check the current environment of the app.
     *
     * @return current environment value of the app.
     */
    public static Environment getEnvironment() {
        return environment;
    }

    // Domain/Server
    public static String DEVELOPMENT_DOMAIN = "";
    public static String TESTING_DOMAIN = "";
    public static String PRODUCTION_DOMAIN = "";

    /**
     * Get domain based on the environment of the app.
     *
     * @return domain based on the app environment. (e.g. development/production)
     */
    private static String getDomain() {
        switch (environment) {
            case DEVELOPMENT:
                return DEVELOPMENT_DOMAIN;
            case PRODUCTION:
                return PRODUCTION_DOMAIN;
            case TESTING:
                return TESTING_DOMAIN;
            default:
                return "";
        }
    }

    /**
     * Get API prefix.
     *
     * @return string with combination of domain and method.
     */
    private static String getAPIPrefix() {
        return getDomain() + "api/" + "values/";
    }

}
