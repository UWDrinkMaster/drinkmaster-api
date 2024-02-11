package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.feature.order.dto.PourItemDTO;
import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.util.List;

public interface IMqttClientService {
    void publishPourMessage(Long id, Long machineId, Long transId, LocalDateTime time, List<PourItemDTO> pourItems) throws AWSIotException, JsonProcessingException;
}
