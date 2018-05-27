package br.ufrn.dct.apf.count;

import java.util.Set;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.UserStory;

public class IndicativeCount extends AbstractFunctionPointCount {
    
    private static final int FUNCTION_POINT_ILF = 35;
    
    private static final int FUNCTION_POINT_EIF = 15;
    
    public int calculeFunctionPoint(Project project) {
        Set<UserStory> userStories = project.getUserStories();
        
        int i = 0;
        for (UserStory us : userStories) {
            i += calculeFunctionPoint(us);
        }
        return i;
    }
    
    public int calculeFunctionPoint(UserStory us) {
        Set<DataFunction> df = us.getDataFunction();
        
        int i = 0;
        for (DataFunction dataFunction : df) {
            i += calculeFunctionPoint(dataFunction);
        }
        return i;
    }
    
    public int calculeFunctionPoint(DataFunction df) {
        if (df.getType().equals(TYPE_ILF)) return FUNCTION_POINT_ILF;
        if (df.getType().equals(TYPE_EIF)) return FUNCTION_POINT_EIF;
        return 0;
    }
}
