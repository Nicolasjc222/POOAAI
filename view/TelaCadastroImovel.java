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
import javafx.scene.layout.GridPane;
import model.Imovel;
import model.Endereco;

public class TelaCadastroImovel {
	
	private TextField tfRua;
	private TextField tfCep;
	private TextField tfCidade;
	private TextField tfBairro;
	private TextField tfUF;
	private TextField tfNumero;
	private TextField tfComplemento;
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
		Label lblTitulo = new Label("Cadastro de Imóveis");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.DARKBLUE);
		
		// Gridpane Endereço
		Label lblEndereco = new Label("Endereço:");
		lblEndereco.setFont(Font.font(14));
		lblEndereco.setPrefWidth(85);
		lblEndereco.setMinWidth(60);
		VBox vboxEndereco = new VBox(10); // Espaçamento de 20 pixels entre componentes
		vboxEndereco.setAlignment(Pos.CENTER); // Centraliza os componentes
		
		Label lblCep = new Label("CEP:");
		lblCep.setFont(Font.font(14));
		tfCep = new TextField();
		tfCep.setPrefWidth(250);
		tfCep.setPromptText("00000-000"); // Texto de dica

		Label lblRua = new Label("Rua:");
		lblRua.setFont(Font.font(14));
		tfRua = new TextField();
		tfRua.setPrefWidth(250);
		tfRua.setPromptText("Digite a rua do imóvel"); // Texto de dica
		
		Label lblCidade = new Label("Cidade:");
		lblCidade.setFont(Font.font(14));
		tfCidade = new TextField();
		tfCidade.setPrefWidth(250);
		tfCidade.setPromptText("Digite a cidade do imóvel"); // Texto de dica
		
		Label lblBairro = new Label("Bairro:");
		lblBairro.setFont(Font.font(14));
		tfBairro = new TextField();
		tfBairro.setPrefWidth(250);
		tfBairro.setPromptText("Digite o bairro do imóvel"); // Texto de dica
		
		Label lblUF = new Label("UF:");
		lblUF.setFont(Font.font(14));
		tfUF = new TextField();
		tfUF.setPrefWidth(250);
		tfUF.setPromptText("Digite a UF do imóvel"); // Texto de dica
		
		Label lblNumero = new Label("Número:");
		lblNumero.setFont(Font.font(14));
		tfNumero = new TextField();
		tfNumero.setPrefWidth(250);
		tfNumero.setPromptText("Digite o endereço do imóvel"); // Texto de dica
		
		Label lblComplemento = new Label("Complemento:");
		lblComplemento.setFont(Font.font(14));
		tfComplemento = new TextField();
		tfComplemento.setPrefWidth(250);
		tfComplemento.setPromptText("Digite o complemento do imóvel"); // Texto de dica
		
		GridPane grid = new GridPane();
		grid.setHgap(10);  // espaço entre label e textfield
		grid.setVgap(12);  // espaço entre cada linha
		grid.setAlignment(Pos.CENTER_LEFT);
		//    	  				col  linha
		grid.add(lblCep,    	 0,	  0);
		grid.add(tfCep,    	 	 1,	  0);
		grid.add(lblRua,    	 2,	  0);
		grid.add(tfRua,   		 3,   0);
		grid.add(lblNumero, 	 0,	  1);
		grid.add(tfNumero, 		 1,	  1);
		grid.add(lblBairro,	 	 2,	  1);
		grid.add(tfBairro, 		 3,	  1);
		grid.add(lblCidade, 	 0,   2);
		grid.add(tfCidade,  	 1,   2);
		grid.add(lblUF,    	     2,   2);
		grid.add(tfUF,       	 3,   2);
		grid.add(lblComplemento, 0,   3);
		grid.add(tfComplemento,  1,   3);
		vboxEndereco.getChildren().addAll(lblEndereco, grid);
		// HBox para linha do tipo de propriedade do imovel
		HBox hboxTipoPropriedade = new HBox(30);
		hboxTipoPropriedade.setAlignment(Pos.CENTER_LEFT);
		Label lblTipoPropriedade = new Label("Tipo de propriedade:");
		lblTipoPropriedade.setFont(Font.font(14));
		lblTipoPropriedade.setPrefWidth(150);
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
		root.getChildren().addAll(lblTitulo, vboxEndereco, hboxTipoPropriedade, hboxArea, hboxValor, hboxComodos, btnSalvar, lblErro);

		// Cria a Scene com tamanho fixo (400x350)
		Scene scene = new Scene(root, 800, 600);
		return scene;
	}

	private boolean validarCampos() {
		// String rua, String bairro, String cidade, String uf, int cep, int numero, String complemento
        String tipoPropriedade = tfTipoPropriedade.getText().trim();
        String r = tfRua.getText().trim();
        String b = tfBairro.getText().trim();
        String c = tfCidade.getText().trim();
        String uf = tfUF.getText().trim();
        String comp = tfComplemento.getText().trim();
        
        boolean areaOk;
        boolean valorOk;
        boolean comodosOk;
        boolean cepOk;
        boolean numeroOk;
        boolean ufOk;
        
        try {
        	Integer.parseInt(tfCep.getText().trim());
        	cepOk = true;
        } catch (NumberFormatException e) {
        	cepOk = false;
        }
        
        try {
        	Integer.parseInt(tfNumero.getText().trim());
        	numeroOk = true;
        } catch (NumberFormatException e) {
        	numeroOk = false;
        }
        
        
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
        
        if(comp == null) {
        	comp = "";
        }
        if(uf.length() != 2) {
        	ufOk = false;
        } else {
        	ufOk = true;
        }
        
        return 	!r.isEmpty() &&
        		!b.isEmpty() &&
        		!c.isEmpty() &&
        		!tipoPropriedade.isEmpty() && 
        		ufOk &&
        		cepOk &&
        		numeroOk &&
        		valorOk &&
        		areaOk &&
        		comodosOk;
    }

	private void handleCadastro() throws SQLException {
		lblErro.setText(""); // Limpa mensagem anterior
		if (validarCampos()) {
			lblErro.setTextFill(Color.GREEN);
			lblErro.setText("Cadastro realizado com sucesso!");
			String tipoPropriedadeTxt = tfTipoPropriedade.getText().trim();
			int area = Integer.parseInt(tfArea.getText().trim());
			int valor = Integer.parseInt(tfValor.getText().trim());
		    int comodos = Integer.parseInt(tfComodos.getText().trim());
		    int numero = Integer.parseInt(tfNumero.getText().trim());
		    int cep = Integer.parseInt(tfCep.getText().trim());
		    Endereco endereco = new Endereco(tfRua.getText().trim(), tfBairro.getText().trim(), tfCidade.getText().trim(), tfUF.getText().trim(), cep, numero, tfComplemento.getText().trim());
		    Imovel imovel = new Imovel (endereco, tipoPropriedadeTxt, area, valor, comodos);
		    imovelCtrl = new ImovelController(imovel);
		    imovelCtrl.salvarImovel();
		    
		} else {
			lblErro.setTextFill(Color.RED);
			lblErro.setText("Por favor, preencha todos os campos!");
			tfCep.requestFocus(); // Foca no campo CEP
		}
	}

	
}
