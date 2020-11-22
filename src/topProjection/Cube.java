package topProjection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.stream.IntStream;

abstract class Cube implements Drawable.Full{
  final Color c;Cube(Color c){this.c=c;}
  public static Drawable ground(Color mainColor){return new Ground(mainColor);}
  public static Drawable water(){return new Water();}
  static boolean drawableWithCamera(View v,int z){return v.camera().z*4d>z+10;}
  static boolean isTransparent(View v,int x, int y, int z){
    if(x<0 || y<0 || z<0){return true;}
    if(!drawableWithCamera(v,z)){return true;}
    if(x>=v.vpSide() ||y>=v.vpSide() ||z>=v.maxZ()){return true;}
    return v.vpGet(x,y,z) instanceof Drawable.Transparent;
    }
  static Color mix(Color c1,Color c2) {
    return new Color(
      (c1.getRed()+c2.getRed())/2,
      (c1.getGreen()+c2.getGreen())/2,
      (c1.getBlue()+c2.getBlue())/2,
      c1.getAlpha()
      );
    }
  public void draw(View v,Graphics2D g,int x,int y,int z){
    if(!drawableWithCamera(v,z))return;
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
      fill4(v,g,left(colorOf(v,x,y,z)),
        v.coordPs(x,y,z), v.coordPs(x,y+1,z),
        v.coordPs(x,y+1,z+1), v.coordPs(x,y,z+1));
    if(paintUp)
      fill4(v,g,up(colorOf(v,x,y,z)),
        v.coordPs(x, y, z),v.coordPs(x+1, y, z),
        v.coordPs(x+1, y, z+1),v.coordPs(x, y, z+1));
    if(paintRight)
      fill4(v,g,left(colorOf(v,x,y,z)),
        v.coordPs(x+1,y,z), v.coordPs(x+1,y+1,z),
        v.coordPs(x+1,y+1,z+1), v.coordPs(x+1,y,z+1));
    if(paintDown)
      fill4(v,g,up(colorOf(v,x,y,z)),
        v.coordPs(x,y+1,z), v.coordPs(x+1,y+1,z),
        v.coordPs(x+1,y+1,z+1), v.coordPs(x,y+1,z+1));
    if(paintTop)
      fill4(v,g,colorOf(v,x,y,z),
        v.coordPs(x,y,z+1), v.coordPs(x+1,y,z+1),
        v.coordPs(x+1,y+1,z+1), v.coordPs(x,y+1,z+1));
    }
  abstract Color colorOf(View v,int x, int y, int z);
  static final Color brown1=new Color(233, 168, 81);
  static final Color grass1=new Color(10,200,5);
  static final Color rock1=new Color(100,100,100);
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
class Ground extends Cube{
  Ground(Color c){super(c);}
  Color colorOf(View v,int x, int y, int z){
    Color c=this.c;//so we can twist it locally
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
  }
class Water extends Cube /*implements Transparent*/{
  Water(){super(new Color(5,20,250/*,150*/));}
  int dept(View v,int x, int y, int z){
    int dept=0;
    while(z-dept>0 && v.vpGet(x,y,z-dept) instanceof Water){dept+=1;}
    return dept;
    }
  @Override Color colorOf(View v,int x, int y, int z) {
    int dept=dept(v,x,y,z);
    dept=Math.max(0,Math.min(255,200-dept*14));
    return mix(c,new Color(dept,dept,dept));
    }
}
interface Decoration extends Drawable.Transparent{
  @Override default void draw(View v,Graphics2D g,int x, int y, int z) {
    if(!Cube.drawableWithCamera(v,z)){return;}
    int c11=v.coordPs(x, y, z);
    int c21=v.coordPs(x+1, y, z);
    int c31=v.coordPs(x+1, y+1, z);
    int c41=v.coordPs(x, y+1, z);  
    int c12=v.coordPs(x, y, z+1);
    int c22=v.coordPs(x+1, y, z+1);
    int c32=v.coordPs(x+1, y+1, z+1);
    int c42=v.coordPs(x, y+1, z+1);
    points(g,
      v.pixelX(c11),v.pixelX(c21),
      v.pixelX(c31),v.pixelX(c41),
      v.pixelX(c12),v.pixelX(c22),
      v.pixelX(c32),v.pixelX(c42),
        
      v.pixelY(c11),v.pixelY(c21),
      v.pixelY(c31),v.pixelY(c41),
      v.pixelY(c12),v.pixelY(c22),
      v.pixelY(c32),v.pixelY(c42)      
      );   
  }
  void points(Graphics2D g,
    int x000, int x100,
    int x010, int x110,
    int x001, int x101,
    int x011, int x111,
        
    int y000, int y100,
    int y010, int y110,
    int y001, int y101,
    int y011, int y111
    );
}
class TreeTrunk implements Decoration,Drawable.Tree{
  public void points(Graphics2D g,
    int x000, int x100,
    int x010, int x110,
    int x001, int x101,
    int x011, int x111,
          
    int y000, int y100,
    int y010, int y110,
    int y001, int y101,
    int y011, int y111
    ) {
    int xMid0=(x000+x100+x010+x110)/4;
    int yMid0=(y000+y100+y010+y110)/4;
    int xMid1=(x001+x101+x011+x111)/4;
    int yMid1=(y001+y101+y011+y111)/4;

    g.setColor(Color.black);
    g.setStroke(new BasicStroke(3));
    g.drawLine(xMid0, yMid0, xMid1, yMid1);
   }
}
class TreeLeaves implements Decoration, Drawable.Tree{
  public void points(Graphics2D g,
    int x000, int x100,
    int x010, int x110,
    int x001, int x101,
    int x011, int x111,
          
    int y000, int y100,
    int y010, int y110,
    int y001, int y101,
    int y011, int y111
    ) {
    int xMid=(x000+x100+x010+x110+x001+x101+x011+x111)/8;
    int yMid=(y000+y100+y010+y110+y001+y101+y011+y111)/8;
    g.setColor(new Color(15,250,12,200));
    int r=IntStream.of(x101-x001,x111-x001,y101-y001,y111-y001).max().getAsInt()/2;
    int startX = xMid-(r/2);
    int startY = yMid-(r/2);
    g.fillOval(startX, startY,r,r);
  }
}