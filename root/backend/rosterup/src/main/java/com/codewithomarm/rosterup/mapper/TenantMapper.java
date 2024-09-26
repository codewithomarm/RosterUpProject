package com.codewithomarm.rosterup.mapper;

import com.codewithomarm.rosterup.dto.TenantDTO;
import com.codewithomarm.rosterup.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    TenantMapper mapper = Mappers.getMapper(TenantMapper.class);

    TenantDTO toDto(Tenant tenant);

    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "positions", ignore = true)
    @Mapping(target = "lineOfBusinesses", ignore = true)
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "rosters", ignore = true)
    Tenant toEntity(TenantDTO tenantDto);
}
