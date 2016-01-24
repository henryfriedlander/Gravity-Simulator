package henry.term2project;

public class Position {
	double x;
	double y;
	double z;
	double xVel;
	double yVel;
	double zVel;
	double xAccel;
	double yAccel;
	double zAccel;
	
	public Position(double x, double y, double xVel, double yVel){
		this.x=x;
		this.y=y;
		this.xVel=xVel;
		this.yVel=yVel;
	}
	public Position(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public void setX(double x){
		this.x=x;
	}
	public void setY(double y){
		this.y=y;
	}
	public void setZ(double z){
		this.z=z;
	}
	public void setxVel(double xVel){
		this.xVel=xVel;
	}
	public void setyVel(double yVel){
		this.yVel=yVel;
	}
	public void setzVel(double zVel){
		this.zVel=zVel;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}
	public double getxVel(){
		return xVel;
	}
	public double getyVel(){
		return yVel;
	}
	public double getzVel(){
		return zVel;
	}
	public String toString(){
		return (x+" "+y+" "+" "+xVel+" "+yVel);
	}
}
