package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Result;

public class GetCreatorsPage implements
		IGetPageExecutor<com.pcab.marvel.model.Creator> {

	@Override
	public Result<com.pcab.marvel.model.Creator> executeRequest(
			IMarvelAPI client, Map<String, Object> pageParams) {
		return client.getCreators(pageParams);
	}

}
