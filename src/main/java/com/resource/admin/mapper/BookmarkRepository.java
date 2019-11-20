package com.resource.admin.mapper;

import com.resource.admin.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, String>, JpaSpecificationExecutor<Bookmark> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM bookmark WHERE id IN ?1")
    void deleteAllInBookmarkIds(String[] ids);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE bookmark SET delete_bool = ?2 WHERE id IN ?1")
    void changeBookmarksStatus(String[] ids, Integer deleteBool);

    @Query(nativeQuery = true, value = "SELECT t1.* FROM bookmark t1 " +
            "WHERE t1.id IN (" +
            "SELECT t2.bookmark_id FROM bookmark_tag t2 WHERE tag_id IN ?1 GROUP BY t2.tag_id HAVING COUNT(1) = ?2) " +
            "AND t1.delete_bool = 0 ORDER BY t1.last_update_time DESC LIMIT ?3 OFFSET ?4")
    List<Bookmark> getBookmarksWithMultipleTag(String[] tagIds, Integer tagCount, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) " +
            "FROM bookmark " +
            "WHERE id IN (" +
            "SELECT t2.bookmark_id FROM bookmark_tag t2 WHERE tag_id IN ?1 GROUP BY t2.tag_id HAVING COUNT(1) = ?2) " +
            "AND delete_bool = 0")
    Long countBookmarksWithMultipleTagCount(String[] tagIds, Integer tagCount);


    @Query(nativeQuery = true, value ="SELECT t1.* " +
            "FROM bookmark t1 " +
            "WHERE t1.id IN (" +
            "SELECT t2.bookmark_id FROM bookmark_tag t2 WHERE tag_id IN ?1) " +
            "AND t1.delete_bool = 0 ORDER BY t1.last_update_time DESC LIMIT ?2 OFFSET ?3")
    List<Bookmark> getBookmarksWithTag(String[] tagIds, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) " +
            "FROM bookmark t1 " +
            "WHERE t1.id IN (" +
            "SELECT t2.bookmark_id FROM bookmark_tag t2 WHERE t2.tag_id IN ?1) " +
            "AND t1.delete_bool = 0")
    Long countBookmarksWithTag(String[] tagIds);

}
