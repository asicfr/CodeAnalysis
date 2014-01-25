package org.analyser;

import org.analyser.model.Appel;
import org.analyser.model.MethodDescription;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Method;

public class AppMethodVisitor extends MethodVisitor {

	private final MethodDescription methodAppelee;
	private final ParserASM parserASM;
	private final AppClassVisitor appClassVisitor;
	
	boolean callsTarget;
	int line;

	public AppMethodVisitor(ParserASM parserASMIn, AppClassVisitor appClassVisitorIn, MethodDescription methodAppeleeIn) {
		super(Opcodes.ASM4);
		System.out.println("instanciate AppMethodVisitor");
		this.parserASM = parserASMIn;
		this.appClassVisitor = appClassVisitorIn;
		this.methodAppelee = methodAppeleeIn;
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		// opcode, owner, name et desc definissent la methode appelee
		if (owner.equals(this.methodAppelee.getEstPorteePar().getCompleteName()) && name.equals(this.getMeth().getName()) && desc.equals(this.getMeth().getDescriptor())) {
			System.out.println("            Called meth : owner=" + this.methodAppelee.getEstPorteePar().getCompleteName() 
					+ " source=" + this.methodAppelee.getSource() 
					+ " name=" + this.methodAppelee.getMethodName() + " desc=" + this.methodAppelee.getMethodDesc());
			System.out.println("            Calling meth : owner=" + this.methodAppelee.getEstPorteePar().getCompleteName() 
					+ " source=" + this.methodAppelee.getSource() 
					+ " name=" + this.methodAppelee.getMethodName() + " desc=" + this.methodAppelee.getMethodDesc());
			callsTarget = true;
		}
	}

	public void visitCode() {
		callsTarget = false;
	}

	public void visitLineNumber(int line, Label start) {
		this.line = line;
	}

	public void visitEnd() {
		if (callsTarget) {
			Appel appel = new Appel(this.appClassVisitor.getMethAppelante(), this.parserASM.getMethodAppelee(), this.line);
			this.parserASM.getCallees().add(appel);
		}
	}

	private Method getMeth() {
		return this.parserASM.getMethodAppelee().getMeth();
	}

}

