import com.fraucred.Sample
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class SampleTest {

    private val testSample: Sample = Sample()

    @Test
    fun testSum() {
        val expected = 44
        assertEquals(expected, testSample.sum(40, 2))
    }
}