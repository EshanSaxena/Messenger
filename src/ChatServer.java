import java.net.*;
import java.util.Vector;
import java.io.DataInputStream;
import java.io.DataOutputStream; 
import java.io.IOException;
import java.util.StringTokenizer;

public class ChatServer
	{
		static Vector ClientSockets;  //Vector class implements a growable array of objects
		static Vector LoginNames;//will allow multiple clients and multiple login names
		
		ChatServer() throws IOException
		{
			ServerSocket server =new ServerSocket(5217);//ServerSocket class contain a constructor used to create a separate port number to run the server program.
			ClientSockets=new Vector();//initialising vector
			LoginNames=new Vector();//initialising vector
			
			while(true)
			{
				Socket client = server.accept();//establishes connection   
				AcceptClient acceptClient= new AcceptClient(client);
			}
		}
		
		public static void main(String args[]) throws IOException
		{
			ChatServer server=new ChatServer();
		}
		
		class AcceptClient extends Thread
			{
			Socket ClientSocket;
			
			//InputStream is used for reading, OutputStream for writing. They are connected as decorators to one another such that you can read/write
			//all different types of data from all different types of sources.
			
			DataInputStream din;//Returns the input stream of the socket. The input stream is connected to the output stream of the remote socket.
			DataOutputStream dout; //Returns the output stream of the socket. The output stream is connected to the input stream of the remote socket.
			AcceptClient(Socket client) throws IOException
			{
				ClientSocket=client;
				//An application uses a data output stream to write data that can later be read by a data input stream.
				din= new DataInputStream(ClientSocket.getInputStream());
				dout= new DataOutputStream(ClientSocket.getOutputStream());
				
				String LoginName= din.readUTF();
				LoginNames.add(LoginName);
				ClientSockets.add(ClientSocket);
				
				start();
			}
			
			public void run()
			{
				while(true)
				{
					try
					{
						String msgfromClient=din.readUTF();
						StringTokenizer st= new StringTokenizer(msgfromClient);
						String LoginName=st.nextToken();
						String MsgType=st.nextToken();
						int i;
						for( i =0;i<LoginNames.size();i++)
						{
							Socket psocket=(Socket) ClientSockets.elementAt(i);
							DataOutputStream pOut= new DataOutputStream(psocket.getOutputStream());
							pOut.writeUTF(LoginName+" has logged in");
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}