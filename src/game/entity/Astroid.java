package game.entity;

import game.world.World;

public class Astroid extends Entity {
	private boolean split = false;
	public Astroid(World home) {
		super(home);
		// TODO Auto-generated constructor stub
	}

	public boolean hasSplit() {
		// TODO Auto-generated method stub
		return split;
	}

	public void getChildren(Astroid[] children, int len) {
		split= false;
		children[0] = new Astroid(getMyworld());
		children[1] = new Astroid(getMyworld());
		
		children[0].setPosition(position);
		children[1].setPosition(position);
		
		children[0].oldposition = oldposition;
		children[1].oldposition = oldposition;
		
		children[0].setSize(size/2);
		children[1].setSize(size/2);
		
		children[0].mycol = mycol;
		children[1].mycol = mycol;
		
		children[0].setSpeed(speed*2);
		children[1].setSpeed(speed*2);
		
		children[0].setHeading(heading.scalarMultiply(-1));
		children[1].setHeading(heading.scalarMultiply(1));
		//TODO heading
		
	}

	@Override
	public boolean colidedWith(Entity cEntity) {
		if(cEntity instanceof Bullet&& alive) {
			if(size>10) {
				split= true;
			}
			System.out.println("bonk");
			System.out.println(cEntity + ""+ this);
			alive=false;
			cEntity.alive=false;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

}
