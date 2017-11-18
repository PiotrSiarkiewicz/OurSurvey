package com.survey.repository;

import com.survey.models.ResultData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResultDataRepository
                extends JpaRepository<ResultData, Long>
{
}
