package nevsrg.entidades;

import com.badlogic.gdx.Game;

import nevsrg.screens.SelectionScreen;

public class GameNEVSRG extends Game {
	@Override
    public void create () {
        // En lugar de ir directo a GameScreen, iniciamos en el menú
        this.setScreen(new SelectionScreen(this)); 
    }
    
    @Override
    public void render () {
        super.render(); 
    }
}
