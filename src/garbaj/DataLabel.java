package garbaj;

import java.util.ArrayList;

import javax.swing.JLabel;

import GUI.GUI;
import scraping.City;
import scraping.Source;

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
		Source source = this.panel.source;
		this.panel.changeSource(Source.get(source.getSource(), GUI.getSelectedCity()));
		
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
		this.setText(((City)GUI.getSelectedCity()).getCrntTemp());
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
