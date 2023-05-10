package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import scraping.Scrape;

public class GUI implements ActionListener {
	static String root = System.getProperty("user.dir") + "/src/main/";
	static JFrame mainFrame;
	static Container mainContainer;
	static JComboBox<String> mainComboBox;
	static JTabbedPane mainTabbedPane;
	
//	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//	static int screenWidth = (int) screenSize.getWidth();
//	static int screenHeight = (int) screenSize.getHeight();
	
	static int WIDTH = 500;
	static int HEIGHT = 320;
	static int leftAlign = 18;
	static Rectangle mainComboBoxRec = new Rectangle(leftAlign,15,450,25);
	static Rectangle mainFrameRec = new Rectangle(0, 0, WIDTH, HEIGHT);
	static Rectangle mainTabPaneRec = new Rectangle(leftAlign, 55, 450, 215);
	
	
	public static void createMainFrame() {
		mainFrame = new JFrame("Weather data aplication");
		mainFrame.setBounds(mainFrameRec);
		mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setVisible(true);
		mainContainer = mainFrame.getContentPane();
	}
	
	public static void createMainComboBox(String[] wordList) {
		mainComboBox = createComboBox(wordList, mainComboBoxRec);
		mainContainer.add(mainComboBox);
		mainComboBox.setVisible(true);
		mainComboBox.addActionListener(new GUI());
		refreshMain();
	}
	
	public static void createTabbedPane() {
		mainTabbedPane = new JTabbedPane();
		mainTabbedPane.setBounds(mainTabPaneRec);
		mainContainer.add(mainTabbedPane);
		addTabsToMainPane();
		mainTabbedPane.setVisible(true);
		refreshMain();
	}
	
	static String getSelectedCity() {
		return mainComboBox.getSelectedItem().toString();
	}
	
	private static void addTabsToMainPane() {
		mainTabbedPane.addTab("AccuWeather", createSitePanel("AccuWeather"));
		mainTabbedPane.addTab("Gismeteo", createSitePanel("GisMeteo"));
		mainTabbedPane.addTab("LVĢMC", createSitePanel("LVĢMC"));
		mainTabbedPane.addTab("Summary", new JPanel());
		mainTabbedPane.addTab("History", new JPanel());

	}
	
	private static JPanel createSitePanel(String sourceName) {
		Scrape scrape = Scrape.get(sourceName, GUI.getSelectedCity());
		Panel panel = new Panel(scrape);
		panel.setLayout(null);
		
		addPremadeLabels(panel, scrape);

		//adding table
		JTable table = new JTable();
		table.setBounds(170, 10, 265, 170);
		panel.add(table);
		
		return panel;
	}
	
	private static void addPremadeLabels(Panel panel, Scrape scrape) {
		int leftPadding = 10;
		JLabel crntWthrLbl = new JLabel("CURRENT PANEL");
		centerAlignJLabel(crntWthrLbl);
		crntWthrLbl.setBounds(leftPadding,10,150,20);
		
		DataLabel crntWthrInfo = new DataLabel("e", panel, "current");
		centerAlignJLabel(crntWthrInfo);
		crntWthrInfo.setBounds(leftPadding, 35, 150, 70);
		crntWthrInfo.setFont(new Font(null, Font.PLAIN, 70));
		crntWthrInfo.setText(scrape.getCurrentTemp());
		
		JLabel feelsLikeLbl = new JLabel("FEELS LIKE");
		feelsLikeLbl.setBounds(leftPadding, 110, 120, 20);
		
		DataLabel feelsLikeInfo = new DataLabel("e", panel, "feels");
		feelsLikeInfo.setBounds(leftPadding+120, 110, 30, 20);
		feelsLikeInfo.setText(scrape.getFeelsLike());
		
		JLabel windLbl = new JLabel("WIND");
		windLbl.setBounds(leftPadding, 135, 70, 20);
		
		DataLabel windInfo =  new DataLabel("e", panel, "wind");
		centerAlignJLabel(windInfo);
		windInfo.setBounds(leftPadding+70, 135, 80, 20);
		windInfo.setText(scrape.getWindSpeed());
		
		JLabel humLbl = new JLabel("HUMIDITY");
		humLbl.setBounds(leftPadding, 160, 120, 20);
		
		DataLabel humInfo =  new DataLabel("e", panel, "humidity");
		centerAlignJLabel(humInfo);
		humInfo.setBounds(leftPadding+120, 160, 30, 20);
		humInfo.setText(scrape.getHumidity());
		
		
		panel.add(crntWthrLbl);
		panel.add(crntWthrInfo);
		panel.add(feelsLikeLbl);
		panel.add(feelsLikeInfo);
		panel.add(windInfo);
		panel.add(windLbl);
		panel.add(humLbl);
		panel.add(humInfo);
		
		// debug
		crntWthrLbl.setBackground(Color.pink);
		crntWthrLbl.setOpaque(true);
		crntWthrInfo.setBackground(Color.lightGray);
		crntWthrInfo.setOpaque(true);
		
		feelsLikeLbl.setBackground(Color.lightGray);
		feelsLikeLbl.setOpaque(true);
		feelsLikeInfo.setBackground(Color.pink);
		feelsLikeInfo.setOpaque(true);
		
		windLbl.setBackground(Color.pink);
		windLbl.setOpaque(true);
		windInfo.setBackground(Color.lightGray);
		windInfo.setOpaque(true);
		
		humLbl.setBackground(Color.lightGray);
		humLbl.setOpaque(true);
		humInfo.setBackground(Color.pink);
		humInfo.setOpaque(true);
		
	}
	
	private static void refreshMain() {
		mainFrame.repaint();
	}
	
	private static void centerAlignJLabel(JLabel label) {
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setVerticalAlignment(SwingConstants.CENTER);
	}
	
	private static JComboBox<String> createComboBox(String[] wordList, Rectangle rec) {
		JComboBox<String> comboBox = new JComboBox<String>(wordList);
		comboBox.setBounds(rec);
		return comboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() instanceof JComboBox) {
				JComboBox comboBoxSource = (JComboBox) e.getSource();
				if (comboBoxSource == mainComboBox) Panel.refreshData();
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
	
	
}
