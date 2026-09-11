import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class BottleLoaderPlant extends ClockDomain{
  public BottleLoaderPlant(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel load_in = new input_Channel();
  public input_Channel retract_in = new input_Channel();
  public output_Channel bottleAvailable_o = new output_Channel();
  public output_Channel placed_o = new output_Channel();
  public output_Channel armHome_o = new output_Channel();
  private long __start_thread_1;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
  private int S3202 = 1;
  private int S110 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S112 = 1;
  private int S201 = 1;
  private int S196 = 1;
  private int S377 = 1;
  private int S372 = 1;
  private int S765 = 1;
  private int S760 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S3202){
        case 0 : 
          S3202=0;
          break RUN;
        
        case 1 : 
          S3202=2;
          S3202=2;
          S110=0;
          S6=0;
          if(!bottleAvailable_o.isPartnerPresent() || bottleAvailable_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
            bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
              bottleAvailable_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
              S1=1;
              if(!bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                ends[1]=2;
                ;//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                S110=1;
                S28=0;
                if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                  load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                    load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                    S23=1;
                    if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      ends[1]=2;
                      ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                      S110=2;
                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                      S112=0;
                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                        ends[1]=2;
                        ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                        System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                        S110=3;
                        S201=0;
                        if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          S201=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S196=0;
                          if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            S196=1;
                            if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              S110=4;
                              S377=0;
                              if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                S377=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S372=0;
                                if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  S372=1;
                                  if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                    S110=5;
                                    __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                    if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                      System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                      S110=6;
                                      S765=0;
                                      if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        S765=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S760=0;
                                        if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          S760=1;
                                          if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            S110=7;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                      }
                      else {
                        S112=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                  else {
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                }
              }
              else {
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
            }
            else {
              active[1]=1;
              ends[1]=1;
              break RUN;
            }
          }
        
        case 2 : 
          switch(S110){
            case 0 : 
              switch(S6){
                case 0 : 
                  if(!bottleAvailable_o.isPartnerPresent() || bottleAvailable_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                          bottleAvailable_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                          S1=1;
                          if(!bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                            bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                            S110=1;
                            S28=0;
                            if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                S23=1;
                                if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                  load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                  System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                                  S110=2;
                                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  S112=0;
                                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                    System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                                    S110=3;
                                    S201=0;
                                    if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      S201=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S196=0;
                                      if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                        placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                        S196=1;
                                        if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                          placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                          S110=4;
                                          S377=0;
                                          if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            S377=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S372=0;
                                            if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                              retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                              S372=1;
                                              if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                                retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                                System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                                S110=5;
                                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                                  System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                                  S110=6;
                                                  S765=0;
                                                  if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    S765=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S760=0;
                                                    if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                      armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                      S760=1;
                                                      if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                        armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                        S110=7;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S112=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(!bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                          bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                          S110=1;
                          S28=0;
                          if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              S23=1;
                              if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                                System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                                S110=2;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                S112=0;
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                                  S110=3;
                                  S201=0;
                                  if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    S201=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S196=0;
                                    if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      S196=1;
                                      if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                        placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                        S110=4;
                                        S377=0;
                                        if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          S377=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S372=0;
                                          if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            S372=1;
                                            if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                              retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                              System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                              S110=5;
                                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                                System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                                S110=6;
                                                S765=0;
                                                if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  S765=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S760=0;
                                                  if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    S760=1;
                                                    if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                      armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                      S110=7;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S112=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                    }
                  }
                  break;
                
                case 1 : 
                  S6=1;
                  S6=0;
                  if(!bottleAvailable_o.isPartnerPresent() || bottleAvailable_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                      bottleAvailable_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                      S1=1;
                      if(!bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                        bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                        S110=1;
                        S28=0;
                        if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            S23=1;
                            if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                              System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                              S110=2;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                              S112=0;
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                                S110=3;
                                S201=0;
                                if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  S201=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S196=0;
                                  if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    S196=1;
                                    if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                      S110=4;
                                      S377=0;
                                      if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        S377=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S372=0;
                                        if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          S372=1;
                                          if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                            System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                            S110=5;
                                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                              System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                              S110=6;
                                              S765=0;
                                              if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                S765=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S760=0;
                                                if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  S760=1;
                                                  if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                    S110=7;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                S112=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 1 : 
              switch(S28){
                case 0 : 
                  if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                    load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S23){
                      case 0 : 
                        if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          S23=1;
                          if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                            System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                            S110=2;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            S112=0;
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                              ends[1]=2;
                              ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                              System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                              S110=3;
                              S201=0;
                              if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                S201=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S196=0;
                                if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  S196=1;
                                  if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                    S110=4;
                                    S377=0;
                                    if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      S377=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S372=0;
                                      if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        S372=1;
                                        if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                          System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                          S110=5;
                                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                            System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                            S110=6;
                                            S765=0;
                                            if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              S765=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S760=0;
                                              if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                S760=1;
                                                if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                  S110=7;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              S112=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                          S110=2;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          S112=0;
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                            S110=3;
                            S201=0;
                            if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              S201=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S196=0;
                              if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                S196=1;
                                if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  S110=4;
                                  S377=0;
                                  if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    S377=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S372=0;
                                    if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      S372=1;
                                      if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                        S110=5;
                                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                          System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                          S110=6;
                                          S765=0;
                                          if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            S765=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S760=0;
                                            if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              S760=1;
                                              if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                S110=7;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            S112=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                    }
                  }
                  break;
                
                case 1 : 
                  S28=1;
                  S28=0;
                  if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                    load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      S23=1;
                      if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                        load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                        System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                        S110=2;
                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                        S112=0;
                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                          S110=3;
                          S201=0;
                          if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            S201=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S196=0;
                            if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              S196=1;
                              if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                S110=4;
                                S377=0;
                                if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  S377=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S372=0;
                                  if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    S372=1;
                                    if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                      S110=5;
                                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                        System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                        S110=6;
                                        S765=0;
                                        if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          S765=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S760=0;
                                          if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            S760=1;
                                            if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              S110=7;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          S112=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 2 : 
              switch(S112){
                case 0 : 
                  S112=0;
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                    ends[1]=2;
                    ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                    System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                    S110=3;
                    S201=0;
                    if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                      placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                      S201=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S196=0;
                      if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        S196=1;
                        if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          S110=4;
                          S377=0;
                          if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            S377=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S372=0;
                            if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              S372=1;
                              if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                S110=5;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                  S110=6;
                                  S765=0;
                                  if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    S765=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S760=0;
                                    if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      S760=1;
                                      if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        S110=7;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                  }
                  else {
                    S112=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                
                case 1 : 
                  S112=1;
                  S112=0;
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                    ends[1]=2;
                    ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                    System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                    S110=3;
                    S201=0;
                    if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                      placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                      S201=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S196=0;
                      if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        S196=1;
                        if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          S110=4;
                          S377=0;
                          if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            S377=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S372=0;
                            if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              S372=1;
                              if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                S110=5;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                  S110=6;
                                  S765=0;
                                  if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    S765=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S760=0;
                                    if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      S760=1;
                                      if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        S110=7;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                  }
                  else {
                    S112=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                
              }
              break;
            
            case 3 : 
              switch(S201){
                case 0 : 
                  if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                    placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                    S201=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S196){
                      case 0 : 
                        if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          S196=1;
                          if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                            S110=4;
                            S377=0;
                            if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              S377=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S372=0;
                              if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                S372=1;
                                if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                  System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                  S110=5;
                                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                    System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                    S110=6;
                                    S765=0;
                                    if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      S765=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S760=0;
                                      if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        S760=1;
                                        if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                          S110=7;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                          S110=4;
                          S377=0;
                          if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            S377=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S372=0;
                            if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              S372=1;
                              if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                S110=5;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                  System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                  S110=6;
                                  S765=0;
                                  if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    S765=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S760=0;
                                    if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      S760=1;
                                      if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                        S110=7;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                    }
                  }
                  break;
                
                case 1 : 
                  S201=1;
                  S201=0;
                  if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                    placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                    S201=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S196=0;
                    if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                      placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                      S196=1;
                      if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                        S110=4;
                        S377=0;
                        if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          S377=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S372=0;
                          if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            S372=1;
                            if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                              System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                              S110=5;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                S110=6;
                                S765=0;
                                if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  S765=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S760=0;
                                  if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    S760=1;
                                    if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                      S110=7;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 4 : 
              switch(S377){
                case 0 : 
                  if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                    retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                    S377=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S372){
                      case 0 : 
                        if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          S372=1;
                          if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                            System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                            S110=5;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                              ends[1]=2;
                              ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                              System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                              S110=6;
                              S765=0;
                              if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                S765=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S760=0;
                                if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  S760=1;
                                  if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                    S110=7;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                          System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                          S110=5;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                            S110=6;
                            S765=0;
                            if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                              armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                              S765=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S760=0;
                              if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                S760=1;
                                if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                  S110=7;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                    }
                  }
                  break;
                
                case 1 : 
                  S377=1;
                  S377=0;
                  if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                    retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                    S377=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S372=0;
                    if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                      retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                      S372=1;
                      if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                        retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                        System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                        S110=5;
                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                          S110=6;
                          S765=0;
                          if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                            armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                            S765=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S760=0;
                            if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                              armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                              S760=1;
                              if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                S110=7;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 5 : 
              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                ends[1]=2;
                ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                S110=6;
                S765=0;
                if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                  armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                  S765=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S760=0;
                  if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                    armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                    S760=1;
                    if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                      armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                      ends[1]=2;
                      ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                      S110=7;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                  else {
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                }
              }
              else {
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
            
            case 6 : 
              switch(S765){
                case 0 : 
                  if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                    S765=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S760){
                      case 0 : 
                        if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                          armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                          S760=1;
                          if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                            armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                            S110=7;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                          armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                          S110=7;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                    }
                  }
                  break;
                
                case 1 : 
                  S765=1;
                  S765=0;
                  if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                    armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                    S765=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S760=0;
                    if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                      armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                      S760=1;
                      if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                        armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                        S110=7;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 7 : 
              S110=7;
              S110=0;
              S6=0;
              if(!bottleAvailable_o.isPartnerPresent() || bottleAvailable_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                  bottleAvailable_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                  S1=1;
                  if(!bottleAvailable_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    bottleAvailable_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    ends[1]=2;
                    ;//sysj/BottleLoaderPlant.sysj line: 15, column: 9
                    S110=1;
                    S28=0;
                    if(!load_in.isPartnerPresent() || load_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                      S28=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S23=0;
                      if(!load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                        load_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                        S23=1;
                        if(load_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          load_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderPlant.sysj line: 16, column: 9
                          System.out.println("Plant: LOAD received");//sysj/BottleLoaderPlant.sysj line: 17, column: 9
                          S110=2;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                          S112=0;
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            ends[1]=2;
                            ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                            System.out.println("Plant: bottle placed");//sysj/BottleLoaderPlant.sysj line: 19, column: 9
                            S110=3;
                            S201=0;
                            if(!placed_o.isPartnerPresent() || placed_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                              S201=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S196=0;
                              if(placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                placed_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                S196=1;
                                if(!placed_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  placed_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderPlant.sysj line: 20, column: 9
                                  S110=4;
                                  S377=0;
                                  if(!retract_in.isPartnerPresent() || retract_in.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                    S377=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S372=0;
                                    if(!retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      retract_in.setACK(true);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                      S372=1;
                                      if(retract_in.isREQ()){//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        retract_in.setACK(false);//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderPlant.sysj line: 22, column: 9
                                        System.out.println("Plant: RETRACT received");//sysj/BottleLoaderPlant.sysj line: 23, column: 9
                                        S110=5;
                                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderPlant.sysj line: 13, column: 5
                                          System.out.println("Plant: arm home");//sysj/BottleLoaderPlant.sysj line: 25, column: 9
                                          S110=6;
                                          S765=0;
                                          if(!armHome_o.isPartnerPresent() || armHome_o.isPartnerPreempted()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                            S765=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S760=0;
                                            if(armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              armHome_o.setVal(true);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                              S760=1;
                                              if(!armHome_o.isACK()){//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                armHome_o.setREQ(false);//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderPlant.sysj line: 26, column: 9
                                                S110=7;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            S112=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                  }
                  else {
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                }
                else {
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
              }
            
          }
        
      }
    }
  }

  public void init(){
    char [] active1 = {1, 1};
    char [] paused1 = {0, 0};
    char [] suspended1 = {0, 0};
    paused = paused1;
    active = active1;
    suspended = suspended1;
    // Now instantiate all the local signals ONLY
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
          load_in.gethook();
          retract_in.gethook();
          bottleAvailable_o.gethook();
          placed_o.gethook();
          armHome_o.gethook();
          df = true;
        }
        runClockDomain();
      }
      int dummyint = 0;
      for(int qw=0;qw<currsigs.size();++qw){
        dummyint = ((Signal)currsigs.elementAt(qw)).getStatus() ? ((Signal)currsigs.elementAt(qw)).setprepresent() : ((Signal)currsigs.elementAt(qw)).setpreclear();
        ((Signal)currsigs.elementAt(qw)).setpreval(((Signal)currsigs.elementAt(qw)).getValue());
      }
      currsigs.removeAllElements();
      load_in.sethook();
      retract_in.sethook();
      bottleAvailable_o.sethook();
      placed_o.sethook();
      armHome_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        load_in.gethook();
        retract_in.gethook();
        bottleAvailable_o.gethook();
        placed_o.gethook();
        armHome_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
