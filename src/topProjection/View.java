package topProjection;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
public record View(
  Camera camera,
  int side,
  int maxZ,
  List<Drawable>[] items,
  int vpSide,
  int vpMaxZ,
  int[] vpPoints,
  int vpHalf,
  double scale
  ){
  static final int mulZ=2;
  static final double scaleZ=0.5d;
  public View{assert side%4==0 && camera.x>=vpHalf && camera.y>=vpHalf;}//assert vpHalf*2==side;
  public static View of(Camera camera,int side,int maxZ,int vpSide,int vpMaxZ,double scale){
    var vpPoints=new int[(vpSide+1)*(vpSide+1)*(vpMaxZ+2)*mulZ];
    @SuppressWarnings("unchecked")
    List<Drawable>[] items=Stream.generate(()->new ArrayList<Drawable>())
      .limit(side*side).toArray(List[]::new);
    return new View(camera,side,maxZ,items,vpSide,vpMaxZ,vpPoints,vpSide/2,scale);
    }
  public Drawable vpGet(int x, int y, int z){
    return get(x+((int)camera.x)-vpHalf,y+((int)camera.y)-vpHalf,z);
    }
  public void setMap(List<List<Drawable>>map){
    for(int i=0;i<map.size();i++){
      var ii=items[i];
      ii.clear();
      ii.addAll(map.get(i));
      }
    }
  public int pixelX(int coord) {return vpPoints[2*coord];}
  public int pixelY(int coord) {return vpPoints[1+2*coord];}
  public void setPixelX(int coord,int that) {vpPoints[2*coord]=that;}
  public void setPixelY(int coord,int that) {vpPoints[1+2*coord]=that;}
  public void cachePoint(Dimension dim,int x,int y,int z,double xi, double yi,double zi){
    double zd=camera.z-(zi/2d);
    double xd=xi-(vpSide-1d)/2d;
    double yd=yi-(vpSide-1d)/2d;
    double d=Math.sqrt(zd*zd+xd*xd+yd*yd);
    double f=scale/d;
    setPixelX(coordPs(x,y,z),(int)(scale*xd/d-f/2d)+dim.width/2);
    setPixelY(coordPs(x,y,z),(int)(scale*yd/d-f/2d)+dim.height/2);
    }
  public int coordPs(int x,int y,int z){
    assert x>=0 && y>=0 && z>=0;
    var s1=vpSide+1;
    assert x<s1 && z<vpMaxZ+1;
    return x+y*s1+z*s1*s1;
    }
  public int coordDs(int x,int y,int z){
    assert x>=0 && y>=0 && z>=0;
    assert x<vpSide && y<vpSide && z<vpMaxZ;
    return x+y*vpSide+z*vpSide*vpSide;
    }
  public Drawable get(int x, int y, int z) {
    if(x<0 || y<0){return Drawable.air;}
    assert x>=0 && y>=0 && side>=0: x+" "+y+" "+side;
    var lz=items[x+y*side];
    if(lz.size()<=z) {return Drawable.air;}
    return lz.get(z);
    } 
  public void set(int x, int y, int z,Drawable item) {
    assert x>=0 && y>=0;
    var lz=items[x+y*side];
    while(lz.size()<=z)lz.add(Drawable.air);
    lz.set(z,item);
    }
  public void drawCell(Graphics2D g,int x,int y,int z){
    vpGet(x,y,z).draw(this,g,x,y,z);
    }
  public void visitQuadrants(Graphics2D g){
    assert (vpSide-1)%2!=0;
    for(int z = 0; z < vpMaxZ; z+=1){drawQuadrantsLevel(g,z);}
    }
  private void drawQuadrantsLevel(Graphics2D g,int z) {
    int max=(vpSide-1)/2;
    for(int y = 0; y < max; y+=1)
      for (int x = 0; x < max; x+=1)
        drawCell(g,x,y,z);
    for(int y = 0; y < max; y+=1)
      for (int x = max; x > 0; x-=1)
        drawCell(g,max+x,y,z); 
    for(int y = max; y > 0; y-=1)
      for (int x = max; x > 0; x-=1)
        drawCell(g,max+x,max+y,z);
    for(int y = max; y > 0; y-=1)
      for (int x = 0; x < max; x+=1)
        drawCell(g,x,max+y,z);
    for (int x = 0; x < max; x+=1)
      drawCell(g,x,max,z);
    for (int x = max; x > 0; x-=1)
      drawCell(g,max+x,max,z);
    for (int y = 0; y < max; y+=1)
      drawCell(g,max,y,z);
    for (int y = max; y > 0; y-=1)
      drawCell(g,max,max+y,z);
    drawCell(g,max,max,z);
    }
  public void updateCurrentViewPort(Dimension dim) {
    assert camera.x>=0d && camera.y>=0d;
    int offX=(int)camera.x;
    int offY=(int)camera.y;
    double extraX=camera.x-offX;
    double extraY=camera.y-offY;
    for(int z=0;z<vpMaxZ+1;z++) for(int x=0;x<vpSide;x++)for(int y=0;y<vpSide;y++)
      cachePoint(dim,x, y, z, x+1d-extraX, y+1d-extraY,z*scaleZ);
    }
  }