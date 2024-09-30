package com.maple.maple_boss_now.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.openmaru.dashboard.config.serializer.ZonedDateTimeDeserializer;
import io.openmaru.dashboard.config.serializer.ZonedDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@Builder
public class WebsocketResponse {
//    private WebsocketType type;     // 활용 방안이...
    private String version;
    private Integer status;
    private Object body;
    private String message;
    @Builder.Default
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime created = ZonedDateTime.now();

    private static WebsocketResponseBuilder builder() {
        return null;
    }

    public static WebsocketResponse ok(Object body) {
        WebsocketResponseBuilder WebsocketResponseBuilder = new WebsocketResponseBuilder();
        WebsocketResponseBuilder.status = HttpStatus.OK.value();
        WebsocketResponseBuilder.version = "v1";
        WebsocketResponseBuilder.body = body;
        return WebsocketResponseBuilder.build();
    }

    public static WebsocketResponse error(String message) {
        WebsocketResponseBuilder WebsocketResponseBuilder = new WebsocketResponseBuilder();
        WebsocketResponseBuilder.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        WebsocketResponseBuilder.version = "v1";
        WebsocketResponseBuilder.message = message;
        return WebsocketResponseBuilder.build();
    }

    public static class WebsocketResponseBuilder {
        public WebsocketResponse version(String version) {
            this.version = version;
            return this.build();
        }
    }
}
