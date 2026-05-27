package view;

import java.sql.SQLException;

import controller.ClienteController;
import controller.EnderecoController;
import controller.PessoaFisicaController;
import controller.PessoaJuridicaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;
import model.Cliente;
import model.Endereco;
import model.PessoaFisica;
import model.PessoaJuridica;

public class TelaAtualizarCliente {

	private TextField tfIdCliente;
	private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
	private TextField tfTelefone, tfEmail;
	private TextField tfNome, tfCpf;
	private TextField tfRazaoSocial, tfCnpj;
	private ComboBox<String> cbTipo;
	private Label lblErro;
	private VBox vboxTipoExtra;
	private Cliente clienteProcurado;
	private PessoaFisica pessoaFisicaProcurada;
	private PessoaJuridica pessoaJuridicaProcurada;

	private ClienteController clienteCtrl;
	private EnderecoController enderecoCtrl;
	private PessoaFisicaController pfCtrl;
	private PessoaJuridicaController pjCtrl;

	public Scene getScene() {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

		Label lblTitulo = new Label("Atualizar cliente");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.DARKBLUE);
		
		Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(150);
        btnVoltar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnVoltar.setStyle("-fx-background-color: #0c27f0; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        btnVoltar.setOnAction(e -> {
        Trocador.voltarTela();
        });
        
		Label lblIdCliente = new Label("ID Cliente:");
		lblIdCliente.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		tfIdCliente = new TextField(); tfIdCliente.setPromptText("ID do cliente a ser editado");

		VBox vboxIdCliente = new VBox(10, lblIdCliente, tfIdCliente);
		vboxIdCliente.setAlignment(Pos.CENTER);

		Button btnVerificarId = new Button("Verificar");
		btnVerificarId.setPrefWidth(150);
		btnVerificarId.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnVerificarId.setStyle("-fx-background-color: #b515e6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnVerificarId.setOnAction(e -> {
			if(verificarId()) {
				// --- Seção Endereço ---
				Label lblEndereco = new Label("Endereço:");
				lblEndereco.setFont(Font.font("Arial", FontWeight.BOLD, 14));

				tfCep         = new TextField(); tfCep.setPromptText("00000-000"); tfCep.setText(clienteProcurado.getEndereco().getCep());
				tfRua         = new TextField(); tfRua.setPromptText("Digite a rua"); tfRua.setText(clienteProcurado.getEndereco().getRua());
				tfNumero      = new TextField(); tfNumero.setPromptText("Número"); tfNumero.setText(String.valueOf(clienteProcurado.getEndereco().getNumero()));
				tfBairro      = new TextField(); tfBairro.setPromptText("Bairro"); tfBairro.setText(clienteProcurado.getEndereco().getBairro());
				tfCidade      = new TextField(); tfCidade.setPromptText("Cidade"); tfCidade.setText(clienteProcurado.getEndereco().getCidade());
				tfUF          = new TextField(); tfUF.setPromptText("UF"); tfUF.setText(clienteProcurado.getEndereco().getUf());
				tfComplemento = new TextField(); tfComplemento.setPromptText("Complemento (opcional)"); tfComplemento.setText(clienteProcurado.getEndereco().getComplemento());

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

				// --- Seção Cliente ---
				Label lblDadosCliente = new Label("Dados do Cliente:");
				lblDadosCliente.setFont(Font.font("Arial", FontWeight.BOLD, 14));

				tfTelefone = new TextField(); tfTelefone.setPromptText("Digite o telefone"); tfTelefone.setText(String.valueOf(clienteProcurado.getTelefone()));
				tfEmail    = new TextField(); tfEmail.setPromptText("Digite o email"); tfEmail.setText(clienteProcurado.getEmail());

				cbTipo = new ComboBox<>();
				cbTipo.getItems().addAll("PF", "PJ");
				cbTipo.setPromptText("Selecione o tipo");
				cbTipo.setValue(clienteProcurado.getTipoCliente());
				cbTipo.setDisable(true);

				// painel dinâmico que muda conforme PF ou PJ
				vboxTipoExtra = new VBox(10);
				vboxTipoExtra.setAlignment(Pos.CENTER_LEFT);

				cbTipo.setOnAction(er -> {
					vboxTipoExtra.getChildren().clear();

					GridPane gridTipo = new GridPane();
					gridTipo.setHgap(10);
					gridTipo.setVgap(12);
					gridTipo.setAlignment(Pos.CENTER_LEFT);

					if (cbTipo.getValue().equals("PF")) {
						tfNome = new TextField(); tfNome.setPromptText("Nome completo"); tfNome.setText(pessoaFisicaProcurada.getNome());
						tfCpf  = new TextField(); tfCpf.setPromptText("000.000.000-00"); tfCpf.setText(pessoaFisicaProcurada.getCpf());
						gridTipo.add(new Label("Nome:"), 0, 0); gridTipo.add(tfNome, 1, 0);
						gridTipo.add(new Label("CPF:"),  0, 1); gridTipo.add(tfCpf,  1, 1);
					} else {
						tfRazaoSocial = new TextField(); tfRazaoSocial.setPromptText("Razão Social"); tfRazaoSocial.setText(pessoaJuridicaProcurada.getRazaoSocial());
						tfCnpj        = new TextField(); tfCnpj.setPromptText("00.000.000/0000-00"); tfCnpj.setText(pessoaJuridicaProcurada.getCnpj());
						gridTipo.add(new Label("Razão Social:"), 0, 0); gridTipo.add(tfRazaoSocial, 1, 0);
						gridTipo.add(new Label("CNPJ:"),         0, 1); gridTipo.add(tfCnpj,        1, 1);
					}

					vboxTipoExtra.getChildren().add(gridTipo);
				});

				GridPane gridCliente = new GridPane();
				gridCliente.setHgap(10);
				gridCliente.setVgap(12);
				gridCliente.setAlignment(Pos.CENTER_LEFT);
				gridCliente.add(new Label("Telefone:"),  0, 0); gridCliente.add(tfTelefone, 1, 0);
				gridCliente.add(new Label("Email:"),     0, 1); gridCliente.add(tfEmail,    1, 1);
				gridCliente.add(new Label("Tipo:"),      0, 2); gridCliente.add(cbTipo,     1, 2);

				VBox vboxCliente = new VBox(10, lblDadosCliente, gridCliente, vboxTipoExtra);
				vboxCliente.setAlignment(Pos.CENTER);

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
				root.getChildren().addAll(lblTitulo, vboxIdCliente, btnVerificarId, vboxEndereco, vboxCliente, btnSalvar, btnLimpar, btnVoltar, lblErro);
			}   
		});
        
		lblErro = new Label();
		lblErro.setTextFill(Color.RED);
		lblErro.setFont(Font.font(12));
		root.getChildren().addAll(lblTitulo, vboxIdCliente, btnVerificarId, btnVoltar, lblErro);
		return new Scene(root, 800, 600);
	}

	private boolean validarCampos() {
		boolean telefoneOk;
		boolean numeroOk;

		try { Integer.parseInt(tfTelefone.getText().trim()); telefoneOk = true; }
		catch (NumberFormatException e) { telefoneOk = false; }


		try { Integer.parseInt(tfNumero.getText().trim()); numeroOk = true; }
		catch (NumberFormatException e) { numeroOk = false; }

		if (cbTipo.getValue() == null) return false;

		boolean tipoExtraOk;
		if (cbTipo.getValue().equals("PF")) {
			tipoExtraOk = tfNome != null && !tfNome.getText().trim().isEmpty() &&
					tfCpf  != null && !tfCpf.getText().trim().isEmpty();
		} else {
			tipoExtraOk = tfRazaoSocial != null && !tfRazaoSocial.getText().trim().isEmpty() &&
					tfCnpj        != null && !tfCnpj.getText().trim().isEmpty();
		}

		return !tfRua.getText().trim().isEmpty() &&
				!tfBairro.getText().trim().isEmpty() &&
				!tfCidade.getText().trim().isEmpty() &&
				!tfEmail.getText().trim().isEmpty() &&
				tfUF.getText().trim().length() == 2 &&
				tfCep.getText().trim().length() == 8 &&
				telefoneOk &&
				numeroOk &&
				tipoExtraOk;
	}

	private void handleAtualizar() throws SQLException {
		lblErro.setText("");
		if (validarCampos()) {
			int numero   = Integer.parseInt(tfNumero.getText().trim());
			int telefone = Integer.parseInt(tfTelefone.getText().trim());

			// 1. salva endereço
			Endereco endereco = new Endereco(
					tfRua.getText().trim(), tfBairro.getText().trim(),
					tfCidade.getText().trim(), tfUF.getText().trim(),
					tfCep.getText().trim(), numero, tfComplemento.getText().trim()
					);
			enderecoCtrl = new EnderecoController(endereco);
			enderecoCtrl.atualizarEndereco();

			// 2. salva cliente
			Cliente cliente = new Cliente();
			cliente.setTelefone(telefone);
			cliente.setEmail(tfEmail.getText().trim());
			cliente.setTipoCliente(cbTipo.getValue());
			cliente.setEndereco(endereco);
			clienteCtrl = new ClienteController(cliente);
			clienteCtrl.atualizarCliente();

			// 3. salva PF ou PJ com o idCliente gerado
			if (cbTipo.getValue().equals("PF")) {
				PessoaFisica pf = new PessoaFisica();
				pf.setIdCliente(cliente.getIdCliente());
				pf.setNome(tfNome.getText().trim());
				pf.setCpf(tfCpf.getText().trim());
				pfCtrl = new PessoaFisicaController(pf);
				pfCtrl.atualizarPessoaFisica();
			} else {
				PessoaJuridica pj = new PessoaJuridica();
				pj.setIdCliente(cliente.getIdCliente());
				pj.setRazaoSocial(tfRazaoSocial.getText().trim());
				pj.setCnpj(tfCnpj.getText().trim());
				pjCtrl = new PessoaJuridicaController(pj);
				pjCtrl.atualizarPessoaJuridica();
			}

			lblErro.setTextFill(Color.GREEN);
			lblErro.setText("Cliente atualizado com sucesso!");
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
		tfTelefone.setText("");
		tfEmail.setText("");
		cbTipo.setValue("");
		vboxTipoExtra.getChildren().clear();
		if (tfNome != null)        tfNome.setText("");
		if (tfCpf != null)         tfCpf.setText("");
		if (tfRazaoSocial != null) tfRazaoSocial.setText("");
		if (tfCnpj != null)        tfCnpj.setText("");
	}

	private boolean verificarId() {
		boolean idOk;
		try {
			int idCliente = Integer.parseInt(tfIdCliente.getText().trim());
			Cliente cliente = new Cliente();
			cliente.setIdCliente(idCliente);
			clienteCtrl = new ClienteController(cliente);
			clienteProcurado = clienteCtrl.procurarCliente();
			if(clienteProcurado.getTipoCliente().equals("PF")) {
				pessoaFisicaProcurada = new PessoaFisica();
				pessoaFisicaProcurada.setIdCliente(idCliente);
				pfCtrl = new PessoaFisicaController(pessoaFisicaProcurada);
				pessoaFisicaProcurada = pfCtrl.procurarPessoaFisica();
			} else {
				pessoaJuridicaProcurada = new PessoaJuridica();
				pessoaJuridicaProcurada.setIdCliente(idCliente);
				pjCtrl = new PessoaJuridicaController(pessoaJuridicaProcurada);
				pessoaJuridicaProcurada = pjCtrl.procurarPessoaJuridica();
			}
			idOk = true;

		} catch (NumberFormatException e) { idOk = false; }
		return idOk;
	}
}