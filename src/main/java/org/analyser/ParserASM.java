package org.analyser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import org.analyser.model.Appel;
import org.analyser.model.MethodDescription;
import org.analyser.util.ParserTools;
import org.analyser.visitor.AppClassVisitor;
import org.objectweb.asm.ClassReader;

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
public class ParserASM {

	private final MethodDescription methodAppelee;
	private final String packageName;
	private final AppClassVisitor cv;
	private final ArrayList<Appel> callees;
	
	public ParserASM(String packageNameIn, MethodDescription methodAppeleeIn) {
		super();
		this.packageName = packageNameIn;
		this.methodAppelee = methodAppeleeIn;
		this.cv = new AppClassVisitor(this, this.methodAppelee);
		this.callees = new ArrayList<Appel>();
	}

	public MethodDescription getMethodAppelee() {
		return methodAppelee;
	}

	public String getPackageName() {
		return packageName;
	}

	public AppClassVisitor getCv() {
		return cv;
	}

	public ArrayList<Appel> getCallees() {
		return callees;
	}

	public void findCallingMethodsInJar() throws Exception {
		Set<Class<?>> mySet = ParserTools.getClasses(packageName);
		System.out.println("detected classes " + mySet.size());
		for (Class<?> class1 : mySet) {
			String className = class1.getName();
			System.out.println("    detect classe " + className);
			String classAsPath = className.replace('.', '/') + ".class";
			InputStream stream = class1.getClassLoader().getResourceAsStream(classAsPath);
			ClassReader reader = new ClassReader(stream);
			reader.accept(cv, 0);
		}
	}
	
}