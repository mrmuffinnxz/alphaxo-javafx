package logic;

import java.util.ArrayList;
import java.util.HashMap;

import bot.Bot;
import bot.FutureBot;
import bot.ParinyaBot;
import bot.RandomBot;
import gui.Board;
import gui.GameMode;
import gui.Panel;
import gui.PlayType;
import gui.Square;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

public class Game {
	private static Game instance;
	private ArrayList<ArrayList<Integer>> winState;
	private ArrayList<Integer> xSquare;
	private ArrayList<Integer> oSquare;
	private int boardSize;
	private int turn;
	private boolean isGameEnd;
	private State winner;
	private State currentTurn;
	private Board board;
	private Panel panel;
	private HashMap<String, Bot> bots;

	private PlayType playType;
	private GameMode gameMode;

	public Game(int boardSize, GameMode gameMode, PlayType playType) {
		this.setBoardSize(boardSize);
		this.setGameMode(gameMode);
		this.setPlayType(playType);
		this.setWinState(this.getGameMode() == GameMode.Speed ? this.generateSpeedWinState() : this.generateWinState());
		this.createBots();
		this.newGame();
	}

	public static void newInstance(int boardSize, GameMode gameMode, PlayType playType) {
		instance = new Game(boardSize, gameMode, playType);
	}

	public static Game getInstance() {
		return instance;
	}

	private void checkValidBot(Bot bot) {
		if (this.getBoardSize() <= bot.getBoardSizeLimit() || (this.getGameMode() == GameMode.Speed && bot.isSpeedModeEnable())) {
			this.getBots().put(bot.getName(), bot);
		}
	}

	private void createBots() {
		this.setBots(new HashMap<String, Bot>());

		this.checkValidBot(new RandomBot());
		this.checkValidBot(new FutureBot());
		this.checkValidBot(new ParinyaBot());
	}

	public void updateBoard() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Square s : Game.getInstance().getBoard().getSquares()) {
					Runnable updater = new UpdateBoardState(s);
					Platform.runLater(updater);
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
	}

	public void checkGameEnd(State player) {
		ArrayList<Integer> playerSquare = (player == State.X) ? this.getxSquare() : this.getoSquare();
		for (ArrayList<Integer> win : this.getWinState()) {
			ArrayList<Integer> winStateCopy = new ArrayList<Integer>(win);
			winStateCopy.removeAll(playerSquare);
			if (winStateCopy.size() == 0) {
				this.setWinner(player);
				this.getBoard().drawWinSquare(win);
				this.setGameEnd(true);
				return;
			}
		}
		if (this.getTurn() == this.getBoardSize() * this.getBoardSize()) {
			this.setWinner(State.EMPTY);
			this.setGameEnd(true);
		}
	}

	public void updateGameState(State player, int position) {
		this.updateBoard();

		ArrayList<Integer> playerSquare = (player == State.X) ? this.getxSquare() : this.getoSquare();
		playerSquare.add(position);

		this.setCurrentTurn((this.getTurn() % 2 == 0) ? State.O : State.X);
		this.setTurn(this.getTurn() + 1);

		this.getPanel().getGameText().setText(((this.getCurrentTurn() == State.X) ? "X" : "O") + "'s turn to play");
		this.getPanel().getTurnCount().setText("Turn #" + Game.getInstance().getTurn());
		this.getPanel().getBotText().setText("Recommend Move Bot");

		this.checkGameEnd(player);

		if (this.isGameEnd()) {
			this.getPanel().getTurnCount().setText("Game Ended");
			if (this.getWinner() == State.EMPTY) {
				this.getPanel().getGameText().setText("Draw");
			} else {
				if (this.getWinner() == State.X) {
					this.getPanel().getGameText().setText("X win");
				} else {
					this.getPanel().getGameText().setText("O win");
				}
			}
			Runnable updater = new Runnable() {
				@Override
				public void run() {
					ButtonType newGame = new ButtonType("New Game", ButtonBar.ButtonData.OK_DONE);
					ButtonType exitGame = new ButtonType("Exit Game", ButtonBar.ButtonData.CANCEL_CLOSE);
					Alert alert = new Alert(AlertType.CONFIRMATION,
							Game.getInstance().getPanel().getGameText().getText(), newGame, exitGame);
					alert.setTitle("Result");
					alert.setHeaderText(null);
					alert.showAndWait();
					if (alert.getResult() == newGame) {
						Game.getInstance().getPanel().newGameHandler();
					} else {
						Platform.exit();
					}
				}
			};
			Platform.runLater(updater);
		} else if (this.getPlayType() == PlayType.PlayperVsBot && this.getCurrentTurn() == State.O) {
			Bot bot = this.getBots().get(this.getPanel().getUseBot().getValue());
			int move = bot.getBestMove(this.getCurrentTurn(), this.getCurrentBoardState());
			this.getBoard().getSquares().get(move).onClickHandler();
		}
	}

	public int indexTo1D(int i, int j) {
		return i * this.getBoardSize() + j;
	}

	public void newGame() {
		this.setWinner(null);
		this.setGameEnd(false);
		this.setxSquare(new ArrayList<Integer>());
		this.setoSquare(new ArrayList<Integer>());
		this.setCurrentTurn(State.X);
		this.setTurn(0);
	}

	public ArrayList<Integer> getValidMoves(char[] boardState) {
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for (int i = 0; i < this.getBoardSize() * this.getBoardSize(); i++) {
			if (boardState[i] == '-') {
				validMoves.add(i);
			}
		}
		return validMoves;
	}

	public ArrayList<Integer> getPlayerSquareFromBoardState(State player, char[] boardState) {
		char playerChar = (player == State.X) ? 'X' : 'O';
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < boardState.length; i++) {
			if (boardState[i] == playerChar) {
				positions.add(i);
			}
		}
		return positions;
	}

	public char[] getCurrentBoardState() {
		char[] stateChars = new char[this.getBoardSize() * this.getBoardSize()];
		for (int i = 0; i < this.getBoardSize() * this.getBoardSize(); i++) {
			if (this.getxSquare().contains(i)) {
				stateChars[i] = 'X';
			} else if (this.getoSquare().contains(i)) {
				stateChars[i] = 'O';
			} else {
				stateChars[i] = '-';
			}
		}
		return stateChars;
	}

	private ArrayList<ArrayList<Integer>> generateSpeedWinState() {
		ArrayList<ArrayList<Integer>> winState = new ArrayList<ArrayList<Integer>>();

		for (int x = 0; x < this.getBoardSize() - 4; x++) {
			for (int y = 0; y < this.getBoardSize() - 4; y++) {
				ArrayList<Integer> diagonal1 = new ArrayList<Integer>();
				ArrayList<Integer> diagonal2 = new ArrayList<Integer>();
				for (int i = x; i < x + 5; i++) {
					ArrayList<Integer> row = new ArrayList<Integer>();
					ArrayList<Integer> col = new ArrayList<Integer>();
					for (int j = y; j < y + 5; j++) {
						row.add(this.indexTo1D(i, j));
						col.add(this.indexTo1D(j, i));
						if (i-x == j-y) {
							diagonal1.add(this.indexTo1D(i, j));
							diagonal2.add(this.indexTo1D(i, y+4-i+x));
						}
					}
					winState.add(row);
					winState.add(col);
				}
				winState.add(diagonal1);
				winState.add(diagonal2);
			}

		}
		
		return winState;
	}

	private ArrayList<ArrayList<Integer>> generateWinState() {
		ArrayList<ArrayList<Integer>> winState = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> diagonal1 = new ArrayList<Integer>();
		ArrayList<Integer> diagonal2 = new ArrayList<Integer>();
		for (int i = 0; i < this.getBoardSize(); i++) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			ArrayList<Integer> col = new ArrayList<Integer>();
			for (int j = 0; j < this.getBoardSize(); j++) {
				row.add(this.indexTo1D(i, j));
				col.add(this.indexTo1D(j, i));
				if (i == j) {
					diagonal1.add(this.indexTo1D(i, j));
					diagonal2.add(this.indexTo1D(i, this.getBoardSize() - j - 1));
				}
			}
			winState.add(row);
			winState.add(col);
		}
		winState.add(diagonal1);
		winState.add(diagonal2);

		return winState;
	}

	public HashMap<String, Bot> getBots() {
		return bots;
	}

	public void setBots(HashMap<String, Bot> bots) {
		this.bots = bots;
	}

	public boolean isGameEnd() {
		return isGameEnd;
	}

	public void setGameEnd(boolean isGameEnd) {
		this.isGameEnd = isGameEnd;
	}

	public State getWinner() {
		return winner;
	}

	public void setWinner(State winner) {
		this.winner = winner;
	}

	public static void setInstance(Game instance) {
		Game.instance = instance;
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

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public ArrayList<ArrayList<Integer>> getWinState() {
		return winState;
	}

	public void setWinState(ArrayList<ArrayList<Integer>> winState) {
		this.winState = winState;
	}

	public ArrayList<Integer> getxSquare() {
		return xSquare;
	}

	public void setxSquare(ArrayList<Integer> xSquare) {
		this.xSquare = xSquare;
	}

	public ArrayList<Integer> getoSquare() {
		return oSquare;
	}

	public void setoSquare(ArrayList<Integer> oSquare) {
		this.oSquare = oSquare;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public State getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(State currentTurn) {
		this.currentTurn = currentTurn;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

}
