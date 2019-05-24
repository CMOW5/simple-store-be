package com.cristian.simplestore.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Config config = new Config();
  
    public static class Config {
        private String path;
        private String port;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
        
        public String getUrl() {
          return path + ":" + port;
        }
        
        public String getApiUrl() {
          return getUrl() + "/api";
        }
    }

    public Config getConfig() {
        return config;
    }
}
