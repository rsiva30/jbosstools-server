/******************************************************************************* 
 * Copyright (c) 2007 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.ide.eclipse.as.openshift.internal.core.response;

import org.jboss.dmr.ModelNode;
import org.jboss.ide.eclipse.as.openshift.core.IOpenshiftJsonConstants;
import org.jboss.ide.eclipse.as.openshift.core.IOpenshiftObject;
import org.jboss.ide.eclipse.as.openshift.core.OpenshiftException;

/**
 * @author André Dietisheim
 */
public abstract class AbstractOpenshiftJsonResponseFactory<OPENSHIFTOBJECT extends IOpenshiftObject> {

	private String response;

	public AbstractOpenshiftJsonResponseFactory(String response) {
		this.response = response;
	}

	public OpenshiftResponse<OPENSHIFTOBJECT> create() throws OpenshiftException {
		ModelNode node = ModelNode.fromJSONString(response);
		boolean debug = node.get(IOpenshiftJsonConstants.PROPERTY_DEBUG).asBoolean();
		String messages = node.get(IOpenshiftJsonConstants.PROPERTY_MESSAGES).asString();
		String result = node.get(IOpenshiftJsonConstants.PROPERTY_RESULT).asString();
		int exitCode = node.get(IOpenshiftJsonConstants.PROPERTY_EXIT_CODE).asInt();
		OPENSHIFTOBJECT openshiftObject = createOpenshiftObject(node.get(IOpenshiftJsonConstants.PROPERTY_DATA));
		return new OpenshiftResponse<OPENSHIFTOBJECT>(debug, messages, result, openshiftObject, exitCode);
	}

	protected abstract OPENSHIFTOBJECT createOpenshiftObject(ModelNode node);

	protected String getResponse() {
		return response;
	}

}
