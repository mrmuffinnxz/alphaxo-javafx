package gui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class Menu extends VBox {
	private ArrayList<Integer> validModes;
	private ImageView logo;
	private Text title;
	private ArrayList<Button> selectMode;
	private Button exit;

	public Menu(ArrayList<Integer> validModes) {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);

		this.setLogo(GuiTool.createImageView("logo.png", 100, 100));
		this.setTitle(GuiTool.createTitle("AlphaXO Game"));

		this.setValidModes(validModes);
		this.setSelectMode(new ArrayList<Button>());
		for (int i = 0; i < this.getValidModes().size(); i++) {
			this.getSelectMode().add(this.createSelectModeButton(this.getValidModes().get(i)));
		}
		this.setExit(this.createExitButton());

		this.getChildren().add(this.getLogo());
		this.getChildren().add(this.getTitle());
		for (int i = 0; i < this.getSelectMode().size(); i++) {
			this.getChildren().add(this.getSelectMode().get(i));
		}
		this.getChildren().add(this.getExit());
	}

	private Button createSelectModeButton(int boardSize) {
		Button btn = new Button();
		btn.setText("Play " + boardSize + " X " + boardSize);
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
		btn.setOnAction(event -> this.selectModeHandler(event, boardSize));
		return btn;
	}

	private Button createExitButton() {
		Button btn = new Button();
		btn.setText("Exit");
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
		btn.setOnAction(e -> Platform.exit());
		return btn;
	}

	private void selectModeHandler(ActionEvent event, int boardSize) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(new SubMenu(boardSize), Main.getScreenWidth(), Main.getScreenHeight());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public ImageView getLogo() {
		return logo;
	}

	public void setLogo(ImageView logo) {
		this.logo = logo;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public ArrayList<Integer> getValidModes() {
		return validModes;
	}

	public void setValidModes(ArrayList<Integer> validModes) {
		this.validModes = validModes;
	}

	public ArrayList<Button> getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(ArrayList<Button> selectMode) {
		this.selectMode = selectMode;
	}

	public Button getExit() {
		return exit;
	}

	public void setExit(Button exit) {
		this.exit = exit;
	}
}
