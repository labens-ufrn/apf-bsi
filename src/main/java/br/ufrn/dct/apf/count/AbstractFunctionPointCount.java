package br.ufrn.dct.apf.count;

import br.ufrn.dct.apf.model.DataFunction;

public abstract class AbstractFunctionPointCount implements FunctionPointCount {
    
    protected static final String TYPE_ILF = "ILF";
    
    protected static final String TYPE_EIF = "EIF";
    
    public int calculeFunctionPointILF(DataFunction df) {
        return 0;
    }
    
    public int calculeFunctionPointEIF(DataFunction df) {
        return 0;
    }
}
