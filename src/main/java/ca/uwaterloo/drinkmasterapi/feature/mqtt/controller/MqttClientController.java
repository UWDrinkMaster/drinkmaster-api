package ca.uwaterloo.drinkmasterapi.feature.mqtt.controller;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.service.IMqttClientService;
import com.amazonaws.services.iot.client.AWSIotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/mqtt")
public class MqttClientController {

    @Autowired
    private IMqttClientService iMqttClientServiceImpl;

    //todo: add drinkmaster endpoint
    @GetMapping("/pour")
    public ResponseEntity publishPourMessage(@RequestParam("id") final Integer id,
                                             @RequestParam(value = "machineId") final Integer machineId,
                                             @RequestParam(value = "transId") final Integer transId,
                                             @RequestParam(value = "time", required = false) final String time,
                                             @RequestParam(value = "content") final String content
    ) throws AWSIotException {
        this.iMqttClientServiceImpl.publishPourMessage(id, machineId, transId, time, content);
        return ResponseEntity.ok().build();
    }
}
