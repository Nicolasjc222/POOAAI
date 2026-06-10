package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TelaLogin {

	private TextField tfUsuario;
	private PasswordField pfSenha;
	private Label lblErro;

	public Scene getScene() {
		// Fundo principal neutro
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #F4F6F8;"); // Cinza bem claro e moderno

		// Card de Login (Branco com leve sombra)
		VBox card = new VBox(20);
		card.setAlignment(Pos.CENTER);
		card.setMaxWidth(400);
		card.setPadding(new Insets(40));
		card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

		Label lblTitulo = new Label("Imobiliária System");
		lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
		lblTitulo.setTextFill(Color.valueOf("#2C3E50")); // Azul escuro sóbrio

		Label lblSubtitulo = new Label("Faça login para continuar");
		lblSubtitulo.setFont(Font.font("Segoe UI", 14));
		lblSubtitulo.setTextFill(Color.GRAY);
		VBox.setMargin(lblSubtitulo, new Insets(-15, 0, 20, 0));

		tfUsuario = new TextField();
		tfUsuario.setPromptText("Usuário");
		tfUsuario.setStyle("-fx-padding: 10; -fx-background-radius: 4; -fx-border-color: #E0E0E0; -fx-border-radius: 4;");

		pfSenha = new PasswordField();
		pfSenha.setPromptText("Senha");
		pfSenha.setStyle("-fx-padding: 10; -fx-background-radius: 4; -fx-border-color: #E0E0E0; -fx-border-radius: 4;");

		Button btnEntrar = new Button("Entrar no Sistema");
		btnEntrar.setMaxWidth(Double.MAX_VALUE); // Ocupa toda a largura do card
		btnEntrar.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 4; -fx-cursor: hand;");

		// Efeito de hover básico no botão
		btnEntrar.setOnMouseEntered(e -> btnEntrar.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 4; -fx-cursor: hand;"));
		btnEntrar.setOnMouseExited(e -> btnEntrar.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 4; -fx-cursor: hand;"));

		btnEntrar.setOnAction(e -> handleLogin());

		lblErro = new Label();
		lblErro.setTextFill(Color.valueOf("#E74C3C"));
		lblErro.setFont(Font.font("Segoe UI", 12));

		card.getChildren().addAll(lblTitulo, lblSubtitulo, tfUsuario, pfSenha, btnEntrar, lblErro);
		root.getChildren().add(card);

		return new Scene(root, 1280, 720);
	}

	private void handleLogin() {
		lblErro.setText("");
		String usuario = tfUsuario.getText().trim();
		String senha = pfSenha.getText().trim();

		if (usuario.isEmpty() || senha.isEmpty()) {
			lblErro.setText("Por favor, preencha todos os campos.");
		} else if (usuario.equals("admin") && senha.equals("admin")) {
			// AQUI MUDAMOS PARA O DASHBOARD EM VEZ DO MENU ANTIGO
			Trocador.trocarTela("TelaDashboard"); 
		} else {
			lblErro.setText("Usuário ou senha inválidos.");
		}
	}
}