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

import scraping.City;
import scraping.Source;

public class GUI implements ActionListener {
	static String root = System.getProperty("user.dir") + "/src/main/";
	static JFrame mainFrame;
	static Container mainContainer;
	static JComboBox<Source> sourceComboBox;
	static JComboBox<City> cityComboBox;
	static JTabbedPane mainTabbedPane;
	
	static JLabel crntWthrInfo;
	static JLabel feelsLikeInfo;
	static JLabel windInfo;
	static JLabel humidityInfo;
	
	static GUI listener = new GUI();
	
	
//	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//	static int screenWidth = (int) screenSize.getWidth();
//	static int screenHeight = (int) screenSize.getHeight();
	
	static int WIDTH = 500;
	static int HEIGHT = 365;
	static int leftAlign = 18;
	static Rectangle sourceComboBoxRec = new Rectangle(leftAlign,15,450,25);
	static Rectangle cityComboBoxRec = new Rectangle(leftAlign,45,450,25);
	
	static Rectangle mainFrameRec = new Rectangle(0, 0, WIDTH, HEIGHT);
	static Rectangle mainTabPaneRec = new Rectangle(leftAlign, 75, 450, 215);
	
	
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
	
	public static void createMainComboBoxes() {
		createSourceComboBox();
		createCityComboBox();
		refreshMain();
	}
	
	private static void createSourceComboBox() {
		sourceComboBox = new JComboBox<Source>(Source.getAvailableSources());
		sourceComboBox.setBounds(sourceComboBoxRec);
		sourceComboBox.setVisible(true);
		sourceComboBox.addActionListener(listener);
		mainContainer.add(sourceComboBox);
	}
	
	private static void createCityComboBox() {
		Source selectedSrc = (Source) sourceComboBox.getSelectedItem();
		cityComboBox = new JComboBox<City>(selectedSrc.getSrcAvailableCities());
		cityComboBox.setBounds(cityComboBoxRec);
		cityComboBox.setVisible(true);
		cityComboBox.addActionListener(listener);
		mainContainer.add(cityComboBox);
	}
	
	public static void createTabbedPane() {
		mainTabbedPane = new JTabbedPane();
		mainTabbedPane.setBounds(mainTabPaneRec);
		mainContainer.add(mainTabbedPane);
		addTabsToMainPane();
		mainTabbedPane.setVisible(true);
		refreshMain();
	}
	
	static City getSelectedCity() {
		return (City) cityComboBox.getSelectedItem();
	}
	
	private static void addTabsToMainPane() {
		mainTabbedPane.addTab("Site data", createSitePanel());
		mainTabbedPane.addTab("Summary", new JPanel());
		mainTabbedPane.addTab("History", new JPanel());
	}
	
	private static JPanel createSitePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		addPremadeLabels(panel);

		//adding table
		JTable table = new JTable();
		table.setBounds(170, 10, 265, 170);
		panel.add(table);
		
		return panel;
	}
	
	private static void addPremadeLabels(JPanel panel) {
		int leftPadding = 10;
		JLabel crntWthrLbl = new JLabel("CURRENT WEATHER (°C)");
		centerAlignJLabel(crntWthrLbl);
		crntWthrLbl.setBounds(leftPadding,10,150,20);
		
		crntWthrInfo = new JLabel("...");
		centerAlignJLabel(crntWthrInfo);
		crntWthrInfo.setBounds(leftPadding, 35, 150, 70);
		crntWthrInfo.setFont(new Font(null, Font.PLAIN, 70));
		
		JLabel feelsLikeLbl = new JLabel("FEELS LIKE (C°)");
		feelsLikeLbl.setBounds(leftPadding, 110, 120, 20);
		
		feelsLikeInfo = new JLabel("...");
		centerAlignJLabel(feelsLikeInfo);
		feelsLikeInfo.setBounds(leftPadding+120, 110, 30, 20);
		
		JLabel windLbl = new JLabel("WIND");
		windLbl.setBounds(leftPadding, 135, 70, 20);
		
		windInfo =  new JLabel("...");
		centerAlignJLabel(windInfo);
		windInfo.setBounds(leftPadding+70, 135, 80, 20);
		
		JLabel humLbl = new JLabel("HUMIDITY");
		humLbl.setBounds(leftPadding, 160, 120, 20);
		
		humidityInfo = new JLabel("...");
		centerAlignJLabel(humidityInfo);
		humidityInfo.setBounds(leftPadding+120, 160, 30, 20);
		
		
		panel.add(crntWthrLbl);
		panel.add(crntWthrInfo);
		panel.add(feelsLikeLbl);
		panel.add(feelsLikeInfo);
		panel.add(windInfo);
		panel.add(windLbl);
		panel.add(humLbl);
		panel.add(humidityInfo);
		
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
		humidityInfo.setBackground(Color.pink);
		humidityInfo.setOpaque(true);
		
	}
	
	private static void refreshMain() {
		mainFrame.repaint();
	}
	
	private static void centerAlignJLabel(JLabel label) {
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setVerticalAlignment(SwingConstants.CENTER);
	}
	
//	private static JComboBox<String> createComboBox(String[] wordList, Rectangle rec) {
//		JComboBox<String> comboBox = new JComboBox<String>(wordList);
//		comboBox.setBounds(rec);
//		return comboBox;
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() instanceof JComboBox) {
				JComboBox comboBoxSource = (JComboBox) e.getSource();
				if (comboBoxSource == cityComboBox) GUI.updateInfoLabels();
				if (comboBoxSource == sourceComboBox) GUI.recreateCityComboBox();;
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
	
	private static void updateInfoLabels() {
		City selectedCity = GUI.getSelectedCity();
		crntWthrInfo.setText(selectedCity.getCurrentTemp());
		feelsLikeInfo.setText(selectedCity.getFeelsLike());
		windInfo.setText(selectedCity.getWindSpeed());
		humidityInfo.setText(selectedCity.getHumidity());
	}
	
	private static void recreateCityComboBox() {
		mainContainer.remove(cityComboBox);
		createCityComboBox();
		refreshMain();
	}
	
}
