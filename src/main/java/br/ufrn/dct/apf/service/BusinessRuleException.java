package br.ufrn.dct.apf.service;

/**
 * Exception class of the business layer.
 *
 * @author Taciano Morais Silva
 * @version v-1.0
 * @since 15/05/2018, 19h27m
 */
public class BusinessRuleException extends Exception {

    /**
     * Parameters for error messages.
     */
    private final String[] parameters;

    public BusinessRuleException(String message, Exception ex) {
        super(message, ex);
        this.parameters = null;
    }

    public BusinessRuleException(String message) {
        super(message);
        this.parameters = null;
    }

    public BusinessRuleException(String message, String[] parameters) {
        super(message);
        this.parameters = parameters;
    }

    public String[] getParameters() {
        return parameters;
    }
}
