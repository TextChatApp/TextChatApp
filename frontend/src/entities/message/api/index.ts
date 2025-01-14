import { http } from '@/shared/api'

export const getChatHistory = (id: string | undefined) => {
  return http.get(`/chats/${id}/messages`)
}
