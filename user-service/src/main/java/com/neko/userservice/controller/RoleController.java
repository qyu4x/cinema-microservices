package com.neko.userservice.controller;

import com.neko.userservice.model.request.RoleRequest;
import com.neko.userservice.model.response.RoleResponse;
import com.neko.userservice.model.response.WebResponse;
import com.neko.userservice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void postInitRoleUserAndAdmin() {
        log.info("#calling postInitRoleUserAndAdmin");
        roleService.initRoleUserAndAdmin();
        log.info("#successfully entered the main user and admin data");

    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> save(@RequestBody RoleRequest roleRequest) {
        log.info("#calling method save role");
        WebResponse<RoleResponse> webResponse = new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                roleService.save(roleRequest)
        );
        log.info("#successfully enter the role");
        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }
}
