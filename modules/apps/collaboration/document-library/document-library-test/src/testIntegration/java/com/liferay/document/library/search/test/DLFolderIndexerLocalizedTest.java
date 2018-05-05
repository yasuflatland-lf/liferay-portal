package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.DLFolderIndexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yasuyuki Takeo
 */
@RunWith(Arquillian.class)
public class DLFolderIndexerLocalizedTest {

	private String _CONTENT;
	private String _DESCRIPTION;
	private String _TITLE;

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {

		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		_indexer = new DLFolderIndexer();

		_TITLE = _indexer.getPrefixedFieldName(Field.TITLE);
		_CONTENT = _indexer.getPrefixedFieldName(Field.CONTENT);
		_DESCRIPTION = _indexer.getPrefixedFieldName(Field.DESCRIPTION);
	}

	@Test
	public void testJapaneseDescription() throws Exception {
		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, LocaleUtil.JAPAN);

		String title = "平家物語";
		String description = "諸行無常";

		DLFolder folder = addForlder(_group.getGroupId(), title, description);

		String word1 = "平家";
		String word2 = "諸行";

		Document document1 = _search(word1, LocaleUtil.JAPAN);

		List<String> fields1 = _getFieldValues(_TITLE, document1);

		Assert.assertTrue(fields1.contains(_TITLE + "_ja_JP"));

		Document document2 = _search(word2, LocaleUtil.JAPAN);

		List<String> fields2 = _getFieldValues(_DESCRIPTION, document2);

		Assert.assertTrue(fields2.contains(_DESCRIPTION + "_ja_JP"));
	}

	protected DLFolder addForlder(long groupId, String name, String description)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		Folder folder = DLAppServiceUtil.addFolder(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			serviceContext);

		return (DLFolder)folder.getModel();
	}

	private static List<String> _getFieldValues(
		String prefix, Document document) {

		List<String> filteredFields = new ArrayList<>();

		Map<String, Field> fields = document.getFields();

		for (String field : fields.keySet()) {
			if (field.contains(prefix)) {
				filteredFields.add(field);
			}
		}

		return filteredFields;
	}

	private SearchContext _getSearchContext(
			String searchTerm, Locale locale, long groupId)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			groupId);

		searchContext.setKeywords(searchTerm);
		searchContext.setLocale(locale);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	private Document _getSingleDocument(String searchTerm, Hits hits) {
		List<Document> documents = hits.toList();

		if (documents.size() == 1) {
			return documents.get(0);
		}

		throw new AssertionError(searchTerm + "->" + documents);
	}

	private Document _search(String searchTerm, Locale locale) {
		return _search(searchTerm, locale, _group.getGroupId());
	}

	private Document _search(String searchTerm, Locale locale, long groupId) {
		try {
			SearchContext searchContext = _getSearchContext(
				searchTerm, locale, groupId);

			Hits hits = _indexer.search(searchContext);

			return _getSingleDocument(searchTerm, hits);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<?> _indexer;

}