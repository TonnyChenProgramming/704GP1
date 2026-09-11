import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class RotaryTableController extends ClockDomain{
  public RotaryTableController(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public Signal rotateTrigger = new Signal("rotateTrigger", Signal.INPUT);
  public Signal tableAligned = new Signal("tableAligned", Signal.OUTPUT);
  public input_Channel aligned_in = new input_Channel();
  public input_Channel occupancy_in = new input_Channel();
  public output_Channel rotate_o = new output_Channel();
  private String currentOccupancy_thread_1;//sysj/RoteryTableController.sysj line: 51, column: 13
  private int S1231 = 1;
  private int S1 = 1;
  private int S8 = 1;
  private int S3 = 1;
  private int S52 = 1;
  private int S47 = 1;
  private int S140 = 1;
  private int S135 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S1231){
        case 0 : 
          S1231=0;
          break RUN;
        
        case 1 : 
          S1231=2;
          S1231=2;
          S1=0;
          active[1]=1;
          ends[1]=1;
          break RUN;
        
        case 2 : 
          switch(S1){
            case 0 : 
              if(rotateTrigger.getprestatus()){//sysj/RoteryTableController.sysj line: 24, column: 15
                System.out.println("ROTATE trigger received");//sysj/RoteryTableController.sysj line: 27, column: 9
                S1=1;
                S8=0;
                if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 9
                  rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                  S8=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S3=0;
                  if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                    rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 34, column: 9
                    S3=1;
                    if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                      rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                      ends[1]=2;
                      ;//sysj/RoteryTableController.sysj line: 34, column: 9
                      S1=2;
                      S52=0;
                      if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 39, column: 9
                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                        S52=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S47=0;
                        if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                          aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 39, column: 9
                          S47=1;
                          if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 39, column: 9
                            S1=3;
                            if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                              System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                              S140=0;
                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                S140=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S135=0;
                                if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                  occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                                  S135=1;
                                  if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                    currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                    System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                    tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                    currsigs.addElement(tableAligned);
                                    System.out.println("Emitted tableAligned");
                                    S1=4;
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
                              S1=4;
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
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
            
            case 1 : 
              switch(S8){
                case 0 : 
                  if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 9
                    rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                    S8=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S3){
                      case 0 : 
                        if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                          rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 34, column: 9
                          S3=1;
                          if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                            rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 34, column: 9
                            S1=2;
                            S52=0;
                            if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 39, column: 9
                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                              S52=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S47=0;
                              if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                                aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 39, column: 9
                                S47=1;
                                if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                                  aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 39, column: 9
                                  S1=3;
                                  if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                                    System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                                    S140=0;
                                    if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                      S140=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S135=0;
                                      if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                        occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                                        S135=1;
                                        if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                          occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                          currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                          System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                          tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                          currsigs.addElement(tableAligned);
                                          System.out.println("Emitted tableAligned");
                                          S1=4;
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
                                    S1=4;
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
                        if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                          rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                          ends[1]=2;
                          ;//sysj/RoteryTableController.sysj line: 34, column: 9
                          S1=2;
                          S52=0;
                          if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 39, column: 9
                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                            S52=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S47=0;
                            if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                              aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 39, column: 9
                              S47=1;
                              if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                                aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 39, column: 9
                                S1=3;
                                if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                                  System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                                  S140=0;
                                  if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                    S140=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S135=0;
                                    if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                      occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                                      S135=1;
                                      if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                        currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                        System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                        tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                        currsigs.addElement(tableAligned);
                                        System.out.println("Emitted tableAligned");
                                        S1=4;
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
                                  S1=4;
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
                  S8=1;
                  S8=0;
                  if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 9
                    rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                    S8=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S3=0;
                    if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                      rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 34, column: 9
                      S3=1;
                      if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 9
                        rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 9
                        ends[1]=2;
                        ;//sysj/RoteryTableController.sysj line: 34, column: 9
                        S1=2;
                        S52=0;
                        if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 39, column: 9
                          aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                          S52=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S47=0;
                          if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                            aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 39, column: 9
                            S47=1;
                            if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                              ends[1]=2;
                              ;//sysj/RoteryTableController.sysj line: 39, column: 9
                              S1=3;
                              if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                                System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                                S140=0;
                                if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                  S140=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S135=0;
                                  if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                    occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                                    S135=1;
                                    if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                      currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                      System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                      tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                      currsigs.addElement(tableAligned);
                                      System.out.println("Emitted tableAligned");
                                      S1=4;
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
                                S1=4;
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
            
            case 2 : 
              switch(S52){
                case 0 : 
                  if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 39, column: 9
                    aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                    S52=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S47){
                      case 0 : 
                        if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                          aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 39, column: 9
                          S47=1;
                          if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 39, column: 9
                            S1=3;
                            if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                              System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                              S140=0;
                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                S140=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S135=0;
                                if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                  occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                                  S135=1;
                                  if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                    currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                    System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                    tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                    currsigs.addElement(tableAligned);
                                    System.out.println("Emitted tableAligned");
                                    S1=4;
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
                              S1=4;
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
                        if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                          aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                          ends[1]=2;
                          ;//sysj/RoteryTableController.sysj line: 39, column: 9
                          S1=3;
                          if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                            System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                            S140=0;
                            if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                              occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                              S140=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S135=0;
                              if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                                S135=1;
                                if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                  currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                  System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                  tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                  currsigs.addElement(tableAligned);
                                  System.out.println("Emitted tableAligned");
                                  S1=4;
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
                            S1=4;
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
                  S52=1;
                  S52=0;
                  if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 39, column: 9
                    aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                    S52=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S47=0;
                    if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                      aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 39, column: 9
                      S47=1;
                      if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 39, column: 9
                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 39, column: 9
                        ends[1]=2;
                        ;//sysj/RoteryTableController.sysj line: 39, column: 9
                        S1=3;
                        if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 42, column: 12
                          System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 44, column: 13
                          S140=0;
                          if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                            occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                            S140=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S135=0;
                            if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                              occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                              S135=1;
                              if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 49, column: 13
                                currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                                System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                                tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                                currsigs.addElement(tableAligned);
                                System.out.println("Emitted tableAligned");
                                S1=4;
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
                          S1=4;
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
            
            case 3 : 
              switch(S140){
                case 0 : 
                  if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                    S140=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S135){
                      case 0 : 
                        if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                          occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                          S135=1;
                          if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                            occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 49, column: 13
                            currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                            System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                            tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                            currsigs.addElement(tableAligned);
                            System.out.println("Emitted tableAligned");
                            S1=4;
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
                        if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                          occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                          ends[1]=2;
                          ;//sysj/RoteryTableController.sysj line: 49, column: 13
                          currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                          System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                          tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                          currsigs.addElement(tableAligned);
                          System.out.println("Emitted tableAligned");
                          S1=4;
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
                  S140=1;
                  S140=0;
                  if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 49, column: 13
                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                    S140=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S135=0;
                    if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                      occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 49, column: 13
                      S135=1;
                      if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 49, column: 13
                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 49, column: 13
                        ends[1]=2;
                        ;//sysj/RoteryTableController.sysj line: 49, column: 13
                        currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 51, column: 13
                        System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 55, column: 13
                        tableAligned.setPresent();//sysj/RoteryTableController.sysj line: 63, column: 13
                        currsigs.addElement(tableAligned);
                        System.out.println("Emitted tableAligned");
                        S1=4;
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
            
            case 4 : 
              S1=4;
              S1=0;
              active[1]=1;
              ends[1]=1;
              break RUN;
            
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
          aligned_in.gethook();
          occupancy_in.gethook();
          rotate_o.gethook();
          rotateTrigger.gethook();
          df = true;
        }
        runClockDomain();
      }
      rotateTrigger.setpreclear();
      tableAligned.setpreclear();
      int dummyint = 0;
      for(int qw=0;qw<currsigs.size();++qw){
        dummyint = ((Signal)currsigs.elementAt(qw)).getStatus() ? ((Signal)currsigs.elementAt(qw)).setprepresent() : ((Signal)currsigs.elementAt(qw)).setpreclear();
        ((Signal)currsigs.elementAt(qw)).setpreval(((Signal)currsigs.elementAt(qw)).getValue());
      }
      currsigs.removeAllElements();
      dummyint = rotateTrigger.getStatus() ? rotateTrigger.setprepresent() : rotateTrigger.setpreclear();
      rotateTrigger.setpreval(rotateTrigger.getValue());
      rotateTrigger.setClear();
      tableAligned.sethook();
      tableAligned.setClear();
      aligned_in.sethook();
      occupancy_in.sethook();
      rotate_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        aligned_in.gethook();
        occupancy_in.gethook();
        rotate_o.gethook();
        rotateTrigger.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
