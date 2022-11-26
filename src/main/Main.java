package main;

import java.util.ArrayList;
import java.util.Arrays;

import gui.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int screenWidth = 1000;
	public static final int screenHeight = 600;
	public static final ArrayList<Integer> validSizes = new ArrayList<Integer>(Arrays.asList(3, 5, 7, 9, 11));
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Menu menu = new Menu(getValidSizes());
		primaryStage.setTitle("AlphaXO");
		primaryStage.setScene(new Scene(menu, getScreenWidth(), getScreenHeight()));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}
	
	public static ArrayList<Integer> getValidSizes() {
		return validSizes;
	}
	
	
}
