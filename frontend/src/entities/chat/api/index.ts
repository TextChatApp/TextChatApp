import { http } from '@/shared/api'
import { type IChat } from '../model/type'

export const getMyChats = (userToken: string) => {
  return http.get('/chats/my-chats', {
    headers: {
      Authorization: userToken
    }
  })
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

export const getChatInfo = (id: any, token: string | null) => {
  return http.get(`/chats/${id}`, {
    headers: {
      Authorization: token
    }
  })
}
