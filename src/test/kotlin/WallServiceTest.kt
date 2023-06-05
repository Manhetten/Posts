import org.junit.Before
import org.junit.Test
import kotlin.test.*

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPostToWall() {

        val post = Post()
        val result = WallService.addPostToWall(post = post).id

        assertNotEquals(0, result)
    }

    @Test
    fun updateExisting() {
        WallService.addPostToWall(post = Post())

        val result = WallService.update(post = Post(1, likes = Likes(5)))

        assertTrue(result)
    }

    @Test
    fun updateNotExisting() {
        WallService.addPostToWall(post = Post())

        val result = WallService.update(post = Post(0, likes = Likes(5)))

        assertFalse(result)
    }

    @Test
    fun createComment() {
        val post = WallService.addPostToWall(post = Post())
        val result = WallService.createComment(1, comment = Comment(text = "Good bye"))
        assertNotEquals(0, result.id)
    }

    @Test(expected = PostNotFoundExeption::class)
    fun shouldThrow() {
        val post = WallService.addPostToWall(post = Post())
        val result = WallService.createComment(23, comment = Comment())
    }

}