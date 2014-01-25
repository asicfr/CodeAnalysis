package org.analyser.model;


public class Appel {
	
	private final MethodDescription methodAppelante;
	private final MethodDescription methodAppelee;
	private final int lineInMethodAppelante;
	
	public Appel(MethodDescription methodAppelante, MethodDescription methodAppelee, int lineInMethodAppelanteIn) {
		super();
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

}
