package com.tomasruud.ruudit.helpers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class Flash {

    public static <T> void showViolations( Set<ConstraintViolation<T>> violations ) {

        violations.forEach( violation -> showError( violation.getMessage() ) );
    }

    public static void showSuccess( String message ) {

        showFlash( FacesMessage.SEVERITY_INFO, "Success!", message );
    }

    public static void showError( String message ) {

        showFlash( FacesMessage.SEVERITY_ERROR, "Oops!", message );
    }

    public static void showFlash( FacesMessage.Severity severity, String title,
                                  String message ) {

        FacesContext context = FacesContext.getCurrentInstance();

        if( context == null )
            return;

        context.getExternalContext().getFlash().setKeepMessages( true );
        context.addMessage( null, new FacesMessage( severity, title, message ) );
    }

}

