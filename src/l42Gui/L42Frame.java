package l42Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import is.L42.platformSpecific.javaEvents.Event;

@SuppressWarnings("serial")
public class L42Frame extends JFrame{
  public final Event event;
  public L42Frame(Event event,String name, int x, int y){
    this.event=event;
    event.registerEvent("Kill",(k,id,msg)->
      dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING))
      );
    setTitle(name);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(x,y);
    setVisible(true);
    }
  public void addEast(JComponent c,JComponent that){addPlace(c,that,BorderLayout.EAST);}
  public void addWest(JComponent c,JComponent that){addPlace(c,that,BorderLayout.WEST);}
  public void addNorth(JComponent c,JComponent that){addPlace(c,that,BorderLayout.NORTH);}
  public void addSouth(JComponent c,JComponent that){addPlace(c,that,BorderLayout.SOUTH);}
  public void addCenter(JComponent c,JComponent that){addPlace(c,that,BorderLayout.CENTER);}
  private void addPlace(JComponent c,JComponent that,String s){
    if(!(c.getLayout() instanceof BorderLayout)){c.setLayout(new BorderLayout());}
    c.add(that,s);
    }
  public void addFlow(JComponent c,JComponent that){
    if(!(c.getLayout() instanceof FlowLayout)){c.setLayout(new FlowLayout());}
    c.add(that);
    }
  public static <T extends L42Frame> T openAndPings(int delay,int period,Supplier<T>s){
    T res=open(s);
    res.pings(delay, period);
    return res;
    }
  public static <T extends L42Frame> T open(Supplier<T>s){
    CompletableFuture<T> out=new CompletableFuture<>();
    SwingUtilities.invokeLater(()->{
      try{out.complete(s.get());}
      catch(Throwable t){out.completeExceptionally(t);}
      });
    return completableWait(out);
    }
  private ScheduledThreadPoolExecutor scheduler;
  public synchronized void pings(long delay,long period){
    if(scheduler!=null){scheduler.shutdown();}
    var sched=scheduler=new ScheduledThreadPoolExecutor(1);
    sched.scheduleAtFixedRate(()->{
      try{this.ping();}//if it just throws, it would stop without any info
      catch(Throwable t){
        System.err.println("Log: Java ping events could not continue");
        sched.shutdown();
        t.printStackTrace();
        throw t;}
      },delay,period,TimeUnit.MILLISECONDS);    
    }
  private static <T> T completableWait(CompletableFuture<T> f){
    try{return f.join();}
    catch(CompletionException ce){
      if(ce.getCause() instanceof Error err){throw err;}
      if(ce.getCause() instanceof RuntimeException err){throw err;}
      throw new Error("Unreachable code",ce);
      }    
    }
  public void ping(){}//for overriding
  }