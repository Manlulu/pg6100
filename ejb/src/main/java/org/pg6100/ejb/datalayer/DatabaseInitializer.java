package org.pg6100.ejb.datalayer;


import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@DataSourceDefinition(
        className = "org.apache.derby.jdbc.EmbeddedDataSource",
        name = "java:global/jdbc/ejbExample",
        user = "app",
        password = "app",
        databaseName = "ejbExample",
        properties = {"connectionAttributes=;create=true"}
)
public class DatabaseInitializer {
}
