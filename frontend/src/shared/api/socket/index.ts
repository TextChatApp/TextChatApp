import { defineStore } from 'pinia'
import { Client } from '@stomp/stompjs'
import { reactive } from 'vue'
import { useUser } from '@/entities/user'

export const useSocketStore = defineStore('socket', () => {
  const state = reactive({
    socket: null as Client | null,
    isConnected: false,
    privateMessages: [] as object[],
    roomMessages: [] as object[],
    privateSubscription: null as any,
    users: [] as any[],
    usersSub: null as any,
    roomSubscriptions: new Map<number, any>() // Подписки на комнаты
  })

  // Подключение к WebSocket
  const connect = (url: string, token: any) => {
    state.socket = new Client({
      brokerURL: url,
      connectHeaders: {
        Authorization: `Bearer ${token}`
      },
      disconnectHeaders: {
        Authorization: `Bearer ${token}`
      },
      onConnect: () => {
        state.isConnected = true
        console.log('WebSocket connected')
      },
      onDisconnect: () => {
        state.isConnected = false
        console.log('WebSocket disconnected')
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame)
      }
    })

    try {
      state.socket.activate()
    } catch (error) {
      console.error('Error during WebSocket connection:', error)
    }
  }

  // Подписка на личные сообщения
  const subscribeToPrivateMessages = () => {
    if (!state.isConnected) {
      console.error('WebSocket is not connected')
      return
    }

    // Если подписка уже активна, пропускаем повторную подписку
    if (state.privateSubscription) {
      return
    }

    state.privateSubscription = state.socket?.subscribe('/topic/messages', (message) => {
      console.log(message)
      const privateMessage = JSON.parse(message.body)
      state.privateMessages.unshift(privateMessage)
      console.log('Received private message:', privateMessage)
    })
  }

  // Подписка на сообщения в комнатах
  const subscribeToRoom = (roomId: any) => {
    if (!state.isConnected) {
      console.error('WebSocket is not connected')
      return
    }

    // Проверяем, подписаны ли уже на эту комнату
    if (state.roomSubscriptions.has(roomId)) {
      console.log(`Already subscribed to room ${roomId}`)
      return
    }
    const subscription = state.socket?.subscribe(`/topic/room/${roomId}`, (message) => {
      const roomMessage = JSON.parse(message.body)

      state.roomMessages.unshift(roomMessage)
      console.log(`Received message for room ${roomId}:`, roomMessage)
    })
    if (subscription) {
      state.roomSubscriptions.set(roomId, subscription)
    }
  }

  // Отправка личного сообщения
  const sendPrivateMessage = (message: string, senderId: number, receiverId: number) => {
    if (state.isConnected && state.socket?.connected) {
      const privateMessageDTO = {
        sender: { id: senderId },
        receiver: { id: receiverId },
        content: message
      }
      console.log('Sending private message:', JSON.stringify(privateMessageDTO))
      state.socket?.publish({
        destination: '/app/sendMessage',
        body: JSON.stringify(privateMessageDTO)
      })
    } else {
      console.error('WebSocket is not open')
    }
  }

  // Отправка сообщения в комнату
  const sendRoomMessage = (message: string, senderId: number, roomId: any) => {
    if (state.isConnected && state.socket?.connected) {
      const roomMessageDTO = {
        userId: senderId,
        roomId: roomId,
        content: message
      }
      console.log('Sending room message:', JSON.stringify(roomMessageDTO))
      state.socket?.publish({
        destination: '/app/room/sendMessage',
        body: JSON.stringify(roomMessageDTO)
      })
    } else {
      console.error('WebSocket is not open')
    }
  }

  const subscribeToUser = () => {
    if (!state.isConnected) {
      console.error('WebSocket is not connected')
      return
    }

    const userStore = useUser()

    // Если подписка уже активна, пропускаем повторную подписку
    if (state.usersSub) {
      return
    }

    state.usersSub = state.socket?.subscribe('/topic/status', (message) => {
      const userStatus = JSON.parse(message.body)
      state.users.unshift(userStatus)
      userStore.updateUserStatus(userStatus)
      console.log('Received private message:', userStatus)
    })
  }

  // Отключение от WebSocket
  const disconnect = () => {
    state.socket?.deactivate()
    state.isConnected = false
    console.log('WebSocket disconnected')

    // Очистка подписок
    state.privateSubscription = null
    state.roomSubscriptions.clear()
    state.usersSub = null
  }

  return {
    state,
    connect,
    subscribeToPrivateMessages,
    subscribeToRoom,
    subscribeToUser,
    sendPrivateMessage,
    sendRoomMessage,
    disconnect
  }
})
