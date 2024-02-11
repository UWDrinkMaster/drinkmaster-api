package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.common.MqttTopic;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.PourItemDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.event.OrderCompletionEvent;
import ca.uwaterloo.drinkmasterapi.feature.order.event.PourDrinkEvent;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.iot.client.*;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IMqttClientServiceImpl implements IMqttClientService {
    private final AWSIotMqttClient awsIotMqttClient;
    private final ApplicationEventPublisher eventPublisher;

    public IMqttClientServiceImpl(@Value("${awsiot.clientEndpoint}") String clientEndpoint,
                                  @Value("${awsiot.clientId}") String clientId,
                                  @Value("${awsiot.accessKey}") String accessKey,
                                  @Value("${awsiot.secretKey}") String secretKey,
                                  @Value("${awsiot.region}") String region,
                                  ApplicationEventPublisher eventPublisher) throws AWSIotException {
        this.awsIotMqttClient = initMqttClient(clientEndpoint, clientId, accessKey, secretKey, region);
        subscribeToTopic(constructTopicPath(1L, MqttTopic.COMPLETE_TOPIC.getTopic()));
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void onPourDrinkEvent(PourDrinkEvent event) {
        try {
            publishPourMessage(event.getOrderId(),
                    event.getMachineId(),
                    event.getTransId(),
                    event.getOrderTime(),
                    event.getPourItems());
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish pour message", e);
        }
    }

    @Override
    public void publishPourMessage(Long id, Long machineId, Long transId, LocalDateTime time, List<PourItemDTO> pourItems) throws AWSIotException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode pourObject = objectMapper.createObjectNode();
        ArrayNode contentArray = objectMapper.valueToTree(pourItems);

        pourObject.put("id", id);
        pourObject.put("transId", transId);
        pourObject.put("time", time.toString());
        pourObject.set("content", contentArray);

        AWSIotMessage pub = new AWSIotMessage(constructTopicPath(machineId, MqttTopic.POUR_TOPIC.getTopic()), AWSIotQos.QOS1, pourObject.toString());
        this.awsIotMqttClient.publish(pub);
    }

    private void processMessage(AWSIotMessage message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message.getStringPayload());
            Long orderId = jsonNode.get("transId").asLong();
            boolean isCompleted = jsonNode.get("val").asBoolean();

            OrderCompletionEvent completionEvent = new OrderCompletionEvent(this, orderId, isCompleted);
            eventPublisher.publishEvent(completionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle message processing error
        }
    }

    private void subscribeToTopic(String topic) throws AWSIotException {
        try {
            awsIotMqttClient.subscribe(new AWSIotTopic(topic, AWSIotQos.QOS0) {
                @Override
                public void onMessage(AWSIotMessage message) {
                    // This method is called whenever a message is received on the subscribed topic
                    processMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
