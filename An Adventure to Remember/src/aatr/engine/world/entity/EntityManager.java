package aatr.engine.world.entity;

import java.util.ArrayList;
import java.util.HashMap;

import aatr.engine.gamestate.GameState;

public class EntityManager {
	
	private GameState gs;
	
	private HashMap<String, Entity> entities;
	private HashMap<String, Group> groups;
	
	public EntityManager(GameState gs) {
		entities = new HashMap<>();
		groups = new HashMap<>();
		this.gs = gs;
	}
	
	public void update(double tick) {
		for(Entity e : entities.values()) {
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