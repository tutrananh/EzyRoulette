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
		GetBalance();
		GetTime();
		SpinResponseHandler.spinResponseEvent += OnSpinResponse;
		BalanceResponseHandler.balanceResponseEvent += OnBalanceResponse;
		TimerResponseHandler.timerResponseEvent += OnTimerResponse;
		BetResponseHandler.betResponseEvent += OnBetResponse;
		BetListResponseHandler.betListResponseEvent += OnBetListResponse;
		ChatResponseHandler.chatResponseEvent += OnChatRespone;
		ChatListResponseHandler.chatListResponseEvent += OnChatListRespone;
		AddByGiftCardResponseHandler.addBalanceByGiftCardResponseEvent += OnAddBalanceByGiftCardResponse;
	}

    private void OnAddBalanceByGiftCardResponse(int amount)
    {
		ShopUI.INSTANCE.OnClickAddBalanceByGiftCard(amount);
    }

    private void OnChatListRespone(List<string> contents)
    {
		GameUI.INSTANCE.updateChatBox(contents);
		GetBalance();
	}

    private void OnChatRespone(string obj)
    {
		GetChatList();
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
		GameUI.INSTANCE.BalanceText.text = balance.ToString();
	}

    private void OnBetResponse(float betAmount)
    {
		GetBetList();
		GetBalance();
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

    public void GetTime()
    {
		SocketRequest.GetInstance().sendGetTimeRequest();
	}
	public void GetBalance()
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
	public void GetBetList()
    {
		SocketRequest.GetInstance().sendBetListRequest();

	}

	public void Chat()
	{
		SocketRequest.GetInstance().sendChatRequest(GameUI.INSTANCE.ChatInputField.text);
		GameUI.INSTANCE.ChatInputField.text = "";
	}

	public void GetChatList()
	{
		SocketRequest.GetInstance().sendChatListRequest();

	}

	public void AddBalanceByGiftCard()
    {
		RestAddBalanceRequest.INSTANCE.SendAddBalanceByGiftCardRequestToHttpServer(GameUI.INSTANCE.Username.text, ShopUI.INSTANCE.Serial.text, ShopUI.INSTANCE.Code.text);
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
				GetBetList();
				GetTime();
				GetBalance();
				GameUI.INSTANCE.DelayToNewRoundTime = 10f;
				GameUI.INSTANCE.BetOnGreenBtn.interactable = true;
				GameUI.INSTANCE.BetOnRedBtn.interactable = true;
			}
			
		}
	}
}
