package aatr.engine.gamestate;

import aatr.engine.world.World;
import aatr.engine.world.entity.EntityManager;
import aatr.engine.world.player.Player;

public abstract class GameStateWorld extends GameState {
	
	//Max 8 layers
	protected World[] worlds;
	protected Player player;
	protected EntityManager em;
	
	public GameStateWorld(GameStateManager gsm, int id) {
		super(gsm, id);
	}

	@Override
	public void init() {
		worlds = new World[8];
		worlds[0] = new World();
		player = new Player(this);
		em = new EntityManager(this);
	}

	@Override
	public void update(double tick) {
		em.update(tick); 
		player.update(tick);
	}

	@Override
	public void draw() {
		int layer = player.getLayer();
		for(int l = 0; l < worlds.length; l++) {
			if(worlds[l] != null)
				worlds[l].draw(player);
			if(l == layer) 
				player.draw();
		}	
	}

	@Override
	public void exit() {
		for(World w : worlds) {
			if(w == null)
				continue;
			w.destroy();
		}
		player.destroy();
		em.destroy();
	}
	
	
	
}