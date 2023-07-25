package ca.uwaterloo.drinkmasterapi.feature.mqtt.service;

import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IMqttClientService {
    void publishPourMessage(Integer id, Integer machineId, Integer transId, String time, String content) throws AWSIotException, JsonProcessingException;
}
