/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since October-2017
 * @version 1.0
 */
package com.sharepoint.connect;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SharePointFolders {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		AccessToken accessToken = SharePointConnector.getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken.getAccess_token());
		headers.set("Accept", "application/json;odata=verbose");
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		createFolder(restTemplate, entity, "FOLDER_NAME");
		readFolder(restTemplate, entity, "FOLDER_NAME");
		deleteFolder(restTemplate, entity, "FOLDER_NAME");

		addFolder(restTemplate, entity, "FOLDER_PATH/FOLDER_NAME");
		recycleFolder(restTemplate, entity, "FOLDER_NAME");

		getFoldersInFolder(restTemplate, entity, "FOLDER_NAME");
		getFilesInFolder(restTemplate, entity, "FOLDER_NAME");
		readFolderByFolderName(restTemplate, entity, "FOLDER_NAME");
	}

	public static void createFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		JSONObject postData = new JSONObject();
		postData.put("ServerRelativeUrl", folderName);
		HttpEntity<String> postEntity = new HttpEntity<String>(postData.toString(), entity.getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/getfolderbyserverrelativeurl('/Shared Documents')/folders",
				HttpMethod.POST, postEntity, String.class);
		new SharePointDisplayResults("Create Folder").extractResponse(response);
	}

	public static void readFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/GetFolderByServerRelativeUrl('/Shared Documents/" + folderName + "')",
				HttpMethod.GET, entity, String.class);
		new SharePointDisplayResults("Read Folder").extractResponse(response);
	}

	public static void deleteFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/GetFolderByServerRelativeUrl('/Shared Documents/" + folderName + "')",
				HttpMethod.DELETE, entity, String.class);
		System.out.println("-----------------------------");
		System.out.println("Folder Deleted " + response);
	}

	public static void addFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/folders/add('/Shared Documents/" + folderName + "')", HttpMethod.POST,
				entity, String.class);
		new SharePointDisplayResults("Add Folder").extractResponse(response);
	}

	public static void recycleFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/GetFolderByServerRelativeUrl('/Shared Documents/" + folderName + "')/recycle", HttpMethod.POST,
				entity, String.class);
		new SharePointDisplayResults("Recycle Folder").extractResponse(response);
	}

	public static void getFilesInFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/GetFolderByServerRelativeUrl('/Shared Documents/" + folderName + "')/Files", HttpMethod.GET, entity,
				String.class);
		new SharePointDisplayResults("Read Files In a Folder").extractResponse(response);

	}

	public static void getFoldersInFolder(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/GetFolderByServerRelativeUrl('/Shared Documents/" + folderName + "')/Folders", HttpMethod.GET,
				entity, String.class);
		new SharePointDisplayResults("Read Folders In a Folder").extractResponse(response);
	}

	public static void readFolderByFolderName(RestTemplate restTemplate, HttpEntity<String> entity, String folderName) {
		ResponseEntity<String> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/GetFolderByServerRelativeUrl('/Shared Documents/')/Folders('" + folderName + "')", HttpMethod.GET,
				entity, String.class);
		new SharePointDisplayResults("Read Folder By Folder Name").extractResponse(response);
	}

}
