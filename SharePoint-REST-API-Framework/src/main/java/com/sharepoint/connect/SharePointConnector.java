/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since October-2017
 * @version 1.0
 */
package com.sharepoint.connect;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SharePointConnector
{
	static String	CONNECTION_URL	= "https://accounts.accesscontrol.windows.net/tenantId/tokens/OAuth/2";
	static String	API_URL			= "https://{domain}.sharepoint.com/_api/web";

	public static AccessToken getAccessToken()
	{
		final String GRANT_TYPE = "client_credentials";
		// clientId@tenantId
		final String CLIENT_ID = "clientId@tenantId";
		final String CLIENT_SECRET = "CLIENT_SECRET";
		// resource/SiteDomain@TenantID
		final String RESOURCE = "resource/SiteDomain@TenantID";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", GRANT_TYPE);
		map.add("client_id", CLIENT_ID);
		map.add("client_secret", CLIENT_SECRET);
		map.add("resource", RESOURCE);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		AccessToken accessToken = restTemplate.postForObject(CONNECTION_URL, request, AccessToken.class);
		return accessToken;
	}

}
