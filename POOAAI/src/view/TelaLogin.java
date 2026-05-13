package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// Tela de login principal

public class TelaLogin {

    private TextField tfUsuario;  // Campo para o nome de usuário
    private PasswordField pfSenha; // Campo para a senha (mascara os caracteres)
    private Label lblErro;         // Label para exibir mensagens de erro ou sucesso

    /**
     * Método principal que cria e configura toda a interface da tela de login.
     * Retorna uma Scene pronta para ser exibida no Stage.
     * @return Scene configurada com a tela de login
     */
    public Scene getScene() {
        // VBox principal para organizar os componentes verticalmente
        VBox root = new VBox(20); // Espaçamento de 20 pixels entre componentes
        root.setAlignment(Pos.CENTER); // Centraliza os componentes
        root.setPadding(new Insets(40)); // Padding de 40 pixels em todos os lados

        // Fundo claro para melhor aparência
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

        // Título da tela
        Label lblTitulo = new Label("Login");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.DARKBLUE);

        // HBox para linha do usuário
        HBox hboxUsuario = new HBox(15);
        hboxUsuario.setAlignment(Pos.CENTER);
        Label lblUsuario = new Label("Usuário:");
        lblUsuario.setFont(Font.font(14));
        tfUsuario = new TextField();
        tfUsuario.setPrefWidth(250);
        tfUsuario.setPromptText("Digite seu usuário"); // Texto de dica
        hboxUsuario.getChildren().addAll(lblUsuario, tfUsuario);

        // HBox para linha da senha
        HBox hboxSenha = new HBox(15);
        hboxSenha.setAlignment(Pos.CENTER);
        Label lblSenha = new Label("Senha:  ");
        lblSenha.setFont(Font.font(14));
        pfSenha = new PasswordField();
        pfSenha.setPrefWidth(250);
        pfSenha.setPromptText("Digite sua senha");
        hboxSenha.getChildren().addAll(lblSenha, pfSenha);
        
        
        // Botão Entrar com estilo
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setPrefWidth(150);
        btnEntrar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnEntrar.setAlignment(Pos.CENTER);
        btnEntrar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        btnEntrar.setOnAction(e -> handleLogin()); // Ação do botão

        // Label para mensagens de erro/sucesso
        lblErro = new Label();
        lblErro.setTextFill(Color.RED);
        lblErro.setFont(Font.font(12));
        lblErro.setStyle("-fx-padding: 10 0 0 0;");

        // Adiciona todos os componentes ao VBox principal
        root.getChildren().addAll(
            lblTitulo,
            hboxUsuario,
            hboxSenha,
            btnEntrar,
            lblErro
        );

        // Cria a Scene com tamanho fixo
        Scene scene = new Scene(root, 1280, 720);
        return scene;
    }

    /**
     * Método para validar se os campos de usuário e senha não estão vazios.
     * @return true se ambos os campos estão preenchidos, false caso contrário
     */
    private int validarCampos() {
        String usuario = tfUsuario.getText().trim();
        String senha = pfSenha.getText().trim();
        if(usuario.equals("admin") && senha.equals("admin")) {
        	return 99;
        } else if(usuario.isEmpty() || senha.isEmpty()) {
        	return -1;
        } else return 0;
//        if(verificarUsuario(usuario)) {
//        	if(verificarSenha(usuario, senha)) {
//        		valido = 99;
//        	}
//        }
    }

    /**
     * Manipulador do evento do botão Entrar.
     * Valida os campos e exibe mensagem apropriada.
     * (Futuramente, aqui você pode conectar ao banco de dados)
     */
    private void handleLogin() {
        lblErro.setText(""); // Limpa mensagem anterior
        switch(validarCampos()) {
        case -1: 
        	lblErro.setTextFill(Color.RED);
        	lblErro.setText("Por favor, preencha todos os campos!");
        	tfUsuario.requestFocus();
        	break;
        case 0:
        	lblErro.setTextFill(Color.RED);
            lblErro.setText("Login ou senha inválido");
            tfUsuario.requestFocus(); // Foca no campo usuário
            break;
        case 99:
        	lblErro.setTextFill(Color.GREEN);
            lblErro.setText("Login realizado com sucesso!");
            // Principal.trocarTela("TelaMenu");
        }
    }

    // Exemplo de uso (para testar em uma classe Main):
    /*
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
    */
}
