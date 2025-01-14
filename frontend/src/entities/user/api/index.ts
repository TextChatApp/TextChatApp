import { http } from '@/shared/api'

const token = localStorage.getItem('token')

export const getAllUsers = () => {
  return http.get('/users')
}

export const getMeInfo = () => {
  return http.get('/users/me', {
    headers: {
      Authorization: token
    }
  })
}

export const changeUserInfoQuery = (data: any) => {
  console.log(data)
  return http.put('/users/me', data, {
    headers: {
      Authorization: token
    }
  })
}

export const changeUserAvatarQuery = (data: any) => {
  return http.post('/users/me/avatar', data, {
    headers: {
      Authorization: token
    }
  })
}
