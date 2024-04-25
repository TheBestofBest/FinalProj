package com.app.businessBridge.global.image.controller;


import com.app.businessBridge.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
}
