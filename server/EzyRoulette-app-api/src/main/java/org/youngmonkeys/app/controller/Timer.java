package org.youngmonkeys.app.controller;


import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import lombok.Data;
import org.youngmonkeys.app.entity.Bettor;
import org.youngmonkeys.app.entity.Result;
import org.youngmonkeys.app.service.GiftCardService;
import org.youngmonkeys.app.service.ResultService;
import org.youngmonkeys.app.service.WheelService;
import org.youngmonkeys.common.service.UserService;

import java.util.ArrayList;
import java.util.List;

@EzyRequestController
public class Timer implements java.lang.Runnable{

    @EzyAutoBind
    private static WheelService wheelService;

    @EzyAutoBind
    private static ResultService resultService;

    @EzyAutoBind
    private static UserService userService;

    @EzyAutoBind
    private static GiftCardService giftCardService;


    private static int timeInARound = 30;
    private static int time;
    private static int result=0;


    private static int maxChatLog = 10;

    private static List<Bettor> redBettors = new ArrayList<>();
    private static List<Bettor> greenBettors = new ArrayList<>();
    private static List<String> chatContents = new ArrayList<>();

    public static int getMaxChatLog() {
        return maxChatLog;
    }
    public static int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static int getResult() {
        return result;
    }

    public static void setResult(int result) {
        Timer.result = result;
    }

    public static List<Bettor> getRedBettors() {
        return redBettors;
    }

    public static void setRedBettors(List<Bettor> redBettors) {
        Timer.redBettors = redBettors;
    }

    public static List<Bettor> getGreenBettors() {
        return greenBettors;
    }

    public static void setGreenBettors(List<Bettor> greenBettors) {
        Timer.greenBettors = greenBettors;
    }

    public static List<String> getChatContents() {
        return chatContents;
    }

    public static void setChatContents(List<String> chatContents) {
        Timer.chatContents = chatContents;
    }

    @Override
    public void run() {
        this.runTimer();
    }

    public void runTimer(){
        int i = timeInARound;
        while (i>0){
            System.out.println("Time: "+i+" seconds");
            setTime(i);
            try {
                i--;
                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
                if(i<=0){
                    setResult(wheelService.spin());
                    Result r = resultService.createResult(getResult());
                    System.out.println("result: "+getResult());
                    updateBalance();
                    redBettors.removeAll(redBettors);
                    greenBettors.removeAll(greenBettors);
                    Thread.sleep(10000L);
                    i=timeInARound;
                }
            }
            catch (InterruptedException e) {
            }
        }
    }
    public void updateBalance(){
        if(getResult()%2==0){
            for (Bettor b:redBettors) {
                userService.updateUser(b.getUsername(),b.getBetAmount()*2);
            }

        }
        else{
            for (Bettor b:greenBettors) {
                userService.updateUser(b.getUsername(),b.getBetAmount()*2);
            }
        }
    }

}
