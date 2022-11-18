package com.neko.userservice.service;

import com.neko.userservice.model.request.RoleRequest;
import com.neko.userservice.model.response.RoleResponse;

public interface RoleService {

    RoleResponse save(RoleRequest roleRequest);

    void initRoleUserAndAdmin();

}
