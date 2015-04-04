/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.marvel.strategy;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.display.Password;

import com.pcab.marvel.Client;
import com.pcab.marvel.IMarvelAPI;
import com.pcab.marvel.model.Character;
import com.pcab.marvel.model.Result;
import com.pcab.marvel.model.common.QueryParam;

/**
 * Connection Management Strategy
 *
 * @author MuleSoft, Inc.
 */
@ConnectionManagement(configElementName = "config-type", friendlyName = "Connection Managament type strategy")
public class ConnectorConnectionStrategy {

	private IMarvelAPI client;

	/**
	 * Connect
	 *
	 * @param publicKey
	 *            Your public key
	 * @param privateKey
	 *            Your private key
	 * @throws ConnectionException
	 */
	@Connect
	@TestConnectivity
	public void connect(@ConnectionKey String publicKey,
			@Password String privateKey) throws ConnectionException {
		client = Client.newInstance(publicKey, privateKey);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(QueryParam.LIMIT.getName(), 1);
		// try to get a single Character to see if the credentials are good.
		Result<Character> result = client.getCharacters(params);
		if (!Integer.valueOf(200).equals(result.getCode())) {
			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN,
					String.valueOf(result.getCode()), result.getStatus());
		}
	}

	/**
	 * Disconnect
	 */
	@Disconnect
	public void disconnect() {
		client = null;
	}

	/**
	 * Are we connected
	 */
	@ValidateConnection
	public boolean isConnected() {
		return client != null;
	}

	/**
	 * Are we connected
	 */
	@ConnectionIdentifier
	public String connectionId() {
		return "100";
	}

	public IMarvelAPI getClient() {
		return client;
	}

	public void setClient(IMarvelAPI client) {
		this.client = client;
	}

}