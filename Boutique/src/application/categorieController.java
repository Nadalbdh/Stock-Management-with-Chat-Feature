package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import boutique.Categorie;
import boutique.Categorie;
import boutique.Categorie;





public class categorieController implements Initializable {

    @FXML
    private TextField idCField;
    
    @FXML
    private TextField nomField;

    @FXML
    private TextField notesField;

    @FXML
    private Button ajouter;

    @FXML
    private Button mettreAJour;

    @FXML
    private Button supprimer;

    @FXML
    private TableView<Categorie> TableView;
    
    @FXML
    private TableColumn<Categorie, Integer> idCColumn;
    
    @FXML
    private TableColumn<Categorie, String> nomColumn;

    @FXML
    private TableColumn<Categorie, String> notesColumn;
    
    @FXML
    private void ajouter() throws ClassNotFoundException, SQLException {
    	String query = "insert into categorie values("+idCField.getText()+",'"+nomField.getText()+"','"+notesField.getText()+"')";
    	executeQuery(query);
    	showCategorie();
    }
    
    
    @FXML 
    private void mettreAJour() throws ClassNotFoundException, SQLException {
    String query = "UPDATE categorie SET nom='"+nomField.getText()+"',notes='"+notesField.getText()+"' WHERE idC="+idCField.getText()+"";
    executeQuery(query);
	showCategorie();
    }
    
    @FXML
    private void supprimer() throws ClassNotFoundException, SQLException {
    	String query = "DELETE FROM categorie WHERE idC ="+idCField.getText()+"";
    	executeQuery(query);
    	showCategorie();
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
		showCategorie();
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
    
    public ObservableList<Categorie> getcategorieList(){
    	ObservableList<Categorie> data = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM categorie ";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
	    	  while (rs.next()) {
	    	      Categorie categorie = new Categorie();
	    	      categorie.idC.set(rs.getInt("idC"));
	    	      categorie.nom.set(rs.getString("nom"));
	    	      categorie.notes.set(rs.getString("notes"));
	    	      data.add(categorie);
	    	  }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return (ObservableList<Categorie>) data;
    }
    
    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showCategorie() {
    	ObservableList<Categorie> list = getcategorieList();
    	
    	idCColumn.setCellValueFactory(new PropertyValueFactory<Categorie,Integer>("idC"));
    	nomColumn.setCellValueFactory(new PropertyValueFactory<Categorie,String>("nom"));
    	notesColumn.setCellValueFactory(new PropertyValueFactory<Categorie,String>("notes"));
    	TableView.setItems(list);
    }

}

