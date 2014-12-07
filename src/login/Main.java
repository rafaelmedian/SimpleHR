package login;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	public static Stage ventana; // declaro la stage static para poderla usar en LoginController
	@Override
	public void start(Stage primaryStage) {
		
			ventana = primaryStage;
			inicializar(ventana);
			ventana.show();
	}
	private void inicializar(Stage primaryStage){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Vista.fxml"));
		} catch (IOException e) {
			System.out.println("No se puede encontrar el fichero FXML");
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("login/itlaicon.png"));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Analisis y diseno - Login");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
