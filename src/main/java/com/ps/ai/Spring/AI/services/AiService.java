package com.ps.ai.Spring.AI.services;

import com.ps.ai.Spring.AI.dto.*;
import com.ps.ai.Spring.AI.function.WeatherServiceFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AzureOpenAiChatModel chatModel;
    private final WeatherServiceFunction weatherServiceFunction;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/capital-prompt.st")
    private Resource capitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptInfo;

    public Answer askQuestion(Question question){
        PromptTemplate promptTemplate=new PromptTemplate(question.getQuestion());
        Prompt prompt=promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return  Answer.builder().answer(response.getResult().getOutput().getText()).build();

    }

    public Answer askCapital(CapitalRequest request) {
        PromptTemplate promptTemplate=new PromptTemplate(getCapitalPrompt);
        Prompt prompt=promptTemplate.create(Map.of("place",request.place()));


        ChatResponse response = chatModel.call(prompt);

        return  Answer.builder().answer(response.getResult().getOutput().getText()).build();
    }

    public Answer askCapitalWithInfo(CapitalRequest request) {
        PromptTemplate promptTemplate=new PromptTemplate(getCapitalPromptInfo);
        Prompt prompt=promptTemplate.create(Map.of("place",request.place()));


        ChatResponse response = chatModel.call(prompt);

        return  Answer.builder().answer(response.getResult().getOutput().getText()).build();
    }

    public CapitalWithInfoResponse askCapitalWithInfoWithJson(CapitalRequest request) {
        BeanOutputConverter<CapitalWithInfoResponse> converter = new BeanOutputConverter<>(CapitalWithInfoResponse.class);
        String format=converter.getFormat();

        PromptTemplate promptTemplate=new PromptTemplate(capitalPrompt);
        Prompt prompt=promptTemplate.create(Map.of("place",request.place(),"format", format));


        ChatResponse response = chatModel.call(prompt);

        return converter.convert(response.getResult().getOutput().getText());
    }

    public Answer getSummaryAccordingToPrompt(Summary summary) {
        PromptTemplate promptTemplate=new PromptTemplate(summary.prompt());
        Prompt prompt=promptTemplate.create(Map.of("text",summary.text()));

        ChatResponse response = chatModel.call(prompt);

        return Answer.builder().answer(response.getResult().getOutput().getText()).build();
    }

    public WeatherResponse functionCalling(WeatherQuestion question) {
        BeanOutputConverter<WeatherResponse> converter = new BeanOutputConverter<>(WeatherResponse.class);
        String format=converter.getFormat();

        String pattern = """
            You are a weather API response assistant.

            Given the following coordinates:
            lat: {lat}
            lon: {lon}

            Return the weather data as a **JSON object** in this format:
            {format}

            Only respond with the JSON, without extra text.
            """;;
        ToolCallback toolCallback = FunctionToolCallback
                .builder("currentWeather", weatherServiceFunction)
                .description("get weather for lat and lon")
                .inputType(WeatherQuestion.class)
                .build();
        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(toolCallback)
                .build();
        PromptTemplate promptTemplate=new PromptTemplate(pattern);

        Prompt prompt = promptTemplate.create(Map.of("lat",question.lat(),"lon",question.lon(),"format",format),chatOptions);

        var response=chatModel.call(prompt);



        return converter.convert(response.getResult().getOutput().getText());

    }
}
