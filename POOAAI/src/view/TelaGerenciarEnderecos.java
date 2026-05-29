package view;

import controller.EnderecoController;
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
import model.Endereco;

public class TelaGerenciarEnderecos {

    private TableView<Endereco> tabelaEnderecos;

    public Region getLayout() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        // --- CABEÇALHO DA PÁGINA ---
        Label lblTitulo = new Label("Gerenciamento de Endereços");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Espaçador dinâmico para empurrar o botão para a extremidade direita
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botão para abrir o formulário de cadastro existente
        Button btnNovoEndereco = new Button("+ Novo Endereço");
        btnNovoEndereco.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnNovoEndereco.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        
        // Ação do botão: Chama a tela de cadastro que você já tem pronta!
        btnNovoEndereco.setOnAction(e -> {
            //TelaCadastroEndereco telaCadastro = new TelaCadastroEndereco();
            //TelaDashboard.mudarConteudo(telaCadastro.getLayout());
        });

        HBox header = new HBox(20, lblTitulo, spacer, btnNovoEndereco);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- TABELA DE DADOS (Envolvida em um Card Branco) ---
        VBox cardTabela = new VBox();
        cardTabela.setPadding(new Insets(20));
        cardTabela.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        tabelaEnderecos = new TableView<>();
        tabelaEnderecos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tabelaEnderecos.setPrefHeight(500);
        tabelaEnderecos.setStyle("-fx-background-radius: 6;");

        // Configuração das Colunas da Tabela
    	
        TableColumn<Endereco, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idEndereco"));
        colId.setPrefWidth(80);

        TableColumn<Endereco, String> colRua = new TableColumn<>("Rua");
        colRua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        colRua.setPrefWidth(100);

        TableColumn<Endereco, String> colBairro = new TableColumn<>("Bairro");
        colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colBairro.setPrefWidth(180);

        TableColumn<Endereco, String> colCidade = new TableColumn<>("Cidade");
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colCidade.setPrefWidth(300);
        
        TableColumn<Endereco, String> colUf = new TableColumn<>("UF");
        colUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
        colUf.setPrefWidth(80);
        
        TableColumn<Endereco, String> colCep = new TableColumn<>("CEP");
        colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        colCep.setPrefWidth(120);
        
        TableColumn<Endereco, String> colNumero = new TableColumn<>("Numero");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(80);
        
        TableColumn<Endereco, String> colComplemento = new TableColumn<>("Complemento");
        colComplemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));
        colComplemento.setPrefWidth(300);
        
        tabelaEnderecos.getColumns().add(colId);
        tabelaEnderecos.getColumns().add(colRua);
        tabelaEnderecos.getColumns().add(colBairro);
        tabelaEnderecos.getColumns().add(colCidade);
        tabelaEnderecos.getColumns().add(colUf);
        tabelaEnderecos.getColumns().add(colCep);
        tabelaEnderecos.getColumns().add(colNumero);
        tabelaEnderecos.getColumns().add(colComplemento);
        cardTabela.getChildren().add(tabelaEnderecos);

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
            EnderecoController e = new EnderecoController();
            
            // 1. Buscamos os dados no banco e GUARDAMOS na variável
            var listaDoBanco = e.listarEnderecos(); 
            
            // 2. Convertendo a lista comum do Java para a lista observável do JavaFX
            ObservableList<Endereco> dados = FXCollections.observableArrayList(listaDoBanco);
            
            // 3. Colocamos os dados REAIS na tabela (adeus, mockup!)
            tabelaEnderecos.setItems(dados);
            
        } catch (Exception e) {
            // Se der algum erro de SQL no banco, agora ele vai aparecer no console!
            System.out.println("Erro ao carregar dados na tabela:");
            e.printStackTrace();
        }
    }
}