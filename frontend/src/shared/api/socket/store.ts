// stores/socketStore.ts
import { defineStore } from 'pinia'
import { Client } from '@stomp/stompjs'

export const useSocketStore = defineStore('socket', {
  state: () => ({
    socket: null as Client | null,
    isConnected: false,
    messages: [] as object[]
  }),
  actions: {
    connect(url: string) {
      this.socket = new Client({
        brokerURL: url,
        onConnect: () => {
          this.isConnected = true
          console.log('WebSocket connected')
          this.socket?.subscribe('/topic/messages', (message) => {
            this.messages.unshift(JSON.parse(message.body))
          })
        },
        onDisconnect: () => {
          this.isConnected = false
          console.log('WebSocket disconnected')
        },
        onStompError: (frame) => {
          console.error('STOMP error', frame)
        }
      })

      try {
        this.socket.activate() // Подключаемся к серверу
      } catch (error) {
        console.error('Error during WebSocket connection:', error)
      }
    },
    sendMessage(message: string, senderId: number, receiverId: number) {
      if (this.isConnected && this.socket?.connected) {
        const messageDTO = {
          sender: { id: senderId },
          receiver: { id: receiverId },
          content: message
        }
        console.log('Sending message:', JSON.stringify(messageDTO))
        this.socket?.publish({
          destination: '/app/sendMessage',
          body: JSON.stringify(messageDTO)
        })
      } else {
        console.error('WebSocket is not open')
      }
    },
    disconnect() {
      this.socket?.deactivate()
      this.isConnected = false
      console.log('WebSocket disconnected')
    }
  }
})
