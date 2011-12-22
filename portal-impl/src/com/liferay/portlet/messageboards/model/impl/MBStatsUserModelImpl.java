/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBStatsUserModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the MBStatsUser service. Represents a row in the &quot;MBStatsUser&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.messageboards.model.MBStatsUserModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MBStatsUserImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserImpl
 * @see com.liferay.portlet.messageboards.model.MBStatsUser
 * @see com.liferay.portlet.messageboards.model.MBStatsUserModel
 * @generated
 */
public class MBStatsUserModelImpl extends BaseModelImpl<MBStatsUser>
	implements MBStatsUserModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message boards stats user model instance should use the {@link com.liferay.portlet.messageboards.model.MBStatsUser} interface instead.
	 */
	public static final String TABLE_NAME = "MBStatsUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "statsUserId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "messageCount", Types.INTEGER },
			{ "lastPostDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table MBStatsUser (statsUserId LONG not null primary key,groupId LONG,userId LONG,messageCount INTEGER,lastPostDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table MBStatsUser";
	public static final String ORDER_BY_JPQL = " ORDER BY mbStatsUser.messageCount DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MBStatsUser.messageCount DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);
	public static long GROUPID_COLUMN_BITMASK = 1L;
	public static long MESSAGECOUNT_COLUMN_BITMASK = 2L;
	public static long USERID_COLUMN_BITMASK = 4L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBStatsUser"));

	public MBStatsUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _statsUserId;
	}

	public void setPrimaryKey(long primaryKey) {
		setStatsUserId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_statsUserId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return MBStatsUser.class;
	}

	public String getModelClassName() {
		return MBStatsUser.class.getName();
	}

	public long getStatsUserId() {
		return _statsUserId;
	}

	public void setStatsUserId(long statsUserId) {
		_statsUserId = statsUserId;
	}

	public String getStatsUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatsUserId(), "uuid", _statsUserUuid);
	}

	public void setStatsUserUuid(String statsUserUuid) {
		_statsUserUuid = statsUserUuid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		_columnBitmask = -1L;

		if (!_setOriginalMessageCount) {
			_setOriginalMessageCount = true;

			_originalMessageCount = _messageCount;
		}

		_messageCount = messageCount;
	}

	public int getOriginalMessageCount() {
		return _originalMessageCount;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public MBStatsUser toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (MBStatsUser)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					MBStatsUser.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		MBStatsUserImpl mbStatsUserImpl = new MBStatsUserImpl();

		mbStatsUserImpl.setStatsUserId(getStatsUserId());
		mbStatsUserImpl.setGroupId(getGroupId());
		mbStatsUserImpl.setUserId(getUserId());
		mbStatsUserImpl.setMessageCount(getMessageCount());
		mbStatsUserImpl.setLastPostDate(getLastPostDate());

		mbStatsUserImpl.resetOriginalValues();

		return mbStatsUserImpl;
	}

	public int compareTo(MBStatsUser mbStatsUser) {
		int value = 0;

		if (getMessageCount() < mbStatsUser.getMessageCount()) {
			value = -1;
		}
		else if (getMessageCount() > mbStatsUser.getMessageCount()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBStatsUser mbStatsUser = null;

		try {
			mbStatsUser = (MBStatsUser)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = mbStatsUser.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		MBStatsUserModelImpl mbStatsUserModelImpl = this;

		mbStatsUserModelImpl._originalGroupId = mbStatsUserModelImpl._groupId;

		mbStatsUserModelImpl._setOriginalGroupId = false;

		mbStatsUserModelImpl._originalUserId = mbStatsUserModelImpl._userId;

		mbStatsUserModelImpl._setOriginalUserId = false;

		mbStatsUserModelImpl._originalMessageCount = mbStatsUserModelImpl._messageCount;

		mbStatsUserModelImpl._setOriginalMessageCount = false;

		mbStatsUserModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<MBStatsUser> toCacheModel() {
		MBStatsUserCacheModel mbStatsUserCacheModel = new MBStatsUserCacheModel();

		mbStatsUserCacheModel.statsUserId = getStatsUserId();

		mbStatsUserCacheModel.groupId = getGroupId();

		mbStatsUserCacheModel.userId = getUserId();

		mbStatsUserCacheModel.messageCount = getMessageCount();

		Date lastPostDate = getLastPostDate();

		if (lastPostDate != null) {
			mbStatsUserCacheModel.lastPostDate = lastPostDate.getTime();
		}
		else {
			mbStatsUserCacheModel.lastPostDate = Long.MIN_VALUE;
		}

		return mbStatsUserCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{statsUserId=");
		sb.append(getStatsUserId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", messageCount=");
		sb.append(getMessageCount());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBStatsUser");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>statsUserId</column-name><column-value><![CDATA[");
		sb.append(getStatsUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageCount</column-name><column-value><![CDATA[");
		sb.append(getMessageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = MBStatsUser.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			MBStatsUser.class
		};
	private long _statsUserId;
	private String _statsUserUuid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private int _messageCount;
	private int _originalMessageCount;
	private boolean _setOriginalMessageCount;
	private Date _lastPostDate;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private MBStatsUser _escapedModelProxy;
}