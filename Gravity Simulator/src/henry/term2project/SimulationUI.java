package henry.term2project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class SimulationUI extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5659010045381897708L;
	private static final String START_BUTTON = "Start";
    private static final String STOP_BUTTON = "Stop";
    private static final String RESTART_BUTTON = "Restart";

    private final Simulation world;
    private final JFrame frame;

    private final WorldPanel worldPanel;
    private final JButton restartButton;
    private final JButton runButton;

	public SimulationUI(Simulation s){
		world=s;
		worldPanel=new WorldPanel();
		runButton = new JButton(START_BUTTON);
        restartButton = new JButton(RESTART_BUTTON);
		

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(runButton, BorderLayout.WEST);
        bottom.add(restartButton, BorderLayout.EAST);
        
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(Constants.screenX, Constants.screenY);
		
		runButton.addActionListener(new ActionListener() {
            boolean toggle = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggle) {
                    worldPanel.start();
                    runButton.setText(STOP_BUTTON);
                } else {
                    worldPanel.stop();
                    runButton.setText(START_BUTTON);
                }
                toggle = !toggle;
            }
        });
		restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.restart();
                worldPanel.start();
                worldPanel.stop();
            }
        });
		Container pane = frame.getContentPane();
        pane.add(worldPanel, BorderLayout.CENTER);
        pane.add(bottom, BorderLayout.SOUTH);
        
	}
	public void show() {
        frame.setVisible(true);
    }
	
	
	
	
	private class WorldPanel extends JPanel implements MouseMotionListener, MouseListener{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3983835989954582924L;
		private static final int RUN_SPEED = 0;
		private final Timer timer;
		private boolean mouseClicked=false;
		private Agent curPlanet;
		private int initXpos;
		private int initYpos;
		private int XplanetPos;
		private int YplanetPos;
		public WorldPanel(){
			System.out.println("HI");
			timer = new Timer(RUN_SPEED, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    step();
                }
            });
		}
		@Override
		public void paintComponent(Graphics oldg){
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			Graphics2D g = (Graphics2D)oldg;
			
			g.setRenderingHint(
			        RenderingHints.KEY_ANTIALIASING,
			        RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.BLUE);
			System.out.println(world.getPlanets().size());
			for(Agent p:world.getPlanets()){
				Position pos=p.getPosition();
				drawPlanet(pos,g);
				drawTrails(p,g);
				p.addPoint(new Point((int)pos.getX()+Constants.screenX/2,(int)pos.getY()+Constants.screenY/2));
			}
			g.drawOval(Constants.screenX/2,Constants.screenY/2,1,1);
			
			g.setColor(Color.red);
			Point2D.Double com=world.getCOM();
			//System.out.println("x: " + com.x + ", y: " + com.y);
			drawCircle(com.x+Constants.screenX/2, com.y+Constants.screenY/2, 2, g);
		}
		public void drawPlanet(Position pos, Graphics2D g){
			drawCircle((int)(pos.getX()+Constants.screenX/2),(int)(pos.getY()+Constants.screenY/2),15,g);
		}
		public void drawTrails(Agent p, Graphics2D g){
			ArrayList<Point> points=p.getTrail();
			for(int i=1;i<points.size();i++){
				g.drawLine(points.get(i-1).x,points.get(i-1).y,points.get(i).x,points.get(i).y);
			}
		}
	
	    public void drawCircle(double x,double y,double r, Graphics2D g){
	        x = x-r;
	        y = y-r;
	        g.fillOval((int)x,(int)y,(int)(2*r),(int)(2*r));
	    }
	    public void start(){
	    	timer.start();
	    }
	    public void stop(){
	    	timer.stop();
	    }
	    public void step(){
	    	System.out.println("STEP");
	    	world.takeSteps();
	    	repaint();
	    }
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            System.out.println("here");
            for(Agent p:world.getPlanets()){
                Position pos=p.getPosition();
                if(inCircle((int)pos.getX(),(int)pos.getY(),e.getX(),e.getY(),15)){
                    p=curPlanet;
                    mouseClicked=true;
                }
            }
	        if(curPlanet!=null){
	        	System.out.println("curplanet");
	            XplanetPos=(int)curPlanet.getPosition().getX();
	            YplanetPos=(int)curPlanet.getPosition().getY();
	            initXpos=e.getX();
	            initYpos=e.getY();
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            curPlanet=null;
            mouseClicked=false;
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
        	if(curPlanet!=null){
                Position pos=curPlanet.getPosition();
                System.out.println("dragging");
                pos.setX(e.getX()-initXpos+XplanetPos);
                pos.setY(e.getY()-initYpos+YplanetPos);
        	}
        }
        @Override
        public void mouseMoved(MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }
        public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius){
            return java.lang.Math.pow((circleX+radius - clickX),2) + java.lang.Math.pow((circleY+radius -clickY),2) < java.lang.Math.pow(radius,2);
        }
	}
}
