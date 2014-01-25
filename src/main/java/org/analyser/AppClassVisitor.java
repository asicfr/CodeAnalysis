package org.analyser;

import org.analyser.model.MethodDescription;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AppClassVisitor extends ClassVisitor {

	private final MethodDescription methodAppelee;
	private final ParserASM parserASM;
	private final AppMethodVisitor mv;
	
	private String source;
	private String className;
	private String methodName;
	private String methodDesc;

	public AppClassVisitor(ParserASM parserASMIn, MethodDescription methodAppeleeIn) {
		super(Opcodes.ASM4);
		this.parserASM = parserASMIn;
		this.methodAppelee = methodAppeleeIn;
		this.mv = new AppMethodVisitor(this.parserASM, this, this.methodAppelee);
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		className = name;
		System.out.println("    visite classe " + className);
	}

	public void visitSource(String source, String debug) {
		this.source = source;
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		methodName = name;
		methodDesc = desc;
		System.out.println("        visite methode " + methodName);
		return mv;
	}

	public String getSource() {
		return source;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMethodDesc() {
		return methodDesc;
	}
	
}
