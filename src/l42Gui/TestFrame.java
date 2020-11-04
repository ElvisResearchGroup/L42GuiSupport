package l42Gui;

import java.awt.event.KeyListener;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import is.L42.platformSpecific.javaEvents.Event;

public class TestFrame {
/*  static boolean basic(char ch){
    return "()[]<>&|*+-=/!?;:,. ~@#$%`^_\\{}\"'\n".contains(""+ch)        
      || Character.isAlphabetic(ch) || Character.isDigit(ch);
    }
  static String eventToS(KeyEvent e){
    String res=preEventToS(e);
    if(res.contains("keyCode: 0x0")){res+=e;}
    return frontEventToS(e)+res;
    }
  static String charToS(boolean toLow,boolean ctrl,boolean mainKeys,char ch){
    if(ctrl && ch<040){ch+=0100;}
    if(ctrl){//early termination to avoid system dependencies
      if(!Character.isAlphabetic(ch)){return "";}
      }
    if(!mainKeys && ch=='J'){return "\n";}
    if(toLow){ch=Character.toLowerCase(ch);}
    return switch(ch){
      case '\b'->"Backspace";
      case '\t'->"Tab";
      case '\u0018'->"Cancel";
      case '\u001b'->"Escape";
      case '\u007f'->"Delete";
      default ->""+ch;
      };
    }
  static String frontEventToS(KeyEvent e){
    var numPad=e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD;
    if(numPad){return "NumPad-";}
    var left=e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT;
    if(left){return "Left-";}
    var right=e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT;
    if(right){return "Right-";}
    var ctrl=(e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
    var alt=(e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0;
    return (ctrl?"Ctrl-":"")+(alt?"Alt-":"");    
    }
  static String preEventToS(KeyEvent e){
    var ch=e.getKeyChar();
    var keyText=KeyEvent.getKeyText(e.getKeyCode());
    boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    var shift=(e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0;
    var toLow=shift==caps;
    var ctrl=(e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
    var alt=(e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0;
    var typed=e.getKeyLocation() == KeyEvent.KEY_LOCATION_UNKNOWN;
    var mainKeys=e.getKeyLocation() == KeyEvent.KEY_LOCATION_STANDARD;
    var left=e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT;
    var right=e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT;
    if((ctrl||alt)&& typed){return "";}
    if(basic(ch)){
      return charToS(toLow,ctrl,mainKeys,ch);
      }
    if(keyText.length()!=1){
      //TODO: enter is control j?
      //do we care? or simply, only 1 char can be typed? controls/Alts should never be 'typed': if there is a '-' plus more stuff, it is never typed
      if(keyText.contains("keyCode: 0x0")){return charToS(toLow,ctrl,mainKeys,ch);}
      if(keyText.equals("Back Quote")){return charToS(toLow,ctrl,mainKeys,'`');}
      if(keyText.equals("Back Slash")){return charToS(toLow,ctrl,mainKeys,'\\');}
      if(keyText.equals("Close Bracket")){return charToS(toLow,ctrl,mainKeys,'}');}
      if(keyText.equals("Minus")){return charToS(toLow,ctrl,mainKeys,'-');}
      if(ctrl && !(left || right)){return "";}
      return keyText;      
      }
    if(ch<040){ 
      assert basic((char)(ch+0100)):e;
      assert ctrl;
      return charToS(toLow,ctrl,mainKeys,ch);
      }
    assert ch==65535;
    assert keyText.length()==1;
    return charToS(toLow,ctrl,mainKeys,keyText.charAt(0));
    }
  static String eeventToS(KeyEvent e){
    //System.err.println(e);
    var ch=e.getKeyChar();
    var keyCode=e.getKeyCode();
    var keyText=KeyEvent.getKeyText(e.getKeyCode());
    boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    var shift=(e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0;
    var ctrl=(e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
    var alt=(e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0;
    var numPad=e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD;
    var front=(numPad?"NumPad-":"")+(ctrl?"Ctrl-":"")+(alt?"Alt-":"");
    //if(action.contains("keyCode: 0x0")){return "Escape**";}//Ctrl-G
    if(keyCode >= KeyEvent.VK_SPACE){
      System.out.println("wiggled keycode: "+keyCode);
      if(ch<040){ch+=0100;}
      if(keyText.length()==1 && ch==Character.MAX_VALUE){ch=keyText.charAt(0);}
      }
    var basic=
        "()[]<>&|*+-=/!?;:,. ~@#$%`^_\\{}\"'\n".contains(""+ch)        
        || Character.isAlphabetic(ch)
        || Character.isDigit(ch);
    if(basic){return front+ch;}
    if(keyText.length()!=1){return front+keyText;}
    //assume letter
    if(shift==caps){keyText=keyText.toLowerCase();}
    return front+keyText;
    //var left=e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT;
    //var right=e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT;
    //var ch=e.getKeyChar();
    //var base=
    //    "()[]<>&|*+-=/!?;:,. ~@#$%`^_\\{}\"'\n".contains(""+ch)        
    //    || Character.isAlphabetic(ch)
    //    || Character.isDigit(ch);
    //if(base){return front+ch;}
    //return front+ch;
    //var action=KeyEvent.getKeyText(e.getKeyCode());
    //if(action.contains("keyCode: 0x0")){return "Escape**";}//Ctrl-G
    //TODOs: Ctrl and letter, Alt and letter, left/right ctrl/alt
    //return action;
    //use action and digit/alphabetic + caps/shift (caps inverts the effect of shift on alpha)
    //call toLowercase to get the printable version
    }*/
  @SuppressWarnings("serial")
  public static void main(String[]a){
    Event.test_only_initialize();
    var g="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png";
    @SuppressWarnings("unused")
    var f=L42Frame.openAndPings(100,30,()->new L42Frame(Event.instance(),"Foo",100,200){
      int posX=20;
      int posY=70;
      @Override public void ping(){
        posX+=1;
        posY+=1;
        e.submit("base",posX+";"+posY+";15;0;"+g,false);
        repaint();
        }
      JLabel label1=new JLabel("yo!");
      JLabel label2=new JLabel("west");
      PaintEntities e=new PaintEntities(){
        @Override protected void paint(Graphics2D g){
          super.paint(g);
          //var font = new java.awt.Font("Serif", java.awt.Font.PLAIN, 12);          
          //g.setFont(font);         
          g.drawString("This is a test string", 100, 200);
          }
        };
      JPanel p=new JPanel();
      { Event.initialize(); 
        p.setFocusable(true);
        p.requestFocusInWindow();
        p.addKeyListener(new BindKeys(Event.instance(),"","cPressd","cReleased","cTyped"));
        /*p.addKeyListener(new KeyListener(){
          public void keyTyped(KeyEvent e){System.out.println("TYPED ["+eventToS(e)+"]"+e.getKeyLocation());}
          public void keyPressed(KeyEvent e){System.out.println("PRESSED ["+eventToS(e)+"]"+e.getKeyLocation());}
          public void keyReleased(KeyEvent e){}//System.out.println("RELEASED ["+eventToS(e)+"]");}
          }); */                    
        //p.getInputMap().put(KeyStroke.getKeyStroke('8'),"L42_up");
        //p.getActionMap().put("L42_up",e->{System.out.println("go up");});
      }
      
      {add(p);}
      {addEast(p,label1);}
      {addCenter(p,e);}
      {addWest(p,label2);}
      });
    //f.pings(100,30);
    }
  }
