package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TelaMenu {
	
	
	public Scene getScene() {
        // VBox principal para organizar os componentes verticalmente
        VBox root = new VBox(20); // Espaçamento de 20 pixels entre componentes
        root.setAlignment(Pos.CENTER); // Centraliza os componentes
        root.setPadding(new Insets(40)); // Padding de 40 pixels em todos os lados

        // Fundo claro para melhor aparência
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

        // Título da tela
        Label lblTitulo = new Label("Menu");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.DARKBLUE);

        
        // Botão Entrar com estilo
        Button btnCadastroCliente = new Button("Cadastro de cliente");
        btnCadastroCliente.setPrefWidth(150);
        btnCadastroCliente.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnCadastroCliente.setAlignment(Pos.CENTER);
        btnCadastroCliente.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        btnCadastroCliente.setOnAction(e -> Trocador.trocarTela("TelaCadastroCliente")); // Ação do botão
        
        Button btnCadastroImovel = new Button("Cadastro de imovel");
        btnCadastroImovel.setPrefWidth(150);
        btnCadastroImovel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnCadastroImovel.setAlignment(Pos.CENTER);
        btnCadastroImovel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        btnCadastroImovel.setOnAction(e -> Trocador.trocarTela("TelaCadastroImovel")); // Ação do botão

        
        // Adiciona todos os componentes ao VBox principal
        root.getChildren().addAll(
            lblTitulo,
            btnCadastroCliente,
            btnCadastroImovel
        );

        // Cria a Scene com tamanho fixo
        Scene scene = new Scene(root, 1280, 720);
        return scene;
    }
	
}
