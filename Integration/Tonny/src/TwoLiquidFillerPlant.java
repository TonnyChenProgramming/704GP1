import java.util.*;
import com.systemj.ClockDomain;
import com.systemj.Signal;
import com.systemj.input_Channel;
import com.systemj.output_Channel;

public class TwoLiquidFillerPlant extends ClockDomain{
  public TwoLiquidFillerPlant(String name){super(name);}
  Vector currsigs = new Vector();
  private boolean df = false;
  private char [] active;
  private char [] paused;
  private char [] suspended;
  public input_Channel dose1_in = new input_Channel();
  public input_Channel dose2_in = new input_Channel();
  public output_Channel bottlePresent_o = new output_Channel();
  public output_Channel dose1Done_o = new output_Channel();
  public output_Channel dose2Done_o = new output_Channel();
  public output_Channel overflow_o = new output_Channel();
  private int currentLevel_thread_1;//sysj/TwoLiquidFillerPlant.sysj line: 17, column: 5
  private long __start_thread_1;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
  private int S10723 = 1;
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
  private int S1111 = 1;
  private int S1073 = 1;
  private int S1068 = 1;
  private int S1095 = 1;
  private int S1090 = 1;
  
  private int[] ends = new int[2];
  private int[] tdone = new int[2];
  
  public void runClockDomain(){
    for(int i=0;i<ends.length;i++){
      ends[i] = 0;
      tdone[i] = 0;
    }
    
    RUN: while(true){
      switch(S10723){
        case 0 : 
          S10723=0;
          break RUN;
        
        case 1 : 
          S10723=2;
          S10723=2;
          currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 17, column: 5
          S110=0;
          S6=0;
          if(!bottlePresent_o.isPartnerPresent() || bottlePresent_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
            bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
            S6=1;
            active[1]=1;
            ends[1]=1;
            break RUN;
          }
          else {
            S1=0;
            if(bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
              bottlePresent_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
              S1=1;
              if(!bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                ends[1]=2;
                ;//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                S110=1;
                S28=0;
                if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                  dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                  S28=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S23=0;
                  if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                    dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                    S23=1;
                    if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      ends[1]=2;
                      ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                      System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                      S110=2;
                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                      S112=0;
                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                        S110=3;
                        S201=0;
                        if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          S201=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S196=0;
                          if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            S196=1;
                            if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              S110=4;
                              S377=0;
                              if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                S377=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S372=0;
                                if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  S372=1;
                                  if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                    System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                    S110=5;
                                    __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                    if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                      S110=6;
                                      S765=0;
                                      if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        S765=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S760=0;
                                        if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          S760=1;
                                          if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            S110=7;
                                            if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                              S1111=0;
                                              S1073=0;
                                              if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                S1073=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1068=0;
                                                if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  S1068=1;
                                                  if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                    S110=8;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                            }
                                            else {
                                              S1111=1;
                                              S1095=0;
                                              if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                S1095=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S1090=0;
                                                if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  S1090=1;
                                                  if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                    S110=8;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
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
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
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
                  if(!bottlePresent_o.isPartnerPresent() || bottlePresent_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S1){
                      case 0 : 
                        if(bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                          bottlePresent_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                          S1=1;
                          if(!bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                            bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                            S110=1;
                            S28=0;
                            if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              S28=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S23=0;
                              if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                S23=1;
                                if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                  dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                  currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                                  System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                                  S110=2;
                                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  S112=0;
                                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                    S110=3;
                                    S201=0;
                                    if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      S201=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S196=0;
                                      if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                        dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                        S196=1;
                                        if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                          dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                          S110=4;
                                          S377=0;
                                          if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            S377=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S372=0;
                                            if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                              dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                              S372=1;
                                              if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                                dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                                currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                                System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                                S110=5;
                                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                                  S110=6;
                                                  S765=0;
                                                  if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    S765=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S760=0;
                                                    if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                      dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                      S760=1;
                                                      if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                        dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                        S110=7;
                                                        if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                          S1111=0;
                                                          S1073=0;
                                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            S1073=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S1068=0;
                                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                              overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                              S1068=1;
                                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                                S110=8;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                        }
                                                        else {
                                                          S1111=1;
                                                          S1095=0;
                                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            S1095=1;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            S1090=0;
                                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                              overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                              S1090=1;
                                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                                ends[1]=2;
                                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                                S110=8;
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
                                                              }
                                                              else {
                                                                active[1]=1;
                                                                ends[1]=1;
                                                                break RUN;
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
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
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
                        if(!bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                          bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                          S110=1;
                          S28=0;
                          if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            S28=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S23=0;
                            if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              S23=1;
                              if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                                currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                                System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                                S110=2;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                S112=0;
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  S110=3;
                                  S201=0;
                                  if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    S201=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S196=0;
                                    if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      S196=1;
                                      if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                        dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                        S110=4;
                                        S377=0;
                                        if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          S377=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S372=0;
                                          if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            S372=1;
                                            if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                              dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                              currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                              System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                              S110=5;
                                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                                S110=6;
                                                S765=0;
                                                if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  S765=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S760=0;
                                                  if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    S760=1;
                                                    if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                      dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                      S110=7;
                                                      if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                        S1111=0;
                                                        S1073=0;
                                                        if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          S1073=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1068=0;
                                                          if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            S1068=1;
                                                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                              S110=8;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                      }
                                                      else {
                                                        S1111=1;
                                                        S1095=0;
                                                        if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          S1095=1;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          S1090=0;
                                                          if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            S1090=1;
                                                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                              ends[1]=2;
                                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                              S110=8;
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
                                                            }
                                                            else {
                                                              active[1]=1;
                                                              ends[1]=1;
                                                              break RUN;
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
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
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
                  if(!bottlePresent_o.isPartnerPresent() || bottlePresent_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    S6=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S1=0;
                    if(bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                      bottlePresent_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                      S1=1;
                      if(!bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                        bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                        S110=1;
                        S28=0;
                        if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          S28=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S23=0;
                          if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            S23=1;
                            if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                              currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                              System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                              S110=2;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                              S112=0;
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                S110=3;
                                S201=0;
                                if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  S201=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S196=0;
                                  if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    S196=1;
                                    if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                      S110=4;
                                      S377=0;
                                      if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        S377=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S372=0;
                                        if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          S372=1;
                                          if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                            currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                            System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                            S110=5;
                                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                              S110=6;
                                              S765=0;
                                              if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                S765=1;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                S760=0;
                                                if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  S760=1;
                                                  if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    ends[1]=2;
                                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                    S110=7;
                                                    if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                      S1111=0;
                                                      S1073=0;
                                                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        S1073=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1068=0;
                                                        if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          S1068=1;
                                                          if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                            currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                            S110=8;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                    }
                                                    else {
                                                      S1111=1;
                                                      S1095=0;
                                                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        S1095=1;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        S1090=0;
                                                        if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          S1090=1;
                                                          if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            ends[1]=2;
                                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                            currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                            S110=8;
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
                                                          }
                                                          else {
                                                            active[1]=1;
                                                            ends[1]=1;
                                                            break RUN;
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
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
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
                  if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                    dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S23){
                      case 0 : 
                        if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          S23=1;
                          if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                            currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                            System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                            S110=2;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            S112=0;
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                              S110=3;
                              S201=0;
                              if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                S201=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S196=0;
                                if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  S196=1;
                                  if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                    S110=4;
                                    S377=0;
                                    if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      S377=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S372=0;
                                      if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        S372=1;
                                        if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                          currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                          System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                          S110=5;
                                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                            S110=6;
                                            S765=0;
                                            if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              S765=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S760=0;
                                              if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                S760=1;
                                                if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                  S110=7;
                                                  if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                    S1111=0;
                                                    S1073=0;
                                                    if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      S1073=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1068=0;
                                                      if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        S1068=1;
                                                        if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                          currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                          S110=8;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                  }
                                                  else {
                                                    S1111=1;
                                                    S1095=0;
                                                    if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      S1095=1;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      S1090=0;
                                                      if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        S1090=1;
                                                        if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          ends[1]=2;
                                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                          currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                          S110=8;
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
                                                        }
                                                        else {
                                                          active[1]=1;
                                                          ends[1]=1;
                                                          break RUN;
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
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
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
                        if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                          System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                          S110=2;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          S112=0;
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            S110=3;
                            S201=0;
                            if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              S201=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S196=0;
                              if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                S196=1;
                                if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  S110=4;
                                  S377=0;
                                  if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    S377=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S372=0;
                                    if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      S372=1;
                                      if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                        System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                        S110=5;
                                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                          S110=6;
                                          S765=0;
                                          if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            S765=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S760=0;
                                            if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              S760=1;
                                              if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                S110=7;
                                                if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                  S1111=0;
                                                  S1073=0;
                                                  if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    S1073=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1068=0;
                                                    if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      S1068=1;
                                                      if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                        S110=8;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  S1111=1;
                                                  S1095=0;
                                                  if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    S1095=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1090=0;
                                                    if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      S1090=1;
                                                      if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                        S110=8;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
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
                  if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                    dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                    S28=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S23=0;
                    if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      S23=1;
                      if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                        dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                        currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                        System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                        S110=2;
                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                        S112=0;
                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          S110=3;
                          S201=0;
                          if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            S201=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S196=0;
                            if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              S196=1;
                              if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                S110=4;
                                S377=0;
                                if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  S377=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S372=0;
                                  if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    S372=1;
                                    if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                      System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                      S110=5;
                                      __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                      if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                        S110=6;
                                        S765=0;
                                        if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          S765=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S760=0;
                                          if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            S760=1;
                                            if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              S110=7;
                                              if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                S1111=0;
                                                S1073=0;
                                                if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  S1073=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1068=0;
                                                  if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    S1068=1;
                                                    if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                      S110=8;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                  else {
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                }
                                              }
                                              else {
                                                S1111=1;
                                                S1095=0;
                                                if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  S1095=1;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  S1090=0;
                                                  if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    S1090=1;
                                                    if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      ends[1]=2;
                                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                      S110=8;
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
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
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
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
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                    ends[1]=2;
                    ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                    S110=3;
                    S201=0;
                    if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                      dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                      S201=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S196=0;
                      if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        S196=1;
                        if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          S110=4;
                          S377=0;
                          if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            S377=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S372=0;
                            if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              S372=1;
                              if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                S110=5;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  S110=6;
                                  S765=0;
                                  if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    S765=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S760=0;
                                    if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      S760=1;
                                      if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        S110=7;
                                        if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                          S1111=0;
                                          S1073=0;
                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            S1073=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1068=0;
                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              S1068=1;
                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                S110=8;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S1111=1;
                                          S1095=0;
                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            S1095=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1090=0;
                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              S1090=1;
                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                S110=8;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
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
                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                    ends[1]=2;
                    ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                    S110=3;
                    S201=0;
                    if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                      dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                      S201=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S196=0;
                      if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        S196=1;
                        if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          S110=4;
                          S377=0;
                          if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            S377=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S372=0;
                            if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              S372=1;
                              if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                S110=5;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  S110=6;
                                  S765=0;
                                  if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    S765=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S760=0;
                                    if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      S760=1;
                                      if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        S110=7;
                                        if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                          S1111=0;
                                          S1073=0;
                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            S1073=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1068=0;
                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              S1068=1;
                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                S110=8;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S1111=1;
                                          S1095=0;
                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            S1095=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1090=0;
                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              S1090=1;
                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                S110=8;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
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
                  if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                    dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                    S201=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S196){
                      case 0 : 
                        if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          S196=1;
                          if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                            S110=4;
                            S377=0;
                            if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              S377=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S372=0;
                              if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                S372=1;
                                if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                  currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                  System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                  S110=5;
                                  __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                    S110=6;
                                    S765=0;
                                    if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      S765=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S760=0;
                                      if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        S760=1;
                                        if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                          S110=7;
                                          if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                            S1111=0;
                                            S1073=0;
                                            if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              S1073=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1068=0;
                                              if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                S1068=1;
                                                if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                  currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                  S110=8;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                          }
                                          else {
                                            S1111=1;
                                            S1095=0;
                                            if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              S1095=1;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              S1090=0;
                                              if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                S1090=1;
                                                if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  ends[1]=2;
                                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                  currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                  S110=8;
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
                                                }
                                                else {
                                                  active[1]=1;
                                                  ends[1]=1;
                                                  break RUN;
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
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            active[1]=1;
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
                        if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                          S110=4;
                          S377=0;
                          if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            S377=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S372=0;
                            if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              S372=1;
                              if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                S110=5;
                                __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                  S110=6;
                                  S765=0;
                                  if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    S765=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S760=0;
                                    if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      S760=1;
                                      if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                        S110=7;
                                        if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                          S1111=0;
                                          S1073=0;
                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            S1073=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1068=0;
                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              S1068=1;
                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                S110=8;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                        }
                                        else {
                                          S1111=1;
                                          S1095=0;
                                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            S1095=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S1090=0;
                                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              S1090=1;
                                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                S110=8;
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
                                              }
                                              else {
                                                active[1]=1;
                                                ends[1]=1;
                                                break RUN;
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
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
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
                  if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                    dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                    S201=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S196=0;
                    if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                      dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                      S196=1;
                      if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                        S110=4;
                        S377=0;
                        if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          S377=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S372=0;
                          if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            S372=1;
                            if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                              currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                              System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                              S110=5;
                              __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                S110=6;
                                S765=0;
                                if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  S765=1;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  S760=0;
                                  if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    S760=1;
                                    if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      ends[1]=2;
                                      ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                      S110=7;
                                      if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                        S1111=0;
                                        S1073=0;
                                        if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          S1073=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1068=0;
                                          if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            S1068=1;
                                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                              S110=8;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                      }
                                      else {
                                        S1111=1;
                                        S1095=0;
                                        if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          S1095=1;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          S1090=0;
                                          if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            S1090=1;
                                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              ends[1]=2;
                                              ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                              S110=8;
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
                                            }
                                            else {
                                              active[1]=1;
                                              ends[1]=1;
                                              break RUN;
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
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                      }
                      else {
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
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
                  if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                    dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                    S377=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S372){
                      case 0 : 
                        if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          S372=1;
                          if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                            currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                            System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                            S110=5;
                            __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                              S110=6;
                              S765=0;
                              if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                S765=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S760=0;
                                if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  S760=1;
                                  if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                    S110=7;
                                    if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                      S1111=0;
                                      S1073=0;
                                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        S1073=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S1068=0;
                                        if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          S1068=1;
                                          if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                            currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                            S110=8;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                    }
                                    else {
                                      S1111=1;
                                      S1095=0;
                                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        S1095=1;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        S1090=0;
                                        if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          S1090=1;
                                          if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            ends[1]=2;
                                            ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                            currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                            S110=8;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
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
                        else {
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                      
                      case 1 : 
                        if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                          currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                          System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                          S110=5;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            S110=6;
                            S765=0;
                            if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                              dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                              S765=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S760=0;
                              if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                S760=1;
                                if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                  S110=7;
                                  if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                    S1111=0;
                                    S1073=0;
                                    if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                      overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                      S1073=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S1068=0;
                                      if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        S1068=1;
                                        if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                          currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                          S110=8;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                  }
                                  else {
                                    S1111=1;
                                    S1095=0;
                                    if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                      overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                      S1095=1;
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                    else {
                                      S1090=0;
                                      if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        S1090=1;
                                        if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                          currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                          S110=8;
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
                                        }
                                        else {
                                          active[1]=1;
                                          ends[1]=1;
                                          break RUN;
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
                  S377=1;
                  S377=0;
                  if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                    dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                    S377=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S372=0;
                    if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                      dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                      S372=1;
                      if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                        dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                        currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                        System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                        S110=5;
                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          S110=6;
                          S765=0;
                          if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                            dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                            S765=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S760=0;
                            if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                              dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                              S760=1;
                              if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                S110=7;
                                if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                  S1111=0;
                                  S1073=0;
                                  if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                    S1073=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1068=0;
                                    if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                      overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                      S1068=1;
                                      if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                        currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                        S110=8;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                    }
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  S1111=1;
                                  S1095=0;
                                  if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                    S1095=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S1090=0;
                                    if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                      overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                      S1090=1;
                                      if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                        currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                        S110=8;
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
                                      }
                                      else {
                                        active[1]=1;
                                        ends[1]=1;
                                        break RUN;
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
                    else {
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                  }
                
              }
              break;
            
            case 5 : 
              if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                ends[1]=2;
                ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                S110=6;
                S765=0;
                if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                  dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                  S765=1;
                  active[1]=1;
                  ends[1]=1;
                  break RUN;
                }
                else {
                  S760=0;
                  if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                    dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                    S760=1;
                    if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                      dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                      ends[1]=2;
                      ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                      S110=7;
                      if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                        S1111=0;
                        S1073=0;
                        if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                          S1073=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S1068=0;
                          if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            S1068=1;
                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                              S110=8;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                        }
                      }
                      else {
                        S1111=1;
                        S1095=0;
                        if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                          overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                          S1095=1;
                          active[1]=1;
                          ends[1]=1;
                          break RUN;
                        }
                        else {
                          S1090=0;
                          if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            S1090=1;
                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                              S110=8;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
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
            
            case 6 : 
              switch(S765){
                case 0 : 
                  if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                    S765=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    switch(S760){
                      case 0 : 
                        if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                          dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                          S760=1;
                          if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                            dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                            S110=7;
                            if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                              S1111=0;
                              S1073=0;
                              if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                S1073=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S1068=0;
                                if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                  overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                  S1068=1;
                                  if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                    currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                    S110=8;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                            }
                            else {
                              S1111=1;
                              S1095=0;
                              if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                S1095=1;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                S1090=0;
                                if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                  overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                  S1090=1;
                                  if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                    ends[1]=2;
                                    ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                    currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                    S110=8;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
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
                        if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                          dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                          S110=7;
                          if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                            S1111=0;
                            S1073=0;
                            if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              S1073=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S1068=0;
                              if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                S1068=1;
                                if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                  overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                  currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                  S110=8;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                          }
                          else {
                            S1111=1;
                            S1095=0;
                            if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              S1095=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S1090=0;
                              if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                S1090=1;
                                if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                  overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                  currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                  S110=8;
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
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
                  S765=1;
                  S765=0;
                  if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                    dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                    S765=1;
                    active[1]=1;
                    ends[1]=1;
                    break RUN;
                  }
                  else {
                    S760=0;
                    if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                      dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                      S760=1;
                      if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                        dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                        ends[1]=2;
                        ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                        S110=7;
                        if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                          S1111=0;
                          S1073=0;
                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            S1073=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S1068=0;
                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              S1068=1;
                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                S110=8;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                            }
                            else {
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                          }
                        }
                        else {
                          S1111=1;
                          S1095=0;
                          if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            S1095=1;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            S1090=0;
                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              S1090=1;
                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                S110=8;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
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
            
            case 7 : 
              switch(S1111){
                case 0 : 
                  switch(S1073){
                    case 0 : 
                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                        S1073=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S1068){
                          case 0 : 
                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              S1068=1;
                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                S110=8;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
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
                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                              S110=8;
                              active[1]=1;
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
                      S1073=1;
                      S1073=0;
                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                        S1073=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S1068=0;
                        if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                          overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                          S1068=1;
                          if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                            currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                            S110=8;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
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
                  switch(S1095){
                    case 0 : 
                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                        S1095=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        switch(S1090){
                          case 0 : 
                            if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              S1090=1;
                              if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                ends[1]=2;
                                ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                S110=8;
                                active[1]=1;
                                ends[1]=1;
                                break RUN;
                              }
                              else {
                                active[1]=1;
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
                            if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              ends[1]=2;
                              ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                              currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                              S110=8;
                              active[1]=1;
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
                      S1095=1;
                      S1095=0;
                      if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                        S1095=1;
                        active[1]=1;
                        ends[1]=1;
                        break RUN;
                      }
                      else {
                        S1090=0;
                        if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                          overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                          S1090=1;
                          if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                            currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                            S110=8;
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
                          }
                          else {
                            active[1]=1;
                            ends[1]=1;
                            break RUN;
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
            
            case 8 : 
              S110=8;
              S110=0;
              S6=0;
              if(!bottlePresent_o.isPartnerPresent() || bottlePresent_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                S6=1;
                active[1]=1;
                ends[1]=1;
                break RUN;
              }
              else {
                S1=0;
                if(bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                  bottlePresent_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                  S1=1;
                  if(!bottlePresent_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    bottlePresent_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    ends[1]=2;
                    ;//sysj/TwoLiquidFillerPlant.sysj line: 21, column: 9
                    S110=1;
                    S28=0;
                    if(!dose1_in.isPartnerPresent() || dose1_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                      S28=1;
                      active[1]=1;
                      ends[1]=1;
                      break RUN;
                    }
                    else {
                      S23=0;
                      if(!dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                        dose1_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                        S23=1;
                        if(dose1_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          dose1_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          ends[1]=2;
                          ;//sysj/TwoLiquidFillerPlant.sysj line: 23, column: 9
                          currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose1_in.getVal() == null ? null : ((Integer)dose1_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 25, column: 9
                          System.out.println("Plant: liquid 1 filled");//sysj/TwoLiquidFillerPlant.sysj line: 27, column: 9
                          S110=2;
                          __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                          S112=0;
                          if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            ends[1]=2;
                            ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                            S110=3;
                            S201=0;
                            if(!dose1Done_o.isPartnerPresent() || dose1Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                              S201=1;
                              active[1]=1;
                              ends[1]=1;
                              break RUN;
                            }
                            else {
                              S196=0;
                              if(dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                dose1Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                S196=1;
                                if(!dose1Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  dose1Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  ends[1]=2;
                                  ;//sysj/TwoLiquidFillerPlant.sysj line: 33, column: 9
                                  S110=4;
                                  S377=0;
                                  if(!dose2_in.isPartnerPresent() || dose2_in.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                    S377=1;
                                    active[1]=1;
                                    ends[1]=1;
                                    break RUN;
                                  }
                                  else {
                                    S372=0;
                                    if(!dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      dose2_in.setACK(true);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                      S372=1;
                                      if(dose2_in.isREQ()){//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        dose2_in.setACK(false);//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        ends[1]=2;
                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 36, column: 9
                                        currentLevel_thread_1 = currentLevel_thread_1 + (Integer)(dose2_in.getVal() == null ? null : ((Integer)dose2_in.getVal()));//sysj/TwoLiquidFillerPlant.sysj line: 38, column: 9
                                        System.out.println("Plant: liquid 2 filled");//sysj/TwoLiquidFillerPlant.sysj line: 40, column: 9
                                        S110=5;
                                        __start_thread_1 = com.systemj.Timer.getMs();//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                        if(com.systemj.Timer.getMs() - __start_thread_1 >= 500){//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                          ends[1]=2;
                                          ;//sysj/TwoLiquidFillerPlant.sysj line: 19, column: 5
                                          S110=6;
                                          S765=0;
                                          if(!dose2Done_o.isPartnerPresent() || dose2Done_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                            S765=1;
                                            active[1]=1;
                                            ends[1]=1;
                                            break RUN;
                                          }
                                          else {
                                            S760=0;
                                            if(dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              dose2Done_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                              S760=1;
                                              if(!dose2Done_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                dose2Done_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                ends[1]=2;
                                                ;//sysj/TwoLiquidFillerPlant.sysj line: 46, column: 9
                                                S110=7;
                                                if(currentLevel_thread_1 > 100){//sysj/TwoLiquidFillerPlant.sysj line: 49, column: 12
                                                  S1111=0;
                                                  S1073=0;
                                                  if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                    S1073=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1068=0;
                                                    if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      overflow_o.setVal(true);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                      S1068=1;
                                                      if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 51, column: 13
                                                        currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                        S110=8;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                    }
                                                    else {
                                                      active[1]=1;
                                                      ends[1]=1;
                                                      break RUN;
                                                    }
                                                  }
                                                }
                                                else {
                                                  S1111=1;
                                                  S1095=0;
                                                  if(!overflow_o.isPartnerPresent() || overflow_o.isPartnerPreempted()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                    S1095=1;
                                                    active[1]=1;
                                                    ends[1]=1;
                                                    break RUN;
                                                  }
                                                  else {
                                                    S1090=0;
                                                    if(overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      overflow_o.setVal(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                      S1090=1;
                                                      if(!overflow_o.isACK()){//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        overflow_o.setREQ(false);//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        ends[1]=2;
                                                        ;//sysj/TwoLiquidFillerPlant.sysj line: 57, column: 13
                                                        currentLevel_thread_1 = 0;//sysj/TwoLiquidFillerPlant.sysj line: 61, column: 9
                                                        S110=8;
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
                                                      }
                                                      else {
                                                        active[1]=1;
                                                        ends[1]=1;
                                                        break RUN;
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
                                    else {
                                      active[1]=1;
                                      ends[1]=1;
                                      break RUN;
                                    }
                                  }
                                }
                                else {
                                  active[1]=1;
                                  ends[1]=1;
                                  break RUN;
                                }
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
          dose1_in.gethook();
          dose2_in.gethook();
          bottlePresent_o.gethook();
          dose1Done_o.gethook();
          dose2Done_o.gethook();
          overflow_o.gethook();
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
      dose1_in.sethook();
      dose2_in.sethook();
      bottlePresent_o.sethook();
      dose1Done_o.sethook();
      dose2Done_o.sethook();
      overflow_o.sethook();
      if(paused[1]!=0 || suspended[1]!=0 || active[1]!=1);
      else{
        dose1_in.gethook();
        dose2_in.gethook();
        bottlePresent_o.gethook();
        dose1Done_o.gethook();
        dose2Done_o.gethook();
        overflow_o.gethook();
      }
      runFinisher();
      if(active[1] == 0){
      	this.terminated = true;
      }
      if(!threaded) break;
    }
  }
}
