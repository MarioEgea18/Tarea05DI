package net.sqlitetutorial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;

public class ListaArtistas {

    @FXML
    private ListView<Artist> listViewArtists;

    @FXML
    public void initialize() {
        ObservableList<Artist> artistList = FXCollections.observableArrayList();
        try {
            Connection conn = DBUtil.getConnection();
            String query = "SELECT ArtistId, Name FROM artists;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Artist artist = new Artist(
                        rs.getInt("ArtistId"),
                        rs.getString("Name")
                );
                artistList.add(artist);
            }
            listViewArtists.setItems(artistList);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleArtistSelection(MouseEvent event) {
        if (event.getClickCount() == 2) { // Hay que darle doble clic para seleccionar el artista
            Artist artistaSeleccionado = listViewArtists.getSelectionModel().getSelectedItem();
            if (artistaSeleccionado != null) {
                try {
                    URL fxmlLocation = getClass().getResource("/fxml/DetalleArtista.fxml");
                    if (fxmlLocation == null) {
                        System.err.println("Error: No se encontró DetalleArtista.fxml");
                        return;
                    }

                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    Parent root = loader.load();
                    DetalleArtistas controller = loader.getController();
                    controller.setArtist(artistaSeleccionado);

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());
                    Stage stage = new Stage();
                    stage.setTitle("Detalle de Artista");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}