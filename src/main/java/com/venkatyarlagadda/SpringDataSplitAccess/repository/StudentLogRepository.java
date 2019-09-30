package com.venkatyarlagadda.SpringDataSplitAccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.venkatyarlagadda.SpringDataSplitAccess.entity.StudentLog;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */

@RestResource(exported = false)
public interface StudentLogRepository extends JpaRepository<StudentLog, Long>{

}
