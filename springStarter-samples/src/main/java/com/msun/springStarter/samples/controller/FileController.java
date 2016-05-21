/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.springStarter.samples.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文件上传下载
 * 
 * @author zxc Apr 28, 2016 2:57:57 PM
 */
@Controller
public class FileController {

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        return new ModelAndView("file");
    }

    /**
     * POST /uploadFile -> receive and locally save a file.
     * 
     * @param uploadfile The uploaded file as Multipart file parameter in the HTTP request. The RequestParam name must
     * be the same of the attribute "name" in the input tag with type file.
     * @return An http OK status in case of success, an http 4xx status in case of errors.
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {

        try {
            // Get the filename and build the local file path (be sure that the
            // application have write permissions on such directory)
            String filename = uploadfile.getOriginalFilename();
            String directory = "/var/temp/uploaded_files";
            String filepath = Paths.get(directory, filename).toString();

            // Save the file locally
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile
}
