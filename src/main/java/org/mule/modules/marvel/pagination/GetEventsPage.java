package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Result;

public class GetEventsPage implements
		IGetPageExecutor<com.pcab.marvel.model.Event> {

	@Override
	public Result<com.pcab.marvel.model.Event> executeRequest(
			IMarvelAPI client, Map<String, Object> pageParams) {
		return client.getEvents(pageParams);
	}

}
