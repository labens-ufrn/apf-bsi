package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;

/**
 * Abstract business class containing helper method for the business layer of the APF system.
 * 
 * @author Taciano Morais Silva
 * @version v-1.0
 * @since 15/05/2018, 19h27m
 */
public class AbstractService {
    
    protected static final int ACTIVE = 1;
    
    protected AbstractService() {
    }
    
    /**
     * Checks whether the object passed as a parameter is null.
     * @param obj An object.
     * @return True if the passed object is null.
     */
    protected boolean checkNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
}
    
    protected Member createMember(Project project, User user) {
        Member member = new Member();
        member.setProject(project);
        member.setUser(user);
        member.setCreatedOn(GregorianCalendar.getInstance().getTime());
        return member;
    }
}
