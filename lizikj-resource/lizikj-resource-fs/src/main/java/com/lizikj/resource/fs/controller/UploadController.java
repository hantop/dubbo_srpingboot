package com.lizikj.resource.fs.controller;


import com.lizikj.resource.api.facade.IMediaFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    private static final Log log= LogFactory.getLog(UploadController.class);

    @Autowired
    private IMediaFacade mediaFacade;

    /**
     * 获取上传凭证
     * @param request
     * @param key  源文件键值
     * @return 凭证token
     */
    @RequestMapping(value = "/gainVoucher.do")
    @ResponseBody
    public String gainVoucher(HttpServletRequest request, @RequestParam(required=false) String key){
        try {
            return mediaFacade.gainUploadVoucher(key);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
