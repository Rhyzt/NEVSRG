package nevsrg.entidades;

import com.badlogic.gdx.Game;

import nevsrg.screens.SelectionScreen;
import nevsrg.visual.Assets;

public class GameNEVSRG extends Game {
	@Override
    public void create () {
		// Se cargan los assets
		Assets.getInstancia().cargarRecursos();
		
        // Se inicia el menu de seleccion de canciones
        this.setScreen(new SelectionScreen(this)); 
    }
    
    @Override
    public void render () {
        super.render(); 
    }
}
