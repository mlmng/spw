package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class Main {
	public static void main(String[] args){
		SpaceShip v = new SpaceShip(180, 550, 50, 50);
		GamePanel gp = new GamePanel();
		final GameEngine engine = new GameEngine(gp, v);
		JFrame frame = new JFrame("Hello Kitty Land");
		JButton bt = new JButton("Start");
		bt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				engine.start();
			}
		});

		JPanel pn = new JPanel();
		pn.setLayout(new BorderLayout());
		pn.add(bt,BorderLayout.SOUTH);

		bt.setFocusable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(475, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.getContentPane().add(pn, BorderLayout.EAST);
		frame.setVisible(true);
		
		
	}
}
