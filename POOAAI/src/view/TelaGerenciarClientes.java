package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ClienteController;
import controller.PessoaFisicaController;
import controller.PessoaJuridicaController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import model.Cliente;
import model.Endereco;
import model.PessoaFisica;
import model.PessoaJuridica;

public class TelaGerenciarClientes {

	private TableView<Cliente> tabelaClientes;
	private ObservableList<Cliente> dadosMaster;
	private FilteredList<Cliente> dadosFiltrados;
	private final Map<String, String> filtrosAtivos = new HashMap<>();
	private final Map<String, Popup> popupsFiltro = new HashMap<>();

	public Region getLayout() {
		VBox root = new VBox(25);
		root.setAlignment(Pos.TOP_LEFT);
		root.setPadding(new Insets(40));

		// --- CABEÇALHO DA PÁGINA ---
		Label lblTitulo = new Label("Gerenciamento de Clientes");
		lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Button btnNovoCliente = new Button("+ Novo Cliente");
		btnNovoCliente.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		btnNovoCliente.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
		btnNovoCliente.setOnAction(e -> {
			TelaCadastroCliente telaCadastro = new TelaCadastroCliente();
			TelaDashboard.mudarConteudo(telaCadastro.getLayout());
		});

		HBox header = new HBox(20, lblTitulo, spacer, btnNovoCliente);
		header.setAlignment(Pos.CENTER_LEFT);

		// --- TABELA ---
		VBox cardTabela = new VBox();
		cardTabela.setPadding(new Insets(20));
		cardTabela.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

		tabelaClientes = new TableView<>();
		tabelaClientes.setPrefHeight(450);
		tabelaClientes.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #E2E8F0;");

		// --- COLUNA NOME / RAZÃO SOCIAL ---
		TableColumn<Cliente, String> colNome = new TableColumn<>("Nome / Razão Social");
		colNome.setPrefWidth(220);
		colNome.setCellValueFactory(cellData -> {
			Cliente c = cellData.getValue();
			if (c instanceof PessoaFisica pf) {
				return new SimpleStringProperty(pf.getNome());
			} else if (c instanceof PessoaJuridica pj) {
				return new SimpleStringProperty(pj.getRazaoSocial());
			}
			return new SimpleStringProperty("");
		});

		// --- COLUNA TIPO ---
		TableColumn<Cliente, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setPrefWidth(80);
		colTipo.setCellValueFactory(cellData ->
		new SimpleStringProperty(cellData.getValue().getTipoCliente())
				);

		// --- COLUNA TELEFONE ---
		TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
		colTelefone.setPrefWidth(140);
		colTelefone.setCellValueFactory(cellData ->
		new SimpleStringProperty(cellData.getValue().getTelefone())
				);

		// --- COLUNA EMAIL ---
		TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
		colEmail.setPrefWidth(200);
		colEmail.setCellValueFactory(cellData ->
		new SimpleStringProperty(cellData.getValue().getEmail())
				);

		// --- COLUNA ENDEREÇO COMPLETO ---
		TableColumn<Cliente, String> colEndereco = new TableColumn<>("Endereço");
		colEndereco.setPrefWidth(280);
		colEndereco.setCellValueFactory(cellData -> {
			Endereco e = cellData.getValue().getEndereco();
			if (e == null) return new SimpleStringProperty("N/A");
			String completo = e.getRua() + ", " + e.getNumero() + " - " + e.getBairro() + ", " + e.getCidade() + " / " + e.getUf();
			return new SimpleStringProperty(completo);
		});

		// --- COLUNA AÇÕES ---
		TableColumn<Cliente, Void> colAcoes = new TableColumn<>("Ações");
		colAcoes.setPrefWidth(150);
		colAcoes.setCellFactory(param -> new TableCell<>() {
			private final Hyperlink linkEditar = new Hyperlink("Editar");
			private final Hyperlink linkExcluir = new Hyperlink("Excluir");
			private final HBox container = new HBox(12, linkEditar, linkExcluir);

			{
				linkEditar.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold; -fx-underline: true;");
				linkExcluir.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-underline: true;");
				container.setAlignment(Pos.CENTER);

				linkEditar.setOnAction(event -> {
					Cliente clienteSelecionado = getTableView().getItems().get(getIndex());
					handleEditar(clienteSelecionado);
				});
				linkExcluir.setOnAction(event -> {
					Cliente clienteSelecionado = getTableView().getItems().get(getIndex());
					handleExcluir(clienteSelecionado);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : container);
			}
		});

		// --- ADICIONA COLUNAS ---
		tabelaClientes.getColumns().add(colNome);
		tabelaClientes.getColumns().add(colTipo);
		tabelaClientes.getColumns().add(colTelefone);
		tabelaClientes.getColumns().add(colEmail);
		tabelaClientes.getColumns().add(colEndereco);
		tabelaClientes.getColumns().add(colAcoes);

		// --- APLICA FILTROS NAS COLUNAS ---
		adicionarFiltroColuna(colNome,     "nome");
		adicionarFiltroColuna(colTipo,     "tipo");
		adicionarFiltroColuna(colTelefone, "telefone");
		adicionarFiltroColuna(colEmail,    "email");
		adicionarFiltroColuna(colEndereco, "endereco");

		cardTabela.getChildren().add(tabelaClientes);
		root.getChildren().addAll(header, cardTabela);

		carregarDadosBanco();

		ScrollPane scroll = new ScrollPane(root);
		scroll.setFitToWidth(true);
		scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");

		return scroll;
	}

	// -----------------------------------------------------------------------
	// Filtro por coluna
	// -----------------------------------------------------------------------

	private <T> void adicionarFiltroColuna(TableColumn<Cliente, T> coluna, String chave) {

		String labelTexto = coluna.getText();

		Label lblNome = new Label(labelTexto);
		lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

		Label indicador = new Label("●");
		indicador.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 9px;");
		indicador.setVisible(false);

		Button btnFiltro = new Button("▼");
		btnFiltro.setStyle("-fx-background-color: transparent; -fx-padding: 0 3; -fx-cursor: hand; -fx-font-size: 9px; -fx-text-fill: #7f8c8d;");

		HBox cabecalho = new HBox(5, lblNome, indicador, btnFiltro);
		cabecalho.setAlignment(Pos.CENTER_LEFT);

		coluna.setText("");
		coluna.setGraphic(cabecalho);

		btnFiltro.setOnAction(e -> {
			Popup popupAberto = popupsFiltro.get(chave);
			if (popupAberto != null && popupAberto.isShowing()) {
				popupAberto.hide();
				return;
			}

			TextField campo = new TextField(filtrosAtivos.getOrDefault(chave, ""));
			campo.setPromptText("Filtrar por " + labelTexto + "...");
			campo.setPrefWidth(200);
			campo.setStyle("-fx-font-size: 13px;");

			Button btnLimpar = new Button("✕  Limpar filtro");
			btnLimpar.setStyle("-fx-background-color: transparent; -fx-text-fill: #c0392b; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 4 0 0 0;");

			VBox conteudoPopup = new VBox(6, campo, btnLimpar);
			conteudoPopup.setPadding(new Insets(10));
			conteudoPopup.setStyle(
					"-fx-background-color: white;" +
							"-fx-border-color: #CBD5E0;" +
							"-fx-border-radius: 6;" +
							"-fx-background-radius: 6;"
					);

			Popup popup = new Popup();
			popup.setAutoHide(true);
			popup.getContent().add(conteudoPopup);
			popup.setOnHidden(ev -> popupsFiltro.remove(chave));

			Runnable fecharPopup = () -> {
				popup.hide();
				btnFiltro.requestFocus();
			};

			// Listener: filtra enquanto digita
			campo.textProperty().addListener((obs, antigo, novo) -> {
				if (novo == null || novo.isBlank()) {
					filtrosAtivos.remove(chave);
					indicador.setVisible(false);
					btnFiltro.setStyle("-fx-background-color: transparent; -fx-padding: 0 3; -fx-cursor: hand; -fx-font-size: 9px; -fx-text-fill: #7f8c8d;");
				} else {
					filtrosAtivos.put(chave, novo.toLowerCase());
					indicador.setVisible(true);
					btnFiltro.setStyle("-fx-background-color: transparent; -fx-padding: 0 3; -fx-cursor: hand; -fx-font-size: 9px; -fx-text-fill: #2980b9;");
				}
				reaplicarFiltros();
			});

			campo.setOnAction(ev -> fecharPopup.run());

			campo.addEventFilter(KeyEvent.KEY_PRESSED, ev -> {
				if (ev.getCode() == KeyCode.ENTER || ev.getCode() == KeyCode.ESCAPE) {
					ev.consume();
					if (ev.getCode() == KeyCode.ENTER) {
						fecharPopup.run();
					} else {
						popup.hide();
						btnFiltro.requestFocus();
					}
				}
			});

			btnLimpar.setOnAction(ev -> {
				campo.clear();
				filtrosAtivos.remove(chave);
				indicador.setVisible(false);
				btnFiltro.setStyle("-fx-background-color: transparent; -fx-padding: 0 3; -fx-cursor: hand; -fx-font-size: 9px; -fx-text-fill: #7f8c8d;");
				reaplicarFiltros();
				popup.hide();
			});

			popupsFiltro.put(chave, popup);

			Bounds bounds = btnFiltro.localToScreen(btnFiltro.getBoundsInLocal());
			popup.show(btnFiltro, bounds.getMinX(), bounds.getMaxY() + 4);
			campo.requestFocus();
			campo.positionCaret(campo.getText().length());
		});
	}

	private void reaplicarFiltros() {
		if (dadosFiltrados == null) return;

		dadosFiltrados.setPredicate(cliente -> {
			for (Map.Entry<String, String> entry : filtrosAtivos.entrySet()) {
				String chave = entry.getKey();
				String texto = entry.getValue();

				boolean passou = switch (chave) {
				case "nome" -> {
					if (cliente instanceof PessoaFisica pf)
						yield pf.getNome() != null && pf.getNome().toLowerCase().contains(texto);
					else if (cliente instanceof PessoaJuridica pj)
						yield pj.getRazaoSocial() != null && pj.getRazaoSocial().toLowerCase().contains(texto);
					yield false;
				}
				case "tipo"     -> cliente.getTipoCliente() != null && cliente.getTipoCliente().toLowerCase().contains(texto);
				case "telefone" -> cliente.getTelefone() != null && cliente.getTelefone().toLowerCase().contains(texto);
				case "email"    -> cliente.getEmail() != null && cliente.getEmail().toLowerCase().contains(texto);
				case "endereco" -> {
					Endereco end = cliente.getEndereco();
					if (end == null) yield false;
					String completo = (end.getRua() + " " + end.getNumero() + " " +
							end.getBairro() + " " + end.getCidade() + " " + end.getUf()).toLowerCase();
					yield completo.contains(texto);
				}
				default -> true;
				};

				if (!passou) return false; // AND: todos os filtros têm que passar
			}
			return true;
		});
	}

	// -----------------------------------------------------------------------
	// Carregamento de dados
	// -----------------------------------------------------------------------

	private void carregarDadosBanco() {
		try {
			ClienteController c = new ClienteController();
			List<Cliente> listaDoBanco = c.listarClientes();

			dadosMaster = FXCollections.observableArrayList(listaDoBanco);
			dadosFiltrados = new FilteredList<>(dadosMaster, p -> true);
			tabelaClientes.setItems(dadosFiltrados);

		} catch (Exception e) {
			System.out.println("Erro ao carregar dados na tabela:");
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------
	// Ações de editar / excluir
	// -----------------------------------------------------------------------

	private void handleEditar(Cliente clienteSelecionado) {
		try {
			ClienteController clienteCtrl = new ClienteController(clienteSelecionado);
			Cliente clienteCompleto = clienteCtrl.procurarCliente();

			if ("PF".equals(clienteCompleto.getTipoCliente())) {
				PessoaFisica pf = new PessoaFisica();
				pf.setIdCliente(clienteCompleto.getIdCliente());

				PessoaFisicaController pfCtrl = new PessoaFisicaController(pf);
				PessoaFisica pfDoBanco = pfCtrl.procurarPessoaFisica();

				pfDoBanco.setEndereco(clienteCompleto.getEndereco());
				pfDoBanco.setTelefone(clienteCompleto.getTelefone());
				pfDoBanco.setEmail(clienteCompleto.getEmail());
				pfDoBanco.setTipoCliente("PF");

				clienteCompleto = pfDoBanco;
			} else {
				PessoaJuridica pj = new PessoaJuridica();
				pj.setIdCliente(clienteCompleto.getIdCliente());

				PessoaJuridicaController pjCtrl = new PessoaJuridicaController(pj);
				PessoaJuridica pjDoBanco = pjCtrl.procurarPessoaJuridica();

				pjDoBanco.setEndereco(clienteCompleto.getEndereco());
				pjDoBanco.setTelefone(clienteCompleto.getTelefone());
				pjDoBanco.setEmail(clienteCompleto.getEmail());
				pjDoBanco.setTipoCliente("PJ");

				clienteCompleto = pjDoBanco;
			}

			TelaAtualizarCliente telaAtualizar = new TelaAtualizarCliente();
			TelaDashboard.mudarConteudo(telaAtualizar.getLayout(clienteCompleto));

		} catch (Exception e) {
			System.out.println("Erro ao carregar dados completos do cliente para edição:");
			e.printStackTrace();
		}
	}

	private void handleExcluir(Cliente clienteSelecionado) {
		String descricao;
		if (clienteSelecionado instanceof PessoaFisica pf) {
			descricao = pf.getNome();
		} else if (clienteSelecionado instanceof PessoaJuridica pj) {
			descricao = pj.getRazaoSocial();
		} else {
			descricao = "ID " + clienteSelecionado.getIdCliente();
		}

		boolean confirmado = ModalConfirmacao.confirmar(
				"Confirmar Exclusão",
				"Deseja excluir permanentemente o cliente?\n\n" + descricao
				);
		if (!confirmado) return;

		try {
			ClienteController ctrl = new ClienteController(clienteSelecionado);
			ctrl.deletarCliente();
			carregarDadosBanco();
		} catch (Exception e) {
			System.out.println("Erro ao excluir cliente:");
			e.printStackTrace();
		}
	}
}