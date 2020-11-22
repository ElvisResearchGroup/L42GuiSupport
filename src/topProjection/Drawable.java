package topProjection;

import java.awt.Graphics2D;

public interface Drawable{
void draw(View view,Graphics2D g,int x,int y,int z);
interface Tree extends Drawable{}
interface Full extends Drawable{}
interface Transparent extends Drawable{}
public static final Transparent air=(port,g,x,y,z)->{};
public static final Full ground=new Ground(Ground.brown1);
public static final Full grass=new Ground(Ground.grass1);
public static final Full rock=new Ground(Ground.rock1);
public static final Tree treeTop=new TreeLeaves();
public static final Tree treeTrunk=new TreeTrunk();
public static final Drawable water=new Water();
}