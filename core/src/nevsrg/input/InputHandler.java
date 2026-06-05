package nevsrg.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import nevsrg.entidades.Nivel;

// TODO: Cambiar esta clase para que pueda ser configurada
public class InputHandler extends InputAdapter {
	public int[] teclasAsignadas;
	private Nivel nivel;
	
	public InputHandler(Nivel nivel, int[] teclasConfiguradas) {
		this.nivel = nivel;
		
		teclasAsignadas = new int[4];
		if (teclasConfiguradas != null && teclasConfiguradas.length == 4) {
			for (int i = 0; i < teclasAsignadas.length; i++) {
				teclasAsignadas[i] = teclasConfiguradas[i];
			}
		} else {
			teclasAsignadas[0] = Keys.D;
	        teclasAsignadas[1] = Keys.F;
	        teclasAsignadas[2] = Keys.J;
	        teclasAsignadas[3] = Keys.K;
		}
	}
	
	/**
	 * Cambia una tecla de un carril especificado
	 * @param numeroCarril El carril objetivo
	 * @param codigoNuevaKey Codigo de la nueva tecla
	 */
	public void cambiarTecla(int numeroCarril, int codigoNuevaKey) {
		if (numeroCarril >= 0 && numeroCarril < 4) {
			teclasAsignadas[numeroCarril] = codigoNuevaKey;
		}
	}

	@Override
	public boolean keyDown(int codigoKey) {
		for (int i = 0; i < teclasAsignadas.length; i++) {
            if (codigoKey == teclasAsignadas[i]) {
                nivel.presionarCarril(i); 
                return true;
            }
        }
		return false;
	}
	
	@Override
	public boolean keyUp(int codigoKey) {
		for (int i = 0; i < teclasAsignadas.length; i++) {
            if (codigoKey == teclasAsignadas[i]) {
                nivel.soltarCarril(i);
                return true;
            }
        }
		return false;
	}
	
	
}
