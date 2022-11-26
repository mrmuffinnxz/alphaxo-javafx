package gui;

import java.util.ArrayList;

import bot.Bot;
import bot.Record;
import bot.StateSpaceRecord;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Game;
import main.Main;

public class Panel extends VBox {
	private ImageView logo;
	private Text gameText;
	private Text turnCount;
	private Button newGame;
	private Button exitGame;
	private Text botText;
	private ChoiceBox<String> useBot;
	private Button suggestMove;

	public Panel() {
		this.setLogo(GuiTool.createImageView("logo.png", 100, 100));
		this.setGameText(this.createText("AlphaXO Game", 32));
		this.setTurnCount(this.createText("Turn #0", 24));
		this.setNewGame(this.createNewGameButton());
		this.setExitGame(this.createExitGameButton());
		this.setBotText(this.createText("Recommend Move Bot", 24));
		this.setUseBot(this.createUseBotSelections());
		this.setSuggestMove(this.createSuggestMoveButton());
		this.setPrefWidth(Main.getScreenWidth());
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().addAll(this.getLogo(), this.getGameText(), this.getTurnCount(), this.getNewGame(),
				this.getExitGame(), this.getBotText(), this.getUseBot(), this.getSuggestMove());
	}

	private ChoiceBox<String> createUseBotSelections() {
		ChoiceBox<String> cb = new ChoiceBox<String>();
		cb.getItems().addAll(new ArrayList<String>(Game.getInstance().getBots().keySet()));
		cb.setValue(cb.getItems().get(0));
		return cb;
	}

	public void newGameHandler() {
		Game.getInstance().newGame();
		this.getGameText().setText("AlphaXO Game");
		this.getTurnCount().setText("Turn #0");
		this.getBotText().setText("Recommend Move Bot");
		for(Square s : Game.getInstance().getBoard().getSquares()) {
			s.reset();
		}
	}
	private Button createNewGameButton() {
		Button btn = new Button();
		btn.setText("New Game");
		btn.setOnAction(e -> newGameHandler());
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
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

	private Button createExitGameButton() {
		Button btn = new Button();
		btn.setText("Return to Menu");
		btn.setOnAction(e -> backToMenu(e));
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
		return btn;
	}

	private void suggestMoveHandler() {
		Bot bot = Game.getInstance().getBots().get(this.getUseBot().getValue());
		Record record = bot.getRecord(Game.getInstance().getCurrentBoardState());
		for (Square s : Game.getInstance().getBoard().getSquares()) {
			if(record.getMoveToPlay().contains(s.getPosition())) {
				Game.getInstance().getBoard().getSquares().get(s.getPosition()).fillBackground(bot.getColor());
			}else {
				Game.getInstance().getBoard().getSquares().get(s.getPosition()).drawCurrentState(s.getBaseColor());
			}
		}
		
		if(record instanceof StateSpaceRecord) {
			this.getBotText().setText(((StateSpaceRecord)record).getResultText());
		} else {
			this.getBotText().setText("Recommend Move Bot");
		}
	}
	
	private Button createSuggestMoveButton() {
		Button btn = new Button();
		btn.setText("Suggest Move");
		btn.setOnAction(e -> suggestMoveHandler());
		btn.setPrefWidth(100);
		btn.setPrefHeight(40);
		return btn;
	}

	private Text createText(String text, int fontSize) {
		Text txt = new Text();
		txt.setText(text);
		txt.setStyle("-fx-font: " + fontSize + " arial;");
		return txt;
	}

	public ImageView getLogo() {
		return logo;
	}

	public void setLogo(ImageView logo) {
		this.logo = logo;
	}

	public Text getGameText() {
		return gameText;
	}

	public void setGameText(Text gameText) {
		this.gameText = gameText;
	}

	public Text getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(Text turnCount) {
		this.turnCount = turnCount;
	}

	public Button getNewGame() {
		return newGame;
	}

	public void setNewGame(Button newGame) {
		this.newGame = newGame;
	}

	public Button getExitGame() {
		return exitGame;
	}

	public void setExitGame(Button exitGame) {
		this.exitGame = exitGame;
	}

	public Text getBotText() {
		return botText;
	}

	public void setBotText(Text botText) {
		this.botText = botText;
	}

	public ChoiceBox<String> getUseBot() {
		return useBot;
	}

	public void setUseBot(ChoiceBox<String> useBot) {
		this.useBot = useBot;
	}

	public Button getSuggestMove() {
		return suggestMove;
	}

	public void setSuggestMove(Button suggestMove) {
		this.suggestMove = suggestMove;
	}

}
