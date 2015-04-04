package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Result;

public class GetStoriesPage implements
		IGetPageExecutor<com.pcab.marvel.model.Story> {

	@Override
	public Result<com.pcab.marvel.model.Story> executeRequest(
			IMarvelAPI client, Map<String, Object> pageParams) {
		return client.getStories(pageParams);
	}

}
