package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainController implements Initializable {

	public void ArticleVue(ActionEvent e) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ArticleView.fxml"));
		Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root,600,550);
		stage.setScene(scene);
		stage.show();
	}
	
	public void CategorieVue(ActionEvent e) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("CategorieView.fxml"));
		Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root,600,550);
		stage.setScene(scene);
		stage.show();
	}
	    
	@Override
	public void initialize(URL url, ResourceBundle arg) {
		// TODO Auto-generated method stub
	    //search_user();
		
	}
		
}