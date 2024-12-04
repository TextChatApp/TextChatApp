import { http } from '@/shared/api'

export const getAllChats = () => {
  return http.get('/chats')
}

export const startPrivateChat = (currentUserId: number, userId: number) => {
  return http.post(
    `/chats/start-chat`,
    JSON.stringify({
      currentUserId: currentUserId,
      userId: userId
    })
  )
}

export const getChatInfo = (id: any) => {
  return http.get(`/chats/${id}`)
}
