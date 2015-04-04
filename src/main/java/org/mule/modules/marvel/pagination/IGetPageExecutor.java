package org.mule.modules.marvel.pagination;

import java.util.Map;

import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.BaseEntity;
import com.pcab.marvel.model.Result;

public interface IGetPageExecutor<T extends BaseEntity> {
	Result<T> executeRequest(IMarvelAPI client,Map<String, Object> pageParams);
}
