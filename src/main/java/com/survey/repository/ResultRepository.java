package com.survey.repository;

import com.survey.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResultRepository
                extends JpaRepository<Result, Long>
{
}
