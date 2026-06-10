package view;

import java.sql.SQLException;

import controller.EnderecoController;
import controller.ImovelController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import model.Imovel;

public class TelaCadastroImovel {

    private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
    private TextField tfTipoPropriedade, tfArea, tfValor, tfComodos;
    private ComboBox<String> cbFinalidade; // NOVO CAMPO
    private Label lblErro;

    private ImovelController imovelCtrl;
    private EnderecoController enderecoCtrl;

    public Region getLayout() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40));

        Label lblTitulo = new Label("Cadastro de Imóveis");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        VBox cardForm = new VBox(20);
        cardForm.setPadding(new Insets(30));
        cardForm.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        // --- Seção Endereço ---
        Label lblEndereco = new Label("Endereço do Imóvel:");
        lblEndereco.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        tfCep         = new TextField(); tfCep.setPromptText("00000-000");
        tfRua         = new TextField(); tfRua.setPromptText("Digite a rua");
        tfNumero      = new TextField(); tfNumero.setPromptText("Número");
        tfBairro      = new TextField(); tfBairro.setPromptText("Bairro");
        tfCidade      = new TextField(); tfCidade.setPromptText("Cidade");
        tfUF          = new TextField(); tfUF.setPromptText("UF");
        tfComplemento = new TextField(); tfComplemento.setPromptText("Complemento (opcional)");

        GridPane gridEndereco = new GridPane();
        gridEndereco.setHgap(10); gridEndereco.setVgap(12);
        gridEndereco.add(new Label("CEP:"),         0, 0); gridEndereco.add(tfCep,         1, 0);
        gridEndereco.add(new Label("Rua:"),         2, 0); gridEndereco.add(tfRua,         3, 0);
        gridEndereco.add(new Label("Número:"),      0, 1); gridEndereco.add(tfNumero,      1, 1);
        gridEndereco.add(new Label("Bairro:"),      2, 1); gridEndereco.add(tfBairro,      3, 1);
        gridEndereco.add(new Label("Cidade:"),      0, 2); gridEndereco.add(tfCidade,      1, 2);
        gridEndereco.add(new Label("UF:"),          2, 2); gridEndereco.add(tfUF,          3, 2);
        gridEndereco.add(new Label("Complemento:"), 0, 3); gridEndereco.add(tfComplemento, 1, 3);

        VBox vboxEndereco = new VBox(10, lblEndereco, gridEndereco);

        // --- Seção Dados do Imóvel ---
        Label lblDadosImovel = new Label("Características:");
        lblDadosImovel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        tfTipoPropriedade = new TextField(); tfTipoPropriedade.setPromptText("Ex: Casa, Apartamento");
        tfArea            = new TextField(); tfArea.setPromptText("Área em m²");
        tfValor           = new TextField(); tfValor.setPromptText("Valor em R$");
        tfComodos         = new TextField(); tfComodos.setPromptText("Qtd. Cômodos");
        
        // NOVO CAMPO: Caixa de seleção para Venda ou Aluguel
        cbFinalidade = new ComboBox<>();
        cbFinalidade.getItems().addAll("Venda", "Aluguel");
        cbFinalidade.setPromptText("Selecione...");

        GridPane gridImovel = new GridPane();
        gridImovel.setHgap(10); gridImovel.setVgap(12);
        gridImovel.add(new Label("Tipo (Casa/Apt):"), 0, 0); gridImovel.add(tfTipoPropriedade, 1, 0);
        gridImovel.add(new Label("Área (m²):"),       0, 1); gridImovel.add(tfArea,            1, 1);
        gridImovel.add(new Label("Valor (R$):"),      0, 2); gridImovel.add(tfValor,           1, 2);
        gridImovel.add(new Label("Cômodos:"),         0, 3); gridImovel.add(tfComodos,         1, 3);
        gridImovel.add(new Label("Finalidade:"),      0, 4); gridImovel.add(cbFinalidade,      1, 4); // ADICIONADO AQUI

        VBox vboxImovel = new VBox(10, lblDadosImovel, gridImovel);

        lblErro = new Label();
        lblErro.setFont(Font.font(12));

        Button btnSalvar = new Button("Salvar Imóvel");
        btnSalvar.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            try { handleCadastro(); } catch (SQLException ex) { ex.printStackTrace(); }
        });

        Button btnLimpar = new Button("Limpar");
        btnLimpar.setStyle("-fx-background-color: #7F8C8D; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnLimpar.setOnAction(e -> limparCampos());

        HBox hboxBotoes = new HBox(15, btnSalvar, btnLimpar, lblErro);
        hboxBotoes.setAlignment(Pos.CENTER_LEFT);

        cardForm.getChildren().addAll(vboxEndereco, vboxImovel, hboxBotoes);
        root.getChildren().addAll(lblTitulo, cardForm);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");

        return scroll;
    }

    private boolean validarCampos() {
        boolean numerosOk;
        try {
            Integer.parseInt(tfNumero.getText().trim());
            Integer.parseInt(tfArea.getText().trim());
            Integer.parseInt(tfValor.getText().trim());
            Integer.parseInt(tfComodos.getText().trim());
            numerosOk = true;
        } catch (NumberFormatException e) {
            numerosOk = false;
        }

        return !tfRua.getText().trim().isEmpty() &&
               !tfBairro.getText().trim().isEmpty() &&
               !tfCidade.getText().trim().isEmpty() &&
               tfUF.getText().trim().length() == 2 &&
               tfCep.getText().trim().length() == 8 &&
               !tfTipoPropriedade.getText().trim().isEmpty() &&
               cbFinalidade.getValue() != null && // VALIDA SE A FINALIDADE FOI SELECIONADA
               numerosOk;
    }

    private void handleCadastro() throws SQLException {
        lblErro.setText("");
        if (validarCampos()) {
            int numero = Integer.parseInt(tfNumero.getText().trim());
            int area   = Integer.parseInt(tfArea.getText().trim());
            int valor  = Integer.parseInt(tfValor.getText().trim());
            int comodos = Integer.parseInt(tfComodos.getText().trim());

            Endereco endereco = new Endereco(
                tfRua.getText().trim(), tfBairro.getText().trim(),
                tfCidade.getText().trim(), tfUF.getText().trim(),
                tfCep.getText().trim(), numero, tfComplemento.getText().trim()
            );
            enderecoCtrl = new EnderecoController(endereco);
            enderecoCtrl.salvarEndereco();

            Imovel imovel = new Imovel(endereco, tfTipoPropriedade.getText().trim(), area, valor, comodos);
            
            // ADICIONADO: Inserindo a finalidade no objeto antes de salvar
            imovel.setFinalidade(cbFinalidade.getValue()); 
            
            imovelCtrl = new ImovelController(imovel);
            imovelCtrl.salvarImovel();

            lblErro.setTextFill(Color.GREEN);
            lblErro.setText("Cadastro realizado com sucesso!");
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
        tfTipoPropriedade.setText("");
        tfArea.setText("");
        tfValor.setText("");
        tfComodos.setText("");
        cbFinalidade.setValue(null); // Limpa o ComboBox
    }
}