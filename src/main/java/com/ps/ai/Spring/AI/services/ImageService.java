package com.ps.ai.Spring.AI.services;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.ImageGenerationOptions;
import com.azure.ai.openai.models.ImageSize;
import com.ps.ai.Spring.AI.dto.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.azure.openai.AzureOpenAiImageModel;
import org.springframework.ai.azure.openai.AzureOpenAiImageOptions;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AzureOpenAiChatModel chatModel;

    public String getImage(Question question) {
        Prompt imagePrompt = new Prompt(question.getQuestion());
        var imageResponse = chatModel.call(imagePrompt);
        return imageResponse.getResult().getOutput().getText();

        /*ImageGenerationOptions options = new ImageGenerationOptions(question.getQuestion())
                .setSize(ImageSize.SIZE1024X1024)
                .setN(1);

        var option= AzureOpenAiImageOptions.builder()
                .width(1024)
                .height(1024)
                .N(2)
                .responseFormat("b64_json")
                .build();

        ImagePrompt imagePrompt=new ImagePrompt(question.getQuestion());
        var imageResponse=imageModel.getImageGenerations("dall-e-3",options);

        return Base64.getDecoder().decode("data");*/
    }

   /* public String getDescription(MultipartFile file){
        var option= AzureOpenAiChatOptions.builder().build();

        var userMessage=new UserMessage( "Explain what do you see in this picture?", List.of(new Media(MimeTypeUtils.IMAGE_PNG,file.getResource())));

        ChatResponse response=chatModel.call(new Prompt(List.of(userMessage),option));

        return response.getResult().getOutput().getText();



    }*/
}
