package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TelaMenu {

    public Scene getScene() {
        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

        Label lblTitulo = new Label("Menu Principal");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.DARKBLUE);

        // --- Seção Cliente ---
        Label lblCliente = new Label("Cliente");
        lblCliente.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblCliente.setTextFill(Color.DARKBLUE);

        Button btnCadastrarCliente  = criarBotao("Cadastrar",  "#4CAF50");
        Button btnAtualizarCliente  = criarBotao("Atualizar",  "#2196F3");
        Button btnExcluirCliente    = criarBotao("Excluir",    "#f44336");

        btnCadastrarCliente.setOnAction(e -> Trocador.trocarTela("TelaCadastroCliente"));
        btnAtualizarCliente.setOnAction(e -> Trocador.trocarTela("TelaAtualizarCliente"));
        btnExcluirCliente.setOnAction(e ->   Trocador.trocarTela("TelaRemoverCliente"));

        HBox hboxCliente = new HBox(20, btnCadastrarCliente, btnAtualizarCliente, btnExcluirCliente);
        hboxCliente.setAlignment(Pos.CENTER);

        VBox vboxCliente = new VBox(12, lblCliente, hboxCliente);
        vboxCliente.setAlignment(Pos.CENTER);
        vboxCliente.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");

        // --- Seção Imóvel ---
        Label lblImovel = new Label("Imóvel");
        lblImovel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblImovel.setTextFill(Color.DARKBLUE);

        Button btnCadastrarImovel = criarBotao("Cadastrar", "#4CAF50");
        Button btnAtualizarImovel = criarBotao("Atualizar", "#2196F3");
        Button btnExcluirImovel   = criarBotao("Excluir",   "#f44336");
        
        btnCadastrarImovel.setOnAction(e -> Trocador.trocarTela("TelaCadastroImovel"));
        btnAtualizarImovel.setOnAction(e -> Trocador.trocarTela("TelaAtualizarImovel"));
        btnExcluirImovel.setOnAction(e ->   Trocador.trocarTela("TelaRemoverImovel"));

        HBox hboxImovel = new HBox(20, btnCadastrarImovel, btnAtualizarImovel, btnExcluirImovel);
        hboxImovel.setAlignment(Pos.CENTER);

        VBox vboxImovel = new VBox(12, lblImovel, hboxImovel);
        vboxImovel.setAlignment(Pos.CENTER);
        vboxImovel.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");
        
        Button btnVoltar = criarBotao("Voltar", "#0c27f0");
        btnVoltar.setOnAction(e -> {
        Trocador.voltarTela();
        });
        
        root.getChildren().addAll(lblTitulo, vboxCliente, vboxImovel, btnVoltar);

        return new Scene(root, 1280, 720);
    }

    // método auxiliar para não repetir estilo de botão
    private Button criarBotao(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setPrefWidth(150);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btn.setStyle("-fx-background-color: " + cor + "; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        return btn;
    }
}