package application;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SampleController implements Initializable {
	@FXML
	private Button button_send;
	@FXML
	private TextField tf_message;
	@FXML
	private VBox vbox_messages;
	@FXML
	private ScrollPane sp_main;
	
	
	private Client client;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			client= new Client(new Socket("localhost",2044));
			System.out.println("Connected to Server");
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error Connecting to Server");
		}
		
		vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sp_main.setVvalue((Double) newValue);
				
			}
			
		});				
		client.recieveMessageFromServer(vbox_messages);
		button_send.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				String messageToSend=tf_message.getText();
				if(!messageToSend.isEmpty()) {
					HBox hBox=new HBox();
					hBox.setAlignment(Pos.CENTER_RIGHT);
					hBox.setPadding(new Insets(5,5,5,10));
					
					
					Text text=new Text(messageToSend);
					TextFlow textFlow=new TextFlow(text);
					textFlow.setStyle("-fx-color:#EEEDFF;"+"-fx-background-color:#929BF8;"+"-fx-background-radius:20px;");
					text.setFill(Color.color(0.943,0.945,0.996));
					
					
					
					hBox.getChildren().add(textFlow);
					vbox_messages.getChildren().add(hBox);
					
					
					client.sendMessageToServer(messageToSend);
					tf_message.clear();
					
					
					
				}
				
			}
			
		});
		
		}
				
	public static void addLabel(String messageFromServer,VBox vbox) {
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(5,5,5,10));
		
		Text text=new Text(messageFromServer);
		TextFlow textFlow= new TextFlow(text);
		textFlow.setStyle("-fx-bachground-color:#E9E9EB;"+"-fx-background-radius:20px;");
		hBox.getChildren().add(textFlow);
		
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				vbox.getChildren().add(hBox);
				
			}
			
		});
	}		
	
	
	
	
	
	
	}
	

