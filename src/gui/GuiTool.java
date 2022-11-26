package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GuiTool {
    public static ImageView createImageView(String url, int width, int height) {
		ImageView img = new ImageView();
		img.setImage(new Image(url, true));
		img.setFitHeight(height);
		img.setFitWidth(width);
		return img;
	}

    public static Text createTitle(String text) {
		Text txt = new Text();
		txt.setText(text);
		txt.setStyle("-fx-font: 32 arial;");
		return txt;
	}
}
