import { defineStore } from 'pinia'
import { Client } from '@stomp/stompjs'
import { reactive } from 'vue'

export const useSocketStore = defineStore('socket', () => {
  const state = reactive({
    socket: null as Client | null,
    isConnected: false,
    privateMessages: [] as object[],
    roomMessages: [] as object[],
    privateSubscription: null as any,
    roomSubscriptions: new Map<number, any>() // Подписки на комнаты
  })

  // Подключение к WebSocket
  const connect = (url: string) => {
    state.socket = new Client({
      brokerURL: url,
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
      // if (!state.roomMessages[roomId]) {
      //   state.roomMessages[roomId] = []
      // }
      state.roomMessages.unshift(roomMessage)
      console.log(`Received message for room ${roomId}:`, roomMessage)
    })
    // Сохраняем подписку
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

  // Отключение от WebSocket
  const disconnect = () => {
    state.socket?.deactivate()
    state.isConnected = false
    console.log('WebSocket disconnected')

    // Очистка подписок
    state.privateSubscription = null
    state.roomSubscriptions.clear()
  }

  return {
    state,
    connect,
    subscribeToPrivateMessages,
    subscribeToRoom,
    sendPrivateMessage,
    sendRoomMessage,
    disconnect
  }
})
