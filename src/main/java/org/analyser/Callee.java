package org.analyser;

/**
 * @author slabbe
 * @deprecated
 */
public class Callee {
	
	public final String className;
	public final String methodName;
	public final String methodDesc;
	public final String source;
	public final int line;

	public Callee(String cName, String mName, String mDesc, String src, int ln) {
		className = cName;
		methodName = mName;
		methodDesc = mDesc;
		source = src;
		line = ln;
	}
	
}
