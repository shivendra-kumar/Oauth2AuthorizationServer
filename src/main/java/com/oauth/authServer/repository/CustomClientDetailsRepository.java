package com.oauth.authServer.repository;

import com.oauth.authServer.entity.CustomClientDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomClientDetailsRepository extends CrudRepository<CustomClientDetails, String> {
}
