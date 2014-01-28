package org.analyser.model;

import org.objectweb.asm.commons.Method;


/**
 * @author slabbe
 */
public class MethodDescription {

	private static int CPT = 1;
	
	private final int numero;
	private final String methodName;
	private final String methodDesc;
	private final ClassDescription estPorteePar;
	private final Method meth;
	
	public MethodDescription(String methodNameIn, String methodDescIn, ClassDescription estPorteeParIn, Method methIn) {
		super();
		this.numero = MethodDescription.CPT++;
		this.methodName = methodNameIn;
		this.methodDesc = methodDescIn;
		this.estPorteePar = estPorteeParIn;
		this.meth = methIn;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMethodDesc() {
		return methodDesc;
	}

	public ClassDescription getEstPorteePar() {
		return estPorteePar;
	}

	public Method getMeth() {
		return meth;
	}

	public int getNumero() {
		return numero;
	}
	
}
