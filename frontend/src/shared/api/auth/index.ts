import { http } from '../http'

export const login = ({ username, password }: { username: string; password: string }) => {
  return http.post('/auth/login', { username, password })
}

export const register = ({
  email,
  username,
  password
}: {
  email: string
  username: string
  password: string
}) => {
  return http.post('/auth/register', { email, username, password })
}
