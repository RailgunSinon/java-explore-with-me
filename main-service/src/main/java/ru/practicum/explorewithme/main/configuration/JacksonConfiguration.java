package ru.practicum.explorewithme.main.configuration;

import static ru.practicum.explorewithme.main.GlobalStaticProperties.DATE_FORMAT;
import static ru.practicum.explorewithme.main.GlobalStaticProperties.DATE_TIME_FORMAT;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> {
            builder.deserializers(new LocalDateDeserializer(DATE_FORMAT));
            builder.deserializers(new LocalDateTimeDeserializer(DATE_TIME_FORMAT));

            builder.serializers(new LocalDateSerializer(DATE_FORMAT));
            builder.serializers(new LocalDateTimeSerializer(DATE_TIME_FORMAT));
        };
    }
}