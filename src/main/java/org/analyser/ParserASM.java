package org.analyser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.analyser.model.Appel;
import org.analyser.model.ClassDescription;
import org.analyser.model.MethodDescription;
import org.analyser.util.ParserTools;
import org.analyser.visitor.ListClassVisitor;
import org.analyser.visitor.RelationClassVisitor;
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

	private final String packageName;
	private final Set<Class<?>> mySet;
	private ListClassVisitor lcv;
	private final List<Appel> listeAppels = new ArrayList<>();
	private final List<ClassDescription> lcd = new ArrayList<>(); 
	
	public ParserASM(String packageNameIn) throws Exception {
		super();
		this.packageName = packageNameIn;
		this.mySet = ParserTools.getClasses(this.packageName);
		System.out.println("detected classes " + mySet.size());
	}

	public String getPackageName() {
		return packageName;
	}

	public void identifyClassesEtMethodes() throws Exception {
		System.out.println("-------------------------------------------------------------");
		this.lcv = new ListClassVisitor(this);
		for (Class<?> class1 : mySet) {
			String className = class1.getName();
			System.out.println("    detect classe " + className);
			String classAsPath = className.replace('.', '/') + ".class";
			InputStream stream = class1.getClassLoader().getResourceAsStream(classAsPath);
			
			// premiere passe pour répertorier les méthodes de chaque classes
			ClassReader reader = new ClassReader(stream);
			reader.accept(lcv, 0);
		}
		
		lcd.addAll(this.lcv.getCd());
	}

	public void identifyRelationShip() throws Exception {
		// deuxieme passe pour lister les relations
		System.out.println("-------------------------------------------------------------");
		List<ClassDescription> listC = lcv.getCd();
		for (ClassDescription classDescription : listC) {
			// pour chaque classe detectee
			List<MethodDescription> listM = classDescription.getPossedeLesMethodes();
			for (MethodDescription methodDescription : listM) {
				// pour chaque methode detectee, on va tenter de detecter qui l'utilise
				final RelationClassVisitor rcv = new RelationClassVisitor(methodDescription, lcd);
				for (Class<?> class1 : mySet) {
					String className = class1.getName();
					System.out.println("    detect classe " + className);
					String classAsPath = className.replace('.', '/') + ".class";
					InputStream stream = class1.getClassLoader().getResourceAsStream(classAsPath);
					
					// premiere passe pour répertorier les méthodes de chaque classes
					ClassReader reader = new ClassReader(stream);
					reader.accept(rcv, 0);
				}
				
				listeAppels.addAll(rcv.getAppels());
			}
		}
	}

	public List<Appel> getListeAppels() {
		return listeAppels;
	}

	public List<ClassDescription> getLcd() {
		return lcd;
	}

}
