package com.codewithomarm.rosterup.controller;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.service.ITenantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/roster-up/tenants")
public class TenantController {

    private final ITenantService tenantService;

    @Autowired
    public TenantController(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping() // GET http://localhost:8080/api/roster-up/tenants
    public ResponseEntity<Page<TenantDTO>> getAllTenants(Pageable pageable) {
        Page<TenantDTO> tenants = tenantService.getAllTenants(pageable);
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}") // GET http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<TenantDTO> getTenantById(@PathVariable String id) {
        TenantDTO tenantDTO = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenantDTO);
    }

    @PostMapping() // POST http://localhost:8080/api/roster-up/tenants
    public ResponseEntity<TenantDTO> createTenant(@Valid @RequestBody TenantDTO tenantDTO) {
        TenantDTO createdTenant = tenantService.createTenant(tenantDTO);

        // Set the location header for the newly created tenant
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTenant.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdTenant);
    }

    @PutMapping("/{id}") // PUT http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<TenantDTO> updateTenant(@PathVariable String id, @Valid @RequestBody TenantDTO tenantDTO) {
        TenantDTO updatedTenant = tenantService.updateTenant(id, tenantDTO);
        return ResponseEntity.ok(updatedTenant);
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8080/api/roster-up/tenants/{id}
    public ResponseEntity<Void> deleteTenant(@PathVariable String id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/name") // GET http://localhost:8080/api/roster-up/tenants/search/name?name=tenantName
    public ResponseEntity<Page<TenantDTO>> getTenantsByName(@RequestParam String name, Pageable pageable) {
        Page<TenantDTO> tenants = tenantService.getTenantsByName(name, pageable);
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("7search/active") // GET http://localhost:8080/api/roster-up/tenants/search/active?active={true/false}
    public ResponseEntity<Page<TenantDTO>> getTenantsByActiveStatus(
            @RequestParam(name="active", required=true) Boolean isActive, Pageable pageable) {
        Page<TenantDTO> tenants;
        if (isActive) {
            tenants = tenantService.getActiveTenants(pageable);
        } else {
            tenants = tenantService.getInactiveTenants(pageable);
        }
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/subdomains/{subdomainName}") // GET http://localhost:8080/api/roster-up/tenants/subdomains/{subdomainName}
    public ResponseEntity<TenantDTO> getTenantBySubdomain(@PathVariable String subdomainName) {
        TenantDTO tenantDTO = tenantService.getTenantBySubdomain(subdomainName);
        return ResponseEntity.ok(tenantDTO);
    }
}
