package com.fu.springbootdemo.service;

import org.springframework.web.multipart.MultipartFile;

public interface ToolboxService {

    String wordToPdf(MultipartFile file,String exchangeType);

}
