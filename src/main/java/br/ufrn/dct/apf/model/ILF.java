package br.ufrn.dct.apf.model;

/**
 * Internal Logical Files (ILF's, in Portuguese, Arquivos Lógico Interno - ALI's).
 * 
 * A user identifiable group of logically related data that resides entirely 
 * within the applications boundary and is maintained through external inputs.
 * 
 * Source: <a href="http://www.softwaremetrics.com/fpafund.htm">http://www.softwaremetrics.com/fpafund.htm</a>
 * 
 * @author Taciano Morais Silva
 * @since 25/03/2018
 */
public class ILF extends DataFunction {
    
    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;
    
    public ILF(String name) {
        super(name, "ILF");
    }
}
