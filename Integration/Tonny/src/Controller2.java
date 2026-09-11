import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class Controller2 extends ClockDomain{
  public Controller2(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public Signal pusherRetracted = new Signal("pusherRetracted", Signal.INPUT);
  public Signal pusherExtended = new Signal("pusherExtended", Signal.INPUT);
  public Signal WPgripped = new Signal("WPgripped", Signal.INPUT);
  public Signal armAtSource = new Signal("armAtSource", Signal.INPUT);
  public Signal armAtDest = new Signal("armAtDest", Signal.INPUT);
  public Signal empty = new Signal("empty", Signal.INPUT);
  public Signal request = new Signal("request", Signal.INPUT);
  public Signal mode = new Signal("mode", Signal.INPUT);
  public Signal pusherExtendM = new Signal("pusherExtendM", Signal.INPUT);
  public Signal vacOnM = new Signal("vacOnM", Signal.INPUT);
  public Signal armSourceM = new Signal("armSourceM", Signal.INPUT);
  public Signal armDestM = new Signal("armDestM", Signal.INPUT);
  public Signal pusherExtend = new Signal("pusherExtend", Signal.OUTPUT);
  public Signal vacOn = new Signal("vacOn", Signal.OUTPUT);
  public Signal armSource = new Signal("armSource", Signal.OUTPUT);
  public Signal armDest = new Signal("armDest", Signal.OUTPUT);
  private Signal state_1;
  private int S1790 = 1;
  private int S1541 = 1;
  private int S1631 = 1;
  private int S1552 = 1;
  private int S1563 = 1;
  private int S1574 = 1;
  private int S1585 = 1;
  private int S1596 = 1;
  private int S1607 = 1;
  private int S1618 = 1;
  private int S1629 = 1;
  private int S1665 = 1;
  private int S1639 = 1;
  private int S1647 = 1;
  private int S1655 = 1;
  private int S1663 = 1;
  
  private int[] ends = new int[16];
  private int[] tdone = new int[16];
  
  public void thread1823(int [] tdone, int [] ends){
        switch(S1663){
      case 0 : 
        active[15]=0;
        ends[15]=0;
        tdone[15]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 2){//sysj\controller2.sysj line: 45, column: 22
          pusherExtend.setPresent();//sysj\controller2.sysj line: 45, column: 45
          currsigs.addElement(pusherExtend);
          active[15]=1;
          ends[15]=1;
          tdone[15]=1;
        }
        else {
          active[15]=1;
          ends[15]=1;
          tdone[15]=1;
        }
        break;
      
    }
  }

  public void thread1822(int [] tdone, int [] ends){
        switch(S1655){
      case 0 : 
        active[14]=0;
        ends[14]=0;
        tdone[14]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 4 | (Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 5){//sysj\controller2.sysj line: 43, column: 22
          armSource.setPresent();//sysj\controller2.sysj line: 43, column: 68
          currsigs.addElement(armSource);
          active[14]=1;
          ends[14]=1;
          tdone[14]=1;
        }
        else {
          active[14]=1;
          ends[14]=1;
          tdone[14]=1;
        }
        break;
      
    }
  }

  public void thread1821(int [] tdone, int [] ends){
        switch(S1647){
      case 0 : 
        active[13]=0;
        ends[13]=0;
        tdone[13]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 5 | (Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 6){//sysj\controller2.sysj line: 41, column: 22
          vacOn.setPresent();//sysj\controller2.sysj line: 41, column: 68
          currsigs.addElement(vacOn);
          active[13]=1;
          ends[13]=1;
          tdone[13]=1;
        }
        else {
          active[13]=1;
          ends[13]=1;
          tdone[13]=1;
        }
        break;
      
    }
  }

  public void thread1820(int [] tdone, int [] ends){
        switch(S1639){
      case 0 : 
        active[12]=0;
        ends[12]=0;
        tdone[12]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) != 4 && (Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) != 5){//sysj\controller2.sysj line: 39, column: 22
          armDest.setPresent();//sysj\controller2.sysj line: 39, column: 70
          currsigs.addElement(armDest);
          active[12]=1;
          ends[12]=1;
          tdone[12]=1;
        }
        else {
          active[12]=1;
          ends[12]=1;
          tdone[12]=1;
        }
        break;
      
    }
  }

  public void thread1819(int [] tdone, int [] ends){
        switch(S1665){
      case 0 : 
        active[11]=0;
        ends[11]=0;
        tdone[11]=1;
        break;
      
      case 1 : 
        thread1820(tdone,ends);
        thread1821(tdone,ends);
        thread1822(tdone,ends);
        thread1823(tdone,ends);
        int biggest1824 = 0;
        if(ends[12]>=biggest1824){
          biggest1824=ends[12];
        }
        if(ends[13]>=biggest1824){
          biggest1824=ends[13];
        }
        if(ends[14]>=biggest1824){
          biggest1824=ends[14];
        }
        if(ends[15]>=biggest1824){
          biggest1824=ends[15];
        }
        if(biggest1824 == 1){
          active[11]=1;
          ends[11]=1;
          tdone[11]=1;
        }
        //FINXME code
        if(biggest1824 == 0){
          S1665=0;
          active[11]=0;
          ends[11]=0;
          tdone[11]=1;
        }
        break;
      
    }
  }

  public void thread1817(int [] tdone, int [] ends){
        switch(S1629){
      case 0 : 
        active[10]=0;
        ends[10]=0;
        tdone[10]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 7){//sysj\controller2.sysj line: 31, column: 21
          if(!WPgripped.getprestatus() && !empty.getprestatus()){//sysj\controller2.sysj line: 31, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 31, column: 74
            currsigs.addElement(state_1);
            state_1.setValue(2);//sysj\controller2.sysj line: 31, column: 74
            active[10]=1;
            ends[10]=1;
            tdone[10]=1;
          }
          else {
            active[10]=1;
            ends[10]=1;
            tdone[10]=1;
          }
        }
        else {
          active[10]=1;
          ends[10]=1;
          tdone[10]=1;
        }
        break;
      
    }
  }

  public void thread1816(int [] tdone, int [] ends){
        switch(S1618){
      case 0 : 
        active[9]=0;
        ends[9]=0;
        tdone[9]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 6){//sysj\controller2.sysj line: 29, column: 21
          if(armAtDest.getprestatus()){//sysj\controller2.sysj line: 29, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 29, column: 63
            currsigs.addElement(state_1);
            state_1.setValue(7);//sysj\controller2.sysj line: 29, column: 63
            active[9]=1;
            ends[9]=1;
            tdone[9]=1;
          }
          else {
            active[9]=1;
            ends[9]=1;
            tdone[9]=1;
          }
        }
        else {
          active[9]=1;
          ends[9]=1;
          tdone[9]=1;
        }
        break;
      
    }
  }

  public void thread1815(int [] tdone, int [] ends){
        switch(S1607){
      case 0 : 
        active[8]=0;
        ends[8]=0;
        tdone[8]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 5){//sysj\controller2.sysj line: 27, column: 21
          if(WPgripped.getprestatus()){//sysj\controller2.sysj line: 27, column: 51
            state_1.setPresent();//sysj\controller2.sysj line: 27, column: 62
            currsigs.addElement(state_1);
            state_1.setValue(6);//sysj\controller2.sysj line: 27, column: 62
            active[8]=1;
            ends[8]=1;
            tdone[8]=1;
          }
          else {
            active[8]=1;
            ends[8]=1;
            tdone[8]=1;
          }
        }
        else {
          active[8]=1;
          ends[8]=1;
          tdone[8]=1;
        }
        break;
      
    }
  }

  public void thread1814(int [] tdone, int [] ends){
        switch(S1596){
      case 0 : 
        active[7]=0;
        ends[7]=0;
        tdone[7]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 4){//sysj\controller2.sysj line: 25, column: 21
          if(armAtSource.getprestatus()){//sysj\controller2.sysj line: 25, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 25, column: 65
            currsigs.addElement(state_1);
            state_1.setValue(5);//sysj\controller2.sysj line: 25, column: 65
            active[7]=1;
            ends[7]=1;
            tdone[7]=1;
          }
          else {
            active[7]=1;
            ends[7]=1;
            tdone[7]=1;
          }
        }
        else {
          active[7]=1;
          ends[7]=1;
          tdone[7]=1;
        }
        break;
      
    }
  }

  public void thread1813(int [] tdone, int [] ends){
        switch(S1585){
      case 0 : 
        active[6]=0;
        ends[6]=0;
        tdone[6]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 3){//sysj\controller2.sysj line: 23, column: 21
          if(pusherRetracted.getprestatus()){//sysj\controller2.sysj line: 23, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 23, column: 69
            currsigs.addElement(state_1);
            state_1.setValue(4);//sysj\controller2.sysj line: 23, column: 69
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          active[6]=1;
          ends[6]=1;
          tdone[6]=1;
        }
        break;
      
    }
  }

  public void thread1812(int [] tdone, int [] ends){
        switch(S1574){
      case 0 : 
        active[5]=0;
        ends[5]=0;
        tdone[5]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 2){//sysj\controller2.sysj line: 21, column: 21
          if(pusherExtended.getprestatus()){//sysj\controller2.sysj line: 21, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 21, column: 68
            currsigs.addElement(state_1);
            state_1.setValue(3);//sysj\controller2.sysj line: 21, column: 68
            active[5]=1;
            ends[5]=1;
            tdone[5]=1;
          }
          else {
            active[5]=1;
            ends[5]=1;
            tdone[5]=1;
          }
        }
        else {
          active[5]=1;
          ends[5]=1;
          tdone[5]=1;
        }
        break;
      
    }
  }

  public void thread1811(int [] tdone, int [] ends){
        switch(S1563){
      case 0 : 
        active[4]=0;
        ends[4]=0;
        tdone[4]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 1){//sysj\controller2.sysj line: 19, column: 21
          if(armAtDest.getprestatus()){//sysj\controller2.sysj line: 19, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 19, column: 63
            currsigs.addElement(state_1);
            state_1.setValue(2);//sysj\controller2.sysj line: 19, column: 63
            active[4]=1;
            ends[4]=1;
            tdone[4]=1;
          }
          else {
            active[4]=1;
            ends[4]=1;
            tdone[4]=1;
          }
        }
        else {
          active[4]=1;
          ends[4]=1;
          tdone[4]=1;
        }
        break;
      
    }
  }

  public void thread1810(int [] tdone, int [] ends){
        switch(S1552){
      case 0 : 
        active[3]=0;
        ends[3]=0;
        tdone[3]=1;
        break;
      
      case 1 : 
        if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 0){//sysj\controller2.sysj line: 17, column: 21
          if(request.getprestatus() && !empty.getprestatus()){//sysj\controller2.sysj line: 17, column: 52
            state_1.setPresent();//sysj\controller2.sysj line: 17, column: 71
            currsigs.addElement(state_1);
            state_1.setValue(1);//sysj\controller2.sysj line: 17, column: 71
            active[3]=1;
            ends[3]=1;
            tdone[3]=1;
          }
          else {
            active[3]=1;
            ends[3]=1;
            tdone[3]=1;
          }
        }
        else {
          active[3]=1;
          ends[3]=1;
          tdone[3]=1;
        }
        break;
      
    }
  }

  public void thread1809(int [] tdone, int [] ends){
        switch(S1631){
      case 0 : 
        active[2]=0;
        ends[2]=0;
        tdone[2]=1;
        break;
      
      case 1 : 
        thread1810(tdone,ends);
        thread1811(tdone,ends);
        thread1812(tdone,ends);
        thread1813(tdone,ends);
        thread1814(tdone,ends);
        thread1815(tdone,ends);
        thread1816(tdone,ends);
        thread1817(tdone,ends);
        int biggest1818 = 0;
        if(ends[3]>=biggest1818){
          biggest1818=ends[3];
        }
        if(ends[4]>=biggest1818){
          biggest1818=ends[4];
        }
        if(ends[5]>=biggest1818){
          biggest1818=ends[5];
        }
        if(ends[6]>=biggest1818){
          biggest1818=ends[6];
        }
        if(ends[7]>=biggest1818){
          biggest1818=ends[7];
        }
        if(ends[8]>=biggest1818){
          biggest1818=ends[8];
        }
        if(ends[9]>=biggest1818){
          biggest1818=ends[9];
        }
        if(ends[10]>=biggest1818){
          biggest1818=ends[10];
        }
        if(biggest1818 == 1){
          active[2]=1;
          ends[2]=1;
          tdone[2]=1;
        }
        //FINXME code
        if(biggest1818 == 0){
          S1631=0;
          active[2]=0;
          ends[2]=0;
          tdone[2]=1;
        }
        break;
      
    }
  }

  public void thread1806(int [] tdone, int [] ends){
        S1663=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 2){//sysj\controller2.sysj line: 45, column: 22
      pusherExtend.setPresent();//sysj\controller2.sysj line: 45, column: 45
      currsigs.addElement(pusherExtend);
      active[15]=1;
      ends[15]=1;
      tdone[15]=1;
    }
    else {
      active[15]=1;
      ends[15]=1;
      tdone[15]=1;
    }
  }

  public void thread1805(int [] tdone, int [] ends){
        S1655=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 4 | (Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 5){//sysj\controller2.sysj line: 43, column: 22
      armSource.setPresent();//sysj\controller2.sysj line: 43, column: 68
      currsigs.addElement(armSource);
      active[14]=1;
      ends[14]=1;
      tdone[14]=1;
    }
    else {
      active[14]=1;
      ends[14]=1;
      tdone[14]=1;
    }
  }

  public void thread1804(int [] tdone, int [] ends){
        S1647=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 5 | (Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 6){//sysj\controller2.sysj line: 41, column: 22
      vacOn.setPresent();//sysj\controller2.sysj line: 41, column: 68
      currsigs.addElement(vacOn);
      active[13]=1;
      ends[13]=1;
      tdone[13]=1;
    }
    else {
      active[13]=1;
      ends[13]=1;
      tdone[13]=1;
    }
  }

  public void thread1803(int [] tdone, int [] ends){
        S1639=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) != 4 && (Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) != 5){//sysj\controller2.sysj line: 39, column: 22
      armDest.setPresent();//sysj\controller2.sysj line: 39, column: 70
      currsigs.addElement(armDest);
      active[12]=1;
      ends[12]=1;
      tdone[12]=1;
    }
    else {
      active[12]=1;
      ends[12]=1;
      tdone[12]=1;
    }
  }

  public void thread1802(int [] tdone, int [] ends){
        S1665=1;
    thread1803(tdone,ends);
    thread1804(tdone,ends);
    thread1805(tdone,ends);
    thread1806(tdone,ends);
    int biggest1807 = 0;
    if(ends[12]>=biggest1807){
      biggest1807=ends[12];
    }
    if(ends[13]>=biggest1807){
      biggest1807=ends[13];
    }
    if(ends[14]>=biggest1807){
      biggest1807=ends[14];
    }
    if(ends[15]>=biggest1807){
      biggest1807=ends[15];
    }
    if(biggest1807 == 1){
      active[11]=1;
      ends[11]=1;
      tdone[11]=1;
    }
  }

  public void thread1800(int [] tdone, int [] ends){
        S1629=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 7){//sysj\controller2.sysj line: 31, column: 21
      if(!WPgripped.getprestatus() && !empty.getprestatus()){//sysj\controller2.sysj line: 31, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 31, column: 74
        currsigs.addElement(state_1);
        state_1.setValue(2);//sysj\controller2.sysj line: 31, column: 74
        active[10]=1;
        ends[10]=1;
        tdone[10]=1;
      }
      else {
        active[10]=1;
        ends[10]=1;
        tdone[10]=1;
      }
    }
    else {
      active[10]=1;
      ends[10]=1;
      tdone[10]=1;
    }
  }

  public void thread1799(int [] tdone, int [] ends){
        S1618=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 6){//sysj\controller2.sysj line: 29, column: 21
      if(armAtDest.getprestatus()){//sysj\controller2.sysj line: 29, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 29, column: 63
        currsigs.addElement(state_1);
        state_1.setValue(7);//sysj\controller2.sysj line: 29, column: 63
        active[9]=1;
        ends[9]=1;
        tdone[9]=1;
      }
      else {
        active[9]=1;
        ends[9]=1;
        tdone[9]=1;
      }
    }
    else {
      active[9]=1;
      ends[9]=1;
      tdone[9]=1;
    }
  }

  public void thread1798(int [] tdone, int [] ends){
        S1607=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 5){//sysj\controller2.sysj line: 27, column: 21
      if(WPgripped.getprestatus()){//sysj\controller2.sysj line: 27, column: 51
        state_1.setPresent();//sysj\controller2.sysj line: 27, column: 62
        currsigs.addElement(state_1);
        state_1.setValue(6);//sysj\controller2.sysj line: 27, column: 62
        active[8]=1;
        ends[8]=1;
        tdone[8]=1;
      }
      else {
        active[8]=1;
        ends[8]=1;
        tdone[8]=1;
      }
    }
    else {
      active[8]=1;
      ends[8]=1;
      tdone[8]=1;
    }
  }

  public void thread1797(int [] tdone, int [] ends){
        S1596=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 4){//sysj\controller2.sysj line: 25, column: 21
      if(armAtSource.getprestatus()){//sysj\controller2.sysj line: 25, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 25, column: 65
        currsigs.addElement(state_1);
        state_1.setValue(5);//sysj\controller2.sysj line: 25, column: 65
        active[7]=1;
        ends[7]=1;
        tdone[7]=1;
      }
      else {
        active[7]=1;
        ends[7]=1;
        tdone[7]=1;
      }
    }
    else {
      active[7]=1;
      ends[7]=1;
      tdone[7]=1;
    }
  }

  public void thread1796(int [] tdone, int [] ends){
        S1585=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 3){//sysj\controller2.sysj line: 23, column: 21
      if(pusherRetracted.getprestatus()){//sysj\controller2.sysj line: 23, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 23, column: 69
        currsigs.addElement(state_1);
        state_1.setValue(4);//sysj\controller2.sysj line: 23, column: 69
        active[6]=1;
        ends[6]=1;
        tdone[6]=1;
      }
      else {
        active[6]=1;
        ends[6]=1;
        tdone[6]=1;
      }
    }
    else {
      active[6]=1;
      ends[6]=1;
      tdone[6]=1;
    }
  }

  public void thread1795(int [] tdone, int [] ends){
        S1574=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 2){//sysj\controller2.sysj line: 21, column: 21
      if(pusherExtended.getprestatus()){//sysj\controller2.sysj line: 21, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 21, column: 68
        currsigs.addElement(state_1);
        state_1.setValue(3);//sysj\controller2.sysj line: 21, column: 68
        active[5]=1;
        ends[5]=1;
        tdone[5]=1;
      }
      else {
        active[5]=1;
        ends[5]=1;
        tdone[5]=1;
      }
    }
    else {
      active[5]=1;
      ends[5]=1;
      tdone[5]=1;
    }
  }

  public void thread1794(int [] tdone, int [] ends){
        S1563=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 1){//sysj\controller2.sysj line: 19, column: 21
      if(armAtDest.getprestatus()){//sysj\controller2.sysj line: 19, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 19, column: 63
        currsigs.addElement(state_1);
        state_1.setValue(2);//sysj\controller2.sysj line: 19, column: 63
        active[4]=1;
        ends[4]=1;
        tdone[4]=1;
      }
      else {
        active[4]=1;
        ends[4]=1;
        tdone[4]=1;
      }
    }
    else {
      active[4]=1;
      ends[4]=1;
      tdone[4]=1;
    }
  }

  public void thread1793(int [] tdone, int [] ends){
        S1552=1;
    if((Integer)(state_1.getpreval() == null ? null : ((Integer)state_1.getpreval())) == 0){//sysj\controller2.sysj line: 17, column: 21
      if(request.getprestatus() && !empty.getprestatus()){//sysj\controller2.sysj line: 17, column: 52
        state_1.setPresent();//sysj\controller2.sysj line: 17, column: 71
        currsigs.addElement(state_1);
        state_1.setValue(1);//sysj\controller2.sysj line: 17, column: 71
        active[3]=1;
        ends[3]=1;
        tdone[3]=1;
      }
      else {
        active[3]=1;
        ends[3]=1;
        tdone[3]=1;
      }
    }
    else {
      active[3]=1;
      ends[3]=1;
      tdone[3]=1;
    }
  }

  public void thread1792(int [] tdone, int [] ends){
        S1631=1;
    thread1793(tdone,ends);
    thread1794(tdone,ends);
    thread1795(tdone,ends);
    thread1796(tdone,ends);
    thread1797(tdone,ends);
    thread1798(tdone,ends);
    thread1799(tdone,ends);
    thread1800(tdone,ends);
    int biggest1801 = 0;
    if(ends[3]>=biggest1801){
      biggest1801=ends[3];
    }
    if(ends[4]>=biggest1801){
      biggest1801=ends[4];
    }
    if(ends[5]>=biggest1801){
      biggest1801=ends[5];
    }
    if(ends[6]>=biggest1801){
      biggest1801=ends[6];
    }
    if(ends[7]>=biggest1801){
      biggest1801=ends[7];
    }
    if(ends[8]>=biggest1801){
      biggest1801=ends[8];
    }
    if(ends[9]>=biggest1801){
      biggest1801=ends[9];
    }
    if(ends[10]>=biggest1801){
      biggest1801=ends[10];
    }
    if(biggest1801 == 1){
      active[2]=1;
      ends[2]=1;
      tdone[2]=1;
    }
  }

  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S1790){
        case 0 : 
          S1790=0;
          break RUN;
        
        case 1 : 
          S1790=2;
          S1790=2;
          state_1.setClear();//sysj\controller2.sysj line: 9, column: 2
          state_1.setPresent();//sysj\controller2.sysj line: 13, column: 2
          currsigs.addElement(state_1);
          state_1.setValue(0);//sysj\controller2.sysj line: 13, column: 2
          S1541=0;
          active[1]=1;
          ends[1]=1;
          break RUN;
        
        case 2 : 
          state_1.setClear();//sysj\controller2.sysj line: 9, column: 2
          switch(S1541){
            case 0 : 
              S1541=0;
              S1541=1;
              thread1792(tdone,ends);
              thread1802(tdone,ends);
              int biggest1808 = 0;
              if(ends[2]>=biggest1808){
                biggest1808=ends[2];
              }
              if(ends[11]>=biggest1808){
                biggest1808=ends[11];
              }
              if(biggest1808 == 1){
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
            
            case 1 : 
              thread1809(tdone,ends);
              thread1819(tdone,ends);
              int biggest1825 = 0;
              if(ends[2]>=biggest1825){
                biggest1825=ends[2];
              }
              if(ends[11]>=biggest1825){
                biggest1825=ends[11];
              }
              if(biggest1825 == 1){
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              //FINXME code
              if(biggest1825 == 0){
                S1790=0;
                active[1]=0;
                ends[1]=0;
                S1790=0;
                break RUN;
              }
            
          }
        
      }
    }
  }

  public void init(){
    char [] active1 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    char [] paused1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    char [] suspended1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    paused = paused1;
    active = active1;
    suspended = suspended1;
    // Now instantiate all the local signals ONLY
    state_1 = new Signal();
    // --------------------------------------------------
  }
  
  public void run(){
    while(active[1] != 0){
      int index = 1;
      if(paused[index]==1 || suspended[index]==1 || active[index] == 0){
        for(int h=1;h<paused.length;++h){
          paused[h]=0;
        }
      }
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        if(!df){
          pusherRetracted.gethook();
          pusherExtended.gethook();
          WPgripped.gethook();
          armAtSource.gethook();
          armAtDest.gethook();
          empty.gethook();
          request.gethook();
          mode.gethook();
          pusherExtendM.gethook();
          vacOnM.gethook();
          armSourceM.gethook();
          armDestM.gethook();
          df = true;
        }
        runClockDomain();
      }
      pusherRetracted.setpreclear();
      pusherExtended.setpreclear();
      WPgripped.setpreclear();
      armAtSource.setpreclear();
      armAtDest.setpreclear();
      empty.setpreclear();
      request.setpreclear();
      mode.setpreclear();
      pusherExtendM.setpreclear();
      vacOnM.setpreclear();
      armSourceM.setpreclear();
      armDestM.setpreclear();
      pusherExtend.setpreclear();
      vacOn.setpreclear();
      armSource.setpreclear();
      armDest.setpreclear();
      state_1.setpreclear();
      int dummyint = 0;
      for(int qw=0;qw<currsigs.size();++qw){
        dummyint = ((Signal)currsigs.elementAt(qw)).getStatus() ? ((Signal)currsigs.elementAt(qw)).setprepresent() : ((Signal)currsigs.elementAt(qw)).setpreclear();
        ((Signal)currsigs.elementAt(qw)).setpreval(((Signal)currsigs.elementAt(qw)).getValue());
      }
      currsigs.removeAllElements();
      dummyint = pusherRetracted.getStatus() ? pusherRetracted.setprepresent() : pusherRetracted.setpreclear();
      pusherRetracted.setpreval(pusherRetracted.getValue());
      pusherRetracted.setClear();
      dummyint = pusherExtended.getStatus() ? pusherExtended.setprepresent() : pusherExtended.setpreclear();
      pusherExtended.setpreval(pusherExtended.getValue());
      pusherExtended.setClear();
      dummyint = WPgripped.getStatus() ? WPgripped.setprepresent() : WPgripped.setpreclear();
      WPgripped.setpreval(WPgripped.getValue());
      WPgripped.setClear();
      dummyint = armAtSource.getStatus() ? armAtSource.setprepresent() : armAtSource.setpreclear();
      armAtSource.setpreval(armAtSource.getValue());
      armAtSource.setClear();
      dummyint = armAtDest.getStatus() ? armAtDest.setprepresent() : armAtDest.setpreclear();
      armAtDest.setpreval(armAtDest.getValue());
      armAtDest.setClear();
      dummyint = empty.getStatus() ? empty.setprepresent() : empty.setpreclear();
      empty.setpreval(empty.getValue());
      empty.setClear();
      dummyint = request.getStatus() ? request.setprepresent() : request.setpreclear();
      request.setpreval(request.getValue());
      request.setClear();
      dummyint = mode.getStatus() ? mode.setprepresent() : mode.setpreclear();
      mode.setpreval(mode.getValue());
      mode.setClear();
      dummyint = pusherExtendM.getStatus() ? pusherExtendM.setprepresent() : pusherExtendM.setpreclear();
      pusherExtendM.setpreval(pusherExtendM.getValue());
      pusherExtendM.setClear();
      dummyint = vacOnM.getStatus() ? vacOnM.setprepresent() : vacOnM.setpreclear();
      vacOnM.setpreval(vacOnM.getValue());
      vacOnM.setClear();
      dummyint = armSourceM.getStatus() ? armSourceM.setprepresent() : armSourceM.setpreclear();
      armSourceM.setpreval(armSourceM.getValue());
      armSourceM.setClear();
      dummyint = armDestM.getStatus() ? armDestM.setprepresent() : armDestM.setpreclear();
      armDestM.setpreval(armDestM.getValue());
      armDestM.setClear();
      pusherExtend.sethook();
      pusherExtend.setClear();
      vacOn.sethook();
      vacOn.setClear();
      armSource.sethook();
      armSource.setClear();
      armDest.sethook();
      armDest.setClear();
      state_1.setClear();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        pusherRetracted.gethook();
        pusherExtended.gethook();
        WPgripped.gethook();
        armAtSource.gethook();
        armAtDest.gethook();
        empty.gethook();
        request.gethook();
        mode.gethook();
        pusherExtendM.gethook();
        vacOnM.gethook();
        armSourceM.gethook();
        armDestM.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
