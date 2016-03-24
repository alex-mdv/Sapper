package Sapper;


import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {

		Image image = new ImageIcon("../Alex/src/Sapper/11.gif").getImage();
		MyFrame frame = new MyFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(image);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}