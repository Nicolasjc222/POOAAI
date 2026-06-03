package view;

import java.sql.SQLException;

import controller.ClienteController;
import controller.EnderecoController;
import controller.PessoaFisicaController;
import controller.PessoaJuridicaController;
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
import model.Cliente;
import model.Endereco;
import model.PessoaFisica;
import model.PessoaJuridica;

public class TelaCadastroCliente {

    private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
    private TextField tfTelefone, tfEmail;
    private TextField tfNome, tfCpf;
    private TextField tfRazaoSocial, tfCnpj;
    private ComboBox<String> cbTipo;
    private Label lblErro;
    private VBox vboxTipoExtra;
    
    private ClienteController clienteCtrl;
    private EnderecoController enderecoCtrl;
    private PessoaFisicaController pfCtrl;
    private PessoaJuridicaController pjCtrl;

    public Region getLayout() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_LEFT); 
        root.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Cadastro de Clientes");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Criação de um "Card" branco para agrupar o formulário
        VBox cardForm = new VBox(20);
        cardForm.setPadding(new Insets(30));
        cardForm.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");	

        // --- Seção Endereço ---
        Label lblEndereco = new Label("Endereço:");
        lblEndereco.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        tfCep         = new TextField(); tfCep.setPromptText("00000-000");
        tfRua         = new TextField(); tfRua.setPromptText("Digite a rua");
        tfNumero      = new TextField(); tfNumero.setPromptText("Número");
        tfBairro      = new TextField(); tfBairro.setPromptText("Bairro");
        tfCidade      = new TextField(); tfCidade.setPromptText("Cidade");
        tfUF          = new TextField(); tfUF.setPromptText("UF");
        tfComplemento = new TextField(); tfComplemento.setPromptText("Complemento (opcional)");

        GridPane gridEndereco = new GridPane();
        gridEndereco.setHgap(10);
        gridEndereco.setVgap(12);
        gridEndereco.setAlignment(Pos.CENTER_LEFT);
        gridEndereco.add(new Label("CEP:"),         0, 0); gridEndereco.add(tfCep,         1, 0);
        gridEndereco.add(new Label("Rua:"),         2, 0); gridEndereco.add(tfRua,         3, 0);
        gridEndereco.add(new Label("Número:"),      0, 1); gridEndereco.add(tfNumero,      1, 1);
        gridEndereco.add(new Label("Bairro:"),      2, 1); gridEndereco.add(tfBairro,      3, 1);
        gridEndereco.add(new Label("Cidade:"),      0, 2); gridEndereco.add(tfCidade,      1, 2);
        gridEndereco.add(new Label("UF:"),          2, 2); gridEndereco.add(tfUF,          3, 2);
        gridEndereco.add(new Label("Complemento:"), 0, 3); gridEndereco.add(tfComplemento, 1, 3);

        VBox vboxEndereco = new VBox(10, lblEndereco, gridEndereco);
        vboxEndereco.setAlignment(Pos.CENTER_LEFT); // Corrigido para esquerda para manter consistência no card

        // --- Seção Cliente ---
        Label lblDadosCliente = new Label("Dados do Cliente:");
        lblDadosCliente.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        tfTelefone = new TextField(); tfTelefone.setPromptText("Digite o telefone");
        tfEmail    = new TextField(); tfEmail.setPromptText("Digite o email");

        cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("PF", "PJ");
        cbTipo.setPromptText("Selecione o tipo");

        vboxTipoExtra = new VBox(10);
        vboxTipoExtra.setAlignment(Pos.CENTER_LEFT);

        cbTipo.setOnAction(e -> {
            vboxTipoExtra.getChildren().clear();
            
            if (cbTipo.getValue() == null) {
                return;
            }

            GridPane gridTipo = new GridPane();
            gridTipo.setHgap(10);
            gridTipo.setVgap(12);
            gridTipo.setAlignment(Pos.CENTER_LEFT);

            if (cbTipo.getValue().equals("PF")) {
                tfNome = new TextField(); tfNome.setPromptText("Nome completo");
                tfCpf  = new TextField(); tfCpf.setPromptText("000.000.000-00");
                gridTipo.add(new Label("Nome:"), 0, 0); gridTipo.add(tfNome, 1, 0);
                gridTipo.add(new Label("CPF:"),  0, 1); gridTipo.add(tfCpf,  1, 1);
            } else {
                tfRazaoSocial = new TextField(); tfRazaoSocial.setPromptText("Razão Social");
                tfCnpj        = new TextField(); tfCnpj.setPromptText("00.000.000/0000-00");
                gridTipo.add(new Label("Razão Social:"), 0, 0); gridTipo.add(tfRazaoSocial, 1, 0);
                gridTipo.add(new Label("CNPJ:"),         0, 1); gridTipo.add(tfCnpj,        1, 1);
            }

            vboxTipoExtra.getChildren().add(gridTipo);
        });

        GridPane gridCliente = new GridPane();
        gridCliente.setHgap(10);
        gridCliente.setVgap(12);
        gridCliente.setAlignment(Pos.CENTER_LEFT);
        gridCliente.add(new Label("Telefone:"),  0, 0); gridCliente.add(tfTelefone, 1, 0);
        gridCliente.add(new Label("Email:"),     0, 1); gridCliente.add(tfEmail,    1, 1);
        gridCliente.add(new Label("Tipo:"),      0, 2); gridCliente.add(cbTipo,     1, 2);

        VBox vboxCliente = new VBox(10, lblDadosCliente, gridCliente, vboxTipoExtra);
        vboxCliente.setAlignment(Pos.CENTER_LEFT);

        // --- CORREÇÃO: Inicializar lblErro ANTES de usar no HBox ---
        lblErro = new Label();
        lblErro.setTextFill(Color.RED);
        lblErro.setFont(Font.font(12));

        // --- Botão e erro ---
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setPrefWidth(150);
        btnSalvar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnSalvar.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            try {
                handleCadastro();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        
        Button btnLimpar = new Button("Limpar");
        btnLimpar.setPrefWidth(150);
        btnLimpar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnLimpar.setStyle("-fx-background-color: #95A5A6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnLimpar.setOnAction(e -> {
            limparCampos();
        });
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(150);
        btnVoltar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnVoltar.setStyle("-fx-background-color: #0c27f0; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> {
        TelaDashboard.mudarConteudo(new TelaGerenciarClientes().getLayout());
        });
        
        // Agrupar botões
        HBox hboxBotoes = new HBox(15, btnSalvar, btnLimpar, btnVoltar, lblErro);
        hboxBotoes.setAlignment(Pos.CENTER_LEFT);

        // --- CORREÇÃO: Adicionar os componentes de formulário e botões dentro do cardForm ---
        cardForm.getChildren().addAll(vboxEndereco, vboxCliente, hboxBotoes);

        root.getChildren().addAll(lblTitulo, cardForm);

        // Retornamos dentro de um ScrollPane caso a tela seja menor que o conteúdo
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");
        
        return scroll;
    }
    
    private boolean validarCampos() {
        boolean telefoneOk;
        boolean numeroOk;

        try {
			Long.parseLong(tfTelefone.getText().trim());
			if(tfTelefone.getText().trim().length() > 9) {
				telefoneOk = true;
			} else { telefoneOk = false; }
        }
		catch (NumberFormatException e) { telefoneOk = false; }


        try { Integer.parseInt(tfNumero.getText().trim()); numeroOk = true; }
        catch (NumberFormatException e) { numeroOk = false; }

        if (cbTipo.getValue() == null) return false;

        boolean tipoExtraOk;
        if (cbTipo.getValue().equals("PF")) {
            tipoExtraOk = tfNome != null && !tfNome.getText().trim().isEmpty() && verificarCpf(tfCpf.getText().trim());
        } else {
            tipoExtraOk = tfRazaoSocial != null && !tfRazaoSocial.getText().trim().isEmpty() && verificarCnpj(tfCnpj.getText().trim());
        }

        return !tfRua.getText().trim().isEmpty() &&
               !tfBairro.getText().trim().isEmpty() &&
               !tfCidade.getText().trim().isEmpty() &&
               verificarEmail(tfEmail.getText().trim()) &&
               tfUF.getText().trim().length() == 2 &&
               tfCep.getText().trim().length() == 8 &&
               telefoneOk &&
               numeroOk &&
               tipoExtraOk;
    }

    private void handleCadastro() throws SQLException {
        lblErro.setText("");
        if (validarCampos()) {
            int numero   = Integer.parseInt(tfNumero.getText().trim());
            
            // 1. salva endereço
            Endereco endereco = new Endereco(
                tfRua.getText().trim(), tfBairro.getText().trim(),
                tfCidade.getText().trim(), tfUF.getText().trim(),
                tfCep.getText().trim(), numero, tfComplemento.getText().trim()
            );
            enderecoCtrl = new EnderecoController(endereco);
            enderecoCtrl.salvarEndereco();

            // 2. salva cliente
            String tipo = cbTipo.getValue();
        	Cliente cliente;
        	if(tipo.equals("PF")) {
        	cliente = new PessoaFisica();
        	} else {
        		cliente = new PessoaJuridica();
        	}
            cliente.setTelefone(tfTelefone.getText().trim());
            cliente.setEmail(tfEmail.getText().trim());
            cliente.setTipoCliente(cbTipo.getValue());
            cliente.setEndereco(endereco);
            clienteCtrl = new ClienteController(cliente);
            clienteCtrl.salvarCliente();

            // 3. salva PF ou PJ com o idCliente gerado
            if (cbTipo.getValue().equals("PF")) {
                PessoaFisica pf = new PessoaFisica();
                pf.setIdCliente(cliente.getIdCliente());
                pf.setNome(tfNome.getText().trim());
                pf.setCpf(tfCpf.getText().trim());
                pfCtrl = new PessoaFisicaController(pf);
                pfCtrl.salvarPessoaFisica();
            } else {
                PessoaJuridica pj = new PessoaJuridica();
                pj.setIdCliente(cliente.getIdCliente());
                pj.setRazaoSocial(tfRazaoSocial.getText().trim());
                pj.setCnpj(tfCnpj.getText().trim());
                pjCtrl = new PessoaJuridicaController(pj);
                pjCtrl.salvarPessoaJuridica();
            }

            lblErro.setTextFill(Color.GREEN);
            lblErro.setText("Cadastro realizado com sucesso!");
            limparCampos();
        } else {
            lblErro.setTextFill(Color.RED);
            lblErro.setText("Por favor, preencha todos os campos corretamente!");
        }
    }
    
    private boolean verificarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) return false;

        int digito1 = 0;
        int digito2 = 0;
        
        for (int x = 10, y = 0; x >= 2; x--, y++) {
            digito1 += (cpf.charAt(y) - '0') * x;
        }
        digito1 = 11 - digito1 % 11;
        if (digito1 >= 10) digito1 = 0;

        for (int x = 11, y = 0; x >= 3; x--, y++) {
            digito2 += (cpf.charAt(y) - '0') * x;
        }
        digito2 += digito1 * 2;
        digito2 = 11 - digito2 % 11;
        if (digito2 >= 10) digito2 = 0;

        return (cpf.charAt(9)  - '0') == digito1 && (cpf.charAt(10) - '0') == digito2;
    }
    
    private boolean verificarCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9A-Z]", "");
        if (cnpj.length() != 14) return false;

        int digito1 = 0;
        for (int x = 5, y = 0; y < 12; x--, y++) {
            if (x == 1) x = 10;
            digito1 += valorChar(cnpj.charAt(y)) * x;
        }
        digito1 = 11 - digito1 % 11;
        if (digito1 >= 10) digito1 = 0;

        int digito2 = 0;
        for (int x = 6, y = 0; y < 13; x--, y++) {
            if (x == 1) x = 10;
            digito2 += valorChar(cnpj.charAt(y)) * x;
        }
        digito2 = 11 - digito2 % 11;
        if (digito2 >= 10) digito2 = 0;

        return valorChar(cnpj.charAt(12)) == digito1 &&
               valorChar(cnpj.charAt(13)) == digito2;
    }

    private int valorChar(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        return c - 'A' + 10;
    }
    
    private boolean verificarEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    
    private void limparCampos() {
    	tfRua.setText("");
    	tfCep.setText("");
    	tfCidade.setText("");
    	tfBairro.setText("");
    	tfUF.setText("");
    	tfNumero.setText("");
    	tfComplemento.setText("");
        tfTelefone.setText("");
        tfEmail.setText("");
        cbTipo.setValue("");
        vboxTipoExtra.getChildren().clear();
        if (tfNome != null)        tfNome.setText("");
        if (tfCpf != null)         tfCpf.setText("");
        if (tfRazaoSocial != null) tfRazaoSocial.setText("");
        if (tfCnpj != null)        tfCnpj.setText("");
    }
}