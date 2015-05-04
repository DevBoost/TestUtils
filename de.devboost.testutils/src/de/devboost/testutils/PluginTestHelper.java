/*******************************************************************************
 * Copyright (c) 2006-2015
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH,, Dresden, Amtsgericht Dresden, HRB 34001.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Dresden, Germany
 *      - initial API and implementation
 ******************************************************************************/
package de.devboost.testutils;

import java.io.File;
import java.net.URL;

/**
 * The {@link PluginTestHelper} can be used to access test resources (e.g., test data) using the location of a Java
 * class instead of the current working directory.
 */
public class PluginTestHelper {

	public static final PluginTestHelper INSTANCE = new PluginTestHelper();

	/**
	 * Use {@link PluginTestHelper#INSTANCE} instead.
	 */
	@Deprecated
	public PluginTestHelper() {
	}

	public String getPluginRootPath(Class<?> clazz) {
		URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
		String binFolder = location.getFile().replace("%20", " ");
		String rootPathString;
		// we must not use File.separator here as the path is obtained from an
		// URL which uses forward slashes on all OSs
		if (binFolder.endsWith("/bin/")) {
			rootPathString = binFolder + File.separator + "..";
		} else {
			rootPathString = binFolder;
		}
		String pluginRootPath = new File(rootPathString).getAbsolutePath();
		return pluginRootPath;
	}

	public String getPluginRootDirectory(Class<?> clazz) {
		File pluginRootDirectory = new File(getPluginRootPath(clazz));
		if (pluginRootDirectory.isFile()) {
			pluginRootDirectory = pluginRootDirectory.getParentFile();
		}
		return pluginRootDirectory.getAbsolutePath();
	}

	public String getSourcePackagePath(Class<?> clazz) {
		String pluginRootPath = getPluginRootPath(clazz);
		String packageDirectory = File.separator + "src" + File.separator
				+ clazz.getPackage().getName().replace(".", File.separator) + File.separator;
		String sourcePackagePath = pluginRootPath + packageDirectory;
		return sourcePackagePath;
	}
}
