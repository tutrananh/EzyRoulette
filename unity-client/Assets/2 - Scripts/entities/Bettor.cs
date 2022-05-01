using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bettor 
{
    private string username;
    private float betAmount;

    public Bettor(string username, float betAmount)
    {
        this.username = username;
        this.betAmount = betAmount;
    }

    public string Username { get => username; set => username = value; }
    public float BetAmount { get => betAmount; set => betAmount = value; }
    
}
