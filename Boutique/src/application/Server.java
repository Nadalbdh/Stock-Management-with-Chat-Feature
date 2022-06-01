package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Server {
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
public Server(ServerSocket serverSocket) {
	this.serverSocket=serverSocket;
	try {
		this.socket=serverSocket.accept();
		this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	} 
	catch (IOException e) {
		System.out.println("Error Creating Server");
		e.printStackTrace();
		closeEverything(socket,bufferedReader,bufferedWriter);
	}
}
public void sendMessageToClient(String messageToClient){
	try {
	bufferedWriter.write(messageToClient);
	bufferedWriter.newLine();
	bufferedWriter.flush();
	
}
	catch (IOException e) {
		e.printStackTrace();
		System.out.println("Error Sending Message to Cleint");
		closeEverything(socket,bufferedReader,bufferedWriter);
	}
}
public void recieveMessageFromClient(VBox vbox) {
	new Thread(new Runnable() {

		@Override
		public void run() {
			while(socket.isConnected()) {
				try {
				String messageFromClient=bufferedReader.readLine();
				SampleController.addLabel(messageFromClient, vbox);
			}
				catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error recieving message from cleint");
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
	