package com.horizon.util.mock;

public final class SomeOtherClass
{
	private final String value;

	public SomeOtherClass(String value) { this.value = value; }

	public String performSomeOperation(int i) { return value + i; }
}