package lab2.soa.newserv.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lab2.soa.newserv.dto.HumanBeingListDto;
import lab2.soa.newserv.dto.Response;
import lab2.soa.newserv.entities.HumanBeing;
import lab2.soa.newserv.entities.WeaponType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/human_being", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(value = "hello", description = "Новый сервис на базе Spring MVC REST")
public class Controller {
    private final String URL_HUMAN_BEING = "https://localhost:8443/web-soa3/api/human_being";

    @Value("${trust.store}")
    private String truststoreLocation;

    @Value("${trust.store.password}")
    private String truststorePassword;


    //все сущности, находящиеся в 1 или 4 четверти
    @GetMapping("/coordinates")
    @ApiOperation(value = "Получить все сущности, находящиеся в 1 или 4 четверти")
    public HumanBeingListDto getHumanBeingFromFirstAndFourthQuarter() {
        RestTemplate restTemplate = getRestTemplate();

        HumanBeingListDto humanBeingListDto = restTemplate.getForObject(URL_HUMAN_BEING, HumanBeingListDto.class);
        List<HumanBeing> humanBeingList = new ArrayList<HumanBeing>();
        humanBeingListDto.getHumanBeingList().forEach(humanBeing -> {
                    if ((humanBeing.getCoordinates().getX() >= 0))
                        humanBeingList.add(humanBeing);
                }
        );
        HumanBeingListDto humanBeingListDtoAnswer = new HumanBeingListDto();
        humanBeingListDtoAnswer.setHumanBeingList(humanBeingList);
        humanBeingListDtoAnswer.setMessage("Done");
        humanBeingListDtoAnswer.setResponseCode(400);
        return humanBeingListDtoAnswer;
    }

    // удалить всех героев с оружием HAMMER
    @SneakyThrows
    @DeleteMapping("/delete/weapon")
    @ApiOperation(value = "Удалить всех героев с оружием HAMMER")
    public Response deleteHumanBeingWithHammer() {
        RestTemplate restTemplate = getRestTemplate();

        HumanBeingListDto humanBeingListDto = restTemplate.getForObject(URL_HUMAN_BEING, HumanBeingListDto.class);
        String message = "";
        for (HumanBeing humanBeing : humanBeingListDto.getHumanBeingList()) {
            if (humanBeing.getWeaponType().equals(WeaponType.HAMMER)) {
                restTemplate.delete(URL_HUMAN_BEING + "/" + humanBeing.getId());
                message = message + "Human Being with id = " + humanBeing.getId() + " deleted\n";
            }
        }

        if (message.length() < 2)
            message = "Nothing to delete";

        Response response = new Response();
        response.setMessage(message);
        response.setResponseCode(200);
        return response;
    }

    @SneakyThrows
    private RestTemplate getRestTemplate() {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(new File(truststoreLocation)),
                truststorePassword.toCharArray());
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .loadKeyMaterial(keyStore, "secret".toCharArray()).build());
        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}