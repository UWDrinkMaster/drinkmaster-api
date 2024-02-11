package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.common.MqttTopic;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.PourItemDTO;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IMqttClientServiceImpl implements IMqttClientService {
    private final AWSIotMqttClient awsIotMqttClient;

    public IMqttClientServiceImpl(@Value("${awsiot.clientEndpoint}") String clientEndpoint,
                                  @Value("${awsiot.clientId}") String clientId,
                                  @Value("${awsiot.accessKey}") String accessKey,
                                  @Value("${awsiot.secretKey}") String secretKey,
                                  @Value("${awsiot.region}") String region) throws AWSIotException {

        this.awsIotMqttClient = initMqttClient(clientEndpoint, clientId, accessKey, secretKey, region);
    }

    @Override
    public void publishPourMessage(Long id, Long machineId, Long transId, LocalDateTime time, List<PourItemDTO> pourItems) throws AWSIotException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode pourObject = objectMapper.createObjectNode();
        ArrayNode contentArray = objectMapper.valueToTree(pourItems);

        pourObject.put("id", id);
        pourObject.put("transId", transId);
        pourObject.put("time", time.toString());
        pourObject.put("content", contentArray);

        AWSIotMessage pub = new AWSIotMessage(constructTopicPath(machineId, MqttTopic.POUR_TOPIC.getTopic()), AWSIotQos.QOS1, pourObject.toString());
        this.awsIotMqttClient.publish(pub);
    }

    private AWSIotMqttClient initMqttClient(String clientEndpoint, String clientId, String accessKey, String secretKey, String region) throws AWSIotException {
        AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        AWSSecurityTokenService sts = AWSSecurityTokenServiceClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
        AWSIotMqttClient awsIotMqttClient;
        final GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();
        final GetSessionTokenResult sessionTokenResult = sts.getSessionToken(getSessionTokenRequest);
        final Credentials credentials = sessionTokenResult.getCredentials();
        awsIotMqttClient = new AWSIotMqttClient(clientEndpoint, clientId, credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken());
        awsIotMqttClient.connect();
        return awsIotMqttClient;
    }

    private String constructTopicPath(Long machineId, String mqttTopic) {
        return "aws_iot/" + machineId + "/" + mqttTopic;
    }
}
