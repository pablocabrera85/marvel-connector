package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Result;

public class GetComicsPage implements
		IGetPageExecutor<com.pcab.marvel.model.Comic> {

	@Override
	public Result<com.pcab.marvel.model.Comic> executeRequest(
			IMarvelAPI client, Map<String, Object> pageParams) {
		return client.getComics(pageParams);
	}

}
