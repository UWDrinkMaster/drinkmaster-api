package ca.uwaterloo.drinkmasterapi.feature.mqtt.service;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.PourItem;
import com.amazonaws.services.iot.client.AWSIotException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

public interface MqttClientService {
    void publishGdprMessage(final String locationId) throws AWSIotException;

    void publishOtaMessage(final String locationId, final String url, final String productIndex) throws AWSIotException, NoSuchAlgorithmException, UnsupportedEncodingException;

    void publishLabelMessage(final String locationId, final String baseUrl, final String productIndex) throws AWSIotException;

    void publishPourMessage(Integer id,Integer machineId, Integer transId, Timestamp time, List<PourItem> content) throws AWSIotException;
}
