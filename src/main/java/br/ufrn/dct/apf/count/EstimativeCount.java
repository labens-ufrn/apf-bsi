package br.ufrn.dct.apf.count;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.TransactionFunction;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.BusinessRuleException;

public class EstimativeCount extends AbstractFunctionPointCount {
    
    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(EstimativeCount.class);

    public int calculeFunctionPoint(Project project) throws BusinessRuleException {
        Set<DataFunction> dfs = project.getDataFunctions();
        Set<TransactionFunction> tfs = project.getTransactionFunctions();
        int funcitionalSize = 0;
        int dfSize = 0;
        int tfSize = 0;
        
        if (!dfs.isEmpty()) {
            for (DataFunction dataFunction : dfs) {
                int size = calculeFunctionPoint(dataFunction);
                dfSize += size;
                logger.debug(project + " - " + dataFunction + ": " + size + " PF");
            }
            funcitionalSize += dfSize;
        }
        
        if (!tfs.isEmpty()) {
            for (TransactionFunction transFunction : tfs) {
                int size = calculeFunctionPoint(transFunction); 
                tfSize += size; 
                logger.debug(project + " - " + transFunction + ": " + size + " PF");
            }
            funcitionalSize += tfSize;
        }
        return funcitionalSize;
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
    
    public int calculeFunctionPoint(TransactionFunction tf) throws BusinessRuleException {
        if (tf != null && tf.isEI()) {
            return calculeFunctionPointEI(tf);
        } else if (tf != null && tf.isEO()) {
            return calculeFunctionPointEO(tf);
        } else if (tf != null && tf.isEQ()) {
            return calculeFunctionPointEQ(tf);
        } else {
            throw new BusinessRuleException("error.count.indicative.datafunction.not.exists");
        }
    }
    
    public int calculeFunctionPointILF(DataFunction df) {
        return FunctionalSize.FUNCTION_POINT_ILF_LOW;
    }
    
    public int calculeFunctionPointEIF(DataFunction df) {
        return FunctionalSize.FUNCTION_POINT_EIF_LOW;
    }
    
    public int calculeFunctionPointEI(TransactionFunction tf) {
        return FunctionalSize.FUNCTION_POINT_EI_AVERAGE;
    }
    
    public int calculeFunctionPointEO(TransactionFunction tf) {
        return FunctionalSize.FUNCTION_POINT_EO_AVERAGE;
    }
    
    public int calculeFunctionPointEQ(TransactionFunction tf) {
        return FunctionalSize.FUNCTION_POINT_EQ_AVERAGE;
    }
}
