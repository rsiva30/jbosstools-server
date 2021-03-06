/*******************************************************************************
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.ide.eclipse.archives.core.model.internal.xb;

public class XbAction extends XbPackageNodeWithProperties {
	private String time, type;
	
	public XbAction() {
		super("buildAction"); //$NON-NLS-1$
	}

	public XbAction(XbAction action) {
		super(action);
		copyFrom(action);
	}
	
	public void copyFrom (XbAction node) {
		super.copyFrom(node);
		this.time = node.time == null ? null : new String(node.time);
		this.type = node.type == null ? null : new String(node.type);
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
