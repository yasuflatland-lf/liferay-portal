package com.liferay.portal.search.facet.faceted.searcher.test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.journal.model.JournalFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portlet.documentlibrary.util.DLFolderIndexer;

/**
 * @author Yasuyuki Takeo
 */
@RunWith(Arquillian.class)
@Sync
public class DLFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
	new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerTestRule.INSTANCE,
		SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		// Dummy

		_indexer = new DLFolderIndexer();

		_TITLE = _indexer.getPrefixedFieldName(Field.TITLE);
		_CONTENT = _indexer.getPrefixedFieldName(Field.CONTENT);
		_DESCRIPTION = _indexer.getPrefixedFieldName(Field.DESCRIPTION);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testFindDocumentAndFolder() throws Exception {

		GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(), null, LocaleUtil.JAPAN);

		// Add Folder
		String folderTitle = "山田太郎";
		String folderDescription = "芸術は爆発";
		DLFolder dlFolder = addForlder(_group.getGroupId(), folderTitle, folderDescription);
		_folders.add(dlFolder);

		// Add Document
		String fileName = "content_search.txt";
		String docTitle1 = "山田太郎と仲間たち";
		String docDescription1 = "東京タワー";
		DLFileEntry dlfile1 = addFileEntry(fileName,_group.getGroupId(),dlFolder.getFolderId(),docTitle1, docDescription1);
		_files.add(dlfile1);

		String docTitle2 = "愛知県";
		String docDescription2 = "名古屋市";
		DLFileEntry dlfile2 = addFileEntry(fileName,_group.getGroupId(),dlFolder.getFolderId(),docTitle2, docDescription2);
		_files.add(dlfile2);

		//
		// Set search context
		//
		String keyword = "山田太郎";
		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setLocale(LocaleUtil.JAPAN);

		searchContext.setEntryClassNames(
				new String[] {DLFolder.class.getName(), DLFileEntry.class.getName()});

		searchContext.setUserId(TestPropsValues.getUser().getUserId());

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
		Arrays.asList(DLFolder.class.getName(), DLFileEntry.class.getName()), hits, keyword, facet);

		Map<String, Integer> expected = new HashMap<String, Integer>() {
			{put(DLFileEntry.class.getName(), 1); }
			{put(DLFolder.class.getName(), 1); }
		};

		assertFrequencies(
			facet.getFieldName(), searchContext, expected);
	}

	@Test
	public void testFindDocumentsInAFolder() throws Exception {

		GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(), null, LocaleUtil.JAPAN);

		// Add Folder
		String folderTitle = "東京都";
		String folderDescription = "青梅市";
		DLFolder dlFolder = addForlder(_group.getGroupId(), folderTitle, folderDescription);
		_folders.add(dlFolder);

		// Add Document
		String fileName = "content_search.txt";
		String docTitle1 = "大阪府";
		String docDescription1 = "通天閣";
		DLFileEntry dlfile1 = addFileEntry(fileName,_group.getGroupId(),dlFolder.getFolderId(),docTitle1, docDescription1);
		_files.add(dlfile1);

		String docTitle2 = "愛知県";
		String docDescription2 = "名古屋市";
		DLFileEntry dlfile2 = addFileEntry(fileName,_group.getGroupId(),dlFolder.getFolderId(),docTitle2, docDescription2);
		_files.add(dlfile2);

		//
		// Set search context
		//
		String keyword = "愛知";
		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setLocale(LocaleUtil.JAPAN);

		searchContext.setEntryClassNames(
				new String[] {DLFolder.class.getName(), DLFileEntry.class.getName()});

		searchContext.setUserId(TestPropsValues.getUser().getUserId());

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
		Arrays.asList(DLFileEntry.class.getName()), hits, keyword, facet);

		assertFrequencies(
			facet.getFieldName(), searchContext,
			Collections.singletonMap(DLFileEntry.class.getName(), 1));
	}

	/**
	 * Create DLFileEntry
	 *
	 * @param fileName
	 * @param groupId
	 * @param folderId
	 * @param title
	 * @param description
	 * @return
	 * @throws Exception
	 */
	protected DLFileEntry addFileEntry(
			String fileName, long groupId, long folderId, String title, String description)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		File file = null;
		FileEntry fileEntry = null;

		try (InputStream inputStream =
				DLFacetedSearcherTest.class.getResourceAsStream(
		"dependencies/" + fileName)) {

			String mimeType = MimeTypesUtil.getContentType(file, fileName);

			file = FileUtil.createTempFile(inputStream);

			fileEntry = _dlAppLocalService.addFileEntry(
					serviceContext.getUserId(), groupId,
					folderId, fileName,
					mimeType, title, description, StringPool.BLANK, file,
					serviceContext);
		} finally {
			FileUtil.delete(file);
		}

		return (DLFileEntry)fileEntry.getModel();
	}

	protected DLFolder addForlder(long groupId,  String name, String description)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		Folder folder = DLAppServiceUtil.addFolder(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			serviceContext);

		return (DLFolder)folder.getModel();
	}

	protected void assertEntryClassNames(
		List<String> entryclassnames, Hits hits, String keyword, Facet facet) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			keyword, hits.getDocs(), facet.getFieldName(), entryclassnames);
	}

	private String _CONTENT;

	private String _DESCRIPTION;

	private String _TITLE;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Inject
	protected PermissionCheckerFactory permissionCheckerFactory;

	@DeleteAfterTestRun
	private final List<DLFolder> _folders = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<DLFileEntry> _files = new ArrayList<>();

	private Indexer<?> _indexer;
	private PermissionChecker _originalPermissionChecker;

}