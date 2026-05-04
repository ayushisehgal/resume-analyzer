package com.example.resumeanalyzer.service;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    // ✅ STEP 1: Extract text from PDF
    public String extractText(MultipartFile file) {
        try {
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            String text;
            text = stripper.getText(document);
            document.close();
            return analyzeResume(text);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting PDF text";
        }
    }

    // ✅ STEP 2: Send text to OpenRouter AI
    public <JsonNode> String analyzeResume(String text) {
        try {
            String url = "https://openrouter.ai/api/v1/chat/completions";

            RestTemplate restTemplate = new RestTemplate();

            // ✅ STEP 3: ADD PROMPT HERE
            String shortText = text.substring(0, Math.min(text.length(), 2000));

            String prompt = "Analyze the following resume:\n\n" + shortText +
                    "\n\nGive output in this format:\n" +
                    "Skills:\nStrengths:\nWeaknesses:\nSuggestions:";

            // ✅ CREATE REQUEST BODY
            String body = "{\n" +
                    "\"model\": \"openai/gpt-4o-mini\",\n" +
                    "\"messages\": [\n" +
                    "  {\"role\": \"user\", \"content\": \""
                    + prompt.replace("\"", "\\\"").replace("\n", " ") + "\"}\n" +
                    "]\n" +
                    "}";

            // ✅ HEADERS
            HttpHeaders headers = new HttpHeaders();

            String apiKey = "sk-or-v1-xxx"; // your key

            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");

// 🔥 REQUIRED FOR OPENROUTER (MOST PEOPLE MISS THIS)
            headers.set("HTTP-Referer", "http://localhost:3000");
            headers.set("X-Title", "Resume Analyzer App");

            // ✅ CALL API
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            // ✅ STEP 4: EXTRACT ONLY CONTENT HERE
            String responseBody = response.getBody();



// Extract only content manually
            int start = responseBody.indexOf("\"content\":\"") + 11;
            int end = responseBody.indexOf("\"", start);

            String result = responseBody.substring(start, end);

// Clean escape characters
            result = result.replace("\\n", "\n").replace("\\\"", "\"");



            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error analyzing resume with AI: " + e.getMessage();
        }
    }

    // ✅ STEP 3: Full process method (controller will call this)
    public String processResume(MultipartFile file) {
        try {
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            // 🔥 DEBUG (ADD THIS)
            System.out.println("===== RESUME TEXT =====");
            System.out.println(text);
            System.out.println("=======================");

            // send to AI
            return analyzeResume(text);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing file";
        }
    }
}