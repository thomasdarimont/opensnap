/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opensnap.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

@Configuration
@Profile("default")
class StaticFilesDevConfig extends WebMvcConfigurerAdapter {

	@Value("${client.path:}")
	private String relativePath;

	private static final Logger logger = LoggerFactory.getLogger(StaticFilesDevConfig.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		try {
			FileSystem fs = FileSystems.getDefault();
			registry.addResourceHandler("/**")
					.addResourceLocations("file:" + fs.getPath(this.relativePath).toFile().getCanonicalPath() + "/")
					.setCachePeriod(0);
		} catch (IOException e) {
			logger.error("Error while adding static files handler", e);
		}
	}

}
