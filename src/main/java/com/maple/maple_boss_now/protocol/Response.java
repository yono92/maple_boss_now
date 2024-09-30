package com.maple.maple_boss_now.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.openmaru.commons.util.JsonUtils;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(value = { "messageParam", "exception", "headers" })
public class Response {
    private static Logger logger = LoggerFactory.getLogger(Response.class);

    private Integer status;
    @Builder.Default
    private long currentTime = System.currentTimeMillis();
    private String message;
    private Object [] messageParam;
    private Throwable exception;
    private HttpHeaders headers;
    private Object body;
    @Builder.Default
    private List<String> invalidVariables = Collections.emptyList();

    private static ResponseBuilder builder() {
        return null;
    }

    public static ResponseBuilder ok() {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.status = HttpStatus.OK.value();
        return responseBuilder;
    }

    public static Response ok(Object body) {
        ResponseBuilder responseBuilder = ok();
        responseBuilder.status = HttpStatus.OK.value();
        responseBuilder.body = body;
        return responseBuilder.build();
    }

    public static Response created() {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.status = HttpStatus.CREATED.value();
        return responseBuilder.build();
    }

    public static Response created(Object body) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.status = HttpStatus.CREATED.value();
        responseBuilder.body(body);
        return responseBuilder.build();
    }

    public static ResponseBuilder error(String message) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        responseBuilder.message = message;
        return responseBuilder;
    }

    public static ResponseBuilder status(HttpStatus httpStatus) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.status = httpStatus.value();
        return responseBuilder;
    }

    public static <T> T toBody(String contentAsString, Class<T> clz) {
        T object = null;
        try {
            Map map = JsonUtils.getJSONStringToObject(contentAsString, Map.class);
            if (map != null) {;
                return new ObjectMapper().convertValue(map.get("body"), clz);
            }

        } catch (Exception e) {
            logger.error("===== error.: {}", e);
        }

        return object;
    }

    public static class ResponseBuilder {
        public ResponseBuilder header(String key, String value) {
            if (this.headers == null) {
                this.headers = new HttpHeaders();
            }
            this.headers.add(key, value);
            return this;
        }

        public Response body(Object body) {
            this.body = body;
            if (this.status == null) {
                this.status = HttpStatus.OK.value();
            }
            return this.build();
        }

        private ResponseBuilder header(HttpHeaders header) {
            return null;
        }

        private ResponseBuilder status(HttpStatus httpStatus) {
            return null;
        }
    }
}
