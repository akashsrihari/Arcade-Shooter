
import javax.swing.*;
import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;

class Molecule extends JPanel {
    
	public int[] x = new int[15];
	public int[] y = new int[15];
	public int[] r = new int[15];
	public int[] m = new int[15];
	public int[] vx = new int[15];
	public int[] vy = new int[15];
	
	public Molecule(){
		for(int i=0;i<7;i++){
			x[i]=(int) (300*Math.random());
			y[i]=(int) (300*Math.random());
			r[i]=(int) (10*Math.random())+20;
			m[i]=(int) (10*Math.random());
			vx[i]=(int) (8*Math.random()) + 1;
			vy[i]=(int) (8*Math.random()) + 1;
		}
		for(int j=7;j<15;j++){
			vx[j] = 0;
			vy[j] = -25;
			x[j] = 250;
			y[j] = 500;
		}
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(int i=0;i<7;i++){
			g.setColor(Color.blue);
			g.fillOval(x[i], y[i], r[i], r[i]);
		}
		g.setColor(Color.black);
		g.fillRect(0,0,3,525);
		g.fillRect(0,0,525,3);
		g.fillRect(525,0,3,528);
		g.fillRect(0,525,525,3);
		for(int j=7;j<15;j++)
		g.fillRect(x[j], y[j], 2, 15);
	}
}

public class CreateB implements MouseListener, KeyListener {
	
	Scanner s = new Scanner(System.in);
	public int[] flagx = new int[15];
	public int[] flagy = new int[15];
	JFrame frame,sc; int shot=0;
	Molecule m1;
	int score=0,time=0,count=0,speed = 25;
	
	public CreateB(){
		for(int i=0;i<15;i++){
			flagx[i]=0;
			flagy[i]=0;
		}
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setVisible(true); 
		frame.addMouseListener(this);
	}
	
	public static void main(String[] args){
		CreateB b1 = new CreateB();	
		String check = "y";
		JPanel panel = new JPanel();
		RestartGame b5 = b1.new RestartGame();
		panel.setBackground(Color.darkGray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(b5.restart);
		b1.frame.getContentPane().add(BorderLayout.SOUTH, panel);
		while(check=="y"){
			b1.go();
			System.out.println("restart? enter y or n");
			check = b1.s.next();
			}
		return;
	}
	
	public void go(){
		JPanel panel = new JPanel();
		pauseGame b1 = new pauseGame();
		ResumeGame b2 = new ResumeGame();
		IncSpeed b3 = new IncSpeed();
		DecSpeed b4 = new DecSpeed();
		RestartGame b5 = new RestartGame();
		panel.setBackground(Color.darkGray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(b1.pause);
		panel.add(b2.resume);
		panel.add(b3.inc);
		panel.add(b4.dec);
		panel.add(b5.restart);
		frame.getContentPane().add(BorderLayout.EAST, panel);
		Label l2 = new Label("Shoot from here");
		frame.add(l2);
		l2.setBounds(50,600,100,50);
		m1 = new Molecule();
		frame.add(m1);
		
		for(int i=0; i<300000; i++){
			//m1=collide(m1);
			m1=hit(m1);
			m1=countUp(m1);
			m1.repaint();
			if(allZero(m1))
				break;
			if(i%70==0)
				{time++; System.out.println("Time: " + time);}
			try{
				Thread.sleep(speed);
			}
			catch(Exception ex){}
			}
		
		String s = " time taken is " + time + " score is " + score + " No. of shots " + count;
		Label l = new Label(s);
		m1.add(l);
		l.setBounds(20,20,250,100);
		return;
	}
	
	public Molecule hit(Molecule m1){
		for(int i=0;i<7;i++){
			if(m1.r[i]>0)
			for(int j=7; j<15;j++){
			if(m1.x[j]>=m1.x[i] && m1.x[j]<=m1.x[i]+(2*m1.r[i])){
				if(m1.y[j]>=m1.y[i] && m1.y[j]<=m1.y[i]+(2*m1.r[i]))
					{m1.r[i]=0; score++; System.out.println(" " + score);}
			}}
		}
		return m1;
	}
	
	public boolean allZero(Molecule m1){
		int flag=0;
		for(int i=0;i<7;i++){
			if(m1.r[i]!=0){
				flag=1;
				break;
			}
		}
		if(flag==0)
			return true;
		return false;
		
	}
	
	public Molecule countUp(Molecule m1){
		for(int j=0;j<15;j++){
			if(flagx[j]==0){
				m1.x[j]=m1.x[j]+m1.vx[j];
			}
			if(flagx[j]==1){
				m1.x[j]=m1.x[j]-m1.vx[j];
			}
			if(flagy[j]==0){
				m1.y[j]=m1.y[j]+m1.vy[j];
			}
			if(flagy[j]==1){
				m1.y[j]=m1.y[j]-m1.vy[j];
			}
		}
		
		for(int j=0;j<7;j++){
		if(m1.x[j]>=499)
			flagx[j]=1;
		if(m1.x[j]<=0)
			flagx[j]=0;
		if(m1.y[j]>=499)
			flagy[j]=1;
		if(m1.y[j]<=0)
			flagy[j]=0;
		}
		
//		if(m1.y[7]<=0){
//			m1.y[7]=530;
//			m1.vy[7]=0;
//		}
		
		return m1;
	}
	
	public class IncSpeed implements ActionListener {
		
		JButton inc;
		
		public IncSpeed(){
			inc = new JButton("Increase speed");
			inc.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(speed>0)
			speed-=3;
			for(int i=0; i<7; i++)
				if(m1.r[i] < 60 && m1.r[i]!=0)
				m1.r[i] = m1.r[i] + 2;
		}

	}
	
	public class DecSpeed implements ActionListener {

		JButton dec;
		
		public DecSpeed(){
			dec = new JButton("Decrease speed");
			dec.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			speed+=3;
			for(int i=0; i<7; i++)
				if(m1.r[i]>10)
				m1.r[i] = m1.r[i] - 2;
		}
		
	}
	
	public class pauseGame implements ActionListener{
		
		JButton pause;
		ResumeGame r = new ResumeGame();
		
		public pauseGame(){
			pause = new JButton("Pause Game");
			pause.addActionListener(this);
//			r.start();
		}
		
		public void actionPerformed(ActionEvent e) {
			
			speed = 1000000000;
		}
		
	}
	
	public class ResumeGame extends Thread implements ActionListener {
		
		JButton resume;
		
		public ResumeGame(){
			resume = new JButton("Resume");
			resume.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {

			speed = 25;
			
		}
		
	}
	
	public class RestartGame implements ActionListener {
		
		JButton restart;
		
		public RestartGame(){
			restart = new JButton("Restart");
			restart.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
			time=count=score=0;
			for(int i=0;i<8;i++){
				m1.x[i]=(int) (300*Math.random());
				m1.y[i]=(int) (300*Math.random());
				m1.r[i]=(int) (10*Math.random())+20;
				m1.vx[i]=(int) (8*Math.random()) + 1;
				m1.vy[i]=(int) (8*Math.random()) + 1;
			}
			for(int j=7;j<15;j++){
				m1.vx[j] = 0;
				m1.vy[j] = -25;
				m1.x[j] = 250;
				m1.y[j] = 500;
			}
		}
		
	}

	
	public Molecule collide(Molecule m1){
		
		double d,key,keyx,keyy,x1,x2,y1,y2,dx,dy,dvx,dvy,dvr;
		
		for(int i=0;i<7;i++){
			for(int j=i+1;j<6;j++){
				
				x1=m1.x[i] + m1.r[i];
				y1=m1.y[i] + m1.r[i];
				x2=m1.x[j] + m1.r[j];
				y2=m1.y[j] + m1.r[j];
				
				d= Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
				 
				if(d<=(m1.r[i]+m1.r[j])){
					dvx=m1.vx[i]-m1.vx[j];
					dvy=m1.vy[i]-m1.vy[j];
					dx=x1-x2;
					dy=y1-y2;
					dvr=(dvx*dx)+(dvy*dy);
					key=(2*dvr)/d;
					keyx=key*dx/d;
					keyy=key*dy/d;
					m1.vx[i]=(int)(m1.vx[i]+keyx);
					m1.vy[i]=(int)(m1.vy[i]+keyy);
					m1.vx[j]=(int)(m1.vx[j]-keyx);
					m1.vy[j]=(int)(m1.vy[j]-keyy);
					}
				}
			}
		return m1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = shot%8;
		
		if(e.getY()>530){
		m1.x[7+x] = e.getX();
		m1.y[7+x] = e.getY();
		count++; shot++;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.print("");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.print("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.print("");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.print("");
	}

	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e) {
//		if(e.getKeyCode()==e.VK_L)
//			m1.x[7] = m1.x[7] - 5;
//		
//		if(e.getKeyCode()==e.VK_R)
//			m1.x[7] = m1.x[7] + 5;
//		
		if(e.getKeyCode()==e.VK_S)
			{m1.vy[7] = -35; count++;}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		
	}
}