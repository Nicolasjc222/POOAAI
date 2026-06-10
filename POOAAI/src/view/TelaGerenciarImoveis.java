package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.EnderecoController;
import controller.ImovelController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import model.Endereco;
import model.Imovel;

public class TelaGerenciarImoveis {

    private TableView<Imovel> tabelaImoveis;
    private ObservableList<Imovel> dadosMaster;
    private FilteredList<Imovel> dadosFiltrados;
    private final Map<String, String> filtrosAtivos = new HashMap<>();

    public Region getLayout() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        // --- CABEÇALHO ---
        Label lblTitulo = new Label("Gerenciamento de Imóveis");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNovoImovel = new Button("+ Novo Imóvel");
        btnNovoImovel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnNovoImovel.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnNovoImovel.setOnAction(e -> {
            TelaCadastroImovel telaCadastro = new TelaCadastroImovel();
            TelaDashboard.mudarConteudo(telaCadastro.getLayout());
        });

        HBox header = new HBox(20, lblTitulo, spacer, btnNovoImovel);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- TABELA ---
        VBox cardTabela = new VBox();
        cardTabela.setPadding(new Insets(20));
        cardTabela.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        tabelaImoveis = new TableView<>();
        tabelaImoveis.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tabelaImoveis.setPrefHeight(500);
        tabelaImoveis.setStyle("-fx-background-radius: 6;");

        // --- COLUNAS ---
        TableColumn<Imovel, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setPrefWidth(150);
        colTipo.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getTipoPropriedade())
        );

        TableColumn<Imovel, String> colArea = new TableColumn<>("Área (m²)");
        colArea.setPrefWidth(100);
        colArea.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getArea()))
        );

        TableColumn<Imovel, String> colValor = new TableColumn<>("Valor (R$)");
        colValor.setPrefWidth(120);
        colValor.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getValor()))
        );

        TableColumn<Imovel, String> colComodos = new TableColumn<>("Cômodos");
        colComodos.setPrefWidth(90);
        colComodos.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getComodos()))
        );

        TableColumn<Imovel, String> colFinalidade = new TableColumn<>("Finalidade");
        colFinalidade.setPrefWidth(120);
        colFinalidade.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getFinalidade())
        );

        TableColumn<Imovel, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setPrefWidth(260);
        colEndereco.setCellValueFactory(cellData -> {
            Endereco e = cellData.getValue().getEndereco();
            if (e == null) return new SimpleStringProperty("N/A");
            String completo = e.getRua() + ", " + e.getNumero() + " - " + e.getBairro() + ", " + e.getCidade() + " / " + e.getUf();
            return new SimpleStringProperty(completo);
        });

        // --- COLUNA AÇÕES ---
        TableColumn<Imovel, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(120);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Hyperlink linkEditar  = new Hyperlink("Editar");
            private final Hyperlink linkExcluir = new Hyperlink("Excluir");
            private final HBox pane = new HBox(10, linkEditar, linkExcluir);

            {
                linkEditar.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold; -fx-underline: true;");
                linkExcluir.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-underline: true;");
                pane.setAlignment(Pos.CENTER_LEFT);

                linkEditar.setOnAction(event -> {
                    Imovel imovel = getTableView().getItems().get(getIndex());
                    handleEditar(imovel);
                });
                linkExcluir.setOnAction(event -> {
                    Imovel imovel = getTableView().getItems().get(getIndex());
                    handleExcluir(imovel);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        tabelaImoveis.getColumns().add(colTipo);
        tabelaImoveis.getColumns().add(colArea);
        tabelaImoveis.getColumns().add(colValor);
        tabelaImoveis.getColumns().add(colComodos);
        tabelaImoveis.getColumns().add(colFinalidade);
        tabelaImoveis.getColumns().add(colEndereco);
        tabelaImoveis.getColumns().add(colAcoes);

        // --- FILTROS ---
        adicionarFiltroColuna(colTipo,       "tipo");
        adicionarFiltroColuna(colArea,       "area");
        adicionarFiltroColuna(colValor,      "valor");
        adicionarFiltroColuna(colComodos,    "comodos");
        adicionarFiltroColuna(colFinalidade, "finalidade");
        adicionarFiltroColuna(colEndereco,   "endereco");

        cardTabela.getChildren().add(tabelaImoveis);
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

    private <T> void adicionarFiltroColuna(TableColumn<Imovel, T> coluna, String chave) {
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

            btnLimpar.setOnAction(ev -> {
                filtrosAtivos.remove(chave);
                indicador.setVisible(false);
                btnFiltro.setStyle("-fx-background-color: transparent; -fx-padding: 0 3; -fx-cursor: hand; -fx-font-size: 9px; -fx-text-fill: #7f8c8d;");
                reaplicarFiltros();
                popup.hide();
            });

            Bounds bounds = btnFiltro.localToScreen(btnFiltro.getBoundsInLocal());
            popup.show(btnFiltro, bounds.getMinX(), bounds.getMaxY() + 4);
            campo.requestFocus();
        });
    }

    private void reaplicarFiltros() {
        if (dadosFiltrados == null) return;

        dadosFiltrados.setPredicate(imovel -> {
            for (Map.Entry<String, String> entry : filtrosAtivos.entrySet()) {
                String chave = entry.getKey();
                String texto = entry.getValue();

                boolean passou = switch (chave) {
                    case "tipo"       -> imovel.getTipoPropriedade() != null && imovel.getTipoPropriedade().toLowerCase().contains(texto);
                    case "area"       -> String.valueOf(imovel.getArea()).contains(texto);
                    case "valor"      -> String.valueOf(imovel.getValor()).contains(texto);
                    case "comodos"    -> String.valueOf(imovel.getComodos()).contains(texto);
                    case "finalidade" -> imovel.getFinalidade() != null && imovel.getFinalidade().toLowerCase().contains(texto);
                    case "endereco"   -> {
                        Endereco end = imovel.getEndereco();
                        if (end == null) yield false;
                        String completo = (end.getRua() + " " + end.getNumero() + " " +
                                           end.getBairro() + " " + end.getCidade() + " " + end.getUf()).toLowerCase();
                        yield completo.contains(texto);
                    }
                    default -> true;
                };

                if (!passou) return false;
            }
            return true;
        });
    }

    // -----------------------------------------------------------------------
    // Carregamento de dados
    // -----------------------------------------------------------------------

    private void carregarDadosBanco() {
        try {
            ImovelController c = new ImovelController();
            List<Imovel> listaDoBanco = c.listarImoveis();

            dadosMaster = FXCollections.observableArrayList(listaDoBanco);
            dadosFiltrados = new FilteredList<>(dadosMaster, p -> true);
            tabelaImoveis.setItems(dadosFiltrados);

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados na tabela de imóveis:");
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------
    // Ações de editar / excluir
    // -----------------------------------------------------------------------

    private void handleEditar(Imovel imovelSelecionado) {
        try {
            ImovelController imovelCtrl = new ImovelController(imovelSelecionado);
            Imovel imovelCompleto = imovelCtrl.procurarImovel();

            if (imovelCompleto != null && imovelCompleto.getEndereco() != null) {
                EnderecoController endCtrl = new EnderecoController(imovelCompleto.getEndereco());
                imovelCompleto.setEndereco(endCtrl.procurarEndereco());
            }

            if (imovelCompleto != null) {
                TelaAtualizarImovel telaAtualizar = new TelaAtualizarImovel();
                TelaDashboard.mudarConteudo(telaAtualizar.getLayout(imovelCompleto));
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar os dados completos do Imóvel para edição:");
            e.printStackTrace();
        }
    }

    private void handleExcluir(Imovel imovelSelecionado) {
        Endereco end = imovelSelecionado.getEndereco();
        String descricao = imovelSelecionado.getTipoPropriedade() + " — "
                         + (end != null ? end.getRua() + ", " + end.getNumero() : "sem endereço");

        boolean confirmado = ModalConfirmacao.confirmar(
            "Confirmar Exclusão",
            "Deseja excluir permanentemente o imóvel?\n\n" + descricao
        );
        if (!confirmado) return;

        try {
            ImovelController imovelCtrl = new ImovelController(imovelSelecionado);
            imovelCtrl.deletarImovel();
            carregarDadosBanco();
            System.out.println("Imóvel ID " + imovelSelecionado.getIdImovel() + " excluído com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir Imóvel:");
            e.printStackTrace();
        }
    }
}