package view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class Principal extends Application{
	
    @Override
    public void start(Stage stage) {
    	
        TelaLogin telaLogin = new TelaLogin();
        Scene scene = telaLogin.getScene();

        // Configura o Stage (janela)
        stage.setTitle("Imobiliária xxxx - Login");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}