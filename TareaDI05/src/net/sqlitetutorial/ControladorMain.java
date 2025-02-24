package net.sqlitetutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

public class ControladorMain {

    public void handleCerrar(ActionEvent event) {
        Platform.exit();
    }

    public void handleInformeClientes(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/InformeClientes.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Informe de Clientes");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleInformeArtistas(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ListaArtistas.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Informe de Artistas");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
