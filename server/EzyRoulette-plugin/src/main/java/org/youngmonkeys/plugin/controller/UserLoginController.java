package org.youngmonkeys.plugin.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.sercurity.EzySHA256;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import org.youngmonkeys.common.entity.User;
import org.youngmonkeys.common.service.UserService;
import org.youngmonkeys.plugin.service.WelcomeService;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;

@EzySingleton
@EzyEventHandler(USER_LOGIN)
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@EzyAutoBind
	private WelcomeService welcomeService;
	
	@EzyAutoBind
	private UserService userService;
	
	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		logger.info("{} login in", welcomeService.welcome(event.getUsername()));
		String username = event.getUsername();
		String password = encodePassword(event.getPassword());
		EzyObject data = event.getData();
		String loginToken = data.get("loginToken");
		User user= userService.getUser(username);

		if(!user.getLoginToken().equals(loginToken)){
			throw  new EzyLoginErrorException(EzyLoginError.SERVER_ERROR);
		}

		logger.info("user and password match, accept user {}", username);
	}
	
	private String encodePassword(String password) {
		return EzySHA256.cryptUtfToLowercase(password);
	}
	
}