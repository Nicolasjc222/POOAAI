package view;

import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Trocador {
    private static Stage stage;
    private static Stack<String> historico = new Stack<>();
    private static String telaAtual;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void trocarTela(String tela) {
        if (telaAtual != null) {
            historico.push(telaAtual); // salva o nome da tela atual
        }
        telaAtual = tela;
        stage.setScene(criarTela(tela));
    }

    public static void voltarTela() {
        if (!historico.isEmpty()) {
            telaAtual = historico.pop();
            stage.setScene(criarTela(telaAtual)); // recria a tela
        }
    }

    private static Scene criarTela(String tela) {
        return switch (tela) {
        case "TelaLogin"   -> new TelaLogin().getScene();
		case "TelaMenu"  -> new TelaMenu().getScene();
		case "TelaCadastroCliente" -> new TelaCadastroCliente().getScene();
		case "TelaAtualizarCliente" -> new TelaAtualizarCliente().getScene();
		case "TelaRemoverCliente" -> new TelaRemoverCliente().getScene();
		case "TelaCadastroImovel" -> new TelaCadastroImovel().getScene();
		case "TelaAtualizarImovel" -> new TelaAtualizarImovel().getScene();
		case "TelaRemoverImovel" -> new TelaRemoverImovel().getScene();
		default        -> throw new IllegalArgumentException("Tela desconhecida: " + tela);
        };
    }
}

