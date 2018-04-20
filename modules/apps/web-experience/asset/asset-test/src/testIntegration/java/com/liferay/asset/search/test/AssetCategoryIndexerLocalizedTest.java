package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
 * Localize index field test for Asset Category
 *
 * @author Yasuyuki Takeo
 */
@RunWith(Arquillian.class)
public class AssetCategoryIndexerLocalizedTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_indexer = _indexerRegistry.getIndexer(AssetCategory.class);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.JAPAN);

		List<Locale> availableLocales = Collections.singletonList(
			LocaleUtil.JAPAN);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, LocaleUtil.JAPAN);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		String title1 = "東京都";

		addCategory(
				assetVocabulary.getVocabularyId(),
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				LocaleUtil.JAPAN, title1);
		
		String searchTerm = "東京都";

		Document document = _search(searchTerm, LocaleUtil.JAPAN);
		
		Map<String, String> titleStrings = new HashMap<String, String>() {
			{
				put("title_ja_JP", searchTerm);
			}
		};
		
		FieldValuesAssert.assertFieldValues(
				titleStrings, "title_ja_JP", document, searchTerm);
	}

	private SearchContext _getSearchContext(String searchTerm, Locale locale)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

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
		try {
			SearchContext searchContext = _getSearchContext(searchTerm, locale);

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

	private AssetCategory addCategory(
			long vocabularyId, long parentCategoryId, Locale locale,
			String title)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>() {
			{
				put(locale, title);
			}
		};

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>() {
			{
				put(locale, RandomTestUtil.randomString());
			}
		};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), parentCategoryId,
			titleMap, descriptionMap, vocabularyId, null, serviceContext);
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<AssetCategory> _indexer;

}