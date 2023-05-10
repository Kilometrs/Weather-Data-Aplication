package GUI;

import java.util.ArrayList;

import javax.swing.JLabel;

import scraping.Scrape;

public class DataLabel extends JLabel {
	Panel panel;
	String type;
	
	static ArrayList<DataLabel> all = new ArrayList<DataLabel>();
	
	public DataLabel(String text,Panel panel, String type) {
		super(text);
		this.panel = panel;
		this.type = type;
		panel.dataLabels.add(this);
	}
	
	public void setLatestData() {
		// changes the panel source
		Scrape source = this.panel.source;
		this.panel.changeSource(Scrape.get(source.getSource(), GUI.getSelectedCity()));
		
		if (this.type.equalsIgnoreCase("current")) {
			this.setCurrentTemp();
			return;
		}
		if (this.type.equalsIgnoreCase("feels")) {
			this.setFeelsLike();
			return;
		}
		if (this.type.equalsIgnoreCase("wind")) {
			this.setWind();
			return;
		}
		if (this.type.equalsIgnoreCase("humidity")) {
			this.setHumidity();
			return;
		}
		this.setText("<e2>");
		
	}

	public void setCurrentTemp() {
		this.setText(panel.source.getCurrentTemp());
	}
	
	public void setFeelsLike() {
		this.setText(panel.source.getFeelsLike());
	}
	
	public void setWind() {
		this.setText(panel.source.getWindSpeed());
	}
	
	public void setHumidity() {
		this.setText(panel.source.getHumidity());
	}
}
