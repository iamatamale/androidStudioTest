package com.example.androidstudiotest;

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

    public Wheel(WheelListener wheelListener, long frameDuration, long start){
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.start = start;
        currentIndex = 0;
        isStarted = true;
    }
    public void nextImg(){
        currentIndex++;

        if(currentIndex == imgs.length){
            currentIndex = 0;
        }
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
            }
        }
    }
    public void stopWheel(){
        isStarted = false;
    }
}
