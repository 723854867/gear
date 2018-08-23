package org.gear.user.event;

import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.bean.entity.Username;
import org.gear.soa.user.bean.model.RegisterMsg;
import org.gear.user.event.message.LoginMessage;
import org.jupiter.dispatcher.Event;
import org.jupiter.dispatcher.EventType;
import org.jupiter.dispatcher.ThreadFakeDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEvents {
	
	public static final EventType EVENT_LOGIN					= EventType.create(0x02);
	public static final EventType EVENT_REGISTER				= EventType.create(0x01);

	private static ThreadFakeDispatcher threadFakeDispatcher;
	
	public static void login(LoginMessage message) {
		threadFakeDispatcher.dispatch(new Event<LoginMessage>(EVENT_LOGIN, message));
	}
	
	public static void register(Username username, UserInfo user, UserInfo inviter) { 
		threadFakeDispatcher.dispatch(new Event<RegisterMsg>(EVENT_REGISTER, new RegisterMsg(user, inviter, username)));
	}
	
	@Autowired
	public void setThreadFakeDispatcher(ThreadFakeDispatcher threadFakeDispatcher) {
		UserEvents.threadFakeDispatcher = threadFakeDispatcher;
	}
}
