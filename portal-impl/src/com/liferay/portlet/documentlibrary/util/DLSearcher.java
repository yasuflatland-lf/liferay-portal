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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;

/**
 * @author Julio Camarero
 * @author Eudaldo Alonso
 */
public class DLSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES =
		{DLFileEntry.class.getName(), DLFolder.class.getName()};

	protected String _TITLE;
	protected String _CONTENT;
	protected String _DESCRIPTION;
	protected String _USER_NAME;

	public static Indexer<?> getInstance() {
		return new DLSearcher();
	}

	public DLSearcher() {
		_TITLE = getPrefixedFieldName(Field.TITLE);
		_CONTENT = getPrefixedFieldName(Field.CONTENT);
		_DESCRIPTION = getPrefixedFieldName(Field.DESCRIPTION);
		_USER_NAME = getPrefixedFieldName(Field.USER_NAME);

		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
            Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.UID);

        setDefaultSelectedLocalizedFieldNames(
        		_DESCRIPTION, _TITLE, _USER_NAME, _CONTENT);

        setFilterSearch(true);
        setPermissionAware(true);
	}

	@Override
	public String[] getSearchClassNames() {
		return CLASS_NAMES;
	}

}