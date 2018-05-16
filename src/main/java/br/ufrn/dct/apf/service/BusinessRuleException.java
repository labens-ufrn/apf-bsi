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
     * serialVersion.
     */
    private static final long serialVersionUID = 1187947337141924317L;
    
    /**
     * Parameters for error messages.
     */
    private String[] parameters;

    public BusinessRuleException(String message, Exception ex) {
        super(message, ex);
    }

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, String[] parameters) {
        super(message);
        this.parameters = parameters;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
}
