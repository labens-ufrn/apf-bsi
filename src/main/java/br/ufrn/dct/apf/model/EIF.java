package br.ufrn.dct.apf.model;

/**
 * Internal Logical Files (ILF's, in Portuguese, Arquivos Lógico Interno - ALI's).
 *
 * External Interface Files (EIF’s, in Portuguese, Arquivos Interface Externa - AIE's).
 *
 * A user identifiable group of logically related data that is used for reference
 * purposes only. The data resides entirely outside the application and is
 * maintained by another application.
 *
 * The external interface file is an internal logical file for another application.
 *
 * Source: <a href="http://www.softwaremetrics.com/fpafund.htm">http://www.softwaremetrics.com/fpafund.htm</a>
 *
 * @author Taciano Morais Silva
 * @since 25/03/2018
 */
public class EIF extends DataFunction {

    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;

    public EIF(String name) {
        super(name, "EIF");
    }
}
