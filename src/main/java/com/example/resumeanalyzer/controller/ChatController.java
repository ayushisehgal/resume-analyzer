package com.example.resumeanalyzer.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    private String storedResume = "";

    @PostMapping("/save")
    public String saveResume(@RequestBody String text) {
        storedResume = text;
        return "Saved";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {

        return "Answer based on resume:\n" + storedResume + "\n\nQuestion: " + question;
    }
}