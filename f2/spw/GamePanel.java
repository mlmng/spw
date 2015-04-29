package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;
	public Image background;  //bg	
	public Image energy; 
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	JFrame frame = new JFrame("SimpleFrame");
		JPanel panel = new JPanel();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.PINK);;;;
;		try {
			background = ImageIO.read(new File("./f2/spw/image/bg.jpg"));  
			energy = ImageIO.read(new File("./f2/spw/image/energy.png")); 
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big.drawImage(background,0,0,null);
		big.setColor(Color.BLACK);		
		big.drawString(String.format("%08d", reporter.getScore()), 320, 20);
		big.drawString(String.format("High Score = %d", reporter.getMaxScore()), 150, 20);
		big.drawString(String.format("Energy : "), 10, 20);
		int x=65;
		for(int i=0; i<reporter.getEnergy(); i++){
			big.drawImage(energy,x,7,20,20,null);
			x+=15;
		 }
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	public void updateGameUIStart(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big.drawImage(background,0,0,null);
		big.setColor(Color.BLACK);		
		big.drawString(String.format("idsdpoopsfdopfsopfp"), 120, 200);


		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
