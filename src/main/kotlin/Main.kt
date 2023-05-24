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

data class Post(
    val id: Int = 0,
    val ownerId: Int = 123456,
    val fromId: Int = 123456,
    val createdBy: Int = 123456,
    val date: Int = System.currentTimeMillis().toInt(),
    val text: String = "Hello World",
    val postType: String = "post",
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val likes: Likes = Likes()
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