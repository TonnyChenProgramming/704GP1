import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class ConveyorPlant extends ClockDomain{
  public ConveyorPlant(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel motorRun_in = new input_Channel();
  public output_Channel photoEye_o = new output_Channel();
  public output_Channel bottleLeft_o = new output_Channel();
  private long __start_thread_1;//sysj/ConveyorPlant.sysj line: 19, column: 31
  private int S4606 = 1;
  private int S1534 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S50 = 1;
  private int S23 = 1;
  private int S57 = 1;
  private int S52 = 1;
  private int S229 = 1;
  private int S224 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S4606){
        case 0 : 
          S4606=0;
          break RUN;
        
        case 1 : 
          S4606=2;
          S4606=2;
          S1534=0;
          S6=0;
          if(!motorRun_in.isPartnerPresent() || motorRun_in.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 17, column: 9
            motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(!motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
              motorRun_in.setACK(true);//sysj/ConveyorPlant.sysj line: 17, column: 9
              S1=1;
              if(motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                ends[1]=2;
                ;//sysj/ConveyorPlant.sysj line: 17, column: 9
                S1534=1;
                if((Boolean)(motorRun_in.getVal() == null ? null : ((Boolean)motorRun_in.getVal()))){//sysj/ConveyorPlant.sysj line: 19, column: 12
                  System.out.println("Plant: MOTOR Run received");//sysj/ConveyorPlant.sysj line: 21, column: 13
                  S50=0;
                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                  S23=0;
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                    ends[1]=2;
                    ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                    System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                    S50=1;
                    S57=0;
                    if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                      photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                      S57=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S52=0;
                      if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                        photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                        S52=1;
                        if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                          photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                          ends[1]=2;
                          ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                          S50=2;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                            ends[1]=2;
                            ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                            System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                            S50=3;
                            S229=0;
                            if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                              bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                              S229=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S224=0;
                              if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                S224=1;
                                if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  S1534=2;
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
                    S23=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                }
                else {
                  System.out.println("Plant: MOTOR OFF received");//sysj/ConveyorPlant.sysj line: 39, column: 13
                  S1534=2;
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
        
        case 2 : 
          switch(S1534){
            case 0 : 
              switch(S6){
                case 0 : 
                  if(!motorRun_in.isPartnerPresent() || motorRun_in.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                    motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(!motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                          motorRun_in.setACK(true);//sysj/ConveyorPlant.sysj line: 17, column: 9
                          S1=1;
                          if(motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                            motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorPlant.sysj line: 17, column: 9
                            S1534=1;
                            if((Boolean)(motorRun_in.getVal() == null ? null : ((Boolean)motorRun_in.getVal()))){//sysj/ConveyorPlant.sysj line: 19, column: 12
                              System.out.println("Plant: MOTOR Run received");//sysj/ConveyorPlant.sysj line: 21, column: 13
                              S50=0;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                              S23=0;
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                                S50=1;
                                S57=0;
                                if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  S57=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S52=0;
                                  if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                    photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                    S52=1;
                                    if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                      photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                                      S50=2;
                                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                        ends[1]=2;
                                        ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                        System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                        S50=3;
                                        S229=0;
                                        if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          S229=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S224=0;
                                          if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                            bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                            S224=1;
                                            if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                              bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                              S1534=2;
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
                                S23=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              System.out.println("Plant: MOTOR OFF received");//sysj/ConveyorPlant.sysj line: 39, column: 13
                              S1534=2;
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
                        if(motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                          motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                          ends[1]=2;
                          ;//sysj/ConveyorPlant.sysj line: 17, column: 9
                          S1534=1;
                          if((Boolean)(motorRun_in.getVal() == null ? null : ((Boolean)motorRun_in.getVal()))){//sysj/ConveyorPlant.sysj line: 19, column: 12
                            System.out.println("Plant: MOTOR Run received");//sysj/ConveyorPlant.sysj line: 21, column: 13
                            S50=0;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                            S23=0;
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                              System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                              S50=1;
                              S57=0;
                              if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                S57=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S52=0;
                                if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  S52=1;
                                  if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                    photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                                    S50=2;
                                    __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                                    if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                      ends[1]=2;
                                      ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                      System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                      S50=3;
                                      S229=0;
                                      if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        S229=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S224=0;
                                        if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          S224=1;
                                          if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                            bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                            S1534=2;
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
                              S23=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            System.out.println("Plant: MOTOR OFF received");//sysj/ConveyorPlant.sysj line: 39, column: 13
                            S1534=2;
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
                  S6=1;
                  S6=0;
                  if(!motorRun_in.isPartnerPresent() || motorRun_in.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                    motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(!motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                      motorRun_in.setACK(true);//sysj/ConveyorPlant.sysj line: 17, column: 9
                      S1=1;
                      if(motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                        motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                        ends[1]=2;
                        ;//sysj/ConveyorPlant.sysj line: 17, column: 9
                        S1534=1;
                        if((Boolean)(motorRun_in.getVal() == null ? null : ((Boolean)motorRun_in.getVal()))){//sysj/ConveyorPlant.sysj line: 19, column: 12
                          System.out.println("Plant: MOTOR Run received");//sysj/ConveyorPlant.sysj line: 21, column: 13
                          S50=0;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                          S23=0;
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                            ends[1]=2;
                            ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                            System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                            S50=1;
                            S57=0;
                            if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                              photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                              S57=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S52=0;
                              if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                S52=1;
                                if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                                  S50=2;
                                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                    ends[1]=2;
                                    ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                    System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                    S50=3;
                                    S229=0;
                                    if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      S229=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S224=0;
                                      if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        S224=1;
                                        if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                          S1534=2;
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
                            S23=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          System.out.println("Plant: MOTOR OFF received");//sysj/ConveyorPlant.sysj line: 39, column: 13
                          S1534=2;
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
            
            case 1 : 
              switch(S50){
                case 0 : 
                  switch(S23){
                    case 0 : 
                      S23=0;
                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                        ends[1]=2;
                        ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                        System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                        S50=1;
                        S57=0;
                        if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                          photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                          S57=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S52=0;
                          if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                            photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                            S52=1;
                            if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                              photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                              S50=2;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                S50=3;
                                S229=0;
                                if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  S229=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S224=0;
                                  if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    S224=1;
                                    if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      S1534=2;
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
                        S23=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    
                    case 1 : 
                      S23=1;
                      S23=0;
                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                        ends[1]=2;
                        ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                        System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                        S50=1;
                        S57=0;
                        if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                          photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                          S57=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S52=0;
                          if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                            photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                            S52=1;
                            if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                              photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                              S50=2;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                S50=3;
                                S229=0;
                                if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  S229=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S224=0;
                                  if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    S224=1;
                                    if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      S1534=2;
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
                        S23=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    
                  }
                  break;
                
                case 1 : 
                  switch(S57){
                    case 0 : 
                      if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                        photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                        S57=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S52){
                          case 0 : 
                            if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                              photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                              S52=1;
                              if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                                photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                                S50=2;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                  ends[1]=2;
                                  ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                  System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                  S50=3;
                                  S229=0;
                                  if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    S229=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S224=0;
                                    if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      S224=1;
                                      if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                        S1534=2;
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
                            if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                              photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                              S50=2;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                S50=3;
                                S229=0;
                                if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  S229=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S224=0;
                                  if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    S224=1;
                                    if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      S1534=2;
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
                      S57=1;
                      S57=0;
                      if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                        photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                        S57=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S52=0;
                        if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                          photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                          S52=1;
                          if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                            photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                            ends[1]=2;
                            ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                            S50=2;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                              System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                              S50=3;
                              S229=0;
                              if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                S229=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S224=0;
                                if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  S224=1;
                                  if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    S1534=2;
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
                
                case 2 : 
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                    ends[1]=2;
                    ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                    System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                    S50=3;
                    S229=0;
                    if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                      bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                      S229=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S224=0;
                      if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                        bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                        S224=1;
                        if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                          bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                          ends[1]=2;
                          ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                          S1534=2;
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
                
                case 3 : 
                  switch(S229){
                    case 0 : 
                      if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                        bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                        S229=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S224){
                          case 0 : 
                            if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                              bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                              S224=1;
                              if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                S1534=2;
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
                            if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                              bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                              S1534=2;
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
                      S229=1;
                      S229=0;
                      if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                        bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                        S229=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S224=0;
                        if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                          bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                          S224=1;
                          if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                            bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                            ends[1]=2;
                            ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                            S1534=2;
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
                
              }
              break;
            
            case 2 : 
              S1534=2;
              S1534=0;
              S6=0;
              if(!motorRun_in.isPartnerPresent() || motorRun_in.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(!motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                  motorRun_in.setACK(true);//sysj/ConveyorPlant.sysj line: 17, column: 9
                  S1=1;
                  if(motorRun_in.isREQ()){//sysj/ConveyorPlant.sysj line: 17, column: 9
                    motorRun_in.setACK(false);//sysj/ConveyorPlant.sysj line: 17, column: 9
                    ends[1]=2;
                    ;//sysj/ConveyorPlant.sysj line: 17, column: 9
                    S1534=1;
                    if((Boolean)(motorRun_in.getVal() == null ? null : ((Boolean)motorRun_in.getVal()))){//sysj/ConveyorPlant.sysj line: 19, column: 12
                      System.out.println("Plant: MOTOR Run received");//sysj/ConveyorPlant.sysj line: 21, column: 13
                      S50=0;
                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                      S23=0;
                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                        ends[1]=2;
                        ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                        System.out.println("Plant: bottle reached photo eye");//sysj/ConveyorPlant.sysj line: 25, column: 13
                        S50=1;
                        S57=0;
                        if(!photoEye_o.isPartnerPresent() || photoEye_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                          photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                          S57=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S52=0;
                          if(photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                            photoEye_o.setVal(true);//sysj/ConveyorPlant.sysj line: 27, column: 13
                            S52=1;
                            if(!photoEye_o.isACK()){//sysj/ConveyorPlant.sysj line: 27, column: 13
                              photoEye_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 27, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorPlant.sysj line: 27, column: 13
                              S50=2;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/ConveyorPlant.sysj line: 19, column: 31
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/ConveyorPlant.sysj line: 19, column: 31
                                ends[1]=2;
                                ;//sysj/ConveyorPlant.sysj line: 19, column: 31
                                System.out.println("Plant: bottle left conveyor");//sysj/ConveyorPlant.sysj line: 31, column: 13
                                S50=3;
                                S229=0;
                                if(!bottleLeft_o.isPartnerPresent() || bottleLeft_o.isPartnerPreempted()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                  S229=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S224=0;
                                  if(bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    bottleLeft_o.setVal(true);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                    S224=1;
                                    if(!bottleLeft_o.isACK()){//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      bottleLeft_o.setREQ(false);//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorPlant.sysj line: 33, column: 13
                                      S1534=2;
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
                        S23=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      System.out.println("Plant: MOTOR OFF received");//sysj/ConveyorPlant.sysj line: 39, column: 13
                      S1534=2;
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
          motorRun_in.gethook();
          photoEye_o.gethook();
          bottleLeft_o.gethook();
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
      motorRun_in.sethook();
      photoEye_o.sethook();
      bottleLeft_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        motorRun_in.gethook();
        photoEye_o.gethook();
        bottleLeft_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
