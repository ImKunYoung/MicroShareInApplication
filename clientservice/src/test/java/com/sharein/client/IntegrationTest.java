package com.sharein.client;

import com.sharein.client.ClientserviceApp;
import com.sharein.client.config.AsyncSyncConfiguration;
import com.sharein.client.config.EmbeddedKafka;
import com.sharein.client.config.EmbeddedRedis;
import com.sharein.client.config.EmbeddedSQL;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ClientserviceApp.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedKafka
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
