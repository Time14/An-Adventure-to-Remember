package aatr.engine.gamestate;

import aatr.engine.ai.DrunkenWalkController;
import aatr.engine.ai.PawnController;
import aatr.engine.ai.PlayerController;
import aatr.engine.ai.QuedController;
import aatr.engine.debug.Debug;
import aatr.engine.gfx.animation.AnimatedTexture;
import aatr.engine.gfx.texture.TextureLibrary;
import aatr.engine.world.World;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.entity.EntityManager;

public abstract class GameStateWorld extends GameState {
	
	//Max 8 layers
	protected World[] worlds;
	protected Entity player;
	protected Entity dude;
	protected QuedController dudeController;
	protected PlayerController controller;
	protected EntityManager em;
	
	public GameStateWorld(GameStateManager gsm, int id) {
		super(gsm, id);
	}

	@Override
	public void init() {
		worlds = new World[8];
		worlds[0] = new World(getDefaultWorldPath());
		em = new EntityManager(this);
		
		player = new Entity(this);
		controller = new PlayerController();
		player.bindController((PawnController) controller);
		
		player.placeRenderer(5, 5);
		
		player.bindAnimation(new AnimatedTexture(player, 8, 8, "TileSet_0", 0, 1, 0, 2, 0, 3, 4).setAnimationSpeed(0.2f));
		
		dude = new Entity(this);
		dudeController =  new QuedController(true, "wwsswwddaa");
		dude.bindController(dudeController);
		dude.placeRenderer(10, 10);
	}
	
	@Override
	public void update(double tick) {
		em.update(tick); 
		controller.update(tick);
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
		em.draw();
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
	
	public World getWorld(int layer) {
		return worlds[layer];
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public abstract String getDefaultWorldPath();
	
}