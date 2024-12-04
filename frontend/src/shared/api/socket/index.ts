import { ref } from 'vue'
import { Client } from '@stomp/stompjs'

export default function useWebSocket(url: string) {
  const socket = ref<Client | null>(null)
  const messages = ref<object[]>([])
  const isConnected = ref<boolean>(false)

  // Устанавливаем соединение через STOMP
  const connect = () => {
    socket.value = new Client({
      brokerURL: url, // Ваша точка подключения
      connectHeaders: {},
      onConnect: () => {
        isConnected.value = true
        console.log('WebSocket connected')

        // Подписываемся на топик, чтобы получать сообщения
        socket.value?.subscribe('/topic/messages', (message) => {
          messages.value.push(JSON.parse(message.body))
        })
      },
      onDisconnect: () => {
        isConnected.value = false
        console.log('WebSocket disconnected')
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame)
      }
    })

    socket.value.activate() // Подключаемся к серверу
  }

  // Отправляем сообщение на сервер
  const sendMessage = (message: string, senderId: number, receiverId: number) => {
    if (socket.value && socket.value.connected) {
      const messageDTO = {
        sender: { id: senderId, username: 'dsdsd' },
        receiver: { id: receiverId, username: '11111' },
        content: message
      }
      console.log(JSON.stringify(messageDTO))
      socket.value.publish({
        destination: '/app/sendMessage',
        body: JSON.stringify(messageDTO)
      })
    } else {
      console.error('WebSocket is not open')
    }
  }

  // Закрываем соединение
  const disconnect = () => {
    socket.value?.deactivate()
  }

  return {
    connect,
    sendMessage,
    disconnect,
    messages,
    isConnected
  }
}
