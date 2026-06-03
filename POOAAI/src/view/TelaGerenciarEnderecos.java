package view;

import java.util.List;
import controller.EnderecoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
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
            TelaCadastroEndereco telaCadastro = new TelaCadastroEndereco();
            TelaDashboard.mudarConteudo(telaCadastro.getLayout());
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
        colUf.setPrefWidth(100);
        
        TableColumn<Endereco, String> colCep = new TableColumn<>("CEP");
        colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        colCep.setPrefWidth(120);
        
        TableColumn<Endereco, String> colNumero = new TableColumn<>("Numero");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(80);
        
        TableColumn<Endereco, String> colComplemento = new TableColumn<>("Complemento");
        colComplemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));
        colComplemento.setPrefWidth(200);
        
     // --- 2. COLUNA DE AÇÕES (EDITAR E EXCLUIR COMO LINKS) ---
        TableColumn<Endereco, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(param -> new TableCell<>() {
        private final Hyperlink linkEditar = new Hyperlink("Editar");
        private final Hyperlink linkExcluir = new Hyperlink("Excluir");
        private final HBox container = new HBox(12, linkEditar, linkExcluir);

        {
            // Estilização para parecerem links da Web modernos
            linkEditar.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold; -fx-underline: true;");
            linkExcluir.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-underline: true;");
            container.setAlignment(Pos.CENTER);

            linkEditar.setOnAction(event -> {
                Endereco enderecoSelecionado = getTableView().getItems().get(getIndex());
                handleEditar(enderecoSelecionado);
            });

            linkExcluir.setOnAction(event -> {
            	Endereco enderecoSelecionado = getTableView().getItems().get(getIndex());
                handleExcluir(enderecoSelecionado);
            });
            
        }
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(container);
            }
        }
        });
        
        tabelaEnderecos.getColumns().add(colRua);
        tabelaEnderecos.getColumns().add(colBairro);
        tabelaEnderecos.getColumns().add(colCidade);
        tabelaEnderecos.getColumns().add(colUf);
        tabelaEnderecos.getColumns().add(colCep);
        tabelaEnderecos.getColumns().add(colNumero);
        tabelaEnderecos.getColumns().add(colComplemento);
        tabelaEnderecos.getColumns().add(colAcoes);
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
            List<Endereco> listaDoBanco = e.listarEnderecos(); 
            
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
    private void handleEditar(Endereco endereco) {
        try {
            System.out.println("Buscando dados completos do Endereço ID: " + endereco.getIdEndereco());

            // 1. Instancia o controller passando o objeto da linha selecionada
            EnderecoController endCtrl = new EnderecoController(endereco);
            
            // 2. Busca o endereço completo atualizado diretamente do banco de dados
            Endereco enderecoCompleto = endCtrl.procurarEndereco(); 

            if (enderecoCompleto != null) {
                // 3. Abre a tela de atualização passando o objeto totalmente preenchido
                TelaAtualizaEndereco telaAtualizar = new TelaAtualizaEndereco();
                Region layoutAtualizacao = telaAtualizar.getLayout(enderecoCompleto);
                
                // 4. Faz a troca de tela usando o Dashboard da aplicação
                TelaDashboard.mudarConteudo(layoutAtualizacao);
                
                System.out.println("Tela de atualização de endereço carregada com sucesso.");
            } else {
                System.out.println("Erro: Não foi possível encontrar o endereço no banco de dados.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao tentar redirecionar para a edição do endereço:");
            e.printStackTrace();
        }
    }

    private void handleExcluir(Endereco endereco) {
        try {
            System.out.println("Tentando excluir o Endereco: " + endereco.getIdEndereco());
            
            // 1. Instancia o controller passando o cliente da linha selecionada
            EnderecoController controller = new EnderecoController(endereco);
            
            // 2. Chama a exclusão no banco de dados
            controller.deletarEndereco();
            
            // 3. Recarrega a tabela visualmente para o cliente sumir da interface na mesma hora
            carregarDadosBanco();
            
            System.out.println("Cliente excluído com sucesso da tela e do banco!");

        } catch (Exception e) {
            System.out.println("Erro ao tentar excluir o cliente:");
            e.printStackTrace();
        }
    }
    
}