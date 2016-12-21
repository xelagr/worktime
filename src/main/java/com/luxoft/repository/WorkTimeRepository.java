package com.luxoft.repository;

import com.luxoft.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Aleksei Grishkov on 15.12.2016.
 */
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long>, WorkTimeRepositoryCustom {
    Collection<WorkTime> findByEmployeeId(Long id);
}

