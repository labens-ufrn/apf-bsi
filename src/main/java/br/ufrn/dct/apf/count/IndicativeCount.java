package br.ufrn.dct.apf.count;

import java.util.Set;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.UserStory;

public class IndicativeCount extends AbstractFunctionPointCount {
    
    private static final int functionPointILF = 35;
    
    private static final int functionPointEIF = 15;
    
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
        if (df.getType().equals("ILF")) return functionPointILF;
        if (df.getType().equals("EIF")) return functionPointEIF;
        return 0;
    }
}
