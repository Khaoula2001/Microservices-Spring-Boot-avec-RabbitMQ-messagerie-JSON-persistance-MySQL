package com.khaoula.springrabbitmqproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class JacksonMessageConverter implements MessageConverter {

    private final ObjectMapper mapper;

    public JacksonMessageConverter() {
        this.mapper = new ObjectMapper();
        // Customize the ObjectMapper if needed (e.g., JavaTimeModule)
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        try {
            byte[] body = mapper.writeValueAsBytes(object);
            if (messageProperties == null) {
                messageProperties = new MessageProperties();
            }
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding("utf-8");
            messageProperties.setContentLength(body.length);
            return new Message(body, messageProperties);
        } catch (Exception e) {
            throw new MessageConversionException("Failed to convert object to JSON message", e);
        }
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        try {
            if (message == null || message.getBody() == null) {
                return null;
            }
            byte[] body = message.getBody();
            MessageProperties props = message.getMessageProperties();
            Object typeIdHeader = props != null ? props.getHeaders().get("__TypeId__") : null;
            if (typeIdHeader instanceof String) {
                try {
                    Class<?> clazz = Class.forName((String) typeIdHeader);
                    return mapper.readValue(body, clazz);
                } catch (ClassNotFoundException ex) {
                    // fallback to generic map
                }
            }
            return mapper.readValue(body, Object.class);
        } catch (Exception e) {
            throw new MessageConversionException("Failed to convert JSON message to object", e);
        }
    }
}

