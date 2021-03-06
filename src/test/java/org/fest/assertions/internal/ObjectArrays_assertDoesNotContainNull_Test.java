/*
 * Created on Nov 29, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.ShouldNotContainNull.shouldNotContainNull;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestFailures.expectedAssertionErrorNotThrown;
import static org.fest.test.ExpectedException.none;
import static org.fest.util.Arrays.array;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.fest.assertions.description.Description;
import org.fest.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link ObjectArrays#assertDoesNotContainNull(Description, Object[])}</code>.
 *
 * @author Yvonne Wang
 */
public class ObjectArrays_assertDoesNotContainNull_Test {

  @Test
  public void should_pass_if_actual_does_not_contain_null() {
    arrays.assertDoesNotContainNull(description, actual);
  }

  @Test
  public void should_pass_if_actual_is_empty() {
    actual = new Object[0];
    arrays.assertDoesNotContainNull(description, actual);
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expect(AssertionError.class, actualIsNull());
    arrays.assertDoesNotContainNull(description, null);
  }

  @Test
  public void should_fail_if_actual_contains_null() {
    actual = array("Luke", "Yoda", null);
    try {
      arrays.assertDoesNotContainNull(description, actual);
    } catch (AssertionError e) {
      verify(failures).failure(description, shouldNotContainNull(actual));
      return;
    }
    expectedAssertionErrorNotThrown();
  }

  @Rule
  public ExpectedException thrown = none();

  private ObjectArrays arrays;
  private Failures failures;
  private Description description;
  private Object[] actual = array("Yoda", "Luke", "Leia");

  @Before
  public void setUp() {
    arrays = ObjectArrays.instance();
    Arrays parent = Arrays.instance();
    failures = spy(new Failures());
    parent.failures = failures;
    description = new TestDescription("testing");
  }
}
