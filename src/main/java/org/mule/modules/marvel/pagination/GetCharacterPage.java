package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Result;

public class GetCharacterPage implements
		IGetPageExecutor<com.pcab.marvel.model.Character> {

	@Override
	public Result<com.pcab.marvel.model.Character> executeRequest(
			IMarvelAPI client, Map<String, Object> pageParams) {
		return client.getCharacters(pageParams);
	}

}
