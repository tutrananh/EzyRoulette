package org.youngmonkeys.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezyfoxserver.wrapper.EzyAppUserManager;
import com.tvd12.ezyfoxserver.wrapper.impl.EzyAppUserManagerImpl;
import org.youngmonkeys.app.entity.Bettor;
import org.youngmonkeys.app.entity.GiftCard;
import org.youngmonkeys.app.request.AddBalanceByGiftCardRequest;
import org.youngmonkeys.app.request.BetRequest;
import org.youngmonkeys.app.request.ChatRequest;
import org.youngmonkeys.app.service.GiftCardService;
import org.youngmonkeys.app.service.ResultService;
import org.youngmonkeys.app.service.WheelService;
import org.youngmonkeys.common.entity.User;
import org.youngmonkeys.common.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EzyRequestController
public class GameRequestController extends EzyLoggable {

	@EzyAutoBind
	private UserService userService;

	@EzyAutoBind
	WheelService wheelService;
	
	@EzyAutoBind
	EzyResponseFactory responseFactory;

	@EzyAutoBind
	ResultService resultService;

	@EzyAutoBind
	GiftCardService giftCardService;

	@EzyAutoBind
	EzyAppUserManager test;

	@EzyDoHandle("spin")
	public void spin(EzyUser user) {
		int result = Timer.getResult();
		responseFactory.newObjectResponse()
				.command("spin")
				.param("result", result)
				.user(user)
				.execute();
	}
	@EzyDoHandle("updateBalance")
	public void updateBlance(EzyUser user) {
		float balance = 0f;
		User u = userService.getUser(user.getName());
		if (user == null) { // user doesn't exist in db
			logger.info("user doesn't exist in db!");
		}else{
			balance = u.getBalance();
		}
		responseFactory.newObjectResponse()
				.command("updateBalance")
				.param("balance",balance)
				.user(user)
				.execute();
	}
	@EzyDoHandle("getTime")
	public void getTime(EzyUser user) {
		int time = Timer.getTime();
		if(time==0){
			int result = Timer.getResult();
			logger.info("result = {}",result);
		}
		logger.info("Time: {}", time);
		responseFactory.newObjectResponse()
				.command("getTime")
				.param("time", time)
				.user(user)
				.execute();
	}

	@EzyDoHandle("bet")
	public void bet(EzyUser user, BetRequest request) {
		logger.info("User: {}, bet: {}",user.getName(),request.getAmount());
		float betAmount = request.getAmount();
		userService.updateUser(user.getName(), -betAmount);
		if(request.getSide()==0){
			Timer.getRedBettors().add(new Bettor(user.getName(),request.getAmount()));
			Collections.sort(Timer.getRedBettors());
		}else if(request.getSide()==1){
			Timer.getGreenBettors().add(new Bettor(user.getName(),request.getAmount()));
			Collections.sort(Timer.getGreenBettors());
		}
		responseFactory.newObjectResponse()
				.command("bet")
				.param("betAmount", betAmount)
				.user(user)
				.execute();
	}

	@EzyDoHandle("betList")
	public void betList(EzyUser user) {
		List<String> redBettors = new ArrayList<>();
		List<String> greenBettors = new ArrayList<>();
		List<EzyUser> ezyUsers = test.getUserList();
		for (Bettor b: Timer.getRedBettors()) {
			redBettors.add(b.toString());
		}
		for (Bettor b: Timer.getGreenBettors()) {
			greenBettors.add(b.toString());
		}
		responseFactory.newArrayResponse()
				.command("betList")
				.param(redBettors)
				.param(greenBettors)
				.users(ezyUsers)
				.execute();
	}

	@EzyDoHandle("chat")
	public void chat(EzyUser user, ChatRequest request) {
		if(Timer.getChatContents().size()==Timer.getMaxChatLog()){
			Timer.getChatContents().remove(Timer.getMaxChatLog()-1);
		}
		Timer.getChatContents().add(0,"  "+user.getName()+": "+request.getContent());
		responseFactory.newObjectResponse()
				.command("chat")
				.param("content", request.getContent())
				.user(user)
				.execute();
	}

	@EzyDoHandle("chatList")
	public void chatList(EzyUser user) {
		List<String> chatContents = new ArrayList<>();
		chatContents = Timer.getChatContents();
		List<EzyUser> ezyUsers = test.getUserList();
		responseFactory.newArrayResponse()
				.command("chatList")
				.param(chatContents)
				.users(ezyUsers)
				.execute();
	}

	@EzyDoHandle("addBalanceByGiftCard")
	public void addBalanceByGiftCard(EzyUser user, AddBalanceByGiftCardRequest request) {
		int amount = 0;
		String serial = request.getSerial();
		String code = request.getCode();
		GiftCard giftCard = giftCardService.getGiftCard(serial);
		if (giftCard == null) { // user doesn't exist in db
			logger.info("giftCard doesn't exist in db");
		}
		else{
			if (code.equals(giftCard.getCode()) && !giftCard.isActivated() ) {
				amount = giftCard.getAmount();
				giftCard.setActivated(true);
				giftCardService.updateGiftCard(giftCard);
				User u = userService.updateUser(user.getName(), amount);
			}
		}
		responseFactory.newObjectResponse()
				.command("addBalanceByGiftCard")
				.param("amount",amount)
				.user(user)
				.execute();
	}
}
