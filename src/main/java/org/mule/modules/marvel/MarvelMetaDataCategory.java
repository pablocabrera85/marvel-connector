package org.mule.modules.marvel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataOutputRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.components.MetaDataCategory;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.datatype.DataType;

import com.pcab.marvel.model.Character;
import com.pcab.marvel.model.Comic;
import com.pcab.marvel.model.Creator;
import com.pcab.marvel.model.Event;
import com.pcab.marvel.model.Series;
import com.pcab.marvel.model.Story;
import com.pcab.marvel.model.common.QueryParam;

@MetaDataCategory
public class MarvelMetaDataCategory {

	@Inject
	private MarvelConnector connector;
	/**
	 * Retrieves the list of keys
	 */
	@MetaDataKeyRetriever
	public List<MetaDataKey> getMetaDataKeys() throws Exception {
		List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

		// Generate the keys
		keys.add(new DefaultMetaDataKey(Constants.CHARACTER, "Character"));
		keys.add(new DefaultMetaDataKey(Constants.COMIC, "Comic"));
		keys.add(new DefaultMetaDataKey(Constants.CREATOR, "Creator"));
		keys.add(new DefaultMetaDataKey(Constants.EVENT, "Event"));
		keys.add(new DefaultMetaDataKey(Constants.SERIES, "Series"));
		keys.add(new DefaultMetaDataKey(Constants.STORY, "Story"));
		return keys;
	}

	/**
	 * Get MetaData given the Key the user selects
	 * 
	 * @param key
	 *            The key selected from the list of valid keys
	 * @return The MetaData model of that corresponds to the key
	 * @throws Exception
	 *             If anything fails
	 */
	@MetaDataRetriever
	public MetaData getMetaData(MetaDataKey key) throws Exception {
		DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();

		// If your build uses Maps as input
		DynamicObjectBuilder<?> dynamicObject = builder.createDynamicObject(key
				.getId());

		if (Constants.CHARACTER.equals(key.getId())) {
			// Add supported fields for this type
			dynamicObject.addSimpleField(QueryParam.NAME.getName(),
					DataType.STRING);
		} else if (Constants.COMIC.equals(key.getId())) {
			// Add supported fields for this type
			dynamicObject.addSimpleField(QueryParam.TITLE.getName(),
					DataType.STRING);
		} else if (Constants.CREATOR.equals(key.getId())) {
			// Add supported fields for this type
			dynamicObject.addSimpleField(QueryParam.FIRST_NAME.getName(),
					DataType.STRING);
		} else if (Constants.EVENT.equals(key.getId())) {
			// Add supported fields for this type
			dynamicObject.addSimpleField(QueryParam.NAME.getName(),
					DataType.STRING);
		} else if (Constants.SERIES.equals(key.getId())) {
			// Add supported fields for this type
			dynamicObject.addSimpleField(QueryParam.TITLE.getName(),
					DataType.STRING);
		} else if (Constants.STORY.equals(key.getId())) {
			// Add supported fields for this type
		} else {
			throw new RuntimeException("Unsupported type:" + key.getId());
		}
		//Adding common fields
		dynamicObject.addSimpleField(QueryParam.ORDER_BY.getName(),
				DataType.STRING);
		dynamicObject.addSimpleField(QueryParam.LIMIT.getName(),
				DataType.INTEGER);
		dynamicObject.addSimpleField(QueryParam.OFFSET.getName(),
				DataType.INTEGER);
		MetaDataModel model = builder.build();
		MetaData metaData = new DefaultMetaData(model);

		return metaData;
	}

	@MetaDataOutputRetriever
	public MetaData getOutputMetaData(MetaDataKey key) throws Exception {
		DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();
		// If you have a Pojo class
		if (Constants.CHARACTER.equals(key.getId())) {
			builder.createPojo(Character.class);
		} else if (Constants.COMIC.equals(key.getId())) {
			builder.createPojo(Comic.class);
		} else if (Constants.CREATOR.equals(key.getId())) {
			builder.createPojo(Creator.class);
		} else if (Constants.EVENT.equals(key.getId())) {
			builder.createPojo(Event.class);
		} else if (Constants.SERIES.equals(key.getId())) {
			builder.createPojo(Series.class);
		} else if (Constants.STORY.equals(key.getId())) {
			builder.createPojo(Story.class);
		} else {
			throw new RuntimeException("Unsupported type:" + key.getId());
		}

		MetaDataModel model = builder.build();
		MetaData metaData = new DefaultMetaData(model);

		return metaData;
	}

	public MarvelConnector getConnector() {
		return connector;
	}

	public void setConnector(MarvelConnector connector) {
		this.connector = connector;
	}
}
