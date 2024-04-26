package com.app.businessBridge.global.image.controller;


import com.app.businessBridge.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
// 백엔드에서 내부적으로 사용할 예정으로 @RestController로 선언하지 않음
public class ImageController {

    private final ImageService imageService;
}
