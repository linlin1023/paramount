package com.paramount.shopping.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.paramount.shopping.domian.response.Result;
import com.paramount.shopping.utils.FastDFSClient;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Random;


@RestController
@RequestMapping("/shopping/upload")
public class UploadController {

	static Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Value("${FILE_SERVER_URL}")
	private String file_server_url;

	@Value("${ENDPOINT}")
	private  String endpoint;

	@Value("${ACCESS_KEY_ID}")
	private  String accessKeyId;

	@Value("${ACCESS_KEY_SECRET}")
	private  String accessKeySecret;

	@Value("${BUCKET_NAME}")
	private  String bucketName;

	@RequestMapping(path="/uploadFile", method = RequestMethod.POST)
	public Result uploadFile( @RequestParam("file") MultipartFile file){

		String url = null;

		OSSClient ossClient = null;

		try {
			logger.info("Started");
			String namekey = new Random().nextInt(10000) + "_"+ file.getOriginalFilename();
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			logger.debug("ossCliet : {}" ,ossClient);
			if (ossClient.doesBucketExist(bucketName)) {
				logger.debug("You've already created the Bucket：" + bucketName + "。");
			}else {
				logger.debug("The Bucket is not exist，Let's create Bucket：" + bucketName + "。");
				ossClient.createBucket(bucketName);
			}
			ossClient.putObject(bucketName, namekey, file.getInputStream());
			logger.debug("Object：" + namekey + "save into OSS successfully。");
			Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
			URL urlGenerated = ossClient.generatePresignedUrl(bucketName, namekey, expiration);
			if (urlGenerated != null)
				url = urlGenerated.toString();
			logger.info("Completed with url : {}", url);
			return new Result(true, url );
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "upload failed！");
		} finally {
	    	ossClient.shutdown();
		}

	}
}
