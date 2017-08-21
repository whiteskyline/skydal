
package com.horizon.util.mock;

public final class SomeClass {
    public String doSomething(int i) {
        SomeOtherClass other = new SomeOtherClass("data");
        return other.performSomeOperation(i);
    }
}
