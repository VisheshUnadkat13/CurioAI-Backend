package com.ai.ai_educator_platform.controller;

import com.ai.ai_educator_platform.repo.PromptRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prompts")
@CrossOrigin(origins = "*")
public class PromptController {


    private final ChatClient chatClient;

    public PromptController(OllamaChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    @Autowired
    private PromptRepository promptRepository;

    @PostMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message) {




        ChatResponse chatResponse = chatClient.prompt(message)
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());


        String response = chatResponse
                .getResult()
                .getOutput()
                .getText();



//        return ResponseEntity.ok(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/you-tube-link")
    public ResponseEntity<String> suggestYouTubeLink(@RequestParam String topic,@RequestParam String lang)
    {
        String suggestion= """
                
                I want to learn about the topic:{topic}
                please suggest relevent you tube video that explain topic clearly
                
                The suggested videos should:
                    - Have high view counts and engagement.
                    - Be from reputable or verified channels (if possible).
                    - Be beginner-friendly and easy to understand and to advance level.
                     - Include the video title, channel name, and a short description.
                     -and preferred language for explanation is {lang}
                     
                     -for dsa suggest coder army,tack u forward,apna college,algo zenith,code with harry,code help
                     and after that much sugggestion you can suggest your self
                     
                     -for programming in java full stack suggest telusko,codewithDurgesh,EmbarkX,Engineering Digest
                                                                                                  etc..
                     
                     -for MERN stack technology first suggest apna college,code with harry,code help,chai aur code,WsCube Tech etc...
              
                     -for competitive programming suggest AlgoZenith and then after you find all you tube and suggest to the user
                     
                     -for all suggestion first suggest india educator
                     
                     -for GATE Examination first suggest unacdemy,GATE Whalla,Gate Academy and then suggest you..
                     
                     -for python and it's framework like fast api,django and flask suggest code with herry course,chai aur code(suggest one shot video)
                       apna college(by shardha didi)(one shot video),WsCube Tech etc..
                     
                     -for AI and ML WsCube Tech etc..
                     
                     -for Data Science WsCube Tech etc..
                     
                     -and every subject suggestion at last also suggest official documentation of search  
                     technology..
                     
                     -for dsa (data structure and algorithm) you must need to follow this type of structur:
                       -first provide brute forse approch to solve a particular problem
                       -then provide better approch
                       -and at last provide optimal approch for particular problem.
                                  
                     
                     
                
                        Limit the suggestions to 3-5 of the best videos.
                
                """;
        PromptTemplate promptTemplate=new PromptTemplate(suggestion);
        Prompt prompt=promptTemplate.create(Map.of("topic",topic,"lang",lang));

        String response1 = chatClient
                .prompt(prompt)
                .call()
                .content();

        return new ResponseEntity<>(response1,HttpStatus.OK);

    }

    @PostMapping("/interviewQuestion")
    public ResponseEntity<String> generateInterviewQuestion(@RequestParam String topic,@RequestParam String difficultyLevel)
    {
        String temp = """
    I want to prepare for interviews on the topic: {topic}.

    First, suggest  related to this topic.
    Then, analyze their content in detail and generate **20 to 25 interview-style questions**.

    For each interview question:
    - Provide a **technically in-depth, well-structured, and descriptive answer**
    - Each answer should cover:
        • Theoretical explanation of the concept
        • Real-world examples or scenarios where applicable
        • Code snippets or pseudo-code if relevant
        • Common pitfalls or misconceptions
    - Ensure the questions and answers reflect the actual content and insights from the suggested YouTube videos
    - The level of detail should be suitable for a **{difficultyLevel}** interview (e.g., beginner, intermediate, advanced)

    Format the output as follows:

    Video Suggestions:
    1. [Title] - [Channel] - [Short Description]

    Interview Questions and In-Depth Answers:
    1. Q: ...
       A: ...
       - Explanation:
       - Example:
       - Key Considerations:
       - Code (if needed):

    Please make the answers comprehensive enough to help someone deeply understand and revise the topic for a technical interview.
    """;


        PromptTemplate promptTemplate=new PromptTemplate(temp);
        Prompt prompt=promptTemplate.create(Map.of("topic",topic,"difficultyLevel",difficultyLevel));


        String response2 = chatClient
                .prompt(prompt)
                .call()
                .content();

        return new ResponseEntity<>(response2,HttpStatus.OK);


    }

    @PostMapping("/articles")
    public ResponseEntity<String> generateArticle(@RequestParam String topic,@RequestParam String lang)
    {
        String temp= """
                
                I want to learn about the topic:{topic}
                
                generate articles about this topic
                and suggest a appropriate book for that topic
                
                My preferred language for explanation is {lang}.
                
                
               
                """;

        PromptTemplate promptTemplate=new PromptTemplate(temp);
        Prompt prompt=promptTemplate.create(Map.of("topic",topic,"lang",lang));



        String response = chatClient
                .prompt(prompt)
                .call()
                .content();

        return  new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @PostMapping("/answer/{chat}")
    public ResponseEntity<String> Chat(@PathVariable String chat) {

        ChatResponse chatResponse = chatClient
                .prompt(chat)
                .call()
                .chatResponse();


        System.out.println(chatResponse.getMetadata().getModel());


        String response = chatResponse
                .getResult()
                .getOutput()
                .getText();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/summery")
    public ResponseEntity<String> recommend(@RequestParam String topic, @RequestParam String level, @RequestParam String lang) {
        String temp = """

                I want to learn about the topic: {topic}.
                    The content should be suitable for a {level} level learner.
                    My preferred language for explanation is {lang}.
                   \s
                    Please provide:
                    1. A simple summary of the topic.
                    2. Key concepts explained clearly.
                    3.please suggest online book related to topic
                    4.
                """;


        PromptTemplate promptTemplate = new PromptTemplate(temp);
        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "level", level, "lang", lang));


        String response = chatClient
                .prompt(prompt)
                .call()
                .content();

       return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @PostMapping("/quiz")
    public ResponseEntity<String> generateQuiz(@RequestParam String topic)
    {
        String temp= """
                
                I want to generate a quiz based on the topic:{topic}
                
                Please create a categorized quiz with the following structure:
                
                    1. **Easy Level (5 questions)**:
                       - Basic and fundamental questions
                       - Short and direct
                       - Focus on definitions and basic concepts
                       -for mathemetics generaete formula based questinn and also for physics
                       
                
                    2. **Medium Level (5 questions)**:
                       - Slightly challenging
                       - Involve reasoning or examples
                       - Require some conceptual understanding
                       
                
                    3. **Hard Level (5 questions)**:
                       - Deep, conceptual, or scenario-based questions
                       - Can include code snippets, edge cases, or real-world applications
                       - Require analytical or in-depth thinking
                
                    For each question:
                    - Label it with its difficulty level
                    - Provide 4 multiple-choice options (A, B, C, D)
                    - Clearly mark the correct answer
                    - Add a brief explanation for the answer
                
                    Format your response like this:
                
                    **Easy Level**
                    1. Question...
                       A. Option 1
                       B. Option 2
                       C. Option 3
                       D. Option 4 \s
                       Answer: B \s
                       Explanation: ...
                
                    **Medium Level**
                    ...
                
                    **Hard Level**
                    ...
                
                    The quiz should be helpful for users who are preparing for interviews or assessments.
                    
                    
                    After Quiz is completed then report card will generated how many of correct 
                    from total quiz questions 
                    
                    and also make suggestion to user which topic is weak 
                    by analyze a wrong answer.
                
                
                """;

        PromptTemplate promptTemplate=new PromptTemplate(temp);

        Prompt prompt=promptTemplate.create(Map.of("topic",topic));

        String response = chatClient
                .prompt(prompt)
                .call()
                .content();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

//    @PostMapping("/quiz-report")
//    public ResponseEntity<String> generateReport(@RequestParam String topic)
//    {
//
//    }


}



