package com.lizikj.resource.provider.service;

import com.lizikj.resource.api.dto.TMediaDto;
import com.lizikj.resource.provider.dao.model.Media;

import java.util.List;


public interface IMediaService {

    void save(Media media);

    TMediaDto findById(Long id);

    List<Media>  findByIds(List<Long> ids);
}
