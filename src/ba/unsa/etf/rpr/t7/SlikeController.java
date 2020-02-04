package ba.unsa.etf.rpr.t7;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SlikeController {
    public TextField fldSlika;
    public GridPane gridPane;
    public ScrollPane scrollPane;
    private String slika;
    private int brojac = 0;

    public void searchAction(ActionEvent actionEvent) throws GiphyException {
        if (fldSlika == null || fldSlika.getText().equals(""))
            return;
        Giphy giphy = new Giphy("glbzpfO2UxOkJoHW1oXxQcy7UcoVQz2A");

        SearchFeed feed = giphy.search(fldSlika.getText(), 25, 0);

        Thread thread = new Thread(() -> {
            boolean max = false;
            int zapamtiBrojac = -1;
            int rowIndex = 1;
            for (int i = 0; i < feed.getDataList().size(); i++) {
                final Button button = new Button();
                final ImageView imageView = new ImageView(new Image("img/loading.gif"));
                imageView.setFitWidth(128);
                imageView.setFitHeight(128);
                button.setGraphic(imageView);
                if (!max && gridPane.getWidth() + 128 >= scrollPane.getWidth()) {
                    max = true;
                    rowIndex++;
                    zapamtiBrojac = brojac;
                    brojac = 0;
                }
                if (max && brojac % zapamtiBrojac == 0) {
                    rowIndex++;
                    brojac = 0;
                }
                int finalRowIndex = rowIndex;
                Platform.runLater(() -> gridPane.add(button, brojac++, finalRowIndex));
                String url = feed.getDataList().get(i).getImages().getOriginalStill().getUrl();
                url = formirajDirektanURL(url);
                button.setId(url);
                final ImageView imageView2 = new ImageView(new Image(url));
                imageView2.setFitWidth(128);
                imageView2.setFitHeight(128);
                Platform.runLater(() -> button.setGraphic(imageView2));
                button.setOnAction(e -> slika = button.getId());
            }
        });
        thread.start();
    }

    private String formirajDirektanURL(String url) {
        String urlP = url.substring(31);
        return "https://i.giphy.com/media/" + urlP.substring(0, urlP.indexOf("/")) + "/giphy_s.gif";
    }

    public void okAction(ActionEvent actionEvent) {
        Stage stage = (Stage) fldSlika.getScene().getWindow();
        stage.close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        slika = null;
        Stage stage = (Stage) fldSlika.getScene().getWindow();
        stage.close();
    }

    public String getSlika() {
        return slika;
    }
}
