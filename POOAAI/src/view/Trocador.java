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
            historico.push(telaAtual); // salva o nome da tela atual no histórico
        }
        telaAtual = tela;
        stage.setScene(criarTela(tela));
    }

    public static void voltarTela() {
        if (!historico.isEmpty()) {
            telaAtual = historico.pop();
            stage.setScene(criarTela(telaAtual)); // recria a janela anterior
        }
    }

    /**
     * Cria as Scenes de escopo global do sistema (Janelas inteiras).
     * Sub-telas devem retornar 'Region' e ser abertas usando TelaDashboard.mudarConteudo().
     */
    private static Scene criarTela(String tela) {
        return switch (tela) {
            case "TelaLogin"     -> new TelaLogin().getScene();
            case "TelaDashboard" -> new TelaDashboard().getScene();
            default -> throw new IllegalArgumentException("Tela global inválida ou deve ser renderizada como sub-painel: " + tela);
        };
    }
}