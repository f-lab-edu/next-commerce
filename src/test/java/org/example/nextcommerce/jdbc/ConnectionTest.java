package org.example.nextcommerce.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Fail.fail;

public class ConnectionTest {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {

        try(Connection con =
                    DriverManager.getConnection(
                            DBInfo.DB_URL.info(),
                            DBInfo.DB_USER_NAME.info(),
                            DBInfo.DB_PASSWORD.info())){
            System.out.println(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
