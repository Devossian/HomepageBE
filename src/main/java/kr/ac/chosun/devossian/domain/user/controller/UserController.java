package kr.ac.chosun.devossian.domain.user.controller;

import kr.ac.chosun.devossian.domain.user.service.UserService;
import kr.ac.chosun.devossian.global.result.ResultResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


}
