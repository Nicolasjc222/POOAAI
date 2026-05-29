package view;

import controller.ImovelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Imovel;

public class TelaGerenciarImoveis {

    private TableView<Imovel> tabelaImoveis;

    public Region getLayout() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        // --- CABEÇALHO DA PÁGINA ---
        Label lblTitulo = new Label("Gerenciamento de Imóveis");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botão para abrir o formulário de cadastro existente
        Button btnNovoImovel = new Button("+ Novo Imóvel");
        btnNovoImovel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnNovoImovel.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        
        // Ação do botão: Chama a tela de cadastro que você já tem pronta!
        btnNovoImovel.setOnAction(e -> {
            TelaCadastroImovel telaCadastro = new TelaCadastroImovel();
            TelaDashboard.mudarConteudo(telaCadastro.getLayout());
        });

        HBox header = new HBox(20, lblTitulo, spacer, btnNovoImovel);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- TABELA DE DADOS (Envolvida em um Card Branco) ---
        VBox cardTabela = new VBox();
        cardTabela.setPadding(new Insets(20));
        cardTabela.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        tabelaImoveis = new TableView<>();
        tabelaImoveis.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tabelaImoveis.setPrefHeight(500);
        tabelaImoveis.setStyle("-fx-background-radius: 6;");

        // Configuração das Colunas da Tabela baseadas no modelo Imovel
        TableColumn<Imovel, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPropriedade"));
        colTipo.setPrefWidth(150);

        TableColumn<Imovel, Integer> colArea = new TableColumn<>("Área (m²)");
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colArea.setPrefWidth(120);

        TableColumn<Imovel, Integer> colValor = new TableColumn<>("Valor (R$)");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setPrefWidth(150);

        TableColumn<Imovel, Integer> colComodos = new TableColumn<>("Cômodos");
        colComodos.setCellValueFactory(new PropertyValueFactory<>("comodos"));
        colComodos.setPrefWidth(100);

        tabelaImoveis.getColumns().add(colTipo);
        tabelaImoveis.getColumns().add(colArea);
        tabelaImoveis.getColumns().add(colValor);
        tabelaImoveis.getColumns().add(colComodos);
        cardTabela.getChildren().add(tabelaImoveis);

        root.getChildren().addAll(header, cardTabela);

        // Carrega os dados iniciais do banco
        carregarDadosBanco();

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");

        return scroll;
    }

    private void carregarDadosBanco() {
        try {
            ImovelController c = new ImovelController();
            
            // 1. Buscamos a lista de imóveis do banco e GUARDAMOS na variável
            // (Assumindo que o método listarImoveis() retorna um List<Imovel>)
            var listaDoBanco = c.listarImoveis(); 
            
            // 2. Convertemos a lista padrão do Java para a lista observável do JavaFX
            ObservableList<Imovel> dados = FXCollections.observableArrayList(listaDoBanco);
            
            // 3. Associamos os dados reais do banco à sua tabela de imóveis
            tabelaImoveis.setItems(dados);
            
        } catch (Exception e) {
            // Se houver qualquer falha de SQL ou conexão, o console agora vai detalhar para você
            System.out.println("Erro ao carregar dados na tabela de imóveis:");
            e.printStackTrace();
        }
    }
}