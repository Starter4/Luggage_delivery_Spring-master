package com.example.mainservice.web;

import com.example.mainservice.entity.enums.MailType;
import com.example.mainservice.payload.request.mailMicroservice.MailRequest;
import com.example.mainservice.payload.response.mailMicroservice.MailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {
    private final WebClient.Builder webClientBuilder;

    @GetMapping()
    public void test(){
        MailResponse mailResponse = webClientBuilder
                .build()
                .post()
                .uri("http://mail-service/api/v2/mail/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new MailRequest("User","testspringwebapp@gmail.com"
                        , "Test",MailType.NEW_NEWS, "QQQ", "qqqq")),MailRequest.class)
                .retrieve()
                .bodyToMono(MailResponse.class)
                .block();
        if (mailResponse != null) {
            System.out.println(mailResponse.getMailStatus().name());
        }else {
            System.out.println("null");
        }
        System.out.println("main-service send");
    }

    @GetMapping("/testGet")
    public void testGet(){
        String b = webClientBuilder
                .build()
                .get()
                .uri("http://mail-service/api/v2/mail/send")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(b);
        System.out.println("main-service sendGet");
    }

    @GetMapping("/testPython")
    public void testPythonService(){
        String pythonResponse = webClientBuilder
                .build()
                .post()
                .uri("http://localhost:8050/parse/twitter")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new MailRequest("User","testspringwebapp@gmail.com"
                        , "Test",MailType.NEW_NEWS, "QQQ", "qqqq")),MailRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(pythonResponse);
    }

    @GetMapping("/testPython/get")
    public void testPythonServiceGET(){
        /*HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange("http://localhost:8050/parse/twitter", HttpMethod.GET,entity,String.class);*/
        String b = webClientBuilder
                .build()
                .get()
                .uri("http://parser-service/parse/twitter")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(b);
        System.out.println("parser-service sendGet");
    }


    /*@GetMapping("/testPython/post")
    public void testPythonServicePost(){
        TestJson b = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/calculateGrades")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new TestJson("User","Email", 20L)),TestJson.class)
                .retrieve()
                .bodyToMono(TestJson.class)
                .block();
        System.out.println(b);
        System.out.println("main-service send");
    }*/

}
