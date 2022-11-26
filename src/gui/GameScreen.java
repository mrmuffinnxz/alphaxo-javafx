package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class GameScreen extends HBox {
	private Board board;
	private Panel panel;

	public GameScreen(int width, int height) {
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		this.setPrefHeight(height);
		this.setPrefWidth(width);

		this.setBoard(new Board());
		this.setPanel(new Panel());
		this.getChildren().addAll(this.getBoard(), this.getPanel());
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

}
