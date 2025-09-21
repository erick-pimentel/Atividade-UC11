/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;



public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        String SQL= "INSERT INTO PRODUTOS (nome,valor,status) VALUES (?,?,?) ";
              
        try{
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL);
            
            prep.setString(1,produto.getNome());
            prep.setInt(2,produto.getValor());
            prep.setString(3,produto.getStatus());
            
            prep.executeUpdate();
            prep.close();
            conn.close();
            
            JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso!");
            
        } catch (SQLException erro) {
            
            JOptionPane.showMessageDialog(null,"Erro ao cadastrar:"+erro.getMessage());
        
        }          
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        String SQL = "SELECT*FROM PRODUTOS";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        
        try{
            
           conn = new conectaDAO().connectDB();
           prep = conn.prepareStatement(SQL);
           resultset = prep.executeQuery();
           
           while(resultset.next()){
               
               ProdutosDTO produto = new ProdutosDTO();
               produto.setId(Integer.valueOf(resultset.getInt("Id")));
               produto.setNome(resultset.getString("nome"));
               produto.setValor(resultset.getInt("valor"));
               produto.setStatus(resultset.getString("status"));
               
               lista.add(produto);
               
           }
            
           resultset.close();
           prep.close();
           conn.close();
           
        } catch(SQLException erro) {
            
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + erro.getMessage());
        }
        
        return lista;
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
    ArrayList<ProdutosDTO> lista = new ArrayList<>();
    
    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();
        
        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            
            lista.add(produto);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
  
    public void venderProduto(int idProduto) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";

    try(Connection conn = new conectaDAO().connectDB();
        PreparedStatement prep = conn.prepareStatement(sql)){

            prep.setString(1,"Vendido");  // novo status
            prep.setInt(2,idProduto);     // produto específico

            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
                
            } else {
                
               JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }
            
        } catch(SQLException e){
        
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        
      }
    }   

    
    
        
}

