/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibs.turnos.controlador;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author marco
 */
public class ConexionDB {
 /*  LOCAL */
private String USERNAME ="root";
private String PASSWORD = "admin";
private String HOST = "localhost";

/*  TIBS
private String USERNAME ="root";
private String PASSWORD = "Tibs2019!";
private String HOST = "127.0.0.2";
*/
    
private String PORT = "3306";
private String DATABASE = "turnos_tibs";
private String CLASSNAMEDRIVER = "com.mysql.jdbc.Driver";
private String URL="jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
private Connection con;

public ConexionDB(){

    try{
    Class.forName(CLASSNAMEDRIVER);
    con = (Connection) DriverManager.getConnection(URL,USERNAME,PASSWORD);
     System.out.println("**** Conexión a DB Exitosa *****");
    }catch (ClassNotFoundException e ){
        System.err.println("ERROR: "+e);
         System.out.println("**** Conexión a DB No Exitosa *****");
    }catch (SQLException e ){
        System.out.println("ERROR: "+e);
         System.out.println("**** Conexión a DB No Exitosa *****");
    }

}

public Connection getConexion(){
return con;
}

    public static void main(String[] args) {
        ConexionDB conn = new ConexionDB();
       
    }
}
