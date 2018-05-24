package br.ufrn.dct.apf.count;

import br.ufrn.dct.apf.model.DataFunction;

public abstract class AbstractFunctionPointCount implements FunctionPointCount {
    
    public int calculeFunctionPointILF(DataFunction df) {
        return 0;
    }
    
    public int calculeFunctionPointEIF(DataFunction df) {
        return 0;
    }
}
