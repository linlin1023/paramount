package com.paramount.shopping.controller;

import com.paramount.shopping.domian.response.Result;
import com.paramount.shopping.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/shopping/upload")
public class UploadController {
	
	@Value("${FILE_SERVER_URL}")
	private String file_server_url;
	
	@RequestMapping(path="/uploadFile", method = RequestMethod.POST)
	public Result uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		
		try {
			// 获得文件名:
			String fileName = file.getOriginalFilename();
			// 获得文件的扩展名:
			String extName = fileName.substring( fileName.lastIndexOf(".")+1 );
			// 创建工具类
			FastDFSClient client = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
			
			String path = client.uploadFile(file.getBytes(), extName); // group1/M00/
			
			String url = file_server_url + path;
			
			return new Result(true, url);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "上传失败！");
		}

		//1.upload path make
		/*String path = request.getSession().getServletContext().getRealPath("/uploads/");
		File folder = new File(path);
		System.out.println(path);
		if(!folder.exists()){
			folder.mkdir();
		}
		String name = file.getOriginalFilename();
		String extName = name.substring( name.lastIndexOf(".")+1 );
		try {
			file.transferTo(new File(path,name));
			return new Result(true, path + name );
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false, "upload failed！");
		}
*/
	}
}
