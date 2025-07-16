package com.ps.ai.Spring.AI.controller;


import com.ps.ai.Spring.AI.services.AiService;
import com.ps.ai.Spring.AI.dto.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AiService aiService;

    public AIController(AiService aiService){
        this.aiService=aiService;
    }

    @PostMapping("/ask")
    Answer askQuestion(@RequestBody Question question){
       return aiService.askQuestion(question);
    }


    @PostMapping("/capital")
    Answer askCapital(@RequestBody CapitalRequest request){
        return aiService.askCapital(request);
    }

    @PostMapping("/capital-info")
    Answer askCapitalWIthInfo(@RequestBody CapitalRequest request){
        return aiService.askCapitalWithInfo(request);
    }

    @PostMapping("/capital-json")
    CapitalWithInfoResponse askCapitalWIthInfoJson(@RequestBody CapitalRequest request){
        return aiService.askCapitalWithInfoWithJson(request);
    }

    @PostMapping("/summary")
    Answer getSummaryAccordingToPrompt(@RequestBody Summary request){
        return aiService.getSummaryAccordingToPrompt(request);
    }

    @PostMapping("/function")
    WeatherResponse getWeatherInfoWithFunctionCalling(@RequestBody WeatherQuestion question){
        return aiService.functionCalling(question);
    }


}
