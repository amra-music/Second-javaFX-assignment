package ba.unsa.etf.rpr.t7;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Locale;
import java.util.ResourceBundle;


public class AboutController {
    public Label aboutLbl;
    public ImageView imageView;

    @FXML
    public void initialize() {
        imageView.setImage(new Image("/img/info.png"));
        String tekst = ResourceBundle.getBundle("Translation", new Locale("bs", "BA")).getString("aboutText");
        if (KorisnikController.engleskiIzabran)
            tekst = ResourceBundle.getBundle("Translation", new Locale("en", "US")).getString("aboutText");
        aboutLbl.setText(tekst);
    }
}
