package com.survey.repository;

import com.survey.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnswerRepository extends JpaRepository<Answer, Long>
{
}
