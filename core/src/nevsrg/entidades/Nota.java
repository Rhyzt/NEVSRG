package nevsrg.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Nota {
	private long hitTime;
	
	   
	public Nota(long hitTime) {
		this.hitTime = hitTime;
	}
	
	public abstract void dibujar(SpriteBatch batch, float x, float receptorY, float tiempoAudioActual, float scrollSpeed);
	public abstract long getTiempoFin();
	
	public long getHitTime() { return hitTime; }
}
