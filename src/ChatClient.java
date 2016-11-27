import javax.swing.*;

import java.io.*;
import java.net.*;
public class ChatClient extends JFrame implements Runnable
{
	Socket socket;
	JTextArea ta;
	
	Thread thread;
	DataInputStream din;
	DataOutputStream dout;
	
	String LoginName;
	
	ChatClient(String login) throws UnknownHostException, IOException
	{
		super(login);
		LoginName=login;
		
		ta=new JTextArea(18,50);
		
		socket=new Socket("localhost",5217);
		
		din=new DataInputStream(socket.getInputStream());
		dout= new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(LoginName);
		dout.writeUTF(LoginName+" "+"LOGIN");
		
		thread=new Thread(this);
		thread.start();
		setup();
		
		
	}
	private void setup() {
		// TODO Auto-generated method stub
		setSize(600,400);
		
		JPanel panel= new JPanel();
		
		panel.add(new JScrollPane(ta));
		add(panel);
		setVisible(true);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				ta.append("\n"+din.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		ChatClient client= new ChatClient("User2");
	}

}
