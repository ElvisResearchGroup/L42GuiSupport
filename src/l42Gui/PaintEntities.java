package l42Gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class PaintEntities extends PaintablePanel{
  public record Entity(int x, int y, int radius, double rotation,String imgUrl){}
  private LinkedHashMap<String,List<Entity>> entities=new LinkedHashMap<>();
  @Override public void paint(Graphics2D g){
    for(var es:entities.values()){for(var e:es){draw(g,e);}}
    }
  public void draw(Graphics2D g,Entity e){
    var r=e.radius();
    Image img=img(e.imgUrl());
    g.drawImage(img,e.x()-r,e.y()-r,r*2,r*2,null);
    }
  public void submit(String name,String newEntities,boolean repaint){
    entities.put(name,parse(newEntities));
    if(repaint){repaint();}
    }
  public List<Entity> parse(String newEntities){
    return newEntities.lines().map(this::parse1).collect(Collectors.toList());
    }
  public Entity parse1(String s){
    var ss=s.split(";");
    if(ss.length<5){throw new Error("Invalid format for "+s);}
    var x=Integer.parseInt(ss[0]);
    var y=Integer.parseInt(ss[1]);
    var r=Integer.parseInt(ss[2]);
    var rot=Double.parseDouble(ss[3]);
    var len=4+ss[0].length()+ss[1].length()+ss[2].length()+ss[3].length();
    return new Entity(x,y,r,rot,s.substring(len).trim());
    }
  private static HashMap<String,BufferedImage>imgs=new HashMap<>();
  public static BufferedImage img(String s){
    return imgs.computeIfAbsent(s,s0->loadImg(s0));
    }
  public static BufferedImage loadImg(String s){
    if(!s.contains("://")){
      try{s=new File(s).toURI().toURL().toString();}
      catch(IOException e){throw new Error("The url '"+s+"' is not well formed",e);}
      }
    try{return ImageIO.read(new URL(s));}
    catch(IOException e){throw new Error(e);}
    }
  }