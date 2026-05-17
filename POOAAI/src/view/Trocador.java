package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Trocador {
    private static Stage stage;
    public static void setStage(Stage s) {
        stage = s;
    }
    public static void trocarTela(String tela) {
    	Scene scene = switch (tela) {
    		case "TelaLogin"   -> new TelaLogin().getScene();
	        case "TelaMenu"  -> new TelaMenu().getScene();
	        //case "TelaCadastroCliente" -> new TelaCadastroCliente().getScene();
	        case "TelaCadastroImovel" -> new TelaCadastroImovel().getScene();
	        default        -> throw new IllegalArgumentException("Tela desconhecida: " + tela);
	        };
	        stage.setScene(scene);
	}
}
