package com.bigboss.useramjobstore.repository;

import com.bigboss.useramjobstore.domain.JobDetails;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: maifuwa
 * @date: 2024/10/11 14:27
 * @description: 定时任务详情持久层
 */
@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Integer> {

    void deleteByJobNameAndJobGroup(String jobName, String jobGroup);

    JobDetails findByJobNameAndJobGroup(@NotBlank String jobName, @NotBlank String jobGroup);
}
