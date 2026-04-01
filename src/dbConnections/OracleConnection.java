//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package dbConnections;

import Encryption.EncryptPassword;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class OracleConnection {
    static Connection oracleConnection = null;
    static Statement stmt = null;

    public OracleConnection() {
    }

    public static Statement getOracleConnetion(String envname) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("environment/ebs_" + envname);
        String url = rb.getString("ebsUrl");
        String userName = rb.getString("ebsUserName");
        String password = rb.getString("ebsPassword");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        oracleConnection = DriverManager.getConnection(url, userName, EncryptPassword.decrypt(password));
        stmt = oracleConnection.createStatement();
        return stmt;
    }

    public static void close() {
        try {
            if(stmt != null) {
                stmt.close();
            }

            if(oracleConnection != null) {
                oracleConnection.close();
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
