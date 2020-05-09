package epidemic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import scenarios.Scenario;

public class Server {

	// The set of all the print writers for all the clients, used for broadcast.
	private static ArrayList<ObjectOutputStream> writers = new ArrayList<>();
	public static Scenario scenario;
	public static JFrame frame;
	static JEditorPane logs;
	
	public static enum logType {
		msg,
		join,
		leave,
		data,
		info
	}

	public static void log(String msg, logType t) {
		String temp = logs.getText()+"\n";
		if(t == logType.msg) {
			temp+=msg;
		} else if(t == logType.join) {
			temp+="<p style='color:green;'>"+msg+"</p>";
		} else if(t == logType.leave) {
			temp+="<p style='color:red;'>"+msg+"</p>";
		} else if(t == logType.info) {
			temp+="<p style='color:blue;'>"+msg+"</p>";
		} else if(t == logType.data) {
			temp+="<p style='color:grey;'>"+msg+"</p>";
		} 
		logs.setText(temp);
		logs.repaint();
		frame.repaint();
	}
	
	public static void startserver() {
		frame = new JFrame("Epidemic Host Settings");
		frame.setSize(1000, 500);
		frame.setLocation(100, 100);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane hostsettings = new JTabbedPane();
		hostsettings.addTab("Server Settings", new JPanel());
		hostsettings.addTab("User Settings", new JScrollPane());
		logs = new JEditorPane();
		logs.setContentType("text/html");
		logs.setEditable(false);
		logs.setText("test");
		hostsettings.addTab("Logs", new JScrollPane(logs));
		frame.setContentPane(hostsettings);
		frame.setVisible(true);
		scenario = new scenarios.Test();
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				log("Server Starting...",logType.info);
				ExecutorService pool = Executors.newFixedThreadPool(500);
				try (ServerSocket listener = new ServerSocket(3220)) {
					while (true) {
						pool.execute(new Handler(listener.accept()));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	/**
	 * The client handler task.
	 */
	private static class Handler implements Runnable {
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(socket.getInputStream());

				log(socket.getInetAddress()+" has joined",logType.join);
				Object[] data = {"scenario",scenario};
				out.writeObject(data);
				out.flush();
				writers.add(out);

				// Accept messages from this client and broadcast them.
				while (true) {
					Object[] input = (Object[]) in.readObject();
					if (((String)input[0]).equals("msg")) {
						log(socket.getInetAddress()+": "+input[1],logType.msg);
						input[1] = socket.getInetAddress()+": "+input[1]+"\n";
						
						for (ObjectOutputStream writer : writers) {
							writer.writeObject(input);
							writer.flush();
						}
					} else if (((String)input[0]).equals("agent")) {
						int wn = (int)(Math.random()*writers.size());
						writers.get(wn).writeObject(input);
						writers.get(wn).flush();
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (out != null) {
					writers.remove(out);
					log(socket.getInetAddress() + " has left",logType.leave);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}