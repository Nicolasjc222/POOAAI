package view;

import java.sql.SQLException;

import controller.ImovelController;
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
import model.Imovel;

public class TelaCadastroImovel {
	
	private TextField tfEndereco;
	private TextField tfTipoPropriedade;
	private TextField tfArea;
	private TextField tfValor;
	private TextField tfComodos;
	private Label lblErro;
	
	private ImovelController imovelCtrl;

	public Scene getScene() {
		// VBox principal para organizar os componentes verticalmente
		VBox root = new VBox(20); // Espaçamento de 20 pixels entre componentes
		root.setAlignment(Pos.CENTER); // Centraliza os componentes
		root.setPadding(new Insets(40)); // Padding de 40 pixels em todos os lados

		// Fundo claro para melhor aparência
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

		// Título da tela
		Label lblTitulo = new Label("Cadastro de Livros");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.DARKBLUE);

		// HBox para linha do endereço do imovel
		HBox hboxEndereco = new HBox(30);
		hboxEndereco.setAlignment(Pos.CENTER_LEFT);
		Label lblEndereco = new Label("Endereço:");
		lblEndereco.setFont(Font.font(14));
		lblEndereco.setPrefWidth(85);
		lblEndereco.setMinWidth(60);
		tfEndereco = new TextField();
		tfEndereco.setPrefWidth(250);
		tfEndereco.setPromptText("Digite o endereço do imóvel"); // Texto de dica
		hboxEndereco.getChildren().addAll(lblEndereco, tfEndereco);

		// HBox para linha do tipo de propriedade do imovel
		HBox hboxTipoPropriedade = new HBox(30);
		hboxTipoPropriedade.setAlignment(Pos.CENTER_LEFT);
		Label lblTipoPropriedade = new Label("Tipo de propriedade:");
		lblTipoPropriedade.setFont(Font.font(14));
		lblTipoPropriedade.setPrefWidth(85);
		lblTipoPropriedade.setMinWidth(60);
		tfTipoPropriedade = new TextField();
		tfTipoPropriedade.setPrefWidth(250);
		tfTipoPropriedade.setPromptText("Digite o tipo da propriedade(Apartamento, Casa)"); // Texto de dica
		hboxTipoPropriedade.getChildren().addAll(lblTipoPropriedade, tfTipoPropriedade);

		// HBox para linha da area do imovel
		HBox hboxArea = new HBox(30);
		hboxArea.setAlignment(Pos.CENTER_LEFT);
		Label lblArea = new Label("Área:");
		lblArea.setFont(Font.font(14));
		lblArea.setPrefWidth(85);
		lblArea.setMinWidth(60);
		tfArea = new TextField();
		tfArea.setPrefWidth(250);
		tfArea.setPromptText("Digite a área do imóvel(em m^2)"); // Texto de dica
		hboxArea.getChildren().addAll(lblArea, tfArea);

		// HBox para linha do valor do imovel
		HBox hboxValor = new HBox(30);
		hboxValor.setAlignment(Pos.CENTER_LEFT);
		Label lblValor = new Label("Valor:");
		lblValor.setFont(Font.font(14));
		lblValor.setPrefWidth(85);
		lblValor.setMinWidth(60);
		tfValor = new TextField();
		tfValor.setPrefWidth(250);
		tfValor.setPromptText("Digite o valor do imóvel(em R$)"); // Texto de dica
		hboxValor.getChildren().addAll(lblValor, tfValor);

		// HBox para linha dos comodos do imovel
		HBox hboxComodos = new HBox(30);
		hboxComodos.setAlignment(Pos.CENTER_LEFT);
		Label lblComodos = new Label("Cômodos:");
		lblComodos.setFont(Font.font(14));
		lblComodos.setPrefWidth(85);
		lblComodos.setMinWidth(60);
		tfComodos = new TextField();
		tfComodos.setPrefWidth(250);
		tfComodos.setPromptText("Digite a quantidade de cômodos do imóvel"); // Texto de dica
		hboxComodos.getChildren().addAll(lblComodos, tfComodos);

		// Botão Salvar com estilo
		Button btnSalvar = new Button("Salvar");
		btnSalvar.setPrefWidth(150);
		btnSalvar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnSalvar.setStyle(
				"-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnSalvar.setOnAction(e -> {
			try {
				handleCadastro();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); // Ação do botão

		// Label para mensagens de erro/sucesso
		lblErro = new Label();
		lblErro.setTextFill(Color.RED);
		lblErro.setFont(Font.font(12));
		lblErro.setStyle("-fx-padding: 10 0 0 0;");

		// Adiciona todos os componentes ao VBox principal
		root.getChildren().addAll(lblTitulo, hboxEndereco, hboxTipoPropriedade, hboxArea, hboxValor, hboxComodos, btnSalvar, lblErro);

		// Cria a Scene com tamanho fixo (400x350)
		Scene scene = new Scene(root, 450, 400);
		return scene;
	}

	private boolean validarCampos() {
        String endereco = tfEndereco.getText().trim();
        String tipoPropriedade = tfTipoPropriedade.getText().trim();
        boolean areaOk;
        boolean valorOk;
        boolean comodosOk;
        
        try {
        	Integer.parseInt(tfArea.getText().trim());
        	areaOk = true;
        } catch (NumberFormatException e) {
        	areaOk = false;
        }
        
        try {
        	Integer.parseInt(tfValor.getText().trim());
        	valorOk = true;
        } catch (NumberFormatException e) {
        	valorOk = false;
        }

        
        try {
            Integer.parseInt(tfComodos.getText().trim());
            comodosOk = true;
        } catch (NumberFormatException e) {
        	comodosOk = false;
        }
        		  
        return 	!endereco.isEmpty() && 
        		!tipoPropriedade.isEmpty() && 
        		valorOk &&
        		areaOk &&
        		comodosOk;
    }

	private void handleCadastro() throws SQLException {
		lblErro.setText(""); // Limpa mensagem anterior
		if (validarCampos()) {
			lblErro.setTextFill(Color.GREEN);
			lblErro.setText("Cadastro realizado com sucesso!");
			String enderecoTxt = tfEndereco.getText().trim();
			String tipoPropriedadeTxt = tfTipoPropriedade.getText().trim();
			int area = Integer.parseInt(tfArea.getText().trim());
			int valor = Integer.parseInt(tfValor.getText().trim());
		    int comodos = Integer.parseInt(tfComodos.getText().trim());

		    Imovel imovel = new Imovel (enderecoTxt, tipoPropriedadeTxt, area, valor, comodos);
		    imovelCtrl = new ImovelController(imovel);
		    imovelCtrl.salvarImovel();
		    
		} else {
			lblErro.setTextFill(Color.RED);
			lblErro.setText("Por favor, preencha todos os campos!");
			tfEndereco.requestFocus(); // Foca no campo livro
		}
	}

	
}
