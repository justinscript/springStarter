/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.springStarter.samples.message;

/**
 * @author zxc Mar 1, 2016 11:07:10 AM
 */
public interface MessageRepository {

	Iterable<Message> findAll();

	Message save(Message message);

	Message findMessage(Long id);

	void deleteMessage(Long id);
}
