package com.falcon.persister.services;

import java.util.List;
import java.util.Optional;

import com.falcon.persister.beans.PublishData;
import com.falcon.persister.dto.ContentTimeDTO;

public interface PersisterService {

	public boolean handleConsumedData(PublishData publishData);

	public Optional<List<ContentTimeDTO>> getAllMessagesSorted(String sortField, String sortDirection);

	public Optional<List<ContentTimeDTO>> getAllMessagesByDate(String requestFieldName, String fromDateStr, String toDateStr, String sortField, String sortDirection);

	public Optional<List<ContentTimeDTO>> getRecordsForField(String requestFieldName, String fieldValue, String sortField, String sortDirection);
}
