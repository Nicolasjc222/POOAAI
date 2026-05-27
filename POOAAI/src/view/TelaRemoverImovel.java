package view;

import java.sql.SQLException;

import controller.ImovelController;
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
import model.Imovel;

public class TelaRemoverImovel {

	private TextField tfIdImovel;
	private Label lblErro;
	private Imovel imovelProcurado;

	private ImovelController imovelCtrl;

	public Scene getScene() {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

		Label lblTitulo = new Label("Remover imovel");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		lblTitulo.setTextFill(Color.DARKBLUE);

		Button btnVoltar = new Button("Voltar");
		btnVoltar.setPrefWidth(150);
		btnVoltar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnVoltar.setStyle("-fx-background-color: #0c27f0; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnVoltar.setOnAction(e -> {
			Trocador.voltarTela();
		});

		Label lblIdImovel = new Label("ID Cliente:");
		lblIdImovel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		tfIdImovel = new TextField(); tfIdImovel.setPromptText("ID do imovel a ser removido");

		VBox vboxIdImovel = new VBox(10, lblIdImovel, tfIdImovel);
		vboxIdImovel.setAlignment(Pos.CENTER);

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
		root.getChildren().addAll(lblTitulo, vboxIdImovel, btnRemover, btnVoltar, lblErro);
		return new Scene(root, 800, 600);
	}

	private void handleRemover() throws SQLException {
		lblErro.setText("");
		if (verificarId()) {

			// remove imovel
			Imovel imovel = new Imovel();
			imovel.setIdImovel(imovelProcurado.getIdImovel());
			imovelCtrl = new ImovelController(imovel);
			imovelCtrl.deletarImovel();

			lblErro.setTextFill(Color.GREEN);
			lblErro.setText("Imovel removido com sucesso!");
			limparCampos();
		} else {
			lblErro.setTextFill(Color.RED);
			lblErro.setText("Por favor, preencha todos os campos corretamente!");
		}
	}

	private void limparCampos() {
		tfIdImovel.setText("");
	}

	private boolean verificarId() {
		boolean idOk;
		try {
			int idImovel = Integer.parseInt(tfIdImovel.getText().trim());
			Imovel imovel = new Imovel();
			imovel.setIdImovel(idImovel);
			imovelCtrl = new ImovelController(imovel);
			imovelProcurado = imovelCtrl.procurarImovel();
			idOk = true;

		} catch (NumberFormatException e) { idOk = false; }
		return idOk;
	}
}