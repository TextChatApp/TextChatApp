import { http } from '@/shared/api'

export const getAllUsers = () => {
  return http.get('/users')
}
