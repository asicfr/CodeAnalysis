package org.analyser;

import org.analyser.model.ClassDescription;
import org.analyser.model.EnumTypeClass;
import org.analyser.model.MethodDescription;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

public class AppClassVisitor extends ClassVisitor {

	private final MethodDescription methodAppelee;
	private final ParserASM parserASM;
	private final AppMethodVisitor mv;

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
	
	public AppClassVisitor(ParserASM parserASMIn, MethodDescription methodAppeleeIn) {
		super(Opcodes.ASM4);
		System.out.println("instanciate AppClassVisitor");
		this.parserASM = parserASMIn;
		this.methodAppelee = methodAppeleeIn;
		this.mv = new AppMethodVisitor(this.parserASM, this, this.methodAppelee);
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.className = name;
		System.out.println("    visite classe " + this.className);
		this.classDescription = new ClassDescription(name, EnumTypeClass.CLASSE);
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

		final String name = this.getNameMethod();
		final Type returnType = Type.getReturnType(this.getDescMethod());
		final Type[] argumentTypes = Type.getArgumentTypes(this.getDescMethod());
		final Method methodeAppelante = new Method(name, returnType, argumentTypes);
		
		this.methAppelante = new MethodDescription(this.getNameMethod(), 
				this.getDescMethod(), this.getSource(), 
				this.getClassDescription(), methodeAppelante);
		
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
	
}
