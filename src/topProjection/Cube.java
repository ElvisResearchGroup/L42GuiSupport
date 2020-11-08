package topProjection;

import java.awt.Color;
import java.awt.Graphics2D;

class Cube{
public static Drawable cube(Color mainColor){return (v,g,x,y,z)->Cube.drawCube(mainColor,v,g,x,y,z);}
static boolean drawableUnderCamera(View v,int z){return v.camera().z*4d>z+10;}
static boolean isTransparent(View v,int x, int y, int z){
  if(drawableUnderCamera(v,z)){return true;}
  if(x<0 || y<0 || z<0)return true;
  if(x>=v.vpSide() ||y>=v.vpSide() ||z>=v.maxZ())return true;
  return v.get(x,y,z) instanceof Drawable.Transparent;
  }
static void drawCube(Color c,View v,Graphics2D g,int x,int y,int z){
  if(!drawableUnderCamera(v,z))return;
  boolean up=y<v.vpSide()/2d-1d;
  boolean down=y>v.vpSide()/2d-1d;
  boolean left=x<v.vpSide()/2d-1d;
  boolean right=x>v.vpSide()/2d-1d;
  boolean paintTop=isTransparent(v,x,y,z+1);
  boolean paintUp= down && isTransparent(v,x,y-1,z);
  boolean paintLeft=right && isTransparent(v,x-1,y,z);
  boolean paintRight=left && isTransparent(v,x+1,y,z);
  boolean paintDown=up && isTransparent(v,x,y+1,z);
  if(paintLeft)
    fill4(v,g,left(colorOf(c,v,x,y,z)),
    v.coordPs(x,y,z), v.coordPs(x,y+1,z), v.coordPs(x,y+1,z+1), v.coordPs(x,y,z+1));
  if(paintUp)
    fill4(v,g,up(colorOf(c,v,x,y,z)),
        v.coordPs(x, y, z),v.coordPs(x+1, y, z),v.coordPs(x+1, y, z+1),v.coordPs(x, y, z+1));
  if(paintRight)
    fill4(v,g,left(colorOf(c,v,x,y,z)),
        v.coordPs(x+1,y,z), v.coordPs(x+1,y+1,z), v.coordPs(x+1,y+1,z+1), v.coordPs(x+1,y,z+1));
  if(paintDown)
     fill4(v,g,up(colorOf(c,v,x,y,z)),
        v.coordPs(x,y+1,z), v.coordPs(x+1,y+1,z), v.coordPs(x+1,y+1,z+1), v.coordPs(x,y+1,z+1));
  if(paintTop)fill4(v,g,colorOf(c,v,x,y,z),
        v.coordPs(x,y,z+1), v.coordPs(x+1,y,z+1), v.coordPs(x+1,y+1,z+1), v.coordPs(x,y+1,z+1));
  }
  static Color colorOf(Color c,View v,int x, int y, int z){
    int variant=z%12;
    if(variant>6)variant=12-variant;
    variant-=3;
    if(variant>0)for(int i=0;i<variant;i+=1)c=mix(c,c.darker());
    else for(int i=0;i<-variant;i+=1)c=mix(c,c.brighter());      
    int snowy=(z-6)/11;
    Color snow=new Color(250,250,250);
    if(snowy>0)for(int i=0;i<snowy;i+=1)c=mix(c,mix(c,snow));
    return c;
    }
  static Color mix(Color c1,Color c2) {
    return new Color(
      (c1.getRed()+c2.getRed())/2,
      (c1.getGreen()+c2.getGreen())/2,
      (c1.getBlue()+c2.getBlue())/2,
      c1.getAlpha()
      );
    }
  static final Color brown1=new Color(233, 168, 81);
  static Color left(Color c) {return mix(c,brown1);}
  static final Color brown2=new Color(154, 87, 25);
  static Color up(Color c) {return mix(c,brown2);}
  static public void fill4(View v,Graphics2D g,Color c, int p1, int p2, int p3,int p4) {
      int[]x= {v.pixelX(p1),v.pixelX(p2),v.pixelX(p3),v.pixelX(p4)};
      int[]y= {v.pixelY(p1),v.pixelY(p2),v.pixelY(p3),v.pixelY(p4)};
      g.setColor(c);
      g.fillPolygon(x, y, 4);
    }
  }