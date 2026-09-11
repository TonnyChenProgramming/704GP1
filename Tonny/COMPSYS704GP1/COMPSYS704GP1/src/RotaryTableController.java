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
  public input_Channel commandIn_in = new input_Channel();
  public input_Channel aligned_in = new input_Channel();
  public input_Channel occupancy_in = new input_Channel();
  public output_Channel reportOut_o = new output_Channel();
  public output_Channel rotate_o = new output_Channel();
  private String command_thread_1;//sysj/RoteryTableController.sysj line: 22, column: 9
  private String workpieceId_thread_1;//sysj/RoteryTableController.sysj line: 27, column: 13
  private String currentOccupancy_thread_1;//sysj/RoteryTableController.sysj line: 56, column: 17
  private int S27586 = 1;
  private int S110 = 1;
  private int S6 = 1;
  private int S1 = 1;
  private int S28 = 1;
  private int S23 = 1;
  private int S111 = 1;
  private int S118 = 1;
  private int S113 = 1;
  private int S162 = 1;
  private int S157 = 1;
  private int S250 = 1;
  private int S245 = 1;
  private int S531 = 1;
  private int S420 = 1;
  private int S382 = 1;
  private int S377 = 1;
  private int S427 = 1;
  private int S422 = 1;
  private int S515 = 1;
  private int S510 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S27586){
        case 0 : 
          S27586=0;
          break RUN;
        
        case 1 : 
          S27586=2;
          S27586=2;
          S110=0;
          S6=0;
          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 17, column: 9
            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
              reportOut_o.setVal("READY");//sysj/RoteryTableController.sysj line: 17, column: 9
              S1=1;
              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                ends[1]=2;
                ;//sysj/RoteryTableController.sysj line: 17, column: 9
                S110=1;
                S28=0;
                if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                  commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                    commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                    S23=1;
                    if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                      commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                      ends[1]=2;
                      ;//sysj/RoteryTableController.sysj line: 20, column: 9
                      command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                      S110=2;
                      if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                        workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                        System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                        S111=0;
                        S118=0;
                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                          S118=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S113=0;
                          if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                            reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                            S113=1;
                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTableController.sysj line: 34, column: 13
                              S111=1;
                              S162=0;
                              if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                  rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                  S157=1;
                                  if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                    rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                    S111=2;
                                    S250=0;
                                    if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                      aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                      S250=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S245=0;
                                      if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                        aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                        S245=1;
                                        if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                          aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                          S111=3;
                                          if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                            S531=0;
                                            System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                            S420=0;
                                            S382=0;
                                            if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                              occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                              S382=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S377=0;
                                              if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                S377=1;
                                                if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                  System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                  S420=1;
                                                  S427=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    S427=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S422=0;
                                                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      S422=1;
                                                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        S110=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            S531=1;
                                            S515=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                              S515=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S510=0;
                                              if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                S510=1;
                                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  S110=3;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
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
                        if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                          System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                        }
                        else {//sysj/RoteryTableController.sysj line: 79, column: 14
                          if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                            System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                          }
                          else {//sysj/RoteryTableController.sysj line: 95, column: 13
                            System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 17, column: 9
                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                          reportOut_o.setVal("READY");//sysj/RoteryTableController.sysj line: 17, column: 9
                          S1=1;
                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 17, column: 9
                            S110=1;
                            S28=0;
                            if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                              commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                                commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                                S23=1;
                                if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                                  commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 20, column: 9
                                  command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                                  S110=2;
                                  if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                                    workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                                    System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                                    S111=0;
                                    S118=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                      S118=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S113=0;
                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                        reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                                        S113=1;
                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                          S111=1;
                                          S162=0;
                                          if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                            rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                            S162=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S157=0;
                                            if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                              rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                              S157=1;
                                              if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                                rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                                ends[1]=2;
                                                ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                                S111=2;
                                                S250=0;
                                                if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  S250=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S245=0;
                                                  if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                    aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                    S245=1;
                                                    if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                      aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                                      S111=3;
                                                      if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                                        S531=0;
                                                        System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                                        S420=0;
                                                        S382=0;
                                                        if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          S382=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S377=0;
                                                          if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                            occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                            S377=1;
                                                            if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                              occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                              currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                              System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                              S420=1;
                                                              S427=0;
                                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                S427=1;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                S422=0;
                                                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                  S422=1;
                                                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                    ends[1]=2;
                                                                    ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                    S110=3;
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                  else {
                                                                    active[1]=1;
                                                                    ends[1]=1;
                                                                    break RUN;
                                                                  }
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        S531=1;
                                                        S515=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          S515=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S510=0;
                                                          if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                            S510=1;
                                                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                              S110=3;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
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
                                    if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                                      System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                                    }
                                    else {//sysj/RoteryTableController.sysj line: 79, column: 14
                                      if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                                        System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                                      }
                                      else {//sysj/RoteryTableController.sysj line: 95, column: 13
                                        System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                          ends[1]=2;
                          ;//sysj/RoteryTableController.sysj line: 17, column: 9
                          S110=1;
                          S28=0;
                          if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                            commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                              commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                              S23=1;
                              if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                                commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 20, column: 9
                                command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                                S110=2;
                                if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                                  workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                                  System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                                  S111=0;
                                  S118=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                    S118=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S113=0;
                                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                      reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                                      S113=1;
                                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                        S111=1;
                                        S162=0;
                                        if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                          rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                          S162=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S157=0;
                                          if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                            rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                            S157=1;
                                            if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                              rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                              S111=2;
                                              S250=0;
                                              if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                S250=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S245=0;
                                                if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  S245=1;
                                                  if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                    aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                                    S111=3;
                                                    if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                                      S531=0;
                                                      System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                                      S420=0;
                                                      S382=0;
                                                      if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        S382=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S377=0;
                                                        if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          S377=1;
                                                          if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                            occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                            currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                            System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                            S420=1;
                                                            S427=0;
                                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              S427=1;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              S422=0;
                                                              if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                S422=1;
                                                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                  ends[1]=2;
                                                                  ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                  S110=3;
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                                else {
                                                                  active[1]=1;
                                                                  ends[1]=1;
                                                                  break RUN;
                                                                }
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S531=1;
                                                      S515=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        S515=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S510=0;
                                                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          S510=1;
                                                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                            S110=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
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
                                  if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                                    System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                                  }
                                  else {//sysj/RoteryTableController.sysj line: 79, column: 14
                                    if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                                      System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                                    }
                                    else {//sysj/RoteryTableController.sysj line: 95, column: 13
                                      System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 17, column: 9
                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                      reportOut_o.setVal("READY");//sysj/RoteryTableController.sysj line: 17, column: 9
                      S1=1;
                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                        ends[1]=2;
                        ;//sysj/RoteryTableController.sysj line: 17, column: 9
                        S110=1;
                        S28=0;
                        if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                          commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                            commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                            S23=1;
                            if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                              commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                              ends[1]=2;
                              ;//sysj/RoteryTableController.sysj line: 20, column: 9
                              command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                              S110=2;
                              if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                                workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                                System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                                S111=0;
                                S118=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                  S118=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S113=0;
                                  if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                    reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                                    S113=1;
                                    if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                      S111=1;
                                      S162=0;
                                      if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                        rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                        S162=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S157=0;
                                        if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                          rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                          S157=1;
                                          if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                            rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                            S111=2;
                                            S250=0;
                                            if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                              S250=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S245=0;
                                              if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                S245=1;
                                                if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                                  S111=3;
                                                  if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                                    S531=0;
                                                    System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                                    S420=0;
                                                    S382=0;
                                                    if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      S382=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S377=0;
                                                      if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        S377=1;
                                                        if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                          currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                          System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                          S420=1;
                                                          S427=0;
                                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            S427=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S422=0;
                                                            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              S422=1;
                                                              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                ends[1]=2;
                                                                ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                                S110=3;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S531=1;
                                                    S515=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      S515=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S510=0;
                                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        S510=1;
                                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                          S110=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                                  System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                                }
                                else {//sysj/RoteryTableController.sysj line: 79, column: 14
                                  if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                                    System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                                  }
                                  else {//sysj/RoteryTableController.sysj line: 95, column: 13
                                    System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                    commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S23){
                      case 0 : 
                        if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                          commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                          S23=1;
                          if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                            commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 20, column: 9
                            command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                            S110=2;
                            if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                              workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                              System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                              S111=0;
                              S118=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                S118=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S113=0;
                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                  reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                                  S113=1;
                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                    S111=1;
                                    S162=0;
                                    if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                      rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                      S162=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S157=0;
                                      if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                        rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                        S157=1;
                                        if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                          rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                          S111=2;
                                          S250=0;
                                          if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                            S250=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S245=0;
                                            if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                              aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                              S245=1;
                                              if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                                aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                                ends[1]=2;
                                                ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                                S111=3;
                                                if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                                  S531=0;
                                                  System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                                  S420=0;
                                                  S382=0;
                                                  if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    S382=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S377=0;
                                                    if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      S377=1;
                                                      if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                        currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                        System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                        S420=1;
                                                        S427=0;
                                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          S427=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S422=0;
                                                          if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            S422=1;
                                                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              ends[1]=2;
                                                              ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                              S110=3;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  S531=1;
                                                  S515=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    S515=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S510=0;
                                                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      S510=1;
                                                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                        S110=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                              if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                                System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                              }
                              else {//sysj/RoteryTableController.sysj line: 79, column: 14
                                if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                                  System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                                }
                                else {//sysj/RoteryTableController.sysj line: 95, column: 13
                                  System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                        if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                          commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                          ends[1]=2;
                          ;//sysj/RoteryTableController.sysj line: 20, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                            workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                            S111=0;
                            S118=0;
                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                                S113=1;
                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                  S111=1;
                                  S162=0;
                                  if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                    rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                      rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                      S157=1;
                                      if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                        rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                        S111=2;
                                        S250=0;
                                        if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                          aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                          S250=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S245=0;
                                          if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                            aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                            S245=1;
                                            if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                              S111=3;
                                              if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                                S531=0;
                                                System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                                S420=0;
                                                S382=0;
                                                if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  S382=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S377=0;
                                                  if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    S377=1;
                                                    if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                      System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                      S420=1;
                                                      S427=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        S427=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S422=0;
                                                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          S422=1;
                                                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            S110=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                S531=1;
                                                S515=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  S515=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S510=0;
                                                  if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    S510=1;
                                                    if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      S110=3;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
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
                            if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                              System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                            }
                            else {//sysj/RoteryTableController.sysj line: 79, column: 14
                              if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                                System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                              }
                              else {//sysj/RoteryTableController.sysj line: 95, column: 13
                                System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                  if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                    commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                      commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                      S23=1;
                      if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                        commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                        ends[1]=2;
                        ;//sysj/RoteryTableController.sysj line: 20, column: 9
                        command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                        S110=2;
                        if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                          workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                          System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                          S111=0;
                          S118=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                            S118=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S113=0;
                            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                              reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                              S113=1;
                              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                S111=1;
                                S162=0;
                                if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                  rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                    rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                    S157=1;
                                    if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                      rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                      S111=2;
                                      S250=0;
                                      if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                        S250=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S245=0;
                                        if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                          aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                          S245=1;
                                          if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                            S111=3;
                                            if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                              S531=0;
                                              System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                              S420=0;
                                              S382=0;
                                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                S382=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S377=0;
                                                if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  S377=1;
                                                  if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                    System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                    S420=1;
                                                    S427=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      S427=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S422=0;
                                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        S422=1;
                                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          S110=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              S531=1;
                                              S515=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                S515=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S510=0;
                                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  S510=1;
                                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    S110=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                          if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                            System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                          }
                          else {//sysj/RoteryTableController.sysj line: 79, column: 14
                            if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                              System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                            }
                            else {//sysj/RoteryTableController.sysj line: 95, column: 13
                              System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                        S118=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S113){
                          case 0 : 
                            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                              reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                              S113=1;
                              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                S111=1;
                                S162=0;
                                if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                  rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                  S162=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S157=0;
                                  if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                    rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                    S157=1;
                                    if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                      rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                      S111=2;
                                      S250=0;
                                      if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                        S250=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S245=0;
                                        if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                          aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                          S245=1;
                                          if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                            S111=3;
                                            if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                              S531=0;
                                              System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                              S420=0;
                                              S382=0;
                                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                S382=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S377=0;
                                                if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  S377=1;
                                                  if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                    System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                    S420=1;
                                                    S427=0;
                                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      S427=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S422=0;
                                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        S422=1;
                                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          ends[1]=2;
                                                          ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          S110=3;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              S531=1;
                                              S515=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                S515=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S510=0;
                                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  S510=1;
                                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    S110=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTableController.sysj line: 34, column: 13
                              S111=1;
                              S162=0;
                              if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                S162=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S157=0;
                                if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                  rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                  S157=1;
                                  if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                    rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                    S111=2;
                                    S250=0;
                                    if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                      aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                      S250=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S245=0;
                                      if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                        aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                        S245=1;
                                        if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                          aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                          S111=3;
                                          if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                            S531=0;
                                            System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                            S420=0;
                                            S382=0;
                                            if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                              occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                              S382=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S377=0;
                                              if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                S377=1;
                                                if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                  System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                  S420=1;
                                                  S427=0;
                                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    S427=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S422=0;
                                                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      S422=1;
                                                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        ends[1]=2;
                                                        ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        S110=3;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            S531=1;
                                            S515=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                              S515=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S510=0;
                                              if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                S510=1;
                                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  S110=3;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
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
                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                        S118=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S113=0;
                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                          reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                          S113=1;
                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 34, column: 13
                            S111=1;
                            S162=0;
                            if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                              rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                              S162=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S157=0;
                              if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                S157=1;
                                if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                  rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                  S111=2;
                                  S250=0;
                                  if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                    aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                    S250=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S245=0;
                                    if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                      aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                      S245=1;
                                      if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                        S111=3;
                                        if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                          S531=0;
                                          System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                          S420=0;
                                          S382=0;
                                          if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                            occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                            S382=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S377=0;
                                            if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                              occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                              S377=1;
                                              if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                ends[1]=2;
                                                ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                S420=1;
                                                S427=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  S427=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S422=0;
                                                  if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    S422=1;
                                                    if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                      S110=3;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S531=1;
                                          S515=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                            S515=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S510=0;
                                            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                              S510=1;
                                              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                ends[1]=2;
                                                ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                S110=3;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                      if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                        rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                        S162=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S157){
                          case 0 : 
                            if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                              rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                              S157=1;
                              if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                S111=2;
                                S250=0;
                                if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                  aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                  S250=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S245=0;
                                  if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                    aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                    S245=1;
                                    if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                      aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                      S111=3;
                                      if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                        S531=0;
                                        System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                        S420=0;
                                        S382=0;
                                        if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                          occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                          S382=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S377=0;
                                          if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                            occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                            S377=1;
                                            if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                              occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                              currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                              System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                              S420=1;
                                              S427=0;
                                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                S427=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S422=0;
                                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  S422=1;
                                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    ends[1]=2;
                                                    ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                    S110=3;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        S531=1;
                                        S515=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                          S515=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S510=0;
                                          if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                            reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                            S510=1;
                                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                              S110=3;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
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
                            if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                              rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTableController.sysj line: 40, column: 13
                              S111=2;
                              S250=0;
                              if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                S250=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S245=0;
                                if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                  aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                  S245=1;
                                  if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                    aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                    S111=3;
                                    if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                      S531=0;
                                      System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                      S420=0;
                                      S382=0;
                                      if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                        S382=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S377=0;
                                        if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                          occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                          S377=1;
                                          if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                            occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                            currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                            System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                            S420=1;
                                            S427=0;
                                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                              S427=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S422=0;
                                              if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                S422=1;
                                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  ends[1]=2;
                                                  ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                  S110=3;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      S531=1;
                                      S515=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                        S515=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S510=0;
                                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                          reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                          S510=1;
                                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                            S110=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
                      if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                        rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                        S162=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S157=0;
                        if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                          rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                          S157=1;
                          if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                            rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 40, column: 13
                            S111=2;
                            S250=0;
                            if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                              S250=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S245=0;
                              if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                S245=1;
                                if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                  aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                  S111=3;
                                  if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                    S531=0;
                                    System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                    S420=0;
                                    S382=0;
                                    if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                      S382=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S377=0;
                                      if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                        occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                        S377=1;
                                        if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                          occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                          currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                          System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                          S420=1;
                                          S427=0;
                                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                            S427=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S422=0;
                                            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                              reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                              S422=1;
                                              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                ends[1]=2;
                                                ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                S110=3;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S531=1;
                                    S515=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                      S515=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S510=0;
                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                        reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                        S510=1;
                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                          S110=3;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                      if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                        S250=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S245){
                          case 0 : 
                            if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                              aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                              S245=1;
                              if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                S111=3;
                                if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                  S531=0;
                                  System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                  S420=0;
                                  S382=0;
                                  if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                    S382=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S377=0;
                                    if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                      occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                      S377=1;
                                      if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                        currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                        System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                        S420=1;
                                        S427=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                          S427=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S422=0;
                                          if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                            S422=1;
                                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                              S110=3;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S531=1;
                                  S515=0;
                                  if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                    S515=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S510=0;
                                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                      reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                      S510=1;
                                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                        S110=3;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
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
                            if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                              ends[1]=2;
                              ;//sysj/RoteryTableController.sysj line: 44, column: 13
                              S111=3;
                              if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                S531=0;
                                System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                S420=0;
                                S382=0;
                                if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                  S382=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S377=0;
                                  if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                    occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                    S377=1;
                                    if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                      currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                      System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                      S420=1;
                                      S427=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                        S427=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S422=0;
                                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                          S422=1;
                                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                            S110=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                S531=1;
                                S515=0;
                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                  S515=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S510=0;
                                  if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                    S510=1;
                                    if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                      S110=3;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
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
                      if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                        aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                        S250=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S245=0;
                        if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                          aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                          S245=1;
                          if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                            aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                            ends[1]=2;
                            ;//sysj/RoteryTableController.sysj line: 44, column: 13
                            S111=3;
                            if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                              S531=0;
                              System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                              S420=0;
                              S382=0;
                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                S382=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S377=0;
                                if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                  occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                  S377=1;
                                  if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                    currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                    System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                    S420=1;
                                    S427=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                      S427=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S422=0;
                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                        S422=1;
                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                          S110=3;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              S531=1;
                              S515=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                S515=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S510=0;
                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                  S510=1;
                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                    S110=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                  switch(S531){
                    case 0 : 
                      switch(S420){
                        case 0 : 
                          switch(S382){
                            case 0 : 
                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                S382=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S377){
                                  case 0 : 
                                    if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                      occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                      S377=1;
                                      if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                        occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                        currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                        System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                        S420=1;
                                        S427=0;
                                        if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                          S427=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S422=0;
                                          if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                            reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                            S422=1;
                                            if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                              S110=3;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                      currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                      System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                      S420=1;
                                      S427=0;
                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                        S427=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S422=0;
                                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                          S422=1;
                                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                            ends[1]=2;
                                            ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                            S110=3;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                              if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                S382=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S377=0;
                                if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                  occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                  S377=1;
                                  if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                    occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                    currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                    System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                    S420=1;
                                    S427=0;
                                    if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                      S427=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S422=0;
                                      if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                        reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                        S422=1;
                                        if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                          reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                          ends[1]=2;
                                          ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                          S110=3;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                S427=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                switch(S422){
                                  case 0 : 
                                    if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                      reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                      S422=1;
                                      if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                        S110=3;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
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
                                    if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                      ends[1]=2;
                                      ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                      S110=3;
                                      active[1]=1;
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
                              S427=1;
                              S427=0;
                              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                S427=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S422=0;
                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                  reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                  S422=1;
                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                    S110=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                      switch(S515){
                        case 0 : 
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                            S515=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            switch(S510){
                              case 0 : 
                                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                  reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                  S510=1;
                                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                    ends[1]=2;
                                    ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                    S110=3;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
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
                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                  S110=3;
                                  active[1]=1;
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
                          S515=1;
                          S515=0;
                          if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                            S515=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S510=0;
                            if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                              reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                              S510=1;
                              if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                ends[1]=2;
                                ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                S110=3;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
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
              if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 17, column: 9
                reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                  reportOut_o.setVal("READY");//sysj/RoteryTableController.sysj line: 17, column: 9
                  S1=1;
                  if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 17, column: 9
                    reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 17, column: 9
                    ends[1]=2;
                    ;//sysj/RoteryTableController.sysj line: 17, column: 9
                    S110=1;
                    S28=0;
                    if(!commandIn_in.isPartnerPresent() || commandIn_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 20, column: 9
                      commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                      S28=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S23=0;
                      if(!commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                        commandIn_in.setACK(true);//sysj/RoteryTableController.sysj line: 20, column: 9
                        S23=1;
                        if(commandIn_in.isREQ()){//sysj/RoteryTableController.sysj line: 20, column: 9
                          commandIn_in.setACK(false);//sysj/RoteryTableController.sysj line: 20, column: 9
                          ends[1]=2;
                          ;//sysj/RoteryTableController.sysj line: 20, column: 9
                          command_thread_1 = (String)(commandIn_in.getVal() == null ? null : ((String)commandIn_in.getVal()));//sysj/RoteryTableController.sysj line: 22, column: 9
                          S110=2;
                          if(command_thread_1.startsWith("START|")){//sysj/RoteryTableController.sysj line: 25, column: 9
                            workpieceId_thread_1 = command_thread_1.substring(6);//sysj/RoteryTableController.sysj line: 27, column: 13
                            System.out.println("START received for " + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 29, column: 13
                            S111=0;
                            S118=0;
                            if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 34, column: 13
                              reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                              S118=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S113=0;
                              if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                reportOut_o.setVal("BUSY|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 34, column: 13
                                S113=1;
                                if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 34, column: 13
                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 34, column: 13
                                  ends[1]=2;
                                  ;//sysj/RoteryTableController.sysj line: 34, column: 13
                                  S111=1;
                                  S162=0;
                                  if(!rotate_o.isPartnerPresent() || rotate_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                    rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                    S162=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S157=0;
                                    if(rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                      rotate_o.setVal(true);//sysj/RoteryTableController.sysj line: 40, column: 13
                                      S157=1;
                                      if(!rotate_o.isACK()){//sysj/RoteryTableController.sysj line: 40, column: 13
                                        rotate_o.setREQ(false);//sysj/RoteryTableController.sysj line: 40, column: 13
                                        ends[1]=2;
                                        ;//sysj/RoteryTableController.sysj line: 40, column: 13
                                        S111=2;
                                        S250=0;
                                        if(!aligned_in.isPartnerPresent() || aligned_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                          aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                          S250=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S245=0;
                                          if(!aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                            aligned_in.setACK(true);//sysj/RoteryTableController.sysj line: 44, column: 13
                                            S245=1;
                                            if(aligned_in.isREQ()){//sysj/RoteryTableController.sysj line: 44, column: 13
                                              aligned_in.setACK(false);//sysj/RoteryTableController.sysj line: 44, column: 13
                                              ends[1]=2;
                                              ;//sysj/RoteryTableController.sysj line: 44, column: 13
                                              S111=3;
                                              if((Boolean)(aligned_in.getVal() == null ? null : ((Boolean)aligned_in.getVal()))){//sysj/RoteryTableController.sysj line: 46, column: 16
                                                S531=0;
                                                System.out.println("Table aligned");//sysj/RoteryTableController.sysj line: 48, column: 17
                                                S420=0;
                                                S382=0;
                                                if(!occupancy_in.isPartnerPresent() || occupancy_in.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                  S382=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S377=0;
                                                  if(!occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    occupancy_in.setACK(true);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                    S377=1;
                                                    if(occupancy_in.isREQ()){//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      occupancy_in.setACK(false);//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/RoteryTableController.sysj line: 54, column: 17
                                                      currentOccupancy_thread_1 = (String)(occupancy_in.getVal() == null ? null : ((String)occupancy_in.getVal()));//sysj/RoteryTableController.sysj line: 56, column: 17
                                                      System.out.println("Current occupancy: " + currentOccupancy_thread_1);//sysj/RoteryTableController.sysj line: 58, column: 17
                                                      S420=1;
                                                      S427=0;
                                                      if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                        S427=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S422=0;
                                                        if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          reportOut_o.setVal("DONE|" + workpieceId_thread_1);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                          S422=1;
                                                          if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            ends[1]=2;
                                                            ;//sysj/RoteryTableController.sysj line: 63, column: 17
                                                            S110=3;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                S531=1;
                                                S515=0;
                                                if(!reportOut_o.isPartnerPresent() || reportOut_o.isPartnerPreempted()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                  S515=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S510=0;
                                                  if(reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    reportOut_o.setVal("FAULT|" + workpieceId_thread_1 + "|TABLE_NOT_ALIGNED");//sysj/RoteryTableController.sysj line: 71, column: 17
                                                    S510=1;
                                                    if(!reportOut_o.isACK()){//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      reportOut_o.setREQ(false);//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      ends[1]=2;
                                                      ;//sysj/RoteryTableController.sysj line: 71, column: 17
                                                      S110=3;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
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
                            if(command_thread_1.equals("RESET")) {//sysj/RoteryTableController.sysj line: 79, column: 41
                              System.out.println("RESET received");//sysj/RoteryTableController.sysj line: 81, column: 13
                            }
                            else {//sysj/RoteryTableController.sysj line: 79, column: 14
                              if(command_thread_1.equals("STOP")) {//sysj/RoteryTableController.sysj line: 87, column: 40
                                System.out.println("STOP received");//sysj/RoteryTableController.sysj line: 89, column: 13
                              }
                              else {//sysj/RoteryTableController.sysj line: 95, column: 13
                                System.out.println("Unknown command: " + command_thread_1);//sysj/RoteryTableController.sysj line: 97, column: 13
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
          aligned_in.gethook();
          occupancy_in.gethook();
          reportOut_o.gethook();
          rotate_o.gethook();
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
      aligned_in.sethook();
      occupancy_in.sethook();
      reportOut_o.sethook();
      rotate_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        commandIn_in.gethook();
        aligned_in.gethook();
        occupancy_in.gethook();
        reportOut_o.gethook();
        rotate_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
