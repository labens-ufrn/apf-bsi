package br.ufrn.dct.apf.count;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.service.BusinessRuleException;

public interface FunctionPointCount {
    
    public int calculeFunctionPoint(DataFunction df) throws BusinessRuleException;
    
    public int calculeFunctionPointILF(DataFunction df);
    
    public int calculeFunctionPointEIF(DataFunction df);

}
