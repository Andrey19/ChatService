import java.util.stream.Collectors.toList
import kotlin.streams.toList

fun main(args: Array<String>) {
    var chatService = ChatService()
    chatService.sendMessage(0, 1, 1, "Hellow !")
    chatService.sendMessage(0, 1, 1, "Hy ")
    chatService.sendMessage(0, 1, 1, "How ")
    chatService.sendMessage(0, 2, 2, "Bye Bye ")
    chatService.sendMessage(0,3,3,"I am tree")
    chatService.sendMessage(0,3,3,"I am four")
    chatService.sendMessage(0,3,3,"I am five")
    println("1111111111111111111111111111")
    println(chatService.getMessageFromChat(3,1,3))
    println("1111111111111111111111111111")
    println("-------")
    println(chatService.chats)
    println("---------getUnreadChatsCount------------------")
    println(chatService.getUnreadChatsCount())
    println("---------------------------")
    println("---------getChatsWithLastMessage------------------")
    println(chatService.getChatsWithLastMessage())
    println("---------------------------")
    try {
        chatService.deleteChat(1)
    } catch (e: ChatNotFoundException) {
        println(" ${e.message}")
    }
    println(chatService.chats)
    try {
        chatService.deleteChat(1)
    } catch (e: ChatNotFoundException) {
        println(" ${e.message}")
    }
    println("----- Delete Message -----")
    try {
        chatService.deleteMessage(3,2)
    } catch (e: MessageNotFoundException) {
        println(" ${e.message}")
    }
    println(chatService.chats)

}
class ChatNotFoundException(message: String) : RuntimeException(message)
class MessageNotFoundException(message: String) : RuntimeException(message)
class ChatService() {
    var chats = mutableListOf<Chat>()
    var nextChatId = 1

    fun clear(){
        chats = emptyList<Chat>().toMutableList()
        nextChatId = 1
    }

    fun getChatsWithLastMessage(): List<Chat>?{
        return  chats.filter { chat: Chat ->  !chat.message.last().read  }
    }

    fun getUnreadChatsCount():Int{
        return  chats.count { chat: Chat -> chat.message.count { message: Message -> !message.read  } != 0  }
    }

    fun getMessageFromChat(
        id: Int,
        lastMessageId: Int,
        countMessage: Long
    ): List<Message> {
        var findChat = chats.find { chat: Chat -> id == chat.id }
        if (findChat == null) {
            throw ChatNotFoundException("No chat with Id = $id")
        } else {
            return findChat.message.stream().filter {message: Message -> message.id >= lastMessageId }.limit(countMessage).toList().setRead()
        }
    }
    fun List<Message>.setRead(): List<Message>{
        this.forEach { it.read = true }
        return this
    }

    fun deleteMessage(
        chatId: Int,
        messageId: Int
    ) : Int{
        var findChat = chats.find { chat: Chat -> chatId == chat.id }
        if (findChat == null) {
            throw ChatNotFoundException("No chat with Id = $chatId")
        } else {
            var findMessage = findChat.message.find { message: Message -> messageId == message.id }
            if (findMessage == null){
                throw MessageNotFoundException("No message with Id = $messageId")
            } else {
                findChat.message.remove(findMessage)
                if (findChat.message.isEmpty()){
                    chats.remove(findChat)
                }
                return 1
            }

        }
    }

    fun deleteChat(chatId: Int): Int {
        var findChat = chats.find { chat: Chat -> chatId == chat.id }
        if (findChat == null) {
            throw ChatNotFoundException("No chat with Id = $chatId")
        } else {
            chats.remove(findChat)
            return 1
        }
    }



    fun sendMessage(
        id: Int,
        toId: Int,
        fromId: Int,
        textMessage: String
    ) {
        var findChat = chats.find { chat: Chat -> toId == chat.ownerId }
        if (findChat == null) {
            chats.add(Chat(nextChatId++, toId, mutableListOf(Message(1, fromId, textMessage))))
        } else {
            findChat.message.add(Message(findChat.nextMessageId++, fromId, textMessage))
        }
    }

    override fun toString(): String {
        return "ChatService(chats=$chats)"
    }

}

class Chat(
    var id: Int,
    var ownerId: Int,
    var message: MutableList<Message>
) {
    var nextMessageId = 2
    override fun toString(): String {
        return "Chat(id=$id, ownerId=$ownerId, message=$message)\n"
    }
}

class Message(
    var id: Int,
    var fromId: Int,
    var text: String,
    var read: Boolean = false
) {
    override fun toString(): String {
        return "Message(id=$id, fromId=$fromId, text='$text', read=$read)"
    }
}

