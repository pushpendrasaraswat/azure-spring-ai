package com.ps.ai.Spring.AI.controller;

import com.ps.ai.Spring.AI.dto.Question;
import com.ps.ai.Spring.AI.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    String getImage(@RequestBody Question question) {
        return imageService.getImage(question);
    }
}
