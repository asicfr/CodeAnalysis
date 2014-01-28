package org.analyser.visitor;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class EmptyMethodVisitor extends MethodVisitor {

	public EmptyMethodVisitor() {
		super(Opcodes.ASM4);
		System.out.println("instanciate EmptyMethodVisitor");
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
	}

	public void visitCode() {
	}

	public void visitLineNumber(int line, Label start) {
	}

	public void visitEnd() {
	}

}

