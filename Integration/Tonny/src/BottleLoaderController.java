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
  public input_Channel ackIn_in = new input_Channel();
  public input_Channel bottleAvailable_in = new input_Channel();
  public input_Channel placed_in = new input_Channel();
  public input_Channel armHome_in = new input_Channel();
  public output_Channel reportOut_o = new output_Channel();
  public output_Channel load_o = new output_Channel();
  public output_Channel retract_o = new output_Channel();
  private String command_thread_1;//sysj/BottleLoaderController.sysj line: 22, column: 9
  private int first_thread_1;//sysj/BottleLoaderController.sysj line: 24, column: 9
  private int second_thread_1;//sysj/BottleLoaderController.sysj line: 25, column: 9
  private int third_thread_1;//sysj/BottleLoaderController.sysj line: 26, column: 9
  private String jobId_thread_1;//sysj/BottleLoaderController.sysj line: 28, column: 9
  private String workpieceId_thread_1;//sysj/BottleLoaderController.sysj line: 29, column: 9
  private int S1016987 = 1;
  private int S1016986 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S44 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S51 = 1;
  private int S46 = 1;
  private int S14238 = 1;
  private int S243 = 1;
  private int S139 = 1;
  private int S134 = 1;
  private int S161 = 1;
  private int S156 = 1;
  private int S250 = 1;
  private int S245 = 1;
  private int S2105 = 1;
  private int S486 = 1;
  private int S382 = 1;
  private int S377 = 1;
  private int S404 = 1;
  private int S399 = 1;
  private int S752 = 1;
  private int S597 = 1;
  private int S493 = 1;
  private int S488 = 1;
  private int S515 = 1;
  private int S510 = 1;
  private int S604 = 1;
  private int S599 = 1;
  private int S736 = 1;
  private int S731 = 1;
  private int S2089 = 1;
  private int S2084 = 1;
  private int S14222 = 1;
  private int S14217 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S1016987){
        case 0 : 
          S1016987=0;
          break RUN;
        
        case 1 : 
          S1016987=2;
          S1016987=2;
          S1016986=0;
          S6=0;
          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 16, column: 5
            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
              reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 16, column: 5
              S1=1;
              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
                ends[1]=2;
                ;//sysj/BottleLoaderController.sysj line: 16, column: 5
                S1016986=1;
                S44=0;
                S28=0;
                if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                  commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                    commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                    S23=1;
                    if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                      commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                      ends[1]=2;
                      ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                      command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                      first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                      second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                      third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                      jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                      workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                      System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                      S44=1;
                      S51=0;
                      if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                        S51=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S46=0;
                        if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                          bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                          S46=1;
                          if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                            bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                            S44=2;
                            if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                              S14238=0;
                              S243=0;
                              S139=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                S139=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S134=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  S134=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    S243=1;
                                    S161=0;
                                    if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      S161=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S156=0;
                                      if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S156=1;
                                        if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S243=2;
                                          S250=0;
                                          if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            S250=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S245=0;
                                            if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S245=1;
                                              if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S243=3;
                                                if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                  S2105=0;
                                                  S486=0;
                                                  S382=0;
                                                  if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    S382=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S377=0;
                                                    if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S377=1;
                                                      if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S486=1;
                                                        S404=0;
                                                        if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          S404=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S399=0;
                                                          if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S399=1;
                                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S486=2;
                                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                S752=0;
                                                                S597=0;
                                                                S493=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  S493=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S488=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S488=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S597=1;
                                                                      S515=0;
                                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        S515=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S510=0;
                                                                        if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S510=1;
                                                                          if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S597=2;
                                                                            S604=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              S604=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S599=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S599=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S44=3;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                S752=1;
                                                                S736=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  S736=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S731=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S731=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S44=3;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
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
                                                  S2105=1;
                                                  S2089=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    S2089=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2084=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S2084=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S44=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                              S14238=1;
                              S14222=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                S14222=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S14217=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  S14217=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    S44=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
        
        case 2 : 
          switch(S1016986){
            case 0 : 
              switch(S6){
                case 0 : 
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                          reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 16, column: 5
                          S1=1;
                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 16, column: 5
                            S1016986=1;
                            S44=0;
                            S28=0;
                            if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                              commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                                commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                                S23=1;
                                if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                                  commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                                  command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                                  first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                                  second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                                  third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                                  jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                                  workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                                  System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                                  S44=1;
                                  S51=0;
                                  if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    S51=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S46=0;
                                    if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      S46=1;
                                      if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                        S44=2;
                                        if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                          S14238=0;
                                          S243=0;
                                          S139=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            S139=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S134=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              S134=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                                S243=1;
                                                S161=0;
                                                if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  S161=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S156=0;
                                                  if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    S156=1;
                                                    if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                      S243=2;
                                                      S250=0;
                                                      if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        S250=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S245=0;
                                                        if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          S245=1;
                                                          if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                            S243=3;
                                                            if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                              S2105=0;
                                                              S486=0;
                                                              S382=0;
                                                              if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                S382=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S377=0;
                                                                if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  S377=1;
                                                                  if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                    S486=1;
                                                                    S404=0;
                                                                    if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      S404=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S399=0;
                                                                      if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        S399=1;
                                                                        if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                          S486=2;
                                                                          if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                            S752=0;
                                                                            S597=0;
                                                                            S493=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              S493=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S488=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                S488=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                  S597=1;
                                                                                  S515=0;
                                                                                  if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    S515=1;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    S510=0;
                                                                                    if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      S510=1;
                                                                                      if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                        ends[1]=2;
                                                                                        ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                        S597=2;
                                                                                        S604=0;
                                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          S604=1;
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                        else {
                                                                                          S599=0;
                                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            S599=1;
                                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                              ends[1]=2;
                                                                                              ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                              S44=3;
                                                                                              active[1]=1;
                                                                                              ends[1]=1;
                                                                                              break RUN;
                                                                                            }
                                                                                            else {
                                                                                              active[1]=1;
                                                                                              ends[1]=1;
                                                                                              break RUN;
                                                                                            }
                                                                                          }
                                                                                          else {
                                                                                            active[1]=1;
                                                                                            ends[1]=1;
                                                                                            break RUN;
                                                                                          }
                                                                                        }
                                                                                      }
                                                                                      else {
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                    }
                                                                                    else {
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                          }
                                                                          else {
                                                                            S752=1;
                                                                            S736=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              S736=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S731=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                S731=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                  S44=3;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
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
                                                              S2105=1;
                                                              S2089=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                S2089=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S2084=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  S2084=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                    S44=3;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
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
                                          S14238=1;
                                          S14222=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            S14222=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S14217=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              S14217=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                                S44=3;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
                          ends[1]=2;
                          ;//sysj/BottleLoaderController.sysj line: 16, column: 5
                          S1016986=1;
                          S44=0;
                          S28=0;
                          if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                            commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                              commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                                commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                                first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                                second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                                third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                                jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                                workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                                S44=1;
                                S51=0;
                                if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  S51=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S46=0;
                                  if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    S46=1;
                                    if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      S44=2;
                                      if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                        S14238=0;
                                        S243=0;
                                        S139=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          S139=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S134=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            S134=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              S243=1;
                                              S161=0;
                                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                S161=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S156=0;
                                                if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  S156=1;
                                                  if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    S243=2;
                                                    S250=0;
                                                    if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      S250=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S245=0;
                                                      if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        S245=1;
                                                        if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          S243=3;
                                                          if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                            S2105=0;
                                                            S486=0;
                                                            S382=0;
                                                            if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              S382=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S377=0;
                                                              if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                S377=1;
                                                                if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  S486=1;
                                                                  S404=0;
                                                                  if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    S404=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S399=0;
                                                                    if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      S399=1;
                                                                      if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        S486=2;
                                                                        if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                          S752=0;
                                                                          S597=0;
                                                                          S493=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            S493=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S488=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              S488=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                S597=1;
                                                                                S515=0;
                                                                                if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  S515=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S510=0;
                                                                                  if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    S510=1;
                                                                                    if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      ends[1]=2;
                                                                                      ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      S597=2;
                                                                                      S604=0;
                                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        S604=1;
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                      else {
                                                                                        S599=0;
                                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          S599=1;
                                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            ends[1]=2;
                                                                                            ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            S44=3;
                                                                                            active[1]=1;
                                                                                            ends[1]=1;
                                                                                            break RUN;
                                                                                          }
                                                                                          else {
                                                                                            active[1]=1;
                                                                                            ends[1]=1;
                                                                                            break RUN;
                                                                                          }
                                                                                        }
                                                                                        else {
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                      }
                                                                                    }
                                                                                    else {
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                        }
                                                                        else {
                                                                          S752=1;
                                                                          S736=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            S736=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S731=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              S731=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                S44=3;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
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
                                                            S2105=1;
                                                            S2089=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              S2089=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S2084=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                S2084=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  S44=3;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
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
                                        S14238=1;
                                        S14222=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          S14222=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S14217=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            S14217=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              S44=3;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
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
                  S6=1;
                  S6=0;
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                      reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 16, column: 5
                      S1=1;
                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 16, column: 5
                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 16, column: 5
                        ends[1]=2;
                        ;//sysj/BottleLoaderController.sysj line: 16, column: 5
                        S1016986=1;
                        S44=0;
                        S28=0;
                        if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                          commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                            commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                            S23=1;
                            if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                              commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                              first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                              second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                              third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                              jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                              workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                              S44=1;
                              S51=0;
                              if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                S51=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S46=0;
                                if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  S46=1;
                                  if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    S44=2;
                                    if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                      S14238=0;
                                      S243=0;
                                      S139=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        S139=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S134=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          S134=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            S243=1;
                                            S161=0;
                                            if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              S161=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S156=0;
                                              if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                S156=1;
                                                if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  S243=2;
                                                  S250=0;
                                                  if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    S250=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S245=0;
                                                    if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      S245=1;
                                                      if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        S243=3;
                                                        if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                          S2105=0;
                                                          S486=0;
                                                          S382=0;
                                                          if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            S382=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S377=0;
                                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              S377=1;
                                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                S486=1;
                                                                S404=0;
                                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  S404=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S399=0;
                                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    S399=1;
                                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      S486=2;
                                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                        S752=0;
                                                                        S597=0;
                                                                        S493=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          S493=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S488=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            S488=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              S597=1;
                                                                              S515=0;
                                                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                S515=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S510=0;
                                                                                if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  S510=1;
                                                                                  if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    S597=2;
                                                                                    S604=0;
                                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      S604=1;
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                    else {
                                                                                      S599=0;
                                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        S599=1;
                                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          ends[1]=2;
                                                                                          ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          S44=3;
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                        else {
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                      }
                                                                                      else {
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                    }
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                      }
                                                                      else {
                                                                        S752=1;
                                                                        S736=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          S736=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S731=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            S731=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              S44=3;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
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
                                                          S2105=1;
                                                          S2089=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            S2089=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S2084=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              S2084=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                S44=3;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
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
                                      S14238=1;
                                      S14222=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        S14222=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S14217=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          S14217=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            S44=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
              switch(S44){
                case 0 : 
                  switch(S28){
                    case 0 : 
                      if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                        commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                        S28=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S23){
                          case 0 : 
                            if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                              commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                                commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                                first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                                second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                                third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                                jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                                workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                                S44=1;
                                S51=0;
                                if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  S51=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S46=0;
                                  if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    S46=1;
                                    if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                      S44=2;
                                      if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                        S14238=0;
                                        S243=0;
                                        S139=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          S139=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S134=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            S134=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                              S243=1;
                                              S161=0;
                                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                S161=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S156=0;
                                                if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  S156=1;
                                                  if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                    S243=2;
                                                    S250=0;
                                                    if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      S250=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S245=0;
                                                      if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        S245=1;
                                                        if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                          S243=3;
                                                          if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                            S2105=0;
                                                            S486=0;
                                                            S382=0;
                                                            if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              S382=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S377=0;
                                                              if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                S377=1;
                                                                if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                  S486=1;
                                                                  S404=0;
                                                                  if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    S404=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S399=0;
                                                                    if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      S399=1;
                                                                      if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                        S486=2;
                                                                        if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                          S752=0;
                                                                          S597=0;
                                                                          S493=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            S493=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S488=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              S488=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                                S597=1;
                                                                                S515=0;
                                                                                if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  S515=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S510=0;
                                                                                  if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    S510=1;
                                                                                    if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      ends[1]=2;
                                                                                      ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                      S597=2;
                                                                                      S604=0;
                                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        S604=1;
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                      else {
                                                                                        S599=0;
                                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          S599=1;
                                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            ends[1]=2;
                                                                                            ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                            S44=3;
                                                                                            active[1]=1;
                                                                                            ends[1]=1;
                                                                                            break RUN;
                                                                                          }
                                                                                          else {
                                                                                            active[1]=1;
                                                                                            ends[1]=1;
                                                                                            break RUN;
                                                                                          }
                                                                                        }
                                                                                        else {
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                      }
                                                                                    }
                                                                                    else {
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                        }
                                                                        else {
                                                                          S752=1;
                                                                          S736=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            S736=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S731=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              S731=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                                S44=3;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
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
                                                            S2105=1;
                                                            S2089=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              S2089=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S2084=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                S2084=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                  S44=3;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
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
                                        S14238=1;
                                        S14222=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          S14222=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S14217=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            S14217=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                              S44=3;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
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
                            if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                              commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                              first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                              second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                              third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                              jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                              workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                              S44=1;
                              S51=0;
                              if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                S51=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S46=0;
                                if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  S46=1;
                                  if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                    S44=2;
                                    if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                      S14238=0;
                                      S243=0;
                                      S139=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        S139=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S134=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          S134=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                            S243=1;
                                            S161=0;
                                            if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              S161=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S156=0;
                                              if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                S156=1;
                                                if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                  S243=2;
                                                  S250=0;
                                                  if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    S250=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S245=0;
                                                    if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      S245=1;
                                                      if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                        S243=3;
                                                        if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                          S2105=0;
                                                          S486=0;
                                                          S382=0;
                                                          if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            S382=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S377=0;
                                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              S377=1;
                                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                                S486=1;
                                                                S404=0;
                                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  S404=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S399=0;
                                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    S399=1;
                                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                      S486=2;
                                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                        S752=0;
                                                                        S597=0;
                                                                        S493=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          S493=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S488=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            S488=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                              S597=1;
                                                                              S515=0;
                                                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                S515=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S510=0;
                                                                                if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  S510=1;
                                                                                  if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                    S597=2;
                                                                                    S604=0;
                                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      S604=1;
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                    else {
                                                                                      S599=0;
                                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        S599=1;
                                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          ends[1]=2;
                                                                                          ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                          S44=3;
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                        else {
                                                                                          active[1]=1;
                                                                                          ends[1]=1;
                                                                                          break RUN;
                                                                                        }
                                                                                      }
                                                                                      else {
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                    }
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                      }
                                                                      else {
                                                                        S752=1;
                                                                        S736=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          S736=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S731=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            S731=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                              S44=3;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
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
                                                          S2105=1;
                                                          S2089=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            S2089=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S2084=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              S2084=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                                S44=3;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
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
                                      S14238=1;
                                      S14222=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        S14222=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S14217=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          S14217=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                            S44=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
                      S28=1;
                      S28=0;
                      if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                        commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                        S28=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S23=0;
                        if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                          commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                          S23=1;
                          if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                            commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                            command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                            first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                            second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                            third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                            jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                            workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                            S44=1;
                            S51=0;
                            if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                              S51=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S46=0;
                              if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                S46=1;
                                if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                  S44=2;
                                  if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                    S14238=0;
                                    S243=0;
                                    S139=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      S139=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S134=0;
                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        S134=1;
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                          S243=1;
                                          S161=0;
                                          if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            S161=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S156=0;
                                            if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              S156=1;
                                              if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                                S243=2;
                                                S250=0;
                                                if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  S250=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S245=0;
                                                  if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    S245=1;
                                                    if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                      S243=3;
                                                      if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                        S2105=0;
                                                        S486=0;
                                                        S382=0;
                                                        if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          S382=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S377=0;
                                                          if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            S377=1;
                                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                              S486=1;
                                                              S404=0;
                                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                S404=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S399=0;
                                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  S399=1;
                                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                    S486=2;
                                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                      S752=0;
                                                                      S597=0;
                                                                      S493=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        S493=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S488=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          S488=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                            S597=1;
                                                                            S515=0;
                                                                            if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              S515=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S510=0;
                                                                              if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                S510=1;
                                                                                if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                  S597=2;
                                                                                  S604=0;
                                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    S604=1;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    S599=0;
                                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      S599=1;
                                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        ends[1]=2;
                                                                                        ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                        S44=3;
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                      else {
                                                                                        active[1]=1;
                                                                                        ends[1]=1;
                                                                                        break RUN;
                                                                                      }
                                                                                    }
                                                                                    else {
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                    }
                                                                    else {
                                                                      S752=1;
                                                                      S736=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        S736=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S731=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          S731=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                            S44=3;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
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
                                                        S2105=1;
                                                        S2089=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          S2089=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S2084=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            S2084=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                              S44=3;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
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
                                    S14238=1;
                                    S14222=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      S14222=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S14217=0;
                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        S14217=1;
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                          S44=3;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                  switch(S51){
                    case 0 : 
                      if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                        S51=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S46){
                          case 0 : 
                            if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                              bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                              S46=1;
                              if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                                bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                                S44=2;
                                if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                  S14238=0;
                                  S243=0;
                                  S139=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    S139=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S134=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      S134=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        S243=1;
                                        S161=0;
                                        if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S161=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S156=0;
                                          if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            S156=1;
                                            if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              S243=2;
                                              S250=0;
                                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S250=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S245=0;
                                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  S245=1;
                                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    S243=3;
                                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                      S2105=0;
                                                      S486=0;
                                                      S382=0;
                                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S382=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S377=0;
                                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          S377=1;
                                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            S486=1;
                                                            S404=0;
                                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S404=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S399=0;
                                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                S399=1;
                                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  S486=2;
                                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                    S752=0;
                                                                    S597=0;
                                                                    S493=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S493=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S488=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        S488=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          S597=1;
                                                                          S515=0;
                                                                          if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S515=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S510=0;
                                                                            if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              S510=1;
                                                                              if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                S597=2;
                                                                                S604=0;
                                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S604=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S599=0;
                                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    S599=1;
                                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      ends[1]=2;
                                                                                      ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      S44=3;
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                    else {
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                  }
                                                                  else {
                                                                    S752=1;
                                                                    S736=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S736=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S731=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        S731=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          S44=3;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
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
                                                      S2105=1;
                                                      S2089=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S2089=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S2084=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          S2084=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            S44=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
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
                                  S14238=1;
                                  S14222=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    S14222=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S14217=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      S14217=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                        S44=3;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
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
                            if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                              S44=2;
                              if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                S14238=0;
                                S243=0;
                                S139=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  S139=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S134=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    S134=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      S243=1;
                                      S161=0;
                                      if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S161=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S156=0;
                                        if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S156=1;
                                          if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            S243=2;
                                            S250=0;
                                            if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S250=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S245=0;
                                              if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S245=1;
                                                if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  S243=3;
                                                  if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                    S2105=0;
                                                    S486=0;
                                                    S382=0;
                                                    if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S382=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S377=0;
                                                      if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S377=1;
                                                        if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          S486=1;
                                                          S404=0;
                                                          if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S404=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S399=0;
                                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S399=1;
                                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                S486=2;
                                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                  S752=0;
                                                                  S597=0;
                                                                  S493=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S493=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S488=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S488=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        S597=1;
                                                                        S515=0;
                                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S515=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S510=0;
                                                                          if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S510=1;
                                                                            if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              S597=2;
                                                                              S604=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S604=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S599=0;
                                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S599=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    S44=3;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  S752=1;
                                                                  S736=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S736=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S731=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S731=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        S44=3;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
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
                                                    S2105=1;
                                                    S2089=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S2089=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S2084=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S2084=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          S44=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                S14238=1;
                                S14222=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  S14222=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S14217=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    S14217=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      S44=3;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
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
                      S51=1;
                      S51=0;
                      if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                        bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                        S51=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S46=0;
                        if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                          bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                          S46=1;
                          if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                            bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                            ends[1]=2;
                            ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                            S44=2;
                            if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                              S14238=0;
                              S243=0;
                              S139=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                S139=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S134=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  S134=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    S243=1;
                                    S161=0;
                                    if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      S161=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S156=0;
                                      if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S156=1;
                                        if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S243=2;
                                          S250=0;
                                          if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            S250=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S245=0;
                                            if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S245=1;
                                              if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S243=3;
                                                if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                  S2105=0;
                                                  S486=0;
                                                  S382=0;
                                                  if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    S382=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S377=0;
                                                    if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S377=1;
                                                      if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S486=1;
                                                        S404=0;
                                                        if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          S404=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S399=0;
                                                          if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S399=1;
                                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S486=2;
                                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                S752=0;
                                                                S597=0;
                                                                S493=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  S493=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S488=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S488=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S597=1;
                                                                      S515=0;
                                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        S515=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S510=0;
                                                                        if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S510=1;
                                                                          if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S597=2;
                                                                            S604=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              S604=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S599=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S599=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S44=3;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                S752=1;
                                                                S736=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  S736=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S731=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S731=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S44=3;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
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
                                                  S2105=1;
                                                  S2089=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    S2089=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2084=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S2084=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S44=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                              S14238=1;
                              S14222=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                S14222=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S14217=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  S14217=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    S44=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                  switch(S14238){
                    case 0 : 
                      switch(S243){
                        case 0 : 
                          switch(S139){
                            case 0 : 
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                S139=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S134){
                                  case 0 : 
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      S134=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                        S243=1;
                                        S161=0;
                                        if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S161=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S156=0;
                                          if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            S156=1;
                                            if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                              S243=2;
                                              S250=0;
                                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S250=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S245=0;
                                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  S245=1;
                                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                    S243=3;
                                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                      S2105=0;
                                                      S486=0;
                                                      S382=0;
                                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S382=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S377=0;
                                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          S377=1;
                                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                            S486=1;
                                                            S404=0;
                                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S404=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S399=0;
                                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                S399=1;
                                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                  S486=2;
                                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                    S752=0;
                                                                    S597=0;
                                                                    S493=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S493=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S488=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        S488=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                          S597=1;
                                                                          S515=0;
                                                                          if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S515=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S510=0;
                                                                            if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              S510=1;
                                                                              if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                                S597=2;
                                                                                S604=0;
                                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S604=1;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  S599=0;
                                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    S599=1;
                                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      ends[1]=2;
                                                                                      ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                      S44=3;
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                    else {
                                                                                      active[1]=1;
                                                                                      ends[1]=1;
                                                                                      break RUN;
                                                                                    }
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                  }
                                                                  else {
                                                                    S752=1;
                                                                    S736=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S736=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S731=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        S731=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                          S44=3;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
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
                                                      S2105=1;
                                                      S2089=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S2089=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S2084=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          S2084=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                            S44=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
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
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      S243=1;
                                      S161=0;
                                      if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S161=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S156=0;
                                        if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S156=1;
                                          if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            S243=2;
                                            S250=0;
                                            if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S250=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S245=0;
                                              if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S245=1;
                                                if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  S243=3;
                                                  if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                    S2105=0;
                                                    S486=0;
                                                    S382=0;
                                                    if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S382=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S377=0;
                                                      if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S377=1;
                                                        if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          S486=1;
                                                          S404=0;
                                                          if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S404=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S399=0;
                                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S399=1;
                                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                S486=2;
                                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                  S752=0;
                                                                  S597=0;
                                                                  S493=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S493=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S488=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S488=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        S597=1;
                                                                        S515=0;
                                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S515=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S510=0;
                                                                          if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S510=1;
                                                                            if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              S597=2;
                                                                              S604=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S604=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S599=0;
                                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S599=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    S44=3;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  S752=1;
                                                                  S736=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S736=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S731=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S731=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        S44=3;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
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
                                                    S2105=1;
                                                    S2089=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S2089=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S2084=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S2084=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          S44=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                              S139=1;
                              S139=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                S139=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S134=0;
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  S134=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    S243=1;
                                    S161=0;
                                    if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      S161=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S156=0;
                                      if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S156=1;
                                        if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S243=2;
                                          S250=0;
                                          if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            S250=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S245=0;
                                            if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S245=1;
                                              if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S243=3;
                                                if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                  S2105=0;
                                                  S486=0;
                                                  S382=0;
                                                  if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    S382=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S377=0;
                                                    if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S377=1;
                                                      if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S486=1;
                                                        S404=0;
                                                        if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          S404=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S399=0;
                                                          if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S399=1;
                                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S486=2;
                                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                S752=0;
                                                                S597=0;
                                                                S493=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  S493=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S488=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S488=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S597=1;
                                                                      S515=0;
                                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        S515=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S510=0;
                                                                        if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S510=1;
                                                                          if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S597=2;
                                                                            S604=0;
                                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              S604=1;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              S599=0;
                                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S599=1;
                                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  ends[1]=2;
                                                                                  ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S44=3;
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                S752=1;
                                                                S736=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  S736=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S731=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S731=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S44=3;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
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
                                                  S2105=1;
                                                  S2089=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    S2089=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S2084=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S2084=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S44=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                          switch(S161){
                            case 0 : 
                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                S161=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S156){
                                  case 0 : 
                                    if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      S156=1;
                                      if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S243=2;
                                        S250=0;
                                        if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          S250=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S245=0;
                                          if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            S245=1;
                                            if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S243=3;
                                              if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                S2105=0;
                                                S486=0;
                                                S382=0;
                                                if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  S382=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S377=0;
                                                  if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    S377=1;
                                                    if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S486=1;
                                                      S404=0;
                                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        S404=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S399=0;
                                                        if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          S399=1;
                                                          if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S486=2;
                                                            if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                              S752=0;
                                                              S597=0;
                                                              S493=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                S493=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S488=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  S488=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S597=1;
                                                                    S515=0;
                                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      S515=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S510=0;
                                                                      if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        S510=1;
                                                                        if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S597=2;
                                                                          S604=0;
                                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            S604=1;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            S599=0;
                                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              S599=1;
                                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                ends[1]=2;
                                                                                ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S44=3;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              S752=1;
                                                              S736=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                S736=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S731=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  S731=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S44=3;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
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
                                                S2105=1;
                                                S2089=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  S2089=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S2084=0;
                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    S2084=1;
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S44=3;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
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
                                    if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                      S243=2;
                                      S250=0;
                                      if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        S250=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S245=0;
                                        if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          S245=1;
                                          if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                            S243=3;
                                            if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                              S2105=0;
                                              S486=0;
                                              S382=0;
                                              if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                S382=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S377=0;
                                                if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  S377=1;
                                                  if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                    S486=1;
                                                    S404=0;
                                                    if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      S404=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S399=0;
                                                      if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        S399=1;
                                                        if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                          S486=2;
                                                          if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                            S752=0;
                                                            S597=0;
                                                            S493=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              S493=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S488=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                S488=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                  S597=1;
                                                                  S515=0;
                                                                  if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    S515=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S510=0;
                                                                    if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      S510=1;
                                                                      if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                        S597=2;
                                                                        S604=0;
                                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          S604=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S599=0;
                                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            S599=1;
                                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                              S44=3;
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            S752=1;
                                                            S736=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              S736=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S731=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                S731=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                  S44=3;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
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
                                              S2105=1;
                                              S2089=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                S2089=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S2084=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  S2084=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                    S44=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                              S161=1;
                              S161=0;
                              if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                S161=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S156=0;
                                if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                  load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                  S156=1;
                                  if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                    load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                    S243=2;
                                    S250=0;
                                    if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      S250=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S245=0;
                                      if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        S245=1;
                                        if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                          S243=3;
                                          if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                            S2105=0;
                                            S486=0;
                                            S382=0;
                                            if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              S382=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S377=0;
                                              if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                S377=1;
                                                if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                  S486=1;
                                                  S404=0;
                                                  if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    S404=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S399=0;
                                                    if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      S399=1;
                                                      if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                        S486=2;
                                                        if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                          S752=0;
                                                          S597=0;
                                                          S493=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            S493=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S488=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              S488=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                S597=1;
                                                                S515=0;
                                                                if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  S515=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S510=0;
                                                                  if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    S510=1;
                                                                    if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                      S597=2;
                                                                      S604=0;
                                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        S604=1;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        S599=0;
                                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          S599=1;
                                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            ends[1]=2;
                                                                            ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                            S44=3;
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          S752=1;
                                                          S736=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            S736=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S731=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              S731=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                S44=3;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
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
                                            S2105=1;
                                            S2089=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              S2089=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S2084=0;
                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                S2084=1;
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                  S44=3;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
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
                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                S250=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S245){
                                  case 0 : 
                                    if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      S245=1;
                                      if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                        S243=3;
                                        if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                          S2105=0;
                                          S486=0;
                                          S382=0;
                                          if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            S382=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S377=0;
                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              S377=1;
                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                S486=1;
                                                S404=0;
                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  S404=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S399=0;
                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    S399=1;
                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      S486=2;
                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                        S752=0;
                                                        S597=0;
                                                        S493=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          S493=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S488=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            S488=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              S597=1;
                                                              S515=0;
                                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                S515=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S510=0;
                                                                if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  S510=1;
                                                                  if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    S597=2;
                                                                    S604=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      S604=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S599=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        S599=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          S44=3;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        S752=1;
                                                        S736=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          S736=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S731=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            S731=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              S44=3;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
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
                                          S2105=1;
                                          S2089=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            S2089=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S2084=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              S2084=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                S44=3;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                                    if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                      S243=3;
                                      if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                        S2105=0;
                                        S486=0;
                                        S382=0;
                                        if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                          S382=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S377=0;
                                          if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            S377=1;
                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              S486=1;
                                              S404=0;
                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                S404=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S399=0;
                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  S399=1;
                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    S486=2;
                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                      S752=0;
                                                      S597=0;
                                                      S493=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        S493=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S488=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          S488=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            S597=1;
                                                            S515=0;
                                                            if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              S515=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S510=0;
                                                              if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                S510=1;
                                                                if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  S597=2;
                                                                  S604=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    S604=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S599=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      S599=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        S44=3;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S752=1;
                                                      S736=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        S736=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S731=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          S731=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            S44=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
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
                                        S2105=1;
                                        S2089=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          S2089=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S2084=0;
                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            S2084=1;
                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                              S44=3;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
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
                              if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                S250=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S245=0;
                                if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                  placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                  S245=1;
                                  if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                    placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                    S243=3;
                                    if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                      S2105=0;
                                      S486=0;
                                      S382=0;
                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                        S382=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S377=0;
                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                          S377=1;
                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            S486=1;
                                            S404=0;
                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              S404=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S399=0;
                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                S399=1;
                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  S486=2;
                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                    S752=0;
                                                    S597=0;
                                                    S493=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      S493=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S488=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        S488=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          S597=1;
                                                          S515=0;
                                                          if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            S515=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S510=0;
                                                            if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              S510=1;
                                                              if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                S597=2;
                                                                S604=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  S604=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S599=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    S599=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      S44=3;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S752=1;
                                                    S736=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      S736=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S731=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        S731=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          S44=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                      S2105=1;
                                      S2089=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                        S2089=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S2084=0;
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          S2084=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            S44=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
                          switch(S2105){
                            case 0 : 
                              switch(S486){
                                case 0 : 
                                  switch(S382){
                                    case 0 : 
                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                        S382=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S377){
                                          case 0 : 
                                            if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              S377=1;
                                              if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                S486=1;
                                                S404=0;
                                                if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  S404=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S399=0;
                                                  if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    S399=1;
                                                    if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                      S486=2;
                                                      if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                        S752=0;
                                                        S597=0;
                                                        S493=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          S493=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S488=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            S488=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                              S597=1;
                                                              S515=0;
                                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                S515=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S510=0;
                                                                if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  S510=1;
                                                                  if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                    S597=2;
                                                                    S604=0;
                                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      S604=1;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      S599=0;
                                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        S599=1;
                                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          ends[1]=2;
                                                                          ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                          S44=3;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        S752=1;
                                                        S736=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          S736=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S731=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            S731=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                              S44=3;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
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
                                            if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                              S486=1;
                                              S404=0;
                                              if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                S404=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S399=0;
                                                if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  S399=1;
                                                  if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                    S486=2;
                                                    if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                      S752=0;
                                                      S597=0;
                                                      S493=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        S493=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S488=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          S488=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                            S597=1;
                                                            S515=0;
                                                            if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              S515=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S510=0;
                                                              if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                S510=1;
                                                                if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                  S597=2;
                                                                  S604=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    S604=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S599=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      S599=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                        S44=3;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S752=1;
                                                      S736=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        S736=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S731=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          S731=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                            S44=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
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
                                      S382=1;
                                      S382=0;
                                      if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                        retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                        S382=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S377=0;
                                        if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                          retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                          S377=1;
                                          if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                            S486=1;
                                            S404=0;
                                            if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              S404=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S399=0;
                                              if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                S399=1;
                                                if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                  S486=2;
                                                  if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                    S752=0;
                                                    S597=0;
                                                    S493=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      S493=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S488=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        S488=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                          S597=1;
                                                          S515=0;
                                                          if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            S515=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S510=0;
                                                            if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              S510=1;
                                                              if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                S597=2;
                                                                S604=0;
                                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  S604=1;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  S599=0;
                                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    S599=1;
                                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      ends[1]=2;
                                                                      ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                      S44=3;
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S752=1;
                                                    S736=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      S736=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S731=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        S731=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                          S44=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                  switch(S404){
                                    case 0 : 
                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                        S404=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        switch(S399){
                                          case 0 : 
                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              S399=1;
                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                S486=2;
                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                  S752=0;
                                                  S597=0;
                                                  S493=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    S493=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S488=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      S488=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        S597=1;
                                                        S515=0;
                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          S515=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S510=0;
                                                          if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            S510=1;
                                                            if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              S597=2;
                                                              S604=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                S604=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S599=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  S599=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    S44=3;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  S752=1;
                                                  S736=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    S736=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S731=0;
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      S731=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                        S44=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                                            if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              ends[1]=2;
                                              ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                              S486=2;
                                              if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                S752=0;
                                                S597=0;
                                                S493=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                  S493=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S488=0;
                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    S488=1;
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      S597=1;
                                                      S515=0;
                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        S515=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S510=0;
                                                        if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          S510=1;
                                                          if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            S597=2;
                                                            S604=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              S604=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S599=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                S599=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  S44=3;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                S752=1;
                                                S736=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  S736=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S731=0;
                                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    S731=1;
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                      S44=3;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
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
                                      S404=1;
                                      S404=0;
                                      if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                        armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                        S404=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S399=0;
                                        if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                          armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                          S399=1;
                                          if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                            S486=2;
                                            if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                              S752=0;
                                              S597=0;
                                              S493=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                S493=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S488=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                  S488=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    S597=1;
                                                    S515=0;
                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      S515=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S510=0;
                                                      if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        S510=1;
                                                        if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          S597=2;
                                                          S604=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            S604=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S599=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              S599=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                S44=3;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              S752=1;
                                              S736=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                S736=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S731=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  S731=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    S44=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                                  switch(S752){
                                    case 0 : 
                                      switch(S597){
                                        case 0 : 
                                          switch(S493){
                                            case 0 : 
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                S493=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                switch(S488){
                                                  case 0 : 
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      S488=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                        S597=1;
                                                        S515=0;
                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          S515=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S510=0;
                                                          if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            S510=1;
                                                            if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                              S597=2;
                                                              S604=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                S604=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S599=0;
                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  S599=1;
                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    ends[1]=2;
                                                                    ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                    S44=3;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
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
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                      S597=1;
                                                      S515=0;
                                                      if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        S515=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S510=0;
                                                        if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          S510=1;
                                                          if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                            S597=2;
                                                            S604=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              S604=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S599=0;
                                                              if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                S599=1;
                                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  ends[1]=2;
                                                                  ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                  S44=3;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                              S493=1;
                                              S493=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                S493=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S488=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                  reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                  S488=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                    S597=1;
                                                    S515=0;
                                                    if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      S515=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S510=0;
                                                      if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        S510=1;
                                                        if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                          S597=2;
                                                          S604=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            S604=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S599=0;
                                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              S599=1;
                                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                S44=3;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                                          switch(S515){
                                            case 0 : 
                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                S515=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                switch(S510){
                                                  case 0 : 
                                                    if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      S510=1;
                                                      if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                        S597=2;
                                                        S604=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          S604=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S599=0;
                                                          if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            S599=1;
                                                            if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              ends[1]=2;
                                                              ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                              S44=3;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
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
                                                    if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                      S597=2;
                                                      S604=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        S604=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S599=0;
                                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          S599=1;
                                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            ends[1]=2;
                                                            ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                            S44=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                              S515=1;
                                              S515=0;
                                              if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                S515=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S510=0;
                                                if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                  ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                  S510=1;
                                                  if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                    ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                    S597=2;
                                                    S604=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      S604=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S599=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        S599=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                          S44=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                                          switch(S604){
                                            case 0 : 
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                S604=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                switch(S599){
                                                  case 0 : 
                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      S599=1;
                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        ends[1]=2;
                                                        ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                        S44=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
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
                                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      ends[1]=2;
                                                      ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                      S44=3;
                                                      active[1]=1;
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
                                              S604=1;
                                              S604=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                S604=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S599=0;
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                  reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                  S599=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                    S44=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                                      switch(S736){
                                        case 0 : 
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                            S736=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            switch(S731){
                                              case 0 : 
                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  S731=1;
                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    ends[1]=2;
                                                    ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                    S44=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
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
                                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                  S44=3;
                                                  active[1]=1;
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
                                          S736=1;
                                          S736=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                            S736=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S731=0;
                                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                              S731=1;
                                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                ends[1]=2;
                                                ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                S44=3;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                              switch(S2089){
                                case 0 : 
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                    S2089=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    switch(S2084){
                                      case 0 : 
                                        if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          S2084=1;
                                          if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                            S44=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
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
                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          ends[1]=2;
                                          ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                          S44=3;
                                          active[1]=1;
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
                                  S2089=1;
                                  S2089=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                    S2089=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S2084=0;
                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                      S2084=1;
                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                        ends[1]=2;
                                        ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                        S44=3;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
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
                      switch(S14222){
                        case 0 : 
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                            S14222=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S14217){
                              case 0 : 
                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  S14217=1;
                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    ends[1]=2;
                                    ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    S44=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
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
                                if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  ends[1]=2;
                                  ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  S44=3;
                                  active[1]=1;
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
                          S14222=1;
                          S14222=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                            reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                            S14222=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S14217=0;
                            if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                              reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                              S14217=1;
                              if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                ends[1]=2;
                                ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                S44=3;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
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
                  S44=3;
                  S44=0;
                  S28=0;
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                    commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                      commandIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 20, column: 9
                      S23=1;
                      if(commandIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 20, column: 9
                        commandIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 20, column: 9
                        ends[1]=2;
                        ;//sysj/BottleLoaderController.sysj line: 20, column: 9
                        command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/BottleLoaderController.sysj line: 22, column: 9
                        first_thread_1 = command_thread_1.indexOf("|");//sysj/BottleLoaderController.sysj line: 24, column: 9
                        second_thread_1 = command_thread_1.indexOf("|", first_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 25, column: 9
                        third_thread_1 = command_thread_1.indexOf("|", second_thread_1 + 1);//sysj/BottleLoaderController.sysj line: 26, column: 9
                        jobId_thread_1 = command_thread_1.substring(first_thread_1 + 1, second_thread_1);//sysj/BottleLoaderController.sysj line: 28, column: 9
                        workpieceId_thread_1 = command_thread_1.substring(second_thread_1 + 1, third_thread_1);//sysj/BottleLoaderController.sysj line: 29, column: 9
                        System.out.println("START received for " + workpieceId_thread_1);//sysj/BottleLoaderController.sysj line: 31, column: 9
                        S44=1;
                        S51=0;
                        if(!bottleAvailable_in.isPartnerPresent() || bottleAvailable_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                          bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                          S51=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S46=0;
                          if(!bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                            bottleAvailable_in.setACK(true);//sysj/BottleLoaderController.sysj line: 36, column: 9
                            S46=1;
                            if(bottleAvailable_in.isREQ()){//sysj/BottleLoaderController.sysj line: 36, column: 9
                              bottleAvailable_in.setACK(false);//sysj/BottleLoaderController.sysj line: 36, column: 9
                              ends[1]=2;
                              ;//sysj/BottleLoaderController.sysj line: 36, column: 9
                              S44=2;
                              if((Boolean)(bottleAvailable_in.getVal() == null ? null : ((Boolean)bottleAvailable_in.getVal()))){//sysj/BottleLoaderController.sysj line: 39, column: 12
                                S14238=0;
                                S243=0;
                                S139=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                  S139=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S134=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    reportOut_o.setVal("BUSY|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER");//sysj/BottleLoaderController.sysj line: 41, column: 13
                                    S134=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 41, column: 13
                                      S243=1;
                                      S161=0;
                                      if(!load_o.isPartnerPresent() || load_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                        S161=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S156=0;
                                        if(load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          load_o.setVal(true);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                          S156=1;
                                          if(!load_o.isACK()){//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            load_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            ends[1]=2;
                                            ;//sysj/BottleLoaderController.sysj line: 49, column: 13
                                            S243=2;
                                            S250=0;
                                            if(!placed_in.isPartnerPresent() || placed_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                              S250=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S245=0;
                                              if(!placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                placed_in.setACK(true);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                S245=1;
                                                if(placed_in.isREQ()){//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  placed_in.setACK(false);//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/BottleLoaderController.sysj line: 51, column: 13
                                                  S243=3;
                                                  if((Boolean)(placed_in.getVal() == null ? null : ((Boolean)placed_in.getVal()))){//sysj/BottleLoaderController.sysj line: 54, column: 16
                                                    S2105=0;
                                                    S486=0;
                                                    S382=0;
                                                    if(!retract_o.isPartnerPresent() || retract_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                      S382=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S377=0;
                                                      if(retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        retract_o.setVal(true);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                        S377=1;
                                                        if(!retract_o.isACK()){//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          retract_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 56, column: 17
                                                          S486=1;
                                                          S404=0;
                                                          if(!armHome_in.isPartnerPresent() || armHome_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                            S404=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S399=0;
                                                            if(!armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              armHome_in.setACK(true);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                              S399=1;
                                                              if(armHome_in.isREQ()){//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                armHome_in.setACK(false);//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/BottleLoaderController.sysj line: 58, column: 17
                                                                S486=2;
                                                                if((Boolean)(armHome_in.getVal() == null ? null : ((Boolean)armHome_in.getVal()))){//sysj/BottleLoaderController.sysj line: 61, column: 20
                                                                  S752=0;
                                                                  S597=0;
                                                                  S493=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                    S493=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S488=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      reportOut_o.setVal("DONE|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|OK|bottlePlaced=true");//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                      S488=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 63, column: 21
                                                                        S597=1;
                                                                        S515=0;
                                                                        if(!ackIn_in.isPartnerPresent() || ackIn_in.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                          S515=1;
                                                                          active[1]=1;
                                                                          ends[1]=1;
                                                                          break RUN;
                                                                        }
                                                                        else {
                                                                          S510=0;
                                                                          if(!ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            ackIn_in.setACK(true);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                            S510=1;
                                                                            if(ackIn_in.isREQ()){//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ackIn_in.setACK(false);//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              ends[1]=2;
                                                                              ;//sysj/BottleLoaderController.sysj line: 71, column: 21
                                                                              S597=2;
                                                                              S604=0;
                                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                S604=1;
                                                                                active[1]=1;
                                                                                ends[1]=1;
                                                                                break RUN;
                                                                              }
                                                                              else {
                                                                                S599=0;
                                                                                if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  reportOut_o.setVal("READY|LOADER");//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                  S599=1;
                                                                                  if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    ends[1]=2;
                                                                                    ;//sysj/BottleLoaderController.sysj line: 74, column: 21
                                                                                    S44=3;
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                  else {
                                                                                    active[1]=1;
                                                                                    ends[1]=1;
                                                                                    break RUN;
                                                                                  }
                                                                                }
                                                                                else {
                                                                                  active[1]=1;
                                                                                  ends[1]=1;
                                                                                  break RUN;
                                                                                }
                                                                              }
                                                                            }
                                                                            else {
                                                                              active[1]=1;
                                                                              ends[1]=1;
                                                                              break RUN;
                                                                            }
                                                                          }
                                                                          else {
                                                                            active[1]=1;
                                                                            ends[1]=1;
                                                                            break RUN;
                                                                          }
                                                                        }
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                    }
                                                                    else {
                                                                      active[1]=1;
                                                                      ends[1]=1;
                                                                      break RUN;
                                                                    }
                                                                  }
                                                                }
                                                                else {
                                                                  S752=1;
                                                                  S736=0;
                                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                    S736=1;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    S731=0;
                                                                    if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|ARM_NOT_HOME");//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                      S731=1;
                                                                      if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        ends[1]=2;
                                                                        ;//sysj/BottleLoaderController.sysj line: 82, column: 21
                                                                        S44=3;
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
                                                                      }
                                                                      else {
                                                                        active[1]=1;
                                                                        ends[1]=1;
                                                                        break RUN;
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
                                                    S2105=1;
                                                    S2089=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                      S2089=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S2084=0;
                                                      if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|BOTTLE_NOT_PLACED");//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                        S2084=1;
                                                        if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/BottleLoaderController.sysj line: 95, column: 17
                                                          S44=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                S14238=1;
                                S14222=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                  S14222=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S14217=0;
                                  if(reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    reportOut_o.setVal("FAULT|" + jobId_thread_1 + "|" + workpieceId_thread_1 + "|LOADER|NO_BOTTLE_AVAILABLE");//sysj/BottleLoaderController.sysj line: 108, column: 13
                                    S14217=1;
                                    if(!reportOut_o.isACK()){//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      reportOut_o.setREQ(false);//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      ends[1]=2;
                                      ;//sysj/BottleLoaderController.sysj line: 108, column: 13
                                      S44=3;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
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
      ackIn_in.sethook();
      bottleAvailable_in.sethook();
      placed_in.sethook();
      armHome_in.sethook();
      reportOut_o.sethook();
      load_o.sethook();
      retract_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        commandIn_in.gethook();
        ackIn_in.gethook();
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
