package epidemic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Vector;
import java.util.jar.JarFile;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import scenarios.City;
import scenarios.Olmsted;
import scenarios.Scenario;
import scenarios.Test;

public class Launcher {

	static JFrame launcher;
	
	
	public static Thread lt;
	
	public static final int version = 0;
    //private final static String URL = "http://epidemic.tfinnm.tk/updates"; //production
	private final static String URL = "http://localhost/update"; //testing

	public static void main(String[] args) {
		loadPluggins();
		launcher = new JFrame("Epidemic Game Launcher");
		launcher.setSize(1000, 500);
		launcher.setLocation(100, 100);
		launcher.setResizable(true);
		
		launcher.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel SPcontent = new JPanel(new BorderLayout());
		JPanel MPcontent = new JPanel(new BorderLayout());
		JEditorPane updatestext = new JEditorPane();
        updatestext.setContentType("text/html");
		updatestext.setEditable(false);
//		try {
//			updatestext.setText(getData(URL));
//		} catch (Exception e) {
//			updatestext.setText("Unable to retreive updates...");
//		}
		JScrollPane updatescroll = new JScrollPane(updatestext);
		JTabbedPane lc = new JTabbedPane();
		lc.addTab("Single Player", SPcontent);
		//lc.addTab("Multiplayer", MPcontent);
		lc.addTab("News & Updates", updatescroll);
		JButton host = new JButton("Host a server");
		JCheckBox dedicated = new JCheckBox("Host Dedicated");
		JButton dc = new JButton("Direct Connect");
		host.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Server.startserver();
					if (!dedicated.isSelected()) {
						Client.joinMulti("localhost");
					} 
					launcher.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		dc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Client.joinMulti(JOptionPane.showInputDialog(null, "Enter Server IP", "Epidemic Direct Connect", JOptionPane.QUESTION_MESSAGE));
					launcher.dispose();
				} catch (Exception e) {
				}
			}
			
		});
		JPanel hostpanel = new JPanel(new BorderLayout());
		hostpanel.add(host,BorderLayout.WEST);
		hostpanel.add(dedicated,BorderLayout.CENTER);
		hostpanel.add(dc,BorderLayout.EAST);
		MPcontent.add(hostpanel,BorderLayout.SOUTH);
		launcher.setBackground(Color.white);
		JList<Scenario> scenarioList = new JList<Scenario>(scenarios);
		JButton play = new JButton("Play!");
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				launcher.dispose();
				lt = new Thread(new Runnable() {
					public void run() {
						UIManager.startGame(scenarioList.getSelectedValue());
					}
				});
				lt.start();
			}

		});
		SPcontent.add(scenarioList,BorderLayout.CENTER);
		SPcontent.add(play,BorderLayout.SOUTH);
		launcher.setContentPane(lc);
		launcher.setVisible(true);
//		try {
//            if (Integer.parseInt(getLatestVersion()) > version) {
//                UpdateInfo(getWhatsNew());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
	}

	static Vector<Scenario> scenarios = new Vector<>();
	@SuppressWarnings("rawtypes")
	public static void loadPluggins() {
		File pluginDirectory=new File("plugins"); //arbitrary directory
		if(!pluginDirectory.exists()) pluginDirectory.mkdir();
		File[] files=pluginDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
		if(files!=null && files.length>0)
		{
			ArrayList<String> classes=new ArrayList<>();
			ArrayList<URL> urls=new ArrayList<>(files.length);
			for(File file:files)
			{
				try {
					JarFile jar = new JarFile(file);

					jar.stream().forEach(jarEntry -> {
						if(jarEntry.getName().endsWith(".class"))
						{
							classes.add(jarEntry.getName());
						}
					});
					URL url=file.toURI().toURL();
					urls.add(url);
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			URLClassLoader urlClassLoader=new URLClassLoader(urls.toArray(new URL[urls.size()]));
			classes.forEach(className->{
				try
				{
					Class cls=urlClassLoader.loadClass(className.replaceAll("/",".").replace(".class","")); //transforming to binary name
					Class[] interfaces=cls.getInterfaces();
					for(Class intface:interfaces)
					{
						if(intface.equals(Scenario.class)) //checking presence of Plugin interface
						{
							Scenario plugin=(Scenario) cls.newInstance(); //instantiating the Plugin
							scenarios.add(plugin);
							break;
						}
					}
				}
				catch (Exception e){e.printStackTrace();}
			});
			scenarios.add(new Test());
			scenarios.add(new City());
			scenarios.add(new Olmsted());
		}


	}
    private static String getData(String address)throws Exception
    {
        URL url = new URL(address);
        
        InputStream html = null;

        html = url.openStream();
        
        int c = 0;
        StringBuffer buffer = new StringBuffer("");

        while(c != -1) {
            c = html.read();
            
        buffer.append((char)c);
        }
        return buffer.toString();
    }
    
    private static JEditorPane infoPane;
    private static JFrame updater;
    private static JScrollPane scp;
    private static JButton ok;
    private static JButton cancel;
    private static JPanel pan1;
    private static JPanel pan2;

    public static void UpdateInfo(String info) {
        initUpdateComponents();
        infoPane.setText(info);
    }

    @SuppressWarnings("deprecation")
	private static void initUpdateComponents() {
    	updater = new JFrame();
    	updater.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    	updater.setTitle("New Update Found");
        pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());

        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        infoPane = new JEditorPane();
        infoPane.setContentType("text/html");
        infoPane.setEditable(false);

        scp = new JScrollPane();
        scp.setViewportView(infoPane);

        ok = new JButton("Update");
        ok.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                try {
					update();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });

        cancel = new JButton("Cancel");
        cancel.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
            	updater.dispose();
            }
        });
        pan2.add(ok);
        pan2.add(cancel);
        pan1.add(pan2, BorderLayout.SOUTH);
        pan1.add(scp, BorderLayout.CENTER);
        updater.add(pan1);
        updater.pack();
        updater.show();
        updater.setSize(600, 400);
    }
    private static void update() throws Exception
    {
    	try {
			Desktop.getDesktop().browse(new URI(getURL()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
    	
    }
    public static String getLatestVersion() throws Exception
    {
        String data = getData(URL);
        return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
    }
    public static String getWhatsNew() throws Exception
    {
        String data = getData(URL);
        return data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
    }
    
    public static String getURL() throws Exception
    {
        String data = getData(URL);
        return data.substring(data.indexOf("[url]")+5,data.indexOf("[/url]"));
    }

}
