package com.puppetlabs.geppetto.startup;

import org.apache.commons.lang.SystemUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Verify startup conditions.
 * Currently only verifies that the Java that we're running on is version 1.7 or higher
 */
public class Activator extends AbstractUIPlugin {
	public void start(BundleContext context) throws Exception {
		super.start(context);
		if(!SystemUtils.isJavaVersionAtLeast(1.7f)) {
			Display display = new Display();
			try {
				MessageDialog.openError(
					new Shell(display),
					"Unsupported JRE",
					"Geppetto features will not be enabled since they need Java version 1.7 or higher to run. This instance was started with Java " +
						SystemUtils.JAVA_RUNTIME_VERSION);
			}
			finally {
				display.dispose();
			}
		}
	}
}
