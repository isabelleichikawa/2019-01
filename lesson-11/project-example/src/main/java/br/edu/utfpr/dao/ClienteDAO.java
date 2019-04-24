package br.edu.utfpr.dao;

import java.sql.*;

import br.edu.utfpr.dto.ClienteDTO;
import br.edu.utfpr.dao.ConexaoFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;
import java.sql.SQLException;

@Log
public class ClienteDAO {
    // Responsável por criar a tabela Cliente no banco.
    
    private Connection conexao = null;
    private ResultSet resultado = null;
	private PreparedStatement preparedstat = null;
    
    public ClienteDAO() {
        conexao = ConexaoFactory.conectar();
        try {

            log.info("Criando tabela cliente ...");
            conexao.createStatement().executeUpdate(
            "CREATE TABLE cliente (" +
						"id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_cliente_pk PRIMARY KEY," +
						"nome varchar(255)," +
						"telefone varchar(30)," + 
						"idade int," + 
                        "limiteCredito double," +
                        "id_pais int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void inserir(ClienteDTO cliente) {
        try {
            String sql = "INSERT INTO cliente VALUES (?,?,?,?,?)";
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            preparedstat = conexao.prepareStatement(sql, tipo, concorrencia);

            preparedstat.setString(1, cliente.getNome());
            preparedstat.setString(2, cliente.getTelefone());
            preparedstat.setInt(3, cliente.getIdade());
            preparedstat.setDouble(4, cliente.getLimiteCredito());
            //preparedstat.setInt(5, cliente.getPais().getId());
            preparedstat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
        } finally {
            ConexaoFactory.fechar(conexao, preparedstat, resultado);
        }
    }

    public void remover(ClienteDTO cliente) {
        try {
            String sql = "DELETE * FROM cliente WHERE id = ?";
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            preparedstat = conexao.prepareStatement(sql, tipo, concorrencia);

            preparedstat.setInt(1, cliente.getId());
            preparedstat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
        } finally {
            ConexaoFactory.fechar(conexao, preparedstat, resultado);
        }
    }
    
    public void atualizar(ClienteDTO cliente) {
        try {
            String sql = "UPDATE cliente"
                    + "SET nome = '?', telefone = '?', idade = ?, limiteCredito = ?, id_pais = ? WHERE id = ?";
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            preparedstat = conexao.prepareStatement(sql, tipo, concorrencia);

            preparedstat.setString(1, cliente.getNome());
            preparedstat.setString(2, cliente.getTelefone());
            preparedstat.setInt(3, cliente.getIdade());
            preparedstat.setDouble(4, cliente.getLimiteCredito());
            //preparedstat.setInt(5, cliente.getPais().getId());
            preparedstat.setInt(6, cliente.getId());
            preparedstat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
        } finally {
            ConexaoFactory.fechar(conexao, preparedstat, resultado);
        }
    }
    
    public ClienteDTO consultar(int id) {
        try {
            String sql = "SELECT * FROM cliente WHERE id = ?";
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            preparedstat = conexao.prepareStatement(sql, tipo, concorrencia);

            preparedstat.setInt(1, id);
            resultado = preparedstat.executeQuery();
            
            ClienteDTO cliente = null;
            while(resultado.next()){
                cliente = ClienteDTO.builder()
                        .id(resultado.getInt(1))
                        .nome(resultado.getString(2))
                        .telefone(resultado.getString(3))
                        .idade(resultado.getInt(4))
                        .limiteCredito(resultado.getInt(5))
                        //.pais(PaisDTO.builder()
                                //.id(resultado.getInt(6))
                                //.build())
                        .build();
            }
            return cliente;
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            return null;
        } finally {
            ConexaoFactory.fechar(conexao, preparedstat, resultado);
        }
    }

}