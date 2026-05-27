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
	
	public void presionar() { siendoPresionada = true; }
	public void dejarDePresionar() { siendoPresionada = false; }
	
	@Override
	public void dibujar(SpriteBatch batch, float x, float y, float scrollSpeed) {
		// Orden de dibujado: Cola, Cuerpo, Cabeza.
		float yCola = y + duracionMS * scrollSpeed; // Se calcula donde deberia ir la cola
		batch.draw(texturaCola, x, yCola); // Se dibuja la cola del Noodle
		
		float alturaCuerpo = yCola - y; // Se calcula el largo del cuerpo
		// Aplica offset  para que aparezca en el medio de la nota y no se vea por los costados inferiores
		batch.draw(texturaCuerpo, x, y + OFFSET, texturaCabeza.getHeight(), alturaCuerpo - OFFSET); 
		
		batch.draw(texturaCabeza, x, y); // Se dibuja la cabeza del Noodle
	}
	
}
