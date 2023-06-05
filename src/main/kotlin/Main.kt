import java.lang.RuntimeException

fun main() {

    val post = Post(likes = Likes(1))
    val post1 = Post(likes = Likes(1))
    val post2 = Post(likes = Likes(1))

    println(WallService.addPostToWall(post = post))
    println(WallService.addPostToWall(post = post1))
    println(WallService.addPostToWall(post = post2))
    println()

    println(WallService.update(post = Post(id = 1))) // пост с id найден и заменен
    println(WallService.update(post = Post(id = 5))) // пост с id не найден
    println()

    WallService.print() //замена поста с найденным id
    println(WallService.createComment(1, comment = Comment(text = "привет")))
    println(WallService.createComment(3, comment = Comment(text = "пока")))
    println(WallService.createComment(56, comment = Comment(text = "привет")))


}

interface Attachment{
    val type: String
}

data class Audio(val id: Int)
data class Video(val id: Int)
data class Photo(val id: Int)
data class Doc(val id: Int)
data class Link(val id: Int)

data class AudioAttachment(val audio: Audio): Attachment{override val type = "audio"}
data class VideoAttachment(val video: Video): Attachment{override val type = "video"}
data class PhotoAttachment(val photo: Photo): Attachment{override val type = "photo"}
data class DocAttachment(val doc: Doc): Attachment{override val type = "doc"}
data class LinkAttachment(val link: Link): Attachment{ override val type = "link" }

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

class ParentStacks()
class PostNotFoundExeption(message: String): RuntimeException(message)

object WallService {
    private var wallOfPosts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var id = 0
    private var commentId = 0

    fun addPostToWall(post: Post): Post {
        id += 1
        wallOfPosts += post.copy(id = id, likes = post.likes.copy())
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
        throw PostNotFoundExeption("Поста с ID $postId не существует")
    }

    fun clear() {
        wallOfPosts = emptyArray()
        id = 0
    }

    fun print() {
        for (post in wallOfPosts) {
            println(post)
        }
    }

}