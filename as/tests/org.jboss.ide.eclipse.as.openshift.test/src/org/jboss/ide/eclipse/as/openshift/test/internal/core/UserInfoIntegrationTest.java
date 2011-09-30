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
package org.jboss.ide.eclipse.as.openshift.test.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jboss.ide.eclipse.as.openshift.core.ICartridge;
import org.jboss.ide.eclipse.as.openshift.core.IOpenshiftService;
import org.jboss.ide.eclipse.as.openshift.core.ISSHPublicKey;
import org.jboss.ide.eclipse.as.openshift.core.OpenshiftException;
import org.jboss.ide.eclipse.as.openshift.core.OpenshiftService;
import org.jboss.ide.eclipse.as.openshift.core.internal.ApplicationInfo;
import org.jboss.ide.eclipse.as.openshift.core.internal.UserInfo;
import org.jboss.ide.eclipse.as.openshift.test.internal.core.fakes.TestUser;
import org.jboss.ide.eclipse.as.openshift.test.internal.core.utils.ApplicationInfoAsserts;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André Dietisheim
 */
public class UserInfoIntegrationTest {

	private OpenshiftService openshiftService;
	private TestUser user;

	@Before
	public void setUp() {
		this.openshiftService = new OpenshiftService();
		this.user = new TestUser();
	}

	@Test
	public void canGetUserInfo() throws Exception {
		UserInfo userInfo = openshiftService.getUserInfo(user);
		assertNotNull(userInfo);

		assertEquals(user.getRhlogin(), userInfo.getRhLogin());
	}

	@Test
	public void userInfoContainsOneMoreApplicationAfterCreatingNewApplication() throws Exception {
		UserInfo userInfo = openshiftService.getUserInfo(user);
		assertNotNull(userInfo);

		List<ApplicationInfo> applicationInfos = userInfo.getApplicationInfos();
		assertNotNull(applicationInfos);
		int numberOfApplicationInfos = applicationInfos.size();

		String applicationName = createRandomName();
		try {
			openshiftService.createApplication(applicationName, ICartridge.JBOSSAS_7, user);

			UserInfo userInfo2 = openshiftService.getUserInfo(user);
			assertEquals(numberOfApplicationInfos + 1, userInfo2.getApplicationInfos().size());
			ApplicationInfoAsserts.assertThatContainsApplicationInfo(applicationName, userInfo2.getApplicationInfos());
		} finally {
			silentlyDestroyAS7Application(applicationName, openshiftService);
		}
	}

	@Test
	public void canUseReturnedSSHKeyToChangeDomain() throws Exception {
		UserInfo userInfo = openshiftService.getUserInfo(user);
		assertNotNull(userInfo);

		ISSHPublicKey sshKey = userInfo.getSshPublicKey();
		openshiftService.changeDomain(createRandomName(), sshKey, user);
	}
	
	private String createRandomName() {
		return String.valueOf(System.currentTimeMillis());
	}

	private void silentlyDestroyAS7Application(String name, IOpenshiftService service) {
		try {
			service.destroyApplication(name, ICartridge.JBOSSAS_7, user);
		} catch (OpenshiftException e) {
			e.printStackTrace();
		}
	}
}