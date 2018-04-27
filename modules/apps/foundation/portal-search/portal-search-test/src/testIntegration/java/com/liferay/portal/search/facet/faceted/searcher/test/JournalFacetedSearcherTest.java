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

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.test.journal.util.JournalArticleBuilder;
import com.liferay.portal.search.test.journal.util.JournalArticleContent;
import com.liferay.portal.search.test.journal.util.JournalArticleTitle;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Yasuyuki Takeo
 */
@RunWith(Arquillian.class)
public class JournalFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testFindArticlesAndAFolder() throws Exception {
		Group group = userSearchFixture.addGroup();

		GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), null, LocaleUtil.JAPAN);

		Locale targetLocale = LocaleUtil.JAPAN;

		// Create article

		JournalArticleBuilder journalArticleBuilder1 = addArticle1(group);
		JournalArticleBuilder journalArticleBuilder2 = addArticle2(group);

		// Add Folder

		String folderTitle = "東京都";
		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		JournalFolder addFolder = addFolder(
			group.getGroupId(), user.getUserId(), folderTitle);

		// Add Article under the folder

		addArticle(
			journalArticleBuilder1, targetLocale, addFolder.getFolderId());
		addArticle(
			journalArticleBuilder2, targetLocale, addFolder.getFolderId());

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));

		// Search with Keyword

		String keyword = "東京都";

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setLocale(targetLocale);

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
		Arrays.asList(JournalArticle.class.getName(), JournalFolder.class.getName()),
		hits, keyword, facet);

		Map<String, Integer> expected = new HashMap<String, Integer>() {
			{put(JournalArticle.class.getName(), 1); }
			{put(JournalFolder.class.getName(), 1); }
		};
		assertFrequencies(facet.getFieldName(), searchContext, expected);
	}

	@Test
	public void testFindArticlesInAFolder() throws Exception {
		Group group = userSearchFixture.addGroup();

		GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), null, LocaleUtil.JAPAN);

		Locale targetLocale = LocaleUtil.JAPAN;

		// Create article

		JournalArticleBuilder journalArticleBuilder1 = addArticle1(group);
		JournalArticleBuilder journalArticleBuilder2 = addArticle2(group);

		// Add Folder

		String folderTitle = "東京都";
		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		JournalFolder addFolder = addFolder(
			group.getGroupId(), user.getUserId(), folderTitle);

		// Add Article under the folder

		addArticle(
			journalArticleBuilder1, targetLocale, addFolder.getFolderId());
		addArticle(
			journalArticleBuilder2, targetLocale, addFolder.getFolderId());

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));

		// Search with Keyword

		String keyword = "三年坂";

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setLocale(targetLocale);

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
		Arrays.asList(JournalArticle.class.getName(), JournalArticle.class.getName()),
		hits, keyword, facet);

		assertFrequencies(
			facet.getFieldName(), searchContext,
			Collections.singletonMap(JournalArticle.class.getName(), 2));
	}

	@Test
	public void testFindArticleUnderAFolder() throws Exception {
		Group group = userSearchFixture.addGroup();

		GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), null, LocaleUtil.JAPAN);

		Locale targetLocale = LocaleUtil.JAPAN;

		// Create article

		JournalArticleBuilder journalArticleBuilder = addArticle1(group);

		// Add Folder

		String folderTitle = "東京都";
		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		JournalFolder addFolder = addFolder(
			group.getGroupId(), user.getUserId(), folderTitle);

		// Add Article under the folder

		addArticle(
			journalArticleBuilder, targetLocale, addFolder.getFolderId());

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));

		// Search with Keyword

		String keyword = "京都";

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setLocale(targetLocale);

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
		Arrays.asList(JournalArticle.class.getName()), hits, keyword, facet);

		assertFrequencies(
			facet.getFieldName(), searchContext,
			Collections.singletonMap(JournalArticle.class.getName(), 1));
	}

	protected JournalArticle addArticle(
		JournalArticleBuilder journalArticleBuilder, Locale locale,
		long folderId) throws Exception {

		journalArticleBuilder.setDraft(false);
		journalArticleBuilder.setWorkflowEnabled(true);

		JournalArticle journalArticle = journalArticleSearchFixture.addArticle(
			journalArticleBuilder);

		journalArticle.setFolderId(folderId);

		return JournalTestUtil.updateArticle(
			journalArticle, journalArticle.getTitle(locale));
	}

	protected JournalArticleBuilder addArticle1(Group group) {
		JournalArticleBuilder journalArticleBuilder =
			new JournalArticleBuilder();

		journalArticleBuilder.setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.US, "Kyoto");
					put(LocaleUtil.SPAIN, "Kyoto");
					put(LocaleUtil.JAPAN, "京都");
				}
			});
		journalArticleBuilder.setContent(
			new JournalArticleContent() {
				{
					name = "content";
					defaultLocale = LocaleUtil.US;

					put(LocaleUtil.US, "Knkakuji Sannenzaka");
					put(LocaleUtil.SPAIN, "Knkakuji Sannenzaka");
					put(LocaleUtil.JAPAN, "金閣寺 三年坂");
				}
			});

		journalArticleBuilder.setGroupId(group.getGroupId());

		return journalArticleBuilder;
	}

	protected JournalArticleBuilder addArticle2(Group group) {
		JournalArticleBuilder journalArticleBuilder =
			new JournalArticleBuilder();

		journalArticleBuilder.setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.US, "Tokyo");
					put(LocaleUtil.SPAIN, "Tokyo");
					put(LocaleUtil.JAPAN, "東京都");
				}
			});
		journalArticleBuilder.setContent(
			new JournalArticleContent() {
				{
					name = "content";
					defaultLocale = LocaleUtil.US;

					put(LocaleUtil.US, "Zojyoji Sannenzaka");
					put(LocaleUtil.SPAIN, "Zojyoji Sannenzaka");
					put(LocaleUtil.JAPAN, "増上寺 三年坂");
				}
			});

		journalArticleBuilder.setGroupId(group.getGroupId());

		return journalArticleBuilder;
	}

	protected JournalFolder addFolder(long groupId, long userId, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		JournalFolder folder = JournalFolderLocalServiceUtil.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, "",
			serviceContext);

		_folders.add(folder);

		return folder;
	}

	protected void assertEntryClassNames(
		List<String> entryclassnames, Hits hits, String keyword, Facet facet) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			keyword, hits.getDocs(), facet.getFieldName(), entryclassnames);
	}

	protected void index(
			Group group, JournalArticleBuilder journalArticleBuilder,
			Locale locale, String folderTitle)
		throws Exception {

		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		journalArticleBuilder.setGroupId(group.getGroupId());

		// Add Folder

		JournalFolder addFolder = addFolder(
			group.getGroupId(), user.getUserId(), folderTitle);

		// Add Article under the folder

		addArticle(journalArticleBuilder, locale, addFolder.getFolderId());

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));
	}

	@Inject
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Inject
	protected PermissionCheckerFactory permissionCheckerFactory;

	@DeleteAfterTestRun
	private final List<JournalFolder> _folders = new ArrayList<>();

}