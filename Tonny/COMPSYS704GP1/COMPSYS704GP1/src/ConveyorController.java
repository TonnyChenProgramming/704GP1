import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class ConveyorController extends ClockDomain{
  public ConveyorController(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel commandIn_in = new input_Channel();
  public input_Channel ackIn_in = new input_Channel();
  public input_Channel photoEye_in = new input_Channel();
  public input_Channel bottleLeft_in = new input_Channel();
  public output_Channel reportOut_o = new output_Channel();
  public output_Channel motorRun_o = new output_Channel();
  private String command_thread_1;//sysj/ConveyorController.sysj line: 23, column: 9
  private int first_thread_1;//sysj/ConveyorController.sysj line: 25, column: 9
  private int second_thread_1;//sysj/ConveyorController.sysj line: 27, column: 9
  private int third_thread_1;//sysj/ConveyorController.sysj line: 30, column: 9
  private String jobId_thread_1;//sysj/ConveyorController.sysj line: 33, column: 9
  private String workpieceId_thread_1;//sysj/ConveyorController.sysj line: 39, column: 9
  private int S278771 = 1;
  private int S278770 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S44 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S51 = 1;
  private int S46 = 1;
  private int S139 = 1;
  private int S134 = 1;
  private int S271 = 1;
  private int S266 = 1;
  private int S2721 = 1;
  private int S485 = 1;
  private int S447 = 1;
  private int S442 = 1;
  private int S1016 = 1;
  private int S596 = 1;
  private int S492 = 1;
  private int S487 = 1;
  private int S514 = 1;
  private int S509 = 1;
  private int S603 = 1;
  private int S598 = 1;
  private int S735 = 1;
  private int S730 = 1;
  private int S1015 = 1;
  private int S911 = 1;
  private int S906 = 1;
  private int S933 = 1;
  private int S928 = 1;
  private int S2720 = 1;
  private int S2616 = 1;
  private int S2611 = 1;
  private int S2638 = 1;
  private int S2633 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S278771){
        case 0 : 
          S278771=0;
          break RUN;
        
        case 1 : 
          S278771=2;
          S278771=2;
          S278770=0;
          S6=0;
          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 17, column: 5
            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
              reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 17, column: 5
              S1=1;
              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
                ends[1]=2;
                ;//sysj/ConveyorController.sysj line: 17, column: 5
                S278770=1;
                S44=0;
                S28=0;
                if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                  commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                    commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                    S23=1;
                    if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                      commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                      ends[1]=2;
                      ;//sysj/ConveyorController.sysj line: 21, column: 9
                      command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                      first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                      second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                      third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                      jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                      workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                      System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                      S44=1;
                      S51=0;
                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                        S51=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S46=0;
                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                          reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                          S46=1;
                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 51, column: 9
                            S44=2;
                            S139=0;
                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                              S139=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S134=0;
                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                S134=1;
                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 59, column: 9
                                  S44=3;
                                  S271=0;
                                  if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                    S271=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S266=0;
                                    if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                      photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                      S266=1;
                                      if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 62, column: 9
                                        S44=4;
                                        if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                          S2721=0;
                                          System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                          S485=0;
                                          S447=0;
                                          if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                            S447=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S442=0;
                                            if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                              bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                              S442=1;
                                              if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                S485=1;
                                                if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                  S1016=0;
                                                  S596=0;
                                                  S492=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                    S492=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S487=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                      S487=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                        S596=1;
                                                        S514=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                          S514=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S509=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                            reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                            S509=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                              S596=2;
                                                              S603=0;
                                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                S603=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S598=0;
                                                                if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  S598=1;
                                                                  if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    S596=3;
                                                                    S735=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      S735=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S730=0;
                                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        S730=1;
                                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          S44=5;
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
                                                  S1016=1;
                                                  S1015=0;
                                                  S911=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                    S911=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S906=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                      S906=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                        S1015=1;
                                                        S933=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                          S933=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S928=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                            S928=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                              S44=5;
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
                                          S2721=1;
                                          S2720=0;
                                          S2616=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                            S2616=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S2611=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                              S2611=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                S2720=1;
                                                S2638=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                  S2638=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S2633=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                    S2633=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                      S44=5;
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
              active[1]=1;
              ends[1]=1;
              break RUN;
            }
          }
        
        case 2 : 
          switch(S278770){
            case 0 : 
              switch(S6){
                case 0 : 
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 17, column: 5
                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
                          reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 17, column: 5
                          S1=1;
                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 17, column: 5
                            S278770=1;
                            S44=0;
                            S28=0;
                            if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                              commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                                commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                                S23=1;
                                if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                                  commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 21, column: 9
                                  command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                                  first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                                  second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                                  third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                                  jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                                  workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                                  System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                                  S44=1;
                                  S51=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                    S51=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S46=0;
                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                      reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                                      S46=1;
                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 51, column: 9
                                        S44=2;
                                        S139=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                          S139=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S134=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                            motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                            S134=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 59, column: 9
                                              S44=3;
                                              S271=0;
                                              if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                                S271=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S266=0;
                                                if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                  photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                                  S266=1;
                                                  if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 62, column: 9
                                                    S44=4;
                                                    if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                                      S2721=0;
                                                      System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                                      S485=0;
                                                      S447=0;
                                                      if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                        S447=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S442=0;
                                                        if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                          bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                          S442=1;
                                                          if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                            S485=1;
                                                            if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                              S1016=0;
                                                              S596=0;
                                                              S492=0;
                                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                S492=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S487=0;
                                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  S487=1;
                                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                                    S596=1;
                                                                    S514=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      S514=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S509=0;
                                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        S509=1;
                                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                          S596=2;
                                                                          S603=0;
                                                                          if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            S603=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S598=0;
                                                                            if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              S598=1;
                                                                              if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                                ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                                ends[1]=2;
                                                                                ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                                S596=3;
                                                                                S735=0;
                                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  S735=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S730=0;
                                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    S730=1;
                                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                      ends[1]=2;
                                                                                      ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                      S44=5;
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
                                                              S1016=1;
                                                              S1015=0;
                                                              S911=0;
                                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                S911=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S906=0;
                                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  S906=1;
                                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                                    S1015=1;
                                                                    S933=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      S933=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S928=0;
                                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        S928=1;
                                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                          S44=5;
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
                                                      S2721=1;
                                                      S2720=0;
                                                      S2616=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                        S2616=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S2611=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                          S2611=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                            S2720=1;
                                                            S2638=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                              S2638=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S2633=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                                S2633=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                                  S44=5;
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
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
                          ends[1]=2;
                          ;//sysj/ConveyorController.sysj line: 17, column: 5
                          S278770=1;
                          S44=0;
                          S28=0;
                          if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                            commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                              commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                                commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 21, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                                first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                                second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                                third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                                jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                                workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                                S44=1;
                                S51=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                  S51=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S46=0;
                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                    reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                                    S46=1;
                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 51, column: 9
                                      S44=2;
                                      S139=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                        S139=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S134=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                          motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                          S134=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 59, column: 9
                                            S44=3;
                                            S271=0;
                                            if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                              S271=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S266=0;
                                              if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                                S266=1;
                                                if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 62, column: 9
                                                  S44=4;
                                                  if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                                    S2721=0;
                                                    System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                                    S485=0;
                                                    S447=0;
                                                    if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                      S447=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S442=0;
                                                      if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                        bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                        S442=1;
                                                        if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                          S485=1;
                                                          if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                            S1016=0;
                                                            S596=0;
                                                            S492=0;
                                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                              S492=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S487=0;
                                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                S487=1;
                                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  S596=1;
                                                                  S514=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    S514=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S509=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      S509=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        S596=2;
                                                                        S603=0;
                                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          S603=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S598=0;
                                                                          if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            S598=1;
                                                                            if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              ends[1]=2;
                                                                              ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              S596=3;
                                                                              S735=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                S735=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S730=0;
                                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  S730=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    ends[1]=2;
                                                                                    ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    S44=5;
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
                                                            S1016=1;
                                                            S1015=0;
                                                            S911=0;
                                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                              S911=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S906=0;
                                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                S906=1;
                                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  S1015=1;
                                                                  S933=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    S933=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S928=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      S928=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        S44=5;
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
                                                    S2721=1;
                                                    S2720=0;
                                                    S2616=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                      S2616=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S2611=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                        S2611=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                          S2720=1;
                                                          S2638=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                            S2638=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S2633=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                              S2633=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                                S44=5;
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
                  }
                  break;
                
                case 1 : 
                  S6=1;
                  S6=0;
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 17, column: 5
                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
                      reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 17, column: 5
                      S1=1;
                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 17, column: 5
                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 17, column: 5
                        ends[1]=2;
                        ;//sysj/ConveyorController.sysj line: 17, column: 5
                        S278770=1;
                        S44=0;
                        S28=0;
                        if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                          commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                            commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                            S23=1;
                            if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                              commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 21, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                              first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                              second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                              third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                              jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                              workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                              S44=1;
                              S51=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                S51=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S46=0;
                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                  reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                                  S46=1;
                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 51, column: 9
                                    S44=2;
                                    S139=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                      S139=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S134=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                        motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                        S134=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 59, column: 9
                                          S44=3;
                                          S271=0;
                                          if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                            S271=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S266=0;
                                            if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                              photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                              S266=1;
                                              if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 62, column: 9
                                                S44=4;
                                                if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                                  S2721=0;
                                                  System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                                  S485=0;
                                                  S447=0;
                                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                    S447=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S442=0;
                                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                      S442=1;
                                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                        S485=1;
                                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                          S1016=0;
                                                          S596=0;
                                                          S492=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                            S492=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S487=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                              S487=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                                S596=1;
                                                                S514=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  S514=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S509=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    S509=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      S596=2;
                                                                      S603=0;
                                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        S603=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S598=0;
                                                                        if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          S598=1;
                                                                          if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ends[1]=2;
                                                                            ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            S596=3;
                                                                            S735=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              S735=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S730=0;
                                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                S730=1;
                                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  ends[1]=2;
                                                                                  ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  S44=5;
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
                                                          S1016=1;
                                                          S1015=0;
                                                          S911=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                            S911=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S906=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                              S906=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                                S1015=1;
                                                                S933=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  S933=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S928=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    S928=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      S44=5;
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
                                                  S2721=1;
                                                  S2720=0;
                                                  S2616=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                    S2616=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2611=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                      S2611=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                        S2720=1;
                                                        S2638=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                          S2638=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S2633=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                            S2633=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                              S44=5;
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
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 1 : 
              switch(S44){
                case 0 : 
                  switch(S28){
                    case 0 : 
                      if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                        commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                        S28=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S23){
                          case 0 : 
                            if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                              commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                                commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 21, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                                first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                                second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                                third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                                jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                                workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                                S44=1;
                                S51=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                  S51=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S46=0;
                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                    reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                                    S46=1;
                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 51, column: 9
                                      S44=2;
                                      S139=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                        S139=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S134=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                          motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                          S134=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 59, column: 9
                                            S44=3;
                                            S271=0;
                                            if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                              S271=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S266=0;
                                              if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                                S266=1;
                                                if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 62, column: 9
                                                  S44=4;
                                                  if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                                    S2721=0;
                                                    System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                                    S485=0;
                                                    S447=0;
                                                    if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                      S447=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S442=0;
                                                      if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                        bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                        S442=1;
                                                        if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                          S485=1;
                                                          if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                            S1016=0;
                                                            S596=0;
                                                            S492=0;
                                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                              S492=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S487=0;
                                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                S487=1;
                                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                                  S596=1;
                                                                  S514=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    S514=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S509=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      S509=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                        S596=2;
                                                                        S603=0;
                                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          S603=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S598=0;
                                                                          if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            S598=1;
                                                                            if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              ends[1]=2;
                                                                              ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                              S596=3;
                                                                              S735=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                S735=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S730=0;
                                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  S730=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    ends[1]=2;
                                                                                    ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                    S44=5;
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
                                                            S1016=1;
                                                            S1015=0;
                                                            S911=0;
                                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                              S911=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S906=0;
                                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                S906=1;
                                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                                  S1015=1;
                                                                  S933=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    S933=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S928=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      S928=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                        S44=5;
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
                                                    S2721=1;
                                                    S2720=0;
                                                    S2616=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                      S2616=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S2611=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                        S2611=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                          S2720=1;
                                                          S2638=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                            S2638=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S2633=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                              S2633=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                                S44=5;
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
                            if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                              commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 21, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                              first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                              second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                              third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                              jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                              workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                              S44=1;
                              S51=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                S51=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S46=0;
                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                  reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                                  S46=1;
                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 51, column: 9
                                    S44=2;
                                    S139=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                      S139=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S134=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                        motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                        S134=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 59, column: 9
                                          S44=3;
                                          S271=0;
                                          if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                            S271=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S266=0;
                                            if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                              photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                              S266=1;
                                              if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 62, column: 9
                                                S44=4;
                                                if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                                  S2721=0;
                                                  System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                                  S485=0;
                                                  S447=0;
                                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                    S447=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S442=0;
                                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                      S442=1;
                                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                        S485=1;
                                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                          S1016=0;
                                                          S596=0;
                                                          S492=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                            S492=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S487=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                              S487=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                                S596=1;
                                                                S514=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  S514=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S509=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    S509=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                      S596=2;
                                                                      S603=0;
                                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        S603=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S598=0;
                                                                        if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          S598=1;
                                                                          if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            ends[1]=2;
                                                                            ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                            S596=3;
                                                                            S735=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              S735=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S730=0;
                                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                S730=1;
                                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  ends[1]=2;
                                                                                  ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                  S44=5;
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
                                                          S1016=1;
                                                          S1015=0;
                                                          S911=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                            S911=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S906=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                              S906=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                                S1015=1;
                                                                S933=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  S933=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S928=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    S928=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                      S44=5;
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
                                                  S2721=1;
                                                  S2720=0;
                                                  S2616=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                    S2616=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2611=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                      S2611=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                        S2720=1;
                                                        S2638=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                          S2638=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S2633=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                            S2633=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                              S44=5;
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
                      S28=1;
                      S28=0;
                      if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                        commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                        S28=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S23=0;
                        if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                          commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                          S23=1;
                          if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                            commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 21, column: 9
                            command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                            first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                            second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                            third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                            jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                            workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                            S44=1;
                            S51=0;
                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                              S51=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S46=0;
                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                                S46=1;
                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 51, column: 9
                                  S44=2;
                                  S139=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                    S139=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S134=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                      motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                      S134=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 59, column: 9
                                        S44=3;
                                        S271=0;
                                        if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                          S271=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S266=0;
                                          if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                            photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                            S266=1;
                                            if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 62, column: 9
                                              S44=4;
                                              if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                                S2721=0;
                                                System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                                S485=0;
                                                S447=0;
                                                if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                  S447=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S442=0;
                                                  if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                    bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                    S442=1;
                                                    if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                      S485=1;
                                                      if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                        S1016=0;
                                                        S596=0;
                                                        S492=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                          S492=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S487=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                            S487=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                              S596=1;
                                                              S514=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                S514=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S509=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  S509=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                    S596=2;
                                                                    S603=0;
                                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      S603=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S598=0;
                                                                      if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        S598=1;
                                                                        if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                          S596=3;
                                                                          S735=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            S735=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S730=0;
                                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              S730=1;
                                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                ends[1]=2;
                                                                                ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                                S44=5;
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
                                                        S1016=1;
                                                        S1015=0;
                                                        S911=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                          S911=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S906=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                            S906=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                              S1015=1;
                                                              S933=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                S933=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S928=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  S928=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                    S44=5;
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
                                                S2721=1;
                                                S2720=0;
                                                S2616=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                  S2616=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S2611=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                    S2611=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                      S2720=1;
                                                      S2638=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                        S2638=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S2633=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                          S2633=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                            S44=5;
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
                  switch(S51){
                    case 0 : 
                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                        S51=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S46){
                          case 0 : 
                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                              reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                              S46=1;
                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 51, column: 9
                                S44=2;
                                S139=0;
                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                  S139=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S134=0;
                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                    motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                    S134=1;
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 59, column: 9
                                      S44=3;
                                      S271=0;
                                      if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                        S271=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S266=0;
                                        if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                          photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                          S266=1;
                                          if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 62, column: 9
                                            S44=4;
                                            if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                              S2721=0;
                                              System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                              S485=0;
                                              S447=0;
                                              if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                S447=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S442=0;
                                                if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                  bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                  S442=1;
                                                  if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                    S485=1;
                                                    if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                      S1016=0;
                                                      S596=0;
                                                      S492=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                        S492=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S487=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                          S487=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                            S596=1;
                                                            S514=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                              S514=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S509=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                                S509=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                  S596=2;
                                                                  S603=0;
                                                                  if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    S603=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S598=0;
                                                                    if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      S598=1;
                                                                      if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                        S596=3;
                                                                        S735=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          S735=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S730=0;
                                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            S730=1;
                                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              ends[1]=2;
                                                                              ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                              S44=5;
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
                                                      S1016=1;
                                                      S1015=0;
                                                      S911=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                        S911=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S906=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                          S906=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                            S1015=1;
                                                            S933=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                              S933=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S928=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                                S928=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                  S44=5;
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
                                              S2721=1;
                                              S2720=0;
                                              S2616=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                S2616=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S2611=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                  S2611=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                    S2720=1;
                                                    S2638=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                      S2638=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S2633=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                        S2633=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                          S44=5;
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
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          
                          case 1 : 
                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 51, column: 9
                              S44=2;
                              S139=0;
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                S139=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S134=0;
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                  motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                  S134=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 59, column: 9
                                    S44=3;
                                    S271=0;
                                    if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                      S271=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S266=0;
                                      if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                        photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                        S266=1;
                                        if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 62, column: 9
                                          S44=4;
                                          if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                            S2721=0;
                                            System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                            S485=0;
                                            S447=0;
                                            if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                              S447=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S442=0;
                                              if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                S442=1;
                                                if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                  S485=1;
                                                  if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                    S1016=0;
                                                    S596=0;
                                                    S492=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                      S492=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S487=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                        S487=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                          S596=1;
                                                          S514=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                            S514=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S509=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                              reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                              S509=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                S596=2;
                                                                S603=0;
                                                                if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  S603=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S598=0;
                                                                  if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    S598=1;
                                                                    if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      S596=3;
                                                                      S735=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        S735=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S730=0;
                                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          S730=1;
                                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            ends[1]=2;
                                                                            ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            S44=5;
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
                                                    S1016=1;
                                                    S1015=0;
                                                    S911=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                      S911=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S906=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                        S906=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                          S1015=1;
                                                          S933=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                            S933=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S928=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                              S928=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                S44=5;
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
                                            S2721=1;
                                            S2720=0;
                                            S2616=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                              S2616=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S2611=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                S2611=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                  S2720=1;
                                                  S2638=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                    S2638=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2633=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                      S2633=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                        S44=5;
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
                      }
                      break;
                    
                    case 1 : 
                      S51=1;
                      S51=0;
                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                        S51=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S46=0;
                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                          reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                          S46=1;
                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 51, column: 9
                            S44=2;
                            S139=0;
                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                              S139=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S134=0;
                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                S134=1;
                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 59, column: 9
                                  S44=3;
                                  S271=0;
                                  if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                    S271=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S266=0;
                                    if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                      photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                      S266=1;
                                      if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 62, column: 9
                                        S44=4;
                                        if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                          S2721=0;
                                          System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                          S485=0;
                                          S447=0;
                                          if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                            S447=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S442=0;
                                            if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                              bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                              S442=1;
                                              if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                S485=1;
                                                if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                  S1016=0;
                                                  S596=0;
                                                  S492=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                    S492=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S487=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                      S487=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                        S596=1;
                                                        S514=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                          S514=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S509=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                            reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                            S509=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                              S596=2;
                                                              S603=0;
                                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                S603=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S598=0;
                                                                if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  S598=1;
                                                                  if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    S596=3;
                                                                    S735=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      S735=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S730=0;
                                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        S730=1;
                                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          S44=5;
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
                                                  S1016=1;
                                                  S1015=0;
                                                  S911=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                    S911=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S906=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                      S906=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                        S1015=1;
                                                        S933=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                          S933=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S928=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                            S928=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                              S44=5;
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
                                          S2721=1;
                                          S2720=0;
                                          S2616=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                            S2616=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S2611=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                              S2611=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                S2720=1;
                                                S2638=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                  S2638=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S2633=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                    S2633=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                      S44=5;
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
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      }
                    
                  }
                  break;
                
                case 2 : 
                  switch(S139){
                    case 0 : 
                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                        S139=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S134){
                          case 0 : 
                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                              motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                              S134=1;
                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 59, column: 9
                                S44=3;
                                S271=0;
                                if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                  S271=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S266=0;
                                  if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                    photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                    S266=1;
                                    if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 62, column: 9
                                      S44=4;
                                      if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                        S2721=0;
                                        System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                        S485=0;
                                        S447=0;
                                        if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                          S447=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S442=0;
                                          if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                            bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                            S442=1;
                                            if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 72, column: 13
                                              S485=1;
                                              if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                S1016=0;
                                                S596=0;
                                                S492=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                  S492=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S487=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                    S487=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                      S596=1;
                                                      S514=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                        S514=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S509=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                          reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                          S509=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                            S596=2;
                                                            S603=0;
                                                            if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                              ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                              S603=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S598=0;
                                                              if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                S598=1;
                                                                if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  S596=3;
                                                                  S735=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    S735=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S730=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      S730=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        S44=5;
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
                                                S1016=1;
                                                S1015=0;
                                                S911=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                  S911=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S906=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                    S906=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                      S1015=1;
                                                      S933=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                        S933=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S928=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                          S928=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                            S44=5;
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
                                        S2721=1;
                                        S2720=0;
                                        S2616=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                          S2616=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S2611=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                            S2611=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 113, column: 13
                                              S2720=1;
                                              S2638=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                S2638=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S2633=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                  S2633=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                    S44=5;
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
                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 59, column: 9
                              S44=3;
                              S271=0;
                              if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                S271=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S266=0;
                                if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                  photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                  S266=1;
                                  if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 62, column: 9
                                    S44=4;
                                    if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                      S2721=0;
                                      System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                      S485=0;
                                      S447=0;
                                      if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                        S447=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S442=0;
                                        if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                          bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                          S442=1;
                                          if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 72, column: 13
                                            S485=1;
                                            if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                              S1016=0;
                                              S596=0;
                                              S492=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                S492=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S487=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                  S487=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                    S596=1;
                                                    S514=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                      S514=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S509=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                        reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                        S509=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                          S596=2;
                                                          S603=0;
                                                          if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                            S603=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S598=0;
                                                            if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                              ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                              S598=1;
                                                              if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                S596=3;
                                                                S735=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  S735=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S730=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    S730=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                      S44=5;
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
                                              S1016=1;
                                              S1015=0;
                                              S911=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                S911=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S906=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                  S906=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                    S1015=1;
                                                    S933=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                      S933=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S928=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                        S928=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                          S44=5;
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
                                      S2721=1;
                                      S2720=0;
                                      S2616=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                        S2616=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S2611=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                          S2611=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 113, column: 13
                                            S2720=1;
                                            S2638=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                              S2638=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S2633=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                S2633=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                  S44=5;
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
                      S139=1;
                      S139=0;
                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                        S139=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S134=0;
                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                          motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                          S134=1;
                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 59, column: 9
                            S44=3;
                            S271=0;
                            if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                              S271=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S266=0;
                              if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                S266=1;
                                if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 62, column: 9
                                  S44=4;
                                  if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                    S2721=0;
                                    System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                    S485=0;
                                    S447=0;
                                    if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                      S447=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S442=0;
                                      if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                        bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                        S442=1;
                                        if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 72, column: 13
                                          S485=1;
                                          if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                            S1016=0;
                                            S596=0;
                                            S492=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              S492=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S487=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                S487=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                  S596=1;
                                                  S514=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S514=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S509=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                      S509=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                        S596=2;
                                                        S603=0;
                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S603=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S598=0;
                                                          if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                            S598=1;
                                                            if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                              ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                              S596=3;
                                                              S735=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S735=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S730=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  S730=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                    S44=5;
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
                                            S1016=1;
                                            S1015=0;
                                            S911=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              S911=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S906=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                S906=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                  S1015=1;
                                                  S933=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S933=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S928=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                      S928=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                        S44=5;
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
                                    S2721=1;
                                    S2720=0;
                                    S2616=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                      S2616=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S2611=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                        S2611=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 113, column: 13
                                          S2720=1;
                                          S2638=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                            S2638=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S2633=0;
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                              S2633=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                S44=5;
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
                
                case 3 : 
                  switch(S271){
                    case 0 : 
                      if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                        S271=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S266){
                          case 0 : 
                            if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                              photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                              S266=1;
                              if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 62, column: 9
                                S44=4;
                                if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                  S2721=0;
                                  System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                  S485=0;
                                  S447=0;
                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                    S447=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S442=0;
                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                      S442=1;
                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 72, column: 13
                                        S485=1;
                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                          S1016=0;
                                          S596=0;
                                          S492=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            S492=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S487=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              S487=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                S596=1;
                                                S514=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S514=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S509=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S509=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                      S596=2;
                                                      S603=0;
                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S603=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S598=0;
                                                        if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S598=1;
                                                          if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                            S596=3;
                                                            S735=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S735=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S730=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S730=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  S44=5;
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
                                          S1016=1;
                                          S1015=0;
                                          S911=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            S911=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S906=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              S906=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                S1015=1;
                                                S933=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S933=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S928=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S928=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                      S44=5;
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
                                  S2721=1;
                                  S2720=0;
                                  S2616=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                    S2616=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S2611=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                      S2611=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 113, column: 13
                                        S2720=1;
                                        S2638=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                          S2638=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S2633=0;
                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                            S2633=1;
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 115, column: 13
                                              S44=5;
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
                            if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 62, column: 9
                              S44=4;
                              if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                S2721=0;
                                System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                S485=0;
                                S447=0;
                                if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                  S447=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S442=0;
                                  if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                    bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                    S442=1;
                                    if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 72, column: 13
                                      S485=1;
                                      if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                        S1016=0;
                                        S596=0;
                                        S492=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                          S492=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S487=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            S487=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 77, column: 17
                                              S596=1;
                                              S514=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                S514=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S509=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S509=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S596=2;
                                                    S603=0;
                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S603=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S598=0;
                                                      if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S598=1;
                                                        if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S596=3;
                                                          S735=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S735=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S730=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S730=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S44=5;
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
                                        S1016=1;
                                        S1015=0;
                                        S911=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                          S911=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S906=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            S906=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 98, column: 17
                                              S1015=1;
                                              S933=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                S933=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S928=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S928=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S44=5;
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
                                S2721=1;
                                S2720=0;
                                S2616=0;
                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                  S2616=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S2611=0;
                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                    S2611=1;
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 113, column: 13
                                      S2720=1;
                                      S2638=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                        S2638=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S2633=0;
                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                          S2633=1;
                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 115, column: 13
                                            S44=5;
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
                      S271=1;
                      S271=0;
                      if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                        S271=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S266=0;
                        if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                          photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                          S266=1;
                          if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 62, column: 9
                            S44=4;
                            if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                              S2721=0;
                              System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                              S485=0;
                              S447=0;
                              if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                S447=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S442=0;
                                if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                  bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                  S442=1;
                                  if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 72, column: 13
                                    S485=1;
                                    if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                      S1016=0;
                                      S596=0;
                                      S492=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                        S492=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S487=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                          S487=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 77, column: 17
                                            S596=1;
                                            S514=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                              S514=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S509=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                S509=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S596=2;
                                                  S603=0;
                                                  if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                    S603=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S598=0;
                                                    if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S598=1;
                                                      if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S596=3;
                                                        S735=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                          S735=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S730=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S730=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S44=5;
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
                                      S1016=1;
                                      S1015=0;
                                      S911=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                        S911=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S906=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                          S906=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 98, column: 17
                                            S1015=1;
                                            S933=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                              S933=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S928=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                S928=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S44=5;
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
                              S2721=1;
                              S2720=0;
                              S2616=0;
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                S2616=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S2611=0;
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                  S2611=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 113, column: 13
                                    S2720=1;
                                    S2638=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                      S2638=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S2633=0;
                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                        S2633=1;
                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 115, column: 13
                                          S44=5;
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
                  switch(S2721){
                    case 0 : 
                      switch(S485){
                        case 0 : 
                          switch(S447){
                            case 0 : 
                              if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                S447=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S442){
                                  case 0 : 
                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                      S442=1;
                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 72, column: 13
                                        S485=1;
                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                          S1016=0;
                                          S596=0;
                                          S492=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            S492=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S487=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              S487=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                S596=1;
                                                S514=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S514=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S509=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S509=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                      S596=2;
                                                      S603=0;
                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S603=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S598=0;
                                                        if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S598=1;
                                                          if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                            S596=3;
                                                            S735=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S735=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S730=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S730=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  S44=5;
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
                                          S1016=1;
                                          S1015=0;
                                          S911=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            S911=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S906=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              S906=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                S1015=1;
                                                S933=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S933=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S928=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S928=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                      S44=5;
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
                                    if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 72, column: 13
                                      S485=1;
                                      if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                        S1016=0;
                                        S596=0;
                                        S492=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                          S492=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S487=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            S487=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 77, column: 17
                                              S596=1;
                                              S514=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                S514=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S509=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S509=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S596=2;
                                                    S603=0;
                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S603=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S598=0;
                                                      if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S598=1;
                                                        if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S596=3;
                                                          S735=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S735=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S730=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S730=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S44=5;
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
                                        S1016=1;
                                        S1015=0;
                                        S911=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                          S911=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S906=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            S906=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 98, column: 17
                                              S1015=1;
                                              S933=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                S933=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S928=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S928=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S44=5;
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
                              S447=1;
                              S447=0;
                              if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                S447=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S442=0;
                                if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                  bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                  S442=1;
                                  if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 72, column: 13
                                    S485=1;
                                    if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                      S1016=0;
                                      S596=0;
                                      S492=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                        S492=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S487=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                          S487=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 77, column: 17
                                            S596=1;
                                            S514=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                              S514=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S509=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                S509=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S596=2;
                                                  S603=0;
                                                  if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                    S603=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S598=0;
                                                    if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S598=1;
                                                      if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S596=3;
                                                        S735=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                          S735=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S730=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S730=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S44=5;
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
                                      S1016=1;
                                      S1015=0;
                                      S911=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                        S911=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S906=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                          S906=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 98, column: 17
                                            S1015=1;
                                            S933=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                              S933=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S928=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                S928=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S44=5;
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
                          switch(S1016){
                            case 0 : 
                              switch(S596){
                                case 0 : 
                                  switch(S492){
                                    case 0 : 
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                        S492=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S487){
                                          case 0 : 
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              S487=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                S596=1;
                                                S514=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S514=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S509=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S509=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                      S596=2;
                                                      S603=0;
                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S603=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S598=0;
                                                        if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S598=1;
                                                          if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                            S596=3;
                                                            S735=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S735=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S730=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S730=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                  S44=5;
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
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 77, column: 17
                                              S596=1;
                                              S514=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                S514=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S509=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S509=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                    S596=2;
                                                    S603=0;
                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S603=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S598=0;
                                                      if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S598=1;
                                                        if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                          S596=3;
                                                          S735=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S735=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S730=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S730=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                S44=5;
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
                                      S492=1;
                                      S492=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                        S492=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S487=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                          S487=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 77, column: 17
                                            S596=1;
                                            S514=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                              S514=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S509=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                S509=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                  S596=2;
                                                  S603=0;
                                                  if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                    S603=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S598=0;
                                                    if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S598=1;
                                                      if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                        S596=3;
                                                        S735=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                          S735=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S730=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S730=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                              S44=5;
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
                                  switch(S514){
                                    case 0 : 
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                        S514=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S509){
                                          case 0 : 
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                              reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                              S509=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                S596=2;
                                                S603=0;
                                                if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                  ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                  S603=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S598=0;
                                                  if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                    ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                    S598=1;
                                                    if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                      S596=3;
                                                      S735=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                        S735=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S730=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                          reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                          S730=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                            S44=5;
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
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          
                                          case 1 : 
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 79, column: 17
                                              S596=2;
                                              S603=0;
                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                S603=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S598=0;
                                                if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                  ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                  S598=1;
                                                  if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                    ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                    S596=3;
                                                    S735=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                      S735=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S730=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                        reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                        S730=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                          S44=5;
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
                                      }
                                      break;
                                    
                                    case 1 : 
                                      S514=1;
                                      S514=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                        S514=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S509=0;
                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                          reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                          S509=1;
                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 79, column: 17
                                            S596=2;
                                            S603=0;
                                            if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                              ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                              S603=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S598=0;
                                              if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                S598=1;
                                                if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                  ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                  S596=3;
                                                  S735=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                    S735=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S730=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                      reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                      S730=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                        S44=5;
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
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    
                                  }
                                  break;
                                
                                case 2 : 
                                  switch(S603){
                                    case 0 : 
                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                        S603=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S598){
                                          case 0 : 
                                            if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                              ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                              S598=1;
                                              if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                S596=3;
                                                S735=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                  S735=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S730=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                    reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                    S730=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                      S44=5;
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
                                            if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                              ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 87, column: 17
                                              S596=3;
                                              S735=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                S735=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S730=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                  reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                  S730=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                    S44=5;
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
                                      S603=1;
                                      S603=0;
                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                        ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                        S603=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S598=0;
                                        if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                          ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                          S598=1;
                                          if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                            ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 87, column: 17
                                            S596=3;
                                            S735=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                              S735=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S730=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                S730=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                  S44=5;
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
                                
                                case 3 : 
                                  switch(S735){
                                    case 0 : 
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                        S735=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S730){
                                          case 0 : 
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                              reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                              S730=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                S44=5;
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
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 90, column: 17
                                              S44=5;
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
                                      S735=1;
                                      S735=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                        S735=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S730=0;
                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                          reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                          S730=1;
                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 90, column: 17
                                            S44=5;
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
                            
                            case 1 : 
                              switch(S1015){
                                case 0 : 
                                  switch(S911){
                                    case 0 : 
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                        S911=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S906){
                                          case 0 : 
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              S906=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                S1015=1;
                                                S933=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S933=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S928=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S928=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                      S44=5;
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
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 98, column: 17
                                              S1015=1;
                                              S933=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                S933=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S928=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S928=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                    S44=5;
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
                                      S911=1;
                                      S911=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                        S911=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S906=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                          S906=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 98, column: 17
                                            S1015=1;
                                            S933=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                              S933=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S928=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                S928=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                  S44=5;
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
                                  switch(S933){
                                    case 0 : 
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                        S933=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S928){
                                          case 0 : 
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                              S928=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                S44=5;
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
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 100, column: 17
                                              S44=5;
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
                                      S933=1;
                                      S933=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                        S933=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S928=0;
                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                          S928=1;
                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 100, column: 17
                                            S44=5;
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
                            
                          }
                          break;
                        
                      }
                      break;
                    
                    case 1 : 
                      switch(S2720){
                        case 0 : 
                          switch(S2616){
                            case 0 : 
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                S2616=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S2611){
                                  case 0 : 
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                      S2611=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 113, column: 13
                                        S2720=1;
                                        S2638=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                          S2638=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S2633=0;
                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                            S2633=1;
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 115, column: 13
                                              S44=5;
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
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 113, column: 13
                                      S2720=1;
                                      S2638=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                        S2638=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S2633=0;
                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                          S2633=1;
                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 115, column: 13
                                            S44=5;
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
                              S2616=1;
                              S2616=0;
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                S2616=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S2611=0;
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                  S2611=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 113, column: 13
                                    S2720=1;
                                    S2638=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                      S2638=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S2633=0;
                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                        S2633=1;
                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 115, column: 13
                                          S44=5;
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
                          switch(S2638){
                            case 0 : 
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                S2638=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S2633){
                                  case 0 : 
                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                      S2633=1;
                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 115, column: 13
                                        S44=5;
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
                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 115, column: 13
                                      S44=5;
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
                              S2638=1;
                              S2638=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                S2638=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S2633=0;
                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                  S2633=1;
                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 115, column: 13
                                    S44=5;
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
                    
                  }
                  break;
                
                case 5 : 
                  S44=5;
                  S44=0;
                  S28=0;
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 21, column: 9
                    commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                      commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 21, column: 9
                      S23=1;
                      if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 21, column: 9
                        commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 21, column: 9
                        ends[1]=2;
                        ;//sysj/ConveyorController.sysj line: 21, column: 9
                        command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 23, column: 9
                        first_thread_1 = command_thread_1.indexOf("|");//sysj/ConveyorController.sysj line: 25, column: 9
                        second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/ConveyorController.sysj line: 27, column: 9
                        third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/ConveyorController.sysj line: 30, column: 9
                        jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/ConveyorController.sysj line: 33, column: 9
                        workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/ConveyorController.sysj line: 39, column: 9
                        System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 46, column: 9
                        S44=1;
                        S51=0;
                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 51, column: 9
                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                          S51=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S46=0;
                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                            reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR");//sysj/ConveyorController.sysj line: 51, column: 9
                            S46=1;
                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 51, column: 9
                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 51, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 51, column: 9
                              S44=2;
                              S139=0;
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 9
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                S139=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S134=0;
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                  motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 59, column: 9
                                  S134=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 9
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 9
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 59, column: 9
                                    S44=3;
                                    S271=0;
                                    if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 62, column: 9
                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                      S271=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S266=0;
                                      if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                        photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 62, column: 9
                                        S266=1;
                                        if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 62, column: 9
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 62, column: 9
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 62, column: 9
                                          S44=4;
                                          if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 65, column: 12
                                            S2721=0;
                                            System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 67, column: 13
                                            S485=0;
                                            S447=0;
                                            if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 72, column: 13
                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                              S447=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S442=0;
                                              if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 72, column: 13
                                                S442=1;
                                                if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 72, column: 13
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 72, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 72, column: 13
                                                  S485=1;
                                                  if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 75, column: 16
                                                    S1016=0;
                                                    S596=0;
                                                    S492=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                      S492=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S487=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                        S487=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 77, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 77, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 77, column: 17
                                                          S596=1;
                                                          S514=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                            S514=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S509=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                              reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|OK|bottleMoved=true");//sysj/ConveyorController.sysj line: 79, column: 17
                                                              S509=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 79, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 79, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 79, column: 17
                                                                S596=2;
                                                                S603=0;
                                                                if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                  S603=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S598=0;
                                                                  if(!ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    ackIn_in.setACK(true);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                    S598=1;
                                                                    if(ackIn_in.isREQ()){//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      ackIn_in.setACK(false);//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 87, column: 17
                                                                      S596=3;
                                                                      S735=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                        S735=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S730=0;
                                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          reportOut_o.setVal("READY|CONVEYOR");//sysj/ConveyorController.sysj line: 90, column: 17
                                                                          S730=1;
                                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            ends[1]=2;
                                                                            ;//sysj/ConveyorController.sysj line: 90, column: 17
                                                                            S44=5;
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
                                                    S1016=1;
                                                    S1015=0;
                                                    S911=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                      S911=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S906=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                        S906=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 98, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 98, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 98, column: 17
                                                          S1015=1;
                                                          S933=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                            S933=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S928=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 100, column: 17
                                                              S928=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 100, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 100, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 100, column: 17
                                                                S44=5;
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
                                            S2721=1;
                                            S2720=0;
                                            S2616=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 113, column: 13
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                              S2616=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S2611=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                S2611=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 113, column: 13
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 113, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 113, column: 13
                                                  S2720=1;
                                                  S2638=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                    S2638=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2633=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|CONVEYOR|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 115, column: 13
                                                      S2633=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 115, column: 13
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 115, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 115, column: 13
                                                        S44=5;
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
          commandIn_in.gethook();
          ackIn_in.gethook();
          photoEye_in.gethook();
          bottleLeft_in.gethook();
          reportOut_o.gethook();
          motorRun_o.gethook();
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
      commandIn_in.sethook();
      ackIn_in.sethook();
      photoEye_in.sethook();
      bottleLeft_in.sethook();
      reportOut_o.sethook();
      motorRun_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        commandIn_in.gethook();
        ackIn_in.gethook();
        photoEye_in.gethook();
        bottleLeft_in.gethook();
        reportOut_o.gethook();
        motorRun_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
