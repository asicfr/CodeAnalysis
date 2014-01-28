package org.analyser.model;


public class Appel {

	private static int CPT = 1;
	
	private final int numero;
	private final MethodDescription methodAppelante;
	private final MethodDescription methodAppelee;
	private final int lineInMethodAppelante;
	
	public Appel(MethodDescription methodAppelante, MethodDescription methodAppelee, int lineInMethodAppelanteIn) {
		super();
		this.numero = Appel.CPT++;
		this.methodAppelante = methodAppelante;
		this.methodAppelee = methodAppelee;
		this.lineInMethodAppelante = lineInMethodAppelanteIn;
	}

	public MethodDescription getMethodAppelante() {
		return methodAppelante;
	}

	public MethodDescription getMethodAppelee() {
		return methodAppelee;
	}

	public int getLineInMethodAppelante() {
		return this.lineInMethodAppelante;
	}

	public int getNumero() {
		return numero;
	}
	
}
