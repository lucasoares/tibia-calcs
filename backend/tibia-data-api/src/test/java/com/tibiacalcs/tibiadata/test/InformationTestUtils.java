package com.tibiacalcs.tibiadata.test;

import com.tibiacalcs.tibiadata.api.entities.Information;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;

@UtilityClass
public class InformationTestUtils {

  public void validateResponseInformation(Information information) {
    Assertions.assertNotNull(information);
    Assertions.assertNotEquals(0, information.getApiVersion());
    Assertions.assertNotEquals(0, information.getExecutionTime());
    Assertions.assertNotNull(information.getLastUpdated());
    Assertions.assertNotNull(information.getTimestamp());
    Assertions.assertTrue(information.getTimestamp().getTime()
        > System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(1));
    Assertions
        .assertTrue(information.getTimestamp().getTime() >= information.getLastUpdated().getTime());
  }
}
