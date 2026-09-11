import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class BottleLoaderController extends ClockDomain{
  public BottleLoaderController(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel commandIn_in = new input_Channel();
  public input_Channel bottleAvailable_in = new input_Channel();
  public input_Channel placed_in = new input_Channel();
  public input_Channel armHome_in = new input_Channel();
  public output_Channel reportOut_o = new output_Channel();
  public output_Channel load_o = new output_Channel();
  public output_Channel retract_o = new output_Channel();
  private String command_thread_1;//sysj/BottleLoaderController.sysj line: 25, column: 9
  private String workpieceId_thread_1;//sysj/BottleLoaderController.sysj line: 31, column: 13
  private int S253018 = 1;
  private int S110 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S111 = 1;
  private int S118 = 1;
  private int S113 = 1;
  private int S3653 = 1;
  private int S266 = 1;
  private int S162 = 1;
  private int S157 = 1;
  private int S184 = 1;
  private int S179 = 1;
  private int S273 = 1;
  private int S268 = 1;
  private int S802 = 1;
  private int S509 = 1;
  private int S405 = 1;
  private int S400 = 1;
  private int S427 = 1;
  private int S422 = 1;
  private int S554 = 1;
  private int S516 = 1;
  private int S511 = 1;
  private int S538 = 1;
  private int S533 = 1;
  private int S786 = 1;
  private int S781 = 1;
  private int S3637 = 1;
  private int S3632 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S253018){
        case 0 : 
          S253018=0;
          break RUN;
        
        case 1 : 
          S253018=2;
          S253018=2;
          S110=0;
          S6=0;
          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 19, column: 9
            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
              reportOut_o.setVal("READY");//sysj/BottleLoaderController.sysj line: 19, column: 9
              S1=1;
              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                ends[1]=2;
                ;//sysj/BottleLoaderController.sysj line: 19, column: 9
                S110=1;
                S28=0;
                if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                  commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                    commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                    S23=1;
                    if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                      commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                      ends[1]=2;
                      ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                      command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                      S110=2;
                      if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                        workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                        System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                        S111=0;
                        S118=0;
                        if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                          bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                          S118=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S113=0;
                          if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                            bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                            S113=1;
                            if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                              S111=1;
                              if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                S3653=0;
                                S266=0;
                                S162=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    S157=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S266=1;
                                      S184=0;
                                      if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        S184=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S179=0;
                                        if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S179=1;
                                          if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S266=2;
                                            S273=0;
                                            if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              S273=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S268=0;
                                              if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S268=1;
                                                if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S266=3;
                                                  if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                    S802=0;
                                                    S509=0;
                                                    S405=0;
                                                    if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      S405=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S400=0;
                                                      if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S400=1;
                                                        if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S509=1;
                                                          S427=0;
                                                          if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            S427=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S422=0;
                                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S422=1;
                                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S509=2;
                                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                  S554=0;
                                                                  S516=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    S516=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S511=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      S511=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                  S554=1;
                                                                  S538=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    S538=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S533=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      S533=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S802=1;
                                                    S786=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      S786=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S781=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        S781=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                S3653=1;
                                S3637=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  S3637=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S3632=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    S3632=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                      }
                      else {
                        if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                          System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                        }
                        else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                          if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                            System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                          }
                          else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                            System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                          }
                        }
                        S110=3;
                        active[1]=1;
                        ends[1]=1;
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
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                          reportOut_o.setVal("READY");//sysj/BottleLoaderController.sysj line: 19, column: 9
                          S1=1;
                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 19, column: 9
                            S110=1;
                            S28=0;
                            if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                              commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                                commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                                S23=1;
                                if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                                  commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                                  command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                                  S110=2;
                                  if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                                    workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                                    System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                                    S111=0;
                                    S118=0;
                                    if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      S118=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S113=0;
                                      if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                        bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                        S113=1;
                                        if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                          bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                          S111=1;
                                          if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                            S3653=0;
                                            S266=0;
                                            S162=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              S162=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S157=0;
                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                S157=1;
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                  S266=1;
                                                  S184=0;
                                                  if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    S184=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S179=0;
                                                    if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                      load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                      S179=1;
                                                      if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                        S266=2;
                                                        S273=0;
                                                        if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          S273=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S268=0;
                                                          if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                            placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                            S268=1;
                                                            if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                              S266=3;
                                                              if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                                S802=0;
                                                                S509=0;
                                                                S405=0;
                                                                if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  S405=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S400=0;
                                                                  if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                    retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                    S400=1;
                                                                    if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                      S509=1;
                                                                      S427=0;
                                                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        S427=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S422=0;
                                                                        if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                          armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                          S422=1;
                                                                          if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                            S509=2;
                                                                            if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                              S554=0;
                                                                              S516=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                S516=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S511=0;
                                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                  S511=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                              S554=1;
                                                                              S538=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                S538=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S533=0;
                                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                  S533=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                S802=1;
                                                                S786=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                  S786=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S781=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                    S781=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            S3653=1;
                                            S3637=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                              S3637=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S3632=0;
                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                                S3632=1;
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                                      System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                                    }
                                    else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                                      if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                                        System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                                      }
                                      else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                                        System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                                      }
                                    }
                                    S110=3;
                                    active[1]=1;
                                    ends[1]=1;
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
                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderController.sysj line: 19, column: 9
                          S110=1;
                          S28=0;
                          if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                            commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                              commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                                commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                                S110=2;
                                if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                                  workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                                  System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                                  S111=0;
                                  S118=0;
                                  if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    S118=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S113=0;
                                    if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      S113=1;
                                      if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                        S111=1;
                                        if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                          S3653=0;
                                          S266=0;
                                          S162=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            S162=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S157=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              S157=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                                S266=1;
                                                S184=0;
                                                if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  S184=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S179=0;
                                                  if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    S179=1;
                                                    if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                      S266=2;
                                                      S273=0;
                                                      if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        S273=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S268=0;
                                                        if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          S268=1;
                                                          if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                            S266=3;
                                                            if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                              S802=0;
                                                              S509=0;
                                                              S405=0;
                                                              if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                S405=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S400=0;
                                                                if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  S400=1;
                                                                  if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                    S509=1;
                                                                    S427=0;
                                                                    if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      S427=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S422=0;
                                                                      if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        S422=1;
                                                                        if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                          S509=2;
                                                                          if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                            S554=0;
                                                                            S516=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                              S516=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S511=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                S511=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                            S554=1;
                                                                            S538=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                              S538=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S533=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                S533=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              S802=1;
                                                              S786=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                S786=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S781=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                  S781=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S3653=1;
                                          S3637=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                            S3637=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S3632=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                              S3632=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                                    System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                                  }
                                  else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                                    if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                                      System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                                    }
                                    else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                                      System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                                    }
                                  }
                                  S110=3;
                                  active[1]=1;
                                  ends[1]=1;
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
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                      reportOut_o.setVal("READY");//sysj/BottleLoaderController.sysj line: 19, column: 9
                      S1=1;
                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderController.sysj line: 19, column: 9
                        S110=1;
                        S28=0;
                        if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                          commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                            commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                            S23=1;
                            if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                              commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                              S110=2;
                              if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                                workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                                S111=0;
                                S118=0;
                                if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  S118=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S113=0;
                                  if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    S113=1;
                                    if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                      S111=1;
                                      if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                        S3653=0;
                                        S266=0;
                                        S162=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          S162=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S157=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            S157=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                              S266=1;
                                              S184=0;
                                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                S184=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S179=0;
                                                if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  S179=1;
                                                  if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                    S266=2;
                                                    S273=0;
                                                    if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      S273=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S268=0;
                                                      if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        S268=1;
                                                        if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                          S266=3;
                                                          if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                            S802=0;
                                                            S509=0;
                                                            S405=0;
                                                            if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              S405=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S400=0;
                                                              if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                S400=1;
                                                                if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                  S509=1;
                                                                  S427=0;
                                                                  if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    S427=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S422=0;
                                                                    if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      S422=1;
                                                                      if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                        S509=2;
                                                                        if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                          S554=0;
                                                                          S516=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            S516=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S511=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                              S511=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                          S554=1;
                                                                          S538=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            S538=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S533=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                              S533=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            S802=1;
                                                            S786=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              S786=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S781=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                S781=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        S3653=1;
                                        S3637=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          S3637=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S3632=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                            S3632=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                                  System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                                }
                                else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                                  if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                                    System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                                  }
                                  else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                                    System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                                  }
                                }
                                S110=3;
                                active[1]=1;
                                ends[1]=1;
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
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                    commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S23){
                      case 0 : 
                        if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                          commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                          S23=1;
                          if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                            commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                            command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                            S110=2;
                            if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                              workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                              S111=0;
                              S118=0;
                              if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                S118=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S113=0;
                                if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  S113=1;
                                  if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                    S111=1;
                                    if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                      S3653=0;
                                      S266=0;
                                      S162=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        S162=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S157=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          S157=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                            S266=1;
                                            S184=0;
                                            if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              S184=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S179=0;
                                              if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                S179=1;
                                                if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                  S266=2;
                                                  S273=0;
                                                  if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    S273=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S268=0;
                                                    if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      S268=1;
                                                      if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                        S266=3;
                                                        if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                          S802=0;
                                                          S509=0;
                                                          S405=0;
                                                          if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            S405=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S400=0;
                                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              S400=1;
                                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                                S509=1;
                                                                S427=0;
                                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  S427=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S422=0;
                                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    S422=1;
                                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                      S509=2;
                                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                        S554=0;
                                                                        S516=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          S516=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S511=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            S511=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                        S554=1;
                                                                        S538=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          S538=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S533=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            S533=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          S802=1;
                                                          S786=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            S786=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S781=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              S781=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      S3653=1;
                                      S3637=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        S3637=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S3632=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          S3632=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                                System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                              }
                              else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                                if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                                  System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                                }
                                else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                                  System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                                }
                              }
                              S110=3;
                              active[1]=1;
                              ends[1]=1;
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
                        if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                          commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                            workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                            S111=0;
                            S118=0;
                            if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                S113=1;
                                if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  S111=1;
                                  if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                    S3653=0;
                                    S266=0;
                                    S162=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        S157=1;
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          S266=1;
                                          S184=0;
                                          if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S184=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S179=0;
                                            if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              S179=1;
                                              if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                S266=2;
                                                S273=0;
                                                if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S273=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S268=0;
                                                  if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    S268=1;
                                                    if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      S266=3;
                                                      if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                        S802=0;
                                                        S509=0;
                                                        S405=0;
                                                        if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S405=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S400=0;
                                                          if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            S400=1;
                                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              S509=1;
                                                              S427=0;
                                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S427=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S422=0;
                                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  S422=1;
                                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    S509=2;
                                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                      S554=0;
                                                                      S516=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        S516=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S511=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          S511=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                      S554=1;
                                                                      S538=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        S538=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S533=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          S533=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        S802=1;
                                                        S786=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          S786=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S781=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            S781=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S3653=1;
                                    S3637=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      S3637=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S3632=0;
                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        S3632=1;
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                              System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                            }
                            else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                              if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                                System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                              }
                              else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                                System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                              }
                            }
                            S110=3;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
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
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                    commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                      commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                      S23=1;
                      if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                        commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                        command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                        S110=2;
                        if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                          workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                          System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                          S111=0;
                          S118=0;
                          if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                            bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                            S118=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S113=0;
                            if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                              bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                              S113=1;
                              if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                S111=1;
                                if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                  S3653=0;
                                  S266=0;
                                  S162=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S157=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        S266=1;
                                        S184=0;
                                        if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S184=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S179=0;
                                          if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S179=1;
                                            if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              S266=2;
                                              S273=0;
                                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S273=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S268=0;
                                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S268=1;
                                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    S266=3;
                                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                      S802=0;
                                                      S509=0;
                                                      S405=0;
                                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S405=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S400=0;
                                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S400=1;
                                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            S509=1;
                                                            S427=0;
                                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S427=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S422=0;
                                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S422=1;
                                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  S509=2;
                                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                    S554=0;
                                                                    S516=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      S516=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S511=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        S511=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                    S554=1;
                                                                    S538=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      S538=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S533=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        S533=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S802=1;
                                                      S786=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        S786=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S781=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          S781=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S3653=1;
                                  S3637=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    S3637=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S3632=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      S3632=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                            System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                          }
                          else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                            if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                              System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                            }
                            else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                              System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                            }
                          }
                          S110=3;
                          active[1]=1;
                          ends[1]=1;
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
              switch(S111){
                case 0 : 
                  switch(S118){
                    case 0 : 
                      if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                        S118=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S113){
                          case 0 : 
                            if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                              bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                              S113=1;
                              if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                S111=1;
                                if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                  S3653=0;
                                  S266=0;
                                  S162=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S157=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        S266=1;
                                        S184=0;
                                        if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S184=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S179=0;
                                          if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S179=1;
                                            if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              S266=2;
                                              S273=0;
                                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S273=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S268=0;
                                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S268=1;
                                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    S266=3;
                                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                      S802=0;
                                                      S509=0;
                                                      S405=0;
                                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S405=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S400=0;
                                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S400=1;
                                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            S509=1;
                                                            S427=0;
                                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S427=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S422=0;
                                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S422=1;
                                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  S509=2;
                                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                    S554=0;
                                                                    S516=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      S516=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S511=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        S511=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                    S554=1;
                                                                    S538=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      S538=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S533=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        S533=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S802=1;
                                                      S786=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        S786=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S781=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          S781=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S3653=1;
                                  S3637=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    S3637=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S3632=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      S3632=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                              }
                              else {
                                active[1]=1;
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
                            if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                              S111=1;
                              if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                S3653=0;
                                S266=0;
                                S162=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    S157=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S266=1;
                                      S184=0;
                                      if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        S184=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S179=0;
                                        if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S179=1;
                                          if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S266=2;
                                            S273=0;
                                            if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              S273=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S268=0;
                                              if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S268=1;
                                                if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S266=3;
                                                  if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                    S802=0;
                                                    S509=0;
                                                    S405=0;
                                                    if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      S405=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S400=0;
                                                      if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S400=1;
                                                        if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S509=1;
                                                          S427=0;
                                                          if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            S427=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S422=0;
                                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S422=1;
                                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S509=2;
                                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                  S554=0;
                                                                  S516=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    S516=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S511=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      S511=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                  S554=1;
                                                                  S538=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    S538=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S533=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      S533=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S802=1;
                                                    S786=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      S786=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S781=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        S781=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                S3653=1;
                                S3637=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  S3637=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S3632=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    S3632=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                      if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                        S118=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S113=0;
                        if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                          bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                          S113=1;
                          if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                            bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                            S111=1;
                            if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                              S3653=0;
                              S266=0;
                              S162=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  S157=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    S266=1;
                                    S184=0;
                                    if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      S184=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S179=0;
                                      if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        S179=1;
                                        if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S266=2;
                                          S273=0;
                                          if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            S273=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S268=0;
                                            if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              S268=1;
                                              if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S266=3;
                                                if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                  S802=0;
                                                  S509=0;
                                                  S405=0;
                                                  if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    S405=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S400=0;
                                                    if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      S400=1;
                                                      if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S509=1;
                                                        S427=0;
                                                        if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          S427=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S422=0;
                                                          if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            S422=1;
                                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S509=2;
                                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                S554=0;
                                                                S516=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  S516=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S511=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    S511=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                S554=1;
                                                                S538=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  S538=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S533=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    S533=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  S802=1;
                                                  S786=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    S786=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S781=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      S781=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              S3653=1;
                              S3637=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                S3637=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S3632=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  S3632=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
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
                  switch(S3653){
                    case 0 : 
                      switch(S266){
                        case 0 : 
                          switch(S162){
                            case 0 : 
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S157){
                                  case 0 : 
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S157=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        S266=1;
                                        S184=0;
                                        if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S184=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S179=0;
                                          if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S179=1;
                                            if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              S266=2;
                                              S273=0;
                                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S273=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S268=0;
                                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S268=1;
                                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    S266=3;
                                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                      S802=0;
                                                      S509=0;
                                                      S405=0;
                                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S405=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S400=0;
                                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S400=1;
                                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            S509=1;
                                                            S427=0;
                                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S427=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S422=0;
                                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S422=1;
                                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  S509=2;
                                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                    S554=0;
                                                                    S516=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      S516=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S511=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        S511=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                    S554=1;
                                                                    S538=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      S538=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S533=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        S533=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S802=1;
                                                      S786=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        S786=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S781=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          S781=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S266=1;
                                      S184=0;
                                      if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        S184=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S179=0;
                                        if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S179=1;
                                          if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S266=2;
                                            S273=0;
                                            if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              S273=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S268=0;
                                              if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S268=1;
                                                if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S266=3;
                                                  if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                    S802=0;
                                                    S509=0;
                                                    S405=0;
                                                    if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      S405=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S400=0;
                                                      if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S400=1;
                                                        if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S509=1;
                                                          S427=0;
                                                          if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            S427=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S422=0;
                                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S422=1;
                                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S509=2;
                                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                  S554=0;
                                                                  S516=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    S516=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S511=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      S511=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                  S554=1;
                                                                  S538=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    S538=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S533=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      S533=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S802=1;
                                                    S786=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      S786=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S781=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        S781=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                  S157=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                    S266=1;
                                    S184=0;
                                    if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      S184=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S179=0;
                                      if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        S179=1;
                                        if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                          S266=2;
                                          S273=0;
                                          if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            S273=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S268=0;
                                            if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              S268=1;
                                              if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                S266=3;
                                                if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                  S802=0;
                                                  S509=0;
                                                  S405=0;
                                                  if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    S405=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S400=0;
                                                    if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      S400=1;
                                                      if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                        S509=1;
                                                        S427=0;
                                                        if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          S427=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S422=0;
                                                          if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            S422=1;
                                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                              S509=2;
                                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                S554=0;
                                                                S516=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  S516=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S511=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    S511=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                S554=1;
                                                                S538=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  S538=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S533=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    S533=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  S802=1;
                                                  S786=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    S786=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S781=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      S781=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                          switch(S184){
                            case 0 : 
                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                S184=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S179){
                                  case 0 : 
                                    if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      S179=1;
                                      if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                        S266=2;
                                        S273=0;
                                        if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          S273=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S268=0;
                                          if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            S268=1;
                                            if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                              S266=3;
                                              if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                S802=0;
                                                S509=0;
                                                S405=0;
                                                if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  S405=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S400=0;
                                                  if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    S400=1;
                                                    if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                      S509=1;
                                                      S427=0;
                                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        S427=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S422=0;
                                                        if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          S422=1;
                                                          if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                            S509=2;
                                                            if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                              S554=0;
                                                              S516=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                S516=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S511=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  S511=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                              S554=1;
                                                              S538=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                S538=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S533=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  S533=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                S802=1;
                                                S786=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                  S786=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S781=0;
                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    S781=1;
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                      S266=2;
                                      S273=0;
                                      if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        S273=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S268=0;
                                        if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          S268=1;
                                          if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                            S266=3;
                                            if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                              S802=0;
                                              S509=0;
                                              S405=0;
                                              if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                S405=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S400=0;
                                                if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  S400=1;
                                                  if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                    S509=1;
                                                    S427=0;
                                                    if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      S427=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S422=0;
                                                      if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        S422=1;
                                                        if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                          S509=2;
                                                          if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                            S554=0;
                                                            S516=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              S516=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S511=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                S511=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                            S554=1;
                                                            S538=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              S538=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S533=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                S533=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              S802=1;
                                              S786=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                S786=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S781=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                  S781=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                              S184=1;
                              S184=0;
                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                S184=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S179=0;
                                if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                  load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                  S179=1;
                                  if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                    load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                    S266=2;
                                    S273=0;
                                    if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      S273=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S268=0;
                                      if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        S268=1;
                                        if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                          S266=3;
                                          if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                            S802=0;
                                            S509=0;
                                            S405=0;
                                            if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              S405=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S400=0;
                                              if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                S400=1;
                                                if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                  S509=1;
                                                  S427=0;
                                                  if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    S427=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S422=0;
                                                    if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      S422=1;
                                                      if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                        S509=2;
                                                        if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                          S554=0;
                                                          S516=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            S516=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S511=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              S511=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                          S554=1;
                                                          S538=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            S538=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S533=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              S533=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            S802=1;
                                            S786=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                              S786=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S781=0;
                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                S781=1;
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                          switch(S273){
                            case 0 : 
                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                S273=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S268){
                                  case 0 : 
                                    if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      S268=1;
                                      if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                        S266=3;
                                        if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                          S802=0;
                                          S509=0;
                                          S405=0;
                                          if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            S405=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S400=0;
                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              S400=1;
                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                S509=1;
                                                S427=0;
                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  S427=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S422=0;
                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    S422=1;
                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      S509=2;
                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                        S554=0;
                                                        S516=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          S516=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S511=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            S511=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                        S554=1;
                                                        S538=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          S538=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S533=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            S533=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S802=1;
                                          S786=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            S786=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S781=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                              S781=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                      S266=3;
                                      if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                        S802=0;
                                        S509=0;
                                        S405=0;
                                        if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                          S405=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S400=0;
                                          if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            S400=1;
                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              S509=1;
                                              S427=0;
                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                S427=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S422=0;
                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  S422=1;
                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    S509=2;
                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                      S554=0;
                                                      S516=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        S516=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S511=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          S511=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                      S554=1;
                                                      S538=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        S538=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S533=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          S533=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        S802=1;
                                        S786=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          S786=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S781=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            S781=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                              S273=1;
                              S273=0;
                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                S273=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S268=0;
                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                  S268=1;
                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                    S266=3;
                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                      S802=0;
                                      S509=0;
                                      S405=0;
                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                        S405=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S400=0;
                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                          S400=1;
                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            S509=1;
                                            S427=0;
                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              S427=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S422=0;
                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                S422=1;
                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  S509=2;
                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                    S554=0;
                                                    S516=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      S516=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S511=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        S511=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                    S554=1;
                                                    S538=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      S538=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S533=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        S533=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      S802=1;
                                      S786=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                        S786=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S781=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          S781=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                          switch(S802){
                            case 0 : 
                              switch(S509){
                                case 0 : 
                                  switch(S405){
                                    case 0 : 
                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                        S405=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S400){
                                          case 0 : 
                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              S400=1;
                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                S509=1;
                                                S427=0;
                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  S427=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S422=0;
                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    S422=1;
                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                      S509=2;
                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                        S554=0;
                                                        S516=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          S516=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S511=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            S511=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                        S554=1;
                                                        S538=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          S538=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S533=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            S533=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
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
                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                              S509=1;
                                              S427=0;
                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                S427=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S422=0;
                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  S422=1;
                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                    S509=2;
                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                      S554=0;
                                                      S516=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        S516=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S511=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          S511=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                      S554=1;
                                                      S538=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        S538=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S533=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          S533=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
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
                                      S405=1;
                                      S405=0;
                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                        S405=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S400=0;
                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                          S400=1;
                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                            S509=1;
                                            S427=0;
                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              S427=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S422=0;
                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                S422=1;
                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                  S509=2;
                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                    S554=0;
                                                    S516=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      S516=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S511=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        S511=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                    S554=1;
                                                    S538=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      S538=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S533=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        S533=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
                                  switch(S427){
                                    case 0 : 
                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                        S427=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S422){
                                          case 0 : 
                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              S422=1;
                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                S509=2;
                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                  S554=0;
                                                  S516=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    S516=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S511=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      S511=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                  S554=1;
                                                  S538=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    S538=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S533=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      S533=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                              }
                                              else {
                                                active[1]=1;
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
                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                              S509=2;
                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                S554=0;
                                                S516=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  S516=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S511=0;
                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    S511=1;
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                S554=1;
                                                S538=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  S538=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S533=0;
                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    S533=1;
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                        S427=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S422=0;
                                        if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                          armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                          S422=1;
                                          if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                            S509=2;
                                            if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                              S554=0;
                                              S516=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                S516=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S511=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  S511=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                              S554=1;
                                              S538=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                S538=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S533=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  S533=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
                                  switch(S554){
                                    case 0 : 
                                      switch(S516){
                                        case 0 : 
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                            S516=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S511){
                                              case 0 : 
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  S511=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                          S516=1;
                                          S516=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                            S516=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S511=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                              S511=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                    
                                    case 1 : 
                                      switch(S538){
                                        case 0 : 
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                            S538=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S533){
                                              case 0 : 
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  S533=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                          S538=1;
                                          S538=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                            S538=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S533=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                              S533=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                            
                            case 1 : 
                              switch(S786){
                                case 0 : 
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                    S786=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S781){
                                      case 0 : 
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          S781=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                  S786=1;
                                  S786=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                    S786=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S781=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                      S781=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                    
                    case 1 : 
                      switch(S3637){
                        case 0 : 
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                            S3637=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S3632){
                              case 0 : 
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  S3632=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                          S3637=1;
                          S3637=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                            S3637=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S3632=0;
                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                              S3632=1;
                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
            
            case 3 : 
              S110=3;
              S110=0;
              S6=0;
              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                  reportOut_o.setVal("READY");//sysj/BottleLoaderController.sysj line: 19, column: 9
                  S1=1;
                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 19, column: 9
                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 19, column: 9
                    ends[1]=2;
                    ;//sysj/BottleLoaderController.sysj line: 19, column: 9
                    S110=1;
                    S28=0;
                    if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                      commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                      S28=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S23=0;
                      if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                        commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 23, column: 9
                        S23=1;
                        if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 23, column: 9
                          commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 23, column: 9
                          ends[1]=2;
                          ;//sysj/BottleLoaderController.sysj line: 23, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 25, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/BottleLoaderController.sysj line: 29, column: 9
                            workpieceId_thread_1 = command_thread_1.substring(6);//sysj/BottleLoaderController.sysj line: 31, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 34, column: 13
                            S111=0;
                            S118=0;
                            if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                S113=1;
                                if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 40, column: 13
                                  S111=1;
                                  if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 43, column: 16
                                    S3653=0;
                                    S266=0;
                                    S162=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                        S157=1;
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 46, column: 17
                                          S266=1;
                                          S184=0;
                                          if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                            S184=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S179=0;
                                            if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                              S179=1;
                                              if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 51, column: 17
                                                S266=2;
                                                S273=0;
                                                if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                  S273=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S268=0;
                                                  if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                    S268=1;
                                                    if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 53, column: 17
                                                      S266=3;
                                                      if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 56, column: 20
                                                        S802=0;
                                                        S509=0;
                                                        S405=0;
                                                        if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                          S405=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S400=0;
                                                          if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                            S400=1;
                                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 59, column: 21
                                                              S509=1;
                                                              S427=0;
                                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                S427=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S422=0;
                                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                  S422=1;
                                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 61, column: 21
                                                                    S509=2;
                                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 64, column: 24
                                                                      S554=0;
                                                                      S516=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                        S516=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S511=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                          S511=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 66, column: 25
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 66, column: 25
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
                                                                      S554=1;
                                                                      S538=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                        S538=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S533=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                          S533=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 75, column: 25
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 75, column: 25
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
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        S802=1;
                                                        S786=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                          S786=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S781=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                            S781=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 86, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 86, column: 21
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
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S3653=1;
                                    S3637=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                      S3637=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S3632=0;
                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 97, column: 17
                                        S3632=1;
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 97, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 97, column: 17
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
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            if(command_thread_1.equals("RESET")) {//sysj/BottleLoaderController.sysj line: 105, column: 41
                              System.out.println("RESET received");//sysj/BottleLoaderController.sysj line: 107, column: 13
                            }
                            else {//sysj/BottleLoaderController.sysj line: 105, column: 14
                              if(command_thread_1.equals("STOP")) {//sysj/BottleLoaderController.sysj line: 113, column: 40
                                System.out.println("STOP received");//sysj/BottleLoaderController.sysj line: 115, column: 13
                              }
                              else {//sysj/BottleLoaderController.sysj line: 121, column: 13
                                System.out.println("Unknown command: " + command_thread_1);//sysj/BottleLoaderController.sysj line: 123, column: 13
                              }
                            }
                            S110=3;
                            active[1]=1;
                            ends[1]=1;
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
          commandIn_in.gethook();
          bottleAvailable_in.gethook();
          placed_in.gethook();
          armHome_in.gethook();
          reportOut_o.gethook();
          load_o.gethook();
          retract_o.gethook();
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
      bottleAvailable_in.sethook();
      placed_in.sethook();
      armHome_in.sethook();
      reportOut_o.sethook();
      load_o.sethook();
      retract_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        commandIn_in.gethook();
        bottleAvailable_in.gethook();
        placed_in.gethook();
        armHome_in.gethook();
        reportOut_o.gethook();
        load_o.gethook();
        retract_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
