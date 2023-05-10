package GUI;

import java.util.ArrayList;

import javax.swing.JPanel;

import scraping.Scrape;

public class Panel extends JPanel {
	Scrape source;
	ArrayList<DataLabel> dataLabels = new ArrayList<DataLabel>();
	static ArrayList<Panel> all = new ArrayList<Panel>();
	
	public Panel(Scrape source) {
		super();
		this.source = source;
		Panel.all.add(this);
	}
	
//	public DataLabel createDataLabel(String name) {
//		return new DataLabel(name, this);
//	}

	public void changeSource(Scrape source) {
		this.source = source;
	}
	
	public static void refreshData() {
		for (Panel panel : all) {
			for (DataLabel lbl : panel.dataLabels) {
				lbl.setLatestData();
			}
		}
	}
	
}
