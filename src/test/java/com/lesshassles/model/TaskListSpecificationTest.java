package com.lesshassles.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnit44Runner;

@RunWith(MockitoJUnit44Runner.class)
public class TaskListSpecificationTest {

    @Test
    public void shouldBeSatisfiedByValidName() {
	String validName = "abcABC0 123 '().- a-";
	TaskList taskList = new TaskList(validName);

	assertTrue(new TaskListSpecification().isSatisfiedBy(taskList));
    }

    @Test
    public void shouldNotBeSatisfiedByNullTaskList() {
	TaskList taskList = null;

	assertFalse(new TaskListSpecification().isSatisfiedBy(taskList));
    }

    @Test
    public void shouldNotBeSatisfiedByEmptyName() {
	final String emptyName = "";
	TaskList taskList = new TaskList(emptyName);

	assertFalse(new TaskListSpecification().isSatisfiedBy(taskList));
    }

    @Test
    public void shouldNotBeSatisfiedByNotTrimmedName() {
	final String emptyName = " abc ";
	TaskList taskList = new TaskList(emptyName);

	assertFalse(new TaskListSpecification().isSatisfiedBy(taskList));
    }

    @Test
    public void shouldNotBeSatisfiedByNameWithMultipleSubsequentWhitespaces() {
	final String emptyName = "a\tb  c";
	TaskList taskList = new TaskList(emptyName);

	assertFalse(new TaskListSpecification().isSatisfiedBy(taskList));
    }

    @Test
    public void shouldNotBeSatisfiedByNameWithInvalidCharacters() {
	final String nameWithInvalidCharacters = " !$,:;\"";
	TaskList taskList = new TaskList(nameWithInvalidCharacters);

	assertFalse(new TaskListSpecification().isSatisfiedBy(taskList));
    }
}
