package com.younchen.chat.model;

/**
 * @author lognquan
 * @date 2015年11月6日
 * @description
 */
public interface NotificationCenterDelegate {
	public void didReceivedNotification(int id, Object... args);
}
