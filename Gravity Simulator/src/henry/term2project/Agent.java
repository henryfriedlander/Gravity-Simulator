package henry.term2project;

import java.awt.Point;
import java.util.ArrayList;

public interface Agent {
	ArrayList<Point> getTrail();
	
	double getMass();

	void timeStep(Simulation s);
	
	void timeStepCircle(Simulation s);
	
	Position getPosition();

	void addPoint(Point point);
	
	double dist(Agent a);
}
