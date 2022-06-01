package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import boutique.Article;

public class articleController implements Initializable {
	public String query;
	
    @FXML
    private TextField idAField;
    
    @FXML
    private TextField marqueField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField prixField;

    @FXML
    private ComboBox comboBox;
    
    @FXML
    private TextField taillePointureField;

    @FXML
    private Button ajouter;

    @FXML
    private Button mettreAJour;

    @FXML
    private Button supprimer;

    @FXML
    private TableView TableView;
    
    @FXML
    private TableColumn<Article, Integer> idAColumn;
    
    @FXML
    private TableColumn<Article, String> marqueColumn;

    @FXML
    private TableColumn<Article, Integer> stockColumn;

    @FXML
    private TableColumn<Article, Float> prixColumn;

    @FXML
    private TableColumn<Article, String> categoryColumn;

    @FXML
    private TableColumn<Article, Integer> taillePointureColumn;
    
    @FXML
    private void ajouter() throws ClassNotFoundException, SQLException, IllegalArgumentException {
    	query = "insert into article values("+idAField.getText()+
    			",'"+marqueField.getText()+"',"+stockField.getText()+","+prixField.getText()+
    			",'"+comboBox.getValue()+"',"+taillePointureField.getText()+")";
    	executeQuery(query);
    	showArticle();
    }
    
    
    @FXML 
    private void mettreAJour() throws ClassNotFoundException, SQLException {
    String query = "UPDATE article SET marque='"+marqueField.getText()+"', stock="+stockField.getText()+", prix="+prixField.getText()+", notes='"+comboBox.getValue()+
    		"', tailleOuPointure="+taillePointureField.getText()+" WHERE idA="+idAField.getText()+"";
    executeQuery(query);
	showArticle();
    }
    
    @FXML
    private void supprimer() throws ClassNotFoundException, SQLException {
    	String query = "DELETE FROM article WHERE idA ="+idAField.getText()+"";
    	executeQuery(query);
    	showArticle();
    }
    
	public void CategorieVue(ActionEvent e) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("CategorieView.fxml"));
		Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root,400,600);
		stage.setScene(scene);
		stage.show();
	}

	public void ServerView(ActionEvent e) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ServerView.fxml"));
		Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root,478,396);
		stage.setScene(scene);
		stage.show();
	}
    public void executeQuery(String query) {
    	Connection conn = getConnection();
    	Statement st;
    	try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
			showArticle();
	        try {
	            Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/Boutique?characterEncoding=UTF-8&elideSetAutoCommits=true&enabledTLSProtocols=TLSv1.2","root","adminnn");
	            ResultSet rs = conn.createStatement().executeQuery("SELECT nom FROM categorie;");
		        while (rs.next()) {  // loop
		            comboBox.getItems().addAll(rs.getString("nom")); 
		       }
	        }    	
	        catch (Exception e){
	    		e.printStackTrace();
	    		return;
	    	}
    }
    
    public Connection getConnection() {
    	Connection conn;
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Boutique?characterEncoding=UTF-8&elideSetAutoCommits=true&enabledTLSProtocols=TLSv1.2","root","adminnn");
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public ObservableList<Article> getarticleList(){
    	ObservableList<Article> data = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM article ";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
	    	  while (rs.next()) {
	    	      Article article = new Article();
	    	      article.idA.set(rs.getInt("idA"));
	    	      article.marque.set(rs.getString("marque"));
	    	      article.stock.set(rs.getInt("stock"));
	    	      article.prix.set(rs.getInt("prix"));
	    	      article.category.set(rs.getString("notes"));
	    	      article.tailleOuPointure.set(rs.getInt("tailleOuPointure"));
	    	      data.add(article);
	    	  }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return (ObservableList<Article>) data;
    }
    
    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showArticle() {
    	ObservableList<Article> list = getarticleList();
    	
    	idAColumn.setCellValueFactory(new PropertyValueFactory<Article,Integer>("idA"));
    	marqueColumn.setCellValueFactory(new PropertyValueFactory<Article,String>("marque"));
    	stockColumn.setCellValueFactory(new PropertyValueFactory<Article,Integer>("stock"));
    	prixColumn.setCellValueFactory(new PropertyValueFactory<Article,Float>("prix"));
    	categoryColumn.setCellValueFactory(new PropertyValueFactory<Article,String>("category"));
    	taillePointureColumn.setCellValueFactory(new PropertyValueFactory<Article,Integer>("tailleOuPointure"));

    	
    	TableView.setItems(list);
    }

}
