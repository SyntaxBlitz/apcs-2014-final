package com.timothyaveni.apcsfinal.client;

import java.util.ArrayList;

public abstract class Player extends Entity {


	public Player(int id, Location loc){
		super(id, loc);
	}

	public void move(int distance, int direction, String plane){}
	
	public Location getLocation(){
		return this.loc;
	}

	public void characterMove(boolean[] keyboard){
		if(keyboard[0]){
			if(keyboard[2]);
			else
				loc.setX(loc.getX() + 8);
		}
		else if(keyboard[1]){
			if(keyboard[3]);
			else
				loc.setY(loc.getY() + 8);
		}
		else if(keyboard[2]){
			if(keyboard[0]);  
			else
				loc.setX(loc.getX() - 8);
		}
		else if(keyboard[3]){
			if(keyboard[1]);
			else
				loc.setY(loc.getY() - 8);
		}
	}
	
	public void attack(ArrayList<Entity> entities){
		int height = this.getHeight();
		int width = this.getWidth();
		Location[] attackLocs = new Location[height * width / 2];
		switch(loc.getDirection()){
		case 1:
			for (int p = 0; p < attackLocs.length; p ++){
				if((p + 1) % width <= width / 2){
					attackLocs[p] = new Location(loc.getX() -(width / 2 - p), loc.getY() + (p / width + 1), 1);
				}
				else
					attackLocs[p] = new Location(loc.getX() + (p - width / 2), loc.getY() + (p / width + 1), 1);
			}
			for (Entity a  : entities){
				for (Location p: attackLocs){
					if(a.getLocation().equals(p)){
						//notify Tim and return
					}
				}
			}
			break;
		case 2:
			for (int p = 0; p < attackLocs.length; p ++){
				if((p + 1) % height <= height / 2){
					attackLocs[p] = new Location(loc.getX() - (p / height + 1), loc.getY() + (height / 2 - p);
				}
				else
					attackLocs[p] = new Location(loc.getX() - (p / height + 1), loc.getY() - (p - height / 2));
 			}
			for (Entity a  : entities){
				for (Location p: attackLocs){
					if(a.getLocation().equals(p)){
						//notify Tim and return
					}
				}
			}
			break;


		}
	}


	public abstract Entity attack();


}
