package com.lizikj.resource.fs.controller;


import com.lizikj.resource.api.facade.IMediaFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/download")
public class DownloadController {

    private static final Log log= LogFactory.getLog(DownloadController.class);

    @Autowired
    private IMediaFacade mediaFacade;

    /**
     * 根据附件ID获取完整url路径
     * @param mediaIds 附件ID集合
     * @return 完整url路径
     */
    @RequestMapping(value = "/url.do")
    @ResponseBody
    private Map<Long, String> url(@RequestParam List<Long> mediaIds){
        try {
            return mediaFacade.getRemoteUrls(mediaIds);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

}
