package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Game;
import main.Main;

public class SubMenu extends VBox {
	private int boardSize;
	private Text title;
	private Text SubTitle;
	private ChoiceBox<String> gameMode;
	private ChoiceBox<String> playType;
	private Button start;
	private Button back;
	
	private HashMap<String, PlayType> playTypeMap;
	private HashMap<String, GameMode> gameModeMap;
	
	public SubMenu(int boardSize) {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setBoardSize(boardSize);
		this.setSubTitle(GuiTool.createTitle("Board size " + boardSize + " X " + boardSize));
		this.getSubTitle().setStyle("-fx-font: 28 arial;");
		this.setTitle(GuiTool.createTitle("Game Setting"));
		this.getTitle().setStyle("-fx-font: 40 arial;");
		this.setGameMode(this.createGameModeSelections());
		this.setPlayType(this.createPlayTypeSelections());
		this.setStart(this.createStartButton());
		this.setBack(this.createBackButton());
		this.getChildren().addAll(this.getTitle(), this.getSubTitle(), this.getGameMode(), this.getPlayType(), this.getStart(), this.getBack());
	}
	
	public void startGameHandler(ActionEvent event) {
		Game.newInstance(this.getBoardSize(), this.getGameModeMap().get(this.getGameMode().getValue()),this.getPlayTypeMap().get(this.getPlayType().getValue()));
		GameScreen screen = new GameScreen(Main.getScreenWidth(), Main.getScreenHeight());
		Game.getInstance().setBoard(screen.getBoard());
		Game.getInstance().setPanel(screen.getPanel());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(screen);
		stage.setScene(scene);
		stage.show();
	}
	
	private Button createStartButton() {
		Button btn = new Button();
		btn.setText("Start Game");
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
		btn.setOnAction(e -> startGameHandler(e));
		return btn;
	}
	
	public void backToMenu(ActionEvent event) {
		Menu menu = new Menu(Main.getValidSizes());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(menu, Main.getScreenWidth(), Main.getScreenHeight());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	private Button createBackButton() {
		Button btn = new Button();
		btn.setText("Back Game");
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
		btn.setOnAction(e -> backToMenu(e));
		return btn;
	}
	
	private ChoiceBox<String> createGameModeSelections() {
		ChoiceBox<String> cb = new ChoiceBox<String>();
		this.setGameModeMap(new HashMap<String, GameMode>());
		this.getGameModeMap().put("Classic", GameMode.Classic);
		if(this.getBoardSize() > 5) {
			this.getGameModeMap().put("Speed", GameMode.Speed);
		}
		cb.getItems().addAll(new ArrayList<String>(this.getGameModeMap().keySet()));
		cb.setValue(cb.getItems().get(0));
		cb.setPrefWidth(150);
		cb.setPrefHeight(40);
		return cb;
	}

	private ChoiceBox<String> createPlayTypeSelections() {
		ChoiceBox<String> cb = new ChoiceBox<String>();
		this.setPlayTypeMap(new HashMap<String, PlayType>());
		this.getPlayTypeMap().put("Player vs Player", PlayType.PlayerVsPlayer);
		this.getPlayTypeMap().put("Player vs Bot", PlayType.PlayperVsBot);
		cb.getItems().addAll(new ArrayList<String>(this.getPlayTypeMap().keySet()));
		cb.setValue(cb.getItems().get(0));
		cb.setPrefWidth(150);
		cb.setPrefHeight(40);
		return cb;
	}
	
	public Text getSubTitle() {
		return SubTitle;
	}

	public void setSubTitle(Text subTitle) {
		SubTitle = subTitle;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public ChoiceBox<String> getGameMode() {
		return gameMode;
	}

	public void setGameMode(ChoiceBox<String> gameMode) {
		this.gameMode = gameMode;
	}

	public ChoiceBox<String> getPlayType() {
		return playType;
	}

	public void setPlayType(ChoiceBox<String> playType) {
		this.playType = playType;
	}

	public Button getStart() {
		return start;
	}

	public void setStart(Button start) {
		this.start = start;
	}

	public HashMap<String, PlayType> getPlayTypeMap() {
		return playTypeMap;
	}

	public void setPlayTypeMap(HashMap<String, PlayType> playTypeMap) {
		this.playTypeMap = playTypeMap;
	}

	public HashMap<String, GameMode> getGameModeMap() {
		return gameModeMap;
	}

	public void setGameModeMap(HashMap<String, GameMode> gameModeMap) {
		this.gameModeMap = gameModeMap;
	}

	public Button getBack() {
		return back;
	}

	public void setBack(Button back) {
		this.back = back;
	}
}
