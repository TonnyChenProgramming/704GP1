import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class TwoLiquidFillerController extends ClockDomain{
  public TwoLiquidFillerController(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel commandIn_in = new input_Channel();
  public input_Channel bottlePresent_in = new input_Channel();
  public input_Channel dose1Done_in = new input_Channel();
  public input_Channel dose2Done_in = new input_Channel();
  public input_Channel overflow_in = new input_Channel();
  public output_Channel reportOut_o = new output_Channel();
  public output_Channel dose1_o = new output_Channel();
  public output_Channel dose2_o = new output_Channel();
  private String command_thread_1;//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
  private String data_thread_1;//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
  private int firstSeparator_thread_1;//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
  private int secondSeparator_thread_1;//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
  private String workpieceId_thread_1;//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
  private int target1_thread_1;//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
  private int target2_thread_1;//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
  private int S113194 = 1;
  private int S110 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S111 = 1;
  private int S118 = 1;
  private int S113 = 1;
  private int S1711 = 1;
  private int S266 = 1;
  private int S162 = 1;
  private int S157 = 1;
  private int S184 = 1;
  private int S179 = 1;
  private int S273 = 1;
  private int S268 = 1;
  private int S405 = 1;
  private int S400 = 1;
  private int S581 = 1;
  private int S576 = 1;
  private int S801 = 1;
  private int S796 = 1;
  private int S1103 = 1;
  private int S1065 = 1;
  private int S1060 = 1;
  private int S1087 = 1;
  private int S1082 = 1;
  private int S1695 = 1;
  private int S1690 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S113194){
        case 0 : 
          S113194=0;
          break RUN;
        
        case 1 : 
          S113194=2;
          S113194=2;
          S110=0;
          S6=0;
          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
              reportOut_o.setVal("READY");//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
              S1=1;
              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                ends[1]=2;
                ;//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                S110=1;
                S28=0;
                if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                  commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                    commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                    S23=1;
                    if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      ends[1]=2;
                      ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                      S110=2;
                      if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                        data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                        firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                        secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                        workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                        target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                        target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                        System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                        S111=0;
                        S118=0;
                        if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                          bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                          S118=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S113=0;
                          if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            S113=1;
                            if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              S111=1;
                              if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                S1711=0;
                                S266=0;
                                S162=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    S157=1;
                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S266=1;
                                      S184=0;
                                      if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        S184=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S179=0;
                                        if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S179=1;
                                          if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S266=2;
                                            S273=0;
                                            if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              S273=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S268=0;
                                              if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S268=1;
                                                if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S266=3;
                                                  S405=0;
                                                  if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    S405=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S400=0;
                                                    if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S400=1;
                                                      if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S266=4;
                                                        S581=0;
                                                        if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          S581=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S576=0;
                                                          if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S576=1;
                                                            if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S266=5;
                                                              S801=0;
                                                              if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                S801=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S796=0;
                                                                if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S796=1;
                                                                  if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S266=6;
                                                                    if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                      S1103=0;
                                                                      S1065=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        S1065=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S1060=0;
                                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          S1060=1;
                                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                      S1103=1;
                                                                      S1087=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        S1087=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S1082=0;
                                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          S1082=1;
                                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                S1711=1;
                                S1695=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  S1695=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S1690=0;
                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    S1690=1;
                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                        if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                          System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                        }
                        else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                          if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                            System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                          reportOut_o.setVal("READY");//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                          S1=1;
                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                            S110=1;
                            S28=0;
                            if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                S23=1;
                                if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                  commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                  command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                                  S110=2;
                                  if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                                    data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                                    firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                                    secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                                    workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                                    target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                                    target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                                    System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                                    S111=0;
                                    S118=0;
                                    if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      S118=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S113=0;
                                      if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                        bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                        S113=1;
                                        if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                          bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                          S111=1;
                                          if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                            S1711=0;
                                            S266=0;
                                            S162=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              S162=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S157=0;
                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                S157=1;
                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                  S266=1;
                                                  S184=0;
                                                  if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    S184=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S179=0;
                                                    if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                      dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                      S179=1;
                                                      if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                        dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                        S266=2;
                                                        S273=0;
                                                        if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          S273=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S268=0;
                                                          if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                            dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                            S268=1;
                                                            if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                              dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                              S266=3;
                                                              S405=0;
                                                              if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                S405=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S400=0;
                                                                if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                  dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                  S400=1;
                                                                  if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                    dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                    S266=4;
                                                                    S581=0;
                                                                    if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      S581=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S576=0;
                                                                      if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                        dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                        S576=1;
                                                                        if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                          S266=5;
                                                                          S801=0;
                                                                          if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            S801=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S796=0;
                                                                            if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                              overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                              S796=1;
                                                                              if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                                ends[1]=2;
                                                                                ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                                S266=6;
                                                                                if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                                  S1103=0;
                                                                                  S1065=0;
                                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                    S1065=1;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    S1060=0;
                                                                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                      S1060=1;
                                                                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                        ends[1]=2;
                                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                                  S1103=1;
                                                                                  S1087=0;
                                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                    S1087=1;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    S1082=0;
                                                                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                      S1082=1;
                                                                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                        ends[1]=2;
                                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            S1711=1;
                                            S1695=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                              S1695=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1690=0;
                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                                S1690=1;
                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                                    if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                                      System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                                    }
                                    else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                                      if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                                        System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                          S110=1;
                          S28=0;
                          if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                                S110=2;
                                if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                                  data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                                  firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                                  secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                                  workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                                  target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                                  target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                                  System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                                  S111=0;
                                  S118=0;
                                  if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    S118=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S113=0;
                                    if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      S113=1;
                                      if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                        bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                        S111=1;
                                        if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                          S1711=0;
                                          S266=0;
                                          S162=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            S162=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S157=0;
                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              S157=1;
                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                                S266=1;
                                                S184=0;
                                                if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  S184=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S179=0;
                                                  if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    S179=1;
                                                    if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                      dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                      S266=2;
                                                      S273=0;
                                                      if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        S273=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S268=0;
                                                        if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          S268=1;
                                                          if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                            dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                            S266=3;
                                                            S405=0;
                                                            if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              S405=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S400=0;
                                                              if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                S400=1;
                                                                if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                  dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                  S266=4;
                                                                  S581=0;
                                                                  if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    S581=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S576=0;
                                                                    if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      S576=1;
                                                                      if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                        dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                        S266=5;
                                                                        S801=0;
                                                                        if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          S801=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S796=0;
                                                                          if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            S796=1;
                                                                            if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                              overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                              S266=6;
                                                                              if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                                S1103=0;
                                                                                S1065=0;
                                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                  S1065=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S1060=0;
                                                                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                    S1060=1;
                                                                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                      ends[1]=2;
                                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                                S1103=1;
                                                                                S1087=0;
                                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                  S1087=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S1082=0;
                                                                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                    S1082=1;
                                                                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                      ends[1]=2;
                                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S1711=1;
                                          S1695=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                            S1695=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1690=0;
                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                              S1690=1;
                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                                  if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                                    System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                                  }
                                  else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                                    if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                                      System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                      reportOut_o.setVal("READY");//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                      S1=1;
                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                        S110=1;
                        S28=0;
                        if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            S23=1;
                            if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                              S110=2;
                              if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                                data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                                firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                                secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                                workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                                target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                                target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                                S111=0;
                                S118=0;
                                if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  S118=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S113=0;
                                  if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    S113=1;
                                    if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                      S111=1;
                                      if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                        S1711=0;
                                        S266=0;
                                        S162=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          S162=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S157=0;
                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            S157=1;
                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                              S266=1;
                                              S184=0;
                                              if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                S184=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S179=0;
                                                if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  S179=1;
                                                  if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                    S266=2;
                                                    S273=0;
                                                    if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      S273=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S268=0;
                                                      if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        S268=1;
                                                        if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                          S266=3;
                                                          S405=0;
                                                          if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            S405=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S400=0;
                                                            if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              S400=1;
                                                              if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                                S266=4;
                                                                S581=0;
                                                                if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  S581=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S576=0;
                                                                  if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    S576=1;
                                                                    if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                      S266=5;
                                                                      S801=0;
                                                                      if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        S801=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S796=0;
                                                                        if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          S796=1;
                                                                          if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                            S266=6;
                                                                            if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                              S1103=0;
                                                                              S1065=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                S1065=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S1060=0;
                                                                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                  S1060=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                              S1103=1;
                                                                              S1087=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                S1087=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S1082=0;
                                                                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                  S1082=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        S1711=1;
                                        S1695=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          S1695=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1690=0;
                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                            S1690=1;
                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                                if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                                  System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                                }
                                else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                                  if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                                    System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                    commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S23){
                      case 0 : 
                        if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          S23=1;
                          if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                            command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                            S110=2;
                            if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                              data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                              firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                              secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                              workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                              target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                              target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                              S111=0;
                              S118=0;
                              if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                S118=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S113=0;
                                if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  S113=1;
                                  if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                    S111=1;
                                    if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                      S1711=0;
                                      S266=0;
                                      S162=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        S162=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S157=0;
                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          S157=1;
                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                            S266=1;
                                            S184=0;
                                            if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              S184=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S179=0;
                                              if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                S179=1;
                                                if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                  S266=2;
                                                  S273=0;
                                                  if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    S273=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S268=0;
                                                    if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      S268=1;
                                                      if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                        S266=3;
                                                        S405=0;
                                                        if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          S405=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S400=0;
                                                          if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            S400=1;
                                                            if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                              S266=4;
                                                              S581=0;
                                                              if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                S581=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S576=0;
                                                                if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  S576=1;
                                                                  if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                    S266=5;
                                                                    S801=0;
                                                                    if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      S801=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S796=0;
                                                                      if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        S796=1;
                                                                        if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                          S266=6;
                                                                          if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                            S1103=0;
                                                                            S1065=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              S1065=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S1060=0;
                                                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                S1060=1;
                                                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                            S1103=1;
                                                                            S1087=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              S1087=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S1082=0;
                                                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                S1082=1;
                                                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      S1711=1;
                                      S1695=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        S1695=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S1690=0;
                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          S1690=1;
                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                              if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                                System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                              }
                              else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                                if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                                  System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                        if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                            data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                            firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                            secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                            workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                            target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                            target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                            S111=0;
                            S118=0;
                            if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                S113=1;
                                if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  S111=1;
                                  if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                    S1711=0;
                                    S266=0;
                                    S162=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        S157=1;
                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          S266=1;
                                          S184=0;
                                          if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S184=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S179=0;
                                            if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              S179=1;
                                              if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                S266=2;
                                                S273=0;
                                                if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S273=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S268=0;
                                                  if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    S268=1;
                                                    if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      S266=3;
                                                      S405=0;
                                                      if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S405=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S400=0;
                                                        if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          S400=1;
                                                          if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            S266=4;
                                                            S581=0;
                                                            if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S581=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S576=0;
                                                              if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                S576=1;
                                                                if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  S266=5;
                                                                  S801=0;
                                                                  if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S801=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S796=0;
                                                                    if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      S796=1;
                                                                      if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        S266=6;
                                                                        if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                          S1103=0;
                                                                          S1065=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            S1065=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S1060=0;
                                                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              S1060=1;
                                                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                          S1103=1;
                                                                          S1087=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            S1087=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S1082=0;
                                                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              S1082=1;
                                                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S1711=1;
                                    S1695=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      S1695=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S1690=0;
                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        S1690=1;
                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                            if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                              System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                            }
                            else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                              if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                                System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                    commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      S23=1;
                      if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                        commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                        command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                        S110=2;
                        if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                          data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                          firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                          secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                          workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                          target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                          target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                          System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                          S111=0;
                          S118=0;
                          if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            S118=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S113=0;
                            if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              S113=1;
                              if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                S111=1;
                                if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                  S1711=0;
                                  S266=0;
                                  S162=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S157=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        S266=1;
                                        S184=0;
                                        if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S184=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S179=0;
                                          if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S179=1;
                                            if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              S266=2;
                                              S273=0;
                                              if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S273=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S268=0;
                                                if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S268=1;
                                                  if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    S266=3;
                                                    S405=0;
                                                    if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S405=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S400=0;
                                                      if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S400=1;
                                                        if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          S266=4;
                                                          S581=0;
                                                          if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S581=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S576=0;
                                                            if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S576=1;
                                                              if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                S266=5;
                                                                S801=0;
                                                                if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S801=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S796=0;
                                                                  if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S796=1;
                                                                    if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      S266=6;
                                                                      if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                        S1103=0;
                                                                        S1065=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          S1065=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S1060=0;
                                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            S1060=1;
                                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                        S1103=1;
                                                                        S1087=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          S1087=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S1082=0;
                                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            S1082=1;
                                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S1711=1;
                                  S1695=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    S1695=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1690=0;
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      S1690=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                          if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                            System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                          }
                          else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                            if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                              System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
                      if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                        bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                        S118=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S113){
                          case 0 : 
                            if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              S113=1;
                              if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                S111=1;
                                if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                  S1711=0;
                                  S266=0;
                                  S162=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S157=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        S266=1;
                                        S184=0;
                                        if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S184=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S179=0;
                                          if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S179=1;
                                            if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              S266=2;
                                              S273=0;
                                              if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S273=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S268=0;
                                                if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S268=1;
                                                  if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    S266=3;
                                                    S405=0;
                                                    if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S405=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S400=0;
                                                      if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S400=1;
                                                        if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          S266=4;
                                                          S581=0;
                                                          if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S581=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S576=0;
                                                            if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S576=1;
                                                              if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                S266=5;
                                                                S801=0;
                                                                if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S801=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S796=0;
                                                                  if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S796=1;
                                                                    if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      S266=6;
                                                                      if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                        S1103=0;
                                                                        S1065=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          S1065=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S1060=0;
                                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            S1060=1;
                                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                        S1103=1;
                                                                        S1087=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          S1087=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S1082=0;
                                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            S1082=1;
                                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S1711=1;
                                  S1695=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    S1695=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1690=0;
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      S1690=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                            if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              S111=1;
                              if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                S1711=0;
                                S266=0;
                                S162=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    S157=1;
                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S266=1;
                                      S184=0;
                                      if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        S184=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S179=0;
                                        if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S179=1;
                                          if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S266=2;
                                            S273=0;
                                            if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              S273=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S268=0;
                                              if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S268=1;
                                                if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S266=3;
                                                  S405=0;
                                                  if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    S405=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S400=0;
                                                    if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S400=1;
                                                      if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S266=4;
                                                        S581=0;
                                                        if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          S581=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S576=0;
                                                          if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S576=1;
                                                            if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S266=5;
                                                              S801=0;
                                                              if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                S801=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S796=0;
                                                                if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S796=1;
                                                                  if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S266=6;
                                                                    if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                      S1103=0;
                                                                      S1065=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        S1065=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S1060=0;
                                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          S1060=1;
                                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                      S1103=1;
                                                                      S1087=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        S1087=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S1082=0;
                                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          S1082=1;
                                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                S1711=1;
                                S1695=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  S1695=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S1690=0;
                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    S1690=1;
                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                      if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                        bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                        S118=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S113=0;
                        if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                          bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                          S113=1;
                          if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                            S111=1;
                            if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                              S1711=0;
                              S266=0;
                              S162=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  S157=1;
                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    S266=1;
                                    S184=0;
                                    if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      S184=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S179=0;
                                      if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        S179=1;
                                        if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S266=2;
                                          S273=0;
                                          if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            S273=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S268=0;
                                            if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              S268=1;
                                              if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S266=3;
                                                S405=0;
                                                if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  S405=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S400=0;
                                                  if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    S400=1;
                                                    if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S266=4;
                                                      S581=0;
                                                      if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        S581=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S576=0;
                                                        if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          S576=1;
                                                          if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S266=5;
                                                            S801=0;
                                                            if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              S801=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S796=0;
                                                              if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                S796=1;
                                                                if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S266=6;
                                                                  if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                    S1103=0;
                                                                    S1065=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      S1065=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S1060=0;
                                                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        S1060=1;
                                                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                    S1103=1;
                                                                    S1087=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      S1087=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S1082=0;
                                                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        S1082=1;
                                                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              S1711=1;
                              S1695=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                S1695=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S1690=0;
                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  S1690=1;
                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                  switch(S1711){
                    case 0 : 
                      switch(S266){
                        case 0 : 
                          switch(S162){
                            case 0 : 
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S157){
                                  case 0 : 
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S157=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        S266=1;
                                        S184=0;
                                        if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S184=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S179=0;
                                          if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S179=1;
                                            if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              S266=2;
                                              S273=0;
                                              if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S273=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S268=0;
                                                if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S268=1;
                                                  if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    S266=3;
                                                    S405=0;
                                                    if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S405=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S400=0;
                                                      if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S400=1;
                                                        if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          S266=4;
                                                          S581=0;
                                                          if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S581=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S576=0;
                                                            if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S576=1;
                                                              if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                S266=5;
                                                                S801=0;
                                                                if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S801=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S796=0;
                                                                  if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S796=1;
                                                                    if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      S266=6;
                                                                      if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                        S1103=0;
                                                                        S1065=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          S1065=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S1060=0;
                                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            S1060=1;
                                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                        S1103=1;
                                                                        S1087=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          S1087=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S1082=0;
                                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            S1082=1;
                                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S266=1;
                                      S184=0;
                                      if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        S184=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S179=0;
                                        if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S179=1;
                                          if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S266=2;
                                            S273=0;
                                            if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              S273=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S268=0;
                                              if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S268=1;
                                                if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S266=3;
                                                  S405=0;
                                                  if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    S405=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S400=0;
                                                    if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S400=1;
                                                      if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S266=4;
                                                        S581=0;
                                                        if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          S581=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S576=0;
                                                          if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S576=1;
                                                            if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S266=5;
                                                              S801=0;
                                                              if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                S801=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S796=0;
                                                                if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S796=1;
                                                                  if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S266=6;
                                                                    if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                      S1103=0;
                                                                      S1065=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        S1065=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S1060=0;
                                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          S1060=1;
                                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                      S1103=1;
                                                                      S1087=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        S1087=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S1082=0;
                                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          S1082=1;
                                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                  S157=1;
                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                    S266=1;
                                    S184=0;
                                    if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      S184=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S179=0;
                                      if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        S179=1;
                                        if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                          S266=2;
                                          S273=0;
                                          if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            S273=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S268=0;
                                            if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              S268=1;
                                              if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                S266=3;
                                                S405=0;
                                                if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  S405=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S400=0;
                                                  if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    S400=1;
                                                    if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                      S266=4;
                                                      S581=0;
                                                      if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        S581=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S576=0;
                                                        if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          S576=1;
                                                          if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                            S266=5;
                                                            S801=0;
                                                            if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              S801=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S796=0;
                                                              if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                S796=1;
                                                                if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                  S266=6;
                                                                  if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                    S1103=0;
                                                                    S1065=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      S1065=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S1060=0;
                                                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        S1060=1;
                                                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                    S1103=1;
                                                                    S1087=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      S1087=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S1082=0;
                                                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        S1082=1;
                                                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                              if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                S184=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S179){
                                  case 0 : 
                                    if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      S179=1;
                                      if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                        S266=2;
                                        S273=0;
                                        if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          S273=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S268=0;
                                          if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            S268=1;
                                            if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                              S266=3;
                                              S405=0;
                                              if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                S405=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S400=0;
                                                if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  S400=1;
                                                  if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                    S266=4;
                                                    S581=0;
                                                    if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      S581=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S576=0;
                                                      if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        S576=1;
                                                        if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                          S266=5;
                                                          S801=0;
                                                          if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            S801=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S796=0;
                                                            if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              S796=1;
                                                              if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                S266=6;
                                                                if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                  S1103=0;
                                                                  S1065=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                    S1065=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S1060=0;
                                                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      S1060=1;
                                                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                  S1103=1;
                                                                  S1087=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                    S1087=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S1082=0;
                                                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      S1082=1;
                                                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                      S266=2;
                                      S273=0;
                                      if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        S273=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S268=0;
                                        if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          S268=1;
                                          if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                            S266=3;
                                            S405=0;
                                            if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              S405=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S400=0;
                                              if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                S400=1;
                                                if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                  S266=4;
                                                  S581=0;
                                                  if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    S581=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S576=0;
                                                    if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      S576=1;
                                                      if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                        S266=5;
                                                        S801=0;
                                                        if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          S801=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S796=0;
                                                          if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            S796=1;
                                                            if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                              S266=6;
                                                              if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                S1103=0;
                                                                S1065=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                  S1065=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S1060=0;
                                                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                    S1060=1;
                                                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                S1103=1;
                                                                S1087=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                  S1087=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S1082=0;
                                                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                    S1082=1;
                                                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                              if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                S184=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S179=0;
                                if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                  dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                  S179=1;
                                  if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                    dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                    S266=2;
                                    S273=0;
                                    if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      S273=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S268=0;
                                      if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        S268=1;
                                        if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                          S266=3;
                                          S405=0;
                                          if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            S405=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S400=0;
                                            if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              S400=1;
                                              if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                S266=4;
                                                S581=0;
                                                if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  S581=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S576=0;
                                                  if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    S576=1;
                                                    if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                      S266=5;
                                                      S801=0;
                                                      if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        S801=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S796=0;
                                                        if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          S796=1;
                                                          if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                            S266=6;
                                                            if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                              S1103=0;
                                                              S1065=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                S1065=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S1060=0;
                                                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                  S1060=1;
                                                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                              S1103=1;
                                                              S1087=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                S1087=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S1082=0;
                                                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                  S1082=1;
                                                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                              if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                S273=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S268){
                                  case 0 : 
                                    if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      S268=1;
                                      if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                        S266=3;
                                        S405=0;
                                        if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          S405=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S400=0;
                                          if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            S400=1;
                                            if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                              S266=4;
                                              S581=0;
                                              if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                S581=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S576=0;
                                                if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  S576=1;
                                                  if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                    S266=5;
                                                    S801=0;
                                                    if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      S801=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S796=0;
                                                      if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        S796=1;
                                                        if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                          S266=6;
                                                          if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                            S1103=0;
                                                            S1065=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                              S1065=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S1060=0;
                                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                S1060=1;
                                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                            S1103=1;
                                                            S1087=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                              S1087=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S1082=0;
                                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                S1082=1;
                                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                        active[1]=1;
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
                                    if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                      S266=3;
                                      S405=0;
                                      if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        S405=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S400=0;
                                        if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          S400=1;
                                          if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                            S266=4;
                                            S581=0;
                                            if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              S581=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S576=0;
                                              if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                S576=1;
                                                if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                  S266=5;
                                                  S801=0;
                                                  if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    S801=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S796=0;
                                                    if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      S796=1;
                                                      if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                        S266=6;
                                                        if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                          S1103=0;
                                                          S1065=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                            S1065=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S1060=0;
                                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                              S1060=1;
                                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                          S1103=1;
                                                          S1087=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                            S1087=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S1082=0;
                                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                              S1082=1;
                                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                              if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                S273=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S268=0;
                                if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                  dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                  S268=1;
                                  if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                    dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                    S266=3;
                                    S405=0;
                                    if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      S405=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S400=0;
                                      if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        S400=1;
                                        if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                          S266=4;
                                          S581=0;
                                          if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            S581=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S576=0;
                                            if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              S576=1;
                                              if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                S266=5;
                                                S801=0;
                                                if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  S801=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S796=0;
                                                  if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    S796=1;
                                                    if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                      S266=6;
                                                      if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                        S1103=0;
                                                        S1065=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                          S1065=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1060=0;
                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                            S1060=1;
                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                        S1103=1;
                                                        S1087=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                          S1087=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1082=0;
                                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                            S1082=1;
                                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                          switch(S405){
                            case 0 : 
                              if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                S405=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S400){
                                  case 0 : 
                                    if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      S400=1;
                                      if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                        S266=4;
                                        S581=0;
                                        if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          S581=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S576=0;
                                          if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            S576=1;
                                            if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                              S266=5;
                                              S801=0;
                                              if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                S801=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S796=0;
                                                if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  S796=1;
                                                  if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                    S266=6;
                                                    if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                      S1103=0;
                                                      S1065=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                        S1065=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1060=0;
                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                          S1060=1;
                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                      S1103=1;
                                                      S1087=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                        S1087=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1082=0;
                                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                          S1082=1;
                                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                    if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                      S266=4;
                                      S581=0;
                                      if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        S581=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S576=0;
                                        if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          S576=1;
                                          if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                            S266=5;
                                            S801=0;
                                            if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              S801=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S796=0;
                                              if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                S796=1;
                                                if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                  S266=6;
                                                  if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                    S1103=0;
                                                    S1065=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                      S1065=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1060=0;
                                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                        S1060=1;
                                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                    S1103=1;
                                                    S1087=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                      S1087=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1082=0;
                                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                        S1082=1;
                                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                              S405=1;
                              S405=0;
                              if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                S405=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S400=0;
                                if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                  dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                  S400=1;
                                  if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                    dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                    S266=4;
                                    S581=0;
                                    if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      S581=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S576=0;
                                      if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        S576=1;
                                        if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                          S266=5;
                                          S801=0;
                                          if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            S801=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S796=0;
                                            if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              S796=1;
                                              if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                S266=6;
                                                if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                  S1103=0;
                                                  S1065=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                    S1065=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1060=0;
                                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                      S1060=1;
                                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                  S1103=1;
                                                  S1087=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                    S1087=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1082=0;
                                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                      S1082=1;
                                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                        
                        case 4 : 
                          switch(S581){
                            case 0 : 
                              if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                S581=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S576){
                                  case 0 : 
                                    if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      S576=1;
                                      if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                        S266=5;
                                        S801=0;
                                        if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          S801=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S796=0;
                                          if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            S796=1;
                                            if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                              S266=6;
                                              if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                S1103=0;
                                                S1065=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                  S1065=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1060=0;
                                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                    S1060=1;
                                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                S1103=1;
                                                S1087=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                  S1087=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1082=0;
                                                  if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                    S1082=1;
                                                    if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                    if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                      S266=5;
                                      S801=0;
                                      if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        S801=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S796=0;
                                        if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          S796=1;
                                          if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                            S266=6;
                                            if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                              S1103=0;
                                              S1065=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                S1065=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1060=0;
                                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                  S1060=1;
                                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                              S1103=1;
                                              S1087=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                S1087=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1082=0;
                                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                  S1082=1;
                                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                              S581=1;
                              S581=0;
                              if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                S581=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S576=0;
                                if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                  dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                  S576=1;
                                  if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                    dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                    S266=5;
                                    S801=0;
                                    if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      S801=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S796=0;
                                      if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        S796=1;
                                        if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                          S266=6;
                                          if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                            S1103=0;
                                            S1065=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                              S1065=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1060=0;
                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                S1060=1;
                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                            S1103=1;
                                            S1087=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                              S1087=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1082=0;
                                              if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                S1082=1;
                                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                        
                        case 5 : 
                          switch(S801){
                            case 0 : 
                              if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                S801=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S796){
                                  case 0 : 
                                    if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      S796=1;
                                      if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                        S266=6;
                                        if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                          S1103=0;
                                          S1065=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            S1065=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1060=0;
                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                              S1060=1;
                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                          S1103=1;
                                          S1087=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            S1087=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1082=0;
                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                              S1082=1;
                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                    if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                      S266=6;
                                      if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                        S1103=0;
                                        S1065=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          S1065=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1060=0;
                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            S1060=1;
                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                        S1103=1;
                                        S1087=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          S1087=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1082=0;
                                          if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            S1082=1;
                                            if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                              reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                              S801=1;
                              S801=0;
                              if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                S801=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S796=0;
                                if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                  overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                  S796=1;
                                  if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                    S266=6;
                                    if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                      S1103=0;
                                      S1065=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                        S1065=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S1060=0;
                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          S1060=1;
                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                      S1103=1;
                                      S1087=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                        S1087=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S1082=0;
                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          S1082=1;
                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                        
                        case 6 : 
                          switch(S1103){
                            case 0 : 
                              switch(S1065){
                                case 0 : 
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                    S1065=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S1060){
                                      case 0 : 
                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          S1060=1;
                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                  S1065=1;
                                  S1065=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                    S1065=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1060=0;
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                      S1060=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                              switch(S1087){
                                case 0 : 
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                    S1087=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S1082){
                                      case 0 : 
                                        if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          S1082=1;
                                          if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                  S1087=1;
                                  S1087=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                    S1087=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1082=0;
                                    if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                      S1082=1;
                                      if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                        reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                      switch(S1695){
                        case 0 : 
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                            S1695=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S1690){
                              case 0 : 
                                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  S1690=1;
                                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                                if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                          S1695=1;
                          S1695=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                            S1695=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S1690=0;
                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                              S1690=1;
                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                  reportOut_o.setVal("READY");//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                  S1=1;
                  if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    ends[1]=2;
                    ;//sysj/TwoLiquidFillerController.sysj line: 22, column: 9
                    S110=1;
                    S28=0;
                    if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                      S28=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S23=0;
                      if(!commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                        commandIn_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                        S23=1;
                        if(commandIn_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          commandIn_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerController.sysj line: 24, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/TwoLiquidFillerController.sysj line: 26, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/TwoLiquidFillerController.sysj line: 29, column: 9
                            data_thread_1 = command_thread_1.substring(6);//sysj/TwoLiquidFillerController.sysj line: 31, column: 13
                            firstSeparator_thread_1 = data_thread_1.indexOf("|");//sysj/TwoLiquidFillerController.sysj line: 33, column: 13
                            secondSeparator_thread_1 = data_thread_1.indexOf("|", firstSeparator_thread_1 + 1);//sysj/TwoLiquidFillerController.sysj line: 35, column: 13
                            workpieceId_thread_1 = data_thread_1.substring(0, firstSeparator_thread_1);//sysj/TwoLiquidFillerController.sysj line: 38, column: 13
                            target1_thread_1 = Integer.parseInt(data_thread_1.substring(firstSeparator_thread_1 + 1, secondSeparator_thread_1));//sysj/TwoLiquidFillerController.sysj line: 41, column: 13
                            target2_thread_1 = Integer.parseInt(data_thread_1.substring(secondSeparator_thread_1 + 1));//sysj/TwoLiquidFillerController.sysj line: 49, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 57, column: 13
                            S111=0;
                            S118=0;
                            if(!bottlePresent_in.isPartnerPresent() || bottlePresent_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(!bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                bottlePresent_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                S113=1;
                                if(bottlePresent_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  bottlePresent_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerController.sysj line: 62, column: 13
                                  S111=1;
                                  if((Boolean)(bottlePresent_in.getVal() == null ? null : ((Boolean)bottlePresent_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 65, column: 16
                                    S1711=0;
                                    S266=0;
                                    S162=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                        S157=1;
                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 67, column: 17
                                          S266=1;
                                          S184=0;
                                          if(!dose1_o.isPartnerPresent() || dose1_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                            S184=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S179=0;
                                            if(dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              dose1_o.setVal(target1_thread_1);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                              S179=1;
                                              if(!dose1_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                dose1_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerController.sysj line: 72, column: 17
                                                S266=2;
                                                S273=0;
                                                if(!dose1Done_in.isPartnerPresent() || dose1Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                  S273=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S268=0;
                                                  if(!dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    dose1Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                    S268=1;
                                                    if(dose1Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      dose1Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerController.sysj line: 74, column: 17
                                                      S266=3;
                                                      S405=0;
                                                      if(!dose2_o.isPartnerPresent() || dose2_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                        S405=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S400=0;
                                                        if(dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          dose2_o.setVal(target2_thread_1);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                          S400=1;
                                                          if(!dose2_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            dose2_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerController.sysj line: 77, column: 17
                                                            S266=4;
                                                            S581=0;
                                                            if(!dose2Done_in.isPartnerPresent() || dose2Done_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                              S581=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S576=0;
                                                              if(!dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                dose2Done_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                S576=1;
                                                                if(dose2Done_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  dose2Done_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/TwoLiquidFillerController.sysj line: 79, column: 17
                                                                  S266=5;
                                                                  S801=0;
                                                                  if(!overflow_in.isPartnerPresent() || overflow_in.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                    S801=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S796=0;
                                                                    if(!overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      overflow_in.setACK(true);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                      S796=1;
                                                                      if(overflow_in.isREQ()){//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        overflow_in.setACK(false);//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/TwoLiquidFillerController.sysj line: 82, column: 17
                                                                        S266=6;
                                                                        if((Boolean)(overflow_in.getVal() == null ? null : ((Boolean)overflow_in.getVal()))){//sysj/TwoLiquidFillerController.sysj line: 85, column: 20
                                                                          S1103=0;
                                                                          S1065=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                            S1065=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S1060=0;
                                                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|OVERFLOW");//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                              S1060=1;
                                                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/TwoLiquidFillerController.sysj line: 87, column: 21
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
                                                                          S1103=1;
                                                                          S1087=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                            S1087=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S1082=0;
                                                                            if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                              S1082=1;
                                                                              if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/TwoLiquidFillerController.sysj line: 97, column: 21
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
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S1711=1;
                                    S1695=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                      S1695=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S1690=0;
                                      if(reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|NO_BOTTLE_PRESENT");//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                        S1690=1;
                                        if(!reportOut_o.isACK()){//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          reportOut_o.setREQ(false);//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerController.sysj line: 107, column: 17
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
                            if(command_thread_1.equals("RESET")) {//sysj/TwoLiquidFillerController.sysj line: 118, column: 41
                              System.out.println("RESET received");//sysj/TwoLiquidFillerController.sysj line: 120, column: 13
                            }
                            else {//sysj/TwoLiquidFillerController.sysj line: 118, column: 14
                              if(command_thread_1.equals("STOP")) {//sysj/TwoLiquidFillerController.sysj line: 127, column: 40
                                System.out.println("STOP received");//sysj/TwoLiquidFillerController.sysj line: 129, column: 13
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
          bottlePresent_in.gethook();
          dose1Done_in.gethook();
          dose2Done_in.gethook();
          overflow_in.gethook();
          reportOut_o.gethook();
          dose1_o.gethook();
          dose2_o.gethook();
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
      bottlePresent_in.sethook();
      dose1Done_in.sethook();
      dose2Done_in.sethook();
      overflow_in.sethook();
      reportOut_o.sethook();
      dose1_o.sethook();
      dose2_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        commandIn_in.gethook();
        bottlePresent_in.gethook();
        dose1Done_in.gethook();
        dose2Done_in.gethook();
        overflow_in.gethook();
        reportOut_o.gethook();
        dose1_o.gethook();
        dose2_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
