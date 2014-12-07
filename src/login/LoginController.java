package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import conexion.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class LoginController implements Initializable {

	public static Stage ventanaGestor; // declaro la stage static para poderla usar en la gui agenda para redimensionar el stage
	public static final double ANCHO = 832;
	public static final double ALTO= 369;
	public static String userLogin = "";
	public static String passLogin = "";
	// del login
	@FXML private TextField txtUsername;
	@FXML private PasswordField txtPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnCancel;
	
	
	
	/* ***********************************************************************************************************************
	 *                                     EVENTO DEL BOTON BTNLOGIN FXML
	 ************************************************************************************************************************/
	
	@FXML
	public void login() throws IOException
	{
		File archivo = new File (System.getProperty("user.home")+"\\AppData"+"\\Local"+"\\agenda"+"\\database.txt");
		
		
			getUsuarioYContrasenia();
		if(txtUsername.getText().equals(userLogin) && txtPassword.getText().equals(passLogin)) { // si el login es correcto
			if(!archivo.exists()){
				Conexion.setConfigDatabase();
			}
			else {
			ventanaGestor = new Stage();
			ventanaGestor.setResizable(false);
			ventanaGestor.setTitle("Itla - Sesion inciada como "+txtUsername.getText());
			Parent root = FXMLLoader.load(getClass().getResource("Gestor.fxml")); // carga el fxml
			Scene escenaAgenda = new Scene(root,ANCHO,ALTO);		
			ventanaGestor.setScene(escenaAgenda);
			ventanaGestor.getIcons().add(new Image("login/itlaicon.png"));
			ventanaGestor.show();
			Main.ventana.close(); // despues de mostrar la agenda, la ventana del login se cierra
			}
		}
		else { // si es incorrecto, muestra un dialog de error
			Dialogs.showErrorDialog(ventanaGestor,"Usuario o contraseña incorrecta.","Error de validacion:","Error de validacion..");
		}
	  }
	
	
	/* *********************************************************************************************************************
	 *              OBTIENE EL USARIO Y CONTRASENIA DEL USUARIO PARA LUEGO COMPROBARLO CON LO INGRESADO
	 * ********************************************************************************************************************/
	
	private void getUsuarioYContrasenia() {
		File archivo = null;
	      FileReader fr = null;
	      BufferedReader lector = null;
	 
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (System.getProperty("user.home")+"\\AppData"+"\\Local"+"\\agenda"+"\\userdata.txt");
	         if(archivo.exists()) {
	         fr = new FileReader (archivo);
	         lector = new BufferedReader(fr);
	 
	         // Lectura del fichero
	         String linea;
	         int i = 0;
	         while((linea=lector.readLine())!=null) {
	        	 if(i == 0) {
	            userLogin = linea;
	        	 }
	        	 else if(i == 1){
	        		 passLogin = linea;
	        	 }
	             i++;
	         }
	        }
	      }
	      catch(IOException ioe){
	         Dialogs.showErrorDialog(ventanaGestor, ioe.getMessage());
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){ 
	                fr.close();     
	            }                  
	         }catch (IOException ioe){ 
	            // nada
	         }
	      } // fin finally
	}
	
	
	/* *******************************************************************************************************************
	 * METODO PARA CREAR UN USUARIO Y CONTRASENIA PREDETERMINADOS CUANDO NO SE HA ESTABLECIDO UN USER Y CONTRASENIA
	 * ******************************************************************************************************************/
	
		private void crearArchivoUserDataIfNull() {
			File archivo = null;
			FileWriter fw = null;
			BufferedWriter escritor = null;
			
			try {
			archivo = new File(System.getProperty("user.home") + "\\AppData"+ "\\Local" + "\\agenda"+"\\userdata.txt");
			fw = new FileWriter(archivo);
			escritor = new BufferedWriter(fw);
			escritor.write("user");
			escritor.newLine();
			escritor.write("pass");
			escritor.flush();
			fw.flush();
		} // fin try
		
		catch (IOException ioe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,
					"Error al escribir los datos predeterminado de ingreso.", "Información",
					"Error...");
		}
		
		finally {
			try {
				if(null != fw) {
				fw.close(); }
			} catch (IOException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,
						"No se puede cerrar el archivo", "Error", "Error");
			 }
			  catch (NullPointerException e) {
				  // nada
			  }
		} // fin finally
	}


	
	/* ******************************************************************************************************************
	 *  METODO QUE INICIALIZA EL FXML
	 * *****************************************************************************************************************/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// si la carpeta agenda donde se guardan los user y pass no existe, la crea.
		File carpeta = new File(System.getProperty("user.home") + "\\AppData"
				+ "\\Local" + "\\agenda");
		if (!carpeta.exists()) {
			carpeta.mkdir();
		}
		// si no existe el archivo de la cuenta de usuario y se borra, se crea uno nuevo.
		File archivo = new File(System.getProperty("user.home") + "\\AppData"+"\\Local"+"\\agenda"+"\\userdata.txt");
		if (!archivo.exists()){
			crearArchivoUserDataIfNull();
		}
	}
	
	
	// sale de la aplicacion
	@FXML
	public void cancel(){
		System.exit(0);
	}
}
