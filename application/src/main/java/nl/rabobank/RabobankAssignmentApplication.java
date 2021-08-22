package nl.rabobank;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabobankAssignmentApplication {

  public static void main(String[] args) {
    SpringApplication.run(RabobankAssignmentApplication.class, args);

    Sentry.init(options -> {
      options.setDsn("https://155ba8755a504cc38377790f74aaba96@o968895.ingest.sentry.io/5920155");
      options.setTracesSampleRate(1.0);
      options.setDebug(true);
    });
  }

}
