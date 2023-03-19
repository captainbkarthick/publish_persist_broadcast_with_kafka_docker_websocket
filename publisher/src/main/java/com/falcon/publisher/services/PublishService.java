package com.falcon.publisher.services;

import com.falcon.publisher.beans.PublishData;

public interface PublishService {

	public boolean processMessage(PublishData publishData);
}
