package com.codewithomarm.rosterup.controller;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.service.impl.TenantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("roster-up/api/tenants")
public class TenantController {

    private final TenantServiceImpl tenantService;

    @Autowired
    public TenantController(TenantServiceImpl tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping()
    public ResponseEntity<Page<TenantDTO>> getAllTenants(Pageable pageable) {
        Page<TenantDTO> tenants = tenantService.getAllTenants(pageable);
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDTO> getTenantById(@PathVariable Long id) {
        TenantDTO tenantDTO = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenantDTO);
    }

    @PostMapping()
    public ResponseEntity<TenantDTO> createTenant(@RequestBody TenantDTO tenantDTO) {
        TenantDTO createdTenant = tenantService.createTenant(tenantDTO);

        // Set the location header for the newly created tenant
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTenant.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdTenant);
    }
}
