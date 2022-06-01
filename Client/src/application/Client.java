package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client {
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public Client(Socket socket) {
		
		try {
			this.socket=socket;
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} 
		catch (IOException e) {
			System.out.println("Error Creating Client");
			e.printStackTrace();
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
	}
	
	public void sendMessageToServer(String messageToServer){
		try {
		bufferedWriter.write(messageToServer);
		bufferedWriter.newLine();
		bufferedWriter.flush();
		
	}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Sending Message to Server");
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
	}
	
	
	
	public void recieveMessageFromServer(VBox vbox) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(socket.isConnected()) {
					try {
					String messageFromServer=bufferedReader.readLine();
					SampleController.addLabel(messageFromServer, vbox);
					
				}
					catch (IOException e) {
						e.printStackTrace();
						System.out.println("Error Recieving Message from Cleint");
						closeEverything(socket,bufferedReader,bufferedWriter);
						break;
					}
					
				}
				
			}
			
		}).start();
	}
	
	
	
	
	
	
	
	
	
	
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
		try {
			if(socket!=null) {
				socket.close();
			}
			if(bufferedReader!=null) {
				bufferedReader.close();
			}
			if(bufferedWriter!=null) {
				bufferedWriter.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
