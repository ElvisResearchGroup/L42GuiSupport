package topProjection;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(()->{
      var f=new JFrame();
      //side 52 may be important      
      var v=View.of(new Camera(37,37,40),300,300,52,100,450d);
      @SuppressWarnings("serial")
      var mapView=new JComponent(){
        {setDoubleBuffered(false);}
        @Override public Dimension getPreferredSize(){return new Dimension(800,800);}
        @Override public void paintComponent(Graphics g){
          var g2d=(Graphics2D)g;
          v.updateCurrentViewPort(this.getSize());
          v.visitQuadrants(g2d);
          }
        };
      int i=70;
      for(int xi=0;xi<55;xi+=1){
        i-=1;
        for(int yi=0;yi<100;yi+=1)
          v.set(xi,yi,i,Drawable.ground);
        }
      f.add(mapView,BorderLayout.CENTER);
      var b1=new JButton("Z+");
      b1.addActionListener(e->{v.camera().z+=1;f.repaint();});
      f.add(b1,BorderLayout.EAST);
      var b2=new JButton("X+");
      b2.addActionListener(e->{v.camera().x+=1;f.repaint();});
      f.add(b2,BorderLayout.SOUTH);
      var b3=new JButton("Y+");
      b3.addActionListener(e->{v.camera().y+=1;f.repaint();});
      f.add(b3,BorderLayout.NORTH);
      f.pack();
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setVisible(true);
      });
    }
  }