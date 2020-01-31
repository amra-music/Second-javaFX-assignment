package ba.unsa.etf.rpr.t7;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AboutController {
    public Label aboutLbl;
    public ImageView imageView;

    @FXML
    public void initialize() {
        imageView.setImage(new Image("/img/info.png"));
        aboutLbl.setText("About\n\nVerzija: 1.0\nWeb stranica:etf.unsa.ba\nGithub:https://github.com/amusic5\nAutor:Amra Music");
    }
}
