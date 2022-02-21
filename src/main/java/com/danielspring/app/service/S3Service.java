package com.danielspring.app.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.danielspring.app.entity.vm.Asset;

@Service
public class S3Service {

	private final static String BUCKET = "demospringboots3bucket3";
	
	@Autowired
	private AmazonS3Client s3Client;
	
	public String putObject(MultipartFile multipartFile) {
		
		String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		String key = String.format("%s.%s",UUID.randomUUID(), extension);
		
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
					s3Client.putObject(putObjectRequest);
			return key;
		} catch (IOException ex) {
			
			throw new RuntimeException(ex);
		}	
	}
	
	public Asset getObject(String key) {
		S3Object s3Object = s3Client.getObject(BUCKET, key);
		ObjectMetadata metadata = s3Object.getObjectMetadata();
		
		try {
		
			S3ObjectInputStream inputStream = s3Object.getObjectContent();		
			byte[] bytes = IOUtils.toByteArray(inputStream);
			
			return new Asset(bytes, metadata.getContentType());
			
		} catch (IOException ex) {
			
			throw new RuntimeException(ex);
		}
	}
	
	public void deleteObject(String key) {
		
		s3Client.deleteObject(BUCKET,key);
	}
	
	public String getObjectUrl(String key) {
		return String.format("https://%s.s3.amazonaws.com/%s",BUCKET, key);

		
		
	}
	
}
