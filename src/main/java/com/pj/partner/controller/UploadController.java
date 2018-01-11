package com.pj.partner.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("upload")
public class UploadController {

	/**
	 * 文件上传
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String,Object> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws ServletException, IOException{

		Map<String,Object> result =new HashMap<String, Object>();

			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding("UTF-8");//你的编码格式
			}

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
			String dateDir = df.format(new Date());// new Date()为获取当前系统时间

			String contentType = file.getContentType();
			String fileName =  makeFileName(file.getOriginalFilename());

			String filePath = request.getSession().getServletContext().getRealPath("/upload/" + dateDir + "/");
			try {
				uploadFile(file.getBytes(), filePath, fileName);
				result.put("filePath", filePath+fileName);
				result.put("fileName", file.getOriginalFilename());
				result.put("success",true);
				result.put("msg","文件上传成功！");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success",false);
				result.put("msg","文件上传失败！");
			}

		return result;
	}

	private void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
			targetFile.mkdirs();
		}else if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath+fileName);
		out.write(file);
		out.flush();
		out.close();
	}
	/* @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     */ 
     private String makeFileName(String filename){  //2.jpg
         //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
         return UUID.randomUUID().toString() + "_" + filename;
     }

	/**
	 * 下载
	 * @param request
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request, String filePath ) throws IOException {
		//String path=request.getSession().getServletContext().getRealPath("/WEB-INF/download/demo.xlsx");
		File file=new File(filePath);
		HttpHeaders headers = new HttpHeaders();
		String realName = filePath.substring(filePath.lastIndexOf("_") + 1);
		String fileName=new String(realName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}


}
