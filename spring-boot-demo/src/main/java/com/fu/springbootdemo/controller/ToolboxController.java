package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.service.ToolboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("toolbox")
public class ToolboxController {
    @Autowired
    private ToolboxService toolboxService;

    @PostMapping("wordToPdf")
    public String wordToPdf(@RequestParam("file")MultipartFile file,@RequestParam("exchangeType") String exchangeType){
        return this.toolboxService.wordToPdf(file,exchangeType);
    }

}
