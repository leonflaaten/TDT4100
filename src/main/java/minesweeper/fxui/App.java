package minesweeper.fxui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{   

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/MainUI.fxml"));
        stage.setTitle("Minesweeper");
      
        Scene scene = new Scene(parent,578,575);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
	}

        
	public static void main(String[] args) { 
		App.launch(args);
		
	}
}