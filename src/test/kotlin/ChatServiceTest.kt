import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ChatServiceTest {

    var chatService = ChatService()

    @Before
    fun clearBeforeTest() {
        chatService.clear()
        chatService.sendMessage(0, 1, 1, "Hellow !")
        chatService.sendMessage(0, 1, 1, "Hy ")
        chatService.sendMessage(0, 1, 1, "How ")
        chatService.sendMessage(0, 2, 2, "Bye Bye ")
        chatService.sendMessage(0,3,3,"I am tree")
        chatService.sendMessage(0,3,3,"I am four")
        chatService.sendMessage(0,3,3,"I am five")
    }

    @Test
    fun  getMessageFromChat_validChatId_returnListWithMessage() {
        var list = chatService.getMessageFromChat(1,1,2)
        assertEquals(2,list.size)
        assertEquals(1,list[0].id)
        assertEquals(2,list[1].id)

    }

    @Test(expected = ChatNotFoundException::class)
    fun getMessageFromChat_invalidChatId_throwChatNotFoundException() {
        chatService.getMessageFromChat(4,1,1)
    }

    @Test
    fun deleteMessage_validMessageId_returnOne() {
        assertEquals(1,chatService.deleteMessage(1,1))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteMessage_invalidMessageId_throwMessageNotFoundException() {
        chatService.deleteMessage(1,5)
    }


    @Test
    fun deleteChat_validChatId_returnOne() {
        assertEquals(1,chatService.deleteChat(1))
    }

    @Test(expected = ChatNotFoundException::class)
    fun deleteChat_invalidChatId_throwChatNotFoundException() {
        chatService.deleteChat(4)
    }
}