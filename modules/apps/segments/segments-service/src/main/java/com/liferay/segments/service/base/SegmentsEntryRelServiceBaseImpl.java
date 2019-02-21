/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelService;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;
import com.liferay.segments.service.persistence.SegmentsEntryRelPersistence;
import com.liferay.segments.service.persistence.SegmentsExperiencePersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the segments entry rel remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.segments.service.impl.SegmentsEntryRelServiceImpl}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see com.liferay.segments.service.impl.SegmentsEntryRelServiceImpl
 * @generated
 */
public abstract class SegmentsEntryRelServiceBaseImpl extends BaseServiceImpl
	implements SegmentsEntryRelService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>SegmentsEntryRelService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.segments.service.SegmentsEntryRelServiceUtil</code>.
	 */

	/**
	 * Returns the segments entry local service.
	 *
	 * @return the segments entry local service
	 */
	public com.liferay.segments.service.SegmentsEntryLocalService getSegmentsEntryLocalService() {
		return segmentsEntryLocalService;
	}

	/**
	 * Sets the segments entry local service.
	 *
	 * @param segmentsEntryLocalService the segments entry local service
	 */
	public void setSegmentsEntryLocalService(
		com.liferay.segments.service.SegmentsEntryLocalService segmentsEntryLocalService) {
		this.segmentsEntryLocalService = segmentsEntryLocalService;
	}

	/**
	 * Returns the segments entry remote service.
	 *
	 * @return the segments entry remote service
	 */
	public com.liferay.segments.service.SegmentsEntryService getSegmentsEntryService() {
		return segmentsEntryService;
	}

	/**
	 * Sets the segments entry remote service.
	 *
	 * @param segmentsEntryService the segments entry remote service
	 */
	public void setSegmentsEntryService(
		com.liferay.segments.service.SegmentsEntryService segmentsEntryService) {
		this.segmentsEntryService = segmentsEntryService;
	}

	/**
	 * Returns the segments entry persistence.
	 *
	 * @return the segments entry persistence
	 */
	public SegmentsEntryPersistence getSegmentsEntryPersistence() {
		return segmentsEntryPersistence;
	}

	/**
	 * Sets the segments entry persistence.
	 *
	 * @param segmentsEntryPersistence the segments entry persistence
	 */
	public void setSegmentsEntryPersistence(
		SegmentsEntryPersistence segmentsEntryPersistence) {
		this.segmentsEntryPersistence = segmentsEntryPersistence;
	}

	/**
	 * Returns the segments entry rel local service.
	 *
	 * @return the segments entry rel local service
	 */
	public com.liferay.segments.service.SegmentsEntryRelLocalService getSegmentsEntryRelLocalService() {
		return segmentsEntryRelLocalService;
	}

	/**
	 * Sets the segments entry rel local service.
	 *
	 * @param segmentsEntryRelLocalService the segments entry rel local service
	 */
	public void setSegmentsEntryRelLocalService(
		com.liferay.segments.service.SegmentsEntryRelLocalService segmentsEntryRelLocalService) {
		this.segmentsEntryRelLocalService = segmentsEntryRelLocalService;
	}

	/**
	 * Returns the segments entry rel remote service.
	 *
	 * @return the segments entry rel remote service
	 */
	public SegmentsEntryRelService getSegmentsEntryRelService() {
		return segmentsEntryRelService;
	}

	/**
	 * Sets the segments entry rel remote service.
	 *
	 * @param segmentsEntryRelService the segments entry rel remote service
	 */
	public void setSegmentsEntryRelService(
		SegmentsEntryRelService segmentsEntryRelService) {
		this.segmentsEntryRelService = segmentsEntryRelService;
	}

	/**
	 * Returns the segments entry rel persistence.
	 *
	 * @return the segments entry rel persistence
	 */
	public SegmentsEntryRelPersistence getSegmentsEntryRelPersistence() {
		return segmentsEntryRelPersistence;
	}

	/**
	 * Sets the segments entry rel persistence.
	 *
	 * @param segmentsEntryRelPersistence the segments entry rel persistence
	 */
	public void setSegmentsEntryRelPersistence(
		SegmentsEntryRelPersistence segmentsEntryRelPersistence) {
		this.segmentsEntryRelPersistence = segmentsEntryRelPersistence;
	}

	/**
	 * Returns the segments experience local service.
	 *
	 * @return the segments experience local service
	 */
	public com.liferay.segments.service.SegmentsExperienceLocalService getSegmentsExperienceLocalService() {
		return segmentsExperienceLocalService;
	}

	/**
	 * Sets the segments experience local service.
	 *
	 * @param segmentsExperienceLocalService the segments experience local service
	 */
	public void setSegmentsExperienceLocalService(
		com.liferay.segments.service.SegmentsExperienceLocalService segmentsExperienceLocalService) {
		this.segmentsExperienceLocalService = segmentsExperienceLocalService;
	}

	/**
	 * Returns the segments experience remote service.
	 *
	 * @return the segments experience remote service
	 */
	public com.liferay.segments.service.SegmentsExperienceService getSegmentsExperienceService() {
		return segmentsExperienceService;
	}

	/**
	 * Sets the segments experience remote service.
	 *
	 * @param segmentsExperienceService the segments experience remote service
	 */
	public void setSegmentsExperienceService(
		com.liferay.segments.service.SegmentsExperienceService segmentsExperienceService) {
		this.segmentsExperienceService = segmentsExperienceService;
	}

	/**
	 * Returns the segments experience persistence.
	 *
	 * @return the segments experience persistence
	 */
	public SegmentsExperiencePersistence getSegmentsExperiencePersistence() {
		return segmentsExperiencePersistence;
	}

	/**
	 * Sets the segments experience persistence.
	 *
	 * @param segmentsExperiencePersistence the segments experience persistence
	 */
	public void setSegmentsExperiencePersistence(
		SegmentsExperiencePersistence segmentsExperiencePersistence) {
		this.segmentsExperiencePersistence = segmentsExperiencePersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SegmentsEntryRelService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SegmentsEntryRel.class;
	}

	protected String getModelClassName() {
		return SegmentsEntryRel.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = segmentsEntryRelPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.segments.service.SegmentsEntryLocalService.class)
	protected com.liferay.segments.service.SegmentsEntryLocalService segmentsEntryLocalService;
	@BeanReference(type = com.liferay.segments.service.SegmentsEntryService.class)
	protected com.liferay.segments.service.SegmentsEntryService segmentsEntryService;
	@BeanReference(type = SegmentsEntryPersistence.class)
	protected SegmentsEntryPersistence segmentsEntryPersistence;
	@BeanReference(type = com.liferay.segments.service.SegmentsEntryRelLocalService.class)
	protected com.liferay.segments.service.SegmentsEntryRelLocalService segmentsEntryRelLocalService;
	@BeanReference(type = SegmentsEntryRelService.class)
	protected SegmentsEntryRelService segmentsEntryRelService;
	@BeanReference(type = SegmentsEntryRelPersistence.class)
	protected SegmentsEntryRelPersistence segmentsEntryRelPersistence;
	@BeanReference(type = com.liferay.segments.service.SegmentsExperienceLocalService.class)
	protected com.liferay.segments.service.SegmentsExperienceLocalService segmentsExperienceLocalService;
	@BeanReference(type = com.liferay.segments.service.SegmentsExperienceService.class)
	protected com.liferay.segments.service.SegmentsExperienceService segmentsExperienceService;
	@BeanReference(type = SegmentsExperiencePersistence.class)
	protected SegmentsExperiencePersistence segmentsExperiencePersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameService.class)
	protected com.liferay.portal.kernel.service.ClassNameService classNameService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserService.class)
	protected com.liferay.portal.kernel.service.UserService userService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
}