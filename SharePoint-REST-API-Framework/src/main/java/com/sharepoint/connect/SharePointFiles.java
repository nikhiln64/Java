/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since October-2017
 * @version 1.0
 */

package com.sharepoint.connect;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SharePointFiles {

	public static void main(String[] args) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		AccessToken accessToken = SharePointConnector.getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken.getAccess_token());
		headers.set("Accept", "application/json;odata=verbose");
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		readFileMetaData(restTemplate, entity, "Folder/sample.docx");
		readFile(restTemplate, entity, "Folder/sample.docx");
		createFile(restTemplate, headers, "Folder/sample1.txt");
		updateFile(restTemplate, headers, "Folder/sample2.txt");

		createFile(restTemplate, headers, "Folder/sample.docx");
		deleteFile(restTemplate, entity, "Folder/sample.docx");
		recycleFile(restTemplate, entity, "Folder/sample.docx");

		versionsOfFile(restTemplate, entity, "Folder/sample.docx");
		readVersionOfFile(restTemplate, entity, "Folder/sample.docx");
	}

	private static void readFileMetaData(RestTemplate restTemplate, HttpEntity<String> entity, String fileName)
			throws IOException {

		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/getfilebyserverrelativeurl('/Shared Documents/" + fileName + "')",
				HttpMethod.GET, entity, String.class);
		new SharePointDisplayResults("Read File MetaData").extractResponse(response);
	}

	private static void readFile(RestTemplate restTemplate, HttpEntity<String> entity, String fileName)
			throws IOException {
		ResponseEntity<byte[]> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/getfilebyserverrelativeurl('/Shared Documents/" + fileName + "')/$value", HttpMethod.GET, entity,
				byte[].class);
		new SharePointDisplayResults("Read File");
		if (response.getStatusCode() == HttpStatus.OK) {
			Files.write(Paths.get("F://" + fileName), response.getBody());
			System.out.println("File Copied to Local");
		}
	}

	private static void createFile(RestTemplate restTemplate, HttpHeaders headers, String fileName) throws IOException {
		HttpEntity<byte[]> postEntity = new HttpEntity<>(Files.readAllBytes(new File("F://" + fileName).toPath()),
				headers);
		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL
						+ "/getfolderbyserverrelativeurl('/Shared Documents/{Domain}')/files/add(overwrite=true,url='temp.txt')')",
				HttpMethod.POST, postEntity, String.class);
		new SharePointDisplayResults("Create File").extractResponse(response);

	}

	private static void updateFile(RestTemplate restTemplate, HttpHeaders headers, String fileName) throws IOException {
		HttpEntity<byte[]> postEntity = new HttpEntity<>(Files.readAllBytes(new File("F://" + fileName).toPath()),
				headers);
		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/GetFileByServerRelativeUrl('/Shared Documents/{Domain}/sample.txt')/$value",
				HttpMethod.PUT, postEntity, String.class);
		new SharePointDisplayResults("Update File").extractResponse(response);

	}

	private static void deleteFile(RestTemplate restTemplate, HttpEntity<String> entity, String fileName)
			throws IOException {

		ResponseEntity<String> response = restTemplate.exchange(
				SharePointConnector.API_URL + "/getfilebyserverrelativeurl('/Shared Documents/" + fileName + "')",
				HttpMethod.DELETE, entity, String.class);
		new SharePointDisplayResults("Delete File").extractResponse(response);
	}

	private static void recycleFile(RestTemplate restTemplate, HttpEntity<String> entity, String fileName) {
		ResponseEntity<String> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/getfilebyserverrelativeurl('/Shared Documents/" + fileName + "')/recycle", HttpMethod.GET, entity,
				String.class);
		new SharePointDisplayResults("Recycle File").extractResponse(response);
	}

	private static void versionsOfFile(RestTemplate restTemplate, HttpEntity<String> entity, String fileName)
			throws IOException {
		readFileMetaData(restTemplate, entity, fileName);
		ResponseEntity<String> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/getfilebyserverrelativeurl('/Shared Documents/" + fileName + "')/versions", HttpMethod.GET, entity,
				String.class);
		new SharePointDisplayResults("Versions of File").extractResponse(response);
	}

	private static void readVersionOfFile(RestTemplate restTemplate, HttpEntity<String> entity, String fileName)
			throws IOException {
		ResponseEntity<byte[]> response = restTemplate.exchange(SharePointConnector.API_URL
				+ "/getfilebyserverrelativeurl('/Shared Documents/" + fileName + "')//versions(512)/$value",
				HttpMethod.GET, entity, byte[].class);
		new SharePointDisplayResults("Read Contents of Versions of File");
		if (response.getStatusCode() == HttpStatus.OK) {
			Files.write(Paths.get("F://{PATH}//dy.txt"), response.getBody());
			System.out.println("File Copied to Local");
		}
	}
}
