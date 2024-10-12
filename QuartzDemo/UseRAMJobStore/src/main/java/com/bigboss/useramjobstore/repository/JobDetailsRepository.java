package com.bigboss.useramjobstore.repository;

import com.bigboss.useramjobstore.domain.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: maifuwa
 * @date: 2024/10/11 14:27
 * @description: 定时任务详情持久层
 */
@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Integer> {

    void deleteByJobNameAndJobGroup(String jobName, String jobGroup);

    JobDetails findByJobNameAndJobGroup(String jobName, String jobGroup);

    @Modifying
    @Query("update JobDetails set paused = :paused where jobName = :jobName and jobGroup = :jobGroup")
    void updatePausedByJobNameAndJobGroup(@Param("paused") Boolean paused, @Param("jobName") String jobName, @Param("jobGroup") String jobGroup);
}
