package conexion;

import gestor.GestorController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginController;
import modelo.Contacto;

public class Conexion {

	public static String userRoot="";
	public static String passRoot="";
	
	
	
	/* *************************************************************************************************************************
	 *                                        OBTIENE UNA CONEXION CON LA BASE DE DATOS
	 **************************************************************************************************************************/
	
	public static Connection getConnection() {
        Connection conexion = null; // crea un objeto de conexion
        
        getRootYContrasenia();
        
        try
        {
            // registra el driver, el servidor, la tabla y el user y contrasenia
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/contactos";
            String usuarioDB = userRoot;
            String passwordDB = passRoot;
            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB); // conexion obtiene todos los datos
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            Dialogs.showErrorDialog(LoginController.ventanaGestor,ex.getMessage());
            conexion=null;
        }
       
        return conexion;
    }
	
	
	
	
	/* *************************************************************************************************************************
	 *                                         REGISTRA UN CONTACTO EN LA BASE DE DATOS
	 **************************************************************************************************************************/
	
	public static void registrarContacto(Contacto contacto) {
		String departamento = contacto.getDepartamento();
		String puesto = contacto.getDepartamento();
		String nombre = contacto.getNombre();
		String apellido = contacto.getApellido();
		String fecha = contacto.getFechaNacimiento();
		String telef = contacto.getTelefono();
		String email = contacto.getEmail();
		String direcc = contacto.getDireccion();
		Connection conexion = null;
		PreparedStatement prepararConsulta = null;
		
		try {
            conexion = (Connection) Conexion.getConnection(); // abrimos la conexion
            if (conexion != null) {
            // crea una sentencia previa para mandarla a una declaracion preparada (PreparedStatement)
            String sentencia = "insert into contacto values(?,?,?,?,?,?,?,?,?)";
            prepararConsulta = conexion.prepareStatement(sentencia); // prepara la declaracion con la sentencia previa
            prepararConsulta.setInt(1, 0); // pasa los valores que se van a insertar en la tabla
            prepararConsulta.setString(2,departamento);
            prepararConsulta.setString(3,puesto);
            prepararConsulta.setString(4,nombre);
            prepararConsulta.setString(5,apellido);
            prepararConsulta.setString(6,fecha);
            prepararConsulta.setString(7,telef);
            prepararConsulta.setString(8,email);
            prepararConsulta.setString(9,direcc);
            
            prepararConsulta.executeUpdate(); // ejecuta la declaracion
            }
        }
    
    catch(SQLException e) {
       Dialogs.showErrorDialog(LoginController.ventanaGestor,e.getMessage(),"Error al registrar el contacto.","Error...");
    }
    catch(NullPointerException np){
    	Dialogs.showErrorDialog(LoginController.ventanaGestor,np.getMessage(),"Error de Conexion","Error...");
    }
        finally{
        	try {
        		if(prepararConsulta != null) {
    				prepararConsulta.close(); // cierra la conexion de la consulta
    				}
    				if(conexion != null){
    				conexion.close(); // cierra la conexion
    				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
            
        } // fin finally
	} // fin registrarContactos
	
	

	
	/* **************************************************************************************************************************
	 *                 CARGA LOS CONTACTOS QUE HAY EN LA BASE DE DATOS A LA TABLA CUANDO SE INICIA SESION
	 ***************************************************************************************************************************/
	
	public static ObservableList<Contacto> cargarContactos() {
		Connection conexion = null;
		PreparedStatement prepararConsulta = null;
		ObservableList<Contacto> actualizar = FXCollections.observableArrayList();
		
		
		try {
			conexion = (Connection) Conexion.getConnection(); // abrimos la conexion
			if (conexion != null) {
			String sentencia = "select * from contacto";
            prepararConsulta = conexion.prepareStatement(sentencia); // prepara la declaracion con la sentencia previa
            ResultSet rs = prepararConsulta.executeQuery();
            while(rs.next()) {
            	actualizar.add(new Contacto(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
		  } // fin if
		} catch(SQLException e){
						
		} catch(NullPointerException npe) {
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,npe.getMessage(),"Error de Conexion","Error...");
		}
		finally {
			try {
				if(prepararConsulta != null) {
				prepararConsulta.close(); // cierra la conexion de la consulta
				}
				if(conexion != null){
				conexion.close(); // cierra la conexion
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			} 
		} // fin finally
		return actualizar;
	} // fin cargarContactos
	
	
	
	
	/* ***********************************************************************************************************************
	 *                                DEVUELVE LA CANTIDAD DE FILAS QUE HAY EN LA TABLA
	 ************************************************************************************************************************/
	
	public static int hayFilasEnTabla() {
		Connection conexion = null;
		PreparedStatement prepararConsulta = null;
		int filas = 0;
		
		try {
			conexion = (Connection) Conexion.getConnection(); // abrimos la conexion
			if (conexion != null) {
			String sentencia = "select count(*) as count from contacto";
            prepararConsulta = conexion.prepareStatement(sentencia); // prepara la declaracion con la sentencia previa
            ResultSet rs = prepararConsulta.executeQuery();
            while(rs.next()) {
            	filas = rs.getInt("count");
            }
		  } // fin if
		} catch(SQLException e){
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,e.getMessage(),"Error al obtener el numero de filas.","Error...");
			
		} catch(NullPointerException npe) {
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,npe.getMessage(),"Error de Conexion","Error...");
		}
		finally {
			try {
				if(prepararConsulta != null) {
					prepararConsulta.close(); // cierra la conexion de la consulta
					}
				if(conexion != null){
					conexion.close(); // cierra la conexion
					}
			} catch (SQLException | NullPointerException e ) {
				// nada
			} 
		} // fin finally
		
		GestorController.posicionArray = filas-1;
		return filas;
	}
	
	
	
	/* ************************************************************************************************************************
	 *                                           REMOVER UN CONTACTO
	 *************************************************************************************************************************/
	
	public static void removerContacto(String registroEliminar,String registroCondicion) {
		
		Connection conexion = null;
		PreparedStatement prepararConsulta = null;
		
		try {
			conexion = (Connection) Conexion.getConnection(); // abrimos la conexion
			if(conexion != null) {
			String sentencia = "delete from contacto where "+registroEliminar+"='"+registroCondicion+"'";
            prepararConsulta = conexion.prepareStatement(sentencia); // prepara la declaracion con la sentencia previa
            prepararConsulta.executeUpdate();
			}
            
		} catch(SQLException e){
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,e.getMessage(),"Error al remover contacto","Error...");
			
		} catch(NullPointerException npe) {
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,npe.getMessage(),"Error de Conexion","Error...");
		}
		finally {
			try {
				if(prepararConsulta != null) {
					prepararConsulta.close(); // cierra la conexion de la consulta
					}
				if(conexion != null){
					conexion.close(); // cierra la conexion
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // fin finally
		
	}
	
	
	/* ***********************************************************************************************************************
	 *                                                    ACTUALIZAR UN REGISTRO
	 * **********************************************************************************************************************/
	
	public static void actualizarRegistro(String viejoRegistro,String nuevoRegistro, String registroActualizar) {
		
		Connection conexion = null;
		PreparedStatement prepararConsulta = null;
		
		try {
			conexion = (Connection) Conexion.getConnection(); // abrimos la conexion
			if(conexion != null) {
			String sentencia = "update contacto set "+registroActualizar+"='"+nuevoRegistro+"'"+" where "+registroActualizar+"='"+viejoRegistro+"'";
            prepararConsulta = conexion.prepareStatement(sentencia); // prepara la declaracion con la sentencia previa
            prepararConsulta.executeUpdate();
			}
            
		} catch(SQLException e){
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,e.getMessage(),"Error de Database","Error de Database");
			
		} catch(NullPointerException npe) {
			
			Dialogs.showErrorDialog(LoginController.ventanaGestor,npe.getMessage(),"Error de Conexion","Error de Conexion");
		}
		finally {
			try {
				if(prepararConsulta != null) {
					prepararConsulta.close(); // cierra la conexion de la consulta
					}
				if(conexion != null){
					conexion.close(); // cierra la conexion
					}
			} catch (SQLException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,"Error al acltualizar el registro.","Error de actualización","Error..");
				e.printStackTrace();
			} 
		} // fin finally
		
	}
	
	
	/* *************************************************************************************************************************
	 * 												LEER ROOT Y CONTRASENIA DESDE EL FICHERO
	 * ************************************************************************************************************************/
	
	private static void getRootYContrasenia() {
		File archivo = null;
	      FileReader fr = null;
	      BufferedReader lector = null;
	 
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (System.getProperty("user.home")+"\\AppData"+"\\Local"+"\\agenda"+"\\database.txt");
	         fr = new FileReader (archivo);
	         lector = new BufferedReader(fr);
	 
	         // Lectura del fichero
	         String linea;
	         int i = 0;
	         while((linea=lector.readLine())!=null) {
	        	 if(i == 0) {
	            userRoot = linea;
	        	 }
	        	 else if(i == 1){
	        		 passRoot = linea;
	        	 }
	             i++;
	         } 
	      }
	      catch(Exception e){
	         Dialogs.showErrorDialog(LoginController.ventanaGestor,e.getMessage());
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            //e2.printStackTrace();
	         }
	      } // fin finally
	}
	
	
	/* ***********************************************************************************************************************
	 * 						DETECTA SI AL INICIAR LA SESION NO SE HA ESTABLECIDO EL ROOT Y PASS DE LA DB
	 * **********************************************************************************************************************/
	
	
	/* ************************************************************************************************************************
	 *										VENTANA PARA ESTABLECER EL ROOT Y PASS DE LA BD 
	 * ***********************************************************************************************************************/
	
	public static void setConfigDatabase() {
		final Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		Pane pane = new Pane();
		Scene scene = new Scene(pane,350,250);
		pane.setStyle("-fx-background-color: #2E2E2E");	
		Label user = new Label("Root:");
		final TextField txtroot = new TextField();
		Label pass = new Label("Contraseña:");
		final PasswordField txtpassw = new PasswordField();
		Button guardar = new Button("Guardar");
		Button cancelar = new Button("Cancelar");
		user.setTextFill(Color.web("#bfbfbf"));
		pass.setTextFill(Color.web("#bfbfbf"));
		user.setStyle("-fx-font-weight: bold");
		pass.setStyle("-fx-font-weight: bold");
		user.setLayoutX(30);
		user.setLayoutY(33);
		txtroot.setLayoutX(130);
		txtroot.setLayoutY(30);
		pass.setLayoutX(30);
		pass.setLayoutY(83);
		txtpassw.setLayoutX(130);
		txtpassw.setLayoutY(80);
		guardar.setLayoutX(30);
		guardar.setLayoutY(160);
		guardar.setPrefSize(90, 35);
		cancelar.setLayoutX(200);
		cancelar.setLayoutY(160);
		cancelar.setPrefSize(90,35);
		pane.getChildren().addAll(user,txtroot,pass,txtpassw,guardar,cancelar);
		guardar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {			
				setRootYPassIfNull(txtroot.getText(), txtpassw.getText());
				dialog.close();			
			}
		});
		cancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				dialog.close();
			}
		});
		
		dialog.setScene(scene);
		dialog.setTitle("Establecer Root y Password");
		dialog.setResizable(false);
		dialog.show();
	}
	
	/* *********************************************************************************************************************
	 * SI EL ARCHIVO DATABASE.TXT NO EXISTE, LO CREA
	 * ********************************************************************************************************************/
	public static void setRootYPassIfNull(String txtroot,String txtpassw) {
		File archivo = null;
		FileWriter fw = null;
		BufferedWriter escritor = null;
		try {

			archivo = new File(System.getProperty("user.home")+"\\AppData"+"\\Local"+"\\agenda"+"\\database.txt");
			archivo.createNewFile();
			fw = new FileWriter(archivo);
			escritor = new BufferedWriter(fw);
			escritor.write(txtroot);
			escritor.newLine();
			escritor.write(txtpassw);
			escritor.flush(); // nos aseguramos que se escriban los datos
			fw.flush();
			Dialogs.showInformationDialog(LoginController.ventanaGestor, "Sus datos se han registrado satisfactoriamente.",
				    "Información", "Informe");			
			}
		
		catch(IOException ioe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor, "Error al registrar sus datos.",
				    "Error al registrar.", "Error...");
		}
		finally {
			try {
				if (fw != null) {
				fw.close(); }
			} catch (IOException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor, "No se puede cerrar el archivo","Error","Error");
			}
		}
	}
	
} // FIN.
