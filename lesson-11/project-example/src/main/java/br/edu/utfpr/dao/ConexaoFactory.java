package br.edu.utfpr.dao;

import java.sql.*;

public class ConexaoFactory {
    
    public static Connection conectar(){
        try{
            return DriverManager.getConnection("jdbc:derby:memory:database;create=true");
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            throw new RuntimeException(e);
        }
    }
        
        public static void fechar(Connection conexao, PreparedStatement pstat, ResultSet resultado) {
        try {
            if (resultado != null) {
                resultado.close();
                pstat.close();
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
        }
    }
}
