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

public class TelaAtualizarCliente {

    private TextField tfRua, tfCep, tfCidade, tfBairro, tfUF, tfNumero, tfComplemento;
    private TextField tfTelefone, tfEmail;
    private TextField tfNome, tfCpf;
    private TextField tfRazaoSocial, tfCnpj;
    private ComboBox<String> cbTipo;
    private Label lblErro;
    private VBox vboxTipoExtra;
    
    private Cliente clienteProcurado;
    private PessoaFisica pessoaFisicaProcurada;
    private PessoaJuridica pessoaJuridicaProcurada;

    private ClienteController clienteCtrl;
    private EnderecoController enderecoCtrl;
    private PessoaFisicaController pfCtrl;
    private PessoaJuridicaController pjCtrl;

    public Region getLayout(Cliente clienteSelecionado) {
        // 1. Carrega e blinda os dados vindo da tabela e do banco
        carregarDadosCompletos(clienteSelecionado);

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_LEFT); 
        root.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Atualizar Cliente");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Card branco para o formulário
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

        // Preenche campos de endereço de forma segura (Null-Safe)
        if (clienteProcurado != null && clienteProcurado.getEndereco() != null) {
            tfCep.setText(clienteProcurado.getEndereco().getCep() != null ? clienteProcurado.getEndereco().getCep() : "");
            tfRua.setText(clienteProcurado.getEndereco().getRua() != null ? clienteProcurado.getEndereco().getRua() : "");
            tfNumero.setText(String.valueOf(clienteProcurado.getEndereco().getNumero()));
            tfBairro.setText(clienteProcurado.getEndereco().getBairro() != null ? clienteProcurado.getEndereco().getBairro() : "");
            tfCidade.setText(clienteProcurado.getEndereco().getCidade() != null ? clienteProcurado.getEndereco().getCidade() : "");
            tfUF.setText(clienteProcurado.getEndereco().getUf() != null ? clienteProcurado.getEndereco().getUf() : "");
            tfComplemento.setText(clienteProcurado.getEndereco().getComplemento() != null ? clienteProcurado.getEndereco().getComplemento() : "");
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

        // --- Seção Cliente ---
        Label lblDadosCliente = new Label("Dados do Cliente:");
        lblDadosCliente.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        tfTelefone = new TextField(); tfTelefone.setPromptText("Digite o telefone");
        tfEmail    = new TextField(); tfEmail.setPromptText("Digite o email");

        if (clienteProcurado != null) {
            tfTelefone.setText(clienteProcurado.getTelefone() != null ? String.valueOf(clienteProcurado.getTelefone()) : "");
            tfEmail.setText(clienteProcurado.getEmail() != null ? clienteProcurado.getEmail() : "");
        }

        cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("PF", "PJ");
        cbTipo.setValue(clienteProcurado != null ? clienteProcurado.getTipoCliente() : "PF");
        cbTipo.setDisable(true); 

        vboxTipoExtra = new VBox(10);
        vboxTipoExtra.setAlignment(Pos.CENTER_LEFT);

        GridPane gridTipo = new GridPane();
        gridTipo.setHgap(10); gridTipo.setVgap(12);
        gridTipo.setAlignment(Pos.CENTER_LEFT);

        if ("PF".equals(cbTipo.getValue())) {
            tfNome = new TextField(); tfNome.setPromptText("Nome completo");
            tfCpf  = new TextField(); tfCpf.setPromptText("000.000.000-00");
            if (pessoaFisicaProcurada != null) {
                tfNome.setText(pessoaFisicaProcurada.getNome() != null ? pessoaFisicaProcurada.getNome() : "");
                tfCpf.setText(pessoaFisicaProcurada.getCpf() != null ? pessoaFisicaProcurada.getCpf() : "");
            }
            gridTipo.add(new Label("Nome:"), 0, 0); gridTipo.add(tfNome, 1, 0);
            gridTipo.add(new Label("CPF:"),  0, 1); gridTipo.add(tfCpf,  1, 1);
        } else {
            tfRazaoSocial = new TextField(); tfRazaoSocial.setPromptText("Razão Social");
            tfCnpj        = new TextField(); tfCnpj.setPromptText("00.000.000/0000-00");
            if (pessoaJuridicaProcurada != null) {
                tfRazaoSocial.setText(pessoaJuridicaProcurada.getRazaoSocial() != null ? pessoaJuridicaProcurada.getRazaoSocial() : "");
                tfCnpj.setText(pessoaJuridicaProcurada.getCnpj() != null ? pessoaJuridicaProcurada.getCnpj() : "");
            }
            gridTipo.add(new Label("Razão Social:"), 0, 0); gridTipo.add(tfRazaoSocial, 1, 0);
            gridTipo.add(new Label("CNPJ:"),         0, 1); gridTipo.add(tfCnpj,        1, 1);
        }
        vboxTipoExtra.getChildren().add(gridTipo);

        GridPane gridCliente = new GridPane();
        gridCliente.setHgap(10); gridCliente.setVgap(12);
        gridCliente.setAlignment(Pos.CENTER_LEFT);
        gridCliente.add(new Label("Telefone:"),  0, 0); gridCliente.add(tfTelefone, 1, 0);
        gridCliente.add(new Label("Email:"),     0, 1); gridCliente.add(tfEmail,    1, 1);
        gridCliente.add(new Label("Tipo:"),      0, 2); gridCliente.add(cbTipo,     1, 2);

        VBox vboxCliente = new VBox(10, lblDadosCliente, gridCliente, vboxTipoExtra);

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
            TelaDashboard.mudarConteudo(new TelaGerenciarClientes().getLayout());
        });
        
        HBox hboxBotoes = new HBox(15, btnSalvar, btnCancelar, lblErro);
        hboxBotoes.setAlignment(Pos.CENTER_LEFT);

        cardForm.getChildren().addAll(vboxEndereco, vboxCliente, hboxBotoes);
        root.getChildren().addAll(lblTitulo, cardForm);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F4F6F8;");
        
        return scroll;
    }

    private void carregarDadosCompletos(Cliente clienteSelecionado) {
        if (clienteSelecionado == null) return;

        // PASSO DE SEGURANÇA: Definimos o clienteProcurado inicialmente com os dados da tabela!
        // Se a busca no banco falhar, o formulário abre com telefone, email e nome preenchidos.
        this.clienteProcurado = clienteSelecionado;
        
        try {
            int idCliente = clienteSelecionado.getIdCliente();
            String tipo = clienteSelecionado.getTipoCliente();
            
            // LOG DE DIAGNÓSTICO: Verifique no seu console se o ID está vindo correto ou se está "0"
            System.out.println("[DEBUG] Editando Cliente - ID vindo da Tabela: " + idCliente + " | Tipo: " + tipo);
            
            // Tenta buscar os dados da tabela mãe (Cliente)
            clienteCtrl = new ClienteController(clienteSelecionado);
            Cliente clienteBanco = clienteCtrl.procurarCliente();
            if (clienteBanco != null) {
                this.clienteProcurado = clienteBanco;
            }

            // Tenta buscar os dados da tabela filha (PF ou PJ)
            if ("PF".equals(tipo)) {
                pessoaFisicaProcurada = new PessoaFisica();
                pessoaFisicaProcurada.setIdCliente(idCliente);
                pfCtrl = new PessoaFisicaController(pessoaFisicaProcurada);
                PessoaFisica pfBanco = pfCtrl.procurarPessoaFisica();
                
                if (pfBanco != null) {
                    this.pessoaFisicaProcurada = pfBanco;
                } else if (clienteSelecionado instanceof PessoaFisica) {
                    this.pessoaFisicaProcurada = (PessoaFisica) clienteSelecionado;
                }
            } else if ("PJ".equals(tipo)) {
                pessoaJuridicaProcurada = new PessoaJuridica();
                pessoaJuridicaProcurada.setIdCliente(idCliente);
                pjCtrl = new PessoaJuridicaController(pessoaJuridicaProcurada);
                PessoaJuridica pjBanco = pjCtrl.procurarPessoaJuridica();
                
                if (pjBanco != null) {
                    this.pessoaJuridicaProcurada = pjBanco;
                } else if (clienteSelecionado instanceof PessoaJuridica) {
                    this.pessoaJuridicaProcurada = (PessoaJuridica) clienteSelecionado;
                }
            }
            
            // Tenta buscar o endereço associado
            if (this.clienteProcurado.getEndereco() != null) {
                enderecoCtrl = new EnderecoController(this.clienteProcurado.getEndereco());
                Endereco endBanco = enderecoCtrl.procurarEndereco();
                if (endBanco != null) {
                    this.clienteProcurado.setEndereco(endBanco);
                }
            }
        } catch (Exception e) {
            System.out.println("[AVISO] Erro ao carregar dados adicionais do banco. Usando dados da tabela.");
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        boolean telefoneOk;
        boolean numeroOk;

        try {
            Long.parseLong(tfTelefone.getText().trim());
            telefoneOk = tfTelefone.getText().trim().length() >= 8;
        } catch (NumberFormatException e) { telefoneOk = false; }

        try { Integer.parseInt(tfNumero.getText().trim()); numeroOk = true; }
        catch (NumberFormatException e) { numeroOk = false; }

        if (cbTipo.getValue() == null) return false;

        boolean tipoExtraOk;
        if (cbTipo.getValue().equals("PF")) {
            tipoExtraOk = tfNome != null && !tfNome.getText().trim().isEmpty() &&
                          tfCpf  != null && !tfCpf.getText().trim().isEmpty();
        } else {
            tipoExtraOk = tfRazaoSocial != null && !tfRazaoSocial.getText().trim().isEmpty() &&
                          tfCnpj        != null && !tfCnpj.getText().trim().isEmpty();
        }

        return !tfRua.getText().trim().isEmpty() &&
               !tfBairro.getText().trim().isEmpty() &&
               !tfCidade.getText().trim().isEmpty() &&
               !tfEmail.getText().trim().isEmpty() &&
               tfUF.getText().trim().length() == 2 &&
               tfCep.getText().trim().length() == 8 &&
               telefoneOk &&
               numeroOk &&
               tipoExtraOk;
    }

    private void handleAtualizar() throws SQLException {
        lblErro.setText("");
        if (validarCampos()) {
            int numero = Integer.parseInt(tfNumero.getText().trim());

            // 1. Atualiza endereço (Cria objeto caso estivesse nulo)
            int idEnderecoExistente = (clienteProcurado.getEndereco() != null) ? clienteProcurado.getEndereco().getIdEndereco() : 0;
            
            Endereco endereco = new Endereco(
                tfRua.getText().trim(), tfBairro.getText().trim(),
                tfCidade.getText().trim(), tfUF.getText().trim(),
                tfCep.getText().trim(), numero, tfComplemento.getText().trim()
            );
            endereco.setIdEndereco(idEnderecoExistente);
            enderecoCtrl = new EnderecoController(endereco);
            
            if (idEnderecoExistente > 0) {
                enderecoCtrl.atualizarEndereco();
            } else {
                // Caso não tivesse endereço, você pode chamar o salvar do endereço aqui
            }

            // 2. Atualiza cliente
            Cliente cliente = "PF".equals(cbTipo.getValue()) ? new PessoaFisica() : new PessoaJuridica();
            cliente.setTelefone(tfTelefone.getText().trim());
            cliente.setEmail(tfEmail.getText().trim());
            cliente.setTipoCliente(cbTipo.getValue());
            cliente.setEndereco(endereco);
            cliente.setIdCliente(clienteProcurado.getIdCliente());
            
            clienteCtrl = new ClienteController(cliente);
            clienteCtrl.atualizarCliente();

            // 3. Atualiza PF ou PJ
            if (cbTipo.getValue().equals("PF")) {
                PessoaFisica pf = new PessoaFisica();
                pf.setIdCliente(cliente.getIdCliente());
                pf.setNome(tfNome.getText().trim());
                pf.setCpf(tfCpf.getText().trim());
                pfCtrl = new PessoaFisicaController(pf);
                pfCtrl.atualizarPessoaFisica();
            } else {
                PessoaJuridica pj = new PessoaJuridica();
                pj.setIdCliente(cliente.getIdCliente());
                pj.setRazaoSocial(tfRazaoSocial.getText().trim());
                pj.setCnpj(tfCnpj.getText().trim());
                pjCtrl = new PessoaJuridicaController(pj);
                pjCtrl.atualizarPessoaJuridica();
            }

            lblErro.setTextFill(Color.GREEN);
            lblErro.setText("Cliente atualizado com sucesso!");
            
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.5));
            delay.setOnFinished(event -> TelaDashboard.mudarConteudo(new TelaGerenciarClientes().getLayout()));
            delay.play();
            
        } else {
            lblErro.setTextFill(Color.RED);
            lblErro.setText("Por favor, preencha todos os campos corretamente!");
        }
    }
}