package com.falcon.broadcaster.services;

import com.falcon.broadcaster.beans.PublishData;

public interface BroadcasterService {
	
	public boolean handleConsumedData(PublishData publishData);
	public boolean testBroadcast();
	
}
