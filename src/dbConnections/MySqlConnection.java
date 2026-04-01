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

public class MySqlConnection {
    static Connection connection;
    static Statement stmt;

    public MySqlConnection() {
    }

    public static Statement getMySqlConnetion(String envName) throws Exception {
        String baseName = "environment/pad_" + envName;
        ResourceBundle rb = ResourceBundle.getBundle(baseName);
        String url = rb.getString("url");
        String userName = rb.getString("username");
        String password = rb.getString("password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, "divyansh", "Divyansh#202!");
        stmt = connection.createStatement();
        return stmt;
    }

    public static void close() {
        try {
            if(stmt != null) {
                stmt.close();
            }

            if(connection != null) {
                connection.close();
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
