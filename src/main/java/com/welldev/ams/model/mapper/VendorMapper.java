package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.db.Vendor;
import com.welldev.ams.model.request.VendorDTO;

@Mapper(componentModel = "spring")
public interface VendorMapper {
  Vendor vendorToEntity(VendorDTO vendorDTO);

  void updateVendor(VendorDTO vendorDTO, @MappingTarget Vendor vendor);
}
