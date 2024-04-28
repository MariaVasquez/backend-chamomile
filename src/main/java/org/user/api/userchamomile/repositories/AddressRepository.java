package org.user.api.userchamomile.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.user.api.userchamomile.entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {
    List<Address> findByUserId(Long userid);
    Optional<Address> findById(Long id);

    @Modifying
    @Query("delete from Address a where a.id=:id")
    void deleteAddressBy(@Param("id") long id);
}
