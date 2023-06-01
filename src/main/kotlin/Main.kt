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

}

interface Attachment{
    val type: String
}

class Audio(override val type: String) : Attachment{}
class Video(override val type: String) : Attachment{}
class Photo(override val type: String) : Attachment{}
class Doc(override val type: String) : Attachment{}
class Link(override val type: String) : Attachment{}

class AudioAttachment(override val type: String, val audio: Audio): Attachment{}
class VideoAttachment(override val type: String, val video: Video): Attachment{}
class PhotoAttachment(override val type: String, val photo: Photo): Attachment{}
class DocAttachment(override val type: String, val doc: Doc): Attachment{}
class LinkAttachment(override val type: String, val link: Link): Attachment{}

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

class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int = 10,
    val placeholder: Placeholder? = null,
    val canPublishFreeCopy: Boolean = false,
    val editMode: String = "all"
)

class Placeholder()
class PostSource()
class Copyright(
    val id: Int = 1,
    val link: String = "link",
    val name: String = "name",
    val type: String = "type"
)

class Geo(
    val type: String = "place",
    val coordinates: String = "12.05",
    val place: Place = Place()
)

class Place(
    val somePlace: Int = 0
)

class Views(
    val count: Int = 0
)

class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = false,
    val canOpen: Boolean = false
)

class Likes(
    val count: Int = 0,
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
) {
    override fun toString(): String {
        return "$count"
    }
}


object WallService {
    private var wallOfPosts = emptyArray<Post>()
    private var id = 0

    fun addPostToWall(post: Post): Post {
        id += 1
        wallOfPosts += post.copy(id = id)
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