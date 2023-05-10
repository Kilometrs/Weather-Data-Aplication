package garbaj;

import java.util.ArrayList;

import javax.swing.JPanel;

import scraping.Source;

public class Panel extends JPanel {
	Source source;
	ArrayList<DataLabel> dataLabels = new ArrayList<DataLabel>();
	static ArrayList<Panel> all = new ArrayList<Panel>();
	
	public Panel(Source source) {
		this.source = source;
		Panel.all.add(this);
	}
	
//	public DataLabel createDataLabel(String name) {
//		return new DataLabel(name, this);
//	}

	public void changeSource(Source source) {
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
