package br.ufrn.dct.apf.count;

import java.util.Set;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.BusinessRuleException;

public class IndicativeCount extends AbstractFunctionPointCount {

    private static final int FUNCTION_POINT_ILF = 35;

    private static final int FUNCTION_POINT_EIF = 15;

    public int calculeFunctionPoint(Project project) throws BusinessRuleException {
        Set<UserStory> userStories = project.getUserStories();

        int i = 0;
        for (UserStory us : userStories) {
            i += calculeFunctionPoint(us);
        }
        return i;
    }

    public int calculeFunctionPoint(UserStory us) throws BusinessRuleException {
        Set<DataFunction> df = us.getDataFunctions();

        int i = 0;
        for (DataFunction dataFunction : df) {
            i += calculeFunctionPoint(dataFunction);
        }
        return i;
    }
    
    public int calculeFunctionPoint(DataFunction df) throws BusinessRuleException {
        if (df != null && df.isILF()) {
            return calculeFunctionPointILF(df);
        } else if (df != null && df.isEIF()) {
            return calculeFunctionPointEIF(df);
        } else {
            throw new BusinessRuleException("error.count.indicative.datafunction.not.exists");
        }
    }
    
    public int calculeFunctionPointILF(DataFunction df) {
        return FUNCTION_POINT_ILF;
    }
    
    public int calculeFunctionPointEIF(DataFunction df) {
        return FUNCTION_POINT_EIF;
    }
}
