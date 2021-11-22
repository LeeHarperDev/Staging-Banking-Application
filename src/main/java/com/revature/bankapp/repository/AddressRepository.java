package com.revature.bankapp.repository;

import com.revature.bankapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Integer> {
}
