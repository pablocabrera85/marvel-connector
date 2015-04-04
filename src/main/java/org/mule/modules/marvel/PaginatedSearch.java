package org.mule.modules.marvel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.mule.api.MuleException;
import org.mule.modules.marvel.pagination.IGetPageExecutor;
import org.mule.streaming.ProviderAwarePagingDelegate;

import com.pcab.marvel.model.BaseEntity;
import com.pcab.marvel.model.DataContainer;
import com.pcab.marvel.model.Result;
import com.pcab.marvel.model.common.QueryParam;

public class PaginatedSearch<T extends BaseEntity> extends
		ProviderAwarePagingDelegate<Object, MarvelConnector> {

	private Map<String, Object> pageParams;
	private Result<T> currentResult;
	private IGetPageExecutor<T> executor;

	public PaginatedSearch(Map<String, Object> pageParams,
			IGetPageExecutor<T> executor) {
		super();
		this.pageParams = pageParams;
		this.executor = executor;
	}

	@Override
	public void close() throws MuleException {
		// Nothing to do here.

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getPage(MarvelConnector connector) throws Exception {
		if (currentResult != null) {
			DataContainer<T> container = currentResult.getData();
			pageParams.put(QueryParam.OFFSET.getName(), container.getOffset()
					+ container.getCount());
			pageParams.put(QueryParam.LIMIT.getName(), container.getLimit());
			if (container.getTotal() < container.getOffset()
					+ container.getCount()) {
				return ListUtils.EMPTY_LIST;
			}
		}

		currentResult = executor.executeRequest(connector
				.getConnectionStrategy().getClient(), pageParams);
		if (currentResult.getCode() != 200) {
			throw new RuntimeException(String.format(
					"Unable to retrieve list. Reason: Code[%s] : %s",
					String.valueOf(currentResult.getCode()),
					currentResult.getStatus()));
		}
		List<Object> result = new ArrayList<Object>();
		Collections.addAll(result, currentResult.getData().getResults().toArray());
		return result;
	}

	@Override
	public int getTotalResults(MarvelConnector arg0) throws Exception {
		if (currentResult == null) {
			return 0;
		}
		return currentResult.getData().getTotal();
	}

}
