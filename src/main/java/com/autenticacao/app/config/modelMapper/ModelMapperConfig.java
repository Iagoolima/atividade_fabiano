package com.autenticacao.app.config.modelMapper;


import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.transportLayer.model.BodyUserRegisterModelRequest;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

            Converter<String, String> lowercaseConverter = new Converter<>() {
                @Override
                public String convert(MappingContext<String, String> context) {
                    String source = context.getSource();
                    return source != null ? source.toLowerCase() : null;
                }
            };

            modelMapper.addMappings(new PropertyMap<BodyUserRegisterModelRequest, User>() {
                @Override
                protected void configure() {
                    using(lowercaseConverter).map(source.getEmail()).setEmail(null);
                }
            });

            return modelMapper;
        }
}
