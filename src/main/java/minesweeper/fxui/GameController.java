package minesweeper.fxui;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class GameController {
	
	
	@FXML
	Button reset;
	@FXML
	GridPane grid = new GridPane();
	@FXML
	AnchorPane pane = new AnchorPane();
	@FXML
	StackPane stackPane = new StackPane();
	@FXML
	Button saveButton;
	@FXML
	Button loadButton;
	@FXML
	TextField saveField;
	@FXML
	TextField loadField;
	@FXML 
	Text saveFileNotFoundMsg;
	@FXML
	Text loadFileNotFoundMsg;
	

	private Game game = new Game(10,10,20);
	private SaveHandler saveHandler = new SaveHandler();
	private int countClicks = 0;
	

	
@FXML
public void resetGame() {
	countClicks = 0;
	game.reset(game, game.getWidth(), game.getHeight(),game.getNumberOfBombs());
	initialize();
}

@FXML
public void initialize() {

//	System.out.println(game);	

	if (game.getNumberOfBombs() >= game.getHeight()*game.getWidth()) {
//		Button button = new Button();
//		button.setMinHeight(500);
//		button.setMinWidth(500);
//		button.setText("It is not possible to play with " + game.getHeight()*game.getWidth() + " bombs");
//		grid.getChildren().add(button);
		throw new IllegalArgumentException("It is not possible to play with " + game.getHeight()*game.getWidth() + " bombs");
		
	}

	if (game.getNumberOfBombs() < game.getHeight()*game.getWidth()) {
		grid.getChildren().clear();
		try {
			reset.setOnAction((event) -> resetGame());
			saveFileNotFoundMsg.setVisible(false);
			loadFileNotFoundMsg.setVisible(false);
			saveButton.setOnAction((event) -> saveGame());
			loadButton.setOnAction((event) -> loadGame());
			buttons();
			}catch (Exception e) {
				e.printStackTrace();
}
}
}
public void buttons()	{
	grid.getChildren().clear();
	for (int i = 0; i < game.getWidth(); i++) {
		for (int j = 0; j < game.getHeight(); j++) {
			Button button = new Button();
			setButtons(button);
			final int x = i;
			final int y = j;
			if(game.getTile(x, y).isDisable()) {
				button.setDisable(true);                         //"snur" buttonen slik at verdien til korespondernde tile viser
				button.setText(game.getTile(x, y).toString());   //hvor vi her setter verdien til en String på buttonen.
			}
			button.setOnAction((event) -> action(button,x,y,countClicks));
			grid.add(button, i, j);	
	}
}
}
public void setButtons(Button button) {
	button.setMinHeight(10);
	button.setMinWidth(10);
	button.prefHeightProperty().bind(stackPane.heightProperty());  //legger buttonen på StackPanet
	button.prefWidthProperty().bind(stackPane.widthProperty());
	
}

public void action(Button button, int i, int j, int countNumberofClick) {
	if (countNumberofClick==0) {
		firstClick(button,i,j);
	}
	if (game.getTile(i, j).isAir()) {
		revealTiles(button,i,j);
	}
	button.setDisable(true);
	button.setText(game.getTile(i, j).toString());
	game.getTile(i, j).setDisable();
	checkIfGameWon(grid);
	
	if(game.getTile(i, j).getType() == '*') {
		game.setGameOver();
		grid.getChildren().clear();
		
		for (int x = 0; x < game.getWidth(); x++) {
			for (int y = 0; y < game.getHeight(); y++) {
				Button button2 = new Button();
				setButtons(button2);
				button2.setText(game.getTile(x, y).toString());
				button2.setDisable(true);
				grid.add(button2, x, y);
			}	
		}
	}
}
public void firstClick(Button button, int i, int j) {

	while (game.getTile(i, j).getType()=='*') {
		resetGame();
		button.fire();
	}
	revealTiles(button,i,j);
}

public boolean checkIfGameWon(GridPane grid) {
	int count = 0;
	for (int x = 0; x < game.gameToList().size(); x++) {

		if(grid.getChildren().get(x).isDisabled()) {
			 count++;
		 }
	}
	if(count == (game.getHeight()*game.getWidth())-game.getNumberOfBombs()) {
				game.setGameWon();
				for (int i = 0; i < grid.getChildren().size(); i++) {
					grid.getChildren().get(i).setDisable(true);
				}
				Button button = new Button();
				button.setMinHeight(578);
				button.setMinWidth(575);
				button.setText("Congratulations you have won the game!");
				button.setOnAction((event) -> button.setVisible(false));
				button.setOnAction((event) -> pane.getChildren().remove(button));
				button.setOnAction((event) -> resetGame());
				grid.getChildren().add(button);
				return true;
			}
	return false;
	}
	


public void saveGame() {
	try {
		saveHandler.save(game);
		saveFileNotFoundMsg.setVisible(false);
 
} catch (FileNotFoundException e) {
	saveFileNotFoundMsg.setText("File not found");
	saveFileNotFoundMsg.setVisible(true);
	
}
	
}

public void loadGame() { 
	try {
		saveHandler.load(game);
		loadFileNotFoundMsg.setVisible(false);
	} catch (FileNotFoundException e) {
		loadFileNotFoundMsg.setVisible(true);
	}
	grid.getChildren().clear();
	for (int i = 0; i < game.getHeight(); i++) {
		 for (int j = 0; j < game.getWidth(); j++) {
			 Button button = new Button();
				setButtons(button);
				final int x = i;
				final int y = j;

				if(game.getTile(x, y).isDisable()){
					button.setDisable(true);
					button.setText(game.getTile(x, y).toString());
				}
				else {
					button.setOnAction((event) -> action(button,x,y,countClicks));
				}

				grid.add(button, i, j);
		}
		 countClicks++;		
		 }
	}

public void revealTiles(Button button, int i, int j) {
	((Labeled) grid.getChildren().get((i*game.getHeight())+j)).setText((game.getTile(i, j).toString()));
	grid.getChildren().get((i*game.getHeight())+j).setDisable(true);
	game.getTile(i, j).setDisable();
	
	if(game.isTile(i, j+1)) {
		if(!game.getTile(i, j+1).isMine()) {
			((Labeled) grid.getChildren().get((i*game.getHeight())+j+1)).setText((game.getTile(i, j+1).toString()));
			grid.getChildren().get((i*game.getHeight())+j+1).setDisable(true);
			game.getTile(i, j+1).setDisable();

		}
	}
	if(game.isTile(i, j-1)) {
		if(!game.getTile(i, j-1).isMine()) {
			((Labeled) grid.getChildren().get((i*game.getHeight())+j-1)).setText((game.getTile(i, j-1).toString()));
			grid.getChildren().get((i*game.getHeight())+j-1).setDisable(true);
			game.getTile(i, j-1).setDisable();

		}
	}
	
	if(game.isTile(i+1, j)) {
		if(!game.getTile(i+1, j).isMine()) {
			((Labeled) grid.getChildren().get(((i+1)*game.getHeight())+j)).setText((game.getTile(i+1, j).toString()));
			grid.getChildren().get(((i+1)*game.getHeight()+j)).setDisable(true);
			game.getTile(i+1, j).setDisable();

		}
	}
	
	
	if(game.isTile(i+1, j+1)) {
		if(!game.getTile(i+1, j+1).isMine()) {
			((Labeled) grid.getChildren().get(((i+1)*game.getHeight())+j+1)).setText((game.getTile(i+1, j+1).toString()));
			grid.getChildren().get(((i+1)*game.getHeight()+j+1)).setDisable(true);
			game.getTile(i+1, j+1).setDisable();

		}
	}
	
	if(game.isTile(i+1, j-1)) {
		if(!game.getTile(i+1, j-1).isMine()) {
			((Labeled) grid.getChildren().get(((i+1)*game.getHeight())+j-1)).setText((game.getTile(i+1, j-1).toString()));
			grid.getChildren().get(((i+1)*game.getHeight()+j-1)).setDisable(true);
			game.getTile(i+1, j-1).setDisable();

		}
	}
	
	if(game.isTile(i-1, j)) {
		if(!game.getTile(i-1, j).isMine()) {
			((Labeled) grid.getChildren().get(((i-1)*game.getHeight())+j)).setText((game.getTile(i-1, j).toString()));
			grid.getChildren().get(((i-1)*game.getHeight()+j)).setDisable(true);
			game.getTile(i-1, j).setDisable();

		}
	}
	
	if(game.isTile(i-1, j+1)) {
		if(!game.getTile(i-1, j+1).isMine()) {
			((Labeled) grid.getChildren().get(((i-1)*game.getHeight())+j+1)).setText((game.getTile(i-1, j+1).toString()));
			grid.getChildren().get(((i-1)*game.getHeight()+j+1)).setDisable(true);
			game.getTile(i-1, j+1).setDisable();

		}
	}
	
	if(game.isTile(i-1, j-1)) {
		if(!game.getTile(i-1, j-1).isMine()) {
			((Labeled) grid.getChildren().get(((i-1)*game.getHeight())+j-1)).setText((game.getTile(i-1, j-1).toString()));
			grid.getChildren().get(((i-1)*game.getHeight()+j-1)).setDisable(true);
			game.getTile(i-1, j-1).setDisable();

		}
	}
	countClicks++;
}
}
	
