package nl.rabobank.config;

import com.newrelic.telemetry.Attributes;
import com.newrelic.telemetry.micrometer.NewRelicRegistry;
import com.newrelic.telemetry.micrometer.NewRelicRegistryConfig;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

@Profile("!test")
@Configuration
@AutoConfigureBefore({
  CompositeMeterRegistryAutoConfiguration.class,
  SimpleMetricsExportAutoConfiguration.class
})
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnClass(NewRelicRegistry.class)
public class NewRelicMetricsExportAutoConfiguration {

  @Value("${management.metrics.export.newrelic.account-id}")
  private String account;

  @Value("${management.metrics.export.newrelic.api-key}")
  private String apiKey;

  @Bean
  public NewRelicRegistryConfig newRelicConfig() {
    return new NewRelicRegistryConfig() {
      @Override
      public String get(String key) {
        return null;
      }

      @Override
      public String uri() {
        return "https://insights-collector.eu01.nr-data.net/v1/accounts/"+ account +"/events";
      }

      @Override
      public String apiKey() {
        return apiKey;
      }

      @Override
      public Duration step() {
        return Duration.ofSeconds(5);
      }

      @Override
      public String serviceName() {
        return "Robobank Assignment";
      }

    };
  }

  @Bean
  public NewRelicRegistry newRelicMeterRegistry(NewRelicRegistryConfig config)
    throws UnknownHostException {
    var newRelicRegistry = NewRelicRegistry.builder(config)
        .commonAttributes(
          new Attributes()
            .put("host", InetAddress.getLocalHost().getHostName()))
        .build();
    newRelicRegistry.config().meterFilter(MeterFilter.denyNameStartsWith("jvm.threads"));
    newRelicRegistry.start(new NamedThreadFactory("newrelic.micrometer.registry"));
    return newRelicRegistry;
  }
}
