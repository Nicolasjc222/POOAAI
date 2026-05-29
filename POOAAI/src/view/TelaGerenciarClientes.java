package view;

import controller.ClienteController;
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
import model.Cliente;

public class TelaGerenciarClientes {

    private TableView<Cliente> tabelaClientes;

    public Region getLayout() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        // --- CABEÇALHO DA PÁGINA ---
        Label lblTitulo = new Label("Gerenciamento de Clientes");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Espaçador dinâmico para empurrar o botão para a extremidade direita
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botão para abrir o formulário de cadastro existente
        Button btnNovoCliente = new Button("+ Novo Cliente");
        btnNovoCliente.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnNovoCliente.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        
        // Ação do botão: Chama a tela de cadastro que você já tem pronta!
        btnNovoCliente.setOnAction(e -> {
            TelaCadastroCliente telaCadastro = new TelaCadastroCliente();
            TelaDashboard.mudarConteudo(telaCadastro.getLayout());
        });

        HBox header = new HBox(20, lblTitulo, spacer, btnNovoCliente);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- TABELA DE DADOS (Envolvida em um Card Branco) ---
        VBox cardTabela = new VBox();
        cardTabela.setPadding(new Insets(20));
        cardTabela.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        tabelaClientes = new TableView<>();
        tabelaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tabelaClientes.setPrefHeight(500);
        tabelaClientes.setStyle("-fx-background-radius: 6;");

        // Configuração das Colunas da Tabela
        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colId.setPrefWidth(80);

        TableColumn<Cliente, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colTipo.setPrefWidth(100);

        TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTelefone.setPrefWidth(180);

        TableColumn<Cliente, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(300);

        tabelaClientes.getColumns().add(colId);
        tabelaClientes.getColumns().add(colTipo);
        tabelaClientes.getColumns().add(colTelefone);
        tabelaClientes.getColumns().add(colEmail);
        cardTabela.getChildren().add(tabelaClientes);

        root.getChildren().addAll(header, cardTabela);

        // Carrega os dados iniciais do banco
        carregarDadosBanco();

        // ScrollPane para garantir responsividade vertical se necessário
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");

        return scroll;
    }

    private void carregarDadosBanco() {
    	try {
            ClienteController c = new ClienteController();
            
            // 1. Buscamos os dados no banco e GUARDAMOS na variável
            // (Estou assumindo que listarClientes() retorna um List<Cliente>)
            var listaDoBanco = c.listarClientes(); 
            
            // 2. Convertendo a lista comum do Java para a lista observável do JavaFX
            ObservableList<Cliente> dados = FXCollections.observableArrayList(listaDoBanco);
            
            // 3. Colocamos os dados REAIS na tabela (adeus, mockup!)
            tabelaClientes.setItems(dados);
            
        } catch (Exception e) {
            // Se der algum erro de SQL no banco, agora ele vai aparecer no console!
            System.out.println("Erro ao carregar dados na tabela:");
            e.printStackTrace();
        }
    }
}