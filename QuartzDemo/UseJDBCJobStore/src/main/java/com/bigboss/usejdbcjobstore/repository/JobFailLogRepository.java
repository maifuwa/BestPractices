package com.bigboss.usejdbcjobstore.repository;

import com.bigboss.usejdbcjobstore.domain.JobFailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: maifuwa
 * @date: 2024/10/12 16:06
 * @description:
 */
@Repository
public interface JobFailLogRepository extends JpaRepository<JobFailLog, Long> {
}
