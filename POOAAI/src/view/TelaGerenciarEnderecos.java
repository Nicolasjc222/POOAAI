package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.EnderecoController;
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

public class TelaGerenciarEnderecos {

    private TableView<Endereco> tabelaEnderecos;
    private ObservableList<Endereco> dadosMaster;
    private FilteredList<Endereco> dadosFiltrados;
    private final Map<String, String> filtrosAtivos = new HashMap<>();

    public Region getLayout() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        // --- CABEÇALHO ---
        Label lblTitulo = new Label("Gerenciamento de Endereços");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNovoEndereco = new Button("+ Novo Endereço");
        btnNovoEndereco.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnNovoEndereco.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnNovoEndereco.setOnAction(e -> {
            TelaCadastroEndereco telaCadastro = new TelaCadastroEndereco();
            TelaDashboard.mudarConteudo(telaCadastro.getLayout());
        });

        HBox header = new HBox(20, lblTitulo, spacer, btnNovoEndereco);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- TABELA ---
        VBox cardTabela = new VBox();
        cardTabela.setPadding(new Insets(20));
        cardTabela.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        tabelaEnderecos = new TableView<>();
        tabelaEnderecos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tabelaEnderecos.setPrefHeight(500);
        tabelaEnderecos.setStyle("-fx-background-radius: 6;");

        // --- COLUNAS ---
        TableColumn<Endereco, String> colRua = new TableColumn<>("Rua");
        colRua.setPrefWidth(160);
        colRua.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getRua())
        );

        TableColumn<Endereco, String> colNumero = new TableColumn<>("Número");
        colNumero.setPrefWidth(70);
        colNumero.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero()))
        );

        TableColumn<Endereco, String> colComplemento = new TableColumn<>("Complemento");
        colComplemento.setPrefWidth(130);
        colComplemento.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getComplemento())
        );

        TableColumn<Endereco, String> colBairro = new TableColumn<>("Bairro");
        colBairro.setPrefWidth(130);
        colBairro.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getBairro())
        );

        TableColumn<Endereco, String> colCidade = new TableColumn<>("Cidade");
        colCidade.setPrefWidth(140);
        colCidade.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getCidade())
        );

        TableColumn<Endereco, String> colUf = new TableColumn<>("UF");
        colUf.setPrefWidth(60);
        colUf.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getUf())
        );

        TableColumn<Endereco, String> colCep = new TableColumn<>("CEP");
        colCep.setPrefWidth(100);
        colCep.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getCep())
        );

        // --- COLUNA AÇÕES ---
        TableColumn<Endereco, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(130);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Hyperlink linkEditar  = new Hyperlink("Editar");
            private final Hyperlink linkExcluir = new Hyperlink("Excluir");
            private final HBox container = new HBox(12, linkEditar, linkExcluir);

            {
                linkEditar.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold; -fx-underline: true;");
                linkExcluir.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-underline: true;");
                container.setAlignment(Pos.CENTER);

                linkEditar.setOnAction(event -> {
                    Endereco end = getTableView().getItems().get(getIndex());
                    handleEditar(end);
                });
                linkExcluir.setOnAction(event -> {
                    Endereco end = getTableView().getItems().get(getIndex());
                    handleExcluir(end);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });

        tabelaEnderecos.getColumns().add(colRua);
        tabelaEnderecos.getColumns().add(colNumero);
        tabelaEnderecos.getColumns().add(colComplemento);
        tabelaEnderecos.getColumns().add(colBairro);
        tabelaEnderecos.getColumns().add(colCidade);
        tabelaEnderecos.getColumns().add(colUf);
        tabelaEnderecos.getColumns().add(colCep);
        tabelaEnderecos.getColumns().add(colAcoes);

        // --- FILTROS ---
        adicionarFiltroColuna(colRua,         "rua");
        adicionarFiltroColuna(colNumero,      "numero");
        adicionarFiltroColuna(colComplemento, "complemento");
        adicionarFiltroColuna(colBairro,      "bairro");
        adicionarFiltroColuna(colCidade,      "cidade");
        adicionarFiltroColuna(colUf,          "uf");
        adicionarFiltroColuna(colCep,         "cep");

        cardTabela.getChildren().add(tabelaEnderecos);
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

    private <T> void adicionarFiltroColuna(TableColumn<Endereco, T> coluna, String chave) {
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

        dadosFiltrados.setPredicate(end -> {
            for (Map.Entry<String, String> entry : filtrosAtivos.entrySet()) {
                String chave = entry.getKey();
                String texto = entry.getValue();

                boolean passou = switch (chave) {
                    case "rua"         -> end.getRua() != null && end.getRua().toLowerCase().contains(texto);
                    case "numero"      -> String.valueOf(end.getNumero()).contains(texto);
                    case "complemento" -> end.getComplemento() != null && end.getComplemento().toLowerCase().contains(texto);
                    case "bairro"      -> end.getBairro() != null && end.getBairro().toLowerCase().contains(texto);
                    case "cidade"      -> end.getCidade() != null && end.getCidade().toLowerCase().contains(texto);
                    case "uf"          -> end.getUf() != null && end.getUf().toLowerCase().contains(texto);
                    case "cep"         -> end.getCep() != null && end.getCep().contains(texto);
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
            EnderecoController e = new EnderecoController();
            List<Endereco> listaDoBanco = e.listarEnderecos();

            dadosMaster = FXCollections.observableArrayList(listaDoBanco);
            dadosFiltrados = new FilteredList<>(dadosMaster, p -> true);
            tabelaEnderecos.setItems(dadosFiltrados);

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados na tabela:");
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------
    // Ações de editar / excluir
    // -----------------------------------------------------------------------

    private void handleEditar(Endereco endereco) {
        try {
            EnderecoController endCtrl = new EnderecoController(endereco);
            Endereco enderecoCompleto = endCtrl.procurarEndereco();

            if (enderecoCompleto != null) {
                TelaAtualizarEndereco telaAtualizar = new TelaAtualizarEndereco();
                TelaDashboard.mudarConteudo(telaAtualizar.getLayout(enderecoCompleto));
            } else {
                System.out.println("Erro: Endereço não encontrado no banco.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao redirecionar para edição do endereço:");
            e.printStackTrace();
        }
    }

    private void handleExcluir(Endereco endereco) {
        String descricao = endereco.getRua() + ", " + endereco.getNumero()
                         + " - " + endereco.getBairro() + ", " + endereco.getCidade();

        boolean confirmado = ModalConfirmacao.confirmar(
            "Confirmar Exclusão",
            "Deseja excluir permanentemente o endereço?\n\n" + descricao
        );
        if (!confirmado) return;

        try {
            EnderecoController controller = new EnderecoController(endereco);
            controller.deletarEndereco();
            carregarDadosBanco();
            System.out.println("Endereço ID " + endereco.getIdEndereco() + " excluído com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir endereço:");
            e.printStackTrace();
        }
    }
}