package org.pg6100.ul.ejb;


import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@DataSourceDefinition(name = "java:global/jdbc/unsecure_logging",
        className = "org.apache.derby.jdbc.EmbeddedDriver",
        url = "jdbc:derby:memory:DefaultUnit;create=true;user=app;password=app"
)
public class DataSourceCreator {
}
