package ca.uwaterloo.drinkmasterapi.feature.mqtt.service;

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
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MqttClientServiceImpl implements MqttClientService {

    private static final Logger logger = LoggerFactory.getLogger(MqttClientServiceImpl.class);

    // examples
    private static final String GDPR_TOPIC = "nvio/gdpr";
    private static final String OTA_TOPIC = "nvio/ota";
    private static final String LABEL_TOPIC = "nvio/label";
    // Todo: create Topic in AWS IOT
    private static final String POUR_TOPIC = "cmd/pour";
    private static final String GETSTAT_TOPIC = "cmd/getstat";
    private static final String STAT_TOPIC = "info/stat";
    private static final String COMPLETE_TOPIC = "info/complete";
    private static final String SETCFG_TOPIC = "cmd/setcfg";
    private final AWSIotMqttClient awsIotMqttClient;

    public MqttClientServiceImpl(@Value("${awsiot.clientEndpoint}") String clientEndpoint,
                                 @Value("${awsiot.clientId}") String clientId,
                                 @Value("${awsiot.accessKey}") String accessKey,
                                 @Value("${awsiot.secretKey}") String secretKey,
                                 @Value("${awsiot.region}") String region) throws AWSIotException {

        this.awsIotMqttClient = initMqttClient(clientEndpoint, clientId, accessKey, secretKey, region);
    }

    @Override
    public void publishGdprMessage(final String locationId) throws AWSIotException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode gdprObject = objectMapper.createObjectNode();
        gdprObject.put("locationId", locationId);
        AWSIotMessage pub = new AWSIotMessage(GDPR_TOPIC, AWSIotQos.QOS1, gdprObject.toString());
        this.awsIotMqttClient.publish(pub);
        logger.info("gdpr message is sent");
    }

    @Override
    public void publishOtaMessage(final String locationId, final String url, final String productIndex) throws AWSIotException, NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest salt = MessageDigest.getInstance("MD5");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        String digest = bytesToHex(salt.digest());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode otaObject = objectMapper.createObjectNode();
        otaObject.put("locationId", locationId);
        otaObject.put("productIndex", productIndex);
        otaObject.put("md5", digest);
        otaObject.put("url", url);
        otaObject.put("device", "tablet/label");
        otaObject.put("build", "prod/dev");
        AWSIotMessage pub = new AWSIotMessage(OTA_TOPIC, AWSIotQos.QOS1, otaObject.toString());
        this.awsIotMqttClient.publish(pub);
        logger.info("ota message is sent");
    }

    @Override
    public void publishLabelMessage(final String locationId, final String baseUrl, final String productIndex) throws AWSIotException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode labelObject = objectMapper.createObjectNode();
        labelObject.put("locationId", locationId);
        labelObject.put("productIndex", productIndex);
        labelObject.put("baseUrl", baseUrl);
        AWSIotMessage pub = new AWSIotMessage(LABEL_TOPIC, AWSIotQos.QOS1, labelObject.toString());
        this.awsIotMqttClient.publish(pub);
        logger.info("label message is sent");
    }

    @Override
    public void publishPourMessage(Integer id, Integer machineId, Integer transId, String time, String content) throws AWSIotException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode pourObject = objectMapper.createObjectNode();
        pourObject.put("id", id);
        pourObject.put("transId", transId);
        pourObject.put("time", time);
        pourObject.put("content", content);
        AWSIotMessage pub = new AWSIotMessage("aws_iot/" + machineId + "/" + POUR_TOPIC, AWSIotQos.QOS1, pourObject.toString());
        this.awsIotMqttClient.publish(pub);
        logger.info("pour cmd is sent");
    }

    private AWSIotMqttClient initMqttClient(String clientEndpoint, String clientId, String accessKey, String secretKey, String region) throws AWSIotException {
        AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        AWSSecurityTokenService sts = AWSSecurityTokenServiceClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
        AWSIotMqttClient awsIotMqttClient = null;
        final GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();
        final GetSessionTokenResult sessionTokenResult = sts.getSessionToken(getSessionTokenRequest);
        final Credentials credentials = sessionTokenResult.getCredentials();
        awsIotMqttClient = new AWSIotMqttClient(clientEndpoint, clientId, credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken());
        if (awsIotMqttClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        }
        awsIotMqttClient.connect();
        logger.info("aws MQTT client is connected");
        return awsIotMqttClient;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
