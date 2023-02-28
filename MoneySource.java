package com.pedromalavet.pedrosmoneytracker;

public class MoneySource
{

    private String name = "New Source";
    private double money = 0.0;

    public MoneySource() { }

    public MoneySource (String name,double money)
    {
        this.name = name;
        this.money = money;
    }

    public String getName()
    {
        return name;
    }

    public double getMoney()
    {
        return money;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setMoney(double money)
    {
        this.money = money;
    }

}
