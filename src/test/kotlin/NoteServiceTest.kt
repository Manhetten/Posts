import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun add() {
        assertEquals(10001, NoteService.add(Note("title", "text")).noteId)
    }

    @Test
    fun edit() {
        NoteService.add(Note("title", "text"))
        val result = NoteService.edit(Note("edit title", "edit text", noteId = 10001))
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun editShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.edit(Note("edit title", "edit text", noteId = 10002))
    }

    @Test
    fun delete() {
        NoteService.add(Note("title", "text"))
        val result = NoteService.delete(10001)
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.edit(Note("edit title", "edit text", noteId = 10002))
    }

    @Test
    fun getById() {
        val note = NoteService.add(Note("title", "text"))
        assertEquals(note, NoteService.getById(10001))
    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.getById(10003)
    }

    @Test
    fun createComment() {
        NoteService.add(Note("title", "text"))
        val result = NoteService.createComment(10001, "comment")
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10005, "comment")
    }

    @Test
    fun editComment() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        val result = NoteService.editComment(20001, "edit comment")
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun editCommentShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        NoteService.editComment(30001, "edit comment")
    }

    @Test
    fun deleteComment() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        val result = NoteService.deleteComment(20001)
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteCommentShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        NoteService.deleteComment(40001)
    }

    @Test
    fun restoreComment() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        NoteService.deleteComment(20001)
        val result = NoteService.restoreComment(20001)
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun restoreCommentShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        NoteService.deleteComment(20001)
        NoteService.restoreComment(2001)
    }

    @Test(expected = NoteNotFoundException::class)
    fun commentVisibilityShouldThrow() {
        NoteService.add(Note("title", "text"))
        NoteService.createComment(10001, "comment")
        NoteService.restoreComment(20001)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getCommentsShouldThrow() {
        NoteService.getComments(10001)
    }
}