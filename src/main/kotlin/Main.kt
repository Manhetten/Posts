import java.lang.RuntimeException

fun main() {

//    val post = Post(likes = Likes(1))
//    val post1 = Post(likes = Likes(1))
//    val post2 = Post(likes = Likes(1))
//
//    println(WallService.addPostToWall(post = post))
//    println(WallService.addPostToWall(post = post1))
//    println(WallService.addPostToWall(post = post2))
//    println()
//
//    println(WallService.update(post = Post(id = 1))) // пост с id найден и заменен
//    println(WallService.update(post = Post(id = 5))) // пост с id не найден
//    println()
//
//    WallService.print() //замена поста с найденным id
//    println()
//    println(WallService.createComment(1, comment = Comment(text = "привет")))
//    println(WallService.createComment(3, comment = Comment(text = "пока")))
//    // println(WallService.createComment(56, comment = Comment(text = "привет")))
//    println()

    println(NoteService.add(Note("123", "hello")))
    println(NoteService.add(Note("234", "hello")))
    println(NoteService.add(Note("345", "hello")))
    println("--удаляю заметку--")
//    NoteService.delete(10001)
//    NoteService.read()
    println("--редактирую заметку--")
    NoteService.edit(Note("123", "bye", noteId = 10002))
    NoteService.read()
    println("--получаю заметку по id--")
    println(NoteService.getById(10003))
    println("--создаю комментарии--")
    NoteService.createComment(10002,"comment")
//    println(NoteService.createComment(10005, "error"))
    NoteService.createComment(10002, "second comment")
    println("--получаю список комментариев к заметке--")
    NoteService.getComments(10002)
    println("--Удаляю заметку--")
    NoteService.delete(10001)
//    NoteService.read()
//    NoteService.getComments(10002)
    println("--редактирую комментарий--")
    NoteService.editComment(20001, "edit comment")
    println("--Удаляю комментарий--")
    NoteService.deleteComment(20002)
    NoteService.getComments(10002)
    NoteService.restoreComment(20002)
    NoteService.getComments(10002)

}

interface Attachment {
    val type: String
}

data class Audio(val id: Int)
data class Video(val id: Int)
data class Photo(val id: Int)
data class Doc(val id: Int)
data class Link(val id: Int)

data class AudioAttachment(val audio: Audio) : Attachment {
    override val type = "audio"
}

data class VideoAttachment(val video: Video) : Attachment {
    override val type = "video"
}

data class PhotoAttachment(val photo: Photo) : Attachment {
    override val type = "photo"
}

data class DocAttachment(val doc: Doc) : Attachment {
    override val type = "doc"
}

data class LinkAttachment(val link: Link) : Attachment {
    override val type = "link"
}

data class Post(
    val id: Int = 0,
    val ownerId: Int = 123456,
    val fromId: Int = 123456,
    val createdBy: Int = 123456,
    val date: Int = System.currentTimeMillis().toInt(),
    val text: String = "Hello World",
    val replyOwnerId: Int = 123456,
    val replyPostId: Int = 1234567890,
    val friendsOnly: Int = 1,
    val comments: Comments = Comments(),
    val copyright: Copyright = Copyright(),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val postType: String = "post",
    val postSource: PostSource? = null,
    val geo: Geo? = null,
    val signerId: Int = 123456,
    val copyHistory: Array<Reposts> = emptyArray(),
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned: Int = 1,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val donut: Donut? = Donut(),
    val likes: Likes = Likes(),
    val attachments: Array<Attachment> = emptyArray()
)

data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int = 10,
    val placeholder: Placeholder? = null,
    val canPublishFreeCopy: Boolean = false,
    val editMode: String = "all"
)

class Placeholder()
class PostSource()
data class Copyright(
    val id: Int = 1,
    val link: String = "link",
    val name: String = "name",
    val type: String = "type"
)

data class Geo(
    val type: String = "place",
    val coordinates: String = "12.05",
    val place: Place = Place()
)

data class Place(
    val somePlace: Int = 0
)


data class Views(
    val count: Int = 0
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = false,
    val canOpen: Boolean = false
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
) {
    override fun toString(): String {
        return "$count"
    }
}

data class Comment(
    val id: Int = 0,
    val fromId: Int = 123,
    val date: Int = System.currentTimeMillis().toInt(),
    val text: String = "Hello",
    val donut: Donut? = null,
    val replyToComment: Int = 12345,
    val attachment: Attachment? = null,
    val parentsStack: Array<ParentStacks> = emptyArray(),
    val thread: Thread? = null
)

data class Note(
    val title: String,
    val text: String,
    val privacy: Int = 0,
    val noteId: Long = 0,
    val date: Int = System.currentTimeMillis().toInt()
)

data class NoteComment(
    val noteId: Long,
    val message: String,
    var visibility: Boolean = true,
    val commentId: Long
)

class ParentStacks()
class PostNotFoundException(message: String) : RuntimeException(message)
class NoteNotFoundException(message: String) : RuntimeException(message)

object WallService {
    private var wallOfPosts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var postId = 0
    private var commentId = 0

    fun addPostToWall(post: Post): Post {
        postId += 1
        wallOfPosts += post.copy(id = postId, likes = post.likes.copy())
        return wallOfPosts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, postOnWall) in wallOfPosts.withIndex()) {
            if (post.id == postOnWall.id) {
                wallOfPosts[index] = post.copy(
                    ownerId = 654321,
                    fromId = 654321,
                    createdBy = 654321,
                    canPin = false,
                    canEdit = false,
                    canDelete = false,
                    likes = Likes(5)
                )
                return true
            }
        }
        return false
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in wallOfPosts) {
            if (postId == post.id) {
                comments += comment.copy(id = ++commentId)
                return comments.last()
            }
        }
        throw PostNotFoundException("Поста с ID $postId не существует")
    }

    fun clear() {
        wallOfPosts = emptyArray()
        postId = 0
        comments = emptyArray()
        commentId = 0
    }

    fun print() {
        println("Список постов:")
        for (post in wallOfPosts) {
            println(post)
        }
    }
}

object NoteService {
    private var notesList = mutableListOf<Note>()
    private var noteId: Long = 10_000
    private var commentsList = mutableListOf<NoteComment>()
    private var commentId: Long = 20_000
    fun add(note: Note): Note {
        notesList.add(note.copy(noteId = ++noteId))
        return notesList.last()
    }

    fun edit(editNote: Note): Boolean {
        val index = notesList.indexOfFirst { it.noteId == editNote.noteId }
        return if (index >= 0) {
            notesList[index] = editNote
            println("Заметка изменена")
            true
        } else {
            throw NoteNotFoundException("Заметка с id ${editNote.noteId} не найдена")
        }
    }
    fun delete(noteId: Long): Boolean {
        if (doesNoteExist(noteId)) {
            notesList.removeIf { it.noteId == noteId }
            commentsList.removeAll { it.noteId == noteId }
            println("Заметка с id $noteId удалена")
            return true
        }
        throw NoteNotFoundException("Заметка с id $noteId не существует")
    }

    fun read() {
        println("Список заметок:")
        this.notesList.forEach(::println)
    }

    fun getById(id: Long): Note {
        for (note in notesList) if (note.noteId == id) {
            return note
        }
        throw NoteNotFoundException("Поста с id $id нет")
    }

    fun createComment(noteId: Long, message: String): Boolean {
        return if (doesNoteExist(noteId)) {
            commentsList.add(NoteComment(noteId = noteId,message = message, commentId = ++commentId))
            println(commentsList.last())
            return true
        } else throw NoteNotFoundException("Невозможно оставить комментарий. Поста с id $noteId нет")
    }

    fun editComment(commentId: Long, message: String): Boolean {
        for ((index, comment) in commentsList.withIndex()) {
            if (comment.commentId == commentId) {
                commentsList[index] = comment.copy(message = message)
                println("Комментарий с id $commentId изменён")
                return true
            }
        }
        throw NoteNotFoundException("Заметка и комментарии удалены")
    }

    fun deleteComment(commentId: Long): Boolean {
        for (comment in commentsList) {
            if (commentId == comment.commentId) {
                comment.visibility = false
                println("Комментарий c id $commentId удалён")
                return true
            }
        }
        throw NoteNotFoundException("Комментарий с id $commentId уже удалён")
    }

    fun restoreComment(commentId: Long): Boolean {
        for (comment in commentsList) {
            if (comment.commentId == commentId && !comment.visibility) {
                comment.visibility = true
                println("Комментарий с id $commentId восстановлен")
                return true
            }
        }
        throw NoteNotFoundException("Комментарий с id $commentId не существует")
    }

    fun getComments(noteId: Long) {
        if (!doesNoteExist(noteId)) {
            throw NoteNotFoundException("Заметка с id $noteId не существует")
        }
        println("Список комментариев к заметке $noteId:")
        commentsList.forEach { comment ->
            if (comment.noteId == noteId && comment.visibility) {
                println(comment)
            }
        }
    }

    private fun doesNoteExist(noteId: Long): Boolean {
        var exists = false
        this.notesList.forEach {
            if (it.noteId == noteId) {
                exists = true
            }
        }
        return exists
    }
    fun clear() {
        notesList = mutableListOf()
        noteId = 10000
        commentsList = mutableListOf()
        commentId = 20000
    }
}
