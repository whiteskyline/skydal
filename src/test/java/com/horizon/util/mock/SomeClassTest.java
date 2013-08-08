package com.horizon.util.mock;

import static org.junit.Assert.*;
import org.junit.*;
import mockit.*;

public final class SomeClassTest
{
	@Tested SomeClass sut;
	@NonStrict SomeOtherClass mock;
	
	@Test
	public void testSomething()
	{
		new Expectations() {{ mock.performSomeOperation(anyInt); result = "mocked"; }};
		
		assertEquals("mocked", sut.doSomething(123));
	}
}