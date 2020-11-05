package l42Gui;

import java.util.HashMap;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import is.L42.platformSpecific.javaEvents.Event;

public class Screens{
  private final JFrame top;
  private final HashMap<String,Function<String,JComponent>>map=new HashMap<>();
  public Screens(JFrame top, Event event, String key){
    this.top=top;
    event.registerEvent(key,(k,id,msg)->action(id,msg));
    }
  public Screens register(String id,Function<String,JComponent>action){
    map.put(id,action);
    return this;
    }
  private void action(String id,String msg){
    var f=map.get(id);
    if(f==null){return;}
    SwingUtilities.invokeLater(()->action(f.apply(msg)));
    }
  private void action(JComponent c){
    top.getContentPane().removeAll();
    top.getContentPane().add(c);
    top.repaint();
    top.setVisible(true);
    }
  }