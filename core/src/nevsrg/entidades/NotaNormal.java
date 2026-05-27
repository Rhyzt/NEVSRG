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
	public void dibujar(SpriteBatch batch, float x, float y, float scrollSpeed) {
		batch.draw(textura, x, y);
	}
}
