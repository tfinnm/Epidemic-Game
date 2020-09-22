package epidemic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import entities.ALSBed;
import entities.Ambulance;
import entities.BLSBed;
import entities.Disease;
import entities.FireTruck;
import entities.Job;
import entities.Renderable;
import entities.RevivalRoom;
import entities.TreatmentRoom;
import entities.TriageChair;
import entities.agent;
import entities.pharmacy;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import scenarios.Scenario;

public class UIManager {

	public static ArrayList<Renderable> drawlist = new ArrayList<Renderable>();

	public static Queue<String> logQueue = new LinkedList<String>();
	public static void log(String msg) {
		logQueue.add("\n" + msg);
	}
	public static void updatelog() {
		String t = "";
		while (logQueue.size() > 0) {
			t+=logQueue.poll();
		}
		LogScreen.setText(LogScreen.getText() + t);
		Thread.yield();
	}
	
	//heights
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	public static Panel ui = new Panel();
	public static JFrame frame;
	public static JFrame control;
	public static JTabbedPane controlPanel;
	public static JCheckBox usePPE;
	public static JCheckBoxMenuItem pause;
	public static JCheckBoxMenuItem hispeed;
	public static boolean advanceOnce = false;
	public static JSlider BuyAmount;
	public static JLabel PPEAvailible;
	public static JLabel CashAvailible;
	public static JCheckBox limitGatherings;
	public static JCheckBox closeNonEssential;
	public static JCheckBox closeDiningRooms;
	public static JCheckBox discourageHospital;
	public static JButton massTesting;
	public static JTextPane LogScreen;
	public static boolean debug = true;
	public static boolean masstest = false;
	static JTabbedPane ResearchScreen;
	public static JPanel GrantPanel; 
	static ITrace2D Population;
	static ITrace2D Deaths;
	static ITrace2D dailyDeaths;
	static ITrace2D knownInfected;
	static ITrace2D Money; 
	static ITrace2D PPETrace;
	static ITrace2D Doctors;
	static ITrace2D Beds;
	static ITrace2D ICU;
	static ITrace2D ambus;
	static ITrace2D resus;
	static ITrace2D treat;
	static ITrace2D pharm;
	
	public static void createFrame() {
		frame = new JFrame("Epidemic ("+ss+")");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setContentPane(ui);
		frame.setVisible(true);
		frame.setBackground(Color.white);
		control = new JFrame("Epidemic (Controls)");
		control.setSize(500,500);
		control.setLocation(500, 0);
		control.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		control.setResizable(false);
		controlPanel = new JTabbedPane();
		//JPanel controlPanelHolder = new JPanel();
		control.setContentPane(controlPanel);
		control.setVisible(true);
		control.setBackground(Color.lightGray);
		//controlPanelHolder.add(controlPanel);
		JMenuBar menu = new JMenuBar();
		control.setJMenuBar(menu);
		JMenu timeMenu = new JMenu("Time");
		menu.add(timeMenu);
		JMenu debugMenu = new JMenu("Debug");
		if (debug) menu.add(debugMenu);
		JMenuItem addMoney = new JMenuItem("Add Money");
		addMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Job.changeBalance(Integer.parseInt(JOptionPane.showInputDialog("Ammount")));
			}});
		debugMenu.add(addMoney);
		pause = new JCheckBoxMenuItem("pause");
		hispeed = new JCheckBoxMenuItem("High Speed");
		JMenuItem advance = new JMenuItem("Advance One Cycle");
		JMenuItem start = new JMenuItem("Start");
		if (Client.multiplayer) {
			pause.setEnabled(false);
			hispeed.setEnabled(false);
			advance.setEnabled(false);
		}
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new CycleManager()).start();
				start.setEnabled(false);
			}
			
		});
		advance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				advanceOnce = true;
			}
			
		});
		timeMenu.add(pause);
		timeMenu.add(hispeed);
		timeMenu.add(advance);
		timeMenu.add(start);
		JMenu gameMenu = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		JMenuItem exitGame = new JMenuItem("Exit");
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		//gameMenu.add(newGame);
		gameMenu.add(exitGame);
		menu.add(gameMenu);
		usePPE = new JCheckBox("Use PPE");
		JPanel PPEScreen = new JPanel(new BorderLayout());
		controlPanel.addTab("PPE", PPEScreen);
		PPEScreen.add(usePPE,BorderLayout.WEST);
		PPEAvailible = new JLabel("Availible: "+TriageChair.PPE);
		PPEScreen.add(PPEAvailible,BorderLayout.NORTH);
		BuyAmount = new JSlider(JSlider.HORIZONTAL,0,1000,0);
		BuyAmount.setMinorTickSpacing(10);
		BuyAmount.setMajorTickSpacing(100);
		BuyAmount.setPaintTicks(true);
		BuyAmount.setPaintLabels(true);
		BuyAmount.setSnapToTicks(true);
		JPanel PPEBottom = new JPanel(new BorderLayout());
		PPEScreen.add(PPEBottom, BorderLayout.SOUTH);
		PPEBottom.add(BuyAmount,BorderLayout.CENTER);
		JButton BuyPPE = new JButton("BUY");
		BuyPPE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Job.bankBalance >= BuyAmount.getValue()) {
					TriageChair.PPE+=BuyAmount.getValue();
					Job.changeBalance(-BuyAmount.getValue());
				} else {
					TriageChair.PPE+=Job.bankBalance;
					Job.changeBalance(-Job.bankBalance);
				}
				UIManager.PPEAvailible.setText("Availible: "+TriageChair.PPE);
			}
			
		});
		PPEBottom.add(BuyPPE,BorderLayout.EAST);
		JPanel EconScreen = new JPanel(new BorderLayout());
		controlPanel.addTab("Econ", EconScreen);
		CashAvailible = new JLabel("Balance: "+Job.bankBalance+" | Jobs: "+Job.JobHandler.size());
		EconScreen.add(CashAvailible,BorderLayout.NORTH);
		JPanel econControls = new JPanel(new BorderLayout());
		closeNonEssential = new JCheckBox("Close Non-Essential Business");
		econControls.add(closeNonEssential, BorderLayout.CENTER);
		closeDiningRooms = new JCheckBox("Close All Dining Rooms");
		econControls.add(closeDiningRooms, BorderLayout.EAST);
		limitGatherings = new JCheckBox("Limit Gatherings");
		econControls.add(limitGatherings, BorderLayout.WEST);
		EconScreen.add(econControls, BorderLayout.SOUTH);
		Chart2D EconChart = new Chart2D();
		Chart2D PopChart = new Chart2D();
		Chart2D PPEChart = new Chart2D();
		Population = new Trace2DLtd(720);
		Population.setColor(Color.blue);
		Population.setName("Population");
		Deaths = new Trace2DLtd(720);
		Deaths.setColor(Color.red);
		Deaths.setName("Deaths");
		dailyDeaths = new Trace2DLtd(720);
		dailyDeaths.setColor(Color.black);
		dailyDeaths.setName("Daily Deaths");
		knownInfected = new Trace2DLtd(720);
		knownInfected.setColor(Color.orange);
		knownInfected.setName("Total Confirmed Cases");
		PPETrace = new Trace2DLtd(720);
		PPETrace.setColor(Color.cyan);
		PPETrace.setName("PPE");
		Money = new Trace2DLtd(720);
		Money.setColor(Color.green);
		Money.setName("Balance");
		PopChart.addTrace(Deaths);
		PopChart.addTrace(dailyDeaths);
		PopChart.addTrace(knownInfected);
		PopChart.addTrace(Population);
		EconChart.addTrace(Money);
		PPEChart.addTrace(PPETrace);
		EconScreen.add(EconChart);
		PPEScreen.add(PPEChart, BorderLayout.CENTER);
		GrantPanel = new JPanel(new GridLayout(0,1));
		JScrollPane jsp = new JScrollPane(GrantPanel);
		jsp.createHorizontalScrollBar();
		controlPanel.addTab("Grants", jsp);
		ResearchScreen = new JTabbedPane();
		controlPanel.addTab("Research", ResearchScreen);
		controlPanel.addTab("Population",PopChart);
		Chart2D hospitalChart = new Chart2D();
		JPanel HospitalScreen = new JPanel(new BorderLayout());
		HospitalScreen.add(hospitalChart,BorderLayout.CENTER);
		Doctors = new Trace2DLtd(720);
		Doctors.setColor(Color.green);
		Doctors.setName("Doctors Availible");
		Beds = new Trace2DLtd(720);
		Beds.setColor(Color.yellow);
		Beds.setName("Hospital Beds Availible");
		ICU = new Trace2DLtd(720);
		ICU.setColor(Color.red);
		ICU.setName("ICU Beds Availible");
		ambus = new Trace2DLtd(720);
		ambus.setColor(Color.blue);
		ambus.setName("EMS Vehicles Availible");
		resus = new Trace2DLtd(720);
		resus.setColor(Color.orange);
		resus.setName("Revival Rooms Availible");
		treat = new Trace2DLtd(720);
		treat.setColor(Color.CYAN);
		treat.setName("Treatment Rooms Availible");
		pharm = new Trace2DLtd(720);
		pharm.setColor(Color.black);
		pharm.setName("Pharmacies Availible");
		hospitalChart.addTrace(Doctors);
		hospitalChart.addTrace(Beds);
		hospitalChart.addTrace(ICU);
		hospitalChart.addTrace(ambus);
		hospitalChart.addTrace(resus);
		hospitalChart.addTrace(treat);
		hospitalChart.addTrace(pharm);
		JPanel hospitalControl = new JPanel(new BorderLayout());
		discourageHospital = new JCheckBox("Discourage going to the hospital");
		hospitalControl.add(discourageHospital,BorderLayout.EAST);
		HospitalScreen.add(hospitalControl,BorderLayout.SOUTH);
		massTesting = new JButton("Run Mass Testing ($10/agent)");
		massTesting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				masstest = true;
			}
			
		});
		hospitalControl.add(massTesting,BorderLayout.CENTER);
		controlPanel.addTab("Hospitals",HospitalScreen);
		LogScreen = new JTextPane();
		LogScreen.setEditable(false);
		LogScreen.setEnabled(true);
		LogScreen.setText("Congradulations on your appointment as the director of the health department. Go ahead, set your policies and when you're ready press \"start\" in the time menu.");
		JScrollPane LogScreenWrapper = new JScrollPane(LogScreen);
		controlPanel.addTab("Event Log", LogScreenWrapper);
	}
	
	public static void loadResearchPanel() {
		for (Disease d: Disease.DiseaseHandler) {
			if (d.showResearchPanel) ResearchScreen.addTab(d.name, d.generateResearchPanel(new JPanel()));
		}
	}
	
	public static Thread rt;
	
	
	public static Scenario ss;
	public static void startGame(Scenario s) {
		ss = s;
		createFrame();
		s.loadScenario();
		loadResearchPanel();
		loadInitialData();
		rt = new Thread(new RenderLoop());
		rt.start();
	}
	
	public static void loadInitialData() {
		Population.addPoint(0, agent.AgentHandler.size());
		Money.addPoint(0, 1000);
		PPETrace.addPoint(0,10);
		Deaths.addPoint(0,0);
		knownInfected.addPoint(0, 0);
		Doctors.addPoint(0,TriageChair.TriageHandler.size());
		Beds.addPoint(0,BLSBed.beds.size());
		ICU.addPoint(0, ALSBed.beds.size());
		ambus.addPoint(0, Ambulance.dispatch.size()+FireTruck.dispatch.size());
		resus.addPoint(0, RevivalRoom.CCHandler.size());
		treat.addPoint(0, TreatmentRoom.TRHandler.size());
		pharm.addPoint(0, pharmacy.PharmaHandler.size());
		Job.changeBalance(0);
	}

}
