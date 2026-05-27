package nevsrg.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Nota {
	private long hitTime;
	
	   
	public Nota(long hitTime) {
		this.hitTime = hitTime;
	}
	
	public abstract void dibujar(SpriteBatch batch, float x, float y, float scrollSpeed);
	
	public long getHitTime() { return hitTime; }
}
