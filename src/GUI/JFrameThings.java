package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFrameThings {
	static JLabel btn1;
	static Container cont;
	static JFrame frame;
	public static void main(String[] args) {
		frame = new JFrame("My thing");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		cont = frame.getContentPane();
		btn1 = new JLabel("My button");
		btn1.setBounds(500, 50, 200, 40);
		Color myColor = Color.decode("#1a94d0");
		cont.setBackground(Color.WHITE);
		btn1.setOpaque(true);
		btn1.setForeground(Color.WHITE);
		btn1.setBackground(myColor);
		cont.add(btn1);
		
		frame.setVisible(true);
		
		Rectangle r1 = btn1.getVisibleRect();
		cont.repaint();
		//lbl.addMouseMotionListener(ml1);
		
		
		//whatever();
	}
	
	static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {}
	}
	
	static void whatever() {
		int dir = 1;
		int maxX = 600;
		while (true) {
			int newX = btn1.getX() + 5 * dir;
			if (newX >= maxX) {
				dir *= -1;
				newX = maxX;
			}
			if (newX <= 0) {
				dir *= -1;
				newX = 0;
			}
			btn1.setLocation(newX, btn1.getY());
			sleep(20);
		}
	}
}

