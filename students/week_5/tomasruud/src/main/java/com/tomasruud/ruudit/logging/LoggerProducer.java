package com.tomasruud.ruudit.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Source: http://stackoverflow.com/a/21092326
 */
public class LoggerProducer {

    @Produces
    public Logger getLogger( InjectionPoint point ) {

        return Logger.getLogger( point.getType().getTypeName() );
    }
}
