package org.analyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ParserTools {

	public static Set<Class<?>> getClasses(String packageName) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return getClasses(loader, packageName);
	}

	private static Set<Class<?>> getClasses(ClassLoader loader, String packageName) throws IOException, ClassNotFoundException {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = loader.getResources(path);
		if (resources != null) {
			while (resources.hasMoreElements()) {
				String filePath = resources.nextElement().getFile();
				// WINDOWS HACK
				if (filePath.indexOf("%20") > 0)
					filePath = filePath.replaceAll("%20", " ");
				if (filePath != null) {
					if ((filePath.indexOf("!") > 0) & (filePath.indexOf(".jar") > 0)) {
						String jarPath = filePath.substring(0, filePath.indexOf("!")).substring(filePath.indexOf(":") + 1);
						// WINDOWS HACK
						if (jarPath.indexOf(":") >= 0)
							jarPath = jarPath.substring(1);
						classes.addAll(getFromJARFile(jarPath, path));
					} else {
						classes.addAll(getFromDirectory(new File(filePath), packageName));
					}
				}
			}
		}
		return classes;
	}

	private static Set<Class<?>> getFromJARFile(String jar, String packageName) throws IOException, ClassNotFoundException {
		System.out.println("        getFromJARFile " + jar);
		Set<Class<?>> classes = new HashSet<Class<?>>();
		JarInputStream jarFile = new JarInputStream(new FileInputStream(jar));
		JarEntry jarEntry;
		do {
			jarEntry = jarFile.getNextJarEntry();
			if (jarEntry != null) {
				String className = jarEntry.getName();
				if (className.endsWith(".class")) {
					className = stripFilenameExtension(className);
					if (className.startsWith(packageName))
						classes.add(Class.forName(className.replace('/', '.')));
				}
			}
		} while (jarEntry != null);
		jarFile.close();
		return classes;
	}

	private static Set<Class<?>> getFromDirectory(File directory, String packageName) throws ClassNotFoundException {
		System.out.println("        getFromDirectory " + directory.toString());
        Set<Class<?>> classes = new HashSet<Class<?>>();
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                String path = packageName.replace('/', '.') + "." + stripFilenameExtension(file.getName());
                if (file.isDirectory()) {
                    classes.addAll(getFromDirectory(file, path));
                } else {
                    Class<?> clazz = Class.forName(path);
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
	
	private static String stripFilenameExtension(String filename) {
		int i = filename.lastIndexOf('.');
		if (i > 0) {
			return filename.substring(0, i);
		} else {
			return filename;
		}
	}

}
