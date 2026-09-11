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
  public input_Channel photoEye_in = new input_Channel();
  public input_Channel bottleLeft_in = new input_Channel();
  public output_Channel reportOut_o = new output_Channel();
  public output_Channel motorRun_o = new output_Channel();
  private String command_thread_1;//sysj/ConveyorController.sysj line: 20, column: 9
  private String workpieceId_thread_1;//sysj/ConveyorController.sysj line: 25, column: 13
  private int S157024 = 1;
  private int S110 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S8815 = 1;
  private int S111 = 1;
  private int S118 = 1;
  private int S113 = 1;
  private int S162 = 1;
  private int S157 = 1;
  private int S250 = 1;
  private int S245 = 1;
  private int S1424 = 1;
  private int S420 = 1;
  private int S382 = 1;
  private int S377 = 1;
  private int S643 = 1;
  private int S531 = 1;
  private int S427 = 1;
  private int S422 = 1;
  private int S449 = 1;
  private int S444 = 1;
  private int S642 = 1;
  private int S538 = 1;
  private int S533 = 1;
  private int S560 = 1;
  private int S555 = 1;
  private int S1423 = 1;
  private int S1319 = 1;
  private int S1314 = 1;
  private int S1341 = 1;
  private int S1336 = 1;
  private int S8774 = 1;
  private int S8769 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S157024){
        case 0 : 
          S157024=0;
          break RUN;
        
        case 1 : 
          S157024=2;
          S157024=2;
          S110=0;
          S6=0;
          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 15, column: 9
            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
              reportOut_o.setVal("READY");//sysj/ConveyorController.sysj line: 15, column: 9
              S1=1;
              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                ends[1]=2;
                ;//sysj/ConveyorController.sysj line: 15, column: 9
                S110=1;
                S28=0;
                if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                  commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                    commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                    S23=1;
                    if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                      commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                      ends[1]=2;
                      ;//sysj/ConveyorController.sysj line: 18, column: 9
                      command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                      S110=2;
                      if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                        S8815=0;
                        workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                        System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                        S111=0;
                        S118=0;
                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                          S118=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S113=0;
                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                            reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                            S113=1;
                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 32, column: 13
                              S111=1;
                              S162=0;
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                  motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                  S157=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 38, column: 13
                                    S111=2;
                                    S250=0;
                                    if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                      S250=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S245=0;
                                      if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                        photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                        S245=1;
                                        if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 42, column: 13
                                          S111=3;
                                          if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                            S1424=0;
                                            System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                            S420=0;
                                            S382=0;
                                            if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                              S382=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S377=0;
                                              if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                S377=1;
                                                if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S420=1;
                                                  if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                    S643=0;
                                                    S531=0;
                                                    S427=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                      S427=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S422=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                        S422=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S531=1;
                                                          S449=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                            S449=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S444=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                              S444=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S110=3;
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
                                                    S643=1;
                                                    S642=0;
                                                    S538=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                      S538=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S533=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                        S533=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S642=1;
                                                          S560=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                            S560=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S555=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                              S555=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S110=3;
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
                                            S1424=1;
                                            S1423=0;
                                            S1319=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                              S1319=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1314=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                S1314=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1423=1;
                                                  S1341=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                    S1341=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1336=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                      S1336=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S110=3;
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
                        S8815=1;
                        if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                          System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                          S110=3;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                            System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                            S8774=0;
                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                              S8774=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S8769=0;
                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                S8769=1;
                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 105, column: 13
                                  S110=3;
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
                            System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                            S110=3;
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
        
        case 2 : 
          switch(S110){
            case 0 : 
              switch(S6){
                case 0 : 
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 15, column: 9
                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                          reportOut_o.setVal("READY");//sysj/ConveyorController.sysj line: 15, column: 9
                          S1=1;
                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 15, column: 9
                            S110=1;
                            S28=0;
                            if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                              commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                                commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                                S23=1;
                                if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                                  commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 18, column: 9
                                  command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                                  S110=2;
                                  if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                                    S8815=0;
                                    workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                                    System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                                    S111=0;
                                    S118=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                      S118=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S113=0;
                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                        reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                        S113=1;
                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 32, column: 13
                                          S111=1;
                                          S162=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                            S162=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S157=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                              motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                              S157=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 38, column: 13
                                                S111=2;
                                                S250=0;
                                                if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                  S250=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S245=0;
                                                  if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                    photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                                    S245=1;
                                                    if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 42, column: 13
                                                      S111=3;
                                                      if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                        S1424=0;
                                                        System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                        S420=0;
                                                        S382=0;
                                                        if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                          S382=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S377=0;
                                                          if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                            bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                            S377=1;
                                                            if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                              S420=1;
                                                              if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                                S643=0;
                                                                S531=0;
                                                                S427=0;
                                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  S427=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S422=0;
                                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                    S422=1;
                                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                                      S531=1;
                                                                      S449=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        S449=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S444=0;
                                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                          S444=1;
                                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                            S110=3;
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
                                                                S643=1;
                                                                S642=0;
                                                                S538=0;
                                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  S538=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S533=0;
                                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                    S533=1;
                                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                                      S642=1;
                                                                      S560=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        S560=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S555=0;
                                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                          S555=1;
                                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                            S110=3;
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
                                                        S1424=1;
                                                        S1423=0;
                                                        S1319=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                          S1319=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1314=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                            S1314=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                              S1423=1;
                                                              S1341=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                                S1341=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S1336=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                                  S1336=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                                    S110=3;
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
                                    S8815=1;
                                    if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                                      System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                                      S110=3;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                                        System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                                        S8774=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                          S8774=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S8769=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                            S8769=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 105, column: 13
                                              S110=3;
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
                                        System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                                        S110=3;
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
                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                          ends[1]=2;
                          ;//sysj/ConveyorController.sysj line: 15, column: 9
                          S110=1;
                          S28=0;
                          if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                            commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                              commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                                commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 18, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                                S110=2;
                                if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                                  S8815=0;
                                  workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                                  System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                                  S111=0;
                                  S118=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                    S118=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S113=0;
                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                      S113=1;
                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 32, column: 13
                                        S111=1;
                                        S162=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                          S162=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S157=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                            motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                            S157=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 38, column: 13
                                              S111=2;
                                              S250=0;
                                              if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                S250=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S245=0;
                                                if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                  photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                                  S245=1;
                                                  if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 42, column: 13
                                                    S111=3;
                                                    if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                      S1424=0;
                                                      System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                      S420=0;
                                                      S382=0;
                                                      if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                        S382=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S377=0;
                                                        if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                          bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                          S377=1;
                                                          if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                            S420=1;
                                                            if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                              S643=0;
                                                              S531=0;
                                                              S427=0;
                                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                S427=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S422=0;
                                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  S422=1;
                                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                                    S531=1;
                                                                    S449=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      S449=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S444=0;
                                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        S444=1;
                                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                          S110=3;
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
                                                              S643=1;
                                                              S642=0;
                                                              S538=0;
                                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                S538=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S533=0;
                                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  S533=1;
                                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                                    S642=1;
                                                                    S560=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      S560=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S555=0;
                                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        S555=1;
                                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                          S110=3;
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
                                                      S1424=1;
                                                      S1423=0;
                                                      S1319=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                        S1319=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1314=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                          S1314=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                            S1423=1;
                                                            S1341=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                              S1341=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S1336=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                                S1336=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                                  S110=3;
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
                                  S8815=1;
                                  if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                                    System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                                    S110=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                                      System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                                      S8774=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                        S8774=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S8769=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                          S8769=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 105, column: 13
                                            S110=3;
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
                                      System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                                      S110=3;
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
                  S6=1;
                  S6=0;
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 15, column: 9
                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                      reportOut_o.setVal("READY");//sysj/ConveyorController.sysj line: 15, column: 9
                      S1=1;
                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                        ends[1]=2;
                        ;//sysj/ConveyorController.sysj line: 15, column: 9
                        S110=1;
                        S28=0;
                        if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                          commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                            commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                            S23=1;
                            if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                              commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 18, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                              S110=2;
                              if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                                S8815=0;
                                workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                                S111=0;
                                S118=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                  S118=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S113=0;
                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                    reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                    S113=1;
                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 32, column: 13
                                      S111=1;
                                      S162=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                        S162=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S157=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                          motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                          S157=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 38, column: 13
                                            S111=2;
                                            S250=0;
                                            if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                              S250=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S245=0;
                                              if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                                S245=1;
                                                if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 42, column: 13
                                                  S111=3;
                                                  if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                    S1424=0;
                                                    System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                    S420=0;
                                                    S382=0;
                                                    if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                      S382=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S377=0;
                                                      if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                        bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                        S377=1;
                                                        if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                          S420=1;
                                                          if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                            S643=0;
                                                            S531=0;
                                                            S427=0;
                                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                              S427=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S422=0;
                                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                S422=1;
                                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                                  S531=1;
                                                                  S449=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    S449=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S444=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      S444=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                        S110=3;
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
                                                            S643=1;
                                                            S642=0;
                                                            S538=0;
                                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                              S538=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S533=0;
                                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                S533=1;
                                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                                  S642=1;
                                                                  S560=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    S560=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S555=0;
                                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      S555=1;
                                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                        S110=3;
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
                                                    S1424=1;
                                                    S1423=0;
                                                    S1319=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                      S1319=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1314=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                        S1314=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                          S1423=1;
                                                          S1341=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                            S1341=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S1336=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                              S1336=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                                S110=3;
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
                                S8815=1;
                                if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                                  System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                                  S110=3;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                                    System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                                    S8774=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                      S8774=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S8769=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                        S8769=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 105, column: 13
                                          S110=3;
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
                                    System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                                    S110=3;
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
            
            case 1 : 
              switch(S28){
                case 0 : 
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                    commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S23){
                      case 0 : 
                        if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                          commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                          S23=1;
                          if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                            commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 18, column: 9
                            command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                            S110=2;
                            if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                              S8815=0;
                              workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                              S111=0;
                              S118=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                S118=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S113=0;
                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                  S113=1;
                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 32, column: 13
                                    S111=1;
                                    S162=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                        motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                        S157=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 38, column: 13
                                          S111=2;
                                          S250=0;
                                          if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                            S250=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S245=0;
                                            if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                              photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                              S245=1;
                                              if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 42, column: 13
                                                S111=3;
                                                if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                  S1424=0;
                                                  System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                  S420=0;
                                                  S382=0;
                                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S382=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S377=0;
                                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                      S377=1;
                                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                        S420=1;
                                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                          S643=0;
                                                          S531=0;
                                                          S427=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S427=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S422=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                              S422=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                                S531=1;
                                                                S449=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S449=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S444=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    S444=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      S110=3;
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
                                                          S643=1;
                                                          S642=0;
                                                          S538=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S538=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S533=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                              S533=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                                S642=1;
                                                                S560=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S560=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S555=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    S555=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      S110=3;
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
                                                  S1424=1;
                                                  S1423=0;
                                                  S1319=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1319=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1314=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                      S1314=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                        S1423=1;
                                                        S1341=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S1341=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1336=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                            S1336=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                              S110=3;
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
                              S8815=1;
                              if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                                System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                                S110=3;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                                  System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                                  S8774=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                    S8774=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S8769=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                      S8769=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 105, column: 13
                                        S110=3;
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
                                  System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                                  S110=3;
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
                        if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                          commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                          ends[1]=2;
                          ;//sysj/ConveyorController.sysj line: 18, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                            S8815=0;
                            workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                            S111=0;
                            S118=0;
                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                S113=1;
                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 32, column: 13
                                  S111=1;
                                  S162=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                      S157=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 38, column: 13
                                        S111=2;
                                        S250=0;
                                        if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                          S250=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S245=0;
                                          if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                            S245=1;
                                            if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 42, column: 13
                                              S111=3;
                                              if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                S1424=0;
                                                System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                S420=0;
                                                S382=0;
                                                if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S382=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S377=0;
                                                  if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S377=1;
                                                    if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                      S420=1;
                                                      if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                        S643=0;
                                                        S531=0;
                                                        S427=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S427=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S422=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S422=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                              S531=1;
                                                              S449=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S449=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S444=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S444=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    S110=3;
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
                                                        S643=1;
                                                        S642=0;
                                                        S538=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S538=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S533=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S533=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                              S642=1;
                                                              S560=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S560=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S555=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S555=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    S110=3;
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
                                                S1424=1;
                                                S1423=0;
                                                S1319=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1319=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1314=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1314=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                      S1423=1;
                                                      S1341=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S1341=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1336=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S1336=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                            S110=3;
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
                            S8815=1;
                            if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                              System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                              S110=3;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                                System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                                S8774=0;
                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                  S8774=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S8769=0;
                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                    S8769=1;
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 105, column: 13
                                      S110=3;
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
                                System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                                S110=3;
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
                  S28=1;
                  S28=0;
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                    commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                      commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                      S23=1;
                      if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                        commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                        ends[1]=2;
                        ;//sysj/ConveyorController.sysj line: 18, column: 9
                        command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                        S110=2;
                        if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                          S8815=0;
                          workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                          System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                          S111=0;
                          S118=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                            S118=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S113=0;
                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                              reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                              S113=1;
                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 32, column: 13
                                S111=1;
                                S162=0;
                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                    S157=1;
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 38, column: 13
                                      S111=2;
                                      S250=0;
                                      if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                        S250=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S245=0;
                                        if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                          S245=1;
                                          if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 42, column: 13
                                            S111=3;
                                            if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                              S1424=0;
                                              System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                              S420=0;
                                              S382=0;
                                              if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                S382=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S377=0;
                                                if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S377=1;
                                                  if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S420=1;
                                                    if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                      S643=0;
                                                      S531=0;
                                                      S427=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                        S427=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S422=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S422=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S531=1;
                                                            S449=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                              S449=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S444=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S444=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S110=3;
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
                                                      S643=1;
                                                      S642=0;
                                                      S538=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                        S538=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S533=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S533=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S642=1;
                                                            S560=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                              S560=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S555=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S555=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S110=3;
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
                                              S1424=1;
                                              S1423=0;
                                              S1319=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                S1319=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1314=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1314=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1423=1;
                                                    S1341=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                      S1341=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1336=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S1336=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S110=3;
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
                          S8815=1;
                          if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                            System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                            S110=3;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                              System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                              S8774=0;
                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                S8774=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S8769=0;
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                  S8769=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 105, column: 13
                                    S110=3;
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
                              System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                              S110=3;
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
            
            case 2 : 
              switch(S8815){
                case 0 : 
                  switch(S111){
                    case 0 : 
                      switch(S118){
                        case 0 : 
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                            S118=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S113){
                              case 0 : 
                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                  S113=1;
                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 32, column: 13
                                    S111=1;
                                    S162=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                        motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                        S157=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 38, column: 13
                                          S111=2;
                                          S250=0;
                                          if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                            S250=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S245=0;
                                            if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                              photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                              S245=1;
                                              if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 42, column: 13
                                                S111=3;
                                                if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                  S1424=0;
                                                  System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                  S420=0;
                                                  S382=0;
                                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S382=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S377=0;
                                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                      S377=1;
                                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                        S420=1;
                                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                          S643=0;
                                                          S531=0;
                                                          S427=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S427=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S422=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                              S422=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                                S531=1;
                                                                S449=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S449=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S444=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    S444=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                      S110=3;
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
                                                          S643=1;
                                                          S642=0;
                                                          S538=0;
                                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S538=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S533=0;
                                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                              S533=1;
                                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                                S642=1;
                                                                S560=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S560=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S555=0;
                                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    S555=1;
                                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                      S110=3;
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
                                                  S1424=1;
                                                  S1423=0;
                                                  S1319=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1319=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1314=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                      S1314=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                        S1423=1;
                                                        S1341=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S1341=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1336=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                            S1336=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                              S110=3;
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
                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 32, column: 13
                                  S111=1;
                                  S162=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                      S157=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 38, column: 13
                                        S111=2;
                                        S250=0;
                                        if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                          S250=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S245=0;
                                          if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                            S245=1;
                                            if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 42, column: 13
                                              S111=3;
                                              if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                S1424=0;
                                                System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                S420=0;
                                                S382=0;
                                                if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S382=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S377=0;
                                                  if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S377=1;
                                                    if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                      S420=1;
                                                      if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                        S643=0;
                                                        S531=0;
                                                        S427=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S427=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S422=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S422=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                              S531=1;
                                                              S449=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S449=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S444=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S444=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    S110=3;
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
                                                        S643=1;
                                                        S642=0;
                                                        S538=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S538=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S533=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S533=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                              S642=1;
                                                              S560=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S560=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S555=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S555=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    S110=3;
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
                                                S1424=1;
                                                S1423=0;
                                                S1319=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1319=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1314=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1314=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                      S1423=1;
                                                      S1341=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S1341=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1336=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S1336=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                            S110=3;
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
                          S118=1;
                          S118=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                            S118=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S113=0;
                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                              reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                              S113=1;
                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 32, column: 13
                                S111=1;
                                S162=0;
                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                    S157=1;
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 38, column: 13
                                      S111=2;
                                      S250=0;
                                      if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                        S250=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S245=0;
                                        if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                          S245=1;
                                          if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 42, column: 13
                                            S111=3;
                                            if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                              S1424=0;
                                              System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                              S420=0;
                                              S382=0;
                                              if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                S382=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S377=0;
                                                if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S377=1;
                                                  if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S420=1;
                                                    if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                      S643=0;
                                                      S531=0;
                                                      S427=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                        S427=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S422=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S422=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S531=1;
                                                            S449=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                              S449=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S444=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S444=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S110=3;
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
                                                      S643=1;
                                                      S642=0;
                                                      S538=0;
                                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                        S538=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S533=0;
                                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S533=1;
                                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S642=1;
                                                            S560=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                              S560=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S555=0;
                                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S555=1;
                                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S110=3;
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
                                              S1424=1;
                                              S1423=0;
                                              S1319=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                S1319=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1314=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1314=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1423=1;
                                                    S1341=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                      S1341=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1336=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S1336=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S110=3;
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
                    
                    case 1 : 
                      switch(S162){
                        case 0 : 
                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                            S162=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S157){
                              case 0 : 
                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                  motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                  S157=1;
                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 38, column: 13
                                    S111=2;
                                    S250=0;
                                    if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                      S250=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S245=0;
                                      if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                        photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                        S245=1;
                                        if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 42, column: 13
                                          S111=3;
                                          if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                            S1424=0;
                                            System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                            S420=0;
                                            S382=0;
                                            if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                              S382=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S377=0;
                                              if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                S377=1;
                                                if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S420=1;
                                                  if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                    S643=0;
                                                    S531=0;
                                                    S427=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                      S427=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S422=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                        S422=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S531=1;
                                                          S449=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                            S449=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S444=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                              S444=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S110=3;
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
                                                    S643=1;
                                                    S642=0;
                                                    S538=0;
                                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                      S538=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S533=0;
                                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                        S533=1;
                                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S642=1;
                                                          S560=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                            S560=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S555=0;
                                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                              S555=1;
                                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S110=3;
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
                                            S1424=1;
                                            S1423=0;
                                            S1319=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                              S1319=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1314=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                S1314=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1423=1;
                                                  S1341=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                    S1341=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1336=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                      S1336=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S110=3;
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
                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 38, column: 13
                                  S111=2;
                                  S250=0;
                                  if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                    S250=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S245=0;
                                    if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                      photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                      S245=1;
                                      if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                        photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 42, column: 13
                                        S111=3;
                                        if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                          S1424=0;
                                          System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                          S420=0;
                                          S382=0;
                                          if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                            S382=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S377=0;
                                            if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                              bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                              S377=1;
                                              if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                S420=1;
                                                if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                  S643=0;
                                                  S531=0;
                                                  S427=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                    S427=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S422=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                      S422=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                        S531=1;
                                                        S449=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                          S449=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S444=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                            S444=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                              S110=3;
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
                                                  S643=1;
                                                  S642=0;
                                                  S538=0;
                                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                    S538=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S533=0;
                                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                      S533=1;
                                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                        S642=1;
                                                        S560=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                          S560=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S555=0;
                                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                            S555=1;
                                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                              S110=3;
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
                                          S1424=1;
                                          S1423=0;
                                          S1319=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                            S1319=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1314=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                              S1314=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                S1423=1;
                                                S1341=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                  S1341=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1336=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                    S1336=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                      S110=3;
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
                          S162=1;
                          S162=0;
                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                            S162=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S157=0;
                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                              motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                              S157=1;
                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 38, column: 13
                                S111=2;
                                S250=0;
                                if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                  S250=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S245=0;
                                  if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                    photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                    S245=1;
                                    if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                      photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 42, column: 13
                                      S111=3;
                                      if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                        S1424=0;
                                        System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                        S420=0;
                                        S382=0;
                                        if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                          S382=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S377=0;
                                          if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                            bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                            S377=1;
                                            if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                              bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 53, column: 17
                                              S420=1;
                                              if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                S643=0;
                                                S531=0;
                                                S427=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S427=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S422=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                    S422=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                      S531=1;
                                                      S449=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S449=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S444=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                          S444=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                            S110=3;
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
                                                S643=1;
                                                S642=0;
                                                S538=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S538=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S533=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                    S533=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                      S642=1;
                                                      S560=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S560=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S555=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                          S555=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                            S110=3;
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
                                        S1424=1;
                                        S1423=0;
                                        S1319=0;
                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                          S1319=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1314=0;
                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                            S1314=1;
                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 81, column: 17
                                              S1423=1;
                                              S1341=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                S1341=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1336=0;
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                  S1336=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                    S110=3;
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
                    
                    case 2 : 
                      switch(S250){
                        case 0 : 
                          if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                            S250=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S245){
                              case 0 : 
                                if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                  photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                  S245=1;
                                  if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                    photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                    ends[1]=2;
                                    ;//sysj/ConveyorController.sysj line: 42, column: 13
                                    S111=3;
                                    if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                      S1424=0;
                                      System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                      S420=0;
                                      S382=0;
                                      if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                        S382=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S377=0;
                                        if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                          bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                          S377=1;
                                          if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 53, column: 17
                                            S420=1;
                                            if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                              S643=0;
                                              S531=0;
                                              S427=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                S427=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S422=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S422=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                    S531=1;
                                                    S449=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S449=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S444=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S444=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                          S110=3;
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
                                              S643=1;
                                              S642=0;
                                              S538=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                S538=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S533=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S533=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                    S642=1;
                                                    S560=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S560=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S555=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S555=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                          S110=3;
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
                                      S1424=1;
                                      S1423=0;
                                      S1319=0;
                                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                        S1319=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S1314=0;
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                          S1314=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 81, column: 17
                                            S1423=1;
                                            S1341=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                              S1341=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1336=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                S1336=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                  S110=3;
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
                                if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                  photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 42, column: 13
                                  S111=3;
                                  if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                    S1424=0;
                                    System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                    S420=0;
                                    S382=0;
                                    if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                      S382=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S377=0;
                                      if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                        bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                        S377=1;
                                        if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 53, column: 17
                                          S420=1;
                                          if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                            S643=0;
                                            S531=0;
                                            S427=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                              S427=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S422=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                S422=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S531=1;
                                                  S449=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S449=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S444=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S444=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S110=3;
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
                                            S643=1;
                                            S642=0;
                                            S538=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                              S538=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S533=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                S533=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S642=1;
                                                  S560=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S560=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S555=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S555=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S110=3;
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
                                    S1424=1;
                                    S1423=0;
                                    S1319=0;
                                    if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                      S1319=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S1314=0;
                                      if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                        motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                        S1314=1;
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 81, column: 17
                                          S1423=1;
                                          S1341=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                            S1341=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1336=0;
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                              S1336=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                S110=3;
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
                          S250=1;
                          S250=0;
                          if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                            photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                            S250=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S245=0;
                            if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                              photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                              S245=1;
                              if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 42, column: 13
                                S111=3;
                                if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                  S1424=0;
                                  System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                  S420=0;
                                  S382=0;
                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                    S382=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S377=0;
                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                      S377=1;
                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 53, column: 17
                                        S420=1;
                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                          S643=0;
                                          S531=0;
                                          S427=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                            S427=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S422=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                              S422=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                S531=1;
                                                S449=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                  S449=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S444=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S444=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S110=3;
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
                                          S643=1;
                                          S642=0;
                                          S538=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                            S538=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S533=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                              S533=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                S642=1;
                                                S560=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                  S560=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S555=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S555=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S110=3;
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
                                  S1424=1;
                                  S1423=0;
                                  S1319=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                    S1319=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1314=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                      S1314=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 81, column: 17
                                        S1423=1;
                                        S1341=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                          S1341=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1336=0;
                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                            S1336=1;
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 83, column: 17
                                              S110=3;
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
                    
                    case 3 : 
                      switch(S1424){
                        case 0 : 
                          switch(S420){
                            case 0 : 
                              switch(S382){
                                case 0 : 
                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                    S382=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S377){
                                      case 0 : 
                                        if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                          bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                          S377=1;
                                          if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                            bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 53, column: 17
                                            S420=1;
                                            if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                              S643=0;
                                              S531=0;
                                              S427=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                S427=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S422=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S422=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                    S531=1;
                                                    S449=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S449=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S444=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S444=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                          S110=3;
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
                                              S643=1;
                                              S642=0;
                                              S538=0;
                                              if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                S538=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S533=0;
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S533=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                    S642=1;
                                                    S560=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S560=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S555=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S555=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                          S110=3;
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
                                        if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                          bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 53, column: 17
                                          S420=1;
                                          if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                            S643=0;
                                            S531=0;
                                            S427=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                              S427=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S422=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                S422=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S531=1;
                                                  S449=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S449=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S444=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S444=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S110=3;
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
                                            S643=1;
                                            S642=0;
                                            S538=0;
                                            if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                              S538=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S533=0;
                                              if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                S533=1;
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S642=1;
                                                  S560=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S560=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S555=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S555=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S110=3;
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
                                  S382=1;
                                  S382=0;
                                  if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                    bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                    S382=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S377=0;
                                    if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                      bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                      S377=1;
                                      if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                        bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 53, column: 17
                                        S420=1;
                                        if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                          S643=0;
                                          S531=0;
                                          S427=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                            S427=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S422=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                              S422=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                S531=1;
                                                S449=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                  S449=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S444=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S444=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S110=3;
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
                                          S643=1;
                                          S642=0;
                                          S538=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                            S538=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S533=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                              S533=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                S642=1;
                                                S560=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                  S560=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S555=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S555=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S110=3;
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
                              switch(S643){
                                case 0 : 
                                  switch(S531){
                                    case 0 : 
                                      switch(S427){
                                        case 0 : 
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                            S427=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S422){
                                              case 0 : 
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S422=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                    S531=1;
                                                    S449=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S449=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S444=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S444=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                          S110=3;
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
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                  S531=1;
                                                  S449=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S449=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S444=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S444=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                        S110=3;
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
                                          S427=1;
                                          S427=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                            S427=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S422=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                              S422=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                S531=1;
                                                S449=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                  S449=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S444=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S444=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                      S110=3;
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
                                      switch(S449){
                                        case 0 : 
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                            S449=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S444){
                                              case 0 : 
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                  S444=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                    S110=3;
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
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                  S110=3;
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
                                          S449=1;
                                          S449=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                            S449=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S444=0;
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                              S444=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                S110=3;
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
                                  switch(S642){
                                    case 0 : 
                                      switch(S538){
                                        case 0 : 
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                            S538=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S533){
                                              case 0 : 
                                                if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S533=1;
                                                  if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                    S642=1;
                                                    S560=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S560=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S555=0;
                                                      if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S555=1;
                                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                          S110=3;
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
                                                if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                  S642=1;
                                                  S560=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S560=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S555=0;
                                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S555=1;
                                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                        S110=3;
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
                                          S538=1;
                                          S538=0;
                                          if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                            S538=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S533=0;
                                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                              S533=1;
                                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                S642=1;
                                                S560=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                  S560=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S555=0;
                                                  if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S555=1;
                                                    if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                      reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                      S110=3;
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
                                      switch(S560){
                                        case 0 : 
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                            S560=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S555){
                                              case 0 : 
                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                  S555=1;
                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                    S110=3;
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
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                  S110=3;
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
                                          S560=1;
                                          S560=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                            S560=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S555=0;
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                              S555=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                S110=3;
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
                          switch(S1423){
                            case 0 : 
                              switch(S1319){
                                case 0 : 
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                    S1319=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S1314){
                                      case 0 : 
                                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                          S1314=1;
                                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 81, column: 17
                                            S1423=1;
                                            S1341=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                              S1341=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1336=0;
                                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                S1336=1;
                                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                  S110=3;
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
                                        if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 81, column: 17
                                          S1423=1;
                                          S1341=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                            S1341=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1336=0;
                                            if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                              S1336=1;
                                              if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                ends[1]=2;
                                                ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                S110=3;
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
                                  S1319=1;
                                  S1319=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                    S1319=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1314=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                      motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                      S1314=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 81, column: 17
                                        S1423=1;
                                        S1341=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                          S1341=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1336=0;
                                          if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                            S1336=1;
                                            if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 83, column: 17
                                              S110=3;
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
                              switch(S1341){
                                case 0 : 
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                    S1341=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S1336){
                                      case 0 : 
                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                          S1336=1;
                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                            ends[1]=2;
                                            ;//sysj/ConveyorController.sysj line: 83, column: 17
                                            S110=3;
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
                                        if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                          reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                          ends[1]=2;
                                          ;//sysj/ConveyorController.sysj line: 83, column: 17
                                          S110=3;
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
                                  S1341=1;
                                  S1341=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                    S1341=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1336=0;
                                    if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                      S1336=1;
                                      if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 83, column: 17
                                        S110=3;
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
                  switch(S8774){
                    case 0 : 
                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                        S8774=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S8769){
                          case 0 : 
                            if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                              motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                              S8769=1;
                              if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                ends[1]=2;
                                ;//sysj/ConveyorController.sysj line: 105, column: 13
                                S110=3;
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
                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                              ends[1]=2;
                              ;//sysj/ConveyorController.sysj line: 105, column: 13
                              S110=3;
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
                      S8774=1;
                      S8774=0;
                      if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                        S8774=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S8769=0;
                        if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                          motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                          S8769=1;
                          if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                            motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                            ends[1]=2;
                            ;//sysj/ConveyorController.sysj line: 105, column: 13
                            S110=3;
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
            
            case 3 : 
              S110=3;
              S110=0;
              S6=0;
              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 15, column: 9
                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                  reportOut_o.setVal("READY");//sysj/ConveyorController.sysj line: 15, column: 9
                  S1=1;
                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 15, column: 9
                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 15, column: 9
                    ends[1]=2;
                    ;//sysj/ConveyorController.sysj line: 15, column: 9
                    S110=1;
                    S28=0;
                    if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 18, column: 9
                      commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                      S28=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S23=0;
                      if(!commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                        commandIn_in.setACK(true);//sysj/ConveyorController.sysj line: 18, column: 9
                        S23=1;
                        if(commandIn_in.isREQ()){//sysj/ConveyorController.sysj line: 18, column: 9
                          commandIn_in.setACK(false);//sysj/ConveyorController.sysj line: 18, column: 9
                          ends[1]=2;
                          ;//sysj/ConveyorController.sysj line: 18, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/ConveyorController.sysj line: 20, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/ConveyorController.sysj line: 23, column: 9
                            S8815=0;
                            workpieceId_thread_1 = command_thread_1.substring(6);//sysj/ConveyorController.sysj line: 25, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 27, column: 13
                            S111=0;
                            S118=0;
                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 32, column: 13
                              reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 32, column: 13
                                S113=1;
                                if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 32, column: 13
                                  reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 32, column: 13
                                  ends[1]=2;
                                  ;//sysj/ConveyorController.sysj line: 32, column: 13
                                  S111=1;
                                  S162=0;
                                  if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 38, column: 13
                                    motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                      motorRun_o.setVal(true);//sysj/ConveyorController.sysj line: 38, column: 13
                                      S157=1;
                                      if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 38, column: 13
                                        motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 38, column: 13
                                        ends[1]=2;
                                        ;//sysj/ConveyorController.sysj line: 38, column: 13
                                        S111=2;
                                        S250=0;
                                        if(!photoEye_in.isPartnerPresent() || photoEye_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 42, column: 13
                                          photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                          S250=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S245=0;
                                          if(!photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                            photoEye_in.setACK(true);//sysj/ConveyorController.sysj line: 42, column: 13
                                            S245=1;
                                            if(photoEye_in.isREQ()){//sysj/ConveyorController.sysj line: 42, column: 13
                                              photoEye_in.setACK(false);//sysj/ConveyorController.sysj line: 42, column: 13
                                              ends[1]=2;
                                              ;//sysj/ConveyorController.sysj line: 42, column: 13
                                              S111=3;
                                              if((Boolean)(photoEye_in.getVal() == null ? null : ((Boolean)photoEye_in.getVal()))){//sysj/ConveyorController.sysj line: 45, column: 16
                                                S1424=0;
                                                System.out.println("Bottle detected by photo eye");//sysj/ConveyorController.sysj line: 47, column: 17
                                                S420=0;
                                                S382=0;
                                                if(!bottleLeft_in.isPartnerPresent() || bottleLeft_in.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                  bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                  S382=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S377=0;
                                                  if(!bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                    bottleLeft_in.setACK(true);//sysj/ConveyorController.sysj line: 53, column: 17
                                                    S377=1;
                                                    if(bottleLeft_in.isREQ()){//sysj/ConveyorController.sysj line: 53, column: 17
                                                      bottleLeft_in.setACK(false);//sysj/ConveyorController.sysj line: 53, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 53, column: 17
                                                      S420=1;
                                                      if((Boolean)(bottleLeft_in.getVal() == null ? null : ((Boolean)bottleLeft_in.getVal()))){//sysj/ConveyorController.sysj line: 56, column: 20
                                                        S643=0;
                                                        S531=0;
                                                        S427=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                          S427=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S422=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                            S422=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 59, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 59, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 59, column: 21
                                                              S531=1;
                                                              S449=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                S449=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S444=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                  S444=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 61, column: 21
                                                                    S110=3;
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
                                                        S643=1;
                                                        S642=0;
                                                        S538=0;
                                                        if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                          motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                          S538=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S533=0;
                                                          if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                            motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                            S533=1;
                                                            if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 69, column: 21
                                                              motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 69, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/ConveyorController.sysj line: 69, column: 21
                                                              S642=1;
                                                              S560=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                S560=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S555=0;
                                                                if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_LEFT");//sysj/ConveyorController.sysj line: 71, column: 21
                                                                  S555=1;
                                                                  if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/ConveyorController.sysj line: 71, column: 21
                                                                    S110=3;
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
                                                S1424=1;
                                                S1423=0;
                                                S1319=0;
                                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                  S1319=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1314=0;
                                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                    S1314=1;
                                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 81, column: 17
                                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 81, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/ConveyorController.sysj line: 81, column: 17
                                                      S1423=1;
                                                      S1341=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                        reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                        S1341=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1336=0;
                                                        if(reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|PHOTO_EYE_NOT_TRIGGERED");//sysj/ConveyorController.sysj line: 83, column: 17
                                                          S1336=1;
                                                          if(!reportOut_o.isACK()){//sysj/ConveyorController.sysj line: 83, column: 17
                                                            reportOut_o.setREQ(false);//sysj/ConveyorController.sysj line: 83, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/ConveyorController.sysj line: 83, column: 17
                                                            S110=3;
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
                            S8815=1;
                            if(command_thread_1.equals("RESET")){//sysj/ConveyorController.sysj line: 91, column: 14
                              System.out.println("RESET received");//sysj/ConveyorController.sysj line: 93, column: 13
                              S110=3;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              if(command_thread_1.equals("STOP")){//sysj/ConveyorController.sysj line: 99, column: 14
                                System.out.println("STOP received");//sysj/ConveyorController.sysj line: 101, column: 13
                                S8774=0;
                                if(!motorRun_o.isPartnerPresent() || motorRun_o.isPartnerPreempted()){//sysj/ConveyorController.sysj line: 105, column: 13
                                  motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                  S8774=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S8769=0;
                                  if(motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                    motorRun_o.setVal(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                    S8769=1;
                                    if(!motorRun_o.isACK()){//sysj/ConveyorController.sysj line: 105, column: 13
                                      motorRun_o.setREQ(false);//sysj/ConveyorController.sysj line: 105, column: 13
                                      ends[1]=2;
                                      ;//sysj/ConveyorController.sysj line: 105, column: 13
                                      S110=3;
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
                                System.out.println("Unknown command: " + command_thread_1);//sysj/ConveyorController.sysj line: 111, column: 13
                                S110=3;
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
      photoEye_in.sethook();
      bottleLeft_in.sethook();
      reportOut_o.sethook();
      motorRun_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        commandIn_in.gethook();
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
