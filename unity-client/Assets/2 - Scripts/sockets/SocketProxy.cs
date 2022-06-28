using System;
using System.Collections.Generic;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.config;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.factory;
using com.tvd12.ezyfoxserver.client.handler;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.util;
using UnityEngine;

class HandshakeHandler : EzyHandshakeHandler
{
	protected override EzyRequest getLoginRequest()
	{


		return new EzyLoginRequest(
			SocketProxy.ZONE_NAME,
			SocketProxy.GetInstance().UserAuthenInfo.Username,
			SocketProxy.GetInstance().UserAuthenInfo.Password,
			SocketProxy.GetInstance().Data
		);
	}
}

class LoginSuccessHandler : EzyLoginSuccessHandler
{
	protected override void handleLoginSuccess(EzyData responseData)
	{
		logger.debug("Login successfully!");
		// send the appAccessRequest to server
		SocketRequest.GetInstance().sendAppAccessRequest();
	}
}

class LoginErrorHandler : EzyLoginErrorHandler
{
	protected override void handleLoginError(EzyArray responseData)
	{
		logger.debug("Login error!");
		string error = responseData.get<string>(1);
		LoginUI.INSTANCE.HandleError(error);
	}
}
class AppAccessHandler : EzyAppAccessHandler
{
	public static event Action appAccessSuccessEvent;
	protected override void postHandle(EzyApp app, EzyArray data)
	{
		logger.debug("App access successfully!");
		// Start game logic
		appAccessSuccessEvent?.Invoke();
	}
}

#region App Data Handler

class SpinResponseHandler : EzyAbstractAppDataHandler<EzyObject>
{
	public static event Action<int> spinResponseEvent;
	protected override void process(EzyApp app, EzyObject data)
	{
		logger.debug("Receive spin response: " + data);
		var result = data.get<int>("result");
		spinResponseEvent?.Invoke(result);
	}
}
class BalanceResponseHandler : EzyAbstractAppDataHandler<EzyObject>
{
	public static event Action<float> balanceResponseEvent;
	protected override void process(EzyApp app, EzyObject data)
	{
		logger.debug("Receive balance: " + data);
		var balance = data.get<float>("balance");
		balanceResponseEvent?.Invoke(balance);
	}
}
class TimerResponseHandler : EzyAbstractAppDataHandler<EzyObject>
{
	public static event Action<int> timerResponseEvent;
	protected override void process(EzyApp app, EzyObject data)
	{
		logger.debug("Receive timer: " + data);
		var result = data.get<int>("time");
		timerResponseEvent?.Invoke(result);
	}
}
class BetResponseHandler : EzyAbstractAppDataHandler<EzyObject>
{
	public static event Action<float> betResponseEvent;
	protected override void process(EzyApp app, EzyObject data)
	{
		logger.debug("Receive bet: " + data);
		var betAmount = data.get<float>("betAmount");
		betResponseEvent?.Invoke(betAmount);
	}
}
class BetListResponseHandler : EzyAbstractAppDataHandler<EzyArray>
{
	public static event Action<List<string>,List<string>> betListResponseEvent;

	protected override void process(EzyApp app, EzyArray data)
	{
		List<string> redBettors = new List<string>();
		List<string> greenBettors = new List<string>();

		redBettors = data.get<EzyArray>(0).toList<string>();
		greenBettors = data.get<EzyArray>(1).toList<string>();
		betListResponseEvent?.Invoke(redBettors, greenBettors);
	}
}

class ChatResponseHandler : EzyAbstractAppDataHandler<EzyObject>
{
	public static event Action<string> chatResponseEvent;
	protected override void process(EzyApp app, EzyObject data)
	{
		logger.debug("Receive bet: " + data);
		var content = data.get<string>("content");
		chatResponseEvent?.Invoke(content);
	}
}

class ChatListResponseHandler : EzyAbstractAppDataHandler<EzyArray>
{
	public static event Action<List<string>> chatListResponseEvent;

	protected override void process(EzyApp app, EzyArray data)
	{
		List<string> chatContents = new List<string>();

		chatContents = data.get<EzyArray>(0).toList<string>();
		chatListResponseEvent?.Invoke(chatContents);
	}
}
class AddByGiftCardResponseHandler : EzyAbstractAppDataHandler<EzyObject>
{
	public static event Action<int> addBalanceByGiftCardResponseEvent;
	protected override void process(EzyApp app, EzyObject data)
	{
		logger.debug("Receive bet: " + data);
		var amount = data.get<int>("amount");
		addBalanceByGiftCardResponseEvent?.Invoke(amount);
	}
}

#endregion

public class SocketProxy
{
	private static readonly SocketProxy INSTANCE = new SocketProxy();

	public const string ZONE_NAME = "EzyRoulette";
	public const string PLUGIN_NAME = "EzyRoulette";
	public const string APP_NAME = "EzyRoulette";

	private string host;
	private int port;
	private EzyClient client;
	private User userAuthenInfo = new User("", "");
	private EzyObject data;

	public string Host => host;
	public int Port => port;
	public EzyClient Client => client;
	public User UserAuthenInfo => userAuthenInfo;

    public EzyObject Data { get => data; set => data = value; }

    public static SocketProxy GetInstance()
	{
		return INSTANCE;
	}

	public EzyClient setup(string host, int port)
	{
		Debug.Log("Start setting up socket client...");
		this.host = host;
		this.port = port;

		var config = EzyClientConfig.builder()
			.clientName(ZONE_NAME)
			.build();
		var clients = EzyClients.getInstance();
		client = clients.newDefaultClient(config);
		var setup = client.setup();
		
		// Add some data handlers to setup
		setup.addDataHandler(EzyCommand.HANDSHAKE, new HandshakeHandler());
		setup.addDataHandler(EzyCommand.LOGIN, new LoginSuccessHandler());
		setup.addDataHandler(EzyCommand.LOGIN_ERROR, new LoginErrorHandler());
		setup.addDataHandler(EzyCommand.APP_ACCESS, new AppAccessHandler());

		var setApp = setup.setupApp(APP_NAME);
		setApp.addDataHandler("spin", new SpinResponseHandler());
		setApp.addDataHandler("updateBalance", new BalanceResponseHandler());
		setApp.addDataHandler("getTime", new TimerResponseHandler());
		setApp.addDataHandler("bet", new BetResponseHandler());
		setApp.addDataHandler("betList", new BetListResponseHandler());
		setApp.addDataHandler("chat", new ChatResponseHandler());
		setApp.addDataHandler("chatList", new ChatListResponseHandler());
		setApp.addDataHandler("addBalanceByGiftCard", new AddByGiftCardResponseHandler());
		Debug.Log("Finish setting up socket client!");
		return client;
	}
	public void login(string username, string password,string loginToken)
	{
		userAuthenInfo.Username = username;
		userAuthenInfo.Password = password;
		Data = EzyEntityFactory.newObjectBuilder()
				.append("loginToken", loginToken)
				.build();
		client.connect(host, port);
	}
}
