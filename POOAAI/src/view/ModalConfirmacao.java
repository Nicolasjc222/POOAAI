package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalConfirmacao {

    /**
     * Exibe um modal de confirmação estilizado e bloqueante.
     *
     * @param titulo   Título exibido no modal
     * @param mensagem Descrição do item a ser excluído
     * @return true se o usuário confirmou, false se cancelou
     */
    public static boolean confirmar(String titulo, String mensagem) {
        final boolean[] confirmado = {false};

        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.TRANSPARENT);
        modal.setResizable(false);

        // Ícone de aviso
        Label lblIcone = new Label("⚠");
        lblIcone.setFont(Font.font("Segoe UI", FontWeight.BOLD, 38));
        lblIcone.setTextFill(Color.valueOf("#E67E22"));

        // Título
        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        lblTitulo.setTextFill(Color.valueOf("#2C3E50"));

        // Mensagem
        Label lblMensagem = new Label(mensagem);
        lblMensagem.setFont(Font.font("Segoe UI", 13));
        lblMensagem.setTextFill(Color.valueOf("#555555"));
        lblMensagem.setWrapText(true);
        lblMensagem.setMaxWidth(320);

        // Botão cancelar (ação neutra fica à esquerda)
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(130);
        btnCancelar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnCancelar.setStyle(
            "-fx-background-color: #95A5A6; -fx-text-fill: white;" +
            "-fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;"
        );
        btnCancelar.setOnAction(e -> modal.close());

        // Botão confirmar (ação destrutiva fica à direita)
        Button btnConfirmar = new Button("Sim, excluir");
        btnConfirmar.setPrefWidth(130);
        btnConfirmar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnConfirmar.setStyle(
            "-fx-background-color: #c0392b; -fx-text-fill: white;" +
            "-fx-padding: 10 20; -fx-background-radius: 4; -fx-cursor: hand;"
        );
        btnConfirmar.setOnAction(e -> {
            confirmado[0] = true;
            modal.close();
        });

        HBox hboxBotoes = new HBox(12, btnCancelar, btnConfirmar);
        hboxBotoes.setAlignment(Pos.CENTER_RIGHT);

        // Card principal
        VBox card = new VBox(15, lblIcone, lblTitulo, lblMensagem, hboxBotoes);
        card.setPadding(new Insets(30));
        card.setMaxWidth(390);
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 24, 0, 0, 8);"
        );

        // Wrapper transparente para a sombra não cortar
        StackPane wrapper = new StackPane(card);
        wrapper.setPadding(new Insets(20));
        wrapper.setStyle("-fx-background-color: transparent;");

        Scene scene = new Scene(wrapper);
        scene.setFill(Color.TRANSPARENT);

        modal.setScene(scene);
        modal.showAndWait(); // bloqueia até o usuário escolher

        return confirmado[0];
    }
}