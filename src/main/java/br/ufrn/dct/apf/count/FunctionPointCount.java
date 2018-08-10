package br.ufrn.dct.apf.count;

import br.ufrn.dct.apf.model.DataFunction;

public interface FunctionPointCount {

    public int calculeFunctionPointILF(DataFunction df);

    public int calculeFunctionPointEIF(DataFunction df);

}
