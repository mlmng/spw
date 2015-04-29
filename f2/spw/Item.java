package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Item extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 5;
	private boolean alive = true;
	public Image item;  //ใช้ในลูก
	public int score;
	
	public Item(int x, int y) {
		super(x, y, 30, 30);
		
	}

	@Override
	public void draw(Graphics2D g) {
		/*if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}*/
		
		//g.setColor(Color.RED);
		//g.fillRect(x, y, width, height);
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			die();
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	public int getScore(){
		return score;
	}
	public void die(){
		 alive = false;
	}
}