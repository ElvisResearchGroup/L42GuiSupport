package l42Gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class PaintablePanel extends JPanel{
  abstract void paint(Graphics2D g);
  @Override protected void paintComponent(Graphics g){
    super.paintComponent(g);
    paint((Graphics2D)g);
    Toolkit.getDefaultToolkit().sync();
    }
  }