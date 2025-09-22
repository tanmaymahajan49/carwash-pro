package com.crop.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI myconfig() {

		return new OpenAPI().info(new Info().title("🌱🌿Crop Service from CropDeal🌿🌱").description("By Kshitij"))
				.servers(Arrays.asList(new Server().url("http://localhost:8085").description("Crop Service"),
						new Server().url("http://localhost:8070").description("Dealer Service")));
	}

}
