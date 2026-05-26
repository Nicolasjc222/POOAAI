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
	        case "TelaCadastroCliente" -> new TelaCadastroCliente().getScene();
//	        case "TelaAtualizarCliente" -> new TelaAtualizarCliente().getScene();
//	        case "TelaRemoverCliente" -> new TelaRemoverCliente().getScene();
	        case "TelaCadastroImovel" -> new TelaCadastroImovel().getScene();
//	        case "TelaAtualizarImovel" -> new TelaAtualizarImovel().getScene();
//	        case "TelaRemoverImovel" -> new TelaRemoverImovel().getScene();
	        default        -> throw new IllegalArgumentException("Tela desconhecida: " + tela);
	        };
	        stage.setScene(scene);
	}
}
