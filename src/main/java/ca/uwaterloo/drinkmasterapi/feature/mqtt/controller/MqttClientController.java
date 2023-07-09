package ca.uwaterloo.drinkmasterapi.feature.mqtt.controller;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.PourItem;
import ca.uwaterloo.drinkmasterapi.feature.mqtt.service.MqttClientService;
import com.amazonaws.services.iot.client.AWSIotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/mqtt")
public class MqttClientController {

    @Autowired
    private MqttClientService mqttClientServiceImpl;

    @GetMapping("/gdpr")
    public ResponseEntity publishGdprMessage(@RequestParam("locationId") final String locationId) throws AWSIotException {
        this.mqttClientServiceImpl.publishGdprMessage(locationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ota")
    public ResponseEntity publishOtaMessage(@RequestParam("locationId") final String locationId,
                                            @RequestParam("url") final String url,
                                            @RequestParam("productIndex") final String productIndex) throws AWSIotException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.mqttClientServiceImpl.publishOtaMessage(locationId, url, productIndex);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/label")
    public ResponseEntity publishLabelMessage(@RequestParam("locationId") final String locationId,
                                              @RequestParam("baseUrl") final String baseUrl,
                                              @RequestParam("productIndex") final String productIndex) throws AWSIotException {
        this.mqttClientServiceImpl.publishLabelMessage(locationId, baseUrl, productIndex);
        return ResponseEntity.ok().build();
    }

    //todo: add drinkmaster endpoint
    @GetMapping("/pour")
    public ResponseEntity publishPourMessage(@RequestParam("id") final Integer id,
                                             @RequestParam("machineId") final Integer machineId,
                                             @RequestParam("transId") final Integer transId,
                                             @RequestParam("time") final Timestamp time,
                                             @RequestParam("content") final List<PourItem> content
    ) throws AWSIotException {
        this.mqttClientServiceImpl.publishPourMessage(id,machineId,transId,time,content);
        return ResponseEntity.ok().build();
    }
}
