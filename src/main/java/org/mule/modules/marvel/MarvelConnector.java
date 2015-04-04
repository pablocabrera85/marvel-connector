/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.marvel;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Paged;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.marvel.pagination.GetCharacterPage;
import org.mule.modules.marvel.pagination.GetComicsPage;
import org.mule.modules.marvel.pagination.GetCreatorsPage;
import org.mule.modules.marvel.pagination.GetEventsPage;
import org.mule.modules.marvel.pagination.GetSeriesPage;
import org.mule.modules.marvel.pagination.GetStoriesPage;
import org.mule.modules.marvel.pagination.IGetPageExecutor;
import org.mule.modules.marvel.strategy.ConnectorConnectionStrategy;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;

import com.pcab.marvel.model.BaseEntity;
import com.pcab.marvel.model.Result;
import com.pcab.marvel.model.common.QueryParam;

/**
 * Anypoint Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "marvel", friendlyName = "Marvel")
public class MarvelConnector {

	@ConnectionStrategy
	ConnectorConnectionStrategy connectionStrategy;
	
	/**
	 * Get the entity collection given a type and some query parameters.
	 * 
	 * @param key The key corresponding to the entity type you want to retrieve.
	 * 			<p/>
	 * 		
	 * @param queryParams The query parameters. See
	 *            {@link com.pcab.marvel.model.common.QueryParam} for more
	 *            details about params.
	 * @param pagingConfiguration
	 *            the paging configuration.
	 * @return An iterator for the objects.
	 */
	@Processor
	@Paged
	@MetaDataScope(MarvelMetaDataCategory.class)
	public ProviderAwarePagingDelegate<Object , MarvelConnector> get(
			@MetaDataKeyParam(affects=MetaDataKeyParamAffectsType.BOTH) String key,
			@Optional @Default("#[payload]") Map<String, Object> queryParams,
			PagingConfiguration pagingConfiguration) {
		if (Constants.CHARACTER.equals(key)) {
			return createPaginatedRequest(queryParams, pagingConfiguration,
					new GetCharacterPage());
		} else if (Constants.COMIC.equals(key)) {
			return createPaginatedRequest(queryParams, pagingConfiguration,
					new GetComicsPage());
		} else if (Constants.CREATOR.equals(key)) {
			return createPaginatedRequest(queryParams, pagingConfiguration,
					new GetCreatorsPage());
		} else if (Constants.EVENT.equals(key)) {
			return createPaginatedRequest(queryParams, pagingConfiguration,
					new GetEventsPage());
		} else if (Constants.SERIES.equals(key)) {
			return createPaginatedRequest(queryParams, pagingConfiguration,
					new GetSeriesPage());
		} else if (Constants.STORY.equals(key)) {
			return createPaginatedRequest(queryParams, pagingConfiguration,
					new GetStoriesPage());
		} else {
			throw new RuntimeException("Unsupported type:" + key);
		}
	}

	/**
	 * Get a single entity given a type and it's id.
	 * @param key
	 * @param id
	 * @return the required Object
	 */
	@Processor
	@MetaDataScope(MarvelMetaDataCategory.class)
	public Object getSingle(
			@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.OUTPUT) String key,
			int id) {
		Result<? extends BaseEntity> result;
		if (Constants.CHARACTER.equals(key)) {
			result = getConnectionStrategy().getClient().getCharacter(id);
		} else if (Constants.COMIC.equals(key)) {
			result = getConnectionStrategy().getClient().getComic(id);
		} else if (Constants.CREATOR.equals(key)) {
			result = getConnectionStrategy().getClient().getCreator(id);
		} else if (Constants.EVENT.equals(key)) {
			result = getConnectionStrategy().getClient().getEvent(id);
		} else if (Constants.SERIES.equals(key)) {
			result = getConnectionStrategy().getClient().getSeries(id);
		} else if (Constants.STORY.equals(key)) {
			result = getConnectionStrategy().getClient().getStory(id);
		} else {
			throw new RuntimeException("Unsupported type:" + key);
		}

		if (!result.getCode().equals(200)) {
			throw new RuntimeException(result.getCode().toString() + ":"
					+ result.getStatus());
		}

		return result.getData().getResults().get(0);
	}
	
	/**
	 * Generic method to prepare the query parameters map and execute the
	 * paginated request
	 * 
	 * @param queryParams
	 *            Parameters of the request
	 * @param pagingConfiguration
	 *            Configuration to paginated the request
	 * @param executor
	 *            object that will make the call to that actual client instance
	 *            of the connector.
	 * @return The paginated search for the given executor.
	 */
	private <T extends BaseEntity> ProviderAwarePagingDelegate<Object, MarvelConnector> createPaginatedRequest(
			Map<String, Object> queryParams,
			PagingConfiguration pagingConfiguration,
			IGetPageExecutor<T> executor) {
		if (queryParams == null) {
			queryParams = new HashMap<String, Object>();
		}
		queryParams.put(QueryParam.LIMIT.getName(),
				pagingConfiguration.getFetchSize());
		return new PaginatedSearch<T>(queryParams, executor);
	}
	
	public ConnectorConnectionStrategy getConnectionStrategy() {
		return connectionStrategy;
	}

	public void setConnectionStrategy(
			ConnectorConnectionStrategy connectionStrategy) {
		this.connectionStrategy = connectionStrategy;
	}

}