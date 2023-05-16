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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.DB;
import main.Main;
import scraping.City;
import scraping.Scrape;
import scraping.Source;

public class GUI implements ActionListener {
	static String root = System.getProperty("user.dir") + "/src/main/";
	
	static JFrame mainFrame;
	static JFrame popupFrame;
	static Container mainContainer;
	static JComboBox<Source> sourceComboBox;
	static JComboBox<City> cityComboBox;
	static JTabbedPane mainTabbedPane;
	
	static JLabel crntWthrInfo;
	static JLabel feelsLikeInfo;
	static JLabel windInfo;
	static JLabel humidityInfo;
	
	static JLabel avgMainInfo;
	static JLabel avgWthrInfo;
	static JLabel avgFeelsLikeInfo;
	static JLabel avgWindInfo;
	static JLabel avgHumidityInfo;
	
	static JLabel historyInfo;
	
	static JTable siteTable;
	static JScrollPane siteTableSPane;
	
	static JScrollPane historyTableSPane;
	static JTable historyTable;
	
	static GUI listener = new GUI();
	
	static Font sumInfoFont = new Font(null, Font.PLAIN, 32);
	static Font siteInfoFont = new Font(null, Font.CENTER_BASELINE, 12);
	
//	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//	static int screenWidth = (int) screenSize.getWidth();
//	static int screenHeight = (int) screenSize.getHeight();
	
	static int WIDTH = 500;
	static int HEIGHT = 335;
	static int mainLeftAlign = 18;
	static Rectangle sourceComboBoxRec = new Rectangle(mainLeftAlign,15,450,25);
	static Rectangle cityComboBoxRec = new Rectangle(mainLeftAlign,45,450,25);
	
	static Rectangle mainFrameRec = new Rectangle(0, 0, WIDTH, HEIGHT);
	static Rectangle mainTabPaneRec = new Rectangle(mainLeftAlign, 75, 450, 215);
	static Rectangle popupWindowRec = new Rectangle(0, 0, 350, 40);
	
	static Rectangle siteTableRec = new Rectangle(170, 10, 265, 170);
	static Rectangle historyTableRec = new Rectangle(10, 30, 425, 145);
	
	
	static String[] tableColumns = ("M-DD,Time,Temp,Feels,Direction,Speed,Humidity").split(",");
	
	static JOptionPane optionPane;
	
	static boolean isVisualDebug = false; 
	// PINK ones are dynamic, GREY ones ain't
	
	
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
	
	static Source getSelectedSource() {
		return (Source) sourceComboBox.getSelectedItem();
	}
	
	private static void addTabsToMainPane() {
		mainTabbedPane.addTab("Site data", createSitePanel());
		mainTabbedPane.addTab("Summary", createSummaryPanel());
		if (DB.isConnected()) mainTabbedPane.addTab("History", createHistoryPanel());
	}
	
	private static JPanel createHistoryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		historyInfo = createLbl("RECORDED AVERAGE VALUES FOR ", true, 10, 5, 425, 20, panel, null);
		String[][] _temp = {{"1","1","1","1","1","1","1"}};
		historyTable = new JTable(new DefaultTableModel(_temp, tableColumns));
		historyTable.setBounds(historyTableRec);
		panel.add(historyTable);
        historyTableSPane = new JScrollPane(historyTable);
        historyTableSPane.setBounds(historyTableRec);
        panel.add(historyTableSPane);
        historyTableSPane.setVisible(true);
        historyTable.setAutoCreateRowSorter(true);
		return panel;
	}
	
	private static JPanel createSitePanel() {

		JPanel panel = new JPanel();
		panel.setLayout(null);
		createSiteLbls(panel);

		//adding table
		String[][] _temp = {{"1","1","1","1","1","1","1"}};
		siteTable = new JTable(new DefaultTableModel(_temp, tableColumns));
		siteTable.setBounds(siteTableRec);
		panel.add(siteTable);
        siteTableSPane = new JScrollPane(siteTable);
        siteTableSPane.setBounds(siteTableRec);
        panel.add(siteTableSPane);
        siteTableSPane.setVisible(false);
        siteTable.setAutoCreateRowSorter(true);
		return panel;
	}
	
	private static JPanel createSummaryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		createSummaryLbls(panel);
		return panel;
	}
	
	private static void updateTables(Source source, City city) {
		updateTable(historyTable, null, city);
		updateTable(siteTable, source, city);
	}

	private static void updateTable(JTable table, Source source, City city) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		String sourceName = source == null ? null : source.getName();
		String cityName = city.getName();
		String[][] newData = Main.db.getSavedHistory(sourceName, cityName);
		for (String[] row : newData) {
		    model.addRow(row);
		}
	}
	
	private static void createSiteLbls(JPanel panel) {
		int leftPadding = 10;
		createLbl("CURRENT WEATHER (°C)", true, leftPadding, 10, 150, 20, panel, null);
		createLbl("FEELS LIKE (C°)", false, 	leftPadding, 110, 120, 20, panel, null);
		createLbl("WIND", false, 				leftPadding, 135, 70, 20, panel, null);
		createLbl("HUMIDITY", false, 			leftPadding, 160, 120, 20, panel, null);

		crntWthrInfo = createLbl("...",true, 	leftPadding, 35, 150, 70, panel, new Font(null, Font.PLAIN, 60));
		feelsLikeInfo = createLbl("...",true, 	leftPadding+120, 110, 30, 20, panel, siteInfoFont);
		windInfo = createLbl("...",true, 		leftPadding+70, 135, 80, 20, panel, siteInfoFont);
		humidityInfo = createLbl("...",true, 	leftPadding+120, 160, 30, 20, panel, siteInfoFont);
	}
	

	
	private static void createSummaryLbls(JPanel panel) {
		avgMainInfo = createLbl("AVERAGE WEATHER DATA FOR ...", true, 10, 5, 425, 20, panel, null);
		int wdth = 210;
		int cl1 = 10;
		int infoH = 48;
		// I know, eye cancer!
		createLbl("AVERAGE CURRENT (°C)", true, 	cl1, 30, wdth, 20, panel, null);
		avgWthrInfo = createLbl("...",true, 	cl1, 55, wdth, infoH, panel, sumInfoFont);
		
		createLbl("AVERAGE WIND", true, cl1, 55+5+infoH, wdth, 20, panel, null);
		avgWindInfo = createLbl("...",true, 	cl1, 55+5+25+infoH, wdth, infoH, panel, sumInfoFont);
		
		int cl2 = cl1 + wdth + 5;
		createLbl("AVERAGE FEEL (°C)", true, cl2, 30, wdth, 20, panel, null);
		avgFeelsLikeInfo = createLbl("...",true, cl2, 55, wdth, infoH, panel, sumInfoFont);
		
		createLbl("AVERAGE HUMIDITY", true, cl2, 55+5+infoH, wdth, 20, panel, null);
		avgHumidityInfo = createLbl("...",true, 	cl2, 55+5+25+infoH, wdth, infoH, panel, sumInfoFont);
		
	}

	private static JLabel createLbl(String txt,boolean isCentered,
										int x, int y, int w, int h, 
										JPanel panel, Font font) {
		JLabel lbl = new JLabel(txt.toUpperCase());
		if (isCentered) centerAlignJLabel(lbl);
		lbl.setBounds(x,y,w,h);
		panel.add(lbl);
		if (font != null) lbl.setFont(font);
		if (isVisualDebug) {
			lbl.setBackground(Color.lightGray);
			if (font != null) lbl.setBackground(Color.pink);
			lbl.setOpaque(true);
		}
		return lbl;
	}
	
	private static void refreshMain() {
		mainFrame.repaint();
	}
	
	private static void centerAlignJLabel(JLabel label) {
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setVerticalAlignment(SwingConstants.CENTER);
	}
	
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
	
	private static void showTables() {
		siteTableSPane.setVisible(true);
		historyTableSPane.setVisible(true);
	}
	
	private static void updateInfoLabels() {
		Source selectedSource = GUI.getSelectedSource();
		City selectedCity = GUI.getSelectedCity();
		Scrape data = Scrape.getBy(selectedSource, selectedCity);
		
		crntWthrInfo.setText	(Main.getFormatTemp(data.getCrntTemp()));
		feelsLikeInfo.setText	(Main.getFormatTemp(data.getFeelsLike()));
		windInfo.setText		(Main.getFormatWind(data.getWindDir(), data.getWindSpeed()));
		humidityInfo.setText	(Main.getFormatPercent(data.getHumidity()));

		String cityName = selectedCity.getName().toUpperCase();
		avgMainInfo.setText		("Average weather data for "+cityName);
		avgWthrInfo.setText		(Main.getFormatTemp(selectedCity.getAvgCurrentTemp()));
		avgFeelsLikeInfo.setText(Main.getFormatTemp(selectedCity.getAvgFeelsLike()));
		avgWindInfo.setText		(Main.getFormatWind(selectedCity.getAvgWindDir(), selectedCity.getAvgWindSpeed()));
		avgHumidityInfo.setText	(Main.getFormatPercent(selectedCity.getAvgHumidity()));
		
		if (DB.isConnected()) {
			showTables();
			historyInfo.setText	("Average recorded weather data for "+cityName);
			updateTables(selectedSource, selectedCity);
		}
		refreshMain();
	}
	
	private static void recreateCityComboBox() {
		mainContainer.remove(cityComboBox);
		createCityComboBox();
		refreshMain();
	}
	
	public static void createWaitPopup() {
		popupFrame = new JFrame("Weather data aplication");
		popupFrame.setBounds(popupWindowRec);
		popupFrame.setLocationRelativeTo(null);
		popupFrame.setResizable(false);
		popupFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		popupFrame.setUndecorated(true);
		popupFrame.getContentPane().setLayout(null);
		popupFrame.setVisible(true);
		JLabel label = new JLabel("Retrieving data. Please wait!");
		label.setBounds(10,10,330,20);
		centerAlignJLabel(label);
		popupFrame.getContentPane().add(label);
	}
	
	public static void hidePopup() {
		popupFrame.setVisible(false);
	}
	
	public static void createErrorPopup() {
		JOptionPane.showMessageDialog(mainFrame, "No data could be retrieved. Check your connection or contact developer.", 
				"Weather Data Application", JOptionPane.INFORMATION_MESSAGE);

	}
	
}
