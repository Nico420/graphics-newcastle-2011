package src;

public class Position {
	public float x;
	public float y;
	public float z;
	
	public Position(float x,float y){
		this.x = x;
		this.y=y;
		this.z=0;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
	public boolean equals(Object o){
		if(o instanceof Position){
			return (((Position) o).getX()==this.x) && ((Position) o).getY()==this.y;
		}
		return false; 
	}
	public static boolean checkCollapse(Position e, Position t, int mouvement) {
		boolean res = true;
		
		switch(mouvement){
		case src.DisplayExample.LEFT:
			boolean test = t.getX()-3<e.getX()&& t.getX()+3>e.getX();
			boolean test2 = t.getY()-3<e.getY()&& t.getY()+3>e.getY();
			System.out.println("Gauche : "+(t.getX()-3)+e.getX()+(t.getX()+3)+test+test2+ (test&&test2));
		case src.DisplayExample.RIGHT:
			break;
		case src.DisplayExample.DOWN:
			break;
		case src.DisplayExample.UP:
			break;
		}
		
		return res;
		
	}
}
