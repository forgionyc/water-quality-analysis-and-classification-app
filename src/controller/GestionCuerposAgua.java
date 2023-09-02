/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import Model.CuerpoDeAgua;
import java.sql.ResultSet;
/**
 *
 * @author Knatos
 */
public class GestionCuerposAgua {
    
    public static ArrayList<CuerpoDeAgua> cuerpos = new ArrayList<CuerpoDeAgua>(); 
    
    public static void crearCuerposAgua(String tipoCuerpo, String tipoAgua, double irca, String nombre, int idCuerpo, String municipio){
        CRUD.insertar(tipoCuerpo, tipoAgua, irca, nombre, idCuerpo, municipio);
        //cuerpos.add(new CuerpoDeAgua(tipoCuerpo, tipoAgua, irca, nombre, idCuerpo, municipio));
    }
    
    //metodo para listar los cuerpos y almacenar en el array list
    public static ArrayList<String> listarCuerpos(){
        ArrayList<String> listaAdicionar = new ArrayList<String>();
        ResultSet respuesta = CRUD.listar();
        //limpiar el arraylist, limpiar los datos de la matriz
        cuerpos.clear();
        //validacion de si existen datos en la base de datos, si no existen retoma vacion.
        if(respuesta!=null){
            //recorre los elementos para armar la lista.
            try{
                while(respuesta.next()){
                    //llenar el array list para visualizar en la lista adicionar/procesar
                    listaAdicionar.add(
                            respuesta.getString("id_cuerpo")+" "+
                            respuesta.getString("nombre_cuerpo")+" "+
                            respuesta.getString("municipio")+" "+
                            respuesta.getString("tipo_cuerpo")+" "+
                            respuesta.getString("tipo_agua")+" "+
                            respuesta.getString("irca")
                    );
                    //llenando el array list con los objetos de cuerpo de agua provenientes de la base de datos
                    cuerpos.add(new CuerpoDeAgua(
                        respuesta.getString("tipo_cuerpo"),
                        respuesta.getString("tipo_agua"),
                        respuesta.getDouble("irca"),
                        respuesta.getString("nombre_cuerpo"),
                        respuesta.getInt("id_cuerpo"),
                        respuesta.getString("municipio")
                        )
                    );
                }
                
            }catch (Exception e){
            }
        }
        return listaAdicionar;
    }
    
    public static CuerpoDeAgua consultaID(String idbusqueda){
        ResultSet respuesta=null;
        respuesta = idbusqueda.isEmpty()?null:CRUD.buscarID(Integer.parseInt(idbusqueda));
        
        CuerpoDeAgua consulta = null;
        if (respuesta != null){
        try {
            while(respuesta.next()){
                        consulta = new CuerpoDeAgua(
                        respuesta.getString("tipo_cuerpo"),
                        respuesta.getString("tipo_agua"),
                        respuesta.getDouble("irca"),
                        respuesta.getString("nombre_cuerpo"),
                        respuesta.getInt("id_cuerpo"),
                        respuesta.getString("municipio")
                        );       
            }
        } catch (Exception e) {
        }
        }
        return  consulta;
    }
    
    public static void editarCuerposAgua(String tipoCuerpo,
            String tipoAgua,
            double irca,
            String nombre,
            int idCuerpo,
            String municipio){
        CRUD.modificar(tipoCuerpo, tipoAgua, irca, nombre, idCuerpo, municipio);
        
    }
    
    public static void eliminarCuerposAgua(String idbusqueda){
        CRUD.eliminar(Integer.parseInt(idbusqueda));
    }
    
    public static ArrayList<String> procesamiento(){
        ArrayList<String> listar = new ArrayList<String>();
        
        int cont = 0;
        
        for (int i=0; i<cuerpos.size(); i++){
            listar.add("Nivel de riesgo id."+cuerpos.get(i).getIdCuerpo()+": "+cuerpos.get(i).nivel());
        
            if (cuerpos.get(i).nivel() == "SIN RIESGO" || cuerpos.get(i).nivel() == "BAJO" || cuerpos.get(i).nivel()== "MEDIO" )
            {
                cont = cont + 1;
            }
            
        }
        listar.add("Nivel de riesgo MEDIO o INFERIOR: "+ Integer.toString(cont));
        
        boolean find = false;
        
        for (int i=0; i<cuerpos.size(); i++){
            if(cuerpos.get(i).nivel() == "MEDIO")
            {   
            listar.add(cuerpos.get(i).getNombre()); 
            find = true;
            }    
        }
        
        if(find == false)
            listar.add("Cuerpos de agua con nivel de riesgo MEDIO: NA");
        
        double menor = cuerpos.get(0).getIrca();
        String nomb = cuerpos.get(0).getNombre();
        int id = cuerpos.get(0).getIdCuerpo();
        
        for (int i = 0; i < cuerpos.size(); i++) {
            if (cuerpos.get(i).getIrca() < menor){
                menor = cuerpos.get(i).getIrca();
                nomb = cuerpos.get(i).getNombre();
                id = cuerpos.get(i).getIdCuerpo();
                }
        }
        
        listar.add("Cuerpo de agua con el menor IRCA "+nomb+" "+id);
        
        return listar;
    }
}
