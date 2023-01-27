import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class UrlShortnerController {
    @FXML
    private TextField longUrlField;

    @FXML
    private Label shortUrlLabel;

    @FXML
    private void shortenUrl() {
        String longUrl = longUrlField.getText();
        String shortUrl = UrlShortner.shortenUrl(longUrl);
        shortUrlLabel.setText(shortUrl);
    }
}
