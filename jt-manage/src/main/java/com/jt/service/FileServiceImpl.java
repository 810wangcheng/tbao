package com.jt.service;

import com.jt.vo.EasyUI_Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

    @Value("${image.localDirPath}")
    private String localDirPath;

    @Value("${image.urlDirPath}")
    private String urlDirPath;

    @Override
    public EasyUI_Image fileUpload(MultipartFile uploadFile) {
        EasyUI_Image ui_image = new EasyUI_Image();
        String filename = uploadFile.getOriginalFilename();
        filename = filename.toLowerCase();
        if (!filename.matches("^.+\\.(jpg|png|gif)$")){
            ui_image.setError(1);
            return ui_image;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height == 0 || width == 0){
                ui_image.setError(1);
                return ui_image;
            }
            ui_image.setHeight(height).setWidth(width);
            String dateFileName= new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String realFileDir = localDirPath+dateFileName;
            File fileDir = new File(realFileDir);
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString().replace("-","");
            String fileType = filename.substring(filename.lastIndexOf("."));
            String realName = uuid+fileType;
            String realFilePath = realFileDir +"/"+ realName;
            uploadFile.transferTo(new File(realFilePath));
            String urlFilePath = urlDirPath + dateFileName +"/"+realName;
            ui_image.setUrl(urlFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            ui_image.setHeight(1);
            return ui_image;
        }
        return ui_image;
    }
}
