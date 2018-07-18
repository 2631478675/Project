package eCommerce.service.impl;

import eCommerce.service.IFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileServiceImpl implements IFileService {
    @Override
    public String upload(MultipartFile file, String path) {
//        String fileName = file.getOriginalFilename();
//        //获取扩展名
//        String extendsName = fileName.substring(fileName.lastIndexOf(".")+1);
//        //做新的文件名
//        String name = UUID.randomUUID().toString()+"."+extendsName;
//
//        File fileDir = new File(path);
//        if(!fileDir.exists()){
////            fileDir.setWritable(true);
//            fileDir.mkdirs();
//        }
    return null;

    }
}
