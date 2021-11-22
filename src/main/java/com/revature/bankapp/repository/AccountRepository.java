package com.revature.bankapp.repository;

import com.revature.bankapp.model.Account;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Generated
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
