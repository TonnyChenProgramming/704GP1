import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class RotaryTablePlant extends ClockDomain{
  public RotaryTablePlant(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel rotate_in = new input_Channel();
  public output_Channel aligned_o = new output_Channel();
  public output_Channel occupancy_o = new output_Channel();
  private String positionOccupancy_thread_1;//sysj/RoteryTablePlant.sysj line: 12, column: 5
  private long __start_thread_1;//sysj/RoteryTablePlant.sysj line: 18, column: 29
  private int S6139 = 1;
  private int S1022 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S50 = 1;
  private int S57 = 1;
  private int S52 = 1;
  private int S145 = 1;
  private int S140 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S6139){
        case 0 : 
          S6139=0;
          break RUN;
        
        case 1 : 
          S6139=2;
          S6139=2;
          positionOccupancy_thread_1 = "000000";//sysj/RoteryTablePlant.sysj line: 12, column: 5
          S1022=0;
          S6=0;
          if(!rotate_in.isPartnerPresent() || rotate_in.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
            rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(!rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
              rotate_in.setACK(true);//sysj/RoteryTablePlant.sysj line: 16, column: 9
              S1=1;
              if(rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                ends[1]=2;
                ;//sysj/RoteryTablePlant.sysj line: 16, column: 9
                S1022=1;
                if((Boolean)(rotate_in.getVal() == null ? null : ((Boolean)rotate_in.getVal()))){//sysj/RoteryTablePlant.sysj line: 18, column: 12
                  System.out.println("Plant: ROTATE received");//sysj/RoteryTablePlant.sysj line: 20, column: 13
                  S50=0;
                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/RoteryTablePlant.sysj line: 18, column: 29
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/RoteryTablePlant.sysj line: 18, column: 29
                    ends[1]=2;
                    ;//sysj/RoteryTablePlant.sysj line: 18, column: 29
                    positionOccupancy_thread_1 = positionOccupancy_thread_1.substring(5) + positionOccupancy_thread_1.substring(0, 5);//sysj/RoteryTablePlant.sysj line: 28, column: 13
                    System.out.println("Plant: table rotated 60 degrees");//sysj/RoteryTablePlant.sysj line: 32, column: 13
                    System.out.println("Plant: occupancy = " + positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 36, column: 13
                    S50=1;
                    S57=0;
                    if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                      aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                      S57=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S52=0;
                      if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        S52=1;
                        if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          ends[1]=2;
                          ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          S50=2;
                          S145=0;
                          if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            S145=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S140=0;
                            if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              S140=1;
                              if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                S1022=2;
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
                  else {
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                }
                else {
                  S1022=2;
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
          switch(S1022){
            case 0 : 
              switch(S6){
                case 0 : 
                  if(!rotate_in.isPartnerPresent() || rotate_in.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(!rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                          rotate_in.setACK(true);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                          S1=1;
                          if(rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                            rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                            ends[1]=2;
                            ;//sysj/RoteryTablePlant.sysj line: 16, column: 9
                            S1022=1;
                            if((Boolean)(rotate_in.getVal() == null ? null : ((Boolean)rotate_in.getVal()))){//sysj/RoteryTablePlant.sysj line: 18, column: 12
                              System.out.println("Plant: ROTATE received");//sysj/RoteryTablePlant.sysj line: 20, column: 13
                              S50=0;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/RoteryTablePlant.sysj line: 18, column: 29
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/RoteryTablePlant.sysj line: 18, column: 29
                                ends[1]=2;
                                ;//sysj/RoteryTablePlant.sysj line: 18, column: 29
                                positionOccupancy_thread_1 = positionOccupancy_thread_1.substring(5) + positionOccupancy_thread_1.substring(0, 5);//sysj/RoteryTablePlant.sysj line: 28, column: 13
                                System.out.println("Plant: table rotated 60 degrees");//sysj/RoteryTablePlant.sysj line: 32, column: 13
                                System.out.println("Plant: occupancy = " + positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 36, column: 13
                                S50=1;
                                S57=0;
                                if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  S57=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S52=0;
                                  if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                    aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                    S52=1;
                                    if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                      aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                      S50=2;
                                      S145=0;
                                      if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        S145=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S140=0;
                                        if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                          occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                          S140=1;
                                          if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                            occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                            ends[1]=2;
                                            ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                            S1022=2;
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
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              S1022=2;
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
                        if(rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                          rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                          ends[1]=2;
                          ;//sysj/RoteryTablePlant.sysj line: 16, column: 9
                          S1022=1;
                          if((Boolean)(rotate_in.getVal() == null ? null : ((Boolean)rotate_in.getVal()))){//sysj/RoteryTablePlant.sysj line: 18, column: 12
                            System.out.println("Plant: ROTATE received");//sysj/RoteryTablePlant.sysj line: 20, column: 13
                            S50=0;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/RoteryTablePlant.sysj line: 18, column: 29
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/RoteryTablePlant.sysj line: 18, column: 29
                              ends[1]=2;
                              ;//sysj/RoteryTablePlant.sysj line: 18, column: 29
                              positionOccupancy_thread_1 = positionOccupancy_thread_1.substring(5) + positionOccupancy_thread_1.substring(0, 5);//sysj/RoteryTablePlant.sysj line: 28, column: 13
                              System.out.println("Plant: table rotated 60 degrees");//sysj/RoteryTablePlant.sysj line: 32, column: 13
                              System.out.println("Plant: occupancy = " + positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 36, column: 13
                              S50=1;
                              S57=0;
                              if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                S57=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S52=0;
                                if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  S52=1;
                                  if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                    aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                    S50=2;
                                    S145=0;
                                    if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      S145=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S140=0;
                                      if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        S140=1;
                                        if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                          occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                          ends[1]=2;
                                          ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                          S1022=2;
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
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            S1022=2;
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
                  if(!rotate_in.isPartnerPresent() || rotate_in.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(!rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                      rotate_in.setACK(true);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                      S1=1;
                      if(rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                        rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                        ends[1]=2;
                        ;//sysj/RoteryTablePlant.sysj line: 16, column: 9
                        S1022=1;
                        if((Boolean)(rotate_in.getVal() == null ? null : ((Boolean)rotate_in.getVal()))){//sysj/RoteryTablePlant.sysj line: 18, column: 12
                          System.out.println("Plant: ROTATE received");//sysj/RoteryTablePlant.sysj line: 20, column: 13
                          S50=0;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/RoteryTablePlant.sysj line: 18, column: 29
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/RoteryTablePlant.sysj line: 18, column: 29
                            ends[1]=2;
                            ;//sysj/RoteryTablePlant.sysj line: 18, column: 29
                            positionOccupancy_thread_1 = positionOccupancy_thread_1.substring(5) + positionOccupancy_thread_1.substring(0, 5);//sysj/RoteryTablePlant.sysj line: 28, column: 13
                            System.out.println("Plant: table rotated 60 degrees");//sysj/RoteryTablePlant.sysj line: 32, column: 13
                            System.out.println("Plant: occupancy = " + positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 36, column: 13
                            S50=1;
                            S57=0;
                            if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              S57=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S52=0;
                              if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                S52=1;
                                if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                  S50=2;
                                  S145=0;
                                  if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    S145=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S140=0;
                                    if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      S140=1;
                                      if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        ends[1]=2;
                                        ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                        S1022=2;
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
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                        else {
                          S1022=2;
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
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/RoteryTablePlant.sysj line: 18, column: 29
                    ends[1]=2;
                    ;//sysj/RoteryTablePlant.sysj line: 18, column: 29
                    positionOccupancy_thread_1 = positionOccupancy_thread_1.substring(5) + positionOccupancy_thread_1.substring(0, 5);//sysj/RoteryTablePlant.sysj line: 28, column: 13
                    System.out.println("Plant: table rotated 60 degrees");//sysj/RoteryTablePlant.sysj line: 32, column: 13
                    System.out.println("Plant: occupancy = " + positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 36, column: 13
                    S50=1;
                    S57=0;
                    if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                      aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                      S57=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S52=0;
                      if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        S52=1;
                        if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          ends[1]=2;
                          ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          S50=2;
                          S145=0;
                          if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            S145=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S140=0;
                            if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              S140=1;
                              if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                S1022=2;
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
                  else {
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                
                case 1 : 
                  switch(S57){
                    case 0 : 
                      if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        S57=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S52){
                          case 0 : 
                            if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              S52=1;
                              if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                                S50=2;
                                S145=0;
                                if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  S145=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S140=0;
                                  if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    S140=1;
                                    if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                      S1022=2;
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
                          
                          case 1 : 
                            if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              S50=2;
                              S145=0;
                              if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                S145=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S140=0;
                                if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  S140=1;
                                  if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    S1022=2;
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
                      }
                      break;
                    
                    case 1 : 
                      S57=1;
                      S57=0;
                      if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                        S57=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S52=0;
                        if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          S52=1;
                          if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                            aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                            ends[1]=2;
                            ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                            S50=2;
                            S145=0;
                            if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              S145=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S140=0;
                              if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                S140=1;
                                if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  S1022=2;
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
                
                case 2 : 
                  switch(S145){
                    case 0 : 
                      if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                        occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                        S145=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S140){
                          case 0 : 
                            if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              S140=1;
                              if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                S1022=2;
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
                            if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                              S1022=2;
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
                      S145=1;
                      S145=0;
                      if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                        occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                        S145=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S140=0;
                        if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                          occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                          S140=1;
                          if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            ends[1]=2;
                            ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                            S1022=2;
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
              S1022=2;
              S1022=0;
              S6=0;
              if(!rotate_in.isPartnerPresent() || rotate_in.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(!rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                  rotate_in.setACK(true);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                  S1=1;
                  if(rotate_in.isREQ()){//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    rotate_in.setACK(false);//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    ends[1]=2;
                    ;//sysj/RoteryTablePlant.sysj line: 16, column: 9
                    S1022=1;
                    if((Boolean)(rotate_in.getVal() == null ? null : ((Boolean)rotate_in.getVal()))){//sysj/RoteryTablePlant.sysj line: 18, column: 12
                      System.out.println("Plant: ROTATE received");//sysj/RoteryTablePlant.sysj line: 20, column: 13
                      S50=0;
                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/RoteryTablePlant.sysj line: 18, column: 29
                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/RoteryTablePlant.sysj line: 18, column: 29
                        ends[1]=2;
                        ;//sysj/RoteryTablePlant.sysj line: 18, column: 29
                        positionOccupancy_thread_1 = positionOccupancy_thread_1.substring(5) + positionOccupancy_thread_1.substring(0, 5);//sysj/RoteryTablePlant.sysj line: 28, column: 13
                        System.out.println("Plant: table rotated 60 degrees");//sysj/RoteryTablePlant.sysj line: 32, column: 13
                        System.out.println("Plant: occupancy = " + positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 36, column: 13
                        S50=1;
                        S57=0;
                        if(!aligned_o.isPartnerPresent() || aligned_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                          S57=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S52=0;
                          if(aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                            aligned_o.setVal(true);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                            S52=1;
                            if(!aligned_o.isACK()){//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              aligned_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTablePlant.sysj line: 40, column: 13
                              S50=2;
                              S145=0;
                              if(!occupancy_o.isPartnerPresent() || occupancy_o.isPartnerPreempted()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                S145=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S140=0;
                                if(occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  occupancy_o.setVal(positionOccupancy_thread_1);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                  S140=1;
                                  if(!occupancy_o.isACK()){//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    occupancy_o.setREQ(false);//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTablePlant.sysj line: 42, column: 13
                                    S1022=2;
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
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                    }
                    else {
                      S1022=2;
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
          rotate_in.gethook();
          aligned_o.gethook();
          occupancy_o.gethook();
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
      rotate_in.sethook();
      aligned_o.sethook();
      occupancy_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        rotate_in.gethook();
        aligned_o.gethook();
        occupancy_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
