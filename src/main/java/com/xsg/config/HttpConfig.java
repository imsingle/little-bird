package com.xsg.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xue shengguo
 * @datetime 17/8/10 下午5:29
 */
@Data
@Slf4j
public class HttpConfig {
    public static final String CONFIG_KEY_CONN_TIMEOUT = "little.bird.http.conn.timeout.ms";
    public static final String CONFIG_KEY_READ_TIMEOUT = "little.bird.http.read.timeout.ms";
    public static final String CONFIG_KEY_WRITE_TIMEOUT = "little.bird.http.write.timeout.ms";

    private static final String CONFIG_FILE = "src/main/java/resources/httpclient.properties";
    private Properties properties;

    private String conn_timeout_ms;
    private String read_timeout_ms;
    private String write_timeout_ms;

    public static HttpConfig of() {
        return new HttpConfig();
    }

    private HttpConfig() {
        try {
            if (System.getProperty(CONFIG_KEY_CONN_TIMEOUT) != null) conn_timeout_ms = System.getProperty(CONFIG_KEY_CONN_TIMEOUT);
            if (System.getProperty(CONFIG_KEY_READ_TIMEOUT) != null) System.getProperty(CONFIG_KEY_READ_TIMEOUT);
            if (System.getProperty(CONFIG_KEY_WRITE_TIMEOUT) != null) System.getProperty(CONFIG_KEY_WRITE_TIMEOUT);
            load();
            if (!properties.isEmpty()) {
                if (StringUtils.isBlank(conn_timeout_ms)) conn_timeout_ms = properties.getProperty(CONFIG_KEY_CONN_TIMEOUT);
                if (StringUtils.isBlank(read_timeout_ms)) read_timeout_ms = properties.getProperty(CONFIG_KEY_READ_TIMEOUT);
                if (StringUtils.isBlank(write_timeout_ms)) write_timeout_ms = properties.getProperty(CONFIG_KEY_WRITE_TIMEOUT);
            }
        } catch (FileNotFoundException e) {
            log.error("default http config file is missing", e);
        }
    }

    public void fill(ConcurrentHashMap map) {
        map.put(CONFIG_KEY_CONN_TIMEOUT, conn_timeout_ms);
        map.put(CONFIG_KEY_READ_TIMEOUT, read_timeout_ms);
        map.put(CONFIG_KEY_WRITE_TIMEOUT, write_timeout_ms);
    }

    private void load() throws FileNotFoundException {
        File file;
        if (!(file = new File(CONFIG_FILE)).exists()) {
            throw new FileNotFoundException();
        }
        InputStream inputStream = new FileInputStream(file);
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("load http config file failed", e);
        }
    }

    public static void main(String[] args) {
        HttpConfig httpConfig = HttpConfig.of();
        System.out.println(httpConfig);
    }
}
