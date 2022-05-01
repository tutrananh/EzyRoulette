using System;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameController : MonoBehaviour
{
	[SerializeField]
	protected RotateWheel rotateWheel;


	private void Awake()
	{
		GameUI.INSTANCE.Username.text = SocketProxy.GetInstance().UserAuthenInfo.Username;
		getBalance();
		getTime();
		InvokeRepeating("getBetList", 1, 1);
		InvokeRepeating("getChatList", 1, 1);
		SpinResponseHandler.spinResponseEvent += OnSpinResponse;
		BalanceResponseHandler.balanceResponseEvent += OnBalanceResponse;
		TimerResponseHandler.timerResponseEvent += OnTimerResponse;
		BetResponseHandler.betResponseEvent += OnBetResponse;
		BetListResponseHandler.betListResponseEvent += OnBetListResponse;
		ChatResponseHandler.chatResponseEvent += OnChatRespone;
		ChatListResponseHandler.chatListResponseEvent += OnChatListRespone;
	}

    private void OnChatListRespone(List<string> contents)
    {
		GameUI.INSTANCE.updateChatBox(contents);
    }

    private void OnChatRespone(string obj)
    {
    }

    private void OnBetListResponse(List<string> redBettors, List<string> greenBettors)
    {
		GameUI.INSTANCE.RedBettorsText.text = "";
		GameUI.INSTANCE.GreenBettorsText.text = "";
		foreach (string bettor in redBettors) {
			GameUI.INSTANCE.RedBettorsText.text += bettor + "\n";
		}
		foreach (string bettor in greenBettors)
		{
			GameUI.INSTANCE.GreenBettorsText.text += bettor + "\n";
		}
	}

    private void OnBalanceResponse(float balance)
    {
		GameUI.INSTANCE.BalanceText.text = "Balance: " + balance.ToString();
	}

    private void OnBetResponse(float betAmount)
    {
		Debug.Log(betAmount);
		getBalance();
	}

    private void OnTimerResponse(int time)
    {
		if (Int32.Parse(GameUI.INSTANCE.Timer.text) != time)
        {
			GameUI.INSTANCE.Spinned = false;
			GameUI.INSTANCE.CurrentTime = time;
		}		
	}

    private void OnSpinResponse(int result)
	{
		rotateWheel.Activate(result);
	}

	public void Spin()
	{
		SocketRequest.GetInstance().sendSpinRequest();
	}

    public void getTime()
    {
		SocketRequest.GetInstance().sendGetTimeRequest();
	}
	public void getBalance()
    {
		SocketRequest.GetInstance().sendUpdateBalanceRequest();
	}
    public void BetOnRed()
    {
		if (Int32.Parse(GameUI.INSTANCE.Timer.text) <= 3)
		{
			Debug.Log("You cannot bet now!!");
		}
        else
        {
			GameUI.INSTANCE.BetOnRedBtn.interactable = false;
			SocketRequest.GetInstance().sendBetRequest(float.Parse(GameUI.INSTANCE.BetInputField.text), 0);
		}
		GameUI.INSTANCE.BetInputField.text = "";
	}
	public void BetOnGreen()
	{
		if (Int32.Parse(GameUI.INSTANCE.Timer.text) <= 3)
		{
			Debug.Log("You cannot bet now!!");
		}
		else
		{
			GameUI.INSTANCE.BetOnGreenBtn.interactable = false;
			SocketRequest.GetInstance().sendBetRequest(float.Parse(GameUI.INSTANCE.BetInputField.text), 1);
		}
		GameUI.INSTANCE.BetInputField.text = "";
	}
	public void getBetList()
    {
		SocketRequest.GetInstance().sendBetListRequest();

	}

	public void Chat()
	{
		SocketRequest.GetInstance().sendChatRequest(GameUI.INSTANCE.ChatInputField.text);
		GameUI.INSTANCE.ChatInputField.text = "";
	}

	public void getChatList()
	{
		SocketRequest.GetInstance().sendChatListRequest();

	}
	private void Update()
	{
		if (GameUI.INSTANCE.CurrentTime >= 0)
		{
			GameUI.INSTANCE.CurrentTime -= Time.deltaTime;
			GameUI.INSTANCE.Timer.text = Mathf.RoundToInt(GameUI.INSTANCE.CurrentTime).ToString();

		}
		if (GameUI.INSTANCE.CurrentTime < 0 && !GameUI.INSTANCE.Spinned)
		{
			Spin();
			GameUI.INSTANCE.Spinned = true;
		}
		if (GameUI.INSTANCE.Spinned)
		{
			GameUI.INSTANCE.DelayToNewRoundTime -= Time.deltaTime;
            if (GameUI.INSTANCE.DelayToNewRoundTime < 0)
            {
				getTime();
				getBalance();
				GameUI.INSTANCE.DelayToNewRoundTime = 10f;
				GameUI.INSTANCE.BetOnGreenBtn.interactable = true;
				GameUI.INSTANCE.BetOnRedBtn.interactable = true;
			}
			
		}
	}
}
