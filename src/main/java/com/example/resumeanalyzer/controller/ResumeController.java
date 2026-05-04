package com.example.resumeanalyzer.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.resumeanalyzer.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Autowired
    private ResumeService service;

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file) {

        String text = service.extractText(file);

        String result = service.analyzeResume(text);

        return result;
    }
}