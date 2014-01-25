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
		this.parserASM = parserASMIn;
		this.appClassVisitor = appClassVisitorIn;
		this.methodAppelee = methodAppeleeIn;
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        // Called meth : owner=MyClass source=MyClass name=methodPublic2 desc=(ZLjava/lang/String;)Ljava/util/List;
        // Calling meth owner=org/test/pck1/MyClass name=methodPublic2 desc=(ZLjava/lang/String;)Ljava/util/List;

		if (owner.equals(methodAppelee.getEstPorteePar().getCompleteName()) && name.equals(this.getMeth().getName()) && desc.equals(this.getMeth().getDescriptor())) {
			System.out.println("            Called meth : owner=" + methodAppelee.getEstPorteePar().getCompleteName() + " source=" + methodAppelee.getSource() 
					+ " name=" + methodAppelee.getMethodName() + " desc=" + methodAppelee.getMethodDesc());
			System.out.println("            Calling meth owner=" + owner + " name=" + name + " desc=" + desc);
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
			
			Appel appel = new Appel(null, parserASM.getMethodAppelee(), this.line);
			parserASM.getCallees().add(appel);
			
//			new Callee(this.appClassVisitor.getClassName(), 
//					this.appClassVisitor.getMethodName(), this.appClassVisitor.getMethodDesc(), 
//					this.appClassVisitor.getSource(), this.line));
		}
	}

	private Method getMeth() {
		return parserASM.getMethodAppelee().getMeth();
	}

}

