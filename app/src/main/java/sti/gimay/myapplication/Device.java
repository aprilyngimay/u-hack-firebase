package sti.gimay.myapplication;

public class Device {

    int Switch = 0;
    int billinglvlwaterHIGH = 0;
    int billinglvlwaterLOW = 0;
    int waterLevel = 0;
    int ledStatus = 0;

    public Device(){

    }

    public int getWaterHigh(){
        return billinglvlwaterHIGH;
    }
    public int getWaterLow(){
        return billinglvlwaterLOW;
    }
    public int getWaterLevel(){
        return waterLevel;
    }
    public int getLedStatus(){
        return waterLevel;
    }
}
