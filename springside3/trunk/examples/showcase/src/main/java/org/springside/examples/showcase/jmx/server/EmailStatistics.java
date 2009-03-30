package org.springside.examples.showcase.jmx.server;

import java.beans.ConstructorProperties;
import java.util.concurrent.atomic.AtomicInteger;

public class EmailStatistics {
	private AtomicInteger sendUserModifyNodify = new AtomicInteger(0);

	public EmailStatistics() {
	}

	@ConstructorProperties( { "sendUserModifyNodify" })
	public EmailStatistics(int sendUserModifyNodify) {
		this.sendUserModifyNodify = new AtomicInteger(sendUserModifyNodify);
	}

	public int getSendUserModifyNodify() {
		return sendUserModifyNodify.get();
	}
}
