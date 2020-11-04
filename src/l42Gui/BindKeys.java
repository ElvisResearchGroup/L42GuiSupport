package l42Gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import is.L42.platformSpecific.javaEvents.Event;

/**
 * Wow, this was challenging:
 * The current result is:
 * We have pressed/released/typed.
 * Keys with Ctrl/Alt are never 'typed'
 * 'typed' events always contains just a simple 1 ASCII character
 * Ctrl press/releases only accepted on the main alphabetic keys
 * Ctrl-Enter on the main position Enter key is recognized as Ctrl-j or Ctrl-J :(
 * All the other keystrokes, for what I can test, works fine in one of the following formats:
 * # Ctrl-Alt-[A-Z]
 * # anything expressible as a simple 1 ASCII character
 * # Left-Ctrl, Left-Shift, Insert and so on
 * 
 * Example use for a JComponent c
 *   c.setFocusable(true);
 *   c.requestFocusInWindow();
 *   c.addKeyListener(new BindKeys("MainClassName","cPressd","cReleased","cTyped"));
 * @author Marco Servetto
 */
public record BindKeys
  (Event event,String key,String idPressed,String idReleased,String idTyped)
  implements KeyListener{
  public void keyTyped(KeyEvent e){sendEvent(idTyped,e);}
  public void keyPressed(KeyEvent e){sendEvent(idPressed,e);}
  public void keyReleased(KeyEvent e){sendEvent(idReleased,e);}
  private static boolean basic(char ch){
    return "()[]<>&|*+-=/!?;:,. ~@#$%`^_\\{}\"'\n".contains(""+ch)        
      || Character.isAlphabetic(ch) || Character.isDigit(ch);
    }
  private static String eventToS(KeyEvent e){
    String res=preEventToS(e);
    if(res.contains("keyCode: 0x0")){res+=e;}
    return frontEventToS(e)+res;
    }
  private static String charToS(boolean toLow,boolean ctrl,boolean mainKeys,char ch){
    if(ctrl && ch<040){ch+=0100;}
    if(ctrl && !Character.isAlphabetic(ch)){return "";}
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
  private static String frontEventToS(KeyEvent e){
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
  private static String preEventToS(KeyEvent e){
    var kk=e.getKeyCode();
    if(kk==KeyEvent.VK_DOWN || kk==KeyEvent.VK_KP_DOWN){return "Down";}
    if(kk==KeyEvent.VK_UP || kk==KeyEvent.VK_KP_UP){return "Up";}
    if(kk==KeyEvent.VK_LEFT || kk==KeyEvent.VK_KP_LEFT){return "Left";}
    if(kk==KeyEvent.VK_RIGHT || kk==KeyEvent.VK_KP_RIGHT){return "Right";}
    var ch=e.getKeyChar();
    var keyText=KeyEvent.getKeyText(kk);
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
    if(basic(ch)){return charToS(toLow,ctrl,mainKeys,ch);}
    if(keyText.length()!=1){
      if(keyText.contains("keyCode: 0x0")){return charToS(toLow,ctrl,mainKeys,ch);}
      if(keyText.equals("Back Quote")){return charToS(toLow,ctrl,mainKeys,'`');}
      if(keyText.equals("Back Slash")){return charToS(toLow,ctrl,mainKeys,'\\');}
      if(keyText.equals("Close Bracket")){return charToS(toLow,ctrl,mainKeys,'}');}
      if(keyText.equals("Minus")){return charToS(toLow,ctrl,mainKeys,'-');}
      if(ctrl && !(left || right)){return "";}
      return keyText;      
      }
    if(ch<040){ 
      assert ctrl && basic((char)(ch+0100)):e;
      return charToS(toLow,ctrl,mainKeys,ch);
      }
    assert ch==65535 && keyText.length()==1;
    return charToS(toLow,ctrl,mainKeys,keyText.charAt(0));
    }
  private void sendEvent(String id,KeyEvent e){
    String res=eventToS(e);
    if(res.length()==0){return;}
    event.submitEvent(key,id,res);
    }
  }