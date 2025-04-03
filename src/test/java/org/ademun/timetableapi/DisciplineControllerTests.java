package org.ademun.timetableapi;

import org.ademun.timetableapi.controller.DisciplineController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DisciplineControllerTests {
  private final DisciplineController controller;

  @Autowired
  public DisciplineControllerTests(DisciplineController controller) {
    this.controller = controller;
  }

  @Test
  public void contextLoads() {
    assertThat(controller).isNotNull();
  }
}
