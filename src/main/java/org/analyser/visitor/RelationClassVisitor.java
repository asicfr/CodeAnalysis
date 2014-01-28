package org.analyser.visitor;

import java.util.ArrayList;
import java.util.List;

import org.analyser.model.Appel;
import org.analyser.model.ClassDescription;
import org.analyser.model.MethodDescription;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RelationClassVisitor extends ClassVisitor {

	private final MethodDescription methodAppelee;
	private final RelationMethodVisitor mv;
	private final List<ClassDescription> lcd;
	
	// classe visitee
	private String source;
	private String className;
	private ClassDescription classDescription;

	// methode visitee
	private int access;
	private String nameMethod;
	private String descMethod;
	private String signatureMethod;
	private String[] exceptionsMethod;
	private MethodDescription methAppelante;

	private List<Appel> appels = new ArrayList<>();
	
	public RelationClassVisitor(MethodDescription methodAppeleeIn, List<ClassDescription> lcdIn) {
		super(Opcodes.ASM4);
		System.out.println("instanciate AppClassVisitor");
		this.methodAppelee = methodAppeleeIn;
		this.mv = new RelationMethodVisitor(this, this.methodAppelee);
		this.lcd = lcdIn;
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.className = name;
		System.out.println("    visite classe " + this.className);
		
		this.classDescription = findClassDescription(name);
	}

	public void visitSource(String source, String debug) {
		this.source = source;
		System.out.println("    visite source " + source);
	}

	public MethodVisitor visitMethod(int accessIn, String nameIn, String descIn, String signatureIn, String[] exceptionsIn) {
		this.access = accessIn;
		this.nameMethod = nameIn;
		this.descMethod = descIn;
		this.signatureMethod = signatureIn;
		this.exceptionsMethod = exceptionsIn;

		this.methAppelante = findMethodDescription(this.className, this.nameMethod, this.descMethod);
		System.out.println("        visite methode " + this.nameMethod);
		System.out.println("           methode access=" + access + " name=" + nameMethod + " desc=" + descMethod + " signature=" + signatureMethod);
		return mv;
	}

	public MethodDescription getMethAppelante() {
		return methAppelante;
	}

	public String getSource() {
		return this.source;
	}

	public String getClassName() {
		return this.className;
	}

	public ClassDescription getClassDescription() {
		return classDescription;
	}

	public int getAccess() {
		return access;
	}

	public String getNameMethod() {
		return nameMethod;
	}

	public String getDescMethod() {
		return descMethod;
	}

	public String getSignatureMethod() {
		return signatureMethod;
	}

	public String[] getExceptionsMethod() {
		return exceptionsMethod;
	}

	public void add(Appel appel) {
		appels.add(appel);
	}

	public List<Appel> getAppels() {
		return appels;
	}

	private ClassDescription findClassDescription(String name) {
		for (ClassDescription oneClassDescription : lcd) {
			if ((oneClassDescription != null) && (oneClassDescription.getCompleteName().equals(name))) {
				return oneClassDescription;
			}
		}
		return null;
	}

	private MethodDescription findMethodDescription(String nameClass, String nameMeth, String descMeth) {
		for (ClassDescription oneClassDescription : lcd) {
			if ((oneClassDescription != null) && (oneClassDescription.getCompleteName().equals(nameClass))) {
				for (MethodDescription oneMethodDescription : oneClassDescription.getPossedeLesMethodes()) {
					if ((oneMethodDescription != null) && 
							(oneMethodDescription.getMethodName().equals(nameMeth))
									&& (oneMethodDescription.getMethodDesc().equals(descMeth))) {
						return oneMethodDescription;
					}
				}
				return null;
			}
		}
		return null;
	}

}
