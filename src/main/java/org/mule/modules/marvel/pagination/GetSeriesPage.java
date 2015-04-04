package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Result;

public class GetSeriesPage implements
		IGetPageExecutor<com.pcab.marvel.model.Series> {

	@Override
	public Result<com.pcab.marvel.model.Series> executeRequest(
			IMarvelAPI client, Map<String, Object> pageParams) {
		return client.getSeries(pageParams);
	}

}
