package Utils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {

    private static int instances = 0;


    /**
     * Loads the properties from the file
     * @param fromWhere the file location
     * @return the property file content
     */

    private InputStream readAllProperties(String fromWhere){
        return getClass().getResourceAsStream(fromWhere);
    }

    public ConnectionBuilder(String propertyFileLocation) throws IOException, ClassNotFoundException, SQLException {
        ++instances;

        System.getProperties().load(readAllProperties(propertyFileLocation));

        if(instances <=  1) Class.forName(System.getProperty("driverToUse"));
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(System.getProperty("connectionUrl"),System.getProperty("userDatabase"),System.getProperty("passwordDatabase"));
    }
}
