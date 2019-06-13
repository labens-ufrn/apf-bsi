package br.ufrn.dct.apf.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.dto.DataFunctionDTO;
import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.service.UserService;

public abstract class AbstractController {

    private User overridenCurrentUser;

    @Autowired
    private UserService userService;

    protected void setUserAuth(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("authorities", getRoles(user));
    }

    protected String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null)
            return null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    protected User getCurrentUser() {
        if (overridenCurrentUser != null) {
            return overridenCurrentUser;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        return userService.findUserByEmail(auth.getName());
    }

    protected String getRoles(User user) {
        Set<Role> roles = user.getRoles();

        StringBuilder bld = new StringBuilder();
        bld.append("[");
        int size = roles.size();
        int i = 1;
        for (Role role : roles) {
            bld.append(role.getRoleName());
            if (i > 0 && i < size) {
                bld.append(", ");
                i += 1;
            }
        }
        bld.append("]");
        return bld.toString();
    }
    
    protected DataFunction convertToEntity(DataFunctionDTO dfDTO) {
        DataFunction df = new DataFunction();
        if (dfDTO.getType().equals(DataFunction.TYPE_ILF)) {
            df = DataFunction.createILF(dfDTO.getName());
        }
        if (dfDTO.getType().equals(DataFunction.TYPE_EIF)) {
            df = DataFunction.createEIF(dfDTO.getName());
        }
        df.setRecordElementTypes(dfDTO.getRecordElementTypes());
        df.setDataElementTypes(dfDTO.getDataElementTypes());
        df.setProject(dfDTO.getProject());
        df.setUserStory(dfDTO.getUserStory());
        
        return df;
    }
    
    protected DataFunctionDTO convertToDto(DataFunction df) {
        DataFunctionDTO dto = new DataFunctionDTO();
        dto.setId(df.getId());
        dto.setName(df.getName());
        dto.setType(df.getType());
        dto.setRecordElementTypes(df.getRecordElementTypes());
        dto.setDataElementTypes(df.getDataElementTypes());
        dto.setProject(df.getProject());
        dto.setUserStory(df.getUserStory());
        
        return dto;
    }
}
