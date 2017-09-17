package com.demo.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.demo.domain.Book;
import org.apache.commons.io.FileUtils;
import com.demo.domain.UserImage;
import com.demo.domain.ImageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadController{

	// 静态的日志类LogFactory
	private static final Log logger = LogFactory
			.getLog(FileUploadController.class);
	
	// 上传文件会自动绑定到MultipartFile中
	 @RequestMapping(value="/uploadimage",method=RequestMethod.POST)
	 public String upload(HttpServletRequest request,
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile file,
						  Model model) throws Exception{

		 System.out.println(description);
		 // 如果文件不为空，写入上传路径
		 if(!file.isEmpty()){
			 // 上传文件路径
			 String path = request.getServletContext().getRealPath(
					 "/images/");
			 // 上传文件名
			 String filename = file.getOriginalFilename();
			 File filepath = new File(path,filename);
			 // 判断路径是否存在，如果不存在就创建一个
			 if (!filepath.getParentFile().exists()) {
				 filepath.getParentFile().mkdirs();
			 }
			 // 将上传文件保存到一个目标文件当中
			 file.transferTo(new File(path+File.separator+ filename));
			 model.addAttribute("msg","Success");
			 return "uploadImageForm";
		 }else{
			 model.addAttribute("msg","Error");
			 return "uploadImageForm";
		 }
	 }

    // 获取图片信息
	@RequestMapping(value="/getimage")
	// @ResponseBody会将集合数据转换json格式返回客户端
	@ResponseBody
	public Object getImage(HttpServletRequest request) {

	 	// 获取图片资源存放的本地路径
		String localpath = request.getServletContext().getRealPath(
				"/images/");
		logger.info("image_localpath:" + localpath);

		// 获取图片资源存放的网络路径
		String netpath = request.getRequestURL().toString();
		netpath = netpath.substring(0, netpath.length() - "/getimage".length()) + "/images/";
		logger.info("image_netpath:" + netpath);

		// 打印图片资源路径下的全部文件
		File imagedir = new File(localpath);
		Integer item = 0;
		Integer total = imagedir.listFiles().length;

		List<ImageInfo> imagelist = new ArrayList<ImageInfo>();

		for (File file : imagedir.listFiles()) {
			if (file.isFile()) {
				logger.info("image_name:" + file.getName());
				imagelist.add(new ImageInfo(item, total, netpath + file.getName()));
				item++;
			}
		}
		return imagelist;
	}

	@RequestMapping(value="/registerimage")
	public String register(HttpServletRequest request,
						   @ModelAttribute UserImage user,
						   Model model)throws Exception{

		logger.info("image id:" + user.getUsername());


		// 如果文件不为空，写入上传路径
		if(!user.getImage().isEmpty()){
			// 上传文件路径
			String path = request.getServletContext().getRealPath(
					"/images/");
			// 上传文件名
			String filename = user.getImage().getOriginalFilename();
			logger.info("image name:" + filename);
			logger.info("image path:" + path+File.separator+ filename);

			File filepath = new File(path,filename);
			// 判断路径是否存在，如果不存在就创建一个
			if (!filepath.getParentFile().exists()) {
				filepath.getParentFile().mkdirs();
			}
			// 将上传文件保存到一个目标文件当中
			user.getImage().transferTo(new File(path+File.separator+ filename));
			// 将用户添加到model
			model.addAttribute("user", user);
			return "userImageInfo";
		}else{
			return "error";
		}
	}

	@RequestMapping(value="/downloadimage")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
										   @RequestParam("filename") String filename,
										   Model model)throws Exception{
		// 下载文件路径
		String path = request.getServletContext().getRealPath(
				"/images/");
		File file = new File(path+File.separator+ filename);
		HttpHeaders headers = new HttpHeaders();
		// 下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
		// 通知浏览器以attachment（下载方式）打开图片
		headers.setContentDispositionFormData("attachment", downloadFielName);
		// application/octet-stream ： 二进制流数据（最常见的文件下载）。
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 201 HttpStatus.CREATED
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}

}

