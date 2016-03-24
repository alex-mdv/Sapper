package Sapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyFrame extends JFrame {

	public MyFrame(){
		setTitle("SAPPER");
		
		final MouseComponent component=new MouseComponent(16,16); 
	add(component);
		JMenuBar menuBar=new JMenuBar();
		
		JMenu gameMenu=new JMenu("����");
		JMenu helpMenu=new JMenu("�������");
		JMenuItem newGameMenu=new JMenuItem("����� ����");
		JMenuItem exitMenu=new JMenuItem("�����");
		gameMenu.add(newGameMenu);
		gameMenu.add(exitMenu);
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		exitMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		newGameMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				component.startGame();
			}
		});
		
		setJMenuBar(menuBar);		
		setSize(275, 325);
		
	}
}
