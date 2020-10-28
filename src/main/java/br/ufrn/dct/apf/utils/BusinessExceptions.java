package br.ufrn.dct.apf.utils;

import br.ufrn.dct.apf.service.BusinessRuleException;

public class BusinessExceptions {
    public final static BusinessRuleException NOT_FOUND = new BusinessRuleException("NOT_FOUND");
    public final static BusinessRuleException WITHOUT_ATTRIBUTION = new BusinessRuleException("WITHOUT_ATTRIBUTION");
    public final static BusinessRuleException MEMBER_NOT_EXISTS = new BusinessRuleException("error.project.service.member.not.exists");
    public final static BusinessRuleException MEMBER_IS_NULL = new BusinessRuleException("error.project.service.member.is.null");
    public final static BusinessRuleException PROJECT_IS_NULL = new BusinessRuleException("error.project.service.project.is.null");
}
