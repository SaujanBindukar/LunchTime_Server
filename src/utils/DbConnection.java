//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection  implements Serializable {
    public static Connection cn;

    public DbConnection() {
    }

    public static Connection myConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
            return cn;
        } catch (SQLException | ClassNotFoundException var1) {
            System.out.println(var1);
            System.out.println("Database not connected from DBConnection");
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }

        return null;
    }

}
