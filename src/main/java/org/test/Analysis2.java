package org.test;

import java.lang.reflect.Method;

import org.test.pck1.MyClass;

/**
 * @author slabbe
 * parser le code pour extraire : 
 * - les classes et leur natures :
 *         interface / classe / enum
 *         webservice
 * - les relations de type : 
 *         utilise / est utilisé par
 *         implémente / est implémenté par
 *         étend / est étendu par
 * - filtrer les classes hors scope (java.*, javax.*)
 * - tout envoyer dans néo4j pour pouvoir faire des requetes
 * 
 * regrouper par couche (web, service, business, dao ...)
 * 
 */
public class Analysis2 {

	
	public static void main(String[] args) throws Exception {

		Class clazz = MyClass.class;
		
		// Get all methods
		Method m[] = clazz.getDeclaredMethods();
		for (Method method : m) {
			// method.
        	System.out.println("methode: " + method.toString());
        	
        	org.objectweb.asm.commons.Method meth = org.objectweb.asm.commons.Method.getMethod(method);
        	System.out.println("     name: " + meth.getName());
        	System.out.println("     descriptor: " + meth.getDescriptor());
        	
        	
        }
        
	}

}
