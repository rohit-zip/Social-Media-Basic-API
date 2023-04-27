package com.api.implementation;

import com.api.services.fileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class fileServiceImplementation implements fileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File Name
        String fileName = file.getOriginalFilename();
        //File Path
        String s = UUID.randomUUID().toString();
        String newName = s.concat(fileName.substring(fileName.lastIndexOf(".")));
        String filePath = path + File.separator + newName;
        //create folder if not exixted
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        //copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
