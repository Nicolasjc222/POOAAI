package view;

import java.sql.SQLException;

import controller.EnderecoController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Endereco;


public class TelaCadastroEndereco {
	
	private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
	private Label lblErro;
	private EnderecoController enderecoCtrl;
	
	public Region getLayout() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        Label lblTitulo = new Label("Cadastro de Endereços");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Criação do "Card" branco para agrupar o formulário
        VBox cardForm = new VBox(20);
        cardForm.setPadding(new Insets(30));
        cardForm.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");
	
		// --- Seção Endereço ---
	    Label lblEndereco = new Label("Endereço:");
	    lblEndereco.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	
	    tfCep         = new TextField(); tfCep.setPromptText("00000-000");
	    tfRua         = new TextField(); tfRua.setPromptText("Digite a rua do imóvel");
	    tfNumero      = new TextField(); tfNumero.setPromptText("Número");
	    tfBairro      = new TextField(); tfBairro.setPromptText("Bairro");
	    tfCidade      = new TextField(); tfCidade.setPromptText("Cidade");
	    tfUF          = new TextField(); tfUF.setPromptText("UF");
	    tfComplemento = new TextField(); tfComplemento.setPromptText("Complemento (opcional)");
	
	    GridPane gridEndereco = new GridPane();
	    gridEndereco.setHgap(10);
	    gridEndereco.setVgap(12);
	    gridEndereco.setAlignment(Pos.CENTER_LEFT);
	    gridEndereco.add(new Label("CEP:"),         0, 0); gridEndereco.add(tfCep,         1, 0);
	    gridEndereco.add(new Label("Rua:"),         2, 0); gridEndereco.add(tfRua,         3, 0);
	    gridEndereco.add(new Label("Número:"),      0, 1); gridEndereco.add(tfNumero,      1, 1);
	    gridEndereco.add(new Label("Bairro:"),      2, 1); gridEndereco.add(tfBairro,      3, 1);
	    gridEndereco.add(new Label("Cidade:"),      0, 2); gridEndereco.add(tfCidade,      1, 2);
	    gridEndereco.add(new Label("UF:"),          2, 2); gridEndereco.add(tfUF,          3, 2);
	    gridEndereco.add(new Label("Complemento:"), 0, 3); gridEndereco.add(tfComplemento, 1, 3);
	
	    VBox vboxEndereco = new VBox(10, lblEndereco, gridEndereco);
	    vboxEndereco.setAlignment(Pos.CENTER_LEFT);
	    
	    // Instanciar o label de erro antes do HBox
		lblErro = new Label();
	    lblErro.setTextFill(Color.RED);
	    lblErro.setFont(Font.font(12));
	    
		// --- Botão ---
	    Button btnSalvar = new Button("Salvar Endereço");
	    btnSalvar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    btnSalvar.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
	    btnSalvar.setOnAction(e -> {
	        try {
	            handleCadastro();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    });
	    
	    Button btnLimpar = new Button("Limpar");
	    btnLimpar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    btnLimpar.setStyle("-fx-background-color: #95A5A6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
	    btnLimpar.setOnAction(e -> {
	        limparCampos();
	    });
	    
	    Button btnVoltar = new Button("Voltar");
	    btnVoltar.setPrefWidth(150);
	    btnVoltar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    btnVoltar.setStyle("-fx-background-color: #0c27f0; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
	    btnVoltar.setOnAction(e -> {
	    TelaDashboard.mudarConteudo(new TelaGerenciarEnderecos().getLayout());
	    });
	    
	    HBox hboxBotoes = new HBox(15, btnSalvar, btnLimpar, btnVoltar, lblErro);
	    hboxBotoes.setAlignment(Pos.CENTER_LEFT);
	    
	 // Adiciona tudo dentro do cardForm
        cardForm.getChildren().addAll(vboxEndereco,hboxBotoes,lblErro);
        root.getChildren().addAll(lblTitulo, cardForm);

        // Embutir num ScrollPane para evitar que o conteúdo corte
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");

        return scroll;
    }
	    
	    private boolean validarCampos() {
	        boolean numeroOk;

	        try { Integer.parseInt(tfNumero.getText().trim()); numeroOk = true; }
	        catch (NumberFormatException e) { numeroOk = false; }
	        
	        return !tfRua.getText().trim().isEmpty() &&
	               !tfBairro.getText().trim().isEmpty() &&
	               !tfCidade.getText().trim().isEmpty() &&
	               !tfComplemento.getText().trim().isEmpty() &&
	               tfUF.getText().trim().length() == 2 &&
	               tfCep.getText().trim().length() == 8 &&
	               numeroOk;
	    }
	    
	    private void handleCadastro() throws SQLException {
	        lblErro.setText("");
	        if (validarCampos()) {
	            int numero = Integer.parseInt(tfNumero.getText().trim());

	            Endereco endereco = new Endereco(
	                tfRua.getText().trim(), tfBairro.getText().trim(),
	                tfCidade.getText().trim(), tfUF.getText().trim(),
	                tfCep.getText().trim(), numero, tfComplemento.getText().trim()
	            );
	            enderecoCtrl = new EnderecoController(endereco);
	            enderecoCtrl.salvarEndereco();

	            lblErro.setTextFill(Color.GREEN);
	            lblErro.setText("Cadastro realizado com sucesso!");
	            limparCampos();
	        } else {
	            lblErro.setTextFill(Color.RED);
	            lblErro.setText("Por favor, preencha todos os campos corretamente!");
	        }
	    }
	    private void limparCampos() {
	    	tfRua.setText("");
	    	tfCep.setText("");
	    	tfCidade.setText("");
	    	tfBairro.setText("");
	    	tfUF.setText("");
	    	tfNumero.setText("");
	    	tfComplemento.setText("");
	    }
}
