/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since October-2017
 * @version 1.0
 */
package com.sharepoint.connect;

import java.time.Instant;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.sharepoint.utilities.UtilityMethods;

public class SharePointDisplayResults
{

	public SharePointDisplayResults()
	{
		super();
	}

	public SharePointDisplayResults(String message)
	{
		System.out.println("---------------------------------");
		System.out.println("Working on " + message);
	}

	public void extractResponse(ResponseEntity<String> response)
	{
		if (response.getStatusCode().is2xxSuccessful())
		{
			System.out.println(response);
			JSONObject body = new JSONObject(response.getBody());
			try
			{
				JSONObject data = body.getJSONObject("d");
				if (data.has("results"))
					displayDataResults(data.getJSONArray("results"));
				else
					displayDataObject(data);
			}
			catch (Exception e)
			{
				System.out.println("Exception while parsing JSON response >>" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void displayDataObject(JSONObject data)
	{
		System.out.println("ItemCount | Name | ServerRelativeUrl | Url | Date | UniqueId | MajorVersion | Version");
		System.out.println(data.opt("ItemCount") + "|" + data.opt("Name") + "|" + data.opt("ServerRelativeUrl") + "|" + data.opt("Url") + "|"
				+ (UtilityMethods.checkNullOrEmpty(data.opt("TimeCreated")) ? Date.from(Instant.parse(data.opt("TimeCreated").toString())) : "") + "|"
				+ data.opt("UniqueId") + "|" + data.opt("MajorVersion") + "|" + data.opt("VersionLabel"));

	}

	private void displayDataResults(JSONArray results)
	{
		for (int i = 0; i < results.length(); i++)
			displayDataObject(results.getJSONObject(i));
	}
}
