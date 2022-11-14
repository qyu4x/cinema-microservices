package com.neko.studioservice.service;

import com.neko.studioservice.model.request.StudioRequest;
import com.neko.studioservice.model.response.StudioResponse;

public interface StudioService {

    StudioResponse save(StudioRequest studioRequest);

    StudioResponse findById(String id);

}
