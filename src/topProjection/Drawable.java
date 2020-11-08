package topProjection;

import java.awt.Graphics2D;

public interface Drawable{
void draw(View view,Graphics2D g,int x,int y,int z);
interface Tree extends Drawable{}
interface Full extends Drawable{}
interface Transparent extends Drawable{}
public static final Drawable air=(port,g,x,y,z)->{};
public static final Full ground=(v,g,x,y,z)->{Cube.drawCube(Cube.brown1,v,g,x,y,z);};
public static final Full grass=(port,g,x,y,z)->{};
public static final Full rock=(port,g,x,y,z)->{};
public static final Tree treeTop=(port,g,x,y,z)->{};
public static final Tree treeTrunk=(port,g,x,y,z)->{};
public static final Drawable water=(view,g,x,y,z)->{};
}