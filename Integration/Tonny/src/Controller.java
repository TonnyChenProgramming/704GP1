import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class Controller extends ClockDomain{
  public Controller(String name){super(name);}
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
  private Signal automatic_1;
  private int S1517 = 1;
  private int S11 = 1;
  private int S466 = 1;
  private int S465 = 1;
  private int S463 = 1;
  private int S25 = 1;
  private int S48 = 1;
  private int S53 = 1;
  private int S488 = 1;
  private int S487 = 1;
  private int S471 = 1;
  private int S505 = 1;
  
  private int[] ends = new int[8];
  private int[] tdone = new int[8];
  
  public void thread1538(int [] tdone, int [] ends){
        S505=1;
    if(request.getprestatus()){//sysj\controller.sysj line: 71, column: 19
      System.out.println("REQUEST");//sysj\controller.sysj line: 72, column: 15
      if(mode.getprestatus()){//sysj\controller.sysj line: 75, column: 19
        System.out.println("MODE = " + (mode.getpreval() == null ? null : ((Integer)mode.getpreval())));//sysj\controller.sysj line: 76, column: 15
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
      if(mode.getprestatus()){//sysj\controller.sysj line: 75, column: 19
        System.out.println("MODE = " + (mode.getpreval() == null ? null : ((Integer)mode.getpreval())));//sysj\controller.sysj line: 76, column: 15
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
  }

  public void thread1537(int [] tdone, int [] ends){
        S488=1;
    S487=0;
    if(pusherExtendM.getprestatus()){//sysj\controller.sysj line: 60, column: 14
      pusherExtend.setPresent();//sysj\controller.sysj line: 60, column: 29
      currsigs.addElement(pusherExtend);
      if(vacOnM.getprestatus()){//sysj\controller.sysj line: 61, column: 14
        vacOn.setPresent();//sysj\controller.sysj line: 61, column: 22
        currsigs.addElement(vacOn);
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
      else {
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
    }
    else {
      if(vacOnM.getprestatus()){//sysj\controller.sysj line: 61, column: 14
        vacOn.setPresent();//sysj\controller.sysj line: 61, column: 22
        currsigs.addElement(vacOn);
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
      else {
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
    }
  }

  public void thread1536(int [] tdone, int [] ends){
        S466=1;
    S465=0;
    S463=0;
    active[3]=1;
    ends[3]=1;
    tdone[3]=1;
  }

  public void thread1535(int [] tdone, int [] ends){
        S11=1;
    if(mode.getprestatus()){//sysj\controller.sysj line: 13, column: 19
      if((Integer)(mode.getpreval() == null ? null : ((Integer)mode.getpreval())) == 1){//sysj\controller.sysj line: 14, column: 18
        automatic_1.setPresent();//sysj\controller.sysj line: 15, column: 19
        currsigs.addElement(automatic_1);
        active[2]=1;
        ends[2]=1;
        tdone[2]=1;
      }
      else {
        active[2]=1;
        ends[2]=1;
        tdone[2]=1;
      }
    }
    else {
      active[2]=1;
      ends[2]=1;
      tdone[2]=1;
    }
  }

  public void thread1533(int [] tdone, int [] ends){
        switch(S505){
      case 0 : 
        active[7]=0;
        ends[7]=0;
        tdone[7]=1;
        break;
      
      case 1 : 
        if(request.getprestatus()){//sysj\controller.sysj line: 71, column: 19
          System.out.println("REQUEST");//sysj\controller.sysj line: 72, column: 15
          if(mode.getprestatus()){//sysj\controller.sysj line: 75, column: 19
            System.out.println("MODE = " + (mode.getpreval() == null ? null : ((Integer)mode.getpreval())));//sysj\controller.sysj line: 76, column: 15
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
          if(mode.getprestatus()){//sysj\controller.sysj line: 75, column: 19
            System.out.println("MODE = " + (mode.getpreval() == null ? null : ((Integer)mode.getpreval())));//sysj\controller.sysj line: 76, column: 15
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
        break;
      
    }
  }

  public void thread1532(int [] tdone, int [] ends){
        switch(S488){
      case 0 : 
        active[6]=0;
        ends[6]=0;
        tdone[6]=1;
        break;
      
      case 1 : 
        switch(S487){
          case 0 : 
            if(!automatic_1.getprestatus()){//sysj\controller.sysj line: 59, column: 10
              S487=1;
              active[6]=1;
              ends[6]=1;
              tdone[6]=1;
            }
            else {
              switch(S471){
                case 0 : 
                  S471=0;
                  S487=1;
                  active[6]=1;
                  ends[6]=1;
                  tdone[6]=1;
                  break;
                
                case 1 : 
                  S487=1;
                  active[6]=1;
                  ends[6]=1;
                  tdone[6]=1;
                  break;
                
                case 2 : 
                  S487=1;
                  active[6]=1;
                  ends[6]=1;
                  tdone[6]=1;
                  break;
                
                case 3 : 
                  S487=1;
                  active[6]=1;
                  ends[6]=1;
                  tdone[6]=1;
                  break;
                
                case 4 : 
                  S487=1;
                  active[6]=1;
                  ends[6]=1;
                  tdone[6]=1;
                  break;
                
              }
            }
            break;
          
          case 1 : 
            S487=1;
            S488=0;
            active[6]=0;
            ends[6]=0;
            tdone[6]=1;
            break;
          
        }
        break;
      
    }
  }

  public void thread1530(int [] tdone, int [] ends){
        switch(S53){
      case 0 : 
        active[5]=0;
        ends[5]=0;
        tdone[5]=1;
        break;
      
      case 1 : 
        armDest.setPresent();//sysj\controller.sysj line: 44, column: 9
        currsigs.addElement(armDest);
        active[5]=1;
        ends[5]=1;
        tdone[5]=1;
        break;
      
    }
  }

  public void thread1529(int [] tdone, int [] ends){
        switch(S48){
      case 0 : 
        active[4]=0;
        ends[4]=0;
        tdone[4]=1;
        break;
      
      case 1 : 
        vacOn.setPresent();//sysj\controller.sysj line: 42, column: 9
        currsigs.addElement(vacOn);
        active[4]=1;
        ends[4]=1;
        tdone[4]=1;
        break;
      
    }
  }

  public void thread1527(int [] tdone, int [] ends){
        S53=1;
    armDest.setPresent();//sysj\controller.sysj line: 44, column: 9
    currsigs.addElement(armDest);
    active[5]=1;
    ends[5]=1;
    tdone[5]=1;
  }

  public void thread1526(int [] tdone, int [] ends){
        S48=1;
    vacOn.setPresent();//sysj\controller.sysj line: 42, column: 9
    currsigs.addElement(vacOn);
    active[4]=1;
    ends[4]=1;
    tdone[4]=1;
  }

  public void thread1525(int [] tdone, int [] ends){
        switch(S466){
      case 0 : 
        active[3]=0;
        ends[3]=0;
        tdone[3]=1;
        break;
      
      case 1 : 
        switch(S465){
          case 0 : 
            if(automatic_1.getprestatus()){//sysj\controller.sysj line: 24, column: 11
              S465=1;
              active[3]=1;
              ends[3]=1;
              tdone[3]=1;
            }
            else {
              switch(S463){
                case 0 : 
                  if(request.getprestatus()){//sysj\controller.sysj line: 25, column: 12
                    S463=1;
                    S25=0;
                    armDest.setPresent();//sysj\controller.sysj line: 29, column: 8
                    currsigs.addElement(armDest);
                    active[3]=1;
                    ends[3]=1;
                    tdone[3]=1;
                  }
                  else {
                    active[3]=1;
                    ends[3]=1;
                    tdone[3]=1;
                  }
                  break;
                
                case 1 : 
                  switch(S25){
                    case 0 : 
                      if(armAtDest.getprestatus()){//sysj\controller.sysj line: 28, column: 13
                        S25=1;
                        pusherExtend.setPresent();//sysj\controller.sysj line: 32, column: 8
                        currsigs.addElement(pusherExtend);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      else {
                        armDest.setPresent();//sysj\controller.sysj line: 29, column: 8
                        currsigs.addElement(armDest);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      break;
                    
                    case 1 : 
                      if(pusherExtended.getprestatus()){//sysj\controller.sysj line: 31, column: 13
                        S25=2;
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      else {
                        pusherExtend.setPresent();//sysj\controller.sysj line: 32, column: 8
                        currsigs.addElement(pusherExtend);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      break;
                    
                    case 2 : 
                      if(pusherRetracted.getprestatus()){//sysj\controller.sysj line: 34, column: 14
                        S25=3;
                        armSource.setPresent();//sysj\controller.sysj line: 36, column: 8
                        currsigs.addElement(armSource);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      else {
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      break;
                    
                    case 3 : 
                      if(armAtSource.getprestatus()){//sysj\controller.sysj line: 35, column: 13
                        S25=4;
                        vacOn.setPresent();//sysj\controller.sysj line: 39, column: 8
                        currsigs.addElement(vacOn);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      else {
                        armSource.setPresent();//sysj\controller.sysj line: 36, column: 8
                        currsigs.addElement(armSource);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      break;
                    
                    case 4 : 
                      if(WPgripped.getprestatus()){//sysj\controller.sysj line: 38, column: 13
                        S25=5;
                        thread1526(tdone,ends);
                        thread1527(tdone,ends);
                        int biggest1528 = 0;
                        if(ends[4]>=biggest1528){
                          biggest1528=ends[4];
                        }
                        if(ends[5]>=biggest1528){
                          biggest1528=ends[5];
                        }
                        if(biggest1528 == 1){
                          active[3]=1;
                          ends[3]=1;
                          tdone[3]=1;
                        }
                      }
                      else {
                        vacOn.setPresent();//sysj\controller.sysj line: 39, column: 8
                        currsigs.addElement(vacOn);
                        active[3]=1;
                        ends[3]=1;
                        tdone[3]=1;
                      }
                      break;
                    
                    case 5 : 
                      if(armAtDest.getprestatus()){//sysj\controller.sysj line: 41, column: 13
                        if(empty.getprestatus()){//sysj\controller.sysj line: 46, column: 15
                          ends[3]=2;
                          ;//sysj\controller.sysj line: 26, column: 5
                          S465=1;
                          active[3]=1;
                          ends[3]=1;
                          tdone[3]=1;
                        }
                        else {
                          S25=0;
                          armDest.setPresent();//sysj\controller.sysj line: 29, column: 8
                          currsigs.addElement(armDest);
                          active[3]=1;
                          ends[3]=1;
                          tdone[3]=1;
                        }
                      }
                      else {
                        thread1529(tdone,ends);
                        thread1530(tdone,ends);
                        int biggest1531 = 0;
                        if(ends[4]>=biggest1531){
                          biggest1531=ends[4];
                        }
                        if(ends[5]>=biggest1531){
                          biggest1531=ends[5];
                        }
                        if(biggest1531 == 1){
                          active[3]=1;
                          ends[3]=1;
                          tdone[3]=1;
                        }
                        //FINXME code
                        if(biggest1531 == 0){
                          if(empty.getprestatus()){//sysj\controller.sysj line: 46, column: 15
                            ends[3]=2;
                            ;//sysj\controller.sysj line: 26, column: 5
                            S465=1;
                            active[3]=1;
                            ends[3]=1;
                            tdone[3]=1;
                          }
                          else {
                            S25=0;
                            armDest.setPresent();//sysj\controller.sysj line: 29, column: 8
                            currsigs.addElement(armDest);
                            active[3]=1;
                            ends[3]=1;
                            tdone[3]=1;
                          }
                        }
                      }
                      break;
                    
                  }
                  break;
                
              }
            }
            break;
          
          case 1 : 
            S465=1;
            S466=0;
            active[3]=0;
            ends[3]=0;
            tdone[3]=1;
            break;
          
        }
        break;
      
    }
  }

  public void thread1524(int [] tdone, int [] ends){
        switch(S11){
      case 0 : 
        active[2]=0;
        ends[2]=0;
        tdone[2]=1;
        break;
      
      case 1 : 
        if(mode.getprestatus()){//sysj\controller.sysj line: 13, column: 19
          if((Integer)(mode.getpreval() == null ? null : ((Integer)mode.getpreval())) == 1){//sysj\controller.sysj line: 14, column: 18
            automatic_1.setPresent();//sysj\controller.sysj line: 15, column: 19
            currsigs.addElement(automatic_1);
            active[2]=1;
            ends[2]=1;
            tdone[2]=1;
          }
          else {
            active[2]=1;
            ends[2]=1;
            tdone[2]=1;
          }
        }
        else {
          active[2]=1;
          ends[2]=1;
          tdone[2]=1;
        }
        break;
      
    }
  }

  public void thread1522(int [] tdone, int [] ends){
        S505=1;
    if(request.getprestatus()){//sysj\controller.sysj line: 71, column: 19
      System.out.println("REQUEST");//sysj\controller.sysj line: 72, column: 15
      if(mode.getprestatus()){//sysj\controller.sysj line: 75, column: 19
        System.out.println("MODE = " + (mode.getpreval() == null ? null : ((Integer)mode.getpreval())));//sysj\controller.sysj line: 76, column: 15
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
      if(mode.getprestatus()){//sysj\controller.sysj line: 75, column: 19
        System.out.println("MODE = " + (mode.getpreval() == null ? null : ((Integer)mode.getpreval())));//sysj\controller.sysj line: 76, column: 15
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
  }

  public void thread1521(int [] tdone, int [] ends){
        S488=1;
    S487=0;
    if(pusherExtendM.getprestatus()){//sysj\controller.sysj line: 60, column: 14
      pusherExtend.setPresent();//sysj\controller.sysj line: 60, column: 29
      currsigs.addElement(pusherExtend);
      if(vacOnM.getprestatus()){//sysj\controller.sysj line: 61, column: 14
        vacOn.setPresent();//sysj\controller.sysj line: 61, column: 22
        currsigs.addElement(vacOn);
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
      else {
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
    }
    else {
      if(vacOnM.getprestatus()){//sysj\controller.sysj line: 61, column: 14
        vacOn.setPresent();//sysj\controller.sysj line: 61, column: 22
        currsigs.addElement(vacOn);
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
      else {
        if(armSourceM.getprestatus()){//sysj\controller.sysj line: 62, column: 14
          armSource.setPresent();//sysj\controller.sysj line: 62, column: 26
          currsigs.addElement(armSource);
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
        else {
          if(armDestM.getprestatus()){//sysj\controller.sysj line: 63, column: 14
            armDest.setPresent();//sysj\controller.sysj line: 63, column: 24
            currsigs.addElement(armDest);
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
          else {
            S471=0;
            active[6]=1;
            ends[6]=1;
            tdone[6]=1;
          }
        }
      }
    }
  }

  public void thread1520(int [] tdone, int [] ends){
        S466=1;
    S465=0;
    S463=0;
    active[3]=1;
    ends[3]=1;
    tdone[3]=1;
  }

  public void thread1519(int [] tdone, int [] ends){
        S11=1;
    if(mode.getprestatus()){//sysj\controller.sysj line: 13, column: 19
      if((Integer)(mode.getpreval() == null ? null : ((Integer)mode.getpreval())) == 1){//sysj\controller.sysj line: 14, column: 18
        automatic_1.setPresent();//sysj\controller.sysj line: 15, column: 19
        currsigs.addElement(automatic_1);
        active[2]=1;
        ends[2]=1;
        tdone[2]=1;
      }
      else {
        active[2]=1;
        ends[2]=1;
        tdone[2]=1;
      }
    }
    else {
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
      switch(S1517){
        case 0 : 
          S1517=0;
          break RUN;
        
        case 1 : 
          S1517=2;
          S1517=2;
          automatic_1.setClear();//sysj\controller.sysj line: 9, column: 2
          thread1519(tdone,ends);
          thread1520(tdone,ends);
          thread1521(tdone,ends);
          thread1522(tdone,ends);
          int biggest1523 = 0;
          if(ends[2]>=biggest1523){
            biggest1523=ends[2];
          }
          if(ends[3]>=biggest1523){
            biggest1523=ends[3];
          }
          if(ends[6]>=biggest1523){
            biggest1523=ends[6];
          }
          if(ends[7]>=biggest1523){
            biggest1523=ends[7];
          }
          if(biggest1523 == 1){
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
        
        case 2 : 
          automatic_1.setClear();//sysj\controller.sysj line: 9, column: 2
          thread1524(tdone,ends);
          thread1525(tdone,ends);
          thread1532(tdone,ends);
          thread1533(tdone,ends);
          int biggest1534 = 0;
          if(ends[2]>=biggest1534){
            biggest1534=ends[2];
          }
          if(ends[3]>=biggest1534){
            biggest1534=ends[3];
          }
          if(ends[6]>=biggest1534){
            biggest1534=ends[6];
          }
          if(ends[7]>=biggest1534){
            biggest1534=ends[7];
          }
          if(biggest1534 == 1){
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          //FINXME code
          if(biggest1534 == 0){
            thread1535(tdone,ends);
            thread1536(tdone,ends);
            thread1537(tdone,ends);
            thread1538(tdone,ends);
            int biggest1539 = 0;
            if(ends[2]>=biggest1539){
              biggest1539=ends[2];
            }
            if(ends[3]>=biggest1539){
              biggest1539=ends[3];
            }
            if(ends[6]>=biggest1539){
              biggest1539=ends[6];
            }
            if(ends[7]>=biggest1539){
              biggest1539=ends[7];
            }
            if(biggest1539 == 1){
              active[1]=1;
              ends[1]=1;
              break RUN;
            }
          }
        
      }
    }
  }

  public void init(){
    char [] active1 = {1, 1, 1, 1, 1, 1, 1, 1};
    char [] paused1 = {0, 0, 0, 0, 0, 0, 0, 0};
    char [] suspended1 = {0, 0, 0, 0, 0, 0, 0, 0};
    paused = paused1;
    active = active1;
    suspended = suspended1;
    // Now instantiate all the local signals ONLY
    automatic_1 = new Signal();
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
      automatic_1.setpreclear();
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
      automatic_1.setClear();
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
