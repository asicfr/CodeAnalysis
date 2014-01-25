package org.test.pck2;

import org.test.pck1.MyClass;

public class MyClass2 {
	
	private MyClass propPrivate;

	private void methodPrivate() {
		propPrivate.methodPublic();
	}
	
	private void methodPrivate2() {
		propPrivate.methodPublic2(true, "");
	}

}
