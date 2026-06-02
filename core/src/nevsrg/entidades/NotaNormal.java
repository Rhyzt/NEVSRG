package nevsrg.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NotaNormal extends Nota {
	private Texture textura;
	
	public NotaNormal(long hitTime, Texture textura) {
		super(hitTime);
		this.textura= textura;
	}
	
	@Override
	public long getTiempoFin() {
		return this.getHitTime();
	}
	
	public void dibujar(SpriteBatch batch, float x, float receptorY, float tiempoAudioActual, float scrollSpeed) {
		// Se calcula la posicion exacta de la nota segun el tiempo de la cancion
		float tiempoRestante = getHitTime() - tiempoAudioActual;
		float y = receptorY + (tiempoRestante * scrollSpeed);
		
		batch.draw(textura, x, y);
	}
}
