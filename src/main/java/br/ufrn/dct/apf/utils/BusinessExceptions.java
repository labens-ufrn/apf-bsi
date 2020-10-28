package br.ufrn.dct.apf.utils;

import br.ufrn.dct.apf.service.BusinessRuleException;

public class BusinessExceptions {
    public final static BusinessRuleException NOT_FOUND = new BusinessRuleException("NOT_FOUND");
    public final static BusinessRuleException WITHOUT_ATTRIBUTION = new BusinessRuleException("WITHOUT_ATTRIBUTION");
    public final static BusinessRuleException MEMBER_NOT_EXISTS = new BusinessRuleException("IS_NOT_MEMBER");
}
