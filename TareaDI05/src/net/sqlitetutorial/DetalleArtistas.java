package net.sqlitetutorial;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleArtistas {

    @FXML
    private Label lblArtistName;
    @FXML
    private TreeView<String> treeViewAlbums;

    private Artist artist;

    public void setArtist(Artist artist) {
        this.artist = artist;
        lblArtistName.setText(artist.getName());
        loadArtistData();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void loadArtistData() {
        TreeItem<String> rootItem = new TreeItem<>("Albums y Pistas");
        try {
            Connection conn = DBUtil.getConnection();
            String albumQuery = "SELECT AlbumId, Title FROM `albums` WHERE ArtistId = ?";
            PreparedStatement pstmtAlbum = conn.prepareStatement(albumQuery);
            pstmtAlbum.setInt(1, artist.getArtistId());
            ResultSet rsAlbum = pstmtAlbum.executeQuery();
            while (rsAlbum.next()) {
                int albumId = rsAlbum.getInt("AlbumId");
                String albumTitle = rsAlbum.getString("Title");
                TreeItem<String> albumItem = new TreeItem<>("Álbum: " + albumTitle);
                String trackQuery = "SELECT Name FROM `tracks` WHERE AlbumId = ?";
                PreparedStatement pstmtTrack = conn.prepareStatement(trackQuery);
                pstmtTrack.setInt(1, albumId);
                ResultSet rsTrack = pstmtTrack.executeQuery();
                while (rsTrack.next()) {
                    String trackName = rsTrack.getString("Name");
                    TreeItem<String> trackItem = new TreeItem<>("Pista: " + trackName);
                    albumItem.getChildren().add(trackItem);
                }
                rsTrack.close();
                pstmtTrack.close();
                rootItem.getChildren().add(albumItem);
            }
            rsAlbum.close();
            pstmtAlbum.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        treeViewAlbums.setRoot(rootItem);
        treeViewAlbums.setShowRoot(false);
    }
}
