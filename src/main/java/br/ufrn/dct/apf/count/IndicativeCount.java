package br.ufrn.dct.apf.count;

import java.util.Set;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.EIF;
import br.ufrn.dct.apf.model.ILF;
import br.ufrn.dct.apf.model.UserStory;

public class IndicativeCount {
    
    public int calculeFunctionPoint(UserStory us) {
        Set<DataFunction> df = us.getDataFunction();
        
        int i = 0;
        for (DataFunction dataFunction : df) {
            i += calculeFunctionPoint(dataFunction);
        }
        return i;
    }
    
    public int calculeFunctionPoint(DataFunction df) {
        if (df.getType().equals("ILF")) return calculeFunctionPoint((ILF)df);
        if (df.getType().equals("EIF")) return calculeFunctionPoint((EIF)df);
        return 0;
    }
    
    public int calculeFunctionPoint(ILF df) {
        return 35;
    }
    
    public int calculeFunctionPoint(EIF df) {
        return 15;
    }
}
