using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.factory;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.util;
using System;

public class SocketRequest : EzyLoggable
{
	private static readonly SocketRequest INSTANCE = new SocketRequest();

	public static SocketRequest GetInstance()
	{
		return INSTANCE;
	}
	public void sendAppAccessRequest()
	{
		var client = SocketProxy.GetInstance().Client;
		var request = new EzyAppAccessRequest(SocketProxy.APP_NAME);
		client.send(request);
	}
	public void sendSpinRequest()
	{
		var client = SocketProxy.GetInstance().Client;
		var app = client.getApp();
		app?.send("spin");
	}
	public void sendUpdateBalanceRequest()
	{
		var client = SocketProxy.GetInstance().Client;
		var app = client.getApp();
		app?.send("updateBalance");
	}
	public void sendGetTimeRequest()
    {
		var client = SocketProxy.GetInstance().Client;
		var app = client.getApp();
		app?.send("getTime");
	}
	public void sendBetRequest(float betAmount, int side)
    {
		var client = SocketProxy.GetInstance().Client;
		EzyObject data = EzyEntityFactory
			.newObjectBuilder()
			.append("amount", betAmount)
			.append("side", side)
			.build();
		client.getApp().send("bet", data);
	}

	public void sendBetListRequest()
    {
		var client = SocketProxy.GetInstance().Client;
		var app = client.getApp();
		app?.send("betList");
	}

	public void sendChatRequest(string content)
	{
		var client = SocketProxy.GetInstance().Client;
		EzyObject data = EzyEntityFactory
			.newObjectBuilder()
			.append("content", content)
			.build();
		client.getApp().send("chat", data);
	}

	public void sendChatListRequest()
	{
		var client = SocketProxy.GetInstance().Client;
		var app = client.getApp();
		app?.send("chatList");
	}

	public void sendAddBalanceByGiftCardRequest(string serial, string code)
	{
		var client = SocketProxy.GetInstance().Client;
		EzyObject data = EzyEntityFactory
			.newObjectBuilder()
			.append("serial", serial)
			.append("code", code)
			.build();
		client.getApp().send("addBalanceByGiftCard", data);
	}
}
