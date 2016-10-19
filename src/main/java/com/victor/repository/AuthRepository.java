package com.victor.repository;

import com.victor.domain.Auth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vvtan on 16/10/19.
 */

@Repository
public interface AuthRepository extends PagingAndSortingRepository<Auth, Long> {
}