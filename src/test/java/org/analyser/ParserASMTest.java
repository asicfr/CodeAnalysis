package org.analyser;

import junit.framework.TestCase;

import org.analyser.model.Appel;
import org.analyser.model.ClassDescription;
import org.analyser.model.EnumTypeClass;
import org.analyser.model.MethodDescription;
import org.junit.Test;
import org.objectweb.asm.commons.Method;
import org.test.pck1.MyClass;

public class ParserASMTest extends TestCase {

	@Test
	public void test1() {
		try {
			// ClassDescription classDescription = new ClassDescription();
			// MethodDescription methodAppelee = new MethodDescription("void methodPublic()", methodDesc, source, classDescription);
			
			// classname:org/test/pck2/MyClass2
			// source:MyClass2.java
			// methodName:methodPrivate
			// methodDesc:()V
			
			// ParserASM app = new ParserASM("org.test", "org/test/pck1/MyClass", "void methodPublic()");
			// Java method declaration, without argument names, of the form "returnType name (argumentType1, ... argumentTypeN)",
			// where the types are in plain Java (e.g. "int", "float", "java.util.List", ...). Classes of the java.lang package can be specified by their unqualified name; 
			// all other classes names must be fully qualified.
			Class clazz = MyClass.class;
			
			// Get all methods
			java.lang.reflect.Method m[] = clazz.getDeclaredMethods();
        	Method meth = null;
			for (java.lang.reflect.Method method : m) {
	        	if ("methodPublic2".equals(method.getName())) {
	        		meth = Method.getMethod(method);
	        	}
	        }

			
			
			// String methodNameIn, String methodDescIn, String sourceIn, ClassDescription estPorteeParIn)
			ClassDescription classDescription = new ClassDescription("org/test/pck1/MyClass", EnumTypeClass.CLASSE);
			MethodDescription methodAppelee = new MethodDescription(meth.getName(), meth.getDescriptor(), clazz.getSimpleName(), classDescription, meth);
			
			// visitMethodInsn name=methodPublic2 desc=(ZLjava/lang/String;)Ljava/util/List;
			ParserASM app = new ParserASM("org.test", methodAppelee);
			app.findCallingMethodsInJar();

			System.out.println("-----------------------------------------");
			System.out.println("appels trouves :" + app.getCallees().size());
			int i = 1;
//			for (Appel c : app.getCallees()) {
//				System.out.println("   " + i + "\nclassname:" + c.className 
//						+ "\nsource:" + c.source 
//						+ "\nline:" + c.line 
//						+ "\nmethodName:" + c.methodName 
//						+ "\nmethodDesc:" + c.methodDesc);
//				i++;
//			}
			
			assertEquals("1 appel trouvé", 1, app.getCallees().size() );
//			assertEquals("className", "org/test/pck2/MyClass2", app.getCallees().get(0).className );
//			assertEquals("methodName", "methodPrivate", app.getCallees().get(0).methodName );
//			assertEquals("line", 11, app.getCallees().get(0).line );

			
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	// 	// public List<String> methodPublic2(boolean toto, String test) {

	
}