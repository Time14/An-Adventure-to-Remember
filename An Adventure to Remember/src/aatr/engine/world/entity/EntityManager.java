package aatr.engine.world.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import aatr.engine.debug.Debug;
import aatr.engine.gamestate.GameState;

public class EntityManager {
	
	public static final String ENTITIES_GROUP = "Entities";
	
	private GameState gs;
	
	private HashMap<String, Entity> entities;
	private HashMap<String, Group> groups;
	
	public EntityManager(GameState gs) {
		entities = new HashMap<>();
		groups = new HashMap<>();
		addGroup(ENTITIES_GROUP);
		this.gs = gs;
	}

	public boolean isFree(int x, int y, int layer) {
		for(Entity e : entities.values()) {
			if(layer == e.getLayer()) {
				if(x == Math.floor(e.getX()) && y == Math.floor(e.getY()) || x == Math.ceil(e.getX()) && y == Math.ceil(e.getY()))
					return false;
			}
		}
		for(Group g : groups.values()) {
			for(Entity e : g.entities) {
				if(layer == e.getLayer()) {
					if(x == Math.floor(e.getX()) && y == Math.floor(e.getY()) || x == Math.ceil(e.getX()) && y == Math.ceil(e.getY()))
						return false;
				}
			}
		}
		
		return true;
	}
	
	public void update(double tick) {
		for(Entity e : entities.values()) {
			Debug.log("001");
			e.update(tick);
		}
		
		for(Group g : groups.values()) {
			g.update(tick);
		}
	}
	
	public void draw() {
		for(Entity e : entities.values()) {
			e.draw();
		}
		
		for(Group g : groups.values()) {
			g.draw();
		}
	}
	
	public EntityManager addEntity(String name, Entity entity) {
		entities.put(name, entity.sendEntityManager(this));
		return this;
	}
	
	public Entity getEntity(String name) {
		return entities.get(name);
	}
	
	public EntityManager addGroup(String... names) {
		for(String name : names) {
			groups.put(name, new Group(name));
		}
		
		return this;
	}
	
	public ArrayList<Entity> getEntitiesFromGroup(String name) {
		return groups.get(name).entities;
	}
	
	public EntityManager addToGroup(String group, Entity... entities) {
		for(Entity e : entities)
			groups.get(group).entities.add(e.sendEntityManager(this));
		return this;
	}
	
	public void destroy() {
		for(Entity e : entities.values())
			e.destroy();
		for(Group g : groups.values())
			g.destroy();
		groups.clear();
	}
	
	private class Group {
		final String NAME;
		
		ArrayList<Entity> entities;
		boolean active = true;
		boolean visible = true;
		
		Group(String name) {
			this.NAME = name;
			entities = new ArrayList<>();
		}
		
		void update(double tick) {
			if(active) {
				for(Entity e : entities)
					e.update(tick);
			}
		}
		
		void draw() {
			if(visible) {
				for(Entity e : entities)
					e.draw();
			}
		}
		
		void destroy() {
			for(Entity e : entities)
				e.destroy();
		}
	}
}