package ca.uwaterloo.drinkmasterapi.common;

public enum MqttTopic {
    POUR_TOPIC("cmd/pour"),
    GETSTAT_TOPIC("cmd/getstat"),
    STAT_TOPIC("info/stat"),
    COMPLETE_TOPIC("info/complete"),
    SETCFG_TOPIC("cmd/setcfg");

    private final String topic;

    MqttTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}