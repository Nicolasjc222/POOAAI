package view;


import javafx.stage.Stage;
import javafx.application.Application;

public class Principal extends Application{
	
    @Override
    public void start(Stage stage) {

        // Configura o Stage (janela)
        stage.setTitle("Imobiliária 1234 - Sistema de controle");
        Trocador.setStage(stage);
        Trocador.trocarTela("TelaLogin");
        stage.setResizable(true);
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}