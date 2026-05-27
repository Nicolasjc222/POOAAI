package view;

import java.sql.SQLException;

import controller.EnderecoController;
import controller.ImovelController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Endereco;
import model.Imovel;

public class TelaAtualizarImovel {

	private TextField tfIdImovel;
	private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
	private TextField tfTipoPropriedade, tfArea, tfValor, tfComodos;
	private Label lblErro;
	private Imovel imovelProcurado;

	private ImovelController imovelCtrl;
	private EnderecoController enderecoCtrl;

	public Scene getScene() {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

		Label lblTitulo = new Label("Atualizar Imóveis");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.DARKBLUE);

		Button btnVoltar = new Button("Voltar");
		btnVoltar.setPrefWidth(150);
		btnVoltar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnVoltar.setStyle("-fx-background-color: #0c27f0; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnVoltar.setOnAction(e -> {
			Trocador.voltarTela();
		});

		Label lblIdImovel = new Label("ID Imovel:");
		lblIdImovel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		tfIdImovel = new TextField(); tfIdImovel.setPromptText("ID do cliente a ser editado");

		VBox vboxIdImovel = new VBox(10, lblIdImovel, tfIdImovel);
		vboxIdImovel.setAlignment(Pos.CENTER);

		Button btnVerificarId = new Button("Verificar");
		btnVerificarId.setPrefWidth(150);
		btnVerificarId.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnVerificarId.setStyle("-fx-background-color: #b515e6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnVerificarId.setOnAction(e -> {
			if(verificarId()) {
				// --- Seção Endereço ---
				Label lblEndereco = new Label("Endereço:");
				lblEndereco.setFont(Font.font("Arial", FontWeight.BOLD, 14));

				tfCep         = new TextField(); tfCep.setPromptText("00000-000"); tfCep.setText(imovelProcurado.getEndereco().getCep());
				tfRua         = new TextField(); tfRua.setPromptText("Digite a rua"); tfRua.setText(imovelProcurado.getEndereco().getRua());
				tfNumero      = new TextField(); tfNumero.setPromptText("Número"); tfNumero.setText(String.valueOf(imovelProcurado.getEndereco().getNumero()));
				tfBairro      = new TextField(); tfBairro.setPromptText("Bairro"); tfBairro.setText(imovelProcurado.getEndereco().getBairro());
				tfCidade      = new TextField(); tfCidade.setPromptText("Cidade"); tfCidade.setText(imovelProcurado.getEndereco().getCidade());
				tfUF          = new TextField(); tfUF.setPromptText("UF"); tfUF.setText(imovelProcurado.getEndereco().getUf());
				tfComplemento = new TextField(); tfComplemento.setPromptText("Complemento (opcional)"); tfComplemento.setText(imovelProcurado.getEndereco().getComplemento());

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
				vboxEndereco.setAlignment(Pos.CENTER);

				// --- Seção Imóvel ---
				Label lblDadosImovel = new Label("Dados do Imóvel:");
				lblDadosImovel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

				tfTipoPropriedade = new TextField(); tfTipoPropriedade.setPromptText("Apartamento, Casa..."); tfTipoPropriedade.setText(imovelProcurado.getTipoPropriedade());
				tfArea            = new TextField(); tfArea.setPromptText("Área em m²"); tfArea.setText(String.valueOf(imovelProcurado.getArea()));
				tfValor           = new TextField(); tfValor.setPromptText("Valor em R$"); tfValor.setText(String.valueOf(imovelProcurado.getValor()));
				tfComodos         = new TextField(); tfComodos.setPromptText("Quantidade de cômodos"); tfComodos.setText(String.valueOf(imovelProcurado.getComodos()));

				GridPane gridImovel = new GridPane();
				gridImovel.setHgap(10);
				gridImovel.setVgap(12);
				gridImovel.setAlignment(Pos.CENTER_LEFT);
				gridImovel.add(new Label("Tipo:"),     0, 0); gridImovel.add(tfTipoPropriedade, 1, 0); 
				gridImovel.add(new Label("Área:"),     0, 1); gridImovel.add(tfArea,            1, 1);
				gridImovel.add(new Label("Valor:"),    0, 2); gridImovel.add(tfValor,           1, 2);
				gridImovel.add(new Label("Cômodos:"),  0, 3); gridImovel.add(tfComodos,         1, 3);

				VBox vboxImovel = new VBox(10, lblDadosImovel, gridImovel);
				vboxImovel.setAlignment(Pos.CENTER);

				// --- Botão e erro ---
				Button btnSalvar = new Button("Salvar");
				btnSalvar.setPrefWidth(150);
				btnSalvar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				btnSalvar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
				btnSalvar.setOnAction(er -> {
					try {
						handleAtualizar();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});

				Button btnLimpar = new Button("Limpar");
				btnLimpar.setPrefWidth(150);
				btnLimpar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				btnLimpar.setStyle("-fx-background-color: #e3f542; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
				btnLimpar.setOnAction(er -> {
					limparCampos();
				});

				lblErro = new Label();
				lblErro.setTextFill(Color.RED);
				lblErro.setFont(Font.font(12));
				root.getChildren().clear();
				root.getChildren().addAll(lblTitulo, vboxIdImovel, btnVerificarId, vboxEndereco, vboxImovel, btnSalvar, btnLimpar, btnVoltar, lblErro);
			}
		});

		lblErro = new Label();
		lblErro.setTextFill(Color.RED);
		lblErro.setFont(Font.font(12));
		root.getChildren().addAll(lblTitulo, vboxIdImovel, btnVerificarId, btnVoltar, lblErro);
		return new Scene(root, 800, 600);
	}

	private boolean validarCampos() {
		boolean numeroOk, areaOk, valorOk, comodosOk;

		try { Integer.parseInt(tfNumero.getText().trim()); numeroOk = true; }
		catch (NumberFormatException e) { numeroOk = false; }

		try { Integer.parseInt(tfArea.getText().trim());   areaOk   = true; }
		catch (NumberFormatException e) { areaOk   = false; }

		try { Integer.parseInt(tfValor.getText().trim());  valorOk  = true; }
		catch (NumberFormatException e) { valorOk  = false; }

		try { Integer.parseInt(tfComodos.getText().trim()); comodosOk = true; }
		catch (NumberFormatException e) { comodosOk = false; }

		return !tfRua.getText().trim().isEmpty() &&
				!tfBairro.getText().trim().isEmpty() &&
				!tfCidade.getText().trim().isEmpty() &&
				!tfTipoPropriedade.getText().trim().isEmpty() &&
				tfUF.getText().trim().length() == 2 &&
				tfCep.getText().trim().length() == 8 &&
				numeroOk && areaOk && valorOk && comodosOk;
	}

	private void handleAtualizar() throws SQLException {
		lblErro.setText("");
		if (validarCampos()) {
			int numero = Integer.parseInt(tfNumero.getText().trim());
			int area   = Integer.parseInt(tfArea.getText().trim());
			int valor  = Integer.parseInt(tfValor.getText().trim());
			int comodos = Integer.parseInt(tfComodos.getText().trim());

			Endereco endereco = new Endereco(
					tfRua.getText().trim(), tfBairro.getText().trim(),
					tfCidade.getText().trim(), tfUF.getText().trim(),
					tfCep.getText().trim(), numero, tfComplemento.getText().trim()
					);
			enderecoCtrl = new EnderecoController(endereco);
			enderecoCtrl.atualizarEndereco();

			Imovel imovel = new Imovel(endereco, tfTipoPropriedade.getText().trim(), area, valor, comodos);
			imovelCtrl = new ImovelController(imovel);
			imovelCtrl.atualizarImovel();

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
		tfTipoPropriedade.setText("");
		tfArea.setText("");
		tfValor.setText("");
		tfComodos.setText("");
	}
	
	private boolean verificarId() {
		boolean idOk;
		try {
			int idImovel = Integer.parseInt(tfIdImovel.getText().trim());
			Imovel imovel = new Imovel();
			imovel.setIdImovel(idImovel);
			imovelCtrl = new ImovelController(imovel);
			imovelProcurado = imovelCtrl.procurarImovel();
			idOk = true;

		} catch (NumberFormatException e) { idOk = false; }
		return idOk;
	}
}