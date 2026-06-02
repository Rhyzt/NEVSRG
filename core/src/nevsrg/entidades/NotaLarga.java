package nevsrg.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NotaLarga extends Nota {
	private Texture texturaCabeza;
	private Texture texturaCuerpo;
	private Texture texturaCola;
	private long duracionMS;
	private boolean siendoPresionada;
	private int OFFSET;
	
	
	
	public NotaLarga(long hitTime, Texture texturaCabeza, Texture texturaCuerpo, Texture texturaCola, long duracionMS) {
		super(hitTime);
		this.texturaCabeza = texturaCabeza;
		this.texturaCuerpo = texturaCuerpo;
		this.texturaCola = texturaCola;
		this.duracionMS = duracionMS;
		this.siendoPresionada = false;
		this.OFFSET = texturaCabeza.getHeight() / 2;
	}
	
	public long getDuracionMS() { return duracionMS; }
	public boolean getSiendoPresionada() { return siendoPresionada; }
	
	@Override
	public long getTiempoFin() {
		return this.getHitTime() + this.getDuracionMS();
	}
	
	public void presionar() { siendoPresionada = true; }
	public void dejarDePresionar() { siendoPresionada = false; }
	
	@Override
	public void dibujar(SpriteBatch batch, float x, float receptorY, float tiempoAudioActual, float scrollSpeed) {
		// Calculo de posicion Y de la cabeza
		float tiempoRestanteCabeza = getHitTime() - tiempoAudioActual;
		float yCabeza = receptorY + (tiempoRestanteCabeza * scrollSpeed);
		
		// Calculo de posicion Y de la cola
		float tiempoRestanteCola = (getHitTime() + duracionMS) - tiempoAudioActual;
		float yCola = receptorY + (tiempoRestanteCola * scrollSpeed);

		// Anclar la cabeza (mientras se mantiene la nota)
		if (siendoPresionada) {
	        yCabeza = receptorY;
	    }
		//Calcular el tamaño del cuerpo
		float alturaCuerpo = Math.max(0, yCola - yCabeza);
		
		// Orden de dibujado: Cola, Cuerpo, Cabeza.
		batch.draw(texturaCola, x, yCola); // Se dibuja la cola del Noodle

		// Aplica offset  para que aparezca en el medio de la nota y no se vea por los costados inferiores
		if (alturaCuerpo > OFFSET) {
	        batch.draw(texturaCuerpo, x, yCabeza + OFFSET, texturaCabeza.getWidth(), alturaCuerpo - OFFSET); 
	    } 

		batch.draw(texturaCabeza, x, yCabeza); // Se dibuja la cabeza del Noodle
	}
}
