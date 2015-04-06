package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EnemyBowblack extends Enemy{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	
	public EnemyBowblack(int x, int y) {
		super(x, y);
		try {
			super.enemy = ImageIO.read(new File("./f2/spw/image/bowblack.png"));  //hellokitty
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.drawImage(enemy,x,y,width,height,null);
		// g.setColor(Color.YELLOW);
		// g.fillRect(x, y, width, height);
		
	}
	@Override
	public int getScore(){
		score = 10;
		return score;
	}

}
