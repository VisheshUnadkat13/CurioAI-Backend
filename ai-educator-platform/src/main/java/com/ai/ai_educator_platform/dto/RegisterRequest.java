package com.ai.ai_educator_platform.dto;

import lombok.Data;
import org.springframework.ai.chat.prompt.Prompt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class RegisterRequest {


    private String username;
    private String email;
    private Set<String> role;
    private String password;
//    private String createdAt;
//    private LocalDateTime updatedAt;
//    private List<Prompt> prompts = new ArrayList<>();
}
