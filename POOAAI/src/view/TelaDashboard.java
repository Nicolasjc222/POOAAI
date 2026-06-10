package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TelaDashboard {

	// Onde o conteúdo (formulários) será injetado
	private static StackPane areaDeConteudo; 

	public Scene getScene() {
		BorderPane root = new BorderPane();

		// 1. Sidebar (Menu Lateral)
		VBox sidebar = new VBox(20);
		sidebar.setPrefWidth(250);
		sidebar.setStyle("-fx-background-color: #2C3E50;"); // Azul Escuro
		sidebar.setPadding(new Insets(30, 20, 30, 20));

		Label lblLogo = new Label("ImobSystem");
		lblLogo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
		lblLogo.setTextFill(Color.WHITE);
		VBox.setMargin(lblLogo, new Insets(0, 0, 30, 0));

		// Botões de navegação lateral
		Button btnClientes = criarBotaoMenu("Gerenciar Clientes");
		Button btnImoveis  = criarBotaoMenu("Gerenciar Imóveis");
		Button btnEndereco = criarBotaoMenu("Gerenciar Endereços");
		Button btnSair     = criarBotaoMenu("Sair");

		// Estilo especial para o botão sair
		btnSair.setStyle("-fx-background-color: transparent; -fx-text-fill: #E74C3C; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10 15; -fx-cursor: hand;");

		// Ações: Injetar as telas de cadastro no StackPane central

		// No evento do botão "Gerenciar Clientes" do seu menu lateral:
		btnClientes.setOnAction(e -> {
			TelaGerenciarClientes telaGerenciar = new TelaGerenciarClientes();
			TelaDashboard.mudarConteudo(telaGerenciar.getLayout()); // Substitua mudarConteudo pelo nome real do método estático que você usa para injetar no painel central do Dashboard.
		});

		// No evento do botão "Gerenciar Imóveis" do seu menu lateral:
		btnImoveis.setOnAction(e -> {
			TelaGerenciarImoveis telaGerenciar = new TelaGerenciarImoveis();
			TelaDashboard.mudarConteudo(telaGerenciar.getLayout());
		});

		btnEndereco.setOnAction(e -> {
			TelaGerenciarEnderecos telaGerenciar = new TelaGerenciarEnderecos();
			TelaDashboard.mudarConteudo(telaGerenciar.getLayout());
		});

		btnSair.setOnAction(e -> Trocador.trocarTela("TelaLogin"));

		sidebar.getChildren().addAll(lblLogo, btnClientes, btnImoveis, btnEndereco, btnSair);

		// 2. Área de Conteúdo (Centro)
		areaDeConteudo = new StackPane();
		areaDeConteudo.setStyle("-fx-background-color: #F4F6F8;");

		// Tela inicial vazia (Boas vindas)
		Label lblBoasVindas = new Label("Bem-vindo ao sistema.\nSelecione uma opção no menu lateral.");
		lblBoasVindas.setTextFill(Color.GRAY);
		lblBoasVindas.setFont(Font.font("Segoe UI", 16));
		areaDeConteudo.getChildren().add(lblBoasVindas);

		root.setLeft(sidebar);
		root.setCenter(areaDeConteudo);

		return new Scene(root, 1280, 720);
	}

	private Button criarBotaoMenu(String texto) {
		Button btn = new Button(texto);
		btn.setMaxWidth(Double.MAX_VALUE);
		// Design flat, sem bordas, alinhado a esquerda
		btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ECF0F1; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10 15; -fx-cursor: hand;");

		// Efeito hover simples
		btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #34495E; -fx-text-fill: #ECF0F1; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10 15; -fx-cursor: hand;"));
		btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ECF0F1; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10 15; -fx-cursor: hand;"));
		return btn;
	}

	// Método que permite trocar o miolo do sistema sem piscar a tela inteira
	public static void mudarConteudo(javafx.scene.Node novoConteudo) {
		areaDeConteudo.getChildren().clear();
		areaDeConteudo.getChildren().add(novoConteudo);
	}
}