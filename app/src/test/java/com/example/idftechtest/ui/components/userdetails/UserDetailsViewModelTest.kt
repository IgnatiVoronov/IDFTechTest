import com.example.idftechtest.data.model.Address
import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.repository.UserRepository
import com.example.idftechtest.ui.components.userdetails.UserDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class UserDetailsViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UserDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        userRepository = mock(UserRepository::class.java)
        viewModel = UserDetailsViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUser Details should update userDetails when repository returns data`() = runTest {
        val userId = 1
        val user = User(userId, "John", "john@example.com", "1234567890", Address("New York"))
        `when`(userRepository.getUserDetails(userId)).thenReturn(user)

        kotlinx.coroutines.test.runTest { viewModel.getUserDetails(userId) }

        val result = viewModel.userDetails.first()
        assertEquals(user, result)
        verify(userRepository, times(1)).getUserDetails(userId)
    }

    @Test
    fun `getUser Details should update errorMessage when repository throws exception`() = runTest {
        val userId = 1
        val exception = RuntimeException("Network error")
        `when`(userRepository.getUserDetails(userId)).thenThrow(exception)

        kotlinx.coroutines.test.runTest { viewModel.getUserDetails(userId) }

        val result = viewModel.errorMessage.first()
        assertEquals("Data access error: ${exception.message}", result)
        verify(userRepository, times(1)).getUserDetails(userId)
    }

    @Test
    fun `clearErrorMessage should clear error message`() = runTest {
        val userId = 1
        val exception = RuntimeException("Network error")
        `when`(userRepository.getUserDetails(userId)).thenThrow(exception)
        viewModel.getUserDetails(userId)

        viewModel.clearErrorMessage()

        val result = viewModel.errorMessage.first()
        assertEquals("", result)
    }
}