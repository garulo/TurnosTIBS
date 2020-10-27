/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibs.turnos.controlador;

import com.tibs.turnos.dao.Oficina;
import com.tibs.turnos.dao.ProxTurnos;
import com.tibs.turnos.dao.Servicio;
import com.tibs.turnos.dao.Turno;
import com.tibs.turnos.dao.Usuario;
import com.tibs.turnos.dao.Ventanilla;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 *
 * @author marco garulo
 */
public class Consultas extends ConexionDB {

    /*
     * USUARIOS
     */
    //AUTENTICACIÓN DE USUARIO
    public Usuario autenticacion(String usuario, String password) {

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consulta = "select * from usuarios_central where usuario = ? and password= ? and estatus ='1';";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, usuario);
            pst.setString(2, password);

            rs = pst.executeQuery();

            Usuario user = new Usuario();
            while (rs.next()) {
                user = new Usuario();
                user.setId(rs.getString("id"));
                user.setUsuario(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setNombre(rs.getString("nombre"));
                user.setEmail(rs.getString("email"));
                user.setIdRol(rs.getString("idRol"));
                user.setEstatus(rs.getString("estatus"));
                user.setFecha(rs.getString("fecha"));

            }

            System.out.println("USUARIO ::: " + user.getNombre());
            return user;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return null;
    }
    //TEST de autenticacion
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     Usuario user = new Usuario();
     user = co.autenticacion("UserAdminTest", "PassTest");
     System.out.println("idRol ::: "+user.getIdRol());

     }*/

    //agregarUsuarioVentanilla 
    public boolean agregarUsuarioVentanilla(String txtAgregarUUsuario, String txtAgregarUNombre, String txtAgregarUPass, String txtAgregarUEmail, String txtAgregarUVent, String txtAgregarUEstatus, String useragr) {

        PreparedStatement pst = null;
        String uid = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`usuarios_central` (`id`, `usuario`, `password`, `nombre`, `email`, `idRol`, `estatus`, `fecha`, `ventanilla`) VALUES (?,?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid);
            pst.setString(2, txtAgregarUUsuario);
            pst.setString(3, txtAgregarUPass);
            pst.setString(4, txtAgregarUNombre);
            pst.setString(5, txtAgregarUEmail);
            pst.setString(6, "3");
            pst.setString(7, txtAgregarUEstatus);
            pst.setString(8, fecha);
            pst.setString(9, txtAgregarUVent);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Agregado :::" + txtAgregarUUsuario);

                PreparedStatement pstz = null;
                DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calz = Calendar.getInstance();
                String fechaz = dateFormatz.format(calz.getTime());
                String consultaz = new String();
                pstz = null;
                dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                calz = Calendar.getInstance();
                fechaz = dateFormatz.format(calz.getTime());
                consultaz = new String();
                consultaz = "UPDATE `turnos_tibs`.`ventanillas` SET `UsuarioAsignado`= ? , `UsuarioLastUpdate`= ? , `FechaLastUpdate`= ?  WHERE (`NombreVentanilla`= ? );";
                pstz = getConexion().prepareStatement(consultaz);

                pstz.setString(1, txtAgregarUUsuario);
                pstz.setString(2, useragr);
                pstz.setString(3, fechaz);
                pstz.setString(4, txtAgregarUVent);

                System.out.println("pstz UPDATE ::: " + pstz);

                if (pstz.executeUpdate() == 1) {
                    System.out.println("Usuario - Ventanilla Agregados :::" + txtAgregarUVent + " ::: " + txtAgregarUUsuario);
                } else {
                    System.out.println("Usuario - Ventanilla ERROR AGREGAR :::" + txtAgregarUVent + " ::: " + txtAgregarUUsuario);
                }

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarVentanilla
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.agregarUsuarioVentanilla("Usuario", "Nombre", "Pass", "Email", "04", "1", "localprueba");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */

    //editarVentUsuario 
    public boolean editarVentUsuario(String txtEditarUIdDet, String txtEditarUVentDetalle, String nomVent) {

        PreparedStatement pst = null;
        PreparedStatement pstz = null;
        PreparedStatement pstzz = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "UPDATE `turnos_tibs`.`usuarios_central` SET  `ventanilla`= ? WHERE (`id`= ? );";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, txtEditarUVentDetalle);
            pst.setString(2, txtEditarUIdDet);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Editado :::" + txtEditarUIdDet);

                String consultaz = new String();
                pstz = null;
                consultaz = new String();

                if (txtEditarUVentDetalle.equals("")) {
                    consultaz = "UPDATE `turnos_tibs`.`ventanillas` SET `UsuarioAsignado`= ''  WHERE (`NombreVentanilla`= ? );";
                    pstz = getConexion().prepareStatement(consultaz);
                    pstz.setString(1, nomVent);

                    System.out.println("pstz UPDATE ::: " + pstz);

                    if (pstz.executeUpdate() == 1) {
                        System.out.println("Usuario - Ventanilla Editado :::" + txtEditarUVentDetalle + " ::: " + txtEditarUIdDet);
                    } else {
                        System.out.println("Usuario - Ventanilla ERROR Editar :::" + txtEditarUVentDetalle + " ::: " + txtEditarUIdDet);
                    }

                } else {
                    consultaz = "UPDATE `turnos_tibs`.`ventanillas` SET `UsuarioAsignado`= (select usuario from usuarios_central where id= ? )  WHERE (`NombreVentanilla`= ? );";
                    pstz = getConexion().prepareStatement(consultaz);
                    pstz.setString(1, txtEditarUIdDet);
                    pstz.setString(2, txtEditarUVentDetalle);

                    System.out.println("pstz UPDATE ::: " + pstz);

                    if (pstz.executeUpdate() == 1) {
                        String consultazz = new String();
                        pstzz = null;
                        consultazz = new String();

                        consultazz = "UPDATE `turnos_tibs`.`ventanillas` SET `UsuarioAsignado`= ''  WHERE (`NombreVentanilla`= ? );";
                        pstzz = getConexion().prepareStatement(consultazz);
                        pstzz.setString(1, nomVent);

                        System.out.println("pstzz UPDATE ::: " + pstz);

                        if (pstzz.executeUpdate() == 1) {
                            System.out.println("Usuario - Ventanilla Borrado :::" + txtEditarUVentDetalle + " ::: ");
                        } else {
                            System.out.println("Usuario - Ventanilla ERROR Borrado :::" + txtEditarUVentDetalle + " ::: ");
                        }

                        System.out.println("Usuario - Ventanilla Editado :::" + txtEditarUVentDetalle + " ::: " + txtEditarUIdDet);
                    } else {
                        System.out.println("Usuario - Ventanilla ERROR Editar :::" + txtEditarUVentDetalle + " ::: " + txtEditarUIdDet);
                    }

                }

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pstz != null) {
                    pstz.close();
                }
                if (pstzz != null) {
                    pstzz.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarVentUsuario
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.editarVentUsuario("606b9ad2-9da7-44fa-ad3f-703bf9ec2874", "","02");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */

    //editarUsuario 
    public boolean editarUsuario(String txtEditarUId, String txtEditarUUsu, String txtEditarUNombre, String txtEditarUPass, String txtEditarUEmail, String txtEditarUVent, String txtEditarUEstatus, String txtusredit) {

        PreparedStatement pst = null;
        PreparedStatement pstz = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "UPDATE `turnos_tibs`.`usuarios_central` SET  `usuario`= ? , `password`= ?, `nombre`= ? , `email`= ? ,  `estatus`= ? , `fecha`= ? , `ventanilla`= ?  WHERE (`id`= ? );";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, txtEditarUUsu);
            pst.setString(2, txtEditarUPass);
            pst.setString(3, txtEditarUNombre);
            pst.setString(4, txtEditarUEmail);
            pst.setString(5, txtEditarUEstatus);
            pst.setString(6, fecha);

            pst.setString(7, txtEditarUVent);
            pst.setString(8, txtEditarUId);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Editado :::" + txtEditarUNombre);

                DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calz = Calendar.getInstance();
                String fechaz = dateFormatz.format(calz.getTime());
                String consultaz = new String();
                pstz = null;
                dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                calz = Calendar.getInstance();
                fechaz = dateFormatz.format(calz.getTime());
                consultaz = new String();
                consultaz = "UPDATE `turnos_tibs`.`ventanillas` SET `UsuarioAsignado`= ? , `UsuarioLastUpdate`= ? , `FechaLastUpdate`= ?  WHERE (`NombreVentanilla`= ? );";
                pstz = getConexion().prepareStatement(consultaz);

                pstz.setString(1, txtEditarUUsu);
                pstz.setString(2, txtusredit);
                pstz.setString(3, fechaz);
                pstz.setString(4, txtEditarUVent);

                System.out.println("pstz UPDATE ::: " + pstz);

                if (pstz.executeUpdate() == 1) {
                    System.out.println("Usuario - Ventanilla Editado :::" + txtEditarUVent + " ::: " + txtEditarUUsu);
                } else {
                    System.out.println("Usuario - Ventanilla ERROR Editar :::" + txtEditarUVent + " ::: " + txtEditarUUsu);
                }

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pstz != null) {
                    pstz.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarUsuario
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.agregarUsuarioVentanilla("Usuario", "Nombre", "Pass", "Email", "04", "1", "localprueba");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */

    //eliminarUsuario 
    public boolean eliminarUsuario(String idUsElim) {

        PreparedStatement pst9 = null;
        PreparedStatement pstz = null;

        try {

            DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar calz = Calendar.getInstance();
            String fechaz = dateFormatz.format(calz.getTime());
            String consultaz = new String();
            pstz = null;
            dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            calz = Calendar.getInstance();
            fechaz = dateFormatz.format(calz.getTime());
            consultaz = new String();
            consultaz = "UPDATE `turnos_tibs`.`ventanillas` SET `UsuarioAsignado`= ''  WHERE (`UsuarioAsignado`= (select usuario from usuarios_central where id= ? ) );";
            pstz = getConexion().prepareStatement(consultaz);

            pstz.setString(1, idUsElim);

            System.out.println("pstz UPDATE ::: " + pstz);

            if (pstz.executeUpdate() == 1) {
                System.out.println("Usuario - Ventanilla Eliminado :::" + idUsElim);
            } else {
                System.out.println("Usuario - Ventanilla ERROR eliminar :::" + idUsElim);
            }

            String consulta9 = "delete from usuarios_central where id = ? ;";
            pst9 = getConexion().prepareStatement(consulta9);
            pst9.setString(1, idUsElim);

            if (pst9.executeUpdate() >= 1) {
                System.out.println("Usuario eliminado :::" + idUsElim);

            }

            return true;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pstz != null) {
                    pstz.close();
                }
                if (pst9 != null) {
                    pst9.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de eliminarUsuario

    /*public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.eliminarUsuario("8baf4c07-8fe0-4ac3-9e7b-ed17319eec11");
     System.out.println("Eliminar Usuario test  :::" + servicio);
     }*/
    /*
     //Obtener allUsuariosgral 
     */
    public List<Usuario> allUsuariosgral() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Usuario usuario;
        List<Usuario> lstUsu = new ArrayList<>();

        try {
            String consulta = "select u.id as id,u.usuario as usuario,u.password as password,u.nombre as nombre,u.email as email,u.idRol as idRol,u.estatus as estatus,u.fecha as fecha,v.NombreVentanilla as ventanilla from usuarios_central u, ventanillas v where u.idRol='3' and v.UsuarioAsignado=u.usuario;";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getString("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setIdRol(rs.getString("idRol"));
                usuario.setEstatus(rs.getString("estatus"));
                usuario.setFecha(rs.getString("fecha"));

                lstUsu.add(usuario);

            }

            return lstUsu;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstUsu;
    }
   //TEST de allUsuarios
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Usuario> lstUsu = new ArrayList();
     lstUsu = co.allUsuariosgral();
     System.out.println("Lista de usuarios :::" + lstUsu.size()); 
     }
     */

    //Obtener all allUsuarios 
    public List<Usuario> allUsuarios() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Usuario usuario;
        List<Usuario> lstUsu = new ArrayList<>();

        try {
            String consulta = "select * from usuarios_central where idRol='3';";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getString("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setIdRol(rs.getString("idRol"));
                usuario.setEstatus(rs.getString("estatus"));
                usuario.setFecha(rs.getString("fecha"));
                usuario.setVentanilla(rs.getString("ventanilla"));

                lstUsu.add(usuario);

            }

            return lstUsu;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstUsu;
    }
   //TEST de allUsuarios
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Usuario> lstUsu = new ArrayList();
     lstUsu = co.allUsuarios();
     System.out.println("Lista de usuarios :::" + lstUsu.size()); 
     }
     */


    /*
     * SERVICIOS
     */
    //CONSULTA DE SERVICIOS ACTIVOS 
    public List<Servicio> serviciosActivos() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Servicio> lstServ = new ArrayList<Servicio>();

        try {
            String consulta = "Select * from servicios where Estatus='1';";
            pst = getConexion().prepareStatement(consulta);
            //pst.setString(1, idOficina);

            rs = pst.executeQuery();

            while (rs.next()) {
                Servicio serv = new Servicio();
                serv.setId(rs.getString("id"));
                serv.setDescripcionServicio(rs.getString("DescripcionServicio"));
                serv.setEstatus(rs.getString("Estatus"));
                serv.setUserLastUpdate(rs.getString("UserLastUpdate"));
                serv.setFechaLastUpdate(rs.getString("FechaLastUpdate"));
                serv.setColor(rs.getString("Color"));

                lstServ.add(serv);
            }

            return lstServ;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return null;
    }

    //TEST de serviciosActivos
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Servicio> lstServ = new ArrayList<Servicio>();
     lstServ = co.serviciosActivos();
     
     for(Servicio s : lstServ)
     {
     System.out.println("SERVICIO ::: "+s.getDescripcionServicio());
     }
    
     }
     */
    //CONSULTA DE SERVICIOS ACTIVOS 
    public Servicio obtenerServicio(String Idservicio) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Servicio servicio = new Servicio();

        try {
            String consulta = "Select * from servicios where id=?;";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, Idservicio);

            rs = pst.executeQuery();

            while (rs.next()) {

                servicio.setId(rs.getString("id"));
                servicio.setDescripcionServicio(rs.getString("DescripcionServicio"));
                servicio.setEstatus(rs.getString("Estatus"));
                servicio.setUserLastUpdate(rs.getString("UserLastUpdate"));
                servicio.setFechaLastUpdate(rs.getString("FechaLastUpdate"));
                servicio.setColor(rs.getString("Color"));

            }

            return servicio;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return null;
    }

    //TEST de serviciosActivos
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     Servicio servicio = new Servicio();
     servicio = co.obtenerServicio("21fa5d9f-e04a-4b72-ab99-00f5d6f331f7");
     System.out.println("Servicio :::" + servicio.getDescripcionServicio() ); 
     }*/
    /*
     * CONFIG
     */
    //editarVideo 
    public boolean editarVideo(String TxtViNameSelected) {

        PreparedStatement pst = null;

        try {
            String consulta = "UPDATE `turnos_tibs`.`oficinalocal` SET  `video`= ? WHERE num ='1';";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, TxtViNameSelected);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Video Editado :::" + TxtViNameSelected);

                return true;
            } else {
                System.out.println("Video No Editado :::" + TxtViNameSelected);
                return false;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }

            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarVideo
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.editarVideo("Prueba");
     System.out.println("Video Actualizado :::" + servicio);
     }
     */

    //Obtener oficina 
    public Oficina consulNombreOficina() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Oficina ofi = new Oficina();

        try {
            String consulta = "Select * from oficinalocal;";
            pst = getConexion().prepareStatement(consulta);
            rs = pst.executeQuery();
            System.out.println("pst ::: " + pst);

            while (rs.next()) {
                ofi = new Oficina();
                ofi.setId(rs.getString("idOficina"));
                ofi.setNombre(rs.getString("Nombre"));
                ofi.setDesc(rs.getString("Desc"));
                ofi.setVideo(rs.getString("video"));
                ofi.setPoster(rs.getString("poster"));

            }

            return ofi;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return ofi;
    }
   //TEST de serviciosActivos
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     Oficina oficina = new Oficina();
     oficina = co.consulNombreOficina();
     System.out.println("Nombre de oficina :::" + oficina.getNombre());
     }
     */

    /*
     * VENTANILLAS
     */
    //Obtener ventanilla 
    public String consulVentanilla(String user) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        String ventanilla = new String();

        try {
            String consulta = "Select NombreVentanilla from ventanillas where UsuarioAsignado= ? ;";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, user);

            rs = pst.executeQuery();

            while (rs.next()) {

                ventanilla = rs.getString("NombreVentanilla");

            }

            return ventanilla;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return ventanilla;
    }
   //TEST de consulVentanilla
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     String ventanilla = new String();
     ventanilla = co.consulVentanilla("UserVentanillaTest");
     System.out.println("Nombre de oficina :::" + ventanilla); 
     }
     */

    //Obtener all ventanillas 
    public List<Ventanilla> allVentanillas() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Ventanilla ventanilla;
        List<Ventanilla> lstVent = new ArrayList<>();

        try {
            String consulta = "select * from ventanillas;";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                ventanilla = new Ventanilla();
                ventanilla.setId(rs.getString("idVentanilla"));
                ventanilla.setNombre(rs.getString("NombreVentanilla"));
                ventanilla.setDesc(rs.getString("DescVentanilla"));
                ventanilla.setUsuario(rs.getString("UsuarioAsignado"));
                ventanilla.setUsuLstUpdt(rs.getString("UsuarioLastUpdate"));
                ventanilla.setDtLstUpdt(rs.getString("FechaLastUpdate"));
                ventanilla.setServicios(rs.getString("servicios"));

                lstVent.add(ventanilla);

            }

            return lstVent;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstVent;
    }
   //TEST de allVentanillas
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Ventanilla> lstVent = new ArrayList();
     lstVent = co.allVentanillas();
     System.out.println("Lista de ventaniillas :::" + lstVent.size()); 
     }
     */

    //Obtener all ventanillas 
    public List<Ventanilla> allVentanillasDisponibles() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Ventanilla ventanilla;
        List<Ventanilla> lstVent = new ArrayList<>();

        try {
            String consulta = "select * from ventanillas where UsuarioAsignado = '';";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                ventanilla = new Ventanilla();
                ventanilla.setId(rs.getString("idVentanilla"));
                ventanilla.setNombre(rs.getString("NombreVentanilla"));
                ventanilla.setDesc(rs.getString("DescVentanilla"));
                ventanilla.setUsuario(rs.getString("UsuarioAsignado"));
                ventanilla.setUsuLstUpdt(rs.getString("UsuarioLastUpdate"));
                ventanilla.setDtLstUpdt(rs.getString("FechaLastUpdate"));
                ventanilla.setServicios(rs.getString("servicios"));

                lstVent.add(ventanilla);

            }

            return lstVent;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstVent;
    }
   //TEST de allVentanillas
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Ventanilla> lstVent = new ArrayList();
     lstVent = co.allVentanillas();
     System.out.println("Lista de ventaniillas :::" + lstVent.size()); 
     }
     */

    //Obtener ServActVent
    public List<String> ServActVent(String name) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        String Srv;
        List<String> lstSrv = new ArrayList<>();

        try {
            String consulta = "select NombreServicio from servicio_ventanillas sv, servicios s where sv.NombreVentanilla =? and s.DescripcionServicio = sv.NombreServicio and s.Estatus='1';";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, name);
            rs = pst.executeQuery();

            while (rs.next()) {
                Srv = new String();
                Srv = rs.getString("NombreServicio");

                lstSrv.add(Srv);

            }

            return lstSrv;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstSrv;
    }
   //TEST de ServActVent

    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<String> lstVent = new ArrayList();
     lstVent = co.ServActVent("01");
     System.out.println("Lista servicios activos de ventaniillas :::" + lstVent.size()); 
     }
     */
    //eliminarVentanilla 
    public boolean eliminarVentanilla(String idVentElim) {

        PreparedStatement pst9 = null;
        PreparedStatement pst = null;

        try {

            String consulta9 = "delete from servicio_ventanillas where NombreVentanilla = (select NombreVentanilla from ventanillas where idVentanilla='" + idVentElim + "');";
            pst9 = getConexion().prepareStatement(consulta9);

            if (pst9.executeUpdate() >= 1) {
                System.out.println("Relacion Ventanilla Servicios Eliminada :::" + idVentElim);

            }

            String consulta = "delete from ventanillas where idVentanilla='" + idVentElim + "';";
            pst = getConexion().prepareStatement(consulta);

            if (pst.executeUpdate() == 1) {
                System.out.println("Ventanilla Eliminada :::" + idVentElim);
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pst9 != null) {
                    pst9.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de eliminarVentanilla
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.eliminarVentanilla("ea296b4b-6160-44db-b578-c1eb8750cad9");
     System.out.println("Eliminar Ventanilla test  :::" + servicio);
     }
     */

    //agregarVentanilla 
    public boolean agregarVentanilla(String agreVentNombre, String agreVentDesc, String agreVentServ, String usuario) {

        PreparedStatement pst = null;
        String uid = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`ventanillas` (`idVentanilla`, `NombreVentanilla`, `DescVentanilla`, `UsuarioAsignado`, `UsuarioLastUpdate`, `FechaLastUpdate`, `LastTurnAtended`, `servicios`) VALUES(?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid);
            pst.setString(2, agreVentNombre);
            pst.setString(3, agreVentDesc);
            pst.setString(4, "");
            pst.setString(5, usuario);
            pst.setString(6, fecha);
            pst.setString(7, "");
            pst.setString(8, agreVentServ);

            if (pst.executeUpdate() == 1) {
                System.out.println("Ventanilla Agregada :::" + agreVentNombre);
                StringTokenizer st = new StringTokenizer(agreVentServ);

                PreparedStatement pstz = null;
                String uidz = UUID.randomUUID().toString();
                DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calz = Calendar.getInstance();
                String fechaz = dateFormatz.format(calz.getTime());
                String consultaz = new String();
                List<String> lstSt = new ArrayList();

                while (st.hasMoreTokens()) {
                    String ser = new String();
                    ser = st.nextToken();
                    lstSt.add(ser);
                }

                for (String strServ : lstSt) {

                    pstz = null;
                    uidz = UUID.randomUUID().toString();
                    dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    calz = Calendar.getInstance();
                    fechaz = dateFormatz.format(calz.getTime());
                    consultaz = new String();
                    consultaz = "INSERT INTO `turnos_tibs`.`servicio_ventanillas` (`id`, `NombreVentanilla`, `NombreServicio`, `UsuarioLastUpdate`, `FechaLastUpdate`) VALUES (?, ?, ?, ?, ?);";
                    pstz = getConexion().prepareStatement(consultaz);

                    pstz.setString(1, uidz);
                    pstz.setString(2, agreVentNombre);
                    pstz.setString(3, strServ);
                    pstz.setString(4, usuario);
                    pstz.setString(5, fechaz);

                    if (pstz.executeUpdate() == 1) {
                        System.out.println("Servicios - Ventanilla Agregados :::" + agreVentNombre + " ::: " + strServ);
                    } else {
                        System.out.println("Servicios - Ventanilla ERROR AGREGAR :::" + agreVentNombre + " ::: " + agreVentServ);
                    }

                }
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarVentanilla
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.agregarVentanilla("13","Prueba","Contratos Aclaraciones","usertest");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */

    //editarVentanilla 
    public boolean editarVentanilla(String txtventedituser, String txtIdEditV, String txtNombreEditV, String txtDescEditV, String editVentServ) {

        PreparedStatement pst = null;
        PreparedStatement pstz = null;
        PreparedStatement pstc = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());
        String delete = new String();

        try {
            String consulta = "UPDATE `turnos_tibs`.`ventanillas` SET  `DescVentanilla`= ? ,  `UsuarioLastUpdate`= ? , `FechaLastUpdate`= ? ,  `servicios`= ? WHERE (`idVentanilla`= ? );";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, txtDescEditV);
            pst.setString(2, txtventedituser);
            pst.setString(3, fecha);
            pst.setString(4, editVentServ);
            pst.setString(5, txtIdEditV);

            if (pst.executeUpdate() == 1) {
                System.out.println("Ventanilla editada :::" + txtNombreEditV);

                pstc = null;
                String consultac = new String();
                consultac = "delete from servicio_ventanillas where NombreVentanilla=(select NombreVentanilla from ventanillas where idVentanilla= ? );";
                pstc = getConexion().prepareStatement(consultac);
                pstc.setString(1, txtIdEditV);
                if (pstc.executeUpdate() == 1) {
                    System.out.println("Servicios - Ventanilla Eliminados :::" + txtNombreEditV + " ::: ");
                }

                StringTokenizer st = new StringTokenizer(editVentServ);

                String uidz = UUID.randomUUID().toString();
                DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calz = Calendar.getInstance();
                String fechaz = dateFormatz.format(calz.getTime());
                String consultaz = new String();
                List<String> lstSt = new ArrayList();

                while (st.hasMoreTokens()) {
                    String ser = new String();
                    ser = st.nextToken();
                    lstSt.add(ser);
                }

                for (String strServ : lstSt) {

                    pstz = null;
                    uidz = UUID.randomUUID().toString();
                    dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    calz = Calendar.getInstance();
                    fechaz = dateFormatz.format(calz.getTime());
                    consultaz = new String();
                    consultaz = "INSERT INTO `turnos_tibs`.`servicio_ventanillas` (`id`, `NombreVentanilla`, `NombreServicio`, `UsuarioLastUpdate`, `FechaLastUpdate`) VALUES (?, ?, ?, ?, ?);";
                    pstz = getConexion().prepareStatement(consultaz);

                    pstz.setString(1, uidz);
                    pstz.setString(2, txtNombreEditV);
                    pstz.setString(3, strServ);
                    pstz.setString(4, txtventedituser);
                    pstz.setString(5, fechaz);

                    if (pstz.executeUpdate() == 1) {
                        System.out.println("Servicios - Ventanilla Agregados :::" + txtNombreEditV + " ::: " + strServ);
                    } else {
                        System.out.println("Servicios - Ventanilla ERROR AGREGAR :::" + txtNombreEditV + " ::: " + editVentServ);
                    }

                }
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pstz != null) {
                    pstz.close();
                }
                if (pstc != null) {
                    pstc.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarVentanilla
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.editarVentanilla("UserLocalTester", "834e4b7e-146a-4535-904a-e83a085c18859", "18", "Prueba", "Aclaraciones");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }*/


    /*
     * TURNOS
     */
    //Obtener consecutivo 
    public String consecutivoServicio(String servicio) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        String consecutivo = new String();
        DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calz = Calendar.getInstance();
        String fechaz = dateFormatz.format(calz.getTime());

        try {
            String consulta = "select max(intTurno) as consecutivo from turnos where intServicio = ?  and dtmFechaEspera > ?;";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, servicio);
            pst.setString(2, fechaz);

            System.out.println("pst :: " + pst);
            rs = pst.executeQuery();

            while (rs.next()) {

                consecutivo = rs.getNString("consecutivo");

            }

            return consecutivo;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return null;
    }
     //TEST de generarTurno
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     String servicio = new String();
     servicio = co.consecutivoServicio("BotÃ³n");
     System.out.println("Servicio :::" + servicio );
     }*/

    //GENERAR UN NUEVO TURNO
    public String generarTurno(String servicio) {
        String turno = new String();

        //Identificar el Servicio para generar turno
        Servicio servObj = new Servicio();
        servObj = obtenerServicio(servicio);

        String prefijoTurno = servObj.getDescripcionServicio().substring(0, 2).toUpperCase();
        String postfijoTurno = new String();
        //Buscar el último valor consecutivo al turno correspondiente del servicio seleccionado
        Consultas cot = new Consultas();
        //System.out.println("cons ::: " + servObj.getDescripcionServicio());
        String cons = cot.consecutivoServicio(servObj.getDescripcionServicio());
        //System.out.println("cons ::: " + cons);

        if (null != cons) {
            if (cons.length() < 3) {
                Integer c = Integer.parseInt(cons);
                Formatter fmt = new Formatter();
                fmt.format("%03d", c);
                turno = prefijoTurno + "-" + fmt;
            } else {

                Integer postfijo = Integer.parseInt(cons.substring(cons.length() - 3, cons.length()));
                postfijo++;
                Formatter fmt = new Formatter();
                fmt.format("%03d", postfijo);
                turno = prefijoTurno + "-" + fmt;
            }

        } else {

            turno = prefijoTurno + "-" + "001";
        }

        return turno;
    }
    /*
     //TEST de generarTurno
     public static void main(String[] args) {
     Consultas co = new Consultas();
     String servicio = new String();
     servicio = co.generarTurno("21fa5d9f-e04a-4b72-ab99-00f5d6f331f9");
     System.out.println("Servicio :::" + servicio);
     }*/

    //Guardar turno 
    //(String idTurno, String intTurno, String intVentanilla, String intServicio, String strCliente, String strEstatusTurno, String strTipoTurno, String dtmFechaEspera, String dtmFechaAtencion, String intOficinadeServicios, String dtmFechaTermino) {
    public boolean guardarNuevoTurno(String intTurno, String intServicio, String intOficinadeServicios) {

        PreparedStatement pst = null;
        PreparedStatement pst1 = null;
        ResultSet rs = null;
        String consecutivo = new String();
        UUID uid = UUID.randomUUID();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`turnos` (`idTurno`, `intTurno`, `intVentanilla`, `intServicio`, `strCliente`, `strEstatusTurno`, `strTipoTurno`, `dtmFechaEspera`, `dtmFechaAtencion`, `intOficinadeServicios`, `dtmFechaTermino`,`enviado`) VALUES \n"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid.toString());
            pst.setString(2, intTurno);
            pst.setString(3, " ");
            pst.setString(4, intServicio);
            pst.setString(5, "Local");
            pst.setString(6, "Espera");
            pst.setString(7, "Normal");
            pst.setString(8, fecha);
            pst.setString(9, "");
            pst.setString(10, intOficinadeServicios);
            pst.setString(11, "");
            pst.setString(12, "0");

            if (pst.executeUpdate() == 1) {
                System.out.println("Turno insertado:::" + intServicio + "" + intTurno);

                return true;

            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
     //TEST de guardarNuevoTurno
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.guardarNuevoTurno("001", "Contratos", "1");
     System.out.println("Servicio :::" + servicio);
     }
     */

    //Obtener consulServiciosVentanilla 
    public List<String> consulServiciosVentanilla(String NumVentanilla) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> lstServ = new ArrayList<String>();

        try {
            String consulta = "Select * from servicio_ventanillas where NombreVentanilla = ? ;";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, NumVentanilla);

            rs = pst.executeQuery();

            while (rs.next()) {
                String serv = new String();
                serv = rs.getString("NombreServicio");
                lstServ.add(serv);
            }

            return lstServ;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstServ;
    }
   //TEST de consulServiciosVentanilla
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<String> lstServ = new ArrayList<String>();
     lstServ = co.consulServiciosVentanilla("1");
     System.out.println("Nombre de oficina :::" + lstServ.size()); 
     }
     */

    //Obtener consulSigTurnoVent 
    public Turno consulSigTurnoVent(List<String> lstServ, String intVent) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        String param = new String();
        String lstParam = new String();
        Turno turno = new Turno();
        //System.out.println("lstServ size ::: " + lstServ.size());
        if (lstServ.size() == 1) {
            for (String s : lstServ) {
                param = new String();
                param = s;
                lstParam = param;
            }
            //System.out.println("Servicios 1::: " + lstParam);
        } else {
            if (lstServ.isEmpty()) {
                System.out.println("::: La lista de Servicios está vacia ::: ");
                return turno;
            }
            if (lstServ.size() > 1) {
                for (String s : lstServ) {

                    param = param + s + "','";
                    lstParam = param;
                }
                lstParam = lstParam.substring(0, lstParam.length() - 3);
            }
            System.out.println("Servicios <1::: " + lstParam);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        String fechaX = new String();
        fechaX = dateFormat.format(cal.getTime());
        fechaX = fechaX + " 00:00:01";
        //System.out.println("fechaX ::: " + fechaX);
        try {
            String consulta = "select * from turnos where intServicio IN('" + lstParam + "') AND num_atenciones<='3' AND strEstatusTurno IN('Espera','Atendiendo') AND dtmFechaEspera = (select MIN(dtmFechaEspera) from turnos WHERE dtmFechaEspera > '" + fechaX + "' AND strEstatusTurno IN('Espera','Atendiendo') AND intServicio IN('" + lstParam + "') ) ; ";
            pst = getConexion().prepareStatement(consulta);

            System.out.println("script --> ::: " + pst);
            rs = pst.executeQuery();

            while (rs.next()) {
                turno = new Turno();

                turno.setIdTurno(rs.getString("idTurno"));
                turno.setIntTurno(rs.getString("intTurno"));
                turno.setIntVentanilla(rs.getString("intVentanilla"));
                turno.setIntServicio(rs.getString("intServicio"));
                turno.setStrCliente(rs.getString("strCliente"));
                turno.setStrEstatusTurno(rs.getString("strEstatusTurno"));
                turno.setStrTipoTurno(rs.getString("strTipoTurno"));
                turno.setDtmFechaEspera(rs.getString("dtmFechaEspera"));
                turno.setDtmFechaAtencion(rs.getString("dtmFechaAtencion"));
                turno.setIntOficinadeServicios(rs.getString("intOficinadeServicios"));
                turno.setDtmFechaTermino(rs.getString("dtmFechaTermino"));
                turno.setDtmFechaTermino(rs.getString("enviado"));
                turno.setDtmFechaTermino(rs.getString("dt_enviado"));
                turno.setDtmFechaTermino(rs.getString("num_atenciones"));

            }

            try {
                PreparedStatement psto = null;
                ResultSet rso = null;
                DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cl = Calendar.getInstance();
                String fechaA = dateFt.format(cl.getTime());
                String consultaUpdate = "UPDATE `turnos_tibs`.`turnos` SET `strEstatusTurno`='Atendiendo', `dtmFechaAtencion`='" + fechaA + "', intVentanilla='" + intVent + " ' WHERE (`idTurno`='" + turno.getIdTurno() + "'); ";
                psto = getConexion().prepareStatement(consultaUpdate);

                if (psto.executeUpdate() == 1) {
                    //System.out.println("Turno " + turno.getIdTurno() + " actualizado a Atendiendo :::");

                    String consultaUpd = "UPDATE `turnos_tibs`.`ventanillas` SET `LastTurnAtended`='" + turno.getIdTurno() + "' WHERE (`NombreVentanilla`='" + intVent + "');";
                    psto = getConexion().prepareStatement(consultaUpd);
                    //System.out.println("script ::: "+psto);
                    if (psto.executeUpdate() == 1) {
                        //System.out.println("Turno " + turno.getIdTurno().toString() + " referenciado en Ventanilla LastTurnAtended  :::");
                    }

                }

                return turno;
            } catch (Exception e) {
                System.err.println("catch Error: " + e);
            } finally {
                try {
                    if (getConexion() != null) {
                        getConexion().close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {

                    System.err.println("finally Error: " + e);
                }

            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return turno;
    }
    //TEST de consulServiciosVentanilla
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<String> lstServ = new ArrayList<String>();
     lstServ.add("Aclaraciones");
     Turno tur = new Turno();
     tur = co.consulSigTurnoVent(lstServ);
     System.out.println("Turno :::" + tur.getIntTurno());
     }
     */

    //Terminar turno 
    //(String idTurno, String intTurno, String intVentanilla, String intServicio, String strCliente, String strEstatusTurno, String strTipoTurno, String dtmFechaEspera, String dtmFechaAtencion, String intOficinadeServicios, String dtmFechaTermino) {
    public boolean terminarTurno(String intVentanilla) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());
        try {
            String consulta = "UPDATE `turnos_tibs`.`turnos` SET dtmFechaTermino='" + fechaT + "', `strEstatusTurno`='Terminado' WHERE `idTurno`=(select LastTurnAtended from ventanillas where NombreVentanilla='" + intVentanilla + "');";
            pst = getConexion().prepareStatement(consulta);

            if (pst.executeUpdate() == 1) {
                System.out.println("Turno Terminado:::");

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de terminarTurno
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.terminarTurno("1");
     System.out.println("Terminar Turno :::" + servicio);
     }*/

    //Obtener obtenerTurnoxId 
    public Turno obtenerTurnoxId(String intVenta) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Turno turno = new Turno();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fechaX = new String();
        fechaX = dateFormat.format(cal.getTime());

        try {
            String consulta = "select * from turnos where idTurno = (select LastTurnAtended from ventanillas where NombreVentanilla='" + intVenta + "');";
            pst = getConexion().prepareStatement(consulta);

            //System.out.println("script ::: "+pst);
            rs = pst.executeQuery();

            while (rs.next()) {
                turno = new Turno();

                turno.setIdTurno(rs.getString("idTurno"));
                turno.setIntTurno(rs.getString("intTurno"));
                turno.setIntVentanilla(rs.getString("intVentanilla"));
                turno.setIntServicio(rs.getString("intServicio"));
                turno.setStrCliente(rs.getString("strCliente"));
                turno.setStrEstatusTurno(rs.getString("strEstatusTurno"));
                turno.setStrTipoTurno(rs.getString("strTipoTurno"));
                turno.setDtmFechaEspera(rs.getString("dtmFechaEspera"));
                turno.setDtmFechaAtencion(rs.getString("dtmFechaAtencion"));
                turno.setIntOficinadeServicios(rs.getString("intOficinadeServicios"));
                turno.setDtmFechaTermino(rs.getString("dtmFechaTermino"));

            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return turno;
    }
    //TEST de obtenerTurnoxId
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     Turno tur = new Turno();
     tur = co.obtenerTurnoxId("1");
     System.out.println("Turno :::" + tur.getIntTurno());
     }
     */

    //Obtener obtenerTurnoxId 
    public ProxTurnos obtenerProxTurnos() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        ProxTurnos turno = new ProxTurnos();
        List<String> lstTurno = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        String fechaX = new String();
        fechaX = dateFormat.format(cal.getTime());
        fechaX = fechaX + " 00:00:01";

        try {
            String consulta = "select intTurno from turnos WHERE dtmFechaEspera > '" + fechaX + "' AND strEstatusTurno='Espera' ORDER BY dtmFechaEspera ASC LIMIT 4;";
            pst = getConexion().prepareStatement(consulta);

            //System.out.println("script ::: "+pst);
            rs = pst.executeQuery();

            while (rs.next()) {
                String t = new String();

                t = rs.getString("intTurno");

                lstTurno.add(t);
            }

            turno.setTur1(lstTurno.get(0));
            turno.setTur2(lstTurno.get(1));
            turno.setTur3(lstTurno.get(2));
            turno.setTur4(lstTurno.get(3));

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return turno;
    }
    //TEST de obtenerProxTurnos

    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     ProxTurnos tur = new ProxTurnos();
     tur = co.obtenerProxTurnos();
     System.out.println("ProxTurnos 1 :::" + tur.getTur1());
     System.out.println("ProxTurnos 2 :::" + tur.getTur2());
     System.out.println("ProxTurnos 3 :::" + tur.getTur3());
     System.out.println("ProxTurnos 4 :::" + tur.getTur4());

     }*/
    //Terminar turno 
    //(String idTurno, String intTurno, String intVentanilla, String intServicio, String strCliente, String strEstatusTurno, String strTipoTurno, String dtmFechaEspera, String dtmFechaAtencion, String intOficinadeServicios, String dtmFechaTermino) {
    public boolean reasignarTurno(Turno turno, String serv) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());
        UUID uid = UUID.randomUUID();
        try {
            String consulta = "INSERT INTO `turnos_tibs`.`turnos` (`idTurno`, `intTurno`, `intVentanilla`, `intServicio`, `strCliente`, `strEstatusTurno`, `strTipoTurno`, `dtmFechaEspera`, `dtmFechaAtencion`, `intOficinadeServicios`, `dtmFechaTermino`) VALUES \n"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid.toString());
            pst.setString(2, turno.getIntTurno());
            pst.setString(3, " ");
            pst.setString(4, serv);
            pst.setString(5, "Local");
            pst.setString(6, "Espera");
            pst.setString(7, "Reasignado");
            pst.setString(8, turno.getDtmFechaEspera());
            pst.setString(9, "");
            pst.setString(10, turno.getIntOficinadeServicios());
            pst.setString(11, "");

            if (pst.executeUpdate() == 1) {
                System.out.println("Turno resignado:::" + serv + "" + turno.getIntTurno());
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de reasignarTurno
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     Turno tu = new Turno();
     tu.setIntTurno("AA-007");
     tu.setDtmFechaAtencion("2020/03/04 11:35:55");
     tu.setIntOficinadeServicios("1");
     boolean servicio = co.reasignarTurno(tu,"PruebaReasigna");
     System.out.println("reasignarTurno :::" + servicio);
     }
     */

    //Obtener all Turnos
    public List<Turno> allTurnosCentral() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Turno turno;
        List<Turno> lstTur = new ArrayList<>();

        try {
            String consulta = "select * from turnos where (dtmFechaEspera >= (select dt_tblturno_last from oficinalocal));";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                turno = new Turno();
                turno.setIdTurno(rs.getString("idTurno"));
                turno.setIntTurno(rs.getString("intTurno"));
                turno.setIntVentanilla(rs.getString("intVentanilla"));
                turno.setIntServicio(rs.getString("intServicio"));
                turno.setStrCliente(rs.getString("strCliente"));
                turno.setStrEstatusTurno(rs.getString("strEstatusTurno"));
                turno.setStrTipoTurno(rs.getString("strTipoTurno"));
                turno.setDtmFechaEspera(rs.getString("dtmFechaEspera"));
                turno.setDtmFechaAtencion(rs.getString("dtmFechaAtencion"));
                turno.setIntOficinadeServicios(rs.getString("intOficinadeServicios"));
                turno.setDtmFechaTermino(rs.getString("dtmFechaTermino"));

                lstTur.add(turno);

            }

            return lstTur;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstTur;
    }
   //TEST de allTurnosCentral
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Turno> lstUsu = new ArrayList();
     lstUsu = co.allTurnos();
     System.out.println("Lista de turnos :::" + lstUsu.size()); 
     }
     */

    //actualizarturnoenviado turno 
    public boolean actualizarturnoenviado(String turno) {

        PreparedStatement pst = null;
        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());
        try {
            String consulta = "UPDATE `turnos_tibs`.`turnos` SET  `enviado`='1', `dt_enviado`=? WHERE `idTurno`=?;";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, fechaT);
            pst.setString(2, turno);

            if (pst.executeUpdate() == 1) {
                System.out.println("Turno actualizado local a enviado:::" + turno);
                return true;
            } else {
                System.out.println("Turno no actualizado local a enviado:::" + turno);
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de actualizarturnoenviado

    //Obtener all Turnos central
    public List<Turno> allTurnosCentralL() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Turno turno;
        List<Turno> lstTur = new ArrayList<>();

        try {
            String consulta = "select * from turnos where enviado = '0' and strEstatusTurno in('Terminado','Cancelado');";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                turno = new Turno();
                turno.setIdTurno(rs.getString("idTurno"));
                turno.setIntTurno(rs.getString("intTurno"));
                turno.setIntVentanilla(rs.getString("intVentanilla"));
                turno.setIntServicio(rs.getString("intServicio"));
                turno.setStrCliente(rs.getString("strCliente"));
                turno.setStrEstatusTurno(rs.getString("strEstatusTurno"));
                turno.setStrTipoTurno(rs.getString("strTipoTurno"));
                turno.setDtmFechaEspera(rs.getString("dtmFechaEspera"));
                turno.setDtmFechaAtencion(rs.getString("dtmFechaAtencion"));
                turno.setIntOficinadeServicios(rs.getString("intOficinadeServicios"));
                turno.setDtmFechaTermino(rs.getString("dtmFechaTermino"));

                lstTur.add(turno);

            }

            return lstTur;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstTur;
    }

    //Obtener obtenerFchLastEnvioCentral 
    public String obtenerFchLastEnvioCentral() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        String fecha = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fechaX = new String();
        fechaX = dateFormat.format(cal.getTime());

        try {
            String consulta = "select dt_tblturno_last from oficinalocal";
            pst = getConexion().prepareStatement(consulta);

            //System.out.println("script ::: "+pst);
            rs = pst.executeQuery();

            while (rs.next()) {
                fecha = new String();

                fecha = rs.getString("dt_tblturno_last");

            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return fecha;
    }
    //TEST de obtenerFchLastEnvioCentral
/*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     String f = new String();
     f = co.obtenerFchLastEnvioCentral();
     System.out.println("Fecha :::" + f);
     }
     */

    //actualizarfechaEnvioCentral
    public boolean actualizarfechaEnvioCentral() {

        PreparedStatement pst = null;

        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());

        try {
            String consulta = "UPDATE `turnos_tibs`.`oficinalocal` SET `dt_tblturno_last`= ?;";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, fechaT);

            if (pst.executeUpdate() == 1) {
                System.out.println("Fecha actualizada:::" + fechaT);
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }

    /*
     *consultarTurno
     */
    //consultarTurno
    public Turno consultarTurno(String idTurno) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Turno turno = new Turno();

        try {
            String consulta = "select * from turnos where idTurno='" + idTurno + "'";

            pst = getConexion().prepareStatement(consulta);

            System.out.println("pst:::" + pst);
            rs = pst.executeQuery();

            while (rs.next()) {
                turno = new Turno();
                turno.setIdTurno(rs.getString("idTurno"));
                turno.setIntTurno(rs.getString("intTurno"));
                turno.setIntVentanilla(rs.getString("intVentanilla"));
                turno.setIntServicio(rs.getString("intServicio"));
                turno.setStrCliente(rs.getString("strCliente"));
                turno.setStrEstatusTurno(rs.getString("strEstatusTurno"));
                turno.setStrTipoTurno(rs.getString("strTipoTurno"));
                turno.setDtmFechaEspera(rs.getString("dtmFechaEspera"));
                turno.setDtmFechaAtencion(rs.getString("dtmFechaAtencion"));
                turno.setIntOficinadeServicios(rs.getString("intOficinadeServicios"));
                turno.setDtmFechaTermino(rs.getString("dtmFechaTermino"));
                turno.setNum_atenciones(rs.getString("num_atenciones"));

            }

            return turno;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return null;
    }

    /*
     *cancelarTurno
     */
    //cancelarTurno
    public boolean cancelarTurno(Turno turno) {

        PreparedStatement pst = null;

        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());
        String na = turno.getNum_atenciones();

        if (na.contains("0")) {

            try {

                String num_ate = "1";
                String consulta = "UPDATE `turnos_tibs`.`turnos` SET `intVentanilla`=' ', `strEstatusTurno`='Espera', `dtmFechaEspera`=?, `num_atenciones`=? WHERE (`idTurno`=?);";
                pst = getConexion().prepareStatement(consulta);

                pst.setString(1, fechaT);
                pst.setString(2, num_ate);
                pst.setString(3, turno.getIdTurno());

                if (pst.executeUpdate() == 1) {
                    System.out.println("Fecha actualizada:::" + fechaT);
                    return true;
                }

            } catch (Exception e) {
                System.err.println("catch Error: " + e);
            } finally {
                try {
                    if (getConexion() != null) {
                        getConexion().close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {

                    System.err.println("finally Error: " + e);
                }

            }

        } else if (na.contains("1")) {

            try {

                String num_ate = "2";
                String consulta = "UPDATE `turnos_tibs`.`turnos` SET `intVentanilla`=' ', `strEstatusTurno`='Espera', `dtmFechaEspera`=?, `num_atenciones`=? WHERE (`idTurno`=?);";
                pst = getConexion().prepareStatement(consulta);

                pst.setString(1, fechaT);
                pst.setString(2, num_ate);
                pst.setString(3, turno.getIdTurno());

                if (pst.executeUpdate() == 1) {
                    return true;
                }

            } catch (Exception e) {
                System.err.println("catch Error: " + e);
            } finally {
                try {
                    if (getConexion() != null) {
                        getConexion().close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {

                    System.err.println("finally Error: " + e);
                }

            }

        } else if (na.contains("2")) {

            try {

                String num_ate = "3";
                String consulta = "UPDATE `turnos_tibs`.`turnos` SET `intVentanilla`=' ', `strEstatusTurno`='Espera', `dtmFechaEspera`=?, `num_atenciones`=? WHERE (`idTurno`=?);";
                pst = getConexion().prepareStatement(consulta);

                pst.setString(1, fechaT);
                pst.setString(2, num_ate);
                pst.setString(3, turno.getIdTurno());

                if (pst.executeUpdate() == 1) {
                    return true;
                }

            } catch (Exception e) {
                System.err.println("catch Error: " + e);
            } finally {
                try {
                    if (getConexion() != null) {
                        getConexion().close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {

                    System.err.println("finally Error: " + e);
                }

            }

        } else if (na.contains("3")) {

            try {
                String consulta = "UPDATE `turnos_tibs`.`turnos` SET  `strEstatusTurno`='Cancelado', `dtmFechaTermino`=?  WHERE (`idTurno`=?);";
                pst = getConexion().prepareStatement(consulta);

                pst.setString(1, fechaT);
                pst.setString(2, turno.getIdTurno());

                if (pst.executeUpdate() == 1) {
                   
                    return true;
                }

            } catch (Exception e) {
                System.err.println("catch Error: " + e);
            } finally {
                try {
                    if (getConexion() != null) {
                        getConexion().close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                } catch (Exception e) {

                    System.err.println("finally Error: " + e);
                }

            }

        }

        return false;
    }

    
    
    //editarPoster 
    public boolean editarPoster(String TxtImgNameSelected) {

        PreparedStatement pst = null;

        try {
            String consulta = "UPDATE `turnos_tibs`.`oficinalocal` SET  `poster`= ? WHERE num ='1';";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, TxtImgNameSelected);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Imagen Editada :::" + TxtImgNameSelected);

                return true;
            } else {
                System.out.println("Imagen No Editada :::" + TxtImgNameSelected);
                return false;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }

            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarPoster
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.editarVideo("Prueba");
     System.out.println("Video Actualizado :::" + servicio);
     }
    
     */
    /*
    *enviarTurnosCentral
     */
     public boolean enviarTurnosCentral(List<Turno> lstTurnos) {

        PreparedStatement pst = null;
        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());
        if(lstTurnos.size()>0){       
            
        try {
            for(Turno t : lstTurnos){
            String consulta = "INSERT INTO `turnos_tibs`.`turnos_central` (`idTurno`, `intTurno`, `intVentanilla`, `intServicio`, `strCliente`, `strEstatusTurno`, `strTipoTurno`, `dtmFechaEspera`, `dtmFechaAtencion`, `intOficinadeServicios`, `dtmFechaTermino`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, t.getIdTurno());
            pst.setString(2, t.getIntTurno());
            pst.setString(3, t.getIntVentanilla());
            pst.setString(4, t.getIntServicio());
            pst.setString(5, t.getStrCliente());
            pst.setString(6, t.getStrEstatusTurno());
            pst.setString(7, t.getStrTipoTurno());
            pst.setString(8, t.getDtmFechaEspera());
            pst.setString(9, t.getDtmFechaAtencion());
            pst.setString(10, t.getIntOficinadeServicios());
            pst.setString(11, t.getDtmFechaTermino());

            if (pst.executeUpdate() == 1) {
                System.out.println("Turno Insertado a Central :::" + fechaT);
               
                
            }}

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }}
        

        return false;
    }
    //TEST de enviarTurnosCentral
/*
     public static void main(String[] args) {
     ConsultasCentral co = new ConsultasCentral();
      List<Turno> lstTurno = new ArrayList<>();
     Turno tu = new Turno();
     tu.setIntTurno("AA-007");
     tu.setDtmFechaAtencion("2020/03/04 11:35:55");
     tu.setIntOficinadeServicios("1");
     lstTurno.add(tu);
     boolean servicio = co.enviarTurnosCentral(lstTurno);
     System.out.println("reasignarTurno :::" + servicio);
     }
     */
     
     
     /*
    *enviarTurnosCentral
     */
     public boolean enviarTurnosEnLinea(List<Turno> lstTurnos) {

        PreparedStatement pst = null;
        DateFormat dateFt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cl = Calendar.getInstance();
        String fechaT = dateFt.format(cl.getTime());
        if(lstTurnos.size()>0){       
            
        try {
            for(Turno t : lstTurnos){
            String consulta = "INSERT INTO `turnos_tibs`.`turnos_central` (`idTurno`, `intTurno`, `intVentanilla`, `intServicio`, `strCliente`, `strEstatusTurno`, `strTipoTurno`, `dtmFechaEspera`, `dtmFechaAtencion`, `intOficinadeServicios`, `dtmFechaTermino`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, t.getIdTurno());
            pst.setString(2, t.getIntTurno());
            pst.setString(3, t.getIntVentanilla());
            pst.setString(4, t.getIntServicio());
            pst.setString(5, t.getStrCliente());
            pst.setString(6, t.getStrEstatusTurno());
            pst.setString(7, t.getStrTipoTurno());
            pst.setString(8, t.getDtmFechaEspera());
            pst.setString(9, t.getDtmFechaAtencion());
            pst.setString(10, t.getIntOficinadeServicios());
            pst.setString(11, t.getDtmFechaTermino());

            if (pst.executeUpdate() == 1) {
                System.out.println("Turno Insertado a Central :::" + fechaT);
               Consultas con = new Consultas();
               boolean a = con.actualizarturnoenviado(t.getIdTurno());
                System.out.println("Id Turno enviado a central "+a+" :: "+t.getIdTurno()+" :: "+t.getIntTurno());
                
            }}

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }}
        

        return false;
    }
    //TEST de enviarTurnosCentral
/*
     public static void main(String[] args) {
     ConsultasCentral co = new ConsultasCentral();
      List<Turno> lstTurno = new ArrayList<>();
     Turno tu = new Turno();
     tu.setIntTurno("AA-007");
     tu.setDtmFechaAtencion("2020/03/04 11:35:55");
     tu.setIntOficinadeServicios("1");
     lstTurno.add(tu);
     boolean servicio = co.enviarTurnosCentral(lstTurno);
     System.out.println("reasignarTurno :::" + servicio);
     }
     */
     
     

    
     /* Obtener all allUsuariosAdmin*/ 
    public List<Usuario> allUsuariosAdmin() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Usuario usuario;
        List<Usuario> lstUsuario = new ArrayList<Usuario>();

        try {
            String consulta = "select * from usuarios_central where idRol <> '3';";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getString("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setIdRol(rs.getString("idRol"));
                usuario.setEstatus(rs.getString("estatus"));
                usuario.setFecha(rs.getString("fecha"));
                usuario.setOficina(rs.getString("oficina"));
                

                lstUsuario.add(usuario);

            }

            return lstUsuario;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstUsuario;
    }
    //TEST de allUsuariosAdmin
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Usuario> allUsuariosAdmin = new ArrayList<Usuario>();
     lstOficinas = co.allOficinas();
     System.out.println("Lista de Usuarios Admin :::" + lstOficinas.size()); 
     }
     */
        
    /*agregarUsuario*/    
    //agregarUsuario 
    public boolean agregarUsuario(String txtAgregarUUsuario, String txtAgregarUNombre, String txtAgregarUPass, String txtAgregarUEmail,String txtAgregarUEPerfil, String txtAgregarUOfi, String txtAgregarUEstatus, String useragr) {

        PreparedStatement pst = null;
        String uid = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`usuarios_central` (`id`, `usuario`, `password`, `nombre`, `email`, `idRol`, `estatus`, `fecha`, `oficina`) VALUES (?,?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid);
            pst.setString(2, txtAgregarUUsuario);
            pst.setString(3, txtAgregarUPass);
            pst.setString(4, txtAgregarUNombre);
            pst.setString(5, txtAgregarUEmail);
            pst.setString(6, txtAgregarUEPerfil);
            pst.setString(7, txtAgregarUEstatus);
            pst.setString(8, fecha);
            pst.setString(9, txtAgregarUOfi);

            //System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Agregado :::" + txtAgregarUUsuario);

                

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarUsuario
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.agregarUsuario("Usuario", "Nombre", "Pass", "Email", "04", "1", "localprueba");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */
    
    /*eliminarUsuario*/
            //eliminarUsuario 
    public boolean eliminarUsuario(String idUsElim,String userelim) {

        PreparedStatement pst9 = null;

        try {

          
            
            String consulta9 = "delete from usuarios_central where id = ? ;";
            pst9 = getConexion().prepareStatement(consulta9);
            pst9.setString(1, idUsElim);

            if (pst9.executeUpdate() >= 1) {
                System.out.println("Usuario eliminado :::" + idUsElim+" :: Por :: "+userelim);
   
            }

            return true;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
              
                if (pst9 != null) {
                    pst9.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de eliminarUsuario

    /*public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.eliminarUsuario("8baf4c07-8fe0-4ac3-9e7b-ed17319eec11","user");
     System.out.println("Eliminar Usuario test  :::" + servicio);
     }*/
    
    /*editarUsuario*/
    //editarUsuario 
    public boolean editarUsuario(String txtEditarUId, String txtEditarUUsu, String txtEditarUNombre, String txtEditarUPass, String txtEditarUEmail, String txtEditarUEPerfil, String txtEditarUEstatus,String txtEditarUOfi, String txtusredit) {

        PreparedStatement pst = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "UPDATE `turnos_tibs`.`usuarios_central` SET `password`=?, `nombre`=?, `email`=?, `idRol`=?, `estatus`=?, `fecha`=?, `oficina`=? WHERE (`usuario`=?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, txtEditarUPass);
            pst.setString(2, txtEditarUNombre);
            pst.setString(3, txtEditarUEmail);
            pst.setString(4, txtEditarUEPerfil);
            pst.setString(5, txtEditarUEstatus);
            pst.setString(6, fecha);
            pst.setString(7, txtEditarUOfi);
            pst.setString(8, txtEditarUUsu);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Editado :::" + txtEditarUUsu);

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
               
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarUsuario
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.agregarUsuarioVentanilla("");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */
    
    /*
     *****
     *****Oficinas
     *****
     */
    //Obtener all allOficinas 
    public List<Oficina> allOficinas() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Oficina oficina;
        List<Oficina> lstOficinas = new ArrayList<Oficina>();

        try {
            String consulta = "select * from oficinas;";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                oficina = new Oficina();
                oficina.setId(rs.getString("idOficina"));
                oficina.setNum(rs.getString("num"));
                oficina.setNombre(rs.getString("nombre"));
                oficina.setDesc(rs.getString("desc"));
                oficina.setDireccion(rs.getString("direccion"));
                oficina.setCp(rs.getString("cp"));
                oficina.setMunicipio(rs.getString("municipio"));
                oficina.setTel1(rs.getString("tel"));
                oficina.setCiudad(rs.getString("ciudad"));
                oficina.setEmail1(rs.getString("email1"));
                oficina.setServidor(rs.getString("servidor"));
                oficina.setDb_username(rs.getString("db_username"));
                oficina.setDb_pass(rs.getString("db_pass"));
                oficina.setDb_host(rs.getString("db_host"));
                oficina.setDb_port(rs.getString("db_port"));
                oficina.setDb_name(rs.getString("db_name"));
                oficina.setServicios(rs.getString("servicios"));

                lstOficinas.add(oficina);

            }

            return lstOficinas;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstOficinas;
    }
    //TEST de allOficinas
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Oficina> lstOficinas = new ArrayList<Oficina>();
     lstOficinas = co.allOficinas();
     System.out.println("Lista de ofcinas :::" + lstOficinas.size()); 
     }
     */

    //agregarOficina 
    public boolean agregarOficina(String user, String agreOfiNum, String agreOfiNombre, String agreOfiDesc, String agreOfiDir, String agreOfiCP, String agreOfiMun, String agreOfiTel, String agreOfiCity, String agreOfiEmail, String agreOfiServ) {

        PreparedStatement pst = null;
        PreparedStatement pstz = null;
        String uid = UUID.randomUUID().toString();

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`oficinas` (`idOficina`, `num`, `nombre`, `desc`, `direccion`, `cp`, `municipio`, `tel`, `ciudad`, `email1`, `servidor`,  `db_username`, `db_pass`, `db_host`, `db_port`, `db_name`,`servicios`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid);
            pst.setString(2, agreOfiNum);
            pst.setString(3, agreOfiNombre);
            pst.setString(4, agreOfiDesc);
            pst.setString(5, agreOfiDir);
            pst.setString(6, agreOfiCP);
            pst.setString(7, agreOfiMun);
            pst.setString(8, agreOfiTel);
            pst.setString(9, agreOfiCity);
            pst.setString(10, agreOfiEmail);
            pst.setString(11, "");
            pst.setString(12, "");
            pst.setString(13, "");
            pst.setString(14, "");
            pst.setString(15, "");
            pst.setString(16, "");
            pst.setString(17, agreOfiServ);

            //System.out.println("pst::: "+pst);
            if (pst.executeUpdate() == 1) {
                //System.out.println("Oficina Agregada :::" + agreOfiNombre);
                pstz = null;
                StringTokenizer st = new StringTokenizer(agreOfiServ);
                String uidz = UUID.randomUUID().toString();
                DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calz = Calendar.getInstance();
                String fechaz = dateFormatz.format(calz.getTime());
                String consultaz = new String();
                List<String> lstSt = new ArrayList();
                while (st.hasMoreTokens()) {
                    String ser = new String();
                    ser = st.nextToken();
                    lstSt.add(ser);
                }
                //System.out.println("lstSt size :::" + lstSt.size();
                if (lstSt.size() > 0) {
                    for (String strServ : lstSt) {
                        pstz = null;
                        uidz = UUID.randomUUID().toString();
                        dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        calz = Calendar.getInstance();
                        fechaz = dateFormatz.format(calz.getTime());
                        consultaz = new String();
                        consultaz = "INSERT INTO `turnos_tibs`.`oficina_servicio` (`id`, `Oficina`, `NombreServicio`, `UsuarioLastUpdate`, `FechaLastUpdate`) VALUES (?, ?, ?, ?, ?);";
                        pstz = getConexion().prepareStatement(consultaz);

                        pstz.setString(1, uidz);
                        pstz.setString(2, agreOfiNum);
                        pstz.setString(3, strServ);
                        pstz.setString(4, user);
                        pstz.setString(5, fechaz);

                        if (pstz.executeUpdate() == 1) {
                            System.out.println("Oficina - Servicio Agregados :::" + agreOfiNum + " ::: " + strServ);
                        } else {
                            System.out.println("Oficina - Servicio ERROR AGREGAR :::" + agreOfiNum + " ::: " + agreOfiNombre);
                        }
                    }
                }

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                 if (pstz != null) {
                    pstz.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarOficina
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean oficina = co.agregarOficina();
     System.out.println("Oficina Agregada :::" + oficina);
     }
     */

    //eliminarOficina 
    public boolean eliminarOficina(String txtNumOficina, String txtIdOficina, String txtUserElim) {

        PreparedStatement pst9 = null;
        PreparedStatement pst = null;

        try {

            String consulta9 = "delete from oficinas where idOficina = ? ;";
            pst9 = getConexion().prepareStatement(consulta9);
            pst9.setString(1, txtIdOficina);

            if (pst9.executeUpdate() >= 1) {
                System.out.println("Oficina eliminada :::" + txtIdOficina + "  Por el usuario::: " + txtUserElim);
                pst = null;
                String consulta = "delete from oficina_servicio where Oficina = ? ;";
                pst = getConexion().prepareStatement(consulta);
                pst.setString(1, txtNumOficina);
                if (pst.executeUpdate() >= 1) {
                    System.out.println("Oficina-Servicio eliminada :::" + txtNumOficina + "  Por el usuario::: " + txtUserElim);
                }
            }

            return true;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pst9 != null) {
                    pst9.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de eliminarOficina

    /*public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.eliminarOficina("8baf4c07-8fe0-4ac3-9e7b-ed17319eec11");
     System.out.println("Eliminar Oficina test  :::" + servicio);
     }*/
    //editarOficina 
    public boolean editarOficina(String user, String editOfiId, String editOfiNum, String editOfiNombre, String editOfiDesc, String editOfiDir, String editOfiCP, String editOfiMun, String editOfiTel, String editOfiCity, String editOfiEmail, String editOfiServ) {

        PreparedStatement pst = null;
        PreparedStatement pstz = null;
        PreparedStatement pstc = null;

        try {
            String consulta = "UPDATE `turnos_tibs`.`oficinas` SET `nombre`=?, `desc`=?, `direccion`=?, `cp`=?, `municipio`=?, `tel`=?, `ciudad`=?, `email1`=?, `servicios`=? WHERE (`idOficina`=?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, editOfiNombre);
            pst.setString(2, editOfiDesc);
            pst.setString(3, editOfiDir);
            pst.setString(4, editOfiCP);
            pst.setString(5, editOfiMun);
            pst.setString(6, editOfiTel);
            pst.setString(7, editOfiCity);
            pst.setString(8, editOfiEmail);
            pst.setString(9, editOfiServ);
            pst.setString(10, editOfiId);

            //System.out.println("pst::: " + pst);
            if (pst.executeUpdate() == 1) {
                System.out.println("Oficina editada :::" + editOfiNombre + "  Por el usuario::: " + user);

                pstc = null;
                String consultac = new String();
                consultac = "delete from oficina_servicio where Oficina= ? ;";
                pstc = getConexion().prepareStatement(consultac);
                pstc.setString(1, editOfiNum);
                if (pstc.executeUpdate() == 1) {
                    System.out.println("Oficina - Servicios Eliminados :::" + editOfiServ + " ::: ");
                }

                StringTokenizer st = new StringTokenizer(editOfiServ);

                String uidz = UUID.randomUUID().toString();
                DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calz = Calendar.getInstance();
                String fechaz = dateFormatz.format(calz.getTime());
                String consultaz = new String();
                List<String> lstSt = new ArrayList();

                while (st.hasMoreTokens()) {
                    String ser = new String();
                    ser = st.nextToken();
                    lstSt.add(ser);
                }

                for (String strServ : lstSt) {

                    pstz = null;
                    uidz = UUID.randomUUID().toString();
                    dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    calz = Calendar.getInstance();
                    fechaz = dateFormatz.format(calz.getTime());
                    consultaz = new String();
                    consultaz = "INSERT INTO `turnos_tibs`.`oficina_servicio` (`id`, `Oficina`, `NombreServicio`, `UsuarioLastUpdate`, `FechaLastUpdate`) VALUES (?, ?, ?, ?, ?);";
                    pstz = getConexion().prepareStatement(consultaz);

                    pstz.setString(1, uidz);
                    pstz.setString(2, editOfiNum);
                    pstz.setString(3, strServ);
                    pstz.setString(4, user);
                    pstz.setString(5, fechaz);

                    if (pstz.executeUpdate() == 1) {
                        System.out.println("Servicios - Ventanilla Agregados :::" + editOfiNum + " ::: " + strServ);
                    } else {
                        System.out.println("Servicios - Ventanilla ERROR AGREGAR :::" + editOfiNum + " ::: " + editOfiServ);
                    }

                }

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pstz != null) {
                    pstz.close();
                }
                if (pstc != null) {
                    pstc.close();
                }

            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarOficina
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean oficina = co.editarOficina();
     System.out.println("Oficina Editada :::" + oficina);
     }
     */

    
    //Obtener allServicios
    public List<Servicio> allServicios() {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Servicio servicio;
        List<Servicio> lstServicios = new ArrayList<Servicio>();

        try {
            String consulta = "select * from servicios";
            pst = getConexion().prepareStatement(consulta);

            rs = pst.executeQuery();

            while (rs.next()) {
                servicio = new Servicio();

                servicio.setId(rs.getString("id"));
                servicio.setDescripcionServicio(rs.getString("DescripcionServicio"));
                servicio.setEstatus(rs.getString("Estatus"));
                servicio.setUserLastUpdate(rs.getString("UserLastUpdate"));
                servicio.setFechaLastUpdate(rs.getString("FechaLastUpdate"));
                servicio.setColor(rs.getString("Color"));

                lstServicios.add(servicio);

            }

            return lstServicios;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        return lstServicios;
    }
    //TEST de allServicios
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Servicio> lstOficinas = new ArrayList<Servicio>();
     lstOficinas = co.allServicios();
     System.out.println("Lista de servicios :::" + lstOficinas.size()); 
     }
     */

    //agregarServicio 
    public boolean agregarServicio(String user, String agreServNombre, String txtAgregarSEstatus, String colorselectorA) {

        PreparedStatement pst = null;
        String uid = UUID.randomUUID().toString();
        DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calz = Calendar.getInstance();
        String fechaz = dateFormatz.format(calz.getTime());

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`servicios` (`id`, `DescripcionServicio`, `Estatus`, `UserLastUpdate`, `FechaLastUpdate`, `Color`) VALUES (?, ?, ?, ?, ?, ?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid);
            pst.setString(2, agreServNombre);
            pst.setString(3, txtAgregarSEstatus);
            pst.setString(4, user);
            pst.setString(5, fechaz);
            pst.setString(6, colorselectorA);

            //System.out.println("pst::: "+pst);
            if (pst.executeUpdate() == 1) {
              //  System.out.println("Servicio Agregado ::: "+agreServNombre+" : Por :" + user);
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarServicio
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean oficina = co.agregarServicio();
     System.out.println("Servicio Agregado :::" + oficina);
     }
     */
    
    
            //eliminarServicio 
    public boolean eliminarServicio(String txtNombreServElim,String txtUserElimServ) {

        PreparedStatement pst9 = null;
        PreparedStatement pst = null;

        try {

            String consulta9 = "delete from servicios where DescripcionServicio = ? ;";
            pst9 = getConexion().prepareStatement(consulta9);
            pst9.setString(1, txtNombreServElim);

            if (pst9.executeUpdate() >= 1) {
                System.out.println("Servicio eliminado :::" + txtNombreServElim + "  Por el usuario::: " + txtUserElimServ);
                pst = null;
                String consulta = "delete from oficina_servicio where NombreServicio = ? ;";
                pst = getConexion().prepareStatement(consulta);
                pst.setString(1, txtNombreServElim);
                if (pst.executeUpdate() >= 1) {
                    System.out.println("Oficina-Servicio eliminada :::" + txtNombreServElim + "  Por el usuario::: " + txtUserElimServ);
                }
            }

            return true;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (pst9 != null) {
                    pst9.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de eliminarServicio

    /*public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.eliminarServicio("");
     System.out.println("Eliminar Oficina test  :::" + servicio);
     }*/


//editarServicio 
    public boolean editarServicio(String user,String editServId,String editServNombre,String txtEditarSEstatus,String colorselectorE) {

        PreparedStatement pst = null;
        String uid = UUID.randomUUID().toString();
        DateFormat dateFormatz = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calz = Calendar.getInstance();
        String fechaz = dateFormatz.format(calz.getTime());

        try {
            String consulta = "UPDATE `turnos_tibs`.`servicios` SET `Estatus`= ? , `UserLastUpdate`= ? , `FechaLastUpdate`= ? , `Color`= ?  WHERE (`id`= ? );";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, txtEditarSEstatus);
            pst.setString(2, user);
            pst.setString(3, fechaz);
            pst.setString(4, colorselectorE);
            pst.setString(5, editServId);

            //System.out.println("pst::: "+pst);
            if (pst.executeUpdate() == 1) {
              //  System.out.println("Servicio Agregado ::: "+agreServNombre+" : Por :" + user);
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarServicio
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean oficina = co.agregarServicio();
     System.out.println("Servicio Agregado :::" + oficina);
     }
     */
    
    
     //Obtener slctServ
    public List<Servicio> slctServ(String agreOfiServ) {

        PreparedStatement pst = null;
        ResultSet rs = null;
        Servicio servicio;
        List<Servicio> lstServicios = new ArrayList<Servicio>();
        StringTokenizer st = new StringTokenizer(agreOfiServ);
        List<String> lstSt = new ArrayList();
        while (st.hasMoreTokens()) {
                    String ser = new String();
                    ser = st.nextToken();
                    lstSt.add(ser);
                }
        
        
             try {
                 for (String strServ : lstSt) {
            String consulta = "select * from servicios where DescripcionServicio =?";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, strServ);
            rs = pst.executeQuery();

            while (rs.next()) {
                servicio = new Servicio();

                servicio.setId(rs.getString("id"));
                servicio.setDescripcionServicio(rs.getString("DescripcionServicio"));
                servicio.setEstatus(rs.getString("Estatus"));
                servicio.setUserLastUpdate(rs.getString("UserLastUpdate"));
                servicio.setFechaLastUpdate(rs.getString("FechaLastUpdate"));
                servicio.setColor(rs.getString("Color"));

                lstServicios.add(servicio);

            }

        
 }
        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }
        
        
        
       
        
        
        
        

       
        return lstServicios;
    }
    //TEST de slctServ
   /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     List<Servicio> lstOficinas = new ArrayList<Servicio>();
     lstOficinas = co.slctServ();
     System.out.println("Lista de servicios :::" + lstOficinas.size()); 
     }
     */
    


    /*
     *serv_ofi
     */
    public boolean serv_ofi(List<Servicio> lstServ) {

        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4 = null;

        try {
            String consulta = "delete from servicios;";
            pst = getConexion().prepareStatement(consulta);

            //System.out.println("pst::: "+pst);
            if (pst.executeUpdate() >= 0) {

                 //System.out.println("lstServ::: "+lstServ.size());
                for (Servicio s : lstServ) {
                    pst2 = null;
                    
                    String consulta2 = "INSERT INTO `turnos_tibs`.`servicios` (`id`, `DescripcionServicio`, `Estatus`, `UserLastUpdate`, `FechaLastUpdate`, `Color`) VALUES (?,?,?,?,?,?);";
                    pst2 = getConexion().prepareStatement(consulta2);
                    pst2.setString(1, s.getId());
                    pst2.setString(2, s.getDescripcionServicio());
                    pst2.setString(3, s.getEstatus());
                    pst2.setString(4, s.getUserLastUpdate());
                    pst2.setString(5, s.getFechaLastUpdate());
                    pst2.setString(6, s.getColor());
                    //System.out.println("pst2::: "+pst2);
                    if (pst2.executeUpdate() == 1) {
                    System.out.println("Servicio Agregado a DB LOCAL :::" + s.getDescripcionServicio());
                    }
                }
                /*Script para eliminar relación servicios ventanillas  
//            String consulta3 = "delete from servicio_ventanillas;";
//            pst3 = getConexion().prepareStatement(consulta3);
//
//            //System.out.println("pst3::: "+pst3);
//            if (pst3.executeUpdate() >= 0) {
//            System.out.println("::: Relacion Servicios Ventanillas eliminado en DB LOCAL :::" );
//           
//            }
//            String consulta4 = "UPDATE ventanillas SET servicios = '';";
//            pst4 = getConexion().prepareStatement(consulta4);
//
//            //System.out.println("pst4::: "+pst4);
//            if (pst4.executeUpdate() >= 0) {
//            System.out.println("::: Relacion Servicios Ventanillas eliminado en DB LOCAL :::" );
//           
//            }
                */
            
                
                
                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
                 if (pst2 != null) {
                    pst2.close();
                }
                   if (pst3 != null) {
                    pst3.close();
                }
                     if (pst4 != null) {
                    pst4.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarOficina
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean oficina = co.agregarOficina();
     System.out.println("Oficina Agregada :::" + oficina);
     }
     */

    /*agregarUsuario*/    
    //agregarUsuario 
    public boolean agregarUsuarioCentral(String txtAgregarUUsuario, String txtAgregarUNombre, String txtAgregarUPass, String txtAgregarUEmail,String txtAgregarUEPerfil, String txtAgregarUOfi, String txtAgregarUEstatus, String useragr) {

        PreparedStatement pst = null;
        String uid = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "INSERT INTO `turnos_tibs`.`usuarios_central` (`id`, `usuario`, `password`, `nombre`, `email`, `idRol`, `estatus`, `fecha`, `oficina`) VALUES (?,?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, uid);
            pst.setString(2, txtAgregarUUsuario);
            pst.setString(3, txtAgregarUPass);
            pst.setString(4, txtAgregarUNombre);
            pst.setString(5, txtAgregarUEmail);
            pst.setString(6, txtAgregarUEPerfil);
            pst.setString(7, txtAgregarUEstatus);
            pst.setString(8, fecha);
            pst.setString(9, txtAgregarUOfi);

            //System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Agregado :::" + txtAgregarUUsuario);

                

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de agregarUsuario
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.agregarUsuario("Usuario", "Nombre", "Pass", "Email", "04", "1", "localprueba");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */
    
     /*eliminarUsuario*/
            //eliminarUsuario 
    public boolean eliminarUsuarioCentral(String idUsElim,String userelim) {

        PreparedStatement pst9 = null;

        try {

          
            
            String consulta9 = "delete from usuarios_central where id = ? ;";
            pst9 = getConexion().prepareStatement(consulta9);
            pst9.setString(1, idUsElim);

            if (pst9.executeUpdate() >= 1) {
                System.out.println("Usuario eliminado :::" + idUsElim+" :: Por :: "+userelim);
   
            }

            return true;

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
              
                if (pst9 != null) {
                    pst9.close();
                }
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de eliminarUsuario

    /*public static void main(String[] args) {
     Consultas co = new Consultas();

     boolean servicio = co.eliminarUsuario("8baf4c07-8fe0-4ac3-9e7b-ed17319eec11","user");
     System.out.println("Eliminar Usuario test  :::" + servicio);
     }*/
    
        /*editarUsuario*/
    //editarUsuario 
    public boolean editarUsuarioCentral(String txtEditarUId, String txtEditarUUsu, String txtEditarUNombre, String txtEditarUPass, String txtEditarUEmail, String txtEditarUEPerfil, String txtEditarUEstatus,String txtEditarUOfi, String txtusredit) {

        PreparedStatement pst = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());

        try {
            String consulta = "UPDATE `turnos_central`.`usuarios_central` SET `password`=?, `nombre`=?, `email`=?, `idRol`=?, `estatus`=?, `fecha`=?, `oficina`=? WHERE (`usuario`=?);";
            pst = getConexion().prepareStatement(consulta);

            pst.setString(1, txtEditarUPass);
            pst.setString(2, txtEditarUNombre);
            pst.setString(3, txtEditarUEmail);
            pst.setString(4, txtEditarUEPerfil);
            pst.setString(5, txtEditarUEstatus);
            pst.setString(6, fecha);
            pst.setString(7, txtEditarUOfi);
            pst.setString(8, txtEditarUUsu);

            System.out.println("pst UPDATE ::: " + pst);

            if (pst.executeUpdate() == 1) {
                System.out.println("Usuario Editado :::" + txtEditarUUsu);

                return true;
            }

        } catch (Exception e) {
            System.err.println("catch Error: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    getConexion().close();
                }
                if (pst != null) {
                    pst.close();
                }
               
            } catch (Exception e) {

                System.err.println("finally Error: " + e);
            }

        }

        return false;
    }
    //TEST de editarUsuario
    /*
     public static void main(String[] args) {
     Consultas co = new Consultas();
     boolean servicio = co.agregarUsuarioVentanilla("");
     System.out.println("Ventanilla Agregada :::" + servicio);
     }
     */
}
