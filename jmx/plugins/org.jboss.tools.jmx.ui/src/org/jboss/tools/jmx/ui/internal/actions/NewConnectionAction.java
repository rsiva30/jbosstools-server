/*******************************************************************************
 * Copyright (c) 2006 Jeff Mesnil
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    "Rob Stryker" <rob.stryker@redhat.com> - Initial implementation
 *******************************************************************************/
package org.jboss.tools.jmx.ui.internal.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.jboss.tools.jmx.ui.Messages;
import org.jboss.tools.jmx.ui.internal.JMXImages;
import org.jboss.tools.jmx.ui.internal.wizards.NewConnectionTaskWizard;

/**
 * Create a new connection
 */
public class NewConnectionAction extends Action {
	public NewConnectionAction() {
		super(Messages.NewConnectionAction);
        JMXImages.setLocalImageDescriptors(this, "attachAgent.gif");  //$NON-NLS-1$
	}

	public void run() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWizard wizard = new NewConnectionTaskWizard();
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
				WizardDialog d = new WizardDialog(win.getShell(), wizard);
				d.open();
			}
		} );
	}
}
