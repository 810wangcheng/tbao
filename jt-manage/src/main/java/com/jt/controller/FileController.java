package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUI_Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping
public class FileController {

    @Autowired
    private FileService fileService;

    /* 1.确定url请求路径
	 * http://localhost:8091/file
     * 2.获取用户提交参数
     * 3.响应合适的页面
     * 业务要求:
     * 将文件上传到D:\jt\images\goodgoodstudy.jpg
	 * @return
     * @throws IOException
	 * @throws IllegalStateException
	 */
    @RequestMapping("/file")
    public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
        String originalFilename = fileImage.getOriginalFilename();
        File fileDir = new File("C:/jt/images");
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        File file = new File("C:/jt/images/" + originalFilename);
        //1.实现文件上传API
        fileImage.transferTo(file);

        //2要求:重定向到file.jsp
        return "redirect:/file.jsp";
    }

    /**
     * 实现用户文件上传
     */
    @RequestMapping("/pic/upload")
    @ResponseBody    //将数据转化为json
    public EasyUI_Image fileUpload(MultipartFile uploadFile) {

        return fileService.fileUpload(uploadFile);
    }
}
