package io.vertx.test.codegen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * Based on:
 *
 * https://github.com/ddopson/java-class-enumerator
 *
 * Which has the following license:
 *
 * *****
 *
 * This code was originally a snippet on StackOverflow, thus it has the following license:
 *
 * http://creativecommons.org/licenses/by-sa/2.5/
 *
 * I deduced this from http://meta.stackexchange.com/questions/25956/what-is-up-with-the-source-code-license-on-stack-overflow
 *
 * Further, whatever rights I have accidentally retained unto my own person, I forfeit under the
 * [WTFPL](http://www.wtfpl.net/about/) to the extent that is compatible with the primary license and
 * does not contradict the primary license. Or whatever. :)
 * ****
 */
public class ClassEnumerator {

  private static final Logger log =   java.util.logging.Logger.getLogger(GeneratorHelper.class.getName());

  private static Class<?> loadClass(String className) {
    try {
      return getClassLoader().loadClass(className);
    }
    catch (ClassNotFoundException e) {
      throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
    }
  }

  private static void processDirectory(File directory, String pkgname, ArrayList<Class<?>> classes, Function<String, Boolean> matcher) {
    if (!matcher.apply(directory.toString())) {
      return;
    }
    log.fine("Reading Directory '" + directory + "'");
    // Get the list of the files contained in the package
    String[] files = directory.list();
    for (int i = 0; i < files.length; i++) {
      String fileName = files[i];
      String className = null;
      // we are only interested in .class files
      if (fileName.endsWith(".class")) {
        // removes the .class extension
        className = pkgname + '.' + fileName.substring(0, fileName.length() - 6);
      }
      log.fine("FileName '" + fileName + "'  =>  class '" + className + "'");
      if (className != null) {
        classes.add(loadClass(className));
      }
      File subdir = new File(directory, fileName);
      if (subdir.isDirectory()) {
        processDirectory(subdir, pkgname + '.' + fileName, classes, matcher);
      }
    }
  }

  private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes,  Function<String, Boolean> matcher) {
    String relPath = pkgname.replace('.', '/');
    String resPath = resource.getPath();
    String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
    log.fine("Reading JAR file: '" + jarPath + "'");
    JarFile jarFile;
    try {
      jarFile = new JarFile(jarPath);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
    }
    Enumeration<JarEntry> entries = jarFile.entries();
    while(entries.hasMoreElements()) {
      JarEntry entry = entries.nextElement();
      String entryName = entry.getName();
      if (matcher.apply(entryName)) {
        String className = null;
        if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
          className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
        }
        log.fine("JarEntry '" + entryName + "'  =>  class '" + className + "'");
        if (className != null) {
          classes.add(loadClass(className));
        }
      }
    }
  }

  public static ArrayList<Class<?>> getClassesForPackage(String pkgname, Function<String, Boolean> matcher) {
    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

    String relPath = pkgname.replace('.', '/');

    // Get a File object for the package
    URL resource = getClassLoader().getResource(relPath);
    if (resource == null) {
      throw new RuntimeException("Unexpected problem: No resource for " + relPath);
    }
    log.fine("Package: '" + pkgname + "' becomes Resource: '" + resource.toString() + "'");

    resource.getPath();
    if(resource.toString().startsWith("jar:")) {
      processJarfile(resource, pkgname, classes, matcher);
    } else {
      processDirectory(new File(resource.getPath()), pkgname, classes, matcher);
    }

    return classes;
  }

  private static ClassLoader getClassLoader() {
    ClassLoader tccl = Thread.currentThread().getContextClassLoader();
    return tccl == null ? ClassEnumerator.class.getClassLoader() : tccl;
  }
}
