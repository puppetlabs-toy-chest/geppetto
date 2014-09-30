package com.puppetlabs.geppetto.startup;

import org.apache.commons.lang.SystemUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Verify startup conditions.
 * Currently only verifies that the Java that we're running on is version 1.7 or higher
 */
public class JVMVersionCheck implements IStartup {
	public void earlyStartup() {
		if(SystemUtils.isJavaVersionAtLeast(1.7f))
			return;

		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if(window != null)
					MessageDialog.openError(
						window.getShell(),
						"Unsupported JRE",
						"Geppetto features will not be enabled since they need Java version 1.7 or higher to run. This instance was started with Java " +
							SystemUtils.JAVA_RUNTIME_VERSION);
			}
		});
	}
}
