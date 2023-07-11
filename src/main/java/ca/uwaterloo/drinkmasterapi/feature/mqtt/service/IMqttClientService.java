package ca.uwaterloo.drinkmasterapi.feature.mqtt.service;

import com.amazonaws.services.iot.client.AWSIotException;

public interface IMqttClientService {
    void publishPourMessage(Integer id, Integer machineId, Integer transId, String time, String content) throws AWSIotException;
}
