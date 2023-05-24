import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPostToWall() {

        val post = Post()
        val i = WallService.addPostToWall(post = post).id

        assertNotEquals(0, i)
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
}