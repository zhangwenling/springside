/*
 * $HeadURL$
 * $Id$
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package org.springside.modules.test.mock;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;



/**
 * Listen JMS message, store them in a List.
 * 
 * @author calvin
 */
public class MockMessageListener implements MessageListener {
    List<Message> messages = new ArrayList<Message>();

    public void onMessage(Message message) {
        messages.add(message);
    }

    public Message getFirstMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(0);
    }

    public Message getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    public List<Message> getAllMessages() {
        return messages;
    }

    public void clearAllMessages() {
        messages.clear();
    }
}
