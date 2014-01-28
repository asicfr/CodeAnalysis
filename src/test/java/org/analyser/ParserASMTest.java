package org.analyser;

import java.util.List;

import junit.framework.TestCase;

import org.analyser.model.Appel;
import org.analyser.model.ClassDescription;
import org.analyser.model.MethodDescription;
import org.junit.Test;

public class ParserASMTest extends TestCase {

	@Test
	public void test1() {
		try {

			ParserASM app = new ParserASM("org.test");
			app.identifyClassesEtMethodes();
			app.identifyRelationShip();
			
			System.out.println("-------------------------------------------------------------");
			System.out.println("CREATE");
			List<ClassDescription> listCD = app.getLcd();
			for (ClassDescription classDescription : listCD) {
				System.out.println("(c" + classDescription.getNumero() + ":Clazz {name: \"" + classDescription.getCompleteName() + "\"}),");
				List<MethodDescription> listMeth = classDescription.getPossedeLesMethodes();
				for (MethodDescription methodDescription : listMeth) {
					System.out.println("(m" + methodDescription.getNumero() + ":Method {name: \"" + methodDescription.getMethodName() + "\", desc: \"" + methodDescription.getMethodDesc() + "\"}),");
					System.out.println("(c" + classDescription.getNumero() + ")-[:saMethode]->(m" + methodDescription.getNumero() + "),");
					System.out.println("(m" + methodDescription.getNumero() + ")-[:saClasse]->(c" + classDescription.getNumero() + "),");
				}
			}
			
			List<Appel> liste = app.getListeAppels();
			for (Appel appel : liste) {
				System.out.println("(m" + appel.getMethodAppelante().getNumero() + ")-[:appel]->(m" + appel.getMethodAppelee().getNumero() + "),");
			}
			
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	
}