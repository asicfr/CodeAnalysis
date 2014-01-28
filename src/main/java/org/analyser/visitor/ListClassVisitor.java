package org.analyser.visitor;

import java.util.ArrayList;
import java.util.List;

import org.analyser.ParserASM;
import org.analyser.model.ClassDescription;
import org.analyser.model.EnumTypeClass;
import org.analyser.model.MethodDescription;
import org.analyser.util.ParserTools;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Method;

public class ListClassVisitor extends ClassVisitor {

	// liste de toutes les classes visitees
	private final List<ClassDescription> cd = new ArrayList<>();

	// classe visitee
	private String className;
	private ClassDescription classDescription;

	public ListClassVisitor(ParserASM parserASMIn) {
		super(Opcodes.ASM4);
		System.out.println("instanciate ListClassVisitor");
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.className = name;
		System.out.println("    visite classe " + this.className);
		this.classDescription = new ClassDescription(name, EnumTypeClass.CLASSE);
		this.cd.add(this.classDescription);
	}

	public void visitSource(String source, String debug) {
		System.out.println("    visite source " + source);
	}

	public MethodVisitor visitMethod(int accessIn, String nameIn, String descIn, String signatureIn, String[] exceptionsIn) {
		final Method methode = ParserTools.extractMeth(nameIn, descIn);
		
		// TODO filtrer les méthodes a juste celle de la classe ?
		
		MethodDescription meth = new MethodDescription(nameIn, descIn, this.classDescription, methode);
		this.classDescription.addPossedeLesMethodes(meth);
		System.out.println("        visite methode " + nameIn);
		return null;
	}

	public List<ClassDescription> getCd() {
		return cd;
	}
	
}
