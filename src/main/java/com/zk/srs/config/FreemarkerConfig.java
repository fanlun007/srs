package com.zk.srs.config;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class FreemarkerConfig {
    @Autowired
    public FreeMarkerProperties properties;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(@Value("${auto_import}") String autoImport){
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
        config.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        config.setDefaultEncoding(this.properties.getCharsetName());
        Properties settings = new Properties();
        settings.putAll(this.properties.getSettings());
        config.setFreemarkerSettings(settings);
        freemarker.template.Configuration configuration = null;
        try {
            configuration = config.createConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        setAutoImport(autoImport, configuration);
        config.setConfiguration(configuration);
        return config;
    }

    private void setAutoImport(String autoImport, freemarker.template.Configuration configuration){
        if ("_".equals(autoImport.trim())) {
            return;
        }
        String[] imports = autoImport.split(";");
        Map<String, String> importMap = new HashMap<>();
        for (String s : imports){
            String[] keyValue = s.split("as");
            if (keyValue.length != 2) {
                log.error("freemarker 配置auto_import 格式不正确");
                throw new RuntimeException("错误");
            }
            importMap.put(keyValue[1].trim(), keyValue[0].trim());
        }
        configuration.setAutoImports(importMap);
    }
}
