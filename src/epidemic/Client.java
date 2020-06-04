package epidemic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entities.agent;
import scenarios.Scenario;

/**
 * A simple Swing-based client for the chat server. Graphically it is a frame
 * with a text field for entering messages and a textarea to see the whole
 * dialog.
 *
 * The client follows the following Chat Protocol. When the server sends
 * "SUBMITNAME" the client replies with the desired screen name. The server will
 * keep sending "SUBMITNAME" requests as long as the client submits screen names
 * that are already in use. When the server sends a line beginning with
 * "NAMEACCEPTED" the client is now allowed to start sending the server
 * arbitrary strings to be broadcast to all chatters connected to the server.
 * When the server sends a line beginning with "MESSAGE" then all characters
 * following this string should be displayed in its message area.
 */
public class Client implements Runnable{

	String serverAddress;
	ObjectInputStream in;
	static ObjectOutputStream out;
	JFrame frame = new JFrame("Epidemic Text Chat");
	static JTextField textField = new JTextField(50);
	JTextArea messageArea = new JTextArea(16, 50);
	static boolean multiplayer = false;

	/**
	 * Constructs the client by laying out the GUI and registering a listener with
	 * the textfield so that pressing Return in the listener sends the textfield
	 * contents to the server. Note however that the textfield is initially NOT
	 * editable, and only becomes editable AFTER the client receives the
	 * NAMEACCEPTED message from the server.
	 */
	public Client(String serverAddress) {
		this.serverAddress = serverAddress;

		textField.setEditable(false);
		messageArea.setEditable(false);
		frame.getContentPane().add(textField, BorderLayout.SOUTH);
		frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
		frame.pack();

		// Send on enter then clear to prepare for next message
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] data = {"msg",textField.getText()};
				try {
					out.writeObject(data);
					textField.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	
	public static boolean stayConnected = true;
	public void run() {
		try {
			Socket socket = new Socket(serverAddress, 3220);
			multiplayer = true;
			System.out.print("Connected");
			in = new ObjectInputStream(socket.getInputStream());
			System.out.print("stream set");
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			System.out.print("Streams set");
			textField.setEditable(true);
			System.out.print("Editable");

			while (stayConnected) {
				try {
					Object[] line = (Object[]) in.readObject();
					if (((String)line[0]).equals("agent")) {
						agent.AgentHandler.add((agent)line[1]);
						System.out.print(agent.AgentHandler.get(agent.AgentHandler.size()-1).Diseases);
					} else if (((String)line[0]).equals("msg")) {
						messageArea.append((String)line[1]);
					} else if (((String)line[0]).equals("scenario")) {
						UIManager.startGame((Scenario)line[1]);
					} else if (((String)line[0]).equals("kick")) {
						stayConnected = false;
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			socket.close();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.print("IOException!");
			e1.printStackTrace();
		} finally {
			frame.setVisible(false);
			frame.dispose();
		}
	}

	public static void joinMulti(String sip) throws Exception {
		Thread client = new Thread(new Client(sip));
		client.start();
	}
}