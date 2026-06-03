package view;

import java.util.List;

import controller.ClienteController;
import controller.EnderecoController;
import controller.PessoaFisicaController;
import controller.PessoaJuridicaController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.Hyperlink;
import model.PessoaFisica;
import model.PessoaJuridica;
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
        tabelaClientes.setPrefHeight(450);
        tabelaClientes.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #E2E8F0;");

        // --- 1. COLUNA NOME / RAZÃO SOCIAL (PURAMENTE VISUAL) ---
        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome / Razão Social");
        colNome.setPrefWidth(220);
        colNome.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            // Lógica dinâmica baseada no tipo real do objeto
            if (c instanceof PessoaFisica) {
                return new SimpleStringProperty(((PessoaFisica) c).getNome());
            } else if (c instanceof PessoaJuridica) {
                return new SimpleStringProperty(((PessoaJuridica) c).getRazaoSocial());
            }
            return new SimpleStringProperty(""); // Fallback caso seja um cliente genérico
        });

        // --- COLUNAS QUE VOCÊ JÁ TINHA ---
        TableColumn<Cliente, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colTipo.setPrefWidth(80);

        TableColumn<Cliente, Integer> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTelefone.setPrefWidth(120);

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(180);

        // --- 2. COLUNA DE AÇÕES (EDITAR E EXCLUIR COMO LINKS) ---
        TableColumn<Cliente, Void> colAcoes = new TableColumn<>("Ações");
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
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });

        // Adiciona todas as colunas na tabela respeitando a nova ordem
        tabelaClientes.getColumns().addAll(colNome, colTipo, colTelefone, colEmail, colAcoes);
        
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
            List<Cliente> listaDoBanco = c.listarClientes(); 
            
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
    
 // =========================================================
    // MÉTODOS DE AÇÃO DOS BOTÕES (LINKS) DA TABELA
    // =========================================================

    private void handleEditar(Cliente cliente) {
        Cliente clienteCompleto = null;

        // 1. Usa os controllers específicos que você já tem na estrutura para carregar tudo do banco
        if ("PF".equals(cliente.getTipoCliente())) {
            PessoaFisica pfPlaceholder = new PessoaFisica();
            pfPlaceholder.setIdCliente(cliente.getIdCliente());
            
            // Instancia o seu controller de PF
            PessoaFisicaController pfCtrl = new PessoaFisicaController(pfPlaceholder);
            clienteCompleto = pfCtrl.procurarPessoaFisica(); // Método que você já tem no controller!
            
        } else if ("PJ".equals(cliente.getTipoCliente())) {
            PessoaJuridica pjPlaceholder = new PessoaJuridica();
            pjPlaceholder.setIdCliente(cliente.getIdCliente());
            
            // Instancia o seu controller de PJ
            PessoaJuridicaController pjCtrl = new PessoaJuridicaController(pjPlaceholder);
            clienteCompleto = pjCtrl.procurarPessoaJuridica(); // Método que você já tem no controller!
        }

        // 2. Busca também o endereço completo usando o seu EnderecoController
        if (clienteCompleto != null && cliente.getEndereco() != null) {
            EnderecoController endCtrl = new EnderecoController(cliente.getEndereco());
            clienteCompleto.setEndereco(endCtrl.procurarEndereco()); // Método que você já tem no controller!
        }

        // 3. Abre a tela de atualização passando o objeto totalmente preenchido
        TelaAtualizarCliente telaAtualizar = new TelaAtualizarCliente();
        Region layoutAtualizacao = telaAtualizar.getLayout(clienteCompleto);
        
        // 4. Faz a troca de tela (Usando seu Trocador ou o Dashboard)
        TelaDashboard.mudarConteudo(layoutAtualizacao); 
        // ou: Trocador.trocarTela(layoutAtualizacao);
    }

    private void handleExcluir(Cliente cliente) {
        try {
            System.out.println("Tentando excluir o cliente ID: " + cliente.getIdCliente());
            
            // 1. Instancia o controller passando o cliente da linha selecionada
            ClienteController controller = new ClienteController(cliente);
            
            // 2. Chama a exclusão no banco de dados
            controller.deletarCliente();
            
            // 3. Recarrega a tabela visualmente para o cliente sumir da interface na mesma hora
            carregarDadosBanco();
            
            System.out.println("Cliente excluído com sucesso da tela e do banco!");

        } catch (Exception e) {
            System.out.println("Erro ao tentar excluir o cliente:");
            e.printStackTrace();
        }
    }
}