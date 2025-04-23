package com.example.androidstudiotest;

import java.util.Random;

public class Wheel extends Thread{
    interface WheelListener {
        void NewImage(int img);
    }

    private static int[] imgs = {R.drawable.slot1,R.drawable.slot2,R.drawable.slot3,R.drawable.slot4,R.drawable.slot5,R.drawable.slot6};
    public int currentIndex;
    private WheelListener wheelListener;
    private long frameDuration;
    private long start;
    private boolean isStarted;
    private Random random = new Random();
    private int previousIndex = -1;
    public int finalImageIndex;
    public boolean isStopped;

    public Wheel(WheelListener wheelListener, long frameDuration, long start){
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.start = start;
        currentIndex = 0;
        isStarted = true;
    }
    public void nextImg(){
        int newIndex;
        do {
            newIndex = random.nextInt(imgs.length);
        } while (newIndex == previousIndex);

        previousIndex = newIndex;
        currentIndex = newIndex;
    }
    public void run(){
        try{
            Thread.sleep(start);
        } catch (InterruptedException e){

        }

        while(isStarted){
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {

            }
            nextImg();
            if(wheelListener != null){
                wheelListener.NewImage(imgs[currentIndex]);
                finalImageIndex = currentIndex;
            }
        }
        isStopped = true;
    }
    public void stopWheel(){
        isStarted = false;
    }
}
