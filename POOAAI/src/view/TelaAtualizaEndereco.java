package view;

import java.sql.SQLException;
import controller.EnderecoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Endereco;

public class TelaAtualizaEndereco {

    private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
    private Label lblErro;
    
    private Endereco enderecoProcurado;
    private EnderecoController enderecoCtrl;

    public Region getLayout(Endereco enderecoSelecionado) {
        // 1. Carrega os dados vindo da tabela/banco
        carregarDadosCompletos(enderecoSelecionado);

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_LEFT); 
        root.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Atualizar Endereço");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Card branco para o formulário
        VBox cardForm = new VBox(20);
        cardForm.setPadding(new Insets(30));
        cardForm.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");    

        // --- Seção Endereço ---
        Label lblEndereco = new Label("Dados do Endereço:");
        lblEndereco.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        tfCep         = new TextField(); tfCep.setPromptText("00000-000");
        tfRua         = new TextField(); tfRua.setPromptText("Digite a rua");
        tfNumero      = new TextField(); tfNumero.setPromptText("Número");
        tfBairro      = new TextField(); tfBairro.setPromptText("Bairro");
        tfCidade      = new TextField(); tfCidade.setPromptText("Cidade");
        tfUF          = new TextField(); tfUF.setPromptText("UF");
        tfComplemento = new TextField(); tfComplemento.setPromptText("Complemento (opcional)");

        // Preenche campos de endereço de forma segura (Null-Safe)
        if (enderecoProcurado != null) {
            tfCep.setText(enderecoProcurado.getCep() != null ? enderecoProcurado.getCep() : "");
            tfRua.setText(enderecoProcurado.getRua() != null ? enderecoProcurado.getRua() : "");
            tfNumero.setText(String.valueOf(enderecoProcurado.getNumero()));
            tfBairro.setText(enderecoProcurado.getBairro() != null ? enderecoProcurado.getBairro() : "");
            tfCidade.setText(enderecoProcurado.getCidade() != null ? enderecoProcurado.getCidade() : "");
            tfUF.setText(enderecoProcurado.getUf() != null ? enderecoProcurado.getUf() : "");
            tfComplemento.setText(enderecoProcurado.getComplemento() != null ? enderecoProcurado.getComplemento() : "");
        }

        GridPane gridEndereco = new GridPane();
        gridEndereco.setHgap(10); gridEndereco.setVgap(12);
        gridEndereco.setAlignment(Pos.CENTER_LEFT);
        gridEndereco.add(new Label("CEP:"),         0, 0); gridEndereco.add(tfCep,         1, 0);
        gridEndereco.add(new Label("Rua:"),         2, 0); gridEndereco.add(tfRua,         3, 0);
        gridEndereco.add(new Label("Número:"),      0, 1); gridEndereco.add(tfNumero,      1, 1);
        gridEndereco.add(new Label("Bairro:"),      2, 1); gridEndereco.add(tfBairro,      3, 1);
        gridEndereco.add(new Label("Cidade:"),      0, 2); gridEndereco.add(tfCidade,      1, 2);
        gridEndereco.add(new Label("UF:"),          2, 2); gridEndereco.add(tfUF,          3, 2);
        gridEndereco.add(new Label("Complemento:"), 0, 3); gridEndereco.add(tfComplemento, 1, 3);

        VBox vboxEndereco = new VBox(10, lblEndereco, gridEndereco);

        lblErro = new Label();
        lblErro.setFont(Font.font(12));

        // --- Botões ---
        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setPrefWidth(180);
        btnSalvar.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            try {
                handleAtualizar();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(120);
        btnCancelar.setStyle("-fx-background-color: #7F8C8D; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> {
            TelaDashboard.mudarConteudo(new TelaGerenciarEnderecos().getLayout());
        });
        
        // Colocando o lblErro na linha de baixo para evitar que os botões mudem de tamanho dinamicamente
        HBox hboxBotoes = new HBox(15, btnSalvar, btnCancelar);
        hboxBotoes.setAlignment(Pos.CENTER_LEFT);

        cardForm.getChildren().addAll(vboxEndereco, hboxBotoes, lblErro);
        root.getChildren().addAll(lblTitulo, cardForm);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");
        
        return scroll;
    }

    private void carregarDadosCompletos(Endereco enderecoSelecionado) {
        if (enderecoSelecionado == null) return;

        // Backup de segurança com os dados vindos da linha do TableView
        this.enderecoProcurado = enderecoSelecionado;
        
        try {
            int idEndereco = enderecoSelecionado.getIdEndereco();
            System.out.println("[DEBUG] Editando Endereço - ID vindo da Tabela: " + idEndereco);
            
            // Busca o registro atualizado direto do banco de dados
            enderecoCtrl = new EnderecoController(enderecoSelecionado);
            Endereco endBanco = enderecoCtrl.procurarEndereco();
            if (endBanco != null) {
                this.enderecoProcurado = endBanco;
            }
        } catch (Exception e) {
            System.out.println("[AVISO] Erro ao carregar dados do banco. Usando dados da tabela.");
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        boolean numeroOk;

        try { 
            Integer.parseInt(tfNumero.getText().trim()); 
            numeroOk = true; 
        } catch (NumberFormatException e) { 
            numeroOk = false; 
        }

        return !tfRua.getText().trim().isEmpty() &&
               !tfBairro.getText().trim().isEmpty() &&
               !tfCidade.getText().trim().isEmpty() &&
               tfUF.getText().trim().length() == 2 &&
               tfCep.getText().trim().length() == 8 &&
               numeroOk;
    }

    private void handleAtualizar() throws SQLException {
        lblErro.setText("");
        if (validarCampos()) {
            int numero = Integer.parseInt(tfNumero.getText().trim());
            
            // Monta o objeto com as alterações dos inputs da tela
            Endereco endereco = new Endereco(
                tfRua.getText().trim(), tfBairro.getText().trim(),
                tfCidade.getText().trim(), tfUF.getText().trim(),
                tfCep.getText().trim(), numero, tfComplemento.getText().trim()
            );
            endereco.setIdEndereco(enderecoProcurado.getIdEndereco());
            
            // Executa a query de atualização via Controller
            enderecoCtrl = new EnderecoController(endereco);
            enderecoCtrl.atualizarEndereco();

            lblErro.setTextFill(Color.GREEN);
            lblErro.setText("Endereço atualizado com sucesso!");
            
            // Aguarda 1.5 segundos e redireciona de volta para a tabela de listagem
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.5));
            delay.setOnFinished(event -> TelaDashboard.mudarConteudo(new TelaGerenciarEnderecos().getLayout()));
            delay.play();
            
        } else {
            lblErro.setTextFill(Color.RED);
            lblErro.setText("Por favor, preencha todos os campos corretamente!");
        }
    }
}