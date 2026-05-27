package nevsrg.screens;

import com.badlogic.gdx.Game;

public class GameNEVSRG extends Game {
	@Override
    public void create() {
        this.setScreen(new GameScreen()); 
    }
}
