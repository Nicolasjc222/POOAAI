package view;

import java.sql.SQLException;

import controller.ClienteController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Cliente;

public class TelaRemoverCliente {

	private TextField tfIdCliente;
	private Label lblErro;
	private Cliente clienteProcurado;

	private ClienteController clienteCtrl;

	public Scene getScene() {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

		Label lblTitulo = new Label("Remover cliente");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.DARKBLUE);

		Button btnVoltar = new Button("Voltar");
		btnVoltar.setPrefWidth(150);
		btnVoltar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnVoltar.setStyle("-fx-background-color: #0c27f0; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnVoltar.setOnAction(e -> {
			Trocador.voltarTela();
		});

		Label lblIdCliente = new Label("ID Cliente:");
		lblIdCliente.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		tfIdCliente = new TextField(); tfIdCliente.setPromptText("ID do cliente a ser removido");

		VBox vboxIdCliente = new VBox(10, lblIdCliente, tfIdCliente);
		vboxIdCliente.setAlignment(Pos.CENTER);

		Button btnRemover = new Button("Remover");
		btnRemover.setPrefWidth(150);
		btnRemover.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnRemover.setStyle("-fx-background-color: #e61515; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnRemover.setOnAction(e -> {
			try {
				handleRemover();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		lblErro = new Label();
		lblErro.setTextFill(Color.RED);
		lblErro.setFont(Font.font(12));
		root.getChildren().addAll(lblTitulo, vboxIdCliente, btnRemover, btnVoltar, lblErro);
		return new Scene(root, 800, 600);
	}

	private void handleRemover() throws SQLException {
		lblErro.setText("");
		if (verificarId()) {

			// remove cliente
			Cliente cliente = new Cliente();
			cliente.setIdCliente(clienteProcurado.getIdCliente());
			clienteCtrl = new ClienteController(cliente);
			clienteCtrl.deletarCliente();

			lblErro.setTextFill(Color.GREEN);
			lblErro.setText("Cliente removido com sucesso!");
			limparCampos();
		} else {
			lblErro.setTextFill(Color.RED);
			lblErro.setText("Por favor, preencha todos os campos corretamente!");
		}
	}

	private void limparCampos() {
		tfIdCliente.setText("");
	}

	private boolean verificarId() {
		boolean idOk;
		try {
			int idCliente = Integer.parseInt(tfIdCliente.getText().trim());
			Cliente cliente = new Cliente();
			cliente.setIdCliente(idCliente);
			clienteCtrl = new ClienteController(cliente);
			clienteProcurado = clienteCtrl.procurarCliente();
			idOk = true;

		} catch (NumberFormatException e) { idOk = false; }
		return idOk;
	}
}