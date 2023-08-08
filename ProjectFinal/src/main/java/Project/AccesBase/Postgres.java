package Project.AccesBase;

import java.sql.*;
import java.util.Vector;

public class Postgres {
    public static Connection connection() throws Exception {
        String url="jdbc:postgresql://localhost:5432/followup";
        String user="postgres";
        String mdp="1234";
        Connection connection= DriverManager.getConnection(url,user,mdp);
        connection.setAutoCommit(true);
        return connection;
    }
    public static void executeWithOutConnection(String sql) throws Exception {
        try (Connection connection=connection(); Statement statement=connection.createStatement()){
            statement.execute(sql);
        }
    }
    public static void executeWithConnection(String sql,Connection connection) throws Exception {
        try ( Statement statement=connection.createStatement()){
            statement.execute(sql);
        }
    }

    private static Vector<Object>[]getVectors(ResultSet resultSet) throws Exception {
        int size=resultSet.getMetaData().getColumnCount();
        Vector<Object>[]valiny=new Vector[size];
        for (int i = 0; i <size ; i++) {
            valiny[i]=new Vector<>();
        }
        while (resultSet.next()){
            for (int i = 0; i <size ; i++) {
                valiny[i].add(resultSet.getObject(i+1));
            }
        }
        return valiny;
    }
    public static Vector<Object>[]allWithConnection(String sql,Connection connection) throws Exception {
        try (Statement statement=connection.createStatement() ; ResultSet resultSet= statement.executeQuery(sql)){
            return getVectors(resultSet);
        }
    }
    public static Vector<Object>[]allWithOutConnection(String sql) throws Exception {
        try (Connection connection=connection();Statement statement=connection.createStatement() ; ResultSet resultSet= statement.executeQuery(sql)){
            return getVectors(resultSet);
        }
    }
}
