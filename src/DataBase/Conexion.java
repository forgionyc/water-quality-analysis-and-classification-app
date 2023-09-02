/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBase;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author Knatos
 */
public class Conexion {
    //ATRIBUTOS
    Connection con = null;
    
    //Crear metodo para el path sea absoluto.
    public String conexionDB(){
        String sjbdc = "jdbc:sqlite";
        Path path = Paths.get("src/DataBase/reto4DB.s3db");
        String pathDB = sjbdc + ":\\" + path.toAbsolutePath();
        
        return pathDB;
    }


    //Establece la conexion con la base de datos CONSTRUCTOR
    public Conexion(){
        try {
        Class.forName("org.sqlite.JDBC");
        con=DriverManager.getConnection(conexionDB());
            System.out.println("Conexion establecida...");
        } catch (Exception e) {
            System.out.println("Conexion: "+e) ;          
        }
    }
    //Metodo para ejecutar sentencias sin retorno
    public int ejecutarSQL(String sentenciaSQL){
        try{
            PreparedStatement pst = con.prepareStatement(sentenciaSQL);
            pst.execute();
            return 1;
        } catch (SQLException e) {
            System.out.println("Sentencias"+e);
            return 0;
        }
    }
    //Metodo para ejecutar sentencias cuando se espera un resultado
    public ResultSet consultaSQL(String sentenciaSQL){
        try{
            PreparedStatement pst = con.prepareStatement(sentenciaSQL);
            ResultSet response = pst.executeQuery();
            return response;
        }catch (SQLException e){
            System.out.println("Consultas: "+e);
            return null;
        }
    }
}
