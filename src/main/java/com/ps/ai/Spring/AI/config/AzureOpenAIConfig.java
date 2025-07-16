package com.ps.ai.Spring.AI.config;



public class AzureOpenAIConfig {

   /* @Bean
    AzureOpenAiChatModel azureOpenAiChatModel(){
        var openAIClientBuilder = new OpenAIClientBuilder()
                .credential(new AzureKeyCredential("dial-jwy7fn7ad4niq6mg7ohicineu5j"))
                .endpoint("https://ai-proxy.lab.epam.com/v1");

        var openAIChatOptions = AzureOpenAiChatOptions.builder()
                .deploymentName("gpt-4o")
                .temperature(0.4)
                .maxTokens(200)
                .build();

        return AzureOpenAiChatModel.builder()
                .openAIClientBuilder(openAIClientBuilder)
                .defaultOptions(openAIChatOptions)
                .build();
    }*/
}
