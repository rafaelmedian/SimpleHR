package gestor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import conexion.Conexion;
import login.LoginController;
import modelo.Contacto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GestorController implements Initializable {

	private boolean canResizeNew = true; // indica si el boton nuevo puede redimensionar el panel
	private boolean canResizeCancel = false; // indica si el boton cancelar puede redimensionar el panel
	public static int posicionArray = -1; // posicion de los elementos del arraylist. Empieza en -1 por que
										  // luego de agregar el primer contacto al array, se coloca en 0
	private int contadorDepartamento, contadorPuesto, contadorNombre, contadorApellido, contadorFNacimiento, contadorTelefono, contadorEmail, contadorDireccion = 0;
	int suma = 0;
	
	@FXML
	private TextField txtDepartamento;
	@FXML
	private TextField txtPuesto;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;
	@FXML
	private TextField txtFechaNacimiento;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtDireccion;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnCancelar;

	// menubar
	@FXML
	private MenuBar menu;
	@FXML
	private Menu archivo;
	@FXML
	private Menu editar;
	@FXML
	private Menu acerca;
	@FXML
	private MenuItem nuevo;
	@FXML
	private MenuItem salir;
	@FXML
	private MenuItem eliminar;
	@FXML
	private MenuItem setUserPass;
	@FXML
	private MenuItem setDatabase;
	@FXML
	private MenuItem autor;

	@FXML
	TableView<Contacto> tablaContactos = new TableView<>();
	@FXML
	TableColumn<Contacto, String>clDepartamento;
	@FXML
	TableColumn<Contacto, String>clPuesto;
	@FXML
	TableColumn<Contacto, String> clNombre;
	@FXML
	TableColumn<Contacto, String> clApellido;
	@FXML
	TableColumn<Contacto, String> clFechaNacimiento;
	@FXML
	TableColumn<Contacto, String> clTelefono;
	@FXML
	TableColumn<Contacto, String> clEmail;
	@FXML
	TableColumn<Contacto, String> clDireccion;
	ObservableList<Contacto> contactos; // arraylist que almacena los contactos y se asocia a la tabla

	/* ************************************************************************************************************************
	 * CONSTRUCTOR DE LA CLASE
	 * ***********************************************************************************************************************/

	@SuppressWarnings("unchecked")
	public GestorController() {
		// inicializa las columnas y las agrega a la tabla
		clDepartamento = new TableColumn<Contacto, String>("Departamento");
		clPuesto = new TableColumn<Contacto, String>("Puesto");
		clNombre = new TableColumn<Contacto, String>("Nombre");
		clApellido = new TableColumn<Contacto, String>("Apellido");
		clFechaNacimiento = new TableColumn<Contacto, String>("F. Nacimiento");
		clTelefono = new TableColumn<Contacto, String>("Telefono");
		clEmail = new TableColumn<Contacto, String>("Email");
		clDireccion = new TableColumn<Contacto, String>("Direccion");
		tablaContactos.getColumns().addAll(clPuesto,clDepartamento,clNombre, clApellido,
				clFechaNacimiento, clTelefono, clEmail, clDireccion);
		
		
		txtDepartamento = new TextField();
		txtPuesto = new TextField();
		txtNombre = new TextField();
		txtApellido = new TextField();
		txtFechaNacimiento = new TextField();
		txtTelefono = new TextField();
		txtEmail = new TextField();
		txtDireccion = new TextField();
		btnGuardar = new Button();
		
		menu = new MenuBar();
		nuevo = new MenuItem();
		salir = new MenuItem();
		eliminar = new MenuItem();
		setUserPass = new MenuItem();
		setDatabase = new MenuItem();
		autor = new MenuItem();

	}

	/* *********************************************************************************************************************
	 * AGREGA UN NUEVO CONTACTO
	 * ********************************************************************************************************************/

	@FXML
	public void registrar() {
		
		
		String departamento = txtDepartamento.getText();
		String puesto = txtPuesto.getText();
		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String fecha = txtFechaNacimiento.getText();
		String telefono = txtTelefono.getText();
		String email = txtEmail.getText();
		String direccion = txtDireccion.getText();
		// agrega los datos al observaleArrayList por medio del constructor del
		// modelo
		contactos.add(new Contacto(departamento, puesto, nombre, apellido, fecha, telefono, email,
				direccion));
		posicionArray++; // aumenta la posicion en el array tras agregar un nuevo contacto al array
		// agrega los items del observableArrayList a la tabla
		tablaContactos.setItems(contactos);
		
		Conexion.registrarContacto(contactos.get(posicionArray)); // envia el
																	// objeto
																	// Contacto
																	// actual al
																	// metodo
																	// para
																	// insertarlo
																	// en la bd
	}

	/* **********************************************************************************************************************
	 * BORRA LOS CAMPOS DEL PANEL PARA AGREGAR CONTACTO
	 * *********************************************************************************************************************/

	@FXML
	public void borrar() {
		txtNombre.clear();
		txtApellido.clear();
		txtApellido.clear();
		txtFechaNacimiento.clear();
		txtTelefono.clear();
		txtEmail.clear();
		txtDireccion.clear();
		txtNombre.requestFocus();
	}

	/* **********************************************************************************************************************
	 * ESCONDE EL PANEL PARA AGREGAR CONTACTO
	 * *********************************************************************************************************************/

	@FXML
	public void cancelar() {

		if (canResizeCancel) {
			LoginController.ventanaGestor.setWidth(848);
			LoginController.ventanaGestor.setHeight(410);
			borrar();
		}
		canResizeNew = true;
		canResizeCancel = false;
	}

	/* **********************************************************************************************************************
	 * AUMENTA DE TAMAÑO LA PANTALLA PARA QUE SE HAGA VISIBLE EL PANEL PARA AÑADIR UN CONTACTO
	 * *********************************************************************************************************************/

	// este es un metodo del menuitem nuevo
	@FXML
	public void nuevo() {

		if (canResizeNew) {
			LoginController.ventanaGestor.setWidth(848);
			LoginController.ventanaGestor.setHeight(682);
			txtNombre.requestFocus();
		}
		canResizeCancel = true;
		canResizeNew = false;
	}

	/* **********************************************************************************************************************
	 * SALIR DEL PROGRAMA
	 * *********************************************************************************************************************/

	@FXML
	public void exit() {
		System.exit(0);
	}

	/* ***********************************************************************************************************************
	 * REMOVER UN CONTACTO DE LA TABLA Y DE LA BASE DE DATOS
	 * **********************************************************************************************************************/

	@FXML
	public void remover() {
		
		String condicion = (contactos.get(posicionArray).getNombre());
		Conexion.removerContacto("Nombre", condicion);
		contactos.remove(posicionArray);
		posicionArray--;
	}

	/* **********************************************************************************************************************
	 * ACERCA DEL AUTOR
	 * *********************************************************************************************************************/
	@FXML
	public void about() {
		Dialogs.showInformationDialog(LoginController.ventanaGestor,
				"Rafael Medina y Adderly Medina\n"
						+ "@2014 - GNU License.", "Acerca del autor:",
				"Acerca..");
	}
	
	/* ***********************************************************************************************************************
	 * METODOS PARA ESTABLECER EL USUARIO Y CONTRASEÑA DE LA CUENTA
	 * ***********************************************************************************************************************/

	// ventana para establecer el usuario y contraseña de usuario y pass de login
	@FXML
	public void setUserAndPass() {
		final Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 350, 250);
		pane.setStyle("-fx-background-color: #2E2E2E");
		Label user = new Label("Usuario:");
		final TextField txtuser = new TextField();
		Label pass = new Label("Contraseña:");
		final PasswordField txtpass = new PasswordField();
		Button guardar = new Button("Guardar");
		Button cancelar = new Button("Cancelar");
		user.setTextFill(Color.web("#bfbfbf"));
		pass.setTextFill(Color.web("#bfbfbf"));
		user.setStyle("-fx-font-weight: bold");
		pass.setStyle("-fx-font-weight: bold");
		user.setLayoutX(30);
		user.setLayoutY(33);
		txtuser.setLayoutX(130);
		txtuser.setLayoutY(30);
		pass.setLayoutX(30);
		pass.setLayoutY(83);
		txtpass.setLayoutX(130);
		txtpass.setLayoutY(80);
		guardar.setLayoutX(30);
		guardar.setLayoutY(160);
		guardar.setPrefSize(90, 35);
		cancelar.setLayoutX(200);
		cancelar.setLayoutY(160);
		cancelar.setPrefSize(90, 35);
		guardar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				configUserData(txtuser.getText(), txtpass.getText());
				dialog.close();
			}
		});
		cancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				dialog.close();
			}
		});
		pane.getChildren().addAll(user, txtuser, pass, txtpass, guardar,
				cancelar);
		dialog.setScene(scene);
		dialog.setTitle("Establecer Usuario y Contraseña");
		dialog.setResizable(false);
		dialog.show();
	}

	// configuracion del y user y pass del gestor
	public void configUserData(String user, String pass) {

		File archivo = null;
		FileWriter fw = null;
		BufferedWriter escritor = null;
		try {

			archivo = new File(System.getProperty("user.home") + "\\AppData"
					+ "\\Local" + "\\agenda" + "\\userdata.txt");
			if (!archivo.exists()) {
				archivo.createNewFile();
				fw = new FileWriter(archivo);
				escritor = new BufferedWriter(fw);
				escritor.write(user);
				escritor.newLine();
				escritor.write(pass);
				escritor.flush();
				Dialogs.showInformationDialog(LoginController.ventanaGestor,
						"Sus datos se han registrado satisfactoriamente.",
						"Información", "Informe");
			} else {
				modUserData(user, pass);
			}

		} // fin try

		catch (IOException ioe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,
					"Error al registrar sus datos (IOException).", "Informe:", "Error...");
		}
		catch (NullPointerException npe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,"No se ha podido escribir sus datos (NullPointerException)");
		}
		finally {
			try {
				if(null != fw){
				fw.close(); }
			} catch (IOException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,
						"No se puede cerrar el archivo", "Error", "Error");
			} catch (NullPointerException npe) {
				System.err.println(npe.getMessage());
			}
		} // fin finally

	}

	// si el fichero la existe se elimina el fichero y se vuelve a generar uno nuevo
	private void modUserData(String user, String pass) {

		File archivo = null;
		FileWriter fw = null;
		BufferedWriter escritor = null;
		try {
			archivo = new File(System.getProperty("user.home") + "\\AppData"
					+ "\\Local" + "\\agenda" + "\\userdata.txt");
			archivo.delete();
			archivo.createNewFile();
			fw = new FileWriter(archivo);
			escritor = new BufferedWriter(fw);
			escritor.write(user);
			escritor.newLine();
			escritor.write(pass);
			escritor.flush();
			Dialogs.showInformationDialog(LoginController.ventanaGestor,
					 "Sus datos se han modificado satisfactoriamente.",
					 "Información", "Informe");

		} // fin try
		
		catch (IOException ioe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,
					"No se puede editar sus datos (IOException).", "Información", "Informe");
		}
		catch (NullPointerException npe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,"No se ha podido escribir sus datos (NullPointerException)");
		}
		finally {
			try {
				if (null != fw){
				fw.close(); }
			} catch (IOException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,
						"No se puede cerrar el archivo", "Error", "Error");
			}
		} // fin finally

	}

	
	
	/* ********************************************************************************************************************
	 * METODO PARA ESTABLECER EL USUARIO Y CONTRASEÑA DE LA BASE DE DATOS
	 * *******************************************************************************************************************/
	
	// ventana para la configuracion de la database
	@FXML
	public void configDatabase() {
		final Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 350, 250);
		pane.setStyle("-fx-background-color: #2E2E2E");

		Label user = new Label("Root:");
		final TextField txtroot = new TextField();
		Label pass = new Label("Contraseña:");
		final PasswordField txtpassw = new PasswordField();
		Button guardar = new Button("Guardar");
		Button cancelar = new Button("Cancelar");
		guardar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setLoginDatabase(txtroot.getText(), txtpassw.getText());
				dialog.close();
			}
		});
		cancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				dialog.close();
			}
		});
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
		cancelar.setPrefSize(90, 35);
		pane.getChildren().addAll(user, txtroot, pass, txtpassw, guardar,
				cancelar);

		dialog.setScene(scene);
		dialog.setTitle("Establecer Base de datos");
		dialog.setResizable(false);
		dialog.show();
	}

	// metodo para establecer el usuario y contraseña de la base de datos
	public void setLoginDatabase(String user, String pass) {

		File archivo = null;
		FileWriter fw = null;
		BufferedWriter escritor = null;
		try {

			archivo = new File(System.getProperty("user.home") + "\\AppData"
					+ "\\Local" + "\\agenda" + "\\database.txt");
			if (!archivo.exists()) {
				archivo.createNewFile();
				fw = new FileWriter(archivo);
				escritor = new BufferedWriter(fw);
				escritor.write(user);
				escritor.newLine();
				escritor.write(pass);
				escritor.flush();
				Dialogs.showInformationDialog(
						LoginController.ventanaGestor,
						"Sus configuracion se ha registrado satisfactoriamente.",
						"Información", "Informe");
			} else {
				modLoginDatabase(user, pass);
				
			}

		} // fin try

		catch (IOException ioe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,
					"Error al registrar su configuracion.", "Información",
					"Informe");
		} catch (NullPointerException npe) {
			System.err.println(npe.getMessage());
		}
		
		finally {
			try {
				if(null != fw) {
				fw.close(); }
			} catch (IOException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,
						"No se puede cerrar el archivo", "Error", "Error");
			} catch (NullPointerException npe) {
				System.err.println(npe.getMessage());
			}
		} // fin finally
	}

	// si el fichero la existe se elimina el fichero y se vuelve a generar uno nuevo
	private void modLoginDatabase(String user, String pass) {

		File archivo = null;
		FileWriter fw = null;
		BufferedWriter escritor = null;
		try {
			archivo = new File(System.getProperty("user.home") + "\\AppData"
					+ "\\Local" + "\\agenda" + "\\database.txt");
			archivo.delete();
			archivo.createNewFile();
			fw = new FileWriter(archivo);
			escritor = new BufferedWriter(fw);
			escritor.write(user);
			escritor.newLine();
			escritor.write(pass);
			escritor.flush();
			Dialogs.showInformationDialog(LoginController.ventanaGestor,
					 "Sus configuracion de base de datos se han modificado satisfactoriamente.",
					 "Información", "Informe");
		} // fin try
		
		catch (IOException ioe) {
			Dialogs.showErrorDialog(LoginController.ventanaGestor,
					"Error al editar los datos de su Database.", "Información",
					"Informe");
		}
		catch (NullPointerException npe) {
			System.err.println(npe.getMessage());
		}
		finally {
			try {
				if(null != fw) {
				fw.close(); }
			} catch (IOException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,
						"No se puede cerrar el archivo", "Error", "Error");
			}
		} // fin finally
	}

	

	
	/* **********************************************************************************************************************
	 * HACE EDITABLE LA TABLA
	 * *********************************************************************************************************************/

	public void tablaEditable() {
		
		
		clDepartamento.setCellFactory(TextFieldTableCell.<Contacto> forTableColumn());
		clDepartamento.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
			@Override
			public void handle(CellEditEvent<Contacto, String> t) {
				String viejoDepartamento = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getPuesto();

				((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).setPuesto(t
						.getNewValue());

				String nuevoDepartamento = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getNombre();
				// actualiza el campo de la tabla
				Conexion.actualizarRegistro(viejoDepartamento, nuevoDepartamento, "Departamento");
			}
		});
		
		
		clPuesto.setCellFactory(TextFieldTableCell.<Contacto> forTableColumn());
		clPuesto.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
			@Override
			public void handle(CellEditEvent<Contacto, String> t) {
				String viejoPuesto = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getPuesto();

				((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).setPuesto(t
						.getNewValue());

				String nuevoPuesto = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getNombre();
				// actualiza el campo de la tabla
				Conexion.actualizarRegistro(viejoPuesto, nuevoPuesto, "Puesto");
			}
		});
		
		
		clNombre.setCellFactory(TextFieldTableCell.<Contacto> forTableColumn());
		clNombre.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
			@Override
			public void handle(CellEditEvent<Contacto, String> t) {
				String viejoNombre = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getNombre();

				((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).setNombre(t
						.getNewValue());

				String nuevoNombre = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getApellido();
				// actualiza el campo de la tabla
				Conexion.actualizarRegistro(viejoNombre, nuevoNombre, "Nombre");
			}
		});

		clApellido.setCellFactory(TextFieldTableCell
				.<Contacto> forTableColumn());
		clApellido
				.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
					@Override
					public void handle(CellEditEvent<Contacto, String> t) {
						String viejoApellido = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getApellido();

						((Contacto) t.getTableView().getItems()
								.get(t.getTablePosition().getRow()))
								.setApellido(t.getNewValue());

						String nuevoApellido = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getApellido();
						// actualiza el campo de la tabla
						Conexion.actualizarRegistro(viejoApellido,
								nuevoApellido, "Apellido");
					}
				});

		clFechaNacimiento.setCellFactory(TextFieldTableCell
				.<Contacto> forTableColumn());
		clFechaNacimiento
				.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
					@Override
					public void handle(CellEditEvent<Contacto, String> t) {
						String viejaFechaNac = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getFechaNacimiento();

						((Contacto) t.getTableView().getItems()
								.get(t.getTablePosition().getRow()))
								.setFechaNacimiento(t.getNewValue());
						String nuevaFechaNac = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getFechaNacimiento();
						// actualiza el campo de la tabla
						Conexion.actualizarRegistro(viejaFechaNac,
								nuevaFechaNac, "FNacimiento");

					}
				});

		clTelefono.setCellFactory(TextFieldTableCell
				.<Contacto> forTableColumn());
		clTelefono
				.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
					@Override
					public void handle(CellEditEvent<Contacto, String> t) {
						String viejoTelefono = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getTelefono();

						((Contacto) t.getTableView().getItems()
								.get(t.getTablePosition().getRow()))
								.setTelefono(t.getNewValue());

						String nuevoTelefono = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getTelefono();
						// actualiza el campo de la tabla
						Conexion.actualizarRegistro(viejoTelefono,
								nuevoTelefono, "Telefono");

					}
				});

		clEmail.setCellFactory(TextFieldTableCell.<Contacto> forTableColumn());
		clEmail.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
			@Override
			public void handle(CellEditEvent<Contacto, String> t) {
				String viejoEmail = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getEmail();

				((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).setEmail(t
						.getNewValue());

				String nuevoEmail = ((Contacto) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).getEmail();
				// actualiza el campo de la tabla
				Conexion.actualizarRegistro(viejoEmail, nuevoEmail, "Email");
			}
		});

		clDireccion.setCellFactory(TextFieldTableCell
				.<Contacto> forTableColumn());
		clDireccion
				.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
					@Override
					public void handle(CellEditEvent<Contacto, String> t) {
						String viejaDireccion = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getDireccion();

						((Contacto) t.getTableView().getItems()
								.get(t.getTablePosition().getRow()))
								.setDireccion(t.getNewValue());

						String nuevaDireccion = ((Contacto) t.getTableView()
								.getItems().get(t.getTablePosition().getRow()))
								.getDireccion();
						// actualiza el campo de la tabla
						Conexion.actualizarRegistro(viejaDireccion,
								nuevaDireccion, "Direccion");
					}
				});
	}

	
	/* ********************************************************************************************************************
	 * INCIALIZARLA TABLA, ASOCIAR GETTERS A COLUMNAS Y AGREGARLAS, COLOCAR UN MENU EN LA TABLA
	 * *******************************************************************************************************************/

	private void inicializarTablaPersonas() {

		// El metodo setCellValueFactory asocia las celdas de la tabla a los getters del modelo
		clDepartamento
				.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"departamento"));
		clPuesto
				.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"puesto"));
		clNombre.
				setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"nombre"));
		clApellido
				.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"apellido"));
		clFechaNacimiento
				.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"fechaNacimiento"));
		clTelefono
				.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"telefono"));
		clEmail.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
				"email"));
		clDireccion
				.setCellValueFactory(new PropertyValueFactory<Contacto, String>(
						"direccion"));
		contactos = FXCollections.observableArrayList();
		tablaContactos.setEditable(true);
		tablaContactos.setItems(contactos);
		ContextMenu menu = new ContextMenu(); // crea un menu
		MenuItem eliminar = new MenuItem("Eliminar"); // crea un menuitem
		eliminar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				remover();
			}
		});
		menu.getItems().addAll(eliminar); // agrega el (los) menuitem al menu
		tablaContactos.setContextMenu(menu); // agrega el menu a la tabla
		try {
			if (Conexion.hayFilasEnTabla() > 0) {
				try {
					contactos = Conexion.cargarContactos();
				} catch (Exception e) {
					Dialogs.showErrorDialog(LoginController.ventanaGestor,
							e.getMessage());
				}
			}
		} catch (NullPointerException e) {
				Dialogs.showErrorDialog(LoginController.ventanaGestor,e.getMessage());
		}
		
		tablaContactos.setItems(contactos); // agrega el arraylist contactos a la tabla

	}

	
	/* *******************************************************************************************************************
	 * IMPLEMENTA ESCUCHADORES DE EVENTOS DE CAMBIO DE TEXTO EN LOS TEXTFIELDS
	 * *******************************************************************************************************************/
	
	private void establecerEventosTextFields() {
		
		txtDepartamento.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtDepartamento.getText().equals("") || txtDepartamento.getText() == null){
					contadorDepartamento = 0;
				}
				else {
					contadorDepartamento = 1;
				}
				esConforme();
			}
		});
		
		txtPuesto.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtPuesto.getText().equals("") || txtPuesto.getText() == null){
					contadorPuesto = 0;
				}
				else {
					contadorPuesto = 1;
				}
				esConforme();
			}
		});
		
		txtNombre.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtNombre.getText().equals("") || txtNombre.getText() == null){
					contadorNombre = 0;
				}
				else {
					contadorNombre = 1;
				}
				esConforme();
			}
		});
		
		txtApellido.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtApellido.getText().equals("") || txtApellido.getText() == null){
					contadorApellido = 0;
				}
				else {
					contadorApellido = 1;
				}
				esConforme();
			}
		});
		
		// Escuchador para Fecha Nacimiento
		txtFechaNacimiento.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtFechaNacimiento.getText().equals("") || txtFechaNacimiento.getText() == null){
					contadorFNacimiento = 0;
				}
				else {
					contadorFNacimiento = 1;
				}
				esConforme();
			}
		});
		
		// Escuchador para el telefono
		txtTelefono.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtTelefono.getText().equals("") || txtTelefono.getText() == null){
					contadorTelefono = 0;
				}
				else {
					contadorTelefono = 1;
				}
				esConforme();
			}
		});
		
		// Escuchador para el email
		txtEmail.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtEmail.getText().equals("") || txtEmail.getText() == null){
					contadorEmail = 0;
				}
				else {
					contadorEmail = 1;
				}
				esConforme();
			}
		});
		
		// Escuchador para la direccion
		txtDireccion.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				
				if(txtDireccion.getText().equals("") || txtDireccion.getText() == null){
					contadorDireccion = 0;
				}
				else {
					contadorDireccion = 1;
				}
				esConforme();
			}
		});
	}
	
	/* *****************************************************************************************************************
	 * DETERMINA SI SE HABILITAN O DESHABILITAN LOS CONTROLES
	 * ****************************************************************************************************************/
	private void esConforme(){
		suma = contadorDepartamento + contadorPuesto + contadorNombre + contadorApellido + contadorFNacimiento + contadorTelefono + contadorEmail + contadorDireccion;
		if (suma == 8) {
			habilitar();
		}
		else {
			deshabilitar();
		}
			 
		}
	
	/* ******************************************************************************************************************
	 * HABILITA LOS CONTROLES
	 * *****************************************************************************************************************/
	private void habilitar() {
		btnGuardar.setDisable(false);
	}
	
	/* ******************************************************************************************************************
	 * DESHABILITA LOS CONTROLES
	 * ******************************************************************************************************************/
	private void deshabilitar() {
		btnGuardar.setDisable(true);
	}
		
	/* *******************************************************************************************************************
	 * METODO QUE INICIALIZA EL FORMULARIO FXML. AQUI PUEDEN INCIALIZARSE COMPONENTES TAMBIEN
	 * *******************************************************************************************************************/
	
	// este metodo se llama al iniciar el fxml
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.inicializarTablaPersonas();
		this.tablaEditable();
		establecerEventosTextFields(); // llama al metodo para escuchar los cambios en los textifelds
		// para saber cuando habilitar o no los botones
		deshabilitar(); // deshabilita los botones guardar y borrar por defecto
	}
		
	
} // FIN.
