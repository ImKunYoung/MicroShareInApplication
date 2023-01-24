package com.sharein.apartment;

import com.sharein.apartment.ApartmentserviceApp;
import com.sharein.apartment.config.AsyncSyncConfiguration;
import com.sharein.apartment.config.EmbeddedKafka;
import com.sharein.apartment.config.EmbeddedRedis;
import com.sharein.apartment.config.EmbeddedSQL;
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
@SpringBootTest(classes = { ApartmentserviceApp.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedKafka
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
