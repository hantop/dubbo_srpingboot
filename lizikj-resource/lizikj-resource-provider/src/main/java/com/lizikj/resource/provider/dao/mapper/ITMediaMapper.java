package com.lizikj.resource.provider.dao.mapper;


import com.lizikj.resource.api.dto.TMediaDto;
import com.lizikj.resource.provider.dao.model.Media;

import java.util.List;

public interface ITMediaMapper  {

    void insert(Media media);

    Media findOne(Long id);

    List<Media> findByIds(List<Long> ids);

}
