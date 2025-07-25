package com.ai.ai_educator_platform.repo;

import com.ai.ai_educator_platform.model.Prompt;
import com.ai.ai_educator_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<Prompt,Long> {

}
