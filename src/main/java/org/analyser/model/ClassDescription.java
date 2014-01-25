package org.analyser.model;

import java.util.ArrayList;
import java.util.List;


/**
 * @author slabbe
 */
public class ClassDescription {
	
	private final String completeName;
	private final EnumTypeClass type;
	
	/**
	 * inverse l'une de l'autre
	 */
	private List<ClassDescription> implementeLesInterfaces = new ArrayList<>();
	private List<ClassDescription> estImplementePar = new ArrayList<>();
	
	/**
	 * inverse de org.test.MethodDescription.estPorteePar
	 */
	private List<MethodDescription> possedeLesMethodes = new ArrayList<>();

	/**
	 * les annotations de la classe
	 */
	private List<AnnotationDescription> possedeLesAnnotations = new ArrayList<>();

	/**
	 * inverse l'une de l'autre
	 */
	private List<ClassDescription> estUtiliseeParLaClasse = new ArrayList<>();
	private List<ClassDescription> utiliseLaClasse = new ArrayList<>();
	
	public ClassDescription(String completeNameIn, EnumTypeClass typeIn) {
		super();
		this.completeName = completeNameIn;
		this.type = typeIn;
	}

	public String getCompleteName() {
		return completeName;
	}

	public EnumTypeClass getType() {
		return type;
	}

	public List<ClassDescription> getImplementeLesInterfaces() {
		return implementeLesInterfaces;
	}
	
	public List<ClassDescription> getEstImplementePar() {
		return estImplementePar;
	}
	
	public List<MethodDescription> getPossedeLesMethodes() {
		return possedeLesMethodes;
	}
	
	public List<AnnotationDescription> getPossedeLesAnnotations() {
		return possedeLesAnnotations;
	}
	
	public List<ClassDescription> getEstUtiliseeParLaClasse() {
		return estUtiliseeParLaClasse;
	}
	
	public List<ClassDescription> getUtiliseLaClasse() {
		return utiliseLaClasse;
	}

	

	public void addImplementeLesInterfaces(ClassDescription classDescription) {
		implementeLesInterfaces.add(classDescription);
	}

	public void addEstImplementePar(ClassDescription classDescription) {
		estImplementePar.add(classDescription);
	}

	public void addPossedeLesMethodes(MethodDescription methodDescription) {
		possedeLesMethodes.add(methodDescription);
	}

	public void addPossedeLesAnnotations(AnnotationDescription annotationDescription) {
		possedeLesAnnotations.add(annotationDescription);
	}

	public void addEstUtiliseeParLaClasse(ClassDescription classDescription) {
		estUtiliseeParLaClasse.add(classDescription);
	}

	public void addUtiliseLaClasse(ClassDescription classDescription) {
		utiliseLaClasse.add(classDescription);
	}

}
