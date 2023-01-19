package com.Project1.demo.amd.put1;

import lombok.Data;

import javax.persistence.*;


@Entity
//@Table(name = "call1")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class AMDPut1 {

    @Id
    //@SequenceGenerator(name="call1_sequence",sequenceName = "call1_sequence",allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="call1_sequence")
    //@GeneratedValue(strategy =GenerationType.IDENTITY)
    //@Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    //@Column(name = "contractName")
    private String contractName;

    //@Column(name = "bufferForBuyer")
    private float bufferForBuyer;

    //@Column(name = "bufferForSeller")
    private float bufferForSeller;

    //@Column(name = "strike")
    private float strike;

    //@Column(name = "bid")
    private float bid;

    //@Column(name = "ask")
    private float ask;

    //@Column(name = "exp")
    private String exp;

    //@Column(name = "identifier")
    //private String identifier;

    //@Column(name = "delta")
    private float delta;

    //@Column(name = "theta")
    private float theta;

    //@Column(name = "vega")
    private float vega;

    //@Column(name = "gamma")
    private float gamma;
    public AMDPut1(){

        contractName="";
        bufferForBuyer=0;
        bufferForSeller=0;
        strike=0;
        bid=0;
        ask=0;
        exp="";
        //identifier="";
        delta=0;
        theta=0;
        vega=0;
        gamma=0;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){return id;}


    public void setBufferForBuyer(float in){this.bufferForBuyer=in;}
    public float getBufferForBuyer(){return bufferForBuyer;}

    public void setBufferForSeller(float in){this.bufferForSeller =in;}
    public float getBufferForSeller(){return bufferForSeller;}
    public void setContractName(String in){this.contractName=in;}
    public String getContractName(){return contractName;}
    public void setStrike(float in){
        this.strike=in;
    }
    public void setBid(float in){
        this.bid=in;
    }
    public void setAsk(float in){
        this.ask = in;
    }
    public void setExp(String in){
        this.exp=in;
    }
    /*public void setIdentifier(){
        this.identifier = "C"+String.valueOf(getStrike())+getExp();
    }*/
    public void setDelta(float in){
        this.delta = in;
    }
    public void setTheta(float in){
        this.theta = in;
    }
    public void setVega(float in){
        this.vega=in;
    }
    public void setGamma(float in){
        this.gamma = in;
    }



    public double getStrike(){
        return strike;
    }
    public String getExp(){
        return exp;
    }
    /*
    public String getIdentifier(){
        return identifier;
    }
    */






}
