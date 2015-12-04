package jsec.controller;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import jsec.facade.UserFacade;
import jsec.util.Resources;

/**
 * Controller for changing a user password with user and passwordConfirmation.
 * 
 * @author Karl Nicholas
 *
 */
@Model
public class ChangePassword extends Principal {
    
    @Inject UserFacade userFacade;
    private String passwordConfirmation;
    
    /**
     * Check user.password against passwordConfirmation, encode password, and merge user.
     * @return Navigation to /views/account.xhtml
     */
    public String updatePassword() {
        try {
            // check password confirmation.
            if ( !getUser().getPassword().equals(passwordConfirmation) ) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Passwords Must Match", ""));
                return null;
            }
            // update user
            userFacade.merge(userFacade.updatePassword(getUser()));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update failed", Resources.getRootErrorMessage(e)));
            return null;
        }
        // message and navigation
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password Updated", ""));
        return "/views/account.xhtml";
    }

    /**
     * Password Confirmation Field
     * @return passwordConfirmation
     */
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * Password Confirmation Field
     * @param passwordConfirmation
     */
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}