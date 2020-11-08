package topProjection;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import l42Gui.PaintablePanel;

@SuppressWarnings("serial")
public class PaintProjection extends PaintablePanel{
  private View v;
  private HashMap<String,Drawable> names=new HashMap<>();
  public PaintProjection(View v){this.v=v;}
  public PaintProjection name(String name, Drawable d){
    names.put(name,d);
    return this;
    }
  public View view(){return v;}
  @Override public void paint(Graphics2D g){v.visitQuadrants(g);}
  public void cameraPos(String pos){
    var xyz=pos.split(";");
    v.camera().x=Double.parseDouble(xyz[0]);
    v.camera().y=Double.parseDouble(xyz[1]);
    v.camera().z=Double.parseDouble(xyz[2]);
    v.updateCurrentViewPort(this.getSize());
    repaint();
    }
  public void map(String map){
    List<List<Drawable>>res=map.lines()//each line is a pile of items with ever growing z
      . map(this::parseColumn).collect(Collectors.toList());
    v.setMap(res);
    }
  public List<Drawable> parseColumn(String col){
    var res=new ArrayList<Drawable>();
    for(var s:col.split(";")){
      var d=names.get(s);
      if(d==null){throw new Error("Invalid drawable name:"+s);}
      res.add(d);
      }
    return res;
    }
  }